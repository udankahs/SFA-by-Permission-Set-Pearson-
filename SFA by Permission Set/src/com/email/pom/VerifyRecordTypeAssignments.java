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

public class VerifyRecordTypeAssignments {
	private WebDriver driver;

	String recordType = null;
	String recordTypeAssigned = null;


	public VerifyRecordTypeAssignments(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void validateRecordTypeAssignments (String Obj, String baselinepath) {
		
		if (driver.findElements(By.xpath("//span[@class='pcGhost']//a[text()='"+Obj+"']")).size() > 0)
		{	
			driver.findElement(By.xpath("//span[@class='pcGhost']//a[text()='"+Obj+"']")).click();
			if (driver.findElements(By.xpath("//h3[text()='Record Type Assignments']")).size() > 0) {
			
			int numOfRecordType = ExcelLib.getRowCountofColumn(baselinepath, Obj, 0);

			for(int a=2 ; a<numOfRecordType ; a++)
			{
				recordType = ExcelLib.getCellValue(baselinepath, Obj, a, 0);
				recordTypeAssigned = ExcelLib.getCellValue(baselinepath, Obj, a, 1);
			
			if(driver.findElements(By.xpath("//h3[text()='Record Type Assignments']/../..//td[text()='"+recordType+"']")).size() > 0)
			{
				boolean checked = driver.findElement(By.xpath("//h3[text()='Record Type Assignments']/../..//td[text()='"+recordType+"']/../td[2]/input")).isSelected();
				String tarRecordTypeAssined = String.valueOf(checked);
				if (recordTypeAssigned.equalsIgnoreCase(tarRecordTypeAssined))
				{
					System.out.println("PASS : Record Type Assigned matches");
				}
				else
				{
					System.out.println("FAIL : Record Type Assigned doesnt match");
				}
			}
			else
			{
				System.out.println("FAIL : Record type not found in the application");
			}
			}
		} 
		else
		{
			System.out.println("Record Type Assignments section not found in the system");
		}
			driver.findElement(By.xpath("//div[@class='pc_breadcrumbAlign']/a[text()='Object Settings']")).click();
		}
		else
		{
			System.out.println("Object not found in the application");
		}
	}
}
