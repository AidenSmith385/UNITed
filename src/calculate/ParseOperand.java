package calculate;

import units.*;

/**
 * 
 * @author S24Team3F
 * @version 1/1/1
 */
public class ParseOperand {

	
  /**
   * Constructor.
   */
	public ParseOperand() {
		
	}
	
	/**
	 * Creates an operand.
	 * 
	 * @param sign the sign.
	 * @param number the number.
	 * @param unit the unit.
	 * @return a new operand with the desired traits.
	 */
	public Operand createOperand(Boolean sign, String number, Unit unit) {
		return new Operand(sign, number, unit);
	}
	
}