package model.entity;

public class User {
	private String email;
	private String senhaHash;
	private String token2FA;
	
	
	public User(String email, String senhaHash, String token2fa) {

		this.email = email;
		this.senhaHash = senhaHash;
		token2FA = token2fa;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenhaHash() {
		return senhaHash;
	}
	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}
	public String getToken2FA() {
		return token2FA;
	}
	public void setToken2FA(String token2fa) {
		token2FA = token2fa;
	}
}
