package tests;

import page.BusinessDetailPage;
import page.CustomersPage;
import page.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Task Management")
@Feature("Create Task")
public class CreateTaskTest extends BaseTest {

    @BeforeClass
    public void loginBeforeCreateTask() {
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
    @Description("Đi tới màn crm?tab=all, chọn Business và nhấn nút 新規追加")
    public void testGoToBusinessAndClickAddNew() {
        CustomersPage customersPage = new CustomersPage(driver);
        BusinessDetailPage detailPage = new BusinessDetailPage(driver);

        // Bước 1: Đi tới màn hình Tất cả (All)
        driver.get("https://dev3.dev.sfit-local.com/crm?tab=all");
        customersPage.sleep(3);

        // Bước 2: Nhấn chọn Business có ID là G000000054
        String businessId = "G000000054";
        customersPage.clickCustomer(businessId);
        customersPage.sleep(3); // Đợi màn hình chi tiết Business load xong

        // Bước 3: Tìm đến nút 新規追加 và nhấn
        detailPage.clickAddNew();
        detailPage.sleep(2); // Tạm dừng để quan sát (hoặc thêm verify cho form Create Task tiếp theo)
    }
}
