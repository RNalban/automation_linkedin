package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHandler {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/data/LinkedIn_Profiles.xlsx";
    public static String firstName;
    public static String lastName;
    public static String link;
    public static String company;

    public static List<Contact> getContactsFromExcel(int maxRows) throws Exception {

        List<Contact> contacts = new ArrayList<>();
        File file = new File(FILE_PATH);
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("SheetJS");
        int rowCount = 1;
        for (int i = rowCount; i <= maxRows; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue; // Skip empty rows

            String firstName = row.getCell(1).getStringCellValue();
            String lastName = row.getCell(2).getStringCellValue();
            String company = row.getCell(4).getStringCellValue();
            String link = row.getCell(5).getStringCellValue();
            // Column 3: Link
            contacts.add(new Contact(firstName, lastName, link,company));

        }
        workbook.close();
        fis.close();

        return contacts;
    }

    public void AddProfilesToExcel() {

    }

}