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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UrlShortenerServiceTest {

    private UrlMappingRepository repository;
    private UrlShortenerService service;

    @BeforeEach
    void setup() {
        repository = mock(UrlMappingRepository.class);
        service = new UrlShortenerService(repository);
        when(repository.existsById(any())).thenReturn(false);
        when(repository.findByOriginalUrl(any())).thenReturn(Optional.empty());
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

    @Test
    void shouldReturnExistingShortUrlForDuplicateOriginalUrl() {
        UrlMapping first = service.createShortUrl("example.com");

        when(repository.findByOriginalUrl(first.originalUrl()))
                .thenReturn(Optional.of(new UrlMappingDocument(first.shortCode(), first.originalUrl())));

        UrlMapping second = service.createShortUrl("https://example.com");

        assertEquals(first.shortCode(), second.shortCode());
        assertEquals(first.originalUrl(), second.originalUrl());
        verify(repository, times(1)).save(any());
    }
}
