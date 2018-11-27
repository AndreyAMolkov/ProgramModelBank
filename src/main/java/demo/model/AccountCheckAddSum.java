package demo.model;

import org.springframework.beans.factory.annotation.Autowired;

import demo.dao.Dao;

public class AccountCheckAddSum {

	private long IdAccountTo;
	private long IdClientTo;
	private String nameOfClientTo;
	private long amountClientAccountTo;
	private long IdAccountFrom;
	private long IdClientFrom;
	private String nameOfClientFrom;
	private long amountClientAccountFrom;
	private Boolean denied;

	@Autowired
	private Dao<?> dao;

	public AccountCheckAddSum() {
		this.denied = false;
	}

	public long getIdAccountTo() {
		return IdAccountTo;
	}

	public void setIdAccountTo(long idAccountTo) {

		IdAccountTo = idAccountTo;
		Account account = (Account) dao.getById(idAccountTo, Account.class);

		String fullName = "NOT FOUND";
		try {
			Data data = ((Data) dao.getById(account.getData(), Data.class));

			fullName = data.getFullName();

			if (fullName.isEmpty() || fullName == null) {
				fullName = "NOT FOUND";
				setDenied(false);
			}
		} catch (NullPointerException e) {
			setDenied(false);
		}

		setNameOfClientTo(fullName);

	}

	public long getIdClientTo() {
		return IdClientTo;
	}

	public void setIdClientTo(long idClientTo) {
		IdClientTo = idClientTo;
	}

	public String getNameOfClientTo() {
		return nameOfClientTo;
	}

	public void setNameOfClientTo(String nameOfClientTo) {
		this.nameOfClientTo = nameOfClientTo;
	}

	public long getIdAccountFrom() {
		return IdAccountFrom;
	}

	public void setIdAccountFrom(long idAccountFrom) {
		IdAccountFrom = idAccountFrom;
	}

	public long getIdClientFrom() {
		return IdClientFrom;
	}

	public void setIdClientFrom(long idClientFrom) {
		IdClientFrom = idClientFrom;
	}

	public String getNameOfClientFrom() {
		return nameOfClientFrom;
	}

	public void setNameOfClientFrom(String nameOfClientFrom) {
		this.nameOfClientFrom = nameOfClientFrom;
	}

	public long getAmountClientAccountFrom() {
		return amountClientAccountFrom;
	}

	public void setAmountClientAccountFrom(long amountClientAccountFrom) {
		this.amountClientAccountFrom = amountClientAccountFrom;
	}

	public long getAmountClientAccountTo() {
		return amountClientAccountTo;
	}

	public void setAmountClientAccountTo(long amountClientAccountTo) {
		this.amountClientAccountTo = amountClientAccountTo;
	}

	public Boolean getDenied() {
		return denied;
	}

	public void setDenied(Boolean denied) {
		this.denied = denied;
	}

}
