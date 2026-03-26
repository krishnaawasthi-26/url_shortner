package com.example.urlshortner.controller;

import com.example.urlshortner.model.UrlMapping;
import com.example.urlshortner.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private final UrlShortenerService urlShortenerService;

    public WebController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("url") String url, HttpServletRequest request, Model model) {
        UrlMapping mapping = urlShortenerService.createShortUrl(url);
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String shortUrl = baseUrl + "/" + mapping.shortCode();

        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("originalUrl", mapping.originalUrl());
        return "index";
    }
}
