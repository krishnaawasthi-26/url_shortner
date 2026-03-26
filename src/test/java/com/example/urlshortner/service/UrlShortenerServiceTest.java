package com.example.urlshortner.service;

import com.example.urlshortner.model.UrlMapping;
import com.example.urlshortner.model.UrlMappingDocument;
import com.example.urlshortner.repository.UrlMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlMappingRepository repository;

    private UrlShortenerService service;

    @BeforeEach
    void setUp() {
        service = new UrlShortenerService(repository);
    }

    @Test
    void createShortUrlShouldNormalizeAndStore() {
        when(repository.existsById(any())).thenReturn(false);
        ArgumentCaptor<UrlMappingDocument> captor = ArgumentCaptor.forClass(UrlMappingDocument.class);
        when(repository.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        UrlMapping mapping = service.createShortUrl("example.com");

        assertTrue(mapping.originalUrl().startsWith("https://"));
        assertEquals(7, mapping.shortCode().length());
        assertEquals(mapping.shortCode(), captor.getValue().getShortCode());
        assertEquals(mapping.originalUrl(), captor.getValue().getOriginalUrl());
    }

    @Test
    void eachShortCodeShouldBeUnique() {
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UrlMapping first = service.createShortUrl("https://one.example");
        UrlMapping second = service.createShortUrl("https://two.example");

        assertNotEquals(first.shortCode(), second.shortCode());
    }

    @Test
    void getOriginalUrlShouldUseRepositoryLookup() {
        when(repository.findById("abc1234"))
                .thenReturn(Optional.of(new UrlMappingDocument("abc1234", "https://example.com", null)));

        Optional<String> resolved = service.getOriginalUrl("abc1234");

        assertTrue(resolved.isPresent());
        assertEquals("https://example.com", resolved.orElseThrow());
    }
}
