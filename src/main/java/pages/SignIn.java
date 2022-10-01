package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignIn {
	WebDriver driver;

	public SignIn(WebDriver driver) {
		this.driver = driver;
	}

	public void getSepecificButton(String comparisonTxt) throws InterruptedException {
		Thread.sleep(3000);
		List<WebElement> buttonElements = this.driver.findElements(By.xpath("//button"));
		for (WebElement element : buttonElements) {
			if (element.getText().equals(comparisonTxt)) {
				element.click();
				Thread.sleep(3000);
			}
		}
	}

	public void loginIntoFigma(String email, String password) throws InterruptedException {
		Thread.sleep(3000);
		this.driver.switchTo().frame(this.driver.findElement(By.xpath("//iframe[@title='Auth']")));
		Thread.sleep(3000);
		WebElement emailField = this.driver.findElement(By.id("email"));
		emailField.sendKeys(email);

		WebElement passwordField = this.driver.findElement(By.id("current-password"));
		passwordField.sendKeys(password);

		getSepecificButton("Log in");
	}
}
