package com.example.urlshortner.controller;

import com.example.urlshortner.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class RedirectController {

    private final UrlShortenerService urlShortenerService;

    public RedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/{shortCode}")
    public String redirect(@PathVariable String shortCode) {
        return urlShortenerService.getOriginalUrl(shortCode)
                .map(url -> "redirect:" + url)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found"));
    }
}
