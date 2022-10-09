package pages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import browsers.TakeScreenshot;
import io.qameta.allure.Allure;

public class Homepage {
	WebDriver driver;
	JavascriptExecutor js;
	TakeScreenshot screenshot;
	long currentYear;
	WebDriverWait wait;

	final String screenshotsPath = "src/test/resources/screenshots/";

	public Homepage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		this.screenshot = new TakeScreenshot(driver);
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(20));
	}

	public void getCurrentYear() {
		this.currentYear = (long) this.js.executeScript("return new Date().getFullYear();");
	}

	public void clickOnSpanField(String fieldName) {
		WebElement spanBox = this.driver
				.findElement(By.xpath("//div[@data-calendar2-type='" + fieldName + "']/*/span"));
		this.js.executeScript("arguments[0].click();", spanBox);
	}

	public void fillCityFields(String cityName) {
		this.js.executeScript("document.getElementById('ss').value='" + cityName + "';");
	}

	public WebElement getAutoFillList() {
		return this.driver.findElement(By.xpath("//input[@id='ss']"));
	}

	public void chooseFromAutoFillList(int index) {
		WebElement autoFillList = this.wait.until(
				ExpectedConditions.visibilityOf(this.driver.findElement(By.xpath("//li[@data-i='" + index + "']"))));
		this.js.executeScript("arguments[0].click();", autoFillList);
	}

	public void selectDateFromCalendar(String calendarDate) {
		String dateStr = calendarDate + " " + this.currentYear + " 00:00:00 UTC";
		String validDate = (String) this.js.executeScript("return new Date('" + dateStr + "');");
		String dateISO = validDate.substring(0, validDate.indexOf("T"));
		System.out.println("dateISO: " + dateISO);
		WebElement dateBox = this.driver.findElement(By.xpath("//td[@data-date='" + dateISO + "']"));
		this.js.executeScript("arguments[0].click();", dateBox);
	}

	public void getSearchResult(String cityName) throws IOException {
		String screenshotName = cityName + "_address.jpg";
		this.screenshot.takeScreenshot(screenshotName);
		Allure.addAttachment(screenshotName, screenshotsPath + screenshotName);
		this.js.executeScript("document.getElementsByClassName('sb-searchbox__button ')[0].click();");
	}
}