package com.infinity.blogAppApis.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String uploadImage(String path, MultipartFile file) throws IOException;

	InputStream serveImage(String path, String fileName) throws FileNotFoundException;

}
