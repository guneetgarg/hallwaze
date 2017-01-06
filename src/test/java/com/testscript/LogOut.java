package com.testscript;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hallwaze.utilities.BaseTest;
import com.hallwaze.utilities.CustomLogger;

public class LogOut extends BaseTest {

	@Test(description="Positive Scenario")
	public void LogOutScenario() {
		
		CustomLogger.start("Login to Hallwaze");
		CustomLogger.Log.info("Hell");
		Assert.assertEquals(false, true);
		CustomLogger.endTestCase("end");
	}
	
	@Test
	public void LogoutPassScenerio()
	{
		Assert.assertEquals(true, true);
	}

}
