package com.hallwaze.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Wrapper implements XmlUtil, ExcelUtil {

	WebDriver driver;
	FileInputStream inputStream = null;
	Sheet sheet = null;
	Workbook workbook = null;
	XSSFRow row;

	private static Wrapper WA;

	private Wrapper() {

	}

	public static Wrapper getWAInstance() {
		if (WA == null) {
			WA = new Wrapper();
		}
		return WA;
	}

	public void setDriverNull() {
		if (driver != null)
			driver = null;
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public void openBrowser(String browserName) {
		setDriverNull();
		try {
			if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + FConstant.ChromeDriverPath);
				ChromeOptions options = new ChromeOptions();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
			}

			else if (browserName.equalsIgnoreCase("ie")) {

				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + FConstant.IEDriverPath);
				driver = new InternetExplorerDriver(capabilities);
				driver.manage().window().maximize();
			}

			else if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", FConstant.FirefoxDriverPath);
				driver = new FirefoxDriver();
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	public void navigate(String baseURL) {
		driver.get(baseURL);
	}

	public void StopDriver() {
		try {
			if (driver != null) {
				driver.close();
				driver.quit();
				driver = null;
			}
		} catch (Exception ignore) {
			System.out.println("Getting Exception while closing the driver: " + ignore);
		}
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void takeScreenShot(String methodName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(
					System.getProperty("user.dir") + "\\" + FConstant.ScreenShotPath + "\\" + methodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void waitForBrowserToLoadCompletely() {
		String state = null;
		String oldstate = null;
		try {
			System.out.print("Waiting for browser loading to complete");
			int i = 0;
			while (i < 5) {
				Thread.sleep(1000);
				state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
				System.out.print("." + Character.toUpperCase(state.charAt(0)) + ".");
				if (state.equals("interactive") || state.equals("loading"))
					break;
				/*
				 * If browser in 'complete' state since last X seconds. Return.
				 */

				if (i == 1 && state.equals("complete")) {
					System.out.println();
					return;
				}
				i++;
			}
			i = 0;
			oldstate = null;
			Thread.sleep(2000);

			/*
			 * Now wait for state to become complete
			 */
			while (true) {
				state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
				System.out.print("." + state.charAt(0) + ".");
				if (state.equals("complete"))
					break;

				if (state.equals(oldstate))
					i++;
				else
					i = 0;
				/*
				 * If browser state is same (loading/interactive) since last 60
				 * secs. Refresh the page.
				 */
				if (i == 15 && state.equals("loading")) {
					System.out.println("\nBrowser in " + state + " state since last 60 secs. So refreshing browser.");
					driver.navigate().refresh();
					System.out.print("Waiting for browser loading to complete");
					i = 0;
				} else if (i == 6 && state.equals("interactive")) {
					System.out.println(
							"\nBrowser in " + state + " state since last 30 secs. So starting with execution.");
					return;
				}

				Thread.sleep(4000);
				oldstate = state;

			}
			System.out.println();

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	@Override
	public Document convertToDocument(Object xmlContent) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			if (xmlContent instanceof String) {
				document = builder.parse((String) xmlContent);
			} else if (xmlContent instanceof File) {
				document = builder.parse((File) xmlContent);
			}

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return document;
	}

	@Override
	public String getXmlValue(Object xmlContent, String rootTag, String tagElement) {
		Document document = convertToDocument(xmlContent);
		NodeList nList = document.getElementsByTagName(rootTag);
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println(eElement.getElementsByTagName(tagElement).item(0).getTextContent());
				return eElement.getElementsByTagName(tagElement).item(0).getTextContent();
			}
		}
		return "Tag Element not found";
	}

	@Override
	public Workbook getWoorkBook(String excelFilePath) {
		try {
			inputStream = new FileInputStream(excelFilePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		if (excelFilePath.endsWith("xlsx")) {
			try {
				workbook = new XSSFWorkbook(excelFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (excelFilePath.endsWith("xls")) {
			try {
				workbook = new HSSFWorkbook(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	@Override
	public Sheet getSheet(Object sheetName) {
		if (sheetName instanceof String)
			sheet = workbook.getSheet(sheetName.toString());
		if (sheetName instanceof Integer)
			sheet = workbook.getSheetAt(Integer.parseInt(sheetName.toString()));
		return sheet;
	}

	@Override
	public void closeWorkbook() {
		try {
			this.workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}
		return null;
	}
	
	@Override
	public String getExcelValue(String excelFilePath, Object sheetNumber, String param) {
		String val = null;
		workbook = getWoorkBook(excelFilePath);
		if (sheetNumber instanceof String) {
			sheet = getSheet(sheet.toString());
		} else if (sheetNumber instanceof Integer) {
			sheet = getSheet(Integer.parseInt(sheetNumber.toString()));
		}
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		for (int i = 0; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				if (getCellValue(row.getCell(j)).toString().equalsIgnoreCase(param)) {
					val = (String) getCellValue(row.getCell(j + 1));
					closeWorkbook();
					return val;
				}
			}
			System.out.println();
		}
		return null;
	}

	
	public String getJSON(String path, String key) {
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;

			return (String) jsonObject.get(key);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

}