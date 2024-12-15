package com.example.demo.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LoadFilesService {
	
	public byte[] save (MultipartFile file) throws IOException {
    	byte[] bytes = null;
    	if(!file.isEmpty()) {
    		bytes = file.getBytes();
    	}
		return bytes;
		
    }

}
