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

	public void fillCityFields(String cityName) throws InterruptedException {
		this.js.executeScript("document.getElementsByName('ss')[0].value='" + cityName + "';");
//		Thread.sleep(3000);
//		this.js.executeScript("document.body.click();");
	}

	public WebElement getAutoFillList() {
		return this.driver.findElement(By.xpath("//input[@name='ss']"));
	}

	public void chooseFromAutoFillList(int index) throws IOException {
		List<WebElement> autoFillList = this.driver.findElements(By.xpath("//div[@tabindex='-1']"));
		this.js.executeScript("arguments[0].click();", autoFillList.get(index));
	}

	public void setTravelDate(String placeholderField, String date) {
		this.js.executeScript("document.querySelector('[data-placeholder=\"" + placeholderField
				+ "\"]').textContent = \'" + date + "\';");
	}

	public void getSearchResult(String cityName) throws IOException, InterruptedException {
		String screenshotName = cityName + "_address.jpg";
		this.screenshot.takeScreenshot(screenshotName);
		Allure.addAttachment(screenshotName, screenshotsPath + screenshotName);

		List<WebElement> submitBtns = this.driver.findElements(By.xpath("//button[@type='submit']/span"));
		for (WebElement btn : submitBtns) {
			System.out.println("Button ");
			if (btn.getText().contains("Search")) {
				System.out.println("Correct result " + btn);
				this.js.executeScript("arguments[0].click();", btn);
			}
		}
	}
}