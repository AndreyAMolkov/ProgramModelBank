package demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
@Entity(name="Story")
@Table(name="stories")
@Scope(value = "prototype")
public class Story{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long account;
	private LocalDateTime date;
	private String operation;
	private String place;
	private Long sum;

	
	private Story(Long id, Long account, LocalDateTime date, String operation, String place,
			Long sum) {
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
		setDate(date);
		setOperation("Input");
		setPlace(place);
		setSum(sum);
	}
	public void output(String place, Long sum) {
		setDate(date);
		setOperation("Output");
		setPlace(place);
		setSum(sum);
	}
	public Long getId() {
		return id;
	}
	private void setId(Long id) {
		this.id = id;
	}
	public Long getAccount() {
		return account;
	}
	protected void setAccount(Long account) {
		this.account = account;
	}
	public LocalDateTime getDate() {
		return date;
	}
	private void setDate(LocalDateTime date) {
		if(date ==null) {
			date = LocalDateTime.now();
		}
		this.date = date;
	}

	public String getOperation() {
		return operation;
	}
	private void setOperation(String operation) {
		this.operation = operation;
	}
	public String getPlace() {
		return place;
	}
	private void setPlace(String place) {
		this.place = place;
	}
	public Long getSum() {
		if(this.sum==null)
			this.sum=0L;
		return sum;
	}
	private void setSum(Long sum) {
		
		this.sum = getSum() + sum;
	}

	@Override
	public String toString() {
		return "StoryOfAccount [id=" + id + ", account=" + account + ", date=" + date + 
				" " + operation + " " + place + " " + sum + "]";
	}

}
