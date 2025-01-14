package testing;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calculate.*;

class TestEvaluate {

	@Test
	void testAddition() {
		Operand firstOp = new Operand("1.0");
		Operand secondOp = new Operand("2.0");
		Evaluate evaluate = new Evaluate(firstOp, secondOp);
		Operand thirdOp = new Operand("-3.0");
		Operand fourthOp = new Operand("-4.5");
		Evaluate evaluate2 = new Evaluate(thirdOp, fourthOp);
		String num1 = evaluate.addition().getNumber();
		assertEquals("3.0", num1);
		assertEquals("-7.5", evaluate2.addition().getNumber());
	}
	
	@Test
	void testSubtraction() {
		Operand firstOp = new Operand("3.0");
		Operand secondOp = new Operand("2.0");
		Evaluate evaluate = new Evaluate(firstOp, secondOp);
		Operand thirdOp = new Operand("-3.0");
		Operand fourthOp = new Operand("-4.5");
		Evaluate evaluate2 = new Evaluate(thirdOp, fourthOp);
		assertEquals("1.0", evaluate.subtraction().getNumber());
		assertEquals("1.5", evaluate2.subtraction().getNumber());
	}
	
	@Test
	void testMultiplication() {
		Operand firstOp = new Operand("3.0");
		Operand secondOp = new Operand("2.0");
		Evaluate evaluate = new Evaluate(firstOp, secondOp);
		Operand thirdOp = new Operand("-3.0");
		Operand fourthOp = new Operand("3.0");
		Evaluate evaluate2 = new Evaluate(thirdOp, fourthOp);
		assertEquals("6.0", evaluate.multiplication().getNumber());
		assertEquals("-9.0", evaluate2.multiplication().getNumber());
	}
	
	@Test
	void testDivision() {
		Operand firstOperand = new Operand("2.0");
		Operand secondOperand = new Operand("2.0");
		Evaluate evaluate = new Evaluate(firstOperand, secondOperand);
		Operand thirdOperand = new Operand("2.0");
		Operand fourthOperand = new Operand("0.0");
		Evaluate evaluate2 = new Evaluate(thirdOperand, fourthOperand);
		assertEquals("1.0", evaluate.division().getNumber());
		assertThrows(IllegalArgumentException.class,
	            () -> {
	            	evaluate2.division().getNumber();
	            });
	}

	@Test
	void testChanges() {
		Evaluate eval = new Evaluate();
		Operand op1 = new Operand("1.0");
		Operand op2 = new Operand("2.0");
		Evaluate evaluate = new Evaluate(op1, op2);
		evaluate.changeFirst(new Operand("3.0"));
		evaluate.changeSecond(new Operand("2.0"));
		assertEquals("5.0", evaluate.addition().getNumber());
	}
	
}
