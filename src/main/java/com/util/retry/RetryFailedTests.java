package com.util.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTests implements IRetryAnalyzer{

	int retrycount=0;
	public boolean retry(ITestResult result) {
		
		if(retrycount<2)
		{
			retrycount++;
			return true;
		}
		else
		{
			return false;
		}
		
	}
}
