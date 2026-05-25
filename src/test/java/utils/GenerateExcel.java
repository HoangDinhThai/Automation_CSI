package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class GenerateExcel {
	public static void main(String[] args) {
		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Sheet1");

			// Tạo Header
			String[] headers = { "LastName", "FirstName", "KanaLast", "KanaFirst", "Gender", "DOB", "PhonePrefix",
					"PhoneNum", "Email", "SubPhonePrefix", "SubPhoneNum", "SubEmail", "PostalCode", "Prefecture",
					"City", "Area", "AddressDetail", "Building", "VisitType", "ReceptionType", "Motivation",
					"MovingTime" };

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				headerRow.createCell(i).setCellValue(headers[i]);
			}

			// Dữ liệu dòng 1 (Khách hàng 1 mà bạn cung cấp)
			String[] data1 = { "Hoang", "Hung", "ホアン", "フオン", "女性", "1995-10-10", "090", "11223399",
					"hoang.phuong110@example.com", "080", "99887711", "phuong110.sub@example.jp", "180-0002", "東京都",
					"武蔵野市", "Tokyo", "吉祥寺東町", "4-5-6 Kichijoji Center 1001", "店舗直来店", "NETフリー", "CHINTAI",
					"今すぐ引越したい(1-2ヶ月以内)" };

			Row row1 = sheet.createRow(1);
			for (int i = 0; i < data1.length; i++) {
				row1.createCell(i).setCellValue(data1[i]);
			}

			// Lưu file Excel
			File dir = new File("src/test/resources");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			FileOutputStream fileOut = new FileOutputStream("src/test/resources/CustomerData.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();

			System.out.println("Tạo file Excel CustomerData.xlsx thành công!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}