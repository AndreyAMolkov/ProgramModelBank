package demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "Data")
@Table(name = "datas")
public class Data {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;

	private String firstName;
	private String secondName;
	private String lastName;

	public Data() {
		;// empty
	}

	public Data(Long id, Client client, String firstName, String secondName, String lastName) {
		super();
		setId(id);
		setClient(client);
		setFirstName(firstName);
		setSecondName(secondName);
		setLastName(lastName);
	}

	public Data(String firstName, String secondName, String lastName) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object o) {
		String nameMethod = "equals";
		if (this == o)
			return true;
		if (!(o instanceof Data))
			return false;
		return id != null && id.equals(((Data) o).id);
	}

	@Override
	public int hashCode() {
		String nameMethod = "hashCode";
		return 31;
	}

	public void setAllData(String firstName, String secondName, String lastName) {
		String nameMethod = "setAllData";
		setFirstName(firstName);
		setSecondName(secondName);
		setLastName(lastName);
	}

	public Long getId() {
		String nameMethod = "getId";
		return id;
	}

	public void setId(Long id) {
		String nameMethod = "setId";
		this.id = id;
	}

	public String getFirstName() {
		String nameMethod = "getFirstName";
		return firstName;
	}

	public Client getClient() {
		String nameMethod = "getClient";
		return client;
	}

	public void setClient(Client client) {
		String nameMethod = "setClient";
		this.client = client;
	}

	public void setFirstName(String firstName) {
		String nameMethod = "setFirstName";
		this.firstName = firstName;
	}

	public String getSecondName() {
		String nameMethod = "getSecondName";
		return secondName;
	}

	public void setSecondName(String secondName) {
		String nameMethod = "setSecondName";
		this.secondName = secondName;
	}

	public String getLastName() {
		String nameMethod = "getLastName";
		return lastName;
	}

	public void setLastName(String lastName) {
		String nameMethod = "setLastName";
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		String nameMethod = "toString";
		return lastName + " " + firstName + " " + secondName;
	}

	public String getFullName() {
		String nameMethod = "getFullName";
		return lastName + " " + firstName + " " + secondName;
	}

	public void setAllData(Long idData, String firsName, String secondName, String lastName) {
		String nameMethod = "setAllData";
		setAllData(firsName, secondName, lastName);
		setId(idData);
		
	}
}
