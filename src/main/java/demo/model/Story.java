package demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Entity(name = "Story")
@Table(name = "stories")
@Scope(value = "prototype")
public class Story implements Cloneable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long account;
	private LocalDateTime date;
	private String operation;
	private String place;
	private Long sum;

	private Story(Long id, Long account, LocalDateTime date, String operation, String place, Long sum) {
		this.id = id;
		this.account = account;
		setDate(date);
		this.operation = operation;
		this.place = place;
		this.sum = sum;

	}

	public Story() {
		// TODO Auto-generated constructor stub
	}

	public void input(String place, Long sum) {
		String nameMethod = "input";
		setDate(date);
		setOperation("Input");
		setPlace(place);
		setSum(sum);
	}

	public void output(String place, Long sum) {
		String nameMethod = "output";
		setDate(date);
		setOperation("Output");
		setPlace(place);
		setSum(sum);
	}

	public Long getId() {
		String nameMethod = "getId";
		return id;
	}

	private void setId(Long id) {
		String nameMethod = "setId";
		this.id = id;
	}

	public Long getAccount() {
		String nameMethod = "getAccount";
		return account;
	}

	protected void setAccount(Long account) {
		String nameMethod = "setAccount";
		this.account = account;
	}

	public LocalDateTime getDate() {
		String nameMethod = "getDate";
		return date;
	}

	private void setDate(LocalDateTime date) {
		String nameMethod = "setDate";
		if (date == null) {
			date = LocalDateTime.now();
		}
		this.date = date;
	}

	public String getOperation() {
		String nameMethod = "getOperation";
		return operation;
	}

	private void setOperation(String operation) {
		String nameMethod = "setOperation";
		this.operation = operation;
	}

	public String getPlace() {
		String nameMethod = "getPlace";
		return place;
	}

	private void setPlace(String place) {
		String nameMethod = "setPlace";
		this.place = place;
	}

	public Long getSum() {
		String nameMethod = "getSum";
		if (this.sum == null)
			this.sum = 0L;
		return sum;
	}

	private void setSum(Long sum) {
		String nameMethod = "setSum";
		this.sum = getSum() + sum;
	}

	@Override
	public String toString() {
		String nameMethod = "toString";
		return "StoryOfAccount [id=" + id + ", account=" + account + ", date=" + date + " " + operation + " " + place
				+ " " + sum + "]";
	}
	@Override
	protected Story clone() {
		String nameMethod = "clone";
		return new Story(id, account, date, operation, place,sum);

	}
}
