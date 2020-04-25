package com.example.demo;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.common.net.UrlEscapers;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;

public class CreatePact /* extends ConsumerPactTestMk2 */ {

	@Rule
	public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("test_provider_Demo", "localhost", 8095,PactSpecVersion.V2, this);

	@Pact(provider = "test_provider_Demo", consumer = "test_consumer_Demo")
	public RequestResponsePact createFragment(PactDslWithProvider builder) {
		RequestResponsePact pact = builder.given("test state").uponReceiving("random port test interaction")
				.path("/topics/spring").method("GET").willRespondWith().status(200)
				.body("{\"id\":\"spring\",\"name\":\"spring boot\",\"description\":\"Spring description\"}").toPact();
		return pact;
	}

	@PactVerification("test_provider_Demo")
	@Test
	public void runTest() throws Exception {
		// TODO Auto-generated method stub

		assertEquals(new ConsumerClient(mockProvider.getConfig().url()).getAsString("/topics/spring"),"{\"id\":\"spring\",\"name\":\"spring boot\",\"description\":\"Spring description\"}");
		System.out.println("******************************************TEST FINISHED**********************************");
	}

}
