package com.example.urlshortner.service;

import com.example.urlshortner.model.UrlMapping;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlShortenerService {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SHORT_CODE_LENGTH = 7;

    private final SecureRandom random = new SecureRandom();
    private final Map<String, String> storage = new ConcurrentHashMap<>();

    public UrlMapping createShortUrl(String originalUrl) {
        String normalizedUrl = normalizeUrl(originalUrl);
        String shortCode = generateUniqueCode();
        storage.put(shortCode, normalizedUrl);
        return new UrlMapping(shortCode, normalizedUrl);
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        return Optional.ofNullable(storage.get(shortCode));
    }

    private String normalizeUrl(String url) {
        String trimmed = url.trim();
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed;
        }
        return "https://" + trimmed;
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = randomCode();
        } while (storage.containsKey(code));
        return code;
    }

    private String randomCode() {
        StringBuilder sb = new StringBuilder(SHORT_CODE_LENGTH);
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }
}
