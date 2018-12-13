package demo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import demo.constant.Constants;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

	@InjectMocks
	Account account;
	@Mock
	private Story story1;
	@Mock
	private Story story2;
	@Mock
	private Story story3;
	@Mock
	private Story story4;
	@Mock
	private Story story5;

	@Test
	public void sortHistoriesLastDateFirst_NormalCaseDateIntoStories_SortLastDateFirst() {

		List<Story> historiesMock = new ArrayList<>(5);
		historiesMock.add(story1);
		historiesMock.add(story2);
		historiesMock.add(story3);
		historiesMock.add(story4);
		historiesMock.add(story5);
		LocalDateTime date1 = LocalDateTime.now().minusDays(1);
		LocalDateTime date2 = LocalDateTime.now().minusDays(2);
		LocalDateTime date3 = LocalDateTime.now().minusDays(3);
		LocalDateTime date4 = LocalDateTime.now().minusDays(4);
		LocalDateTime date5 = LocalDateTime.now().minusDays(5);
		when(story1.getDate()).thenReturn(date1);
		when(story2.getDate()).thenReturn(date2);
		when(story3.getDate()).thenReturn(date3);
		when(story4.getDate()).thenReturn(date4);
		when(story5.getDate()).thenReturn(date5);

		List<Story> resultStoryList = account.sortHistoriesLastDateFirst(historiesMock);

		assertEquals(story1, resultStoryList.get(0));
		assertEquals(story2, resultStoryList.get(1));
		assertEquals(story3, resultStoryList.get(2));
		assertEquals(story4, resultStoryList.get(3));
		assertEquals(story5, resultStoryList.get(4));
	}

	@Test
	public void setHistories_NormalInput_10L() {
		Long value = 10L;
		when(story1.getSum()).thenReturn(value);
		when(story1.getOperation()).thenReturn(Constants.INPUT_AMOUNT);

		account.setHistories(story1);

		assertTrue(value.equals(account.getSum()));
		;
	}

	@Test
	public void setHistories_NormalOutput_20minus10() {
		Long value1 = 20L;
		Long value2 = -10L;
		Long expected = value1 + value2;
		when(story1.getSum()).thenReturn(value1);
		when(story1.getOperation()).thenReturn(Constants.INPUT_AMOUNT);
		when(story2.getSum()).thenReturn(value2);
		when(story2.getOperation()).thenReturn(Constants.OUTPUT_AMOUNT);

		account.setHistories(story1);
		account.setHistories(story2);

		assertTrue(expected.equals(account.getSum()));
	}

	@Test
	public void setHistories_VolatileCountHistories_OneStory() {
		Long value1 = 20L;
		Long value2 = 10L;
		when(story1.getSum()).thenReturn(value1);
		when(story1.getOperation()).thenReturn(Constants.INPUT_AMOUNT);
		when(story2.getSum()).thenReturn(value2);
		when(story2.getOperation()).thenReturn(Constants.OUTPUT_AMOUNT);

		account.setHistories(story1);
		account.setHistories(story2);

		assertEquals("1", account.getHistoriesSize());
	}

}
