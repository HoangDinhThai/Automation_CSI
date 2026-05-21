package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import page.DashboardPage;
import page.LoginPage;
import page.CustomersPage;
import page.CreateBusinessPage;
import utils.ExcelUtils;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Business Management")
@Feature("Create Business")
public class CreateBusinessTest extends BaseTest {

	@DataProvider(name = "businessData")
	public Object[][] getBusinessData() {
		String filePath = "src/test/resources/CustomerData.xlsx";
		return ExcelUtils.getExcelData(filePath, "Sheet1");
	}

	@BeforeClass
	public void loginBeforeCreateBusiness() {
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
	@Description("Kiểm tra thông báo lỗi của các trường bắt buộc khi bỏ trống")
	public void testRequiredFields_ValidationMessage() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		CreateBusinessPage createPage = new CreateBusinessPage(driver);

		dashboardPage.clickCreateBusiness();
		dashboardPage.clickCreateBusinessForm();

		createPage.clickSave();
		createPage.sleep(2);

		java.util.List<String> errors = createPage.getValidationErrors();
		System.out.println("\n=== DANH SÁCH CÁC LỖI REQUIRED HIỂN THỊ (" + errors.size() + " lỗi) ===");
		for (int i = 0; i < errors.size(); i++) {
			System.out.println("  " + (i + 1) + ". " + errors.get(i));
		}
		System.out.println("========================================================\n");

		org.testng.Assert.assertTrue(errors.size() > 0, "Lỗi: Không tìm thấy thông báo lỗi required nào!");

		driver.get("https://dev3.dev.sfit-local.com/crm/tasks");
		createPage.sleep(3);
	}

	@Test(priority = 2, dataProvider = "businessData")
	@Description("Test luồng tạo mới Business tự động điền Form từ Excel")
	public void testCreateBusiness_FillForm(String lastName, String firstName, String kanaLast, String kanaFirst,
			String gender, String dob, String phonePrefix, String phoneNum, String email, String subPhonePrefix,
			String subPhoneNum, String subEmail, String postalCode, String prefecture, String city, String area,
			String addressDetail, String building, String visitType, String receptionType, String motivation,
			String movingTime) {

		DashboardPage dashboardPage = new DashboardPage(driver);
		CreateBusinessPage createPage = new CreateBusinessPage(driver);

		driver.get("https://dev3.dev.sfit-local.com/crm/tasks");
		dashboardPage.sleep(2);

		dashboardPage.clickCreateBusiness();
		dashboardPage.clickCreateBusinessForm();

		createPage.fillRequiredDropdowns(visitType, receptionType, motivation, movingTime);

		createPage.fillBasicInfo(lastName, firstName, kanaLast, kanaFirst, gender, dob);
		createPage.fillContactInfo(phonePrefix, phoneNum, email, subPhonePrefix, subPhoneNum, subEmail);
		createPage.fillAddressInfo(postalCode, area, building);

		createPage.clickSave();
		createPage.sleep(5);
	}

	@Test(priority = 3, dataProvider = "businessData")
	@Description("Kiểm tra khách hàng mới tạo hiển thị ở màn Customers và màn All")
	public void testVerifyCreatedBusiness(String lastName, String firstName, String kanaLast, String kanaFirst,
			String gender, String dob, String phonePrefix, String phoneNum, String email, String subPhonePrefix,
			String subPhoneNum, String subEmail, String postalCode, String prefecture, String city, String area,
			String addressDetail, String building, String visitType, String receptionType, String motivation,
			String movingTime) {

		CustomersPage customersPage = new CustomersPage(driver);

		driver.get("https://dev3.dev.sfit-local.com/crm/customers");
		customersPage.sleep(3);

		boolean isDisplayOnCustomers = customersPage.isCustomerDisplayed(lastName);
		org.testng.Assert.assertTrue(isDisplayOnCustomers,
				"Lỗi: Không tìm thấy khách hàng chứa text '" + lastName + "' ở màn crm/customers");

		driver.get("https://dev3.dev.sfit-local.com/crm?tab=all");
		customersPage.sleep(3);

		boolean isDisplayOnAll = customersPage.isCustomerDisplayed(lastName);
		org.testng.Assert.assertTrue(isDisplayOnAll,
				"Lỗi: Không tìm thấy khách hàng chứa text '" + lastName + "' ở màn crm?tab=all");
	}

	@Test(priority = 4, dataProvider = "businessData")
	@Description("Kiểm tra luồng Update - Cập nhật thông tin Business vừa tạo")
	public void testUpdateBusiness(String lastName, String firstName, String kanaLast, String kanaFirst, String gender,
			String dob, String phonePrefix, String phoneNum, String email, String subPhonePrefix, String subPhoneNum,
			String subEmail, String postalCode, String prefecture, String city, String area, String addressDetail,
			String building, String visitType, String receptionType, String motivation, String movingTime) {

		DashboardPage dashboardPage = new DashboardPage(driver);
		CreateBusinessPage createPage = new CreateBusinessPage(driver);

		driver.get("https://dev3.dev.sfit-local.com/crm/tasks");
		dashboardPage.sleep(3);

		dashboardPage.clickInputForCustomer(lastName);
		dashboardPage.sleep(1);

		dashboardPage.clickStaffEditButton();
		dashboardPage.sleep(3);

		String updatedLastName = lastName + " Updated";

		createPage.fillBasicInfo(updatedLastName, firstName, kanaLast, kanaFirst, gender, dob);

		createPage.clickSave();
		createPage.sleep(5);
	}
}