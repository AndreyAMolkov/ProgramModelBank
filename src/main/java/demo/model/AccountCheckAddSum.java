package demo.model;

import org.springframework.beans.factory.annotation.Autowired;

import demo.dao.Dao;

public class AccountCheckAddSum {

	private long idAccountTo;
	private long idClientTo;
	private String nameOfClientTo;
	private long amountClientAccountTo;
	private long idAccountFrom;
	private long idClientFrom;
	private String nameOfClientFrom;
	private long amountClientAccountFrom;
	private Boolean denied;

	@Autowired
	private Dao dao;

	public AccountCheckAddSum() {
		this.denied = false;
	}

	public long getIdAccountTo() {
		return idAccountTo;
	}

	public void setIdAccountTo(long idAccountTo) {

		this.idAccountTo = idAccountTo;
		Account account = dao.getAccountById(idAccountTo);
		String fullName = "NOT FOUND";
		try {
			Data data = dao.getDataById(account.getData());
			fullName = data.getFullName();
			if (fullName == null || fullName.isEmpty()) {
				fullName = "NOT FOUND";
				setDenied(false);
			}
		} catch (NullPointerException e) {
			setDenied(false);
		}
		setNameOfClientTo(fullName);
	}

	public long getIdClientTo() {
		return idClientTo;
	}

	public void setIdClientTo(long idClientTo) {
		this.idClientTo = idClientTo;
	}

	public String getNameOfClientTo() {
		return nameOfClientTo;
	}

	public void setNameOfClientTo(String nameOfClientTo) {
		this.nameOfClientTo = nameOfClientTo;
	}

	public long getIdAccountFrom() {
		return idAccountFrom;
	}

	public void setIdAccountFrom(long idAccountFrom) {
		this.idAccountFrom = idAccountFrom;
	}

	public long getIdClientFrom() {
		return idClientFrom;
	}

	public void setIdClientFrom(long idClientFrom) {
		this.idClientFrom = idClientFrom;
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
