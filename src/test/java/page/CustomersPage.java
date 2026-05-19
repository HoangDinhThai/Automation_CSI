package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomersPage extends BasePage {

	public CustomersPage(WebDriver driver) {
		super(driver);
	}

	@Step("Kiểm tra khách hàng '{0}' có hiển thị trên danh sách hay không")
	public boolean isCustomerDisplayed(String expectedText) {
		// Tìm element có chứa text mong đợi (VD: Họ và tên, hoặc số điện thoại, hoặc
		// Kana)
		By customerLocator = By.xpath("//*[contains(text(), '" + expectedText + "')]");
		try {
			// Có thể dùng WebDriverWait nếu mạng chậm
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
}
