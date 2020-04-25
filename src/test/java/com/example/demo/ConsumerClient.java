
package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.UrlEscapers;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class ConsumerClient{
 //   private static final String TESTREQHEADER = "testreqheader";
 //   private static final String TESTREQHEADERVALUE = "testreqheadervalue";
    private String url;

    public ConsumerClient(String url) {
        this.url = url;
    }
    
    public String getAsString(String path) throws IOException {
    	return	new RestTemplate().getForEntity(url+path, String.class).getBody().toString();
    	}
    
   public ResponseEntity postBody(String path, String body) throws IOException {
    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(); 
		headers.add("Content-Type", "application/json"); 
		HttpEntity<?> httpEntity = new HttpEntity<Object>(body,headers);
		ResponseEntity<String> response= new RestTemplate().exchange(url+path, HttpMethod.POST, httpEntity, String.class);
		return response;
   }

}
