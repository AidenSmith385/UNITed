package testing;


import org.junit.jupiter.api.Test;

import calculate.Convert;
import calculate.Operand;
import units.CompoundUnit;
import units.Unit;
import units.UnitType;

import static org.junit.jupiter.api.Assertions.*;

class ConvertTest {

    @Test
    void testConvertTo() {
        // Create a Convert object with some initial operands
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("2", new Unit("m", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);

        // Perform conversion
        convert.convertTo(firstOperand.getUnit(), secondOperand.getUnit());

        // Check if conversion is successful
        assertEquals("500.0", convert.getFirstOperand().getNumber());
        assertEquals("2.0", convert.getSecondOperand().getNumber());
    }

    @Test
    void testConvertFrom() {
      Operand firstOperand = new Operand("100", new Unit("cm", 1.0, UnitType.LENGTH));
      Operand secondOperand = new Operand("1", new Unit("m", 1.0, UnitType.LENGTH));
      Convert convert = new Convert(firstOperand, secondOperand);

      convert.convertTo(firstOperand.getUnit(), secondOperand.getUnit());
      convert.convertFrom(firstOperand.getUnit(), secondOperand.getUnit());

      assertEquals("100.0", convert.getFirstOperand().getNumber());
      assertEquals("1.0", convert.getSecondOperand().getNumber());
    }

    @Test
    void testAddition() {
        // Create a Convert object with some initial operands
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("2", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);

        // Perform addition
        Operand result = convert.addition();

        // Check if addition is successful
        assertEquals("7.0", result.getNumber());
        assertEquals(new Unit("cm", 1.0, UnitType.LENGTH), result.getUnit());
    }

    @Test
    void testSubtraction() {
        // Create a Convert object with some initial operands
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("2", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);

        // Perform subtraction
        Operand result = convert.subtraction();

        // Check if subtraction is successful
        assertEquals("3.0", result.getNumber());
        assertEquals(new Unit("cm", 1.0, UnitType.LENGTH), result.getUnit());
    }

    @Test
    void testMultiplication() {
        // Create a Convert object with some initial operands
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("2", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);

        // Perform multiplication
        Operand result = convert.multiplication();

        // Check if multiplication is successful
        assertEquals("10.0", result.getNumber());
        assertEquals(new CompoundUnit("*", new Unit("cm", 1.0, UnitType.LENGTH), new Unit("cm", 1.0, UnitType.LENGTH)), result.getUnit());
    }

    @Test
    void testDivision() {
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("2", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);

        Operand result = convert.division();

        assertEquals("2.5", result.getNumber());
        assertEquals(new CompoundUnit("/", new Unit("cm", 1.0, UnitType.LENGTH), new Unit("cm", 1.0, UnitType.LENGTH)), result.getUnit());
    }

    @Test
    void testChangeFirst() {
        // Create a Convert object with some initial operands
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("2", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);

        // Change the first operand
        convert.changeFirst(new Operand("10", new Unit("cm", 1.0, UnitType.LENGTH)));

        // Check if the first operand is changed
        assertEquals("10", convert.getFirstOperand().getNumber());
    }

    @Test
    void testChangeSecond() {
        // Create a Convert object with some initial operands
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("2", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);

        // Change the second operand
        convert.changeSecond(new Operand("10", new Unit("cm", 1.0, UnitType.LENGTH)));

        // Check if the second operand is changed
        assertEquals("10", convert.getSecondOperand().getNumber());
    }

    @Test
    void testGreaterThan() {
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("2", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);

        // Check if the first operand is greater than the second operand
        assertTrue(convert.greaterThan());
    }

    @Test
    void testLessThan() {
        Operand firstOperand = new Operand("2", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);
        assertTrue(convert.lessThan());
    }

    @Test
    void testEquals() {
        Operand firstOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Operand secondOperand = new Operand("5", new Unit("cm", 1.0, UnitType.LENGTH));
        Convert convert = new Convert(firstOperand, secondOperand);
        assertTrue(convert.equals());
    }
}
