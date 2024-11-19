package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHandler {
    private static String FILE_PATH = System.getProperty("user.dir") + "/data/LinkedIn_Profiles.xlsx";
    public static String firstName;
    public static String lastName;
    public static String link;
    Contact contact = new Contact(firstName,lastName,link);

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

            String firstName = row.getCell(1).getStringCellValue(); // Column 1: First Name
            String lastName = row.getCell(2).getStringCellValue();  // Column 2: Last Name
            String link = row.getCell(4).getStringCellValue();      // Column 3: Link
            contacts.add(new Contact(firstName, lastName, link));

        }
        workbook.close();
        fis.close();

        return contacts;
    }

    public void AddProfilesToExcel() {

    }

    public static void main(String[] args) {
        try {
            List<Contact> contacts = getContactsFromExcel(10);

            // Store data in variables or use as needed
            for (Contact contact : contacts) {
                String firstName = contact.getFirstName();
                String lastName = contact.getLastName();
                String link = contact.getLink();

                // Example: Print the data
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Link: " + link);
                System.out.println("---------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}