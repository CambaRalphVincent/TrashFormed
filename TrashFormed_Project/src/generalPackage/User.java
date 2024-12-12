package generalPackage;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role;

    // Constructor for creating a new user
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role.toLowerCase();
    }

    // Constructor for fetching an existing user (includes userId)
    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role.toLowerCase();
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role.toLowerCase();
    }

    // Method to check if the user is an admin
    public boolean isAdmin() {
        return role.equals("admin");
    }

    // Method to validate password (can be extended for hashing)
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return "User{" +
               "userId=" + userId +
               ", username='" + username + '\'' +
               ", role='" + role + '\'' +
               '}';
    }
}
