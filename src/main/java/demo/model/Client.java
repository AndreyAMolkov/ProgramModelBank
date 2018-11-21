package demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity(name = "Client")
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "data_id")
	private Data data;

	@Autowired
	@javax.persistence.Transient
	private Story story;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "loginOfClient_id")
	private Login login;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private List<Account> accounts;

	private String nameFromData;

	public Client(Login login, Data data) {
		setAccounts(new ArrayList<Account>());
		setData(data);
		setLogin(login);
	}

	public Client() {
//		this.accounts= new TreeSet<Account>();
	}

	public Client(Long id, Data data, Login login) {
		super();
		this.id = id;
		this.data = data;
		this.login = login;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Data getData() {
		if (data == null) {
			setData(new Data());
		}
		return this.data;
	}

	public void setData(Data data) {
		if (data == null) {
			data = new Data();
		}
		this.data = data;
		data.setClient(this);
		this.nameFromData = data.getFirstName();
	}

	public Login getLogin() {
		if (login == null) {
			setLogin(new Login());
		}
		return login;
	}

	public void setLogin(Login login) {
		if (login == null) {
			login = new Login();
		}
		this.login = login;
		login.setClient(this);
	}

	public List<Account> getAccounts() {
		if (accounts == null) {
			this.accounts = new ArrayList<Account>();
		}
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		if (accounts == null) {
			accounts = new ArrayList<Account>();
		}
		accounts.forEach(a -> a.setData(getData().getId()));
		this.accounts = accounts;
	}

	public void setAccounts(Account account) {
		if (account == null) {
			account = new Account();
		}
		account.setData(getData().getId());
		getAccounts().add(account);
	}

	public String getNameFromData() {
		return nameFromData;
	}

	public void setNameFromData(String nameFromData) {
		this.nameFromData = nameFromData;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", dataOfClient=" + data + ", loginOfClient=" + login + ", accounts=" + accounts
				+ "]";
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

}
