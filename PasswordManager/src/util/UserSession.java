package util;

public class UserSession {
	private static volatile UserSession instance;
    private String userId;
    private String userEmail;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            synchronized (UserSession.class) {
                if (instance == null) {
                    instance = new UserSession();
                }
            }
        }
        return instance;
    }

    public void initSession(String userId, String userEmail) {
        SecurityUtils.validateRequiredFields(userId, userEmail);
        SecurityUtils.validateEmail(userEmail);
        
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void clearSession() {
        this.userId = null;
        this.userEmail = null;
    }

    public boolean isValid() {
        try {
            SecurityUtils.validateRequiredFields(userId, userEmail);
            SecurityUtils.validateEmail(userEmail);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}