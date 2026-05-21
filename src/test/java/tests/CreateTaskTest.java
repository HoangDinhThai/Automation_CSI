package tests;

import page.BusinessDetailPage;
import page.CustomersPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Task Management")
@Feature("Create Task")
public class CreateTaskTest extends BaseTest {

    @BeforeClass
    public void loginBeforeCreateTask() {
        loginCRM();
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