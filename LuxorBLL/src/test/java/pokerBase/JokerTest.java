package pokerBase;

import static org.junit.Assert.*;

import org.junit.Test;

public class JokerTest {
	
	@Test
	public final void testJoker() {
		Joker j1 = new Joker();
		assertEquals(j1.imageNum, 53);
		Joker j2 = new Joker();
		assertEquals(j2.imageNum, 54);
	}

	@Test
	public final void testJokerJoker() {
		Joker j1 = new Joker();
		Joker j2 = new Joker(j1);
		assertNotEquals(j1, j2);
		assertEquals(j1.getImageNum(), j2.getImageNum());
	}

	@Test
	public final void jokerWild() {
		Joker j = new Joker();
		assertEquals(j.isWild(), true);
	}
}
