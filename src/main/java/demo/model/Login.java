package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity(name = "Login")
@Table(name = "logins", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }) })
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(mappedBy = "login")
	private Client client;

	private String login;

	private String password;

	private String role;

	public Login() {
		super();
	}

	public Login(Long id, Client client, String login, String password, String role) {
		super();
		setId(id);
		setClient(client);
		setLogin(login);
		setPassword(password);
		setRole(role);
	}

	public Login(String login, String password, String role) {
		setId(id);
		setLogin(login);
		setPassword(password);
		setRole(role);
	}

	@Override
	public boolean equals(Object o) {
		String nameMethod = "equals";
		if (this == o)
			return true;
		if (!(o instanceof Login))
			return false;
		return id != null && id.equals(((Login) o).id);
	}

	public void setAllData(String login, String password, String role) {
		String nameMethod = "setAllData";
		setLogin(login);
		setPassword(password);
		setRole(role);
	}

	public Long getId() {
		String nameMethod = "getId";
		return id;
	}

	public void setId(Long id) {
		String nameMethod = "setId";
		this.id = id;
	}

	public Client getClient() {
		String nameMethod = "getClient";
		return client;
	}

	public void setClient(Client client) {
		String nameMethod = "setClient";
		this.client = client;
	}

	public String getLogin() {
		String nameMethod = "getLogin";
		return login;
	}

	public void setLogin(String login) {
		String nameMethod = "setLogin";
		this.login = login;
	}

	public String getPassword() {
		String nameMethod = "getPassword";
		return password;
	}

	public void setPassword(String password) {
		String nameMethod = "setPassword";
		String encoded = new BCryptPasswordEncoder().encode(password);
		this.password = encoded;
	}

	public void setPasswordEmpty() {
		String nameMethod = "setPassword";
		this.password = "";
	}

	public String getRole() {
		String nameMethod = "getRole";
		return role;
	}

	public void setRole(String role) {
		String nameMethod = "setRole";
		this.role = role;
	}

	public Long getIdClient() {
		String nameMethod = "getIdClient";
		return client.getId();
	}

	@Override
	public String toString() {
		String nameMethod = "toString";
		return login + " : " + password + ", role=" + role;
	}

	public void setAllData(Long idLogin, String login, String password, String role) {
		String nameMethod = "setAllData";
		setId(idLogin);
		setAllData(login, password, role);

	}

}
