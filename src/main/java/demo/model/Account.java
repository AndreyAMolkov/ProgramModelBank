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

	public Account() {
		setHistories(histories);
		this.sum = 0L;
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long numberOfaccount) {
		this.number = numberOfaccount;
	}

	public Long getSum() {
		if (sum == null)
			this.sum = 0L;

		return sum;
	}

	private void setSum(Long sum) {
		this.sum = getSum() + sum;
	}

	public String getHistoriesSize() {
		if (histories == null) {
			return "empty";
		}
		int result = getHistories().size();
		return String.valueOf(result);
	}

	public List<Story> getHistories() {
		if (histories == null) {
			this.histories = new ArrayList<>();
		}

		return histories;
	}

	public List<Story> getSortHistories() {
		return sortHistoriesLastDateFirst(getCopy());
	}

	private List<Story> getCopy() {
		return getHistories().stream()
				.map(Story::clone)
				.collect(Collectors.toList());

	}

	public List<Story> sortHistoriesLastDateFirst(List<Story> historiesOld) {
		Collections.sort(historiesOld, new Comparator<Story>() {
			public int compare(Story s1, Story s2) {
				return s2.getDate().compareTo(s1.getDate());
			}
		});
		return historiesOld;
	}

	public void setHistories(List<Story> historiesNew) {
		if (this.histories == null)
			this.histories = new ArrayList<>(5);

		if (historiesNew == null) {
			historiesNew = new ArrayList<>(5);
		}

		historiesNew.stream().forEach(e->setHistories(e));

	}

	private boolean validateStory(Story story) {
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
		Long sumOfStory = story.getSum();

		if (validateStory(story)) {
			setSum(sumOfStory);
		} 

		story.setAccount(getNumber());
		this.histories.add(story);
	}


	@Override
	public String toString() {
		return "[" + "number=" + number + ", sum=" + sum + "]";
	}

}
