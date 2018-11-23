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
		String nameMethod = "getIdAccountTo";
		return IdAccountTo;
	}

	public void setIdAccountTo(long idAccountTo) {
		String nameMethod = "setIdAccountTo";
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
		String nameMethod = "getIdClientTo";
		return IdClientTo;
	}

	public void setIdClientTo(long idClientTo) {
		String nameMethod = "setIdClientTo";
		IdClientTo = idClientTo;
	}

	public String getNameOfClientTo() {
		String nameMethod = "getNameOfClientTo";
		return nameOfClientTo;
	}

	public void setNameOfClientTo(String nameOfClientTo) {
		String nameMethod = "setNameOfClientTo";
		this.nameOfClientTo = nameOfClientTo;
	}

	public long getIdAccountFrom() {
		String nameMethod = "getIdAccountFrom";
		return IdAccountFrom;
	}

	public void setIdAccountFrom(long idAccountFrom) {
		String nameMethod = "setIdAccountFrom";
		IdAccountFrom = idAccountFrom;
	}

	public long getIdClientFrom() {
		String nameMethod = "getIdClientFrom";
		return IdClientFrom;
	}

	public void setIdClientFrom(long idClientFrom) {
		String nameMethod = "setIdClientFrom";
		IdClientFrom = idClientFrom;
	}

	public String getNameOfClientFrom() {
		String nameMethod = "getNameOfClientFrom";
		return nameOfClientFrom;
	}

	public void setNameOfClientFrom(String nameOfClientFrom) {
		String nameMethod = "setNameOfClientFrom";
		this.nameOfClientFrom = nameOfClientFrom;
	}

	public long getAmountClientAccountFrom() {
		String nameMethod = "getAmountClientAccountFrom";
		return amountClientAccountFrom;
	}

	public void setAmountClientAccountFrom(long amountClientAccountFrom) {
		String nameMethod = "setAmountClientAccountFrom";
		this.amountClientAccountFrom = amountClientAccountFrom;
	}

	public long getAmountClientAccountTo() {
		String nameMethod = "getAmountClientAccountTo";
		return amountClientAccountTo;
	}

	public void setAmountClientAccountTo(long amountClientAccountTo) {
		String nameMethod = "setAmountClientAccountTo";
		this.amountClientAccountTo = amountClientAccountTo;
	}

	public Boolean getDenied() {
		String nameMethod = "getDenied";
		return denied;
	}

	public void setDenied(Boolean denied) {
		String nameMethod = "setDenied";
		this.denied = denied;
	}

}
