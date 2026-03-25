package com.example.urlshortner.service;

import com.example.urlshortner.model.UrlMapping;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlShortenerServiceTest {

    private final UrlShortenerService service = new UrlShortenerService();

    @Test
    void createShortUrlShouldNormalizeAndStore() {
        UrlMapping mapping = service.createShortUrl("example.com");

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
