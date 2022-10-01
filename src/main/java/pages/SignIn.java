package pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import browsers.TakeScreenshot;

public class SignIn {
	WebDriver driver;
	TakeScreenshot screenshot;

	public SignIn(WebDriver driver) {
		this.driver = driver;
		this.screenshot = new TakeScreenshot(driver);
	}

	public void getSepecificButton(String comparisonTxt) throws InterruptedException, IOException {
		Thread.sleep(3000);
		List<WebElement> buttonElements = this.driver.findElements(By.xpath("//button"));
		for (WebElement element : buttonElements) {
			if (element.getText().equals(comparisonTxt)) {
				element.click();
				Thread.sleep(3000);
				this.screenshot.takeScreenshot("after_clicking_login.jpg");
			}
		}
	}

	public void loginIntoFigma(String email, String password) throws InterruptedException, IOException {
		Thread.sleep(3000);
		this.screenshot.takeScreenshot("before_entering_credentials.jpg");
		WebElement iframe = this.driver.findElement(By.xpath("//iframe[@title='Auth']"));
		this.driver.switchTo().frame(iframe);
		Thread.sleep(3000);
		WebElement emailField = this.driver.findElement(By.id("email"));
		emailField.sendKeys(email);
		this.screenshot.takeScreenshot("after_entering_email.jpg");

		WebElement passwordField = this.driver.findElement(By.id("current-password"));
		passwordField.sendKeys(password);
		this.screenshot.takeScreenshot("after_entering_password.jpg");

		getSepecificButton("Log in");
		Thread.sleep(3000);
	}
}
