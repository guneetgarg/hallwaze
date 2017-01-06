package com.hallwaze.report;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class CustomReporter implements IReporter {
	LinkedHashMap<String, String> hmap = new LinkedHashMap<String, String>();

	public void generateReport(List<XmlSuite> xmlSuite, List<ISuite> iSuite, String s) {
		for (ISuite suite : iSuite) {

			Map<String, ISuiteResult> suiteResults = suite.getResults();

			for (String testName : suiteResults.keySet()) {
				ISuiteResult suiteResult = suiteResults.get(testName);

				ITestContext testContext = suiteResult.getTestContext();

				hmap.put("Total", Integer.toString(testContext.getPassedTests().size()+ testContext.getFailedTests().size() + testContext.getSkippedTests().size()));
				hmap.put("Passed", Integer.toString(testContext.getPassedTests().size()));
				hmap.put("Failed", Integer.toString(testContext.getFailedTests().size()));
				hmap.put("Skipped", Integer.toString(testContext.getSkippedTests().size()));

				hmap.put("Start Time", testContext.getStartDate().toString());
				hmap.put("End Time", testContext.getEndDate().toString());

				PieChart.GeneratePIEChart(hmap);
				
				//Failed Test Case
				IResultMap failedResult = testContext.getFailedTests();
				Set<ITestResult> testsFailed = failedResult.getAllResults();

				
				//Passed Test Case
				IResultMap passResult = testContext.getPassedTests();
				Set<ITestResult> testsPassed = passResult.getAllResults();

				
				//SkipTest Case
				IResultMap skipResult = testContext.getSkippedTests();
				Set<ITestResult> testsSkip = skipResult.getAllResults();
			
				PdfCreate create = new PdfCreate();
				create.createPDF();
				create.createStatusTable(2, hmap);
				
				create.addImage("PieChart.jpeg");
				
				create.writePassData(testsPassed);
				create.writeFailData(testsFailed);
				create.writeSkipData(testsSkip);
				create.closeDocument();
			}
		}
	}
}