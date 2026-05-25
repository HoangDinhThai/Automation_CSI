package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import page.LoginPage;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

@Epic("Authentication")
@Feature("Login Flow")
public class LoginTest extends BaseTest {

    @Test(priority = 1)
    @Description("Kiểm tra đăng nhập thất bại khi tài khoản chưa được cấp quyền trên hệ thống (Unregistered User)")
    public void testLogin_Failed_UnregisteredUser() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.navigateTo("https://dev3.dev.sfit-local.com/login");
        loginPage.sleep(2);

        loginPage.loginWithCognito("developer", "92W4t2QH-@TouyUv@qoJ");
        loginPage.sleep(2);

        loginPage.clickMicrosoftLogin();
        loginPage.sleep(2);

        loginPage.loginWithMicrosoft("sfit_choice_ie-verification2@initial-engine.io", "J)048127895996ug");
        loginPage.sleep(3);

        String expectedError = "基幹システムに登録されていません。";
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed(expectedError);
        Assert.assertTrue(isErrorDisplayed, "Không tìm thấy thông báo lỗi: " + expectedError);
    }

    @Test(priority = 2)
    @Description("Kiểm tra đăng nhập thành công vào màn hình Dashboard với tài khoản hợp lệ")
    public void testLogin_Successful() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickMicrosoftLogin();
        loginPage.sleep(2);

        loginPage.clickUseAnotherAccount();
        loginPage.sleep(1);

        loginPage.loginWithMicrosoft("sfit_choice_ie-verification1@initial-engine.io", "F%866346732819uj");
        loginPage.sleep(3);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlContains("/crm/tasks"));

        boolean isDashboardLoaded = driver.getCurrentUrl().contains("/crm/tasks");
        Assert.assertTrue(isDashboardLoaded, "Login failed, không vào được màn hình Dashboard (Tasks).");
    }
}