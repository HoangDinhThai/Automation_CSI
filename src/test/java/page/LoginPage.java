package page;

import org.openqa.selenium.By;
import io.qameta.allure.Step;

import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

	// Cognito Locators
	private final By usernameInput = By.xpath("//div[contains(@class, 'visible-md')]//input[@id='signInFormUsername']");
	private final By passwordInput = By.xpath("//div[contains(@class, 'visible-md')]//input[@id='signInFormPassword']");
	private final By signInSubmitButton = By
			.xpath("//div[contains(@class, 'visible-md')]//input[@name='signInSubmitButton']");

	// Intermediary Microsoft Login Button Locator
	private final By microsoftLoginButton = By.cssSelector("a[href*='/login/microsoft']");

	// Microsoft Login Locators
	private final By msEmailInput = By.name("loginfmt");
	private final By msNextButton = By.id("idSIButton9");
	private final By msPasswordInput = By.name("passwd");
	private final By msSignInButton = By.id("idSIButton9");
	private final By msStaySignedInNoButton = By.id("idBtn_Back");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@Step("Truy cập trang URL: {0}")
	public void navigateTo(String url) {
		driver.get(url);
	}

	@Step("Đăng nhập Cognito với tài khoản: {0}")
	public void loginWithCognito(String username, String password) {
		enterText(usernameInput, username);
		enterText(passwordInput, password);
		clickElement(signInSubmitButton);
	}

	@Step("Click nút Đăng nhập với Microsoft")
	public void clickMicrosoftLogin() {
		clickElement(microsoftLoginButton);
	}

	@Step("Click Use another account (nếu có)")
	public void clickUseAnotherAccount() {
		clickElement(By.id("otherTile"));
	}

	@Step("Đăng nhập Microsoft với email: {0}")
	public void loginWithMicrosoft(String email, String password) {
		// Nhập email
		enterText(msEmailInput, email);
		clickElement(msNextButton);

		// Nhập password
		enterText(msPasswordInput, password);
		clickElement(msSignInButton);

		// Chọn "No" (Không duy trì đăng nhập) nếu màn hình này xuất hiện
		if (isElementDisplayed(msStaySignedInNoButton)) {
			clickElement(msStaySignedInNoButton);
		}
	}

	@Step("Kiểm tra hiển thị thông báo lỗi: {0}")
	public boolean isErrorMessageDisplayed(String errorMessage) {
		By errorLocator = By.xpath("//*[contains(text(), '" + errorMessage + "')]");
		return isElementDisplayed(errorLocator);
	}
}
