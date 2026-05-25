package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class CustomersPage extends BasePage {
	// Locators cho phần tìm kiếm
	private final By optyNumberInput = By.xpath("(//input[@name='OptyNumber'])[1]");
	private final By searchButton = By.xpath("(//button[@id='btn_search'])[1]");

	public CustomersPage(WebDriver driver) {
		super(driver);
	}

	@Step("Kiểm tra khách hàng '{0}' có hiển thị trên danh sách hay không")
	public boolean isCustomerDisplayed(String expectedText) {
		By customerLocator = By.xpath("//*[contains(text(), '" + expectedText + "')]");
		try {
			sleep(2);
			return driver.findElements(customerLocator).size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	@Step("Click vào khách hàng có ID hoặc Tên là '{0}'")
	public void clickCustomer(String customerIdentifier) {
		By customerLink = By.xpath("//*[contains(text(), '" + customerIdentifier + "')]");
		clickElement(customerLink);
	}

	@Step("Nhập mã cơ hội (OptyNumber): {0}")
	public void enterOptyNumber(String optyNumber) {
		enterText(optyNumberInput, optyNumber);
	}

	@Step("Click nút tìm kiếm")
	public void clickSearch() {
		clickElement(searchButton);
	}
}
