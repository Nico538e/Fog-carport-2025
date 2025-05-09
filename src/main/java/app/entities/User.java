package app.entities;

public class User {

    private int userId;
    private String userName;
    private String userPassword;
    private String role;
    private String userEmail;
    private int userTlf;
    private boolean isPaidStatus;

    public User(int userId, String userName, String userPassword, String role, String userEmail, int userTlf, boolean isPaidStatus) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.isPaidStatus = isPaidStatus;
    }

    public User(int userTlf, String userEmail, String role, String userPassword, String userName) {
        this.userTlf = userTlf;
        this.userEmail = userEmail;
        this.role = role;
        this.userPassword = userPassword;
        this.userName = userName;
    }

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserTlf() {
        return userTlf;
    }

    public void setUserTlf(int userTlf) {
        this.userTlf = userTlf;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", role='" + role + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userTlf=" + userTlf +
                '}';
    }
}
