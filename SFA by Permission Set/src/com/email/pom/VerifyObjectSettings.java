package com.email.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.lib.ExcelLib;

/* Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: QEA CRM
 * Organization		: Cognizant Technology Solutions
 */

public class VerifyObjectSettings {
	private WebDriver driver;

	@FindBy(id = "setupLink")
	private WebElement Setup;

	@FindBy(id = "userNavLabel")
	private WebElement UserName;

	@FindBy(xpath = "//div[@id='userNav-menuItems']/a[contains (text(), 'Setup')]")
	private WebElement Setup2;

	@FindBy(id = "globalHeaderNameMink")
	private WebElement communityUserNav;
	
	@FindBy(xpath = "//a[contains (text(), 'Setup')]")
	private WebElement Setup3;
	
	@FindBy(id = "Security_icon")
	private WebElement Security;

	@FindBy(id = "FieldAccessibility_font")
	private WebElement FieldAccessibility;

	@FindBy(xpath = "//*[@id='bodyCell']//a[text()='View by Record Types']")
	private WebElement ViewByRecordTypes;

	@FindBy(id = "zSelect")
	private WebElement RecordType;

	String Obj = null;
	String tarObjPerm = null;
	String srcObjPerm = null;
	String srcTotalFields = null;
	String tarTotalFields = null;
	String SourceState = null;
	String Result, Color = null;
	String partialXpath = "//th";

	int baselineProfileIndex = 0;
	int noOfColumnsInBaselineSheet = 0;
	int passCount, failCount;

	public VerifyObjectSettings(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void validateObjectSettings (String baselinepath) {
		
		int numOfObj = ExcelLib.getRowCountofColumn(baselinepath, "Object Settings", 0);

		for(int a=1 ; a<numOfObj ; a++)
		{
			Obj = ExcelLib.getCellValue(baselinepath, "Object Settings", a, 0);
			srcObjPerm = ExcelLib.getCellValue(baselinepath, "Object Settings", a, 1);
			srcTotalFields = ExcelLib.getCellValue(baselinepath, "Object Settings", a, 2);
			
			System.out.println("srcObjPerm "+srcObjPerm+" && srcTotalFields : "+srcTotalFields);
			
			if (driver.findElements(By.xpath("//span[@class='pcGhost']//a[text()='"+Obj+"']")).size() > 0) {
				tarObjPerm = driver.findElement(By.xpath("//span[@class='pcGhost']//a[text()='"+Obj+"']/../../../td[2]")).getText();
				tarTotalFields = driver.findElement(By.xpath("//span[@class='pcGhost']//a[text()='"+Obj+"']/../../../td[3]")).getText();
				
				System.out.println("tarObjPerm "+tarObjPerm+" && tarTotalFields : "+tarTotalFields);
				
				if(srcObjPerm.equalsIgnoreCase(tarObjPerm) && srcTotalFields.equalsIgnoreCase(tarTotalFields))
				{
					System.out.println("PASS : Object Name , Object Permissions, Total Fields matches!!");
				}
				else
				{
					System.out.println("FAIL : Object Name , Object Permissions, Total Fields doesn't match!!");
				}
				
			} 
			else
			{
				System.out.println("FAIL : Field not found in the application!!");
			}
		}
	}
}
