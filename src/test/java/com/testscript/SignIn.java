package com.testscript;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hallwaze.pages.SignInPage;
import com.hallwaze.utilities.BaseTest;
import com.hallwaze.utilities.DataProviderClass;

public class SignIn extends BaseTest {

	SignInPage SIP = SignInPage.getSIPInstance();

	@Test(dataProvider = "ExcelTestData", dataProviderClass = DataProviderClass.class)
	public void loginHallwazeValidation(String email, String password) {

	 
		SIP.clickHomeSignInBtn().setEmailTxt(email).clickNextBtn()
				.setPassTxt(password).clickSignInBtn();
	}

	@Test
	public void loginHallwazeVerification()
	{
		Assert.assertEquals(false, true);
	}
	
}
