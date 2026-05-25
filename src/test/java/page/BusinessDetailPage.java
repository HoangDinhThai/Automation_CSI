package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BusinessDetailPage extends BasePage {

	// Locator cho nút 新規追加 (Thêm mới)
	private final By addNewButton = By.xpath("(//button[@id='btn_add_task'])[1]");

	// Locator cho tab/nút 支店・グループ
	private final By branchGroupTab = By.xpath("//*[contains(text(), '支店・グループ')]");

	public BusinessDetailPage(WebDriver driver) {
		super(driver);
	}

	@Step("Click nút '新規追加' (Add New)")
	public void clickAddNew() {
		clickElement(addNewButton);
	}

	@Step("Click tab '支店・グループ'")
	public void clickBranchGroupTab() {
		clickElement(branchGroupTab);
	}
}
