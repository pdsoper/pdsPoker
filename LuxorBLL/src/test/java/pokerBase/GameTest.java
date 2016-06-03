package pokerBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pokerEnums.GameOption;

public class GameTest {

	@Test
	public void testGetName() {
		assertEquals(Game.FiveCardStud().getName(), "Five Card Stud");
	}

	@Test
	public void testGetDeal() {
		ArrayList<CardDraw> cdal = Game.FiveCardStud().getDeal(1);
		assertEquals(cdal.size(), 1);
	}

	@Test
	public void testNDeals() {
		assertEquals(GameOption.FiveCardStud.getGame().nDeals(), 4);
	}

	@Test
	public final void testToString() {
		System.out.println(Game.FiveCardDraw());
		System.out.println(Game.FiveCardStud());
	}

}
