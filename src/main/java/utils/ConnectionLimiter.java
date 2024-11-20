package utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.hc.core5.util.Deadline.DATE_FORMAT;

public class ConnectionLimiter {

    public static final int MAX_CONNECTIONS_PER_DAY = 20;
    private static final Map<Object, UserData> userConnections = new HashMap<>();

    public int sendConnectionRequest(String userEmail) {
        String today = DATE_FORMAT.format(String.valueOf(new Date()));

        // Fetch user data or initialize it if not present
        UserData data = userConnections.getOrDefault(userEmail, new UserData(0, today));

        // Reset the count if the date has changed
        if (!data.lastDate.equals(today)) {
            data.connectionCount = 0;
            data.lastDate = today;
        }

        // Check if the user has reached the daily limit
        if (data.connectionCount < MAX_CONNECTIONS_PER_DAY) {
            data.connectionCount++;
            userConnections.put(userEmail, data); // Update the user's data
            return data.connectionCount; // Return the current connection count
        } else {
            return -1; // Return -1 if the limit has been reached
        }
    }


    // Inner class to hold user data
    static class UserData {
        int connectionCount;
        String lastDate;

        public UserData(int connectionCount, String lastDate) {
            this.connectionCount = connectionCount;
            this.lastDate = lastDate;
        }
    }
}



