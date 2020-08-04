package com.autodeskcrm.orgtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.autodeskcrm.genericutils.ExcelLib;
import com.autodeskcrm.genericutils.FileLib;
import com.autodeskcrm.genericutils.WebDriverUtils;

/**
 * 
 * @author Sanjana
 *
 */

public class DeleteOrganisationTest {
	
	@Test
	public void deleteORgtest() throws Throwable {
		
		WebDriverUtils wLib = new WebDriverUtils();
        FileLib fLib = new FileLib();
        ExcelLib excelLib = new ExcelLib();
		

		/* read data from property File */
		String USERNAME = fLib.getPropertyKeyValue("username");
		String PASSWORD = fLib.getPropertyKeyValue("password");
		String URL = fLib.getPropertyKeyValue("url");
		String BROWSER = fLib.getPropertyKeyValue("browser");
		
		/* read test script specific data*/
		String search_type = excelLib.getExcelData("org", 7, 2);
		String org_name=excelLib.getExcelData("org", 10, 2);
		String orgName = excelLib.getExcelData("org", 1, 2)+ "_"+ wLib.getRamDomNum();
		String org_Type = excelLib.getExcelData("org", 1, 3);
		String org_industry = excelLib.getExcelData("org", 1, 4);
		
		
		/*step 1 : launch the browser*/
		WebDriver driver = null;
		  
		 if(BROWSER.equals("chrome")) {
		   driver= new ChromeDriver();
		 } else if(BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		 }else if(BROWSER.equals("ie")) {
				driver = new InternetExplorerDriver();
	     }else {
	    	 driver = new FirefoxDriver();
	     }
		 
		wLib.waitForPagetoLoad(driver);
		driver.get(URL);
		
		/*step 2 : login*/
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();
		
		wLib.waitForPagetoLoad(driver);
		
		/*step 3 : navigate to Org page*/
		driver.findElement(By.linkText("Organizations")).click();
		
		/*step 4 : create Org*/
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		driver.findElement(By.name("accountname")).sendKeys(orgName);
		
		WebElement  swb1 = driver.findElement(By.name("accounttype"));
	    wLib.select(swb1, org_Type);
				
		WebElement  swb2 = driver.findElement(By.name("industry"));
		wLib.select(swb2, org_industry);
				
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		driver.findElement(By.xpath("//a[.='Organizations']")).click();
		
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
		
		driver.findElement(By.linkText("Sign Out")).click();
		
	}
	
}
