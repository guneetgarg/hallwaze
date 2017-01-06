package com.hallwaze.pages;

import com.hallwaze.module.MainScreen;

public class LogOutPage extends MainScreen {

	private static LogOutPage LOP;
	private LogOutPage() {
	}
	public static LogOutPage getLOPInstance() {
		if (LOP == null) {
			LOP = new LogOutPage();
		}
		return LOP;
	}

	public void logOutHallwaze() {
		clickIntroHeaderUserThumb();
		clickintroHeaderUserThumbLogout();
	}

}
