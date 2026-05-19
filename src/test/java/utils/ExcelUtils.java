package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelUtils {

    /**
     * Đọc dữ liệu từ file Excel và trả về dưới dạng mảng 2 chiều (Object[][]) để dùng cho @DataProvider của TestNG.
     * @param filePath Đường dẫn tới file Excel
     * @param sheetName Tên Sheet chứa dữ liệu
     * @return Mảng 2 chiều chứa dữ liệu các dòng
     */ 
    public static Object[][] getExcelData(String filePath, String sheetName) {
        Object[][] data = null;
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            // Số dòng (Bỏ dòng header đầu tiên)
            int rowCount = sheet.getLastRowNum();
            // Số cột
            int colCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount][colCount];

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = "";
                    if (cell != null) {
                        DataFormatter formatter = new DataFormatter();
                        cellValue = formatter.formatCellValue(cell); // Đọc an toàn mọi định dạng (chữ, số, ngày)
                    }
                    data[i - 1][j] = cellValue;
                }
            }
            workbook.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("Lỗi đọc file Excel: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }
}
