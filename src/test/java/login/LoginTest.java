package login;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import browsers.OpenBrowser;
import pages.SignIn;

public class LoginTest {
	WebDriver driver;
	OpenBrowser browserType;
	final String expEmail = "healthie.temp.email@gmail.com";
	String userEmail;
	String userPassword;

	public LoginTest() {
		this.browserType = new OpenBrowser();
	}

	@Test
	@Parameters({ "browser" })
	public void testLogin(String browser) throws IOException {
		this.driver = this.browserType.createDriver(browser);
//		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		this.driver.get("https://www.figma.com");

		FileReader readFile = new FileReader("credentials.properties");
		Properties properties = new Properties();
		properties.load(readFile);
		this.userEmail = properties.getProperty("email");
		this.userPassword  = properties.getProperty("password");
		Assert.assertEquals(this.userEmail, expEmail);
	}

	@AfterSuite
	public void shutDown() throws InterruptedException, IOException {
		SignIn signIn = new SignIn(this.driver);
		signIn.getSepecificButton("Log in");
		signIn.loginIntoFigma(this.userEmail, this.userPassword);
		this.driver.close();
		this.driver.quit();
	}
}
