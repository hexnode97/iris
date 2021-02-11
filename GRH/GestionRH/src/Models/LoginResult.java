package Models;

public class LoginResult {
	private boolean status;
	private String userType;
	public LoginResult(boolean status, String userType) {
		this.setStatus(status);
		this.setUserType(userType);
	}
	public LoginResult() {
		
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
