package com.util.report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

public class TestReport2 implements IReporter {

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		
		
		//*************************//
		VelocityEngine ve = new VelocityEngine();
		
        try {
			ve.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File f = new File("report/output1.html");
        FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
        
        Map Suites = new HashMap();
        
        Map TestContexts = new HashMap();
        ArrayList TestContext = new ArrayList();
        
        
        //*************************//
        
		// Iterating over each suite included in the test
		for (ISuite suite : suites) {

			// Following code gets the suite name
			String suiteName = suite.getName();
			
			// Getting the results for the said suite
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			
			for (ISuiteResult sr : suiteResults.values()) {
				ITestContext tc = sr.getTestContext();
				String testName = tc.getName();
				
				for (ITestNGMethod m : tc.getAllTestMethods()) {
					System.out.println(m.getMethodName());
//					System.out.println(m.getCurrentInvocationCount());

					Iterator<ITestResult> pi = tc.getPassedTests().getResults(m).iterator();
					while (pi.hasNext()) {
						ITestResult ir = (ITestResult) pi.next();
						System.out.println(statusFomat(ir.getStatus()));
						
						List<String> output = Reporter.getOutput(ir);
				        if (null != output && output.size() > 0) {
				          for (String s : output) {
				            System.out.println(s);
				          }
				        }
					}

					Iterator<ITestResult> fi = tc.getFailedTests().getResults(m).iterator();
					while (fi.hasNext()) {
						ITestResult ir = (ITestResult) fi.next();
						System.out.println(statusFomat(ir.getStatus()));
					}

					Iterator<ITestResult> si = tc.getSkippedTests().getResults(m).iterator();
					while (si.hasNext()) {
						ITestResult ir = (ITestResult) si.next();
						System.out.println(statusFomat(ir.getStatus()));
					}
					
					Iterator<ITestResult> spi = tc.getFailedButWithinSuccessPercentageTests().getResults(m).iterator();
					while (spi.hasNext()) {
						ITestResult ir = (ITestResult) spi.next();
						System.out.println(statusFomat(ir.getStatus()));
					}
					
//					Iterator<ITestResult> spi1 = tc.getPassedTests().getResults(m).iterator();
//					while (spi1.hasNext()) {
//						ITestResult ir1 = (ITestResult) spi1.next();
//						System.out.println(statusFomat(ir1.));
//					}

					// System.out.println(tc.getFailedConfigurations().getResults(m).toString());
					
			        /*
			         *  add that list to a VelocityContext
			         */
			       
			        
				}
			}
			
			Suites.put(suiteName, TestContexts);
			
		}
		
		 //*************************//
//		list.add(suiteResults);
        VelocityContext context = new VelocityContext();
        context.put("Suites", Suites);

        /*
         *   get the Template  
         */

        Template t=null;
		try {
			t = ve.getTemplate( "src/config/html_report.vm" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        /*
         *  now render the template into a Writer, here 
         *  a StringWriter 
         */

        StringWriter writer = new StringWriter();

        try {
			t.merge( context, writer );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        /*
         *  use the output in the body of your emails
         */

//        System.out.println( "out put is :"+writer.toString());
        try {
			bos.write(writer.toString().getBytes());
	        bos.close();
	        fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        //*************************//

	}

	private String statusFomat(int status) {
		// TODO Auto-generated method stub
		if(status==1)
		{
			return "PASS";
		}
		if(status==2)
		{
			return "FAIL";
		}
		return "SKIP";
	}

}
