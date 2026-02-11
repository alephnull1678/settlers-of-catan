// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

import java.util.Random

/*
 * Implementation of regular two six-sided dice.
 */
public class RegularDice implements Dice {
	private Random random;
	
	public RegularDice() {
		this.random = new Random();
	}
	
	@Override
	public int roll() {
		int die1 = random.nextInt(6) + 1;
		int die2 = random.nextInt(6) + 1;
		return die1 + die2;		
	}
}