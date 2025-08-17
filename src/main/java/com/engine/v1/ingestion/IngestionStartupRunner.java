package com.engine.v1.ingestion;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class IngestionStartupRunner implements CommandLineRunner {

    private final DocumentIngestionService ingestionService;
    
    private static final Logger log = LoggerFactory.getLogger(DocumentIngestionService.class);

    private String ingestionMarkerFile = ".ingested";

    private boolean ingestionOnStartup = true;

    public IngestionStartupRunner(DocumentIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @Override
    public void run(String... args) throws Exception {
        Path marker = Path.of(ingestionMarkerFile);

        if (!ingestionOnStartup) {
            log.info("Ingestion on startup is disabled.");
            return;
        }

        if (Files.exists(marker)) {
            log.info("Ingestion already completed once. Skipping...");
            return;
        }

        log.info("Running initial ingestion...");
        ingestionService.ingestFolder();
        Files.createFile(marker);
        log.info("Ingestion complete. Marker file created: " + ingestionMarkerFile);
    }
}
