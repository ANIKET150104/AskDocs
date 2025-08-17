package com.engine.v1.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.engine.v1.service.ChatService;

@Controller
@RequestMapping("/")
public class ChatController {
	
	private final ChatService service;
	public ChatController(ChatService service) {
		this.service = service;
	}
	
	@GetMapping
	public String home() {
		return "index";
	}
	
	@PostMapping("upload")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			service.uploadFile(file);
			return "success";
		} catch (IOException e) {
			return "Error: " + e.getMessage();
		}
	}
	
	@GetMapping("chat")
	@ResponseBody
	public String chat(@RequestParam String query) {
		var answer = service.call(query).replace("\n", "<br>");
		return "<div class='message user'>" + query + "</div>" + "<div class='message bot'>" + answer + "</div>";
	}

}
