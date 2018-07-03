package webtable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTablesHomeWork {

	Map<Integer, String> map = new HashMap();

	// String url =
	// "file:///Users/begimaikanybek/eclipse-workspace/selenium-maven-automation/src/test/java/webtable/webtable.html";

	String url = "file:///E:/SDET/Eclipse_Workspace/selenium-maven-automation/src/test/java/webTables/webtable.html";
	static WebDriver driver;

	@BeforeClass
	public void setUp() {

		WebDriverManager.chromedriver().setup(); // BoniGarcia

		driver = new ChromeDriver(); // Opens Chrome Browser
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize(); // Makes the browser window fullscreen

	}

	@Test
	public void applicantsData() throws InterruptedException {
		driver.get(
				"https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8");

		Select select = new Select(driver.findElement(By.id("recPerPage")));
		select.selectByVisibleText("100");

		int count = 0;

		do {
			if (count != 0) {
				driver.findElement(By.xpath("//span[@id='showNext']/a")).click();

			}
			count++;
			loadMap("reportTab");
		} while (driver.findElement(By.xpath("//span[@id='showNext']/a")).getAttribute("class").equals("nxtArrow"));

		// a.nxtArrow"))).

		System.out.println(map.size());
	}

	public void loadMap(String id) throws InterruptedException {
		Thread.sleep(2000);
		int rowsCount = driver.findElements(By.xpath("//table[@id='" + id + "']/tbody/tr")).size();
		int colCount = driver.findElements(By.xpath("//table[@id='" + id + "']/thead/tr/th")).size();

		for (int i = 1; i <= rowsCount; i++) {
			String webString = new String();

			for (int j = 1; j <= colCount; j++) {

				String something = driver
						.findElement(By.xpath("//table[@id='" + id + "']/tbody/tr[" + i + "]/td[" + j + "]")).getText()
						+ "  "; //// table[@id='reportTab' ]/tbody/tr[]/td
				webString += something;
			}

			if (webString.isEmpty())
				continue;

			String[] temp = convertString(webString);
			map.put(Integer.valueOf(temp[0]), temp[1]);

		}
	}

	public String[] convertString(String temp) {
		String[] strings = temp.split("  "); // 232 daskm asd dasdds dsada
		String[] tempArr = new String[2]; // 0=232 1=dasd-asdasd-dasasd-adasd-
		tempArr[1] = new String();

		for (int i = 0; i < strings.length; i++) {
			if (i == 0) {
				tempArr[0] = strings[i];
			} else {
				tempArr[1] += "-" + strings[i];
			}
		}
		tempArr[1] = tempArr[1].substring(1);
		return tempArr;
	}

	@AfterClass
	public void tearDown() {
		driver.close();
	}

}
