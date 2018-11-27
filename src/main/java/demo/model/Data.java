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
		if (this == o)
			return true;
		if (!(o instanceof Data))
			return false;
		return id != null && id.equals(((Data) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	public void setAllData(String firstName, String secondName, String lastName) {
		setFirstName(firstName);
		setSecondName(secondName);
		setLastName(lastName);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return lastName + " " + firstName + " " + secondName;
	}

	public String getFullName() {
		return lastName + " " + firstName + " " + secondName;
	}

	public void setAllData(Long idData, String firsName, String secondName, String lastName) {
		setAllData(firsName, secondName, lastName);
		setId(idData);

	}
}
