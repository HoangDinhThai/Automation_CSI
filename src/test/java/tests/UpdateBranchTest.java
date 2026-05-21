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

		loginPage.navigateTo("https://dev3.dev.sfit-local.com/login");

		loginPage.loginWithCognito("developer", "92W4t2QH-@TouyUv@qoJ");
		loginPage.sleep(2);

		loginPage.clickMicrosoftLogin();
		loginPage.sleep(2);

		loginPage.loginWithMicrosoft("sfit_choice_ie-verification1@initial-engine.io", "F%866346732819uj");
		loginPage.sleep(3);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.urlContains("/crm/tasks"));
	}

	@Test(priority = 1)
	@Description("Kiểm tra form Edit Branch (các trường bị disable, chỉ dropdown 種別 enable và có 3 options)")
	public void testVerifyEditBranch() {
		BranchPage branchPage = new BranchPage(driver);

		branchPage.clickBranchMenu();
		branchPage.sleep(3);

		branchPage.clickEditFirstBranch();
		branchPage.sleep(2);

		org.testng.Assert.assertTrue(branchPage.isBranchNameDisabled(), "Tên chi nhánh phải bị disable");
		System.out.println("Tên chi nhánh đã disable");

		org.testng.Assert.assertTrue(branchPage.isEmailDisabled(), "Email phải bị disable");
		System.out.println("Email đã disable");

		org.testng.Assert.assertTrue(branchPage.isBranchCodeDisabled(), "Mã chi nhánh phải bị disable");
		System.out.println("Mã chi nhánh đã disable");

		org.testng.Assert.assertTrue(branchPage.isPhoneNumberDisabled(), "Số điện thoại phải bị disable");
		System.out.println("Số điện thoại đã disable");

		org.testng.Assert.assertTrue(branchPage.isAddressDisabled(), "Địa chỉ phải bị disable");
		System.out.println("Địa chỉ đã disable");

		org.testng.Assert.assertTrue(branchPage.isTypeEnabled(), "Trường Loại (Type) phải được enable");

		int typeOptionsCount = branchPage.getTypeOptionsCount();
		org.testng.Assert.assertEquals(typeOptionsCount, 3, "Trường Loại (Type) phải có đúng 3 options");

		branchPage.clickCancel();
		branchPage.sleep(2);

		branchPage.clickEditFirstBranch();
		branchPage.sleep(2);

		branchPage.clickSave();
		branchPage.sleep(3);
	}
}