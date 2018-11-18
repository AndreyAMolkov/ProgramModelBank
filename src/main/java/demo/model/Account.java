package demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity(name="Account")
@Table(name="accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long number;
	
	
	@OneToOne
	@JoinColumn(name ="data_id" )
	private Data data;
	
	private Long sum;
	
	@Autowired
	@javax.persistence.Transient
	private Story story;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "accounts_id")
	private List<Story> histories;
	
	private Account(Long numberOfaccount, Client client, Long sum, List<Story> histories) {
		super();
		this.number = numberOfaccount;
		this.sum = sum;
		setHistories(histories); 
	}

	
	public Account() {
		setHistories(histories); 
		this.sum=0L;
	}


	private Account(long inputSum) {
		setSum(inputSum);
	}


	private String getFullName() {
		String result = data.getFullName();
		return result;
	}


	private Data getData() {
		return data;
	}


	private void setData(Data data) {
		this.data = data;
	}


	public Long getNumber() {
		return number;
	}

	public void setNumber(Long numberOfaccount) {
		this.number = numberOfaccount;
	}

	public Long getSum() {
		if(sum==null)
			this.sum=0L;
		
		return sum;
	}

	private void setSum(Long sum) {
		
		this.sum = getSum()+ sum;
	}

	
	public String getHistoriesSize() {
		if(histories == null) {
			return "empty";
		}
		int result = getHistories().size();
		return String.valueOf(result);
	}
	
	public List<Story> getHistories() {
		if(histories == null) {
			this.histories = new ArrayList<Story>();
		}
		return histories;
	}

	public void setHistories(List<Story> histories) {
		if(this.histories == null)
			this.histories =  new ArrayList<Story>(5);
			
		if(histories == null) {
			histories = new ArrayList<Story>(5);
		}else {
			histories.
			stream().
			forEach(e->setHistories(e));
		}
		
		this.histories=histories;

	}
	
	private boolean validateStory(Story story) {
		Long sumOfStory = story.getSum();
		Long sumOfAccount= getSum();
		if(story.getOperation().toLowerCase().equals("output")) {
			
			Long result= sumOfAccount - sumOfStory;
			if(result < 0)
				return false;
		}
		return true;
	}
	
	public void setHistories(Story story) {

		String nameMethod = "setHistories";
		
		Long sumOfStory = story.getSum();
		Long sumOfAccount= getSum();
 
		if(validateStory(story)) {
			if(story.getOperation().toLowerCase().equals("output")) {
				setSum(sumOfStory);
			}
			else {
				setSum(sumOfStory);
			}
		}else {
				
				new Exception("ERROR " + nameMethod + " negative story, result " 
							+ story.getOperation() + " = " + (sumOfAccount - sumOfStory));
		}
				
		
		story.setAccount(getNumber());
		this.histories.add(story);
	}

	public Story getNewStory() {
		
		
		return story;
		
	}
	
	@Override
	public String toString() {
		return "[" +"number=" + number + ", sum=" + sum + "]";
				
	}
	
	
	
}
