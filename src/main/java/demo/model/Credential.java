package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity(name = "Credential")
@Table(name = "credentials", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }) })
public class Credential {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(mappedBy = "credential")
	private Client client;

	private String login;

	private String password;

	private String role;

	public Credential() {
		super();
	}

	public Credential(Long id, Client client, String login, String password, String role) {
		super();
		setId(id);
		setClient(client);
		setLogin(login);
		setPassword(password);
		setRole(role);
	}

	public Credential(String login, String password, String role) {
		setId(id);
		setLogin(login);
		setPassword(password);
		setRole(role);
	}

	public void setAllData(String login, String password, String role) {
		setLogin(login);
		setPassword(password);
		setRole(role);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		String encoded = new BCryptPasswordEncoder().encode(password);
		this.password = encoded;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getIdClient() {
		return client.getId();
	}

	@Override
	public String toString() {
		return login + " : " + password + ", role=" + role;
	}

	public void setAllData(Long idLogin, String login, String password, String role) {
		setId(idLogin);
		setAllData(login, password, role);

	}

}
