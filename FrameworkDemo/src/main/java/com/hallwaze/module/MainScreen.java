package com.hallwaze.module;

import org.openqa.selenium.By;

import com.hallwaze.pages.SignInPage;
import com.hallwaze.utilities.Wrapper;

public class MainScreen {

	By introHeaderUserThumb = By.xpath(".//*[@id='introHeaderPanel']/li[10]/a/img");
	By introHeaderUserThumbLogout = By.xpath(".//*[@id='introHeaderPanel']/li[10]/ul/li[8]/a");
	
	Wrapper WA = Wrapper.getWAInstance();
	
	protected MainScreen clickIntroHeaderUserThumb(){
		WA.getDriver().findElement(introHeaderUserThumb).click();
		return new MainScreen();
	}
	
	protected MainScreen clickintroHeaderUserThumbLogout(){
		WA.getDriver().findElement(introHeaderUserThumbLogout).click();
		return new MainScreen();
	}
}
