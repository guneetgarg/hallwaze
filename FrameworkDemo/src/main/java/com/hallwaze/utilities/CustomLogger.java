package com.hallwaze.utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CustomLogger {

	public static Logger Log;

	
	public static void start(String messgae) {
		System.out.println(System.getProperty("user.dir")+"\\src\\main\\resources\\report_resource\\" + "Log4j.properties");
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\main\\resources\\report_resource\\" + "Log4j.properties");
		Log = Logger.getLogger(new Exception().getStackTrace()[1].getClassName());
		Log.info(messgae);
		//Log.info(">>>>>>>>>>>>>>>>>>>>>" + TestCaseName+ "<<<<<<<<<<<<<<<<<<<<<<<<<");
	}

	// This is to print log for the ending of the test case
	public static void endTestCase(String sTestCaseName) {
		Log.info("-----------------------" + "-E---N---D-"+ "-----------------------");
	}
}
