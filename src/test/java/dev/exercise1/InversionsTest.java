package dev.exercise1;

import static org.junit.Assert.*;

import org.junit.Test;

import dev.exercise1.Inversions;

public class InversionsTest {

	@Test
	public void test() {
		assertEquals(0, new Inversions().findInversions(new int[]{2, 3, 5, 7, 8, 9, 10}));
		assertEquals(1, new Inversions().findInversions(new int[]{2, 5, 3, 7, 8, 9, 10}));
		assertEquals(8, new Inversions().findInversions(new int[]{2, 5, 3, 7, 8, 9, 10, 1}));
	}

}
