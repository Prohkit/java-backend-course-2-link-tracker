package edu.java.service;

import jakarta.persistence.AttributeConverter;
import java.net.URI;

public class UriToStringConverter implements AttributeConverter<URI, String> {
    @Override
    public String convertToDatabaseColumn(URI uri) {
        return uri.toString();
    }

    @Override
    public URI convertToEntityAttribute(String s) {
        return URI.create(s);
    }
}
