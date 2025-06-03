package model.entity;

public class PasswordEntry {

	private int id;
    private String serviceName;
    private String username;
    private String passwordEncrypted;
    private int userId;

    public PasswordEntry() {}

    public PasswordEntry(String serviceName, String username, String password, int userId) {
        this.serviceName = serviceName;
        this.username = username;
        this.passwordEncrypted = password;
        this.userId = userId;
    }

	public PasswordEntry(String serviceName, String username, String passwordEncrypted) {
		this.serviceName = serviceName;
		this.username = username;
		this.passwordEncrypted = passwordEncrypted;
	}

	public String toString() {
        return "PasswordEntry [id=" + id + 
               ", serviceName=" + serviceName + 
               ", username=" + username + 
               ", passwordEncrypted=" + passwordEncrypted +
               ", userId= "+ userId + "]";
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordEncrypted() {
		return passwordEncrypted;
	}

	public void setPasswordEncrypted(String passwordEncrypted) {
		this.passwordEncrypted = passwordEncrypted;
	}
	
	public int getUserId() {
	    return userId;
	}

	public void setUserId(int userId) {
	    this.userId = userId;
	}
    
}
