package com.newapp.Webapp.Service.Interface;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AzureService {

	@Value("${azure.storage.container-name}")
	private String containername;
	
	private BlobServiceClient blobServiceClient;
	
	@Autowired
	public AzureService(BlobServiceClient blobServiceClient) {
		this.blobServiceClient = blobServiceClient;
	}
	
	public String saveimageblob(MultipartFile photo) {
		try {
			String blobname = photo.getOriginalFilename();
			
			
			BlobContainerClient containerclient = blobServiceClient.getBlobContainerClient(containername);
			BlobClient blobclient  = containerclient.getBlobClient(blobname);
			
			InputStream input = photo.getInputStream();
			
			BlobHttpHeaders header = new BlobHttpHeaders().setContentType("image/jpeg");
			
			blobclient.upload(input,photo.getSize(),true);
			blobclient.setHttpHeaders(header);
			
			return blobclient.getBlobUrl();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("unable to find:" + e.getMessage());
		}
	}
}
