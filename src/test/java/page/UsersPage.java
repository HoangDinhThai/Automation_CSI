package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsersPage extends BasePage {
	private final By usersMenu = By.xpath("//*[contains(text(), 'ユーザー')]");
	private final By editFirstButton = By.xpath("//*[@id='datatable']/tbody/tr[1]//button[contains(., '編集')]");
	private final By staffNumberInput = By.id("staff_number");
	private final By nameInput = By.id("name");
	private final By branchCdSelect = By.id("branch_cd");
	private final By roleSelect = By.id("role");
	private final By emailInput = By.id("email");
	private final By phoneNumberInput = By.id("phone_number");
	private final By coreRoleTextInput = By.id("core_role_text");
	private final By cancelButton = By.xpath("//button[contains(@class, 'btn-cancel')]");
	private final By saveButton = By.id("btn_submit");

	public UsersPage(WebDriver driver) {
		super(driver);
	}

	@Step("Click menu 'ユーザー' để vào màn Users")
	public void clickUsersMenu() {
		clickElement(usersMenu);
	}

	@Step("Click nút '編集' (Edit) của bản ghi đầu tiên")
	public void clickEditFirstUser() {
		clickElement(editFirstButton);
	}

	@Step("Kiểm tra Mã nhân viên bị disable/readonly")
	public boolean isStaffNumberDisabled() {
		return !isElementEnabled(staffNumberInput);
	}

	@Step("Kiểm tra Tên bị disable/readonly")
	public boolean isNameDisabled() {
		return !isElementEnabled(nameInput);
	}

	@Step("Kiểm tra Chi nhánh bị disable")
	public boolean isBranchCdDisabled() {
		return !isElementEnabled(branchCdSelect);
	}

	@Step("Kiểm tra Quyền hạn (Role) có đang enable không")
	public boolean isRoleEnabled() {
		return isElementEnabled(roleSelect);
	}

	@Step("Kiểm tra Email bị disable/readonly")
	public boolean isEmailDisabled() {
		return !isElementEnabled(emailInput);
	}

	@Step("Kiểm tra Số điện thoại bị disable")
	public boolean isPhoneNumberDisabled() {
		return !isElementEnabled(phoneNumberInput);
	}

	@Step("Kiểm tra Vai trò cốt lõi bị disable")
	public boolean isCoreRoleTextDisabled() {
		return !isElementEnabled(coreRoleTextInput);
	}

	@Step("Click nút Hủy (Cancel)")
	public void clickCancel() {
		clickElement(cancelButton);
	}

	@Step("Click nút Cập nhật (Save)")
	public void clickSave() {
		clickElement(saveButton);
	}
}