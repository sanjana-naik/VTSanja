package com.autodeskcrm.contactTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.autodeskcrm.genericutils.BaseClass;

/**
 * 
 * @author Sanjana
 *
 */

public class DeleteContactWithOrgTest extends BaseClass{
	
	public void deleteConTest() throws Throwable
	{
		/* read test script specific data*/
		String orgName = excelLib.getExcelData("contact", 1, 2)+ "_"+ wLib.getRamDomNum();
		//String org_Type = excelLib.getExcelData("contact", 1, 3);
		//String org_industry = excelLib.getExcelData("contact", 1, 4);
		String contactName = excelLib.getExcelData("contact", 1, 5);
		String search_for= excelLib.getExcelData("contact", 4, 2);
		String text="Contact Id";
		String expCon=excelLib.getExcelData("contact", 7, 2);
		
		/*step 1 : navigate to Contact page*/
		driver.findElement(By.linkText("Contacts")).click();
		
		/*step 2 : navigate to create new Contact page*/
		driver.findElement(By.xpath("//img[@alt='Create Contact...']")).click();
		
		/*step 3 : creat new Contact page*/
		driver.findElement(By.name("lastname")).sendKeys(contactName);
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();
		
		   //open new tab
	      wLib.switchToNewTab(driver, "specific_contact_account_address");
		
		driver.findElement(By.name("search_text")).sendKeys(orgName);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.linkText(orgName)).click();
		
		//come back to parent Window
		wLib.switchToNewTab(driver, "Administrator - Contacts");
		
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		driver.findElement(By.className("hdrLink")).click();
		
		/*Step 4: delete an contact*/
		driver.findElement(By.id("28")).click();
		driver.findElement(By.xpath("(//a[.='del'])[7]")).click();
		
		wLib.alertOk(driver);
		
		/*Step 5: verify contact is deleted*/
		driver.findElement(By.name("search_text")).sendKeys(search_for);
		
		WebElement search_field = driver.findElement(By.name("search_field"));
		search_field.click();
		wLib.select(search_field, text);
		
		driver.findElement(By.name("submit")).click();
		
		/*Step 6: Verify contact is delete*/
		String AcCon_text = driver.findElement(By.className("genHeaderSmall")).getText();
		Assert.assertEquals(AcCon_text.contains(expCon), true);
		
	}

}
