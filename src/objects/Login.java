package objects;

public class Login {
	public String name;
	public String password;

	// Constructor method Login
	public Login(String name, String password) {
		this.name = name;
		this.password = password;
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
