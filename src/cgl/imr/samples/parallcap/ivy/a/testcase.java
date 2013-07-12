package cgl.imr.samples.parallcap.ivy.a;

import java.util.List;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class testcase extends TestCase {
	int[] input;
	int size;

	protected void setUp() throws Exception {
		super.setUp();
		input = new int[4];
		for (int i = 0; i < 4; i++) {
			input[i] = i;
		}
		size = input.length;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testCombGen2OutOf4()
	{
		Qns qns = new Qns();
		int[][] expect = {
				{0,1}, {0,2}, {1,2}, {0,3}, {1,3}, {2,3}
		};
		List<int[]> expectList = new ArrayList<int[]>();
		for (int[] elem : expect) {
			expectList.add(elem);
		}
		
		//List<int[]> actual = qns.combinationHelperImpl(2, 4);
//		for (int i = 0; i < 6; i++) {
//			Assert.assertArrayEquals(expectList.get(i), actual.get(i));
//		}
	}
	
	@Test
	public void test3PermGen()
	{
		Qns qns = new Qns();
		List<int[]> actual =  qns.permutationHelperImpl(5);
		actual.toArray();
	}

}
