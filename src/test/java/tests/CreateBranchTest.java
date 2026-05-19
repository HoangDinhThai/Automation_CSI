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
@Feature("Create Branch")
public class CreateBranchTest extends BaseTest {

	@BeforeClass
	public void loginBeforeCreateBranch() {
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
	@Description("Đi tới màn Branchs, nhấn 新規登録 và điền form tạo Branch mới")
	public void testCreateBranch() {
		BranchPage branchPage = new BranchPage(driver);

		// Bước 1: Nhấn menu 支店・グループ để vào màn hình Branchs
		branchPage.clickBranchMenu();
		branchPage.sleep(3); // Đợi trang danh sách load

		// Bước 2: Nhấn nút 新規登録
		branchPage.clickAddNewRegistration();
		branchPage.sleep(2); // Đợi modal form bật lên

		// Bước 3: Điền các thông tin (đã sử dụng xpath lấy được từ form thực tế)
		String branchName = "Chi nhánh Hà Nội " + System.currentTimeMillis();
		branchPage.enterBranchName(branchName);
		branchPage.enterEmail("hanoi_branch@sfit.com");
		branchPage.enterBranchCode("HN" + System.currentTimeMillis() % 1000);
		branchPage.enterPhoneNumber("0123456789");
		branchPage.enterAddress("123 Cầu Giấy, Hà Nội");

		// Bước 4: Nhấn Lưu
		//branchPage.clickSave();
		branchPage.sleep(3); // Đợi lưu xong
	}
}
