package demo.model;

public class SendMoneyForm {
	private Long fromAccountId;
	private Long toAccountId;
	private Long amount;

	public SendMoneyForm() {

	}

	public SendMoneyForm(Long fromAccountId, Long toAccountId, Long amount) {
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
	}

	public Long getFromAccountId() {
		String nameMethod = "getFromAccountId";
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		String nameMethod = "setFromAccountId";
		this.fromAccountId = fromAccountId;
	}

	public Long getToAccountId() {
		String nameMethod = "getToAccountId";
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		String nameMethod = "setToAccountId";
		this.toAccountId = toAccountId;
	}

	public Long getAmount() {
		String nameMethod = "getAmount";
		return amount;
	}

	public void setAmount(Long amount) {
		String nameMethod = "setAmount";
		this.amount = amount;
	}

}
