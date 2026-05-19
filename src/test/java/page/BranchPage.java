package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BranchPage extends BasePage {

	// Locator cho menu 支店・グループ ở ngoài Dashboard
	private final By branchMenu = By.xpath("//*[contains(text(), '支店・グループ')]");

	// Locator cho nút 新規登録 (New Registration)
	private final By addNewRegistrationBtn = By.xpath("//button[contains(text(), '新規登録') or contains(., '新規登録')]");

	// Locators cho form tạo mới Branch (đã lấy được Xpath)
	private final By branchNameInput = By.id("name");
	private final By emailInput = By.id("email");
	private final By branchCodeInput = By.id("branch_cd");
	private final By phoneNumberInput = By.id("phone_number");
	private final By addressInput = By.id("address");
	private final By typeSelect = By.id("type");

	// Nút Lưu và Hủy
	private final By saveButton = By.id("btn_submit");
	private final By cancelButton = By.xpath("//button[contains(text(), 'キャンセル')]");

	// Nút 編集 (Edit) của bản ghi đầu tiên
    private final By editFirstButton = By.xpath("//*[@id=\"datatable\"]/tbody/tr[1]//button[contains(., '編集')]");

	// Nút 編集 (Edit)
	private final By editButton = By.xpath(
			"(//button[contains(text(), '編集')] | //a[contains(text(), '編集')] | //span[contains(text(), '編集')])[1]");

	public BranchPage(WebDriver driver) {
		super(driver);
	}

	@Step("Click menu '支店・グループ' để vào màn Branchs")
	public void clickBranchMenu() {
		clickElement(branchMenu);
	}

    @Step("Click nút '編集' (Edit) của bản ghi đầu tiên")
    public void clickEditFirstBranch() {
        clickElement(editFirstButton);
    }

	@Step("Click nút '編集' (Edit)")
	public void clickEditButton() {
		clickElement(editButton);
	}

	@Step("Kiểm tra Tên chi nhánh bị disable")
	public boolean isBranchNameDisabled() {
		return !isElementEnabled(branchNameInput);
	}

	@Step("Kiểm tra Email bị disable")
	public boolean isEmailDisabled() {
		return !isElementEnabled(emailInput);
	}

	@Step("Kiểm tra Mã chi nhánh bị disable")
	public boolean isBranchCodeDisabled() {
		return !isElementEnabled(branchCodeInput);
	}

	@Step("Kiểm tra Số điện thoại bị disable")
	public boolean isPhoneNumberDisabled() {
		return !isElementEnabled(phoneNumberInput);
	}

	@Step("Kiểm tra Địa chỉ bị disable")
	public boolean isAddressDisabled() {
		return !isElementEnabled(addressInput);
	}

	@Step("Kiểm tra Loại (Type) có đang enable không")
	public boolean isTypeEnabled() {
		return isElementEnabled(typeSelect);
	}

	@Step("Lấy số lượng option của dropdown Loại (Type)")
	public int getTypeOptionsCount() {
		return getSelectOptionsCount(typeSelect);
	}

	@Step("Click nút '新規登録' (Tạo mới branch)")
	public void clickAddNewRegistration() {
		clickElement(addNewRegistrationBtn);
	}

	@Step("Nhập Tên chi nhánh: {0}")
	public void enterBranchName(String name) {
		enterText(branchNameInput, name);
	}

	@Step("Nhập Email: {0}")
	public void enterEmail(String email) {
		enterText(emailInput, email);
	}

	@Step("Nhập Mã chi nhánh: {0}")
	public void enterBranchCode(String code) {
		enterText(branchCodeInput, code);
	}

	@Step("Nhập Số điện thoại: {0}")
	public void enterPhoneNumber(String phone) {
		enterText(phoneNumberInput, phone);
	}

	@Step("Nhập Địa chỉ: {0}")
	public void enterAddress(String address) {
		enterText(addressInput, address);
	}

	@Step("Click nút Cập nhật / Lưu (Save/Update)")
	public void clickSave() {
		clickElement(saveButton);
	}

	@Step("Click nút Hủy (Cancel)")
	public void clickCancel() {
		clickElement(cancelButton);
	}
}
