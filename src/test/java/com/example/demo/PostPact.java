package com.example.demo;

import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;

public class PostPact {

	@Rule
	public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("test_provider_post", "localhost", 8090,
			PactSpecVersion.V2, this);

	@Pact(provider = "test_provider_post", consumer = "test_consumer_post")
	public RequestResponsePact createFragment(PactDslWithProvider builder) {
		RequestResponsePact pact = builder.given("test state")
				.uponReceiving("random port test interaction")
				.path("/topics").method("POST").matchHeader("Content-Type", "application/json")
				.body("{\"id\": \"REST\",\"name\": \"REST ful services\",\"description\": \"rest description\"}")
				.willRespondWith().body("{\"Status\":\"Success\"}").matchHeader("Content-Type", "application/json")
				.status(200)
				.toPact();
		return pact;
	}

	@PactVerification("test_provider_post")
	@Test
	public void runTest() throws Exception {
		// TODO Auto-generated method stub
		String body = "{\"id\": \"REST\",\"name\": \"REST ful services\",\"description\": \"rest description\"}";
		assertEquals(new ConsumerClient(mockProvider.getConfig().url()).postBody("/topics",body).getBody(),"{\"Status\":\"Success\"}");
		System.out.println("******************************************TEST FINISHED**********************************");
	}

}
