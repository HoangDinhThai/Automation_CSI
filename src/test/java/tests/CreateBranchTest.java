package tests;

import page.BranchPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Branch Management")
@Feature("Create Branch")
public class CreateBranchTest extends BaseTest {

	@BeforeClass
	public void loginBeforeCreateBranch() {
		loginCRM();
	}

	@Test(priority = 1)
	@Description("Đi tới màn Branchs, nhấn 新規登録 và điền form tạo Branch mới")
	public void testCreateBranch() {
		BranchPage branchPage = new BranchPage(driver);

		branchPage.clickBranchMenu();
		branchPage.sleep(3);

		branchPage.clickAddNewRegistration();
		branchPage.sleep(2);

		String branchName = "Chi nhánh Hà Nội " + System.currentTimeMillis();
		branchPage.enterBranchName(branchName);
		branchPage.enterEmail("hanoi_branch@sfit.com");
		branchPage.enterBranchCode("HN" + System.currentTimeMillis() % 1000);
		branchPage.enterPhoneNumber("0123456789");
		branchPage.enterAddress("123 Cầu Giấy, Hà Nội");

		// branchPage.clickSave();
		branchPage.sleep(3);
	}
}