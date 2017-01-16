package com.email.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

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
		System.out.println("numOfObj :"+numOfObj);
		
		Reporter.log("<table><tr bgcolor='gray'><th><b>STATUS </th></b> <th><b> OBJECT </th></b><th><b> SOURCE OBJECT PERMISSION(Excel)</b></th><th><b> TARGET OBJECT PERMISSION(Excel)</b></th><th><b> SOURCE TOTAL FIELDS(Excel)</b></th><th><b> TARGET TOTAL FIELDS(Excel)</b></th></tr>",true);

		for(int a=1 ; a<numOfObj ; a++)
		{
			Obj = ExcelLib.getCellValue(baselinepath, "Object Settings", a, 0);
			srcObjPerm = ExcelLib.getCellValue(baselinepath, "Object Settings", a, 1);
			srcTotalFields = ExcelLib.getCellValue(baselinepath, "Object Settings", a, 2);
			
			System.out.println("Obj : "+Obj+"srcObjPerm :"+srcObjPerm+" srcTotalFields :"+srcTotalFields);
			
			if (driver.findElements(By.xpath("//td[@colspan='1']//a[text()='"+Obj+"']")).size() > 0) 
			{
				tarObjPerm = driver.findElement(By.xpath("//td[@colspan='1']//a[text()='"+Obj+"']/../../../td[2]")).getText();
				tarTotalFields = driver.findElement(By.xpath("//td[@colspan='1']//a[text()='"+Obj+"']/../../../td[3]")).getText();
				
				if(srcObjPerm.equalsIgnoreCase(tarObjPerm) && srcTotalFields.equalsIgnoreCase(tarTotalFields))
				{
					Reporter.log("<tr><th><b><font color = 'green'> PASS  </th></b> <th><b> "+Obj+" </th></b><th><b> "+srcObjPerm+"</b></th><th><b> "+tarObjPerm+"</b></th><th><b> "+srcTotalFields+"</b></th><th><b> "+tarTotalFields+"</b></th></tr>",true);
				}
				else
				{
					Reporter.log("<tr><th><b><font color = 'red'> FAIL  </th></b> <th><b> "+Obj+" </th></b><th><b> "+srcObjPerm+"</b></th><th><b> "+tarObjPerm+"</b></th><th><b> "+srcTotalFields+"</b></th><th><b> "+tarTotalFields+"</b></th></tr>",true);
				}
			} 
			else
			{
				Reporter.log("<tr><th><b> <font color = 'red'> FAIL </b></th> <th><b> "+Obj+"  </b></th><th colspan=\"4\"><b> Object not found in the application </b></th></tr>",true);
			}
		}
		Reporter.log("</table>",true);
	}
}
