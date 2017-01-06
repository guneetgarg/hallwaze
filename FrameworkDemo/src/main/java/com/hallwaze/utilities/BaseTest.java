package com.hallwaze.utilities;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

	Wrapper WA = Wrapper.getWAInstance();

	@BeforeMethod
	@Parameters({ "browserName", "baseurl" })
	public void InitiateDriver(String browserName, String baseurl) {
		WA.openBrowser(browserName);
		WA.navigate(baseurl);
	}

	@AfterMethod
	public void StopDriver(ITestResult result) {
		if (!result.isSuccess()) {
			String methodName = result.getName().toString().trim();
			WA.takeScreenShot(methodName);
		} else {
		}
		WA.StopDriver();
	}

}
