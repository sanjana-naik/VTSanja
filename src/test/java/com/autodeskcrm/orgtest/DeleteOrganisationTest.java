package com.autodeskcrm.orgtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.autodeskcrm.genericutils.BaseClass;
import com.autodeskcrm.genericutils.ExcelLib;
import com.autodeskcrm.genericutils.FileLib;
import com.autodeskcrm.genericutils.WebDriverUtils;

/**
 * 
 * @author Sanjana
 *
 */

public class DeleteOrganisationTest extends BaseClass{
	
	@Test
	public void deleteORgtest() throws Throwable {
				
		/* read test script specific data*/
		String search_type = excelLib.getExcelData("org", 7, 2);
		String org_name=excelLib.getExcelData("org", 10, 2);
		String orgName = excelLib.getExcelData("org", 1, 2)+ "_"+ wLib.getRamDomNum();
		String org_Type = excelLib.getExcelData("org", 1, 3);
		String org_industry = excelLib.getExcelData("org", 1, 4);
		
		/*step 3 : navigate to Org page*/
		driver.findElement(By.linkText("Organizations")).click();
		
		/*step 4 : create Org*/
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(orgName);
		
		WebElement  swb1 = driver.findElement(By.name("accounttype"));
	    wLib.select(swb1, org_Type);
				
		WebElement  swb2 = driver.findElement(By.name("industry"));
		wLib.select(swb2, org_industry);
				
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		wLib.waitForPagetoLoad(driver);
		driver.findElement(By.xpath("(//a[.='Organizations'])[2]")).click();
		
		/*step 5: delete the org*/		
		driver.findElement(By.xpath("//input[@id=12]")).click();
		
		driver.findElement(By.xpath("(//input[@class='crmbutton small delete'])[1]")).click();
		
		wLib.alertOk(driver);
		
		/*step 6: search for deleted org*/
		driver.findElement(By.className("txtBox")).sendKeys(search_type);
		
		WebElement swd = driver.findElement(By.className("txtBox"));
		wLib.select(swd, 1);
		
		driver.findElement(By.name("submit")).click();
		
		/*Step 7: Verify whether org is present or not*/
		String OrgNotfound = driver.findElement(By.className("genHeaderSmall")).getText();
		Assert.assertEquals(OrgNotfound, search_type);
		
		
	}
	
}
