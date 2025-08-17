package com.engine.v1.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.engine.v1.ingestion.DocumentIngestionService;


@Service
public class ChatService {
	
	private final ChatClient chatClient;
	private final VectorStore vectorStore;
	private final DocumentIngestionService service;
	
	public ChatService(ChatClient.Builder builder, VectorStore vectorStore, DocumentIngestionService service) {
		this.chatClient = builder
				.defaultAdvisors(MessageChatMemoryAdvisor
						.builder(MessageWindowChatMemory
								.builder()
								.maxMessages(3)
								.build())
						.build())
				.build();
		this.vectorStore = vectorStore;
		this.service = service;
	}
	
	public void uploadFile(MultipartFile file) throws IOException {
		
		Path docsPath = Paths.get("docs");
		if(!Files.exists(docsPath)) {
			Files.createDirectories(docsPath);
		}
		Path filePath = docsPath.resolve(file.getOriginalFilename());
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		service.ingestFile(filePath);
	}
	
	public String call(String query) {
		
		List<Document> similarDocs = vectorStore.similaritySearch(query);
		
		String context = similarDocs
				.stream()
				.map(Document::getText)
				.reduce("", (a, b) -> a + "\n" + b);
		
		String prompt = "Context:\n" + context + "\n\nQuestion: " + query;
		
		return chatClient
				.prompt(prompt)
				.system("You are a helpful assistant. Use the Provided context to answer.")
				.call()
				.content();
	}
	
}
