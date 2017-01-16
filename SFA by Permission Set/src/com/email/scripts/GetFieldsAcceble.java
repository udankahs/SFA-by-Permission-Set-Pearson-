package com.email.scripts;

import org.testng.annotations.Test;

import java.net.URLDecoder;

import org.testng.Reporter;

import com.email.pom.GotoPermissionSet;
import com.email.pom.SFDCLogin;
import com.lib.ExcelLib;

/* 
 * Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: QEA CRM
 * Organization		: Cognizant Technology Solutions
 */

public class GetFieldsAcceble extends SFASuperTestNG {

	@Test
	public void getFields() throws Exception {
		SFDCLogin loginPage = new SFDCLogin(driver);
		GotoPermissionSet gotoPermissionSet = new GotoPermissionSet(driver);

		String JarPath = GetFieldsAcceble.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String folderPath = JarPath.substring(0, JarPath.lastIndexOf("/") + 1);
		String decodedPath = URLDecoder.decode(folderPath, "UTF-8");

		String dataSheetPath = decodedPath + "Data Sheet/Data Sheet.xls";
		String sheetName = "Login";

		String uname = ExcelLib.getCellValue(dataSheetPath, sheetName, 1, 0);
		String password = ExcelLib.getCellValue(dataSheetPath, sheetName, 1, 1);
		String environment = ExcelLib.getCellValue(dataSheetPath, sheetName, 1, 4);
		String URL = "https://test.salesforce.com";
		if (environment.equalsIgnoreCase("Sandbox"))
		{
			URL="https://test.salesforce.com";
		}
		else if (environment.equalsIgnoreCase("Production"))
		{
			URL="https://login.salesforce.com";
		}
		String obj = null;
		String baselineSheetPath = null;

		Reporter.log(
				"<html><head><style>table, th, td { border: 1px solid black; border-collapse: collapse;}</style></head><body>",
				true);
		loginPage.login(uname, password, URL);

		if (loginPage.verifyLogin()) 
		{
			gotoPermissionSet.gotoFieldAaccebilty();
			if(gotoPermissionSet.verifyPermissionVisibility("TestPermissionSet"))
			{
				System.out.println("verifyPermissionVisibility : True");
			}
			else
			{
				System.out.println("verifyPermissionVisibility : False");
			}
		} 
		else 
		{

			Reporter.log(
					"</br><table><tr><th><b>TEST STATUS :</b></th><td> FAILED -- Incorrect Username or Password </td></tr></table>",
					true);
		}
		Reporter.log("</body></html>", true);
	}
}
