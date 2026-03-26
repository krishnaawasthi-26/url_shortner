package com.example.urlshortner.repository;

import com.example.urlshortner.model.UrlMappingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlMappingRepository extends MongoRepository<UrlMappingDocument, String> {
}
