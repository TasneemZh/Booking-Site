package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		List<WebElement> websiteBtns = this.driver.findElements(By.xpath("//button"));
		for (WebElement btn : websiteBtns) {
			if (btn.getText().equals(fieldName)) {
				this.js.executeScript("arguments[0].click();", btn);
			}
		}
//		WebElement spanBox = this.driver
//				.findElement(By.xpath("//div[@data-calendar2-type='" + fieldName + "']/*/span"));
//		this.js.executeScript("arguments[0].click();", spanBox);
	}

	public void fillCityFields(String cityName) throws InterruptedException {
		this.js.executeScript("document.getElementsByName('ss')[0].value='" + cityName + "';");
		Thread.sleep(3000);
		this.js.executeScript("document.body.click();");
	}

	public WebElement getAutoFillList() {
		return this.driver.findElement(By.xpath("//input[@name='ss']"));
	}

	public void chooseFromAutoFillList(int index) throws IOException {
//		WebElement autoFillList = this.wait.until(
//				ExpectedConditions.visibilityOf(this.driver.findElement(By.xpath("//ul[@aria-label='List of suggested destinations ']/li[1]"))));

//		WebElement autoFillList = this.driver.findElement(By.xpath("//li[@data-i='0']"));
//		this.js.executeScript("arguments[0].click();", autoFillList);
		
		List<WebElement> autoFillList = this.driver.findElements(By.xpath("//div[@tabindex='-1']"));
		this.js.executeScript("arguments[0].click();", autoFillList.get(index));
	}

	public void selectDateFromCalendar(String calendarDate) {
		String dateStr = calendarDate + " " + this.currentYear + " 00:00:00 UTC";
		String validDate = (String) this.js.executeScript("return new Date('" + dateStr + "');");
		String dateISO = validDate.substring(0, validDate.indexOf("T"));
		System.out.println("dateISO: " + dateISO);
		WebElement dateBox = this.driver.findElement(By.xpath("//span[@data-date='" + dateISO + "']"));
		this.js.executeScript("arguments[0].click();", dateBox);
	}

	public void getSearchResult(String cityName) throws IOException, InterruptedException {
		String screenshotName = cityName + "_address.jpg";
		this.screenshot.takeScreenshot(screenshotName);
		Allure.addAttachment(screenshotName, screenshotsPath + screenshotName);

		List<WebElement> submitBtns = this.driver.findElements(By.xpath("//button[@type='submit']/span"));
		for (WebElement btn : submitBtns) {
			System.out.println("Button " + btn.getText());
			if (btn.getText().equals("Search")) {
				System.out.println("Correct result " + btn);
				this.js.executeScript("arguments[0].click();", btn);
			}
		}
	}
}