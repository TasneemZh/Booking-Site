package browsers;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OpenBrowser {
	final static boolean isHeadless = true;

	public static WebDriver setChromeOptions() {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--disable-infobars");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--allow-running-insecure-content");

		if (isHeadless) {
			options.addArguments("--headless");
			options.addArguments("--window-size=1920,1080");
		} else {
			options.addArguments("--start-maximized");
		}

		WebDriver driver = new ChromeDriver(options);
		return driver;
	}

	public WebDriver createDriver(String browser) {
		WebDriver driver;
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver.exe");
			driver = OpenBrowser.setChromeOptions();
		} else {
			return null;
		}
		return driver;
	}
}