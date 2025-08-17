package com.engine.v1.ingestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@Service
public class DocumentIngestionService {

    private final VectorStore vectorStore;

    private static final Logger log = LoggerFactory.getLogger(DocumentIngestionService.class);
    
    @Value("${app.docs.path}")
    private String docsPath;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingestFolder() throws IOException {
    	
        Path path = Paths.get(docsPath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Docs folder not found: " + docsPath);
        }

        try (Stream<Path> files = Files.walk(path)) {
            files.filter(Files::isRegularFile).forEach(file -> {
                try {
                    ingestFile(file);
                } catch (Exception e) {
                    log.error("Failed to ingest " + file + ": " + e.getMessage());
                }
            });
        }
    }

    public void ingestFile(Path file) throws IOException {
        log.info("Ingesting: " + file.getFileName());

        TikaDocumentReader reader = new TikaDocumentReader(new FileSystemResource(file));

        
        var splitter = new TokenTextSplitter();
        
        List<Document> docs = splitter.apply(reader.read());

        vectorStore.accept(docs);
        
        log.info("Stored " + docs.size() + " chunks from: " + file.getFileName());
        
    }
}

