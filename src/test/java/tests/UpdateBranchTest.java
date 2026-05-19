package tests;

import page.BranchPage;
import page.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Branch Management")
@Feature("Edit Branch")
public class UpdateBranchTest extends BaseTest {

	@BeforeClass
	public void loginBeforeEditBranch() {
		LoginPage loginPage = new LoginPage(driver);

		// 1. Truy cập trang Login
		loginPage.navigateTo("https://dev3.dev.sfit-local.com/login");

		// 2. Đăng nhập bước 1 (Cognito)
		loginPage.loginWithCognito("developer", "92W4t2QH-@TouyUv@qoJ");
		loginPage.sleep(2);

		// 3. Click nút Đăng nhập với Microsoft
		loginPage.clickMicrosoftLogin();
		loginPage.sleep(2);

		// 4. Đăng nhập bước 2 (Microsoft)
		loginPage.loginWithMicrosoft("sfit_choice_ie-verification1@initial-engine.io", "F%866346732819uj");
		loginPage.sleep(3);

		// Đợi đến khi URL chuyển sang màn Dashboard
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.urlContains("/crm/tasks"));
	}

	@Test(priority = 1)
	@Description("Kiểm tra form Edit Branch (các trường bị disable, chỉ dropdown 種別 enable và có 3 options)")
	public void testVerifyEditBranch() {
		BranchPage branchPage = new BranchPage(driver);

		// Bước 1: Nhấn menu 支店・グループ để vào màn hình Branchs
		branchPage.clickBranchMenu();
		branchPage.sleep(3); // Đợi trang danh sách load

		// Bước 2: Nhấn nút 編集 (Edit) của bản ghi đầu tiên
		branchPage.clickEditFirstBranch();
		branchPage.sleep(2); // Đợi form Edit bật lên

		// Bước 3: Kiểm tra tất cả các trường (trừ 種別) đều bị disable
		org.testng.Assert.assertTrue(branchPage.isBranchNameDisabled(), "Tên chi nhánh phải bị disable");
		org.testng.Assert.assertTrue(branchPage.isEmailDisabled(), "Email phải bị disable");
		org.testng.Assert.assertTrue(branchPage.isBranchCodeDisabled(), "Mã chi nhánh phải bị disable");
		org.testng.Assert.assertTrue(branchPage.isPhoneNumberDisabled(), "Số điện thoại phải bị disable");
		org.testng.Assert.assertTrue(branchPage.isAddressDisabled(), "Địa chỉ phải bị disable");

		// Bước 4: Kiểm tra trường 種別 (Type) đang được enable và có đúng 3 giá trị
		org.testng.Assert.assertTrue(branchPage.isTypeEnabled(), "Trường Loại (Type) phải được enable");
		int typeOptionsCount = branchPage.getTypeOptionsCount();
		org.testng.Assert.assertEquals(typeOptionsCount, 3, "Trường Loại (Type) phải có đúng 3 options");

		// Bước 5: Nhấn nút Hủy (Cancel)
		branchPage.clickCancel();
		branchPage.sleep(2); // Đợi form đóng lại

		// Bước 6: Mở lại form Edit và nhấn Cập nhật
		branchPage.clickEditFirstBranch();
		branchPage.sleep(2);

		branchPage.clickSave(); // btn_submit cho Cập nhật
		branchPage.sleep(3); // Đợi cập nhật xong
	}
}