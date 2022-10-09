package browsers;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

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

	public static WebDriver setFirefoxProfile() {
		String downloadFilepath = "downloads";
		File file = new File(downloadFilepath);
		FirefoxProfile profile = new FirefoxProfile();
		
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", file.getAbsolutePath());
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/java-archive, application/x-msexcel,application/excel,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.microsoft.portable-executable");

		FirefoxOptions option = new FirefoxOptions();
		if (isHeadless) {
			option.addArguments("-headless");
		}
		option.setProfile(profile);

		WebDriver driver = new FirefoxDriver(option);
		return driver;
	}

	public WebDriver createDriver(String browser) {
		WebDriver driver;
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver.exe");
			driver = OpenBrowser.setChromeOptions();
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "./src/main/resources/drivers/geckodriver.exe");
			driver = OpenBrowser.setFirefoxProfile();
		} else {
			return null;
		}
		return driver;
	}
}