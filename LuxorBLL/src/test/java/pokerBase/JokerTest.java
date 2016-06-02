package pokerBase;

import static org.junit.Assert.*;

import org.junit.Test;

import pokerEnums.Rank;

public class JokerTest {
	
	@Test
	public final void JokerTest() {
		Joker j1 = new Joker();
		Joker j2 = new Joker();
		assertTrue(j1.getRank() == null);
		assertTrue(j1.getFaceRank() == null);
		assertTrue(j1.getSuit() == null);
		assertTrue(j1.getFaceSuit() == null);
		assertTrue(j1.imageNum == 53 && j2.imageNum == 54
				|| j1.imageNum == 54 && j2.imageNum == 53);
	}

	@Test
	public final void JokerJokerTest() {
		Joker j1 = new Joker();
		Joker j2 = new Joker(j1);
		assertNotEquals(j1, j2);
		assertEquals(j1.getImageNum(), j2.getImageNum());
	}

	@Test
	public final void isWildTest() {
		Joker j = new Joker();
		assertEquals(j.isWild(), true);
	}
	
	@Test
	public final void toStringTest() {
		Joker j = new Joker();
		System.out.println("Joker : " + j);
		assertTrue(true);
	}
}
