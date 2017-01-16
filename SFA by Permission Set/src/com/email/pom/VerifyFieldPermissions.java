package com.email.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
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

		if (driver.findElements(By.xpath("//span[@class='pcGhost']//a[text()='" + Obj + "']")).size() > 0)
		{
			driver.findElement(By.xpath("//span[@class='pcGhost']//a[text()='" + Obj + "']")).click();
			int numOfFields = ExcelLib.getRowCountofColumn(baselinepath, Obj, 2);

			for (int a = 2; a < numOfFields; a++)
			{
				field = ExcelLib.getCellValue(baselinepath, Obj, a, 2);
				srcReadAccess = ExcelLib.getCellValue(baselinepath, Obj, a, 3);
				srcWriteAccess = ExcelLib.getCellValue(baselinepath, Obj, a, 4);

				if (driver.findElements(By.xpath("//h3[text()='Record Type Assignments']/../..//td[text()='" + field + "']")).size() > 0)
				{
					boolean ReadAccess = driver.findElement(By.xpath("//h3[text()='Record Type Assignments']/../..//td[text()='" + field + "']/../td[2]/input")).isSelected();
					boolean writeAccess = driver.findElement(By.xpath("//h3[text()='Record Type Assignments']/../..//td[text()='" + field + "']/../td[3]/input")).isSelected();

					String tarReadAccess = String.valueOf(ReadAccess);
					String tarWriteAccess = String.valueOf(writeAccess);

					if (srcReadAccess.equalsIgnoreCase(tarReadAccess) && srcWriteAccess.equalsIgnoreCase(tarWriteAccess))
					{
						System.out.println("PASS : Field Permission matches");
					} else
					{
						System.out.println("FAIL : Field Permission doesnt match");
					}
				} else
				{
					System.out.println("FAIL : Field not found in the application");
				}
			}
			driver.findElement(By.xpath("//div[@class='pc_breadcrumbAlign']/a[text()='Object Settings']")).click();
		} else
		{
			System.out.println("Object not found in the application");
		}
	}
}
