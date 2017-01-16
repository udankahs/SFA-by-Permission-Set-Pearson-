package com.email.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.lib.ExcelLib;

/* Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: QEA CRM
 * Organization		: Cognizant Technology Solutions
 */

public class VerifyFieldPermissions
{
	private WebDriver driver;

	String field = null;
	String srcReadAccess = null;
	String srcWriteAccess = null;

	public VerifyFieldPermissions(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void validateFieldPermissions(String Obj, String baselinepath) {

		System.out.println("baselinepath "+baselinepath);
		String xpath= "//td[@colspan='1']//a[text()='"+Obj+"']";
		System.out.println("xpath : "+xpath);
		if (driver.findElements(By.xpath(xpath)).size() > 0)
		{
			driver.findElement(By.xpath("//td[@colspan='1']//a[text()='"+Obj+"']")).click();
			int numOfFields = ExcelLib.getRowCountofColumn(baselinepath, Obj, 2);
			
			Reporter.log("<table><tr bgcolor='gray'><th><b>STATUS </b></th><th><b> FIELD </b></th><th><b> READ ACCESS SOURCE (Excel) </b></th><th><b> READ ACCESS TARGET (Application) </b></th><th><b> EDIT ACCESS SOURCE (Excel)</b></th><th><b> EDIT ACCESS TARGET (Application) </b></th></tr>", true);

			for (int a = 2; a < numOfFields; a++)
			{
				field = ExcelLib.getCellValue(baselinepath, Obj, a, 2);
				srcReadAccess = ExcelLib.getCellValue(baselinepath, Obj, a, 3);
				srcWriteAccess = ExcelLib.getCellValue(baselinepath, Obj, a, 4);

				if (driver.findElements(By.xpath("//h3[text()='Field Permissions']/../..//td[text()='" + field + "']")).size() > 0)
				{
					boolean ReadAccess = driver.findElement(By.xpath("//h3[text()='Field Permissions']/../..//td[text()='" + field + "']/../td[2]/input")).isSelected();
					boolean writeAccess = driver.findElement(By.xpath("//h3[text()='Field Permissions']/../..//td[text()='" + field + "']/../td[3]/input")).isSelected();

					String tarReadAccess = String.valueOf(ReadAccess);
					String tarWriteAccess = String.valueOf(writeAccess);

					if (srcReadAccess.equalsIgnoreCase(tarReadAccess) && srcWriteAccess.equalsIgnoreCase(tarWriteAccess))
					{
						Reporter.log("<tr><th><b><font color = 'green'> PASS </b></th> <th><b> "+field+" </b></th><th><b>"+srcReadAccess+"</b></th><th><b>"+tarReadAccess+"</b></th><th><b>"+srcWriteAccess+"</b></th><th><b>"+tarWriteAccess+"</b></th></tr>",true);
					} else
					{
						Reporter.log("<tr><th><b><font color = 'red'> FAIL </b></th> <th><b> "+field+" </b></th><th><b>"+srcReadAccess+"</b></th><th><b>"+tarReadAccess+"</b></th><th><b>"+srcWriteAccess+"</b></th><th><b>"+tarWriteAccess+"</b></th></tr>",true);
					}
				} else
				{
					Reporter.log("<tr><th><b><font color = 'red'>FAIL </b></th> <th><b>"+field+"  </b></th><th colspan=\"4\"><b> Field not found in the application!!</b></th></tr>", true);
				}
				Reporter.log("</table>", true);
			}
			driver.findElement(By.xpath("//div[@class='pc_breadcrumbAlign']/a[text()='Object Settings']")).click();
		} else
		{
			Reporter.log("<table><tr><th><b><font color = 'red'>ERROR </b></th> <th><b>Object not found in the application!!</b></th></tr></table>", true);
		}
	}
}
