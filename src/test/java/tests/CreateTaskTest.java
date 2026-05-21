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
    @Description("Đi tới màn crm?tab=all, chọn Business và nhấn nút 新規追加")
    public void testGoToBusinessAndClickAddNew() {
        CustomersPage customersPage = new CustomersPage(driver);
        BusinessDetailPage detailPage = new BusinessDetailPage(driver);

        driver.get("https://dev3.dev.sfit-local.com/crm?tab=all");
        customersPage.sleep(3);

        String businessId = "G000000054";
        customersPage.clickCustomer(businessId);
        customersPage.sleep(3);

        detailPage.clickAddNew();
        detailPage.sleep(2);
    }
}