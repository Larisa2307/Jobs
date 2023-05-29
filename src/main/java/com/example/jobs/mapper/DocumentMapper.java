package com.example.jobs.mapper;

import com.example.jobs.entity.Document;
import com.example.jobs.entity.UserApp;

public class DocumentMapper {
    public static Document toFile(final UserApp userApp, final String name, final String extension, final byte[] bytes) {
        return Document.builder()
                .name(name)
                .extension(extension)
                .content(bytes)
                .userApp(userApp)
                .build();
    }
}
