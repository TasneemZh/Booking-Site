package pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import browsers.TakeScreenshot;
import io.qameta.allure.Allure;

public class SearchResult {
	WebDriver driver;
	JavascriptExecutor js;
	TakeScreenshot screenshot;
	String cityName;
	HashMap<String, String> map;

	final String screenshotsPath = "src/test/resources/screenshots/";

	public SearchResult(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		this.screenshot = new TakeScreenshot(driver);
		this.map = new HashMap<String, String>();
	}

	public HashMap<String, String> getHotelInfo(int resultNumber, String cityName) throws IOException {
		this.map.clear();

		List<WebElement> hotelNames = this.driver.findElements(By.xpath("//a[@data-testid='title-link']/div"));
		String hotelName = hotelNames.get(resultNumber).getText();
		this.map.put("Hotel name", hotelName);

		List<WebElement> hotelReviews = this.driver
				.findElements(By.xpath("//div[@data-testid='review-score']/div[2]/div[1]"));
		String hotelReview = hotelReviews.get(resultNumber).getText();
		this.map.put("Hotel review", hotelReview);

		String hotelUrl = (String) this.js.executeScript("return document.URL");
		this.map.put("Booking url", hotelUrl);

		System.out
				.println("Hotel Name: " + hotelName + ", Hotel Review: " + hotelReview + ", and hotelUrl: " + hotelUrl);

		String screenshotName = cityName + "_search_result.jpg";
		this.screenshot.takeScreenshot(screenshotName);
		Allure.addAttachment(screenshotName, screenshotsPath + screenshotName);

		return map;
	}
}
