package com.example.demo;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class RestClass {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = "http://localhost:8080/topics";
		
		  //Post request 
		  String body ="{\"id\": \"REST\",\"name\": \"REST ful services\",\"description\": \"rest description\"}";
		  MultiValueMap<String, String> headers = new LinkedMultiValueMap<String,
		  String>(); headers.add("Content-Type", "application/json"); HttpEntity<?>
		 httpEntity = new HttpEntity<Object>(body, headers); ResponseEntity<String>
		  response = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, httpEntity,
		  String.class); System.out.println(response.getBody());
		 
		
		/*
		  fooResourceUrl = "http://localhost:8080"; 
		  // Get request String
		  resp=Request.Get(fooResourceUrl + Arrays.asList("/topics/spring".split("/"))
		  .stream().map(UrlEscapers.urlPathSegmentEscaper()::escape).collect(Collectors
		  .joining("/"))).addHeader("testreqheader", "testreqheadervalue")
		  .execute().returnContent().asString(); System.out.println(resp);
		 */

	}

}
