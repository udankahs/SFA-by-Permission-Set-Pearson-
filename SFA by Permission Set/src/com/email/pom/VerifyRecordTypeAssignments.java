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

public class VerifyRecordTypeAssignments {
	private WebDriver driver;

	String recordType = null;
	String recordTypeAssigned = null;


	public VerifyRecordTypeAssignments(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void validateRecordTypeAssignments (String Obj, String baselinepath) {
		
		System.out.println("baselinepath "+baselinepath);
		String xpath= "//td[@colspan='1']//a[text()='"+Obj+"']";
		System.out.println("xpath : "+xpath);
		if (driver.findElements(By.xpath(xpath)).size() > 0)
		{	
			driver.findElement(By.xpath("//td[@colspan='1']//a[text()='"+Obj+"']")).click();
			if (driver.findElements(By.xpath("//h3[text()='Record Type Assignments']")).size() > 0) {
			
			Reporter.log("<table><tr bgcolor='gray'><th ><b>STATUS </b></th><th><b> RECORD TYPE </b></th><th><b> SOURCE ASSIGNMENT (Excel) </b></th></th><th><b> TARGET ASSIGNMENT (Application) </b></th></tr>",true);	
			
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
					Reporter.log("<tr><th ><b><font color = 'green'> PASS </b></th><th><b> "+recordType+" </b></th><th><b> "+recordTypeAssigned+" </b></th></th><th><b> "+tarRecordTypeAssined+" </b></th></tr>",true);
				}
				else
				{
					Reporter.log("<tr><th ><b><font color = 'red'> FAIL </b></th><th><b> "+recordType+" </b></th><th><b> "+recordTypeAssigned+" </b></th></th><th><b> "+tarRecordTypeAssined+" </b></th></tr>",true);
				}
			}
			else
			{
				Reporter.log("<tr><th><b><font color = 'red'>FAIL </b></th><th><b>"+recordType+" </b></th> <th colspan=\"2\"><b> Record Type not found!! </b></th></tr>",true);
			}
			}
			Reporter.log("</table>",true);
		} 
		else
		{
			Reporter.log("<table><tr><th><b><font color = 'red'>ERROR </b></th><th><b>Record Type Assignments section not found in the system!! </b></th></tr></table>",true);
		}
			driver.findElement(By.xpath("//div[@class='pc_breadcrumbAlign']/a[text()='Object Settings']")).click();
		}
		else
		{
			Reporter.log("<table><tr><th><b><font color = 'red'>ERROR </b></th><th><b>Object not found in the application!! </b></th></tr></table>",true);
		}
	}
}
