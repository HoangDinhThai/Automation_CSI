package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CreateBusinessPage extends BasePage {

	// --- KHAI BÁO LOCATOR DỰA TRÊN PLACEHOLDER & LABEL (GUEST TỪ ẢNH) ---

	private final By lastNameInput = By.xpath("//input[@placeholder='伊藤' or @name='lastName']");
	private final By firstNameInput = By.xpath("//input[@placeholder='三郎' or @name='firstName']");
	private final By kanaLastNameInput = By.xpath("//input[@placeholder='フリガナ (セイ)' or @placeholder='フリガナ（セイ）']");
	private final By kanaFirstNameInput = By.xpath("//input[@placeholder='フリガナ (メイ)' or @placeholder='フリガナ（メイ）']");

	// Giới tính (Dropdown)
	private final By genderDropdown = By.xpath("//label[contains(text(), '性別')]/following::select[1]");

	// Ngày sinh
	private final By dobInput = By.xpath("//input[contains(@placeholder, '年') and contains(@placeholder, '月')]");

	// SĐT chính (2 ô)
	private final By phonePrefixInput = By.xpath("(//input[@placeholder='090'])[1]");
	private final By phoneNumInput = By.xpath("(//input[@placeholder='12345678'])[1]");

	// Email chính
	private final By emailInput = By.xpath("(//input[contains(@placeholder, 'sample@mail')])[1]");

	// SĐT phụ (2 ô)
	private final By subPhonePrefixInput = By.xpath("(//input[@placeholder='090'])[2]");
	private final By subPhoneNumInput = By.xpath("(//input[@placeholder='12345678'])[2]");

	// Email phụ
	private final By subEmailInput = By.xpath("(//input[contains(@placeholder, 'sample@mail')])[2]");

	// --- PHẦN TRƯỜNG BẮT BUỘC MỚI (DROP DOWNS) ---
	private final By visitTypeDropdown = By.xpath("//label[contains(text(), '来店種別')]/following::select[1]");
	private final By receptionTypeDropdown = By.xpath("//label[contains(text(), '受付種別')]/following::select[1]");
	private final By motivationDropdown = By.xpath("//label[contains(text(), '来店動機')]/following::select[1]");
	private final By movingTimeDropdown = By.xpath("//label[contains(text(), '引越し時期')]/following::select[1]");

	// --- PHẦN ĐỊA CHỈ ---
	private final By postalCodeInput = By.xpath("//input[@placeholder='000-0000']");
	private final By areaInput = By.xpath("//input[contains(@placeholder, '松涛')]");
	private final By buildingInput = By.xpath("//input[@placeholder='建物名']");

	// Nút Lưu (Save) - Giả định
	private final By saveButton = By
			.xpath("//button[contains(text(), '保存') or contains(text(), '登録') or contains(text(), 'Save')]");

	private final By validationErrors = By.xpath(
			"//*[contains(@class, 'invalid-feedback') or contains(@class, 'error') or contains(@class, 'text-danger')]");

	public CreateBusinessPage(WebDriver driver) {
		super(driver);
	}

	@Step("Nhập thông tin cơ bản: {0} {1}")
	public void fillBasicInfo(String lastName, String firstName, String kanaLast, String kanaFirst, String gender,
			String dob) {
		enterText(lastNameInput, lastName);
		enterText(firstNameInput, firstName);
		enterText(kanaLastNameInput, kanaLast);
		enterText(kanaFirstNameInput, kanaFirst);

		// Chọn giới tính nếu có data
		if (gender != null && !gender.isEmpty()) {
			try {
				WebElement genderSelect = driver.findElement(genderDropdown);
				new Select(genderSelect).selectByVisibleText(gender);
			} catch (Exception e) {
				System.out.println("Không thể chọn Giới tính: " + e.getMessage());
			}
		}

		enterText(dobInput, dob);
	}

	@Step("Nhập thông tin liên lạc")
	public void fillContactInfo(String phonePrefix, String phoneNum, String email, String subPhonePrefix,
			String subPhoneNum, String subEmail) {
		enterText(phonePrefixInput, phonePrefix);
		enterText(phoneNumInput, phoneNum);
		enterText(emailInput, email);

		enterText(subPhonePrefixInput, subPhonePrefix);
		enterText(subPhoneNumInput, subPhoneNum);
		enterText(subEmailInput, subEmail);
	}

	@Step("Nhập thông tin địa chỉ")
	public void fillAddressInfo(String postalCode, String area, String building) {
		// Chỉ điền 3 trường theo đúng ảnh yêu cầu
		enterText(postalCodeInput, postalCode);
		enterText(areaInput, area);
		enterText(buildingInput, building);
	}

	@Step("Chọn các trường bắt buộc: {0}, {1}, {2}, {3}")
	public void fillRequiredDropdowns(String visitType, String receptionType, String motivation, String movingTime) {
		if (visitType != null && !visitType.isEmpty()) {
			try {
				new Select(driver.findElement(visitTypeDropdown)).selectByVisibleText(visitType);
			} catch (Exception e) {
			}
		}
		if (receptionType != null && !receptionType.isEmpty()) {
			try {
				new Select(driver.findElement(receptionTypeDropdown)).selectByVisibleText(receptionType);
			} catch (Exception e) {
			}
		}
		if (motivation != null && !motivation.isEmpty()) {
			try {
				new Select(driver.findElement(motivationDropdown)).selectByVisibleText(motivation);
			} catch (Exception e) {
			}
		}
		if (movingTime != null && !movingTime.isEmpty()) {
			try {
				new Select(driver.findElement(movingTimeDropdown)).selectByVisibleText(movingTime);
			} catch (Exception e) {
			}
		}
	}

	@Step("Click nút Đăng ký / Lưu")
	public void clickSave() {
		clickElement(saveButton);
	}

	   @Step("Lấy danh sách các thông báo lỗi")
	    public java.util.List<String> getValidationErrors() {
	        java.util.List<WebElement> errorElements = driver.findElements(validationErrors);
	        java.util.List<String> errorTexts = new java.util.ArrayList<>();
	        for (WebElement el : errorElements) {
	            String text = el.getText().trim();
	            if (!text.isEmpty()) {
	                String fieldName = "Unknown Field";
	                try {
	                    // Tìm thẻ label gần nhất nằm phía trước thông báo lỗi trong HTML
	                    WebElement labelEl = el.findElement(By.xpath("preceding::label[1]"));
	                    fieldName = labelEl.getText().trim();
	                    // Làm sạch tên trường (bỏ dấu sao hoặc chữ bắt buộc nếu có)
	                    fieldName = fieldName.replace("必須", "").replace("*", "").trim();
	                    fieldName = fieldName.replaceAll("\\r\\n|\\r|\\n", " ");
	                } catch (Exception e) {
	                    // Nếu không tìm thấy thẻ label, giữ nguyên là Unknown Field
	                }
	                errorTexts.add(fieldName + " : " + text);
	            }
	        }
	        return errorTexts;
	    }
}
