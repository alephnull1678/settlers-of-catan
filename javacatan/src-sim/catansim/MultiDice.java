// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

import java.util.ArrayList
import java.util.List;

/**
 * Multidice class which represents multiple dice
 */
public class MultiDice implements Dice {
	private List<Dice> diceList;
	
	/**
	 * Constructor for MultiDice.
	 */
	public MultiDice() {
		this.diceList = new ArrayList<>();
	}
	
	/**
	 * Add a dice to the collection
	 * @param dice The dice to add
	 */
	public void addDice(Dice dice) {
		diceList.add(dice);
	}
	
	/**
	 * Roll all dice and the return the sum.
	 * @return The sum of all dice rolls
	 */
	public int roll() {
		int total = 0;
		
		for (Dice dice : diceList) {
			total += dice.roll();
		}
		
		return total;
	}
}