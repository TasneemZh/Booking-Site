package booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvException;

import browsers.OpenBrowser;
import files.ManageCsv;
import pages.Homepage;
import pages.SearchResult;

public class BookingTest {
	WebDriver driver;
	OpenBrowser browserType;
	Homepage homepage;
	SearchResult searchResult;
	ManageCsv manageCsv;
	Map<String, String> map;
	List<Map<String, String>> list;
	String browser;

	final String filePath = "src/test/resources/test-data.csv";
	final String filePathResult = "src/test/resources/test-data-result.csv";
	final String pageUrl = "https://www.booking.com/";

	@BeforeSuite
	public void handleFiles() {
		this.list = new ArrayList<Map<String, String>>();
		this.manageCsv = new ManageCsv();
	}

	@BeforeTest
	@Parameters({ "browser" })
	public void openSite(String browser) {
		this.browser = browser;
	}

	@Test(dataProvider = "csvFile")
	public void testSearch(String cityName, String checkInDate, String checkOutDate)
			throws InterruptedException, IOException {
		this.browserType = new OpenBrowser();
		this.driver = this.browserType.createDriver(this.browser);
		this.driver.manage().window().maximize();
		this.driver.get(this.pageUrl);
		this.map = new HashMap<String, String>();

		this.homepage = new Homepage(this.driver);
		Thread.sleep(30000);

		this.homepage.setTravelDate("date-display-field-start", checkInDate);
		Thread.sleep(3000);

		this.homepage.setTravelDate("date-display-field-end", checkOutDate);
		Thread.sleep(3000);

		this.homepage.fillCityFields(cityName);
		Thread.sleep(3000);

		WebElement autoFillList = this.homepage.getAutoFillList();
		Thread.sleep(30000);

		Actions actions = new Actions(driver);
		actions.click(autoFillList);
		actions.sendKeys("\b");
		actions.build().perform();
		Thread.sleep(3000);

		this.homepage.chooseFromAutoFillList(0);
		Thread.sleep(10000);

		this.homepage.getSearchResult(cityName);
		Thread.sleep(3000);

		this.searchResult = new SearchResult(this.driver);
		Thread.sleep(10000);

		this.searchResult.clickOnPage();
		Thread.sleep(3000);

		this.map = this.searchResult.getHotelInfo(0, cityName);
		this.map.put("City", cityName);
		this.map.put("Check-in", checkInDate);
		this.map.put("Check-out", checkOutDate);
		this.list.add(this.map);

		this.manageCsv.closeReadFile();
		this.driver.close();

		Assert.assertTrue(true);
	}

	@DataProvider
	public Object[][] csvFile() throws IOException, CsvException {
		int rowLine = 0;
		Object[][] bookingInput = new Object[3][3];

		int totalRows = this.manageCsv.readFile(this.filePath);

		while (rowLine < totalRows) {
			String[] rowData = this.manageCsv.getRowData(rowLine);
			bookingInput[rowLine][0] = rowData[0];
			bookingInput[rowLine][1] = rowData[1];
			bookingInput[rowLine][2] = rowData[2];
			rowLine++;
		}
		return bookingInput;
	}

	@Test
	public void writeResultsToCsv() throws Exception {
		this.manageCsv.writeToCsv(this.filePathResult, this.list);
		Assert.assertTrue(true);
	}
}
