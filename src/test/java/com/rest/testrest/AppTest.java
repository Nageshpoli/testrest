package com.rest.testrest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

/**
 * Unit test for simple App.
 */
public class AppTest {

	
	@Test
	public void test1()
	{
		RestAssured.when().get("http://google.com").then().assertThat().statusCode(200);
	}
}
