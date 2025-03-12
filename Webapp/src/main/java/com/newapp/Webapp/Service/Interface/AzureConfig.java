package com.newapp.Webapp.Service.Interface;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Configuration
public class AzureConfig {

	@Value("${azure.storage.account-name}")
	private String accountname;
	
	@Value("${azure.storage.account-key}")
	private String accountkey;
	
	@Value("${azure.storage.container-name}")
	private String Containername;
	
	public BlobServiceClient blobServiceClient() {
		String endpoint = String.format("https://%s.blob.core.windows.net", accountname);
		String connectionstring = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", accountname, accountkey);
				
		return new BlobServiceClientBuilder()
				.endpoint(endpoint)
				.connectionString(connectionstring)
				.buildClient();
	}
}
