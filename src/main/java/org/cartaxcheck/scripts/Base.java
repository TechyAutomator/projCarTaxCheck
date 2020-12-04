package org.cartaxcheck.scripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * Initialising Selenium WebDriver
 */
public class Base extends DriverActions
{
	protected WebDriver driver;
	
	public Base(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}

	public <T> T getPage(Class<T> classs)
	{
		return PageFactory.initElements(driver, classs);
	}

	public void closeDriver()
	{
		driver.close();
	}
}
