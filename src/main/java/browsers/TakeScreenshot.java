package browsers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshot {
	WebDriver driver;
	
	final String screenshotsPath = "src/test/resources/screenshots/";

	public TakeScreenshot(WebDriver driver) {
		this.driver = driver;
	}

	public void takeScreenshot(String screenshotName) throws IOException {
		TakesScreenshot screenshot = ((TakesScreenshot) this.driver);

		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(this.screenshotsPath + screenshotName);

		FileUtils.copyFile(srcFile, destFile);
	}
}
