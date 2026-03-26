package com.example.urlshortner.repository;

import com.example.urlshortner.model.UrlMappingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlMappingRepository extends MongoRepository<UrlMappingDocument, String> {
    Optional<UrlMappingDocument> findByOriginalUrl(String originalUrl);
}
