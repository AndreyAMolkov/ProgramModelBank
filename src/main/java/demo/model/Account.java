package demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

@Entity(name = "Account")
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long number;

	private Long data;
	
	@Transient
	private List<Story> sortList;
	
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
		this.sum = 0L;
	}

	private Account(long inputSum) {
		setSum(inputSum);
	}

	public Long getData() {
		String nameMethod = "getData";
		return data;
	}

	public void setData(Long data) {
		String nameMethod = "setData";
		this.data = data;
	}

	public Long getNumber() {
		String nameMethod = "getNumber";
		return number;
	}

	public void setNumber(Long numberOfaccount) {
		String nameMethod = "setNumber";
		this.number = numberOfaccount;
	}

	public Long getSum() {
		String nameMethod = "getSum";
		if (sum == null)
			this.sum = 0L;

		return sum;
	}

	private void setSum(Long sum) {
		String nameMethod = "setSum";
		this.sum = getSum() + sum;
	}

	public String getHistoriesSize() {
		String nameMethod = "getHistoriesSize";
		if (histories == null) {
			return "empty";
		}
		int result = getHistories().size();
		return String.valueOf(result);
	}

	public List<Story> getHistories() {
		String nameMethod = "getHistories";
		if (histories == null) {
			this.histories = new ArrayList<Story>();
		}

		return histories;
	}

	public List<Story> getSortHistories() {
		String nameMethod = "getSortHistories";
		return sortHistoriesLastDateFirst(getCopy());
	}

	private List<Story> getCopy() {
		String nameMethod = "getCopy";
		return getHistories().stream()
				.map(Story::clone)
				.collect(Collectors.toList());

	}

	public List<Story> sortHistoriesLastDateFirst(List<Story> historiesOld) {
		String nameMethod = "sortHistoriesLastDateFirst";
		Collections.sort(historiesOld, new Comparator<Story>() {
			public int compare(Story s1, Story s2) {
				return s2.getDate().compareTo(s1.getDate());
			}
		});
		return historiesOld;
	}

	public void setHistories(List<Story> historiesNew) {
		String nameMethod = "setHistories";
		if (this.histories == null)
			this.histories = new ArrayList<Story>(5);

		if (historiesNew == null) {
			historiesNew = new ArrayList<Story>(5);
		}

		historiesNew.stream().forEach(e -> setHistories(e));

	}

	private boolean validateStory(Story story) {
		String nameMethod = "validateStory";
		Long sumOfStory = story.getSum();
		Long sumOfAccount = getSum();
		if (("output").equals(story.getOperation().toLowerCase())) {

			Long result = sumOfAccount - sumOfStory;
			if (result < 0)
				return false;
		}
		return true;
	}

	public void setHistories(Story story) {
		String nameMethod = "setHistories";
		Long sumOfStory = story.getSum();
		Long sumOfAccount = getSum();

		if (validateStory(story)) {

			setSum(sumOfStory);

		} else {

			new Exception("ERROR " + nameMethod + " negative story, result " + story.getOperation() + " = "
					+ (sumOfAccount - sumOfStory));
		}

		story.setAccount(getNumber());
		this.histories.add(story);
	}


	@Override
	public String toString() {
		String nameMethod = "toString";
		return "[" + "number=" + number + ", sum=" + sum + "]";

	}

}
