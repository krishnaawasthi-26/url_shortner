package com.example.urlshortner.service;

import com.example.urlshortner.model.UrlMapping;
import com.example.urlshortner.model.UrlMappingDocument;
import com.example.urlshortner.repository.UrlMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UrlShortenerServiceTest {

    private UrlMappingRepository repository;
    private UrlShortenerService service;

    @BeforeEach
    void setup() {
        repository = mock(UrlMappingRepository.class);
        service = new UrlShortenerService(repository);
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void createShortUrlShouldNormalizeAndStore() {
        UrlMapping mapping = service.createShortUrl("example.com");
        when(repository.findById(mapping.shortCode()))
                .thenReturn(Optional.of(new UrlMappingDocument(mapping.shortCode(), mapping.originalUrl())));

        assertTrue(mapping.originalUrl().startsWith("https://"));
        assertEquals(7, mapping.shortCode().length());
        assertEquals(mapping.originalUrl(), service.getOriginalUrl(mapping.shortCode()).orElseThrow());
    }

    @Test
    void eachShortCodeShouldBeUnique() {
        UrlMapping first = service.createShortUrl("https://one.example");
        UrlMapping second = service.createShortUrl("https://two.example");

        assertNotEquals(first.shortCode(), second.shortCode());
    }
}
