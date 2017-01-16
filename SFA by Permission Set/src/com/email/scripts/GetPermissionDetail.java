package com.email.scripts;

import org.testng.annotations.Test;

import java.net.URLDecoder;

import org.testng.Reporter;

import com.email.pom.GotoPermissionSet;
import com.email.pom.SFDCLogin;
import com.email.pom.VerifyFieldPermissions;
import com.email.pom.VerifyObjectSettings;
import com.email.pom.VerifyRecordTypeAssignments;
import com.lib.ExcelLib;

/* 
 * Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: QEA CRM
 * Organization		: Cognizant Technology Solutions
 */

public class GetPermissionDetail extends SFASuperTestNG
{

	@Test
	public void getFields() throws Exception {
		SFDCLogin loginPage = new SFDCLogin(driver);
		GotoPermissionSet gotoPermissionSet = new GotoPermissionSet(driver);
		VerifyObjectSettings verifyObjectSettings = new VerifyObjectSettings(driver);
		VerifyRecordTypeAssignments verifyRecordTypeAssignments = new VerifyRecordTypeAssignments (driver);
		VerifyFieldPermissions verifyFieldPermissions = new VerifyFieldPermissions (driver); 

		String JarPath = GetPermissionDetail.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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
			URL = "https://test.salesforce.com";
		} else if (environment.equalsIgnoreCase("Production"))
		{
			URL = "https://login.salesforce.com";
		}

		Reporter.log("<html><head><style>table, th, td { border: 1px solid black; border-collapse: collapse;}</style></head><body>", true);
		loginPage.login(uname, password, URL);

		if (loginPage.verifyLogin())
		{
			int PrmSetCount = ExcelLib.getRowCountofColumn(dataSheetPath, sheetName, 2);
			for (int a = 1; a < PrmSetCount; a++)
			{
				String PrmSet = ExcelLib.getCellValue(dataSheetPath, sheetName, a, 2);
				Reporter.log("</br><table><tr><th><b>Permission Set taken : "+PrmSet+ "</b></th></tr></table>", true);
				gotoPermissionSet.gotoFieldAaccebilty();
				if (gotoPermissionSet.verifyPermissionVisibility(PrmSet))
				{
					verifyObjectSettings.validateObjectSettings(decodedPath + "Baseline Data/Baseline Excel_" + PrmSet + ".xls");
					
					int ObjCount = ExcelLib.getRowCountofColumn(dataSheetPath, PrmSet, 0);
					
					for (int b=1; b<ObjCount; b++)
					{
						String Obj = ExcelLib.getCellValue(dataSheetPath, PrmSet, a, 0);
						Reporter.log("</br><table><tr><th><b>Record Type Assignments</b></th></tr></table>", true);
						verifyRecordTypeAssignments.validateRecordTypeAssignments(Obj, decodedPath + "Baseline Data/Baseline Excel_" + PrmSet + ".xls");
						
						Reporter.log("</br><table><tr><th><b>Field Permissions</b></th></tr></table>", true);
						verifyFieldPermissions.validateFieldPermissions(Obj, decodedPath + "Baseline Data/Baseline Excel_" + PrmSet + ".xls");
					}
				} else
				{
					Reporter.log("<table><tr><th><b>ERROR : Permission Set "+PrmSet+" not available in the application!!</b></th></tr></table>", true);
				}
			}
		} else
		{

			Reporter.log("</br><table><tr><th><b>TEST STATUS :</b></th><td> FAILED -- Incorrect Username or Password </td></tr></table>", true);
		}
		Reporter.log("</body></html>", true);
	}
}
