package page;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

	private final By createBusinessButton = By.xpath("//*[contains(text(), 'お客様情報入力')]");
	private final By createBusinessForm = By.xpath("//button[@class='btn btn-blue-accent-1 px-3 ml-0 mb-3']");

	public DashboardPage(WebDriver driver) {
		super(driver);
	}

	@Step("Click nút 'お客様情報入力' từ màn hình Dashboard")
	public void clickCreateBusiness() {
		clickElement(createBusinessButton);
	}

	@Step("Click nút để mở form")
	public void clickCreateBusinessForm() {
		clickElement(createBusinessForm);
	}

	@Step("Tìm khách hàng {0} trên Dashboard và click nút '入力'")
	public void clickInputForCustomer(String customerName) {
		// Tìm element chứa tên khách hàng, ngược lên thẻ <tr> (row) và tìm nút/link
		// chứa chữ '入力'
		By inputBtn = By.xpath("//tr[contains(., '" + customerName + "')]//*[contains(text(), '入力')]");
		try {
			clickElement(inputBtn);
		} catch (Exception e) {
			// Backup locator nếu danh sách không dùng thẻ <tr> mà dùng <div> dạng card/row
			By backupBtn = By.xpath("//*[contains(text(), '" + customerName
					+ "')]/ancestor::div[contains(@class, 'row') or contains(@class, 'd-flex')]//*[contains(text(), '入力')]");
			clickElement(backupBtn);
		}
	}

	@Step("Click nút 'スタッフ' để vào màn hình Edit")
	public void clickStaffEditButton() {
		By staffBtn = By.xpath("//button[contains(text(), 'スタッフ') or contains(text(), 'スタッフ入力')]");
		clickElement(staffBtn);
	}
}
