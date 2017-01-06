package com.hallwaze.pages;

import org.openqa.selenium.By;

import com.hallwaze.utilities.Wrapper;
import com.hallwaze.utilities.CustomLogger;

public class SignInPage {

	By HomeSignInBtn = By.xpath(".//*[@id='home-wrapper']/div/nav/div/a[1]");
	By EmailTxt = By.name("email");
	By NextBtn = By.xpath(".//*[@id='authform']/button");
	By PassTxt = By.xpath(".//*[@id='authform']/div[2]/input");
	By SignInBtn = By.xpath(".//*[@id='authform']/button");

	Wrapper WA = Wrapper.getWAInstance();
	
	private static SignInPage SIP;
	private SignInPage() {
	}
	public static SignInPage getSIPInstance() {
		if (SIP == null) {
			SIP = new SignInPage();
		}
		return SIP;
	}
	
	public SignInPage clickHomeSignInBtn() {
		WA.getDriver().findElement(HomeSignInBtn).click();
		
		return new SignInPage();
	}

	public SignInPage setEmailTxt(String str) {
		WA.getDriver().findElement(EmailTxt).sendKeys(str);
	
		return new SignInPage();
	}

	public SignInPage clickNextBtn() {
		WA.getDriver().findElement(NextBtn).click();
		
		return new SignInPage();
	}

	public SignInPage setPassTxt(String str) {
		WA.getDriver().findElement(PassTxt).sendKeys(str);
		
		return new SignInPage();
	}

	public SignInPage clickSignInBtn() {
		WA.getDriver().findElement(SignInBtn).click();
		
		return new SignInPage();
	}
	


}
