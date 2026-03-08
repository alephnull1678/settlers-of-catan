package catansim.Task1;

import org.junit.Test;
import static org.junit.Assert.*;

import catansim.*;

// Behaviors to be tested:
//		1: Regular Dice Range
//		2: Multi-Dice Range
//		3: 2 Regular Dice Range

public class DiceTest {
	
	// 1: Test that a regular die stays within the intended range
	@Test
	public void testRegularDiceRangeWithinBounds() {
		RegularDice dice = new RegularDice();
		
		// Roll the dice 2000 times to make sure roll stays in bounds
		for (int i = 0; i < 2000; i++) {
			int roll = dice.roll();
			
			assertTrue(roll >= 1);
			assertTrue(roll <= 6);
		}
	}
	// 2: Test that a multi-die stays within the intended range
	@Test
	public void testMultiDiceRangeWithinBounds() {
		MultiDice dice = new MultiDice();
		
		// Add dice to total dice count
		dice.addDice(() -> 3);
		dice.addDice(() -> 4);
		
		// Check that the amount of dice added is the amount of rolled
		assertEquals(7, dice.roll());
	}
	
	// 3: 
	@Test
	public void testTwoRegularDiceRangeWithinBounds() {
		MultiDice dice = new MultiDice();
		
		// Add 2 dice
		dice.addDice(new RegularDice());
		dice.addDice(new RegularDice());
		
		for (int i = 0; i < 2000; i++) {
			int roll = dice.roll();
			
			// Checks that rolls stay within range
			assertTrue(roll >= 2);
			assertTrue(roll <= 12);
		}
	}
	
	
	
}
