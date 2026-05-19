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

		// 1. Truy cập trang Login
		loginPage.navigateTo("https://dev3.dev.sfit-local.com/login");

		// 2. Đăng nhập bước 1 (Cognito)
		loginPage.loginWithCognito("developer", "92W4t2QH-@TouyUv@qoJ");
		loginPage.sleep(2);

		// 3. Click nút Đăng nhập với Microsoft
		loginPage.clickMicrosoftLogin();
		loginPage.sleep(2);

		// 4. Nhấn nút Use another account (nếu có)
//		loginPage.clickUseAnotherAccount();
//		loginPage.sleep(1);

		// 5. Đăng nhập bước 2 (Microsoft) với tài khoản hợp lệ
		loginPage.loginWithMicrosoft("sfit_choice_ie-verification1@initial-engine.io", "F%866346732819uj");
		loginPage.sleep(3);

		// Đợi đến khi URL chuyển sang màn Dashboard
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.urlContains("/crm/tasks"));
	}

	@Test(priority = 1)
	@Description("Kiểm tra thông báo lỗi của các trường bắt buộc khi bỏ trống")
	public void testRequiredFields_ValidationMessage() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		CreateBusinessPage createPage = new CreateBusinessPage(driver);

		// --- BƯỚC 1: MỞ FORM TẠO MỚI ---
		dashboardPage.clickCreateBusiness();
		dashboardPage.clickCreateBusinessForm();

		// --- BƯỚC 2: NHẤN LƯU NGAY KHI CHƯA NHẬP DỮ LIỆU ---
		createPage.clickSave();

		// Đợi một chút để các thông báo lỗi (validation message) hiển thị
		createPage.sleep(2);

		// --- BƯỚC 3: KIỂM TRA LỖI ---
		java.util.List<String> errors = createPage.getValidationErrors();
		System.out.println("\n=== DANH SÁCH CÁC LỖI REQUIRED HIỂN THỊ (" + errors.size() + " lỗi) ===");
		for (int i = 0; i < errors.size(); i++) {
			System.out.println("  " + (i + 1) + ". " + errors.get(i));
		}
		System.out.println("========================================================\n");

		// Bạn có thể sửa câu Assert này để check chính xác nội dung list lỗi mong đợi
		org.testng.Assert.assertTrue(errors.size() > 0, "Lỗi: Không tìm thấy thông báo lỗi required nào!");

		// --- BƯỚC 4: RESET TRẠNG THÁI VỀ DASHBOARD ---
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

		// --- BƯỚC 1: QUAY VỀ DASHBOARD NẾU ĐANG Ở MÀN KHÁC (Quan trọng khi chạy vòng
		// lặp) ---
		driver.get("https://dev3.dev.sfit-local.com/crm/tasks");
		dashboardPage.sleep(2);

		// --- BƯỚC 2: MỞ FORM TẠO MỚI ---
		dashboardPage.clickCreateBusiness();
		dashboardPage.clickCreateBusinessForm();

		// --- BƯỚC 3: ĐIỀN DATA VÀO FORM ---
		// Điền các trường bắt buộc trước
		createPage.fillRequiredDropdowns(visitType, receptionType, motivation, movingTime);

		createPage.fillBasicInfo(lastName, firstName, kanaLast, kanaFirst, gender, dob);
		createPage.fillContactInfo(phonePrefix, phoneNum, email, subPhonePrefix, subPhoneNum, subEmail);

		// Chỉ truyền 3 trường cần thiết vào form Address
		createPage.fillAddressInfo(postalCode, area, building);

		// --- BƯỚC 4: LƯU ---
		createPage.clickSave();

		// Thêm sleep để đợi request lưu hoàn thành
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

		// Dựa theo UI, format tên thường ghép LastName + FirstName.
		// Bạn có thể đổi sang check tên Kana nếu muốn chắc chắn hơn.
		String expectedFullName = lastName + " " + firstName;
		// --- BƯỚC 1: KIỂM TRA TẠI MÀN HÌNH CUSTOMERS ---
		driver.get("https://dev3.dev.sfit-local.com/crm/customers");
		customersPage.sleep(3); // Đợi page load

		boolean isDisplayOnCustomers = customersPage.isCustomerDisplayed(lastName); // Tạm search theo LastName
		org.testng.Assert.assertTrue(isDisplayOnCustomers,
				"Lỗi: Không tìm thấy khách hàng chứa text '" + lastName + "' ở màn crm/customers");

		// --- BƯỚC 2: KIỂM TRA TẠI MÀN HÌNH ALL ---
		driver.get("https://dev3.dev.sfit-local.com/crm?tab=all");
		customersPage.sleep(3); // Đợi page load

		boolean isDisplayOnAll = customersPage.isCustomerDisplayed(lastName);
		org.testng.Assert.assertTrue(isDisplayOnAll,
				"Lỗi: Không tìm thấy khách hàng chứa text '" + lastName + "' ở màn crm?tab=all");
	}

//	@Test(priority = 4, dataProvider = "businessData")
//	@Description("Kiểm tra luồng Update - Cập nhật thông tin Business vừa tạo")
//	public void testUpdateBusiness(String lastName, String firstName, String kanaLast, String kanaFirst, String gender,
//			String dob, String phonePrefix, String phoneNum, String email, String subPhonePrefix, String subPhoneNum,
//			String subEmail, String postalCode, String prefecture, String city, String area, String addressDetail,
//			String building, String visitType, String receptionType, String motivation, String movingTime) {
//
//		DashboardPage dashboardPage = new DashboardPage(driver);
//		CreateBusinessPage createPage = new CreateBusinessPage(driver);
//
//		// --- BƯỚC 1: QUAY LẠI MÀN HÌNH DASHBOARD ---
//		driver.get("https://dev3.dev.sfit-local.com/crm/tasks");
//		dashboardPage.sleep(3);
//
//		// --- BƯỚC 2: TÌM BẢN GHI VỪA TẠO VÀ NHẤN NÚT "入力" ---
//		// Tìm theo lastName hoặc fullName tuỳ ý, ở đây mình tìm theo lastName cho dễ
//		dashboardPage.clickInputForCustomer(lastName);
//		dashboardPage.sleep(1);
//
//		// --- BƯỚC 3: NHẤN NÚT "スタッフ" TRONG POPUP ---
//		dashboardPage.clickStaffEditButton();
//		dashboardPage.sleep(3); // Đợi màn hình Edit Form load xong
//
//		// --- BƯỚC 4: TIẾN HÀNH EDIT DỮ LIỆU ---
//		// Bạn có thể update bất kỳ trường nào ở đây. Ví dụ update LastName bằng cách
//		// thêm chữ "Updated"
//		String updatedLastName = lastName + " Updated";
//
//		// Gọi lại hàm điền BasicInfo để overwrite giá trị cũ
//		createPage.fillBasicInfo(updatedLastName, firstName, kanaLast, kanaFirst, gender, dob);
//
//		// --- BƯỚC 5: LƯU BẢN GHI ĐÃ EDIT ---
//		createPage.clickSave();
//		createPage.sleep(5); // Đợi lưu thành công
//	}
}
