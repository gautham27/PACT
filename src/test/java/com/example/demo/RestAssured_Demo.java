package com.example.demo;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class RestAssured_Demo {

	public static void main(String...a)
	{
		
		Response 	Resp=RestAssured.given().when().get("");
		System.out.println(Resp.asString());

	}
}
