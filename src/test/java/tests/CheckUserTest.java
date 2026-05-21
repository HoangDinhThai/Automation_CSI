package tests;

import page.LoginPage;
import page.UsersPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

@Epic("User Management")
@Feature("Check User")
public class CheckUserTest extends BaseTest {
	@BeforeClass
	public void loginBeforeUserTest() {
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
	@Description("Kiểm tra form Edit User (các trường bị disable, chỉ trường 権限 enable)")
	public void testVerifyEditUserModal() {
		UsersPage usersPage = new UsersPage(driver);
		// Bước 1: Nhấn menu ユーザー để vào màn hình Users
		usersPage.clickUsersMenu();
		usersPage.sleep(3); // Đợi trang danh sách load
		// Bước 2: Nhấn nút 編集 (Edit) của bản ghi đầu tiên
		usersPage.clickEditFirstUser();
		usersPage.sleep(2); // Đợi form Edit bật lên
		// Bước 3: Kiểm tra tất cả các trường (trừ 権限 - Role) đều bị disable/readonly
		org.testng.Assert.assertTrue(usersPage.isStaffNumberDisabled(), "Mã nhân viên phải bị disable/readonly");
		System.out.println("Mã nhân viên đã disable");
		org.testng.Assert.assertTrue(usersPage.isNameDisabled(), "Tên phải bị disable/readonly");
		System.out.println("Tên đã disable");
		org.testng.Assert.assertTrue(usersPage.isBranchCdDisabled(), "Chi nhánh phải bị disable");
		System.out.println("Chi nhánh đã disable");
		org.testng.Assert.assertTrue(usersPage.isEmailDisabled(), "Email phải bị disable/readonly");
		System.out.println("Email đã disable");
		org.testng.Assert.assertTrue(usersPage.isPhoneNumberDisabled(), "Số điện thoại phải bị disable");
		System.out.println("Số điện thoại đã disable");
		org.testng.Assert.assertTrue(usersPage.isCoreRoleTextDisabled(), "Vai trò cốt lõi phải bị disable");
		System.out.println("Vai trò cốt lõi đã disable");
		// Bước 4: Kiểm tra trường 権限 (Role) đang được enable
		org.testng.Assert.assertTrue(usersPage.isRoleEnabled(), "Trường Quyền hạn (Role) phải được enable");
		System.out.println("Role enable");
		// Bước 5: Nhấn nút Hủy (Cancel)
		usersPage.clickCancel();
		usersPage.sleep(2); // Đợi form đóng lại
		// Bước 6: Mở lại form Edit và nhấn Cập nhật (Save)
		usersPage.clickEditFirstUser();
		usersPage.sleep(2);

		usersPage.clickSave(); // btn_submit cho Cập nhật
		usersPage.sleep(3); // Đợi cập nhật xong
	}
}
