package project2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jdk.jfr.Timestamp;

/**
 * Unit tests for RatingSummary class
 * 
 * @author Himaja
 * @author Scott
 * @author Daniel
 */

class RatingSummaryTest {

	public RatingSummary rs;

	@BeforeEach
	public void setup() {
		rs = new RatingSummary("A1EE2E3N7PW666", 2);
	}

	// From class RatingSummary
	@Test
	void testRatingSummaryStringLongListOfFloat() {
		String actualOutput = "A1EE2E3N7PW666,2,2.0,2.0,2.0,2.0\n";
		rs.setList(2, 2, 2, 2);
		Assertions.assertTrue(actualOutput.equals(rs.toString()));
	}

	@Test
	void testRatingSummaryStringLongFloat() {
		String output = "A00019103H5DUGXXX2UPR, 1, 4.0, 1.0, 3.0, 0\n";
		rs.setList(4, 1, 3, 0);
		Assertions.assertFalse(output.equals(rs.toString()));
	}

	// From class AbstractRatingSummary
	@Test
	void testSetDegreeLong() {
		rs.setDegree(3);
		Assertions.assertEquals(3, rs.getDegree());
	}

	@Test
	void testSetListFloat() {
		List<Float> actual = Arrays.asList(2.0f, 2.0f, 2.0f, 2.0f);
		rs.setList(2, 2, 2, 2);

		Assertions.assertTrue(actual.equals(rs.getList()));

	}

	@Test
	void testAvgScore() {
		rs.setList(4, 1, 4, 0);
		Float avgScore = rs.getList().get(1) - rs.getList().get(3);
		assertEquals(1, avgScore);
	}

	@Test
	void testStDevScore() {
		rs.setList(5, 1, 3, 0);
		Float stDevScore = rs.getList().get(0) - rs.getList().get(2);
		assertEquals(2, stDevScore);
	}

	@Test
	void testCalculateSD() {
		List<? extends Number> nums = Arrays.asList(1, 2, 3, 4);
		assertEquals(1.2247449159622192, rs.calculateSD(nums, 2));
	}

	@Test
	void testCreateList() {
		List<Float> test = rs.createList(5.0f, 4.0f, 3.0f, 1.2f);
		assertEquals(5.0f, test.get(0));
		assertEquals(4.0f, test.get(1));
		assertEquals(3.0f, test.get(2));
		assertEquals(1.2f, test.get(3));
	}

	@Test
	void testMultipleListMutations() {
		// Using arbitrary values
		rs.setList(9999, 9999, 9999, 9999);
		rs.setList(-9999, -9999, -9999, -9999);
		rs.setList(3, 7, 2, 2);
		rs.setList(-22, 2, 5, 2);
		rs.setList(9999, 1, 2, 2);
		List<Float> test = rs.getList();
		assertEquals(9999, test.get(0));
	}

	@Test
	void testSortStats() {
		rs.setList(3, 4, 1, 2);
		assertEquals(4, rs.sortStats());
	}
	
	@Test
	void testSortStats2() {
		rs.setList(1, 1, 1, 1);
		assertEquals(1, rs.sortStats());
	}

	@Test
	void testAvgScoreWithMultipleMutations() {
		rs.setList(4, 1, 4, 0);
		rs.setList(2, 1, 1, 1);
		rs.setList(5, 1, 10, 0);
		rs.setList(2, 1, 3, 11);
		Float avgScore = rs.getList().get(1) - rs.getList().get(3);
		assertEquals(-10, avgScore);
	}

}
