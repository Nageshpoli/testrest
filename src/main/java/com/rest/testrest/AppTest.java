package com.rest.testrest;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test(parameters = { "testUrl" })
	public void test1(String testUrl) {
		Reporter.log("Test Started test1");
		RestAssured.when().get(testUrl).then().assertThat().statusCode(200);
		Reporter.log("Test Ended test1");

		
	}

	@Test
	public void test2() {
	}
	
	@Test
	public void test3() {
		Assert.assertFalse(true);
	}
}
