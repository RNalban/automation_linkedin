package utils;

import java.util.*;
import java.io.*;

public class ConfigLoader {
private static Properties properties;

    static {
        // Load the properties file
        try (FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/config.properties")) {
            properties = new Properties();
            properties.load(fileInputStream);
            System.out.println("Properties file loaded successfully");
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to get the username for a specific account
    public static String getEmail(String accountKey) {
        String username = properties.getProperty(accountKey + ".username");
        if (username == null) {
            System.err.println("Username not found for account: " + accountKey);
        }
        return username;
    }

    // Method to get the password for a specific account
    public static String getPassword(String accountKey) {
        String password = properties.getProperty(accountKey + ".password");
        if (password == null) {
            System.err.println("Password not found for account: " + accountKey);
        }
        return password;
    }

    public static String getProxy(String accountKey) {
        return properties.getProperty(accountKey + ".proxy");
    }
    public static int getTotalAccounts() {
        int count = 0;
        while (properties.containsKey("account" + (count + 1) + ".username")) {
            count++;
        }
        return count;
    }

}
