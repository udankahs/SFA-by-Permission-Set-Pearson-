package com.email.scripts;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import com.lib.ExcelLib;

/* 
 * Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: QEA CRM
 * Organization		: Cognizant Technology Solutions
 */

public class SFA_Main {
	public static void main(String[] args) throws UnsupportedEncodingException {

		WebDriver driver = null;

		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { GetPermissionDetail.class });
		testng.addListener(tla);
		testng.run();

		String JarPath = GetPermissionDetail.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String unformattedFolderPath = JarPath.substring(0, JarPath.lastIndexOf("/") + 1);
		String folderPath = URLDecoder.decode(unformattedFolderPath, "UTF-8");

		String browser = ExcelLib.getCellValue(folderPath + "/Data Sheet/Data Sheet.xls", "Login", 1, 3);

		if (browser.equals("Firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("Internet Explorer")) {
			System.setProperty("webdriver.ie.driver", folderPath+"Browser Drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		else if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", folderPath+"/Browser Drivers/chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.get("file://" + folderPath + "test-output/index.html");
		driver.findElement(By.xpath("//span[text()='Reporter output']")).click();
	}
}