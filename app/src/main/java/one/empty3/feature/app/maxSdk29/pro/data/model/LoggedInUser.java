package one.empty3.feature.app.maxSdk29.pro.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {
    private static LoggedInUser currentUser;
    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static void setCurrentUser(LoggedInUser currentUser) {
        LoggedInUser.currentUser = currentUser;
    }

    public static LoggedInUser getCurrentUser() {
        return currentUser;
    }
}