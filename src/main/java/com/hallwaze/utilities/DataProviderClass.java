package com.hallwaze.utilities;

import org.testng.annotations.DataProvider;

import com.hallwaze.utilities.Wrapper;

public class DataProviderClass {

	static Wrapper WA = Wrapper.getWAInstance();
	static String path = System.getProperty("user.dir") + "\\src\\test\\resources\\test_data\\data.xml";
	static String pathExcel = System.getProperty("user.dir") + "\\src\\test\\resources\\test_data\\webApplicationSuite.xlsx";

	@DataProvider(name = "XmlTestData")
	public static Object[][] getXmlTestData() {
		return new Object[][] { { WA.getXmlValue(path, "prod", "email"), WA.getXmlValue(path, "prod", "password") } };
	}
	
	@DataProvider(name = "ExcelTestData")
	public static Object[][] getExcelTestData() {
		System.out.println(WA.getExcelValue(pathExcel, 0, "username"));
		System.out.println(WA.getExcelValue(pathExcel, 0, "password"));
		return new Object[][] { { WA.getExcelValue(pathExcel, 0, "username"),WA.getExcelValue(pathExcel, 0, "password") } };
	}
}
