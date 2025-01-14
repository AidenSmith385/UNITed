package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calculate.Operand;
import gui.CalculatorWindow;
import gui.Widgets;
import units.CompoundUnit;
import units.Unit;
import units.UnitType;

class OperandTest {

  @Test
  void testDefaultConstructor() {
    Operand operand = new Operand();
    assertEquals("", operand.getNumber());
    assertEquals(Unit.UNITLESS, operand.getUnit());
    assertFalse(operand.getSign());
    assertFalse(operand.getCompound());
    assertNull(operand.getCompoundUnit());
  }

  @Test
  void testConstructorWithString() {
    Operand operand = new Operand("123");
    assertEquals("123", operand.getNumber());
    assertEquals(Unit.UNITLESS, operand.getUnit());
    assertFalse(operand.getSign());
    assertFalse(operand.getCompound());
    assertNull(operand.getCompoundUnit());
  }

  @Test
  void testConstructorWithStringAndUnit() {
    Unit unit = new Unit("m", 1.0, UnitType.LENGTH);
    Operand operand = new Operand("456", unit);
    assertEquals("456", operand.getNumber());
    assertEquals(unit, operand.getUnit());
    assertFalse(operand.getSign());
    assertFalse(operand.getCompound());
    assertNull(operand.getCompoundUnit());
  }

  @Test
  void testConstructorWithStringAndCompoundUnit() {
      CompoundUnit compoundUnit = new CompoundUnit(" ", Unit.UNITLESS, Unit.UNITLESS);
      Operand operand = new Operand("789", compoundUnit);
      assertEquals("789", operand.getNumber());
      assertNull(operand.getUnit());
      assertTrue(operand.getCompound());
      assertEquals(compoundUnit, operand.getCompoundUnit());
  }

  @Test
  void testConstructorWithSignStringAndUnit() {
      Unit unit = new Unit("kg", 1.0, UnitType.WEIGHT);
      Operand operand = new Operand(true, "987", unit);
      assertEquals("987", operand.getNumber());
      assertEquals(unit, operand.getUnit());
      assertTrue(operand.getSign());
      assertFalse(operand.getCompound());
      assertNull(operand.getCompoundUnit());
  }

  @Test
  void testConstructorWithSignStringAndCompoundUnit() {
      CompoundUnit compoundUnit = new CompoundUnit(" ", Unit.UNITLESS, Unit.UNITLESS);
      Operand operand = new Operand(false, "654", compoundUnit);
      assertEquals("654", operand.getNumber());
      assertNull(operand.getUnit());
      assertFalse(operand.getSign());
      assertTrue(operand.getCompound());
      assertEquals(compoundUnit, operand.getCompoundUnit());
  }

  @Test
  void testSignChange() {
    Operand operand = new Operand("123");
    operand.signChange();
    assertEquals("-123", operand.getNumber());
    assertTrue(operand.getSign());

    operand.signChange();
    assertEquals("123", operand.getNumber());
    assertFalse(operand.getSign());
  }

  @Test
  void testAdd() {
    Operand operand = new Operand("123");
    operand.add("4");
    assertEquals("1234", operand.getNumber());
  }

  @Test
  void testDelete() {
    Operand operand = new Operand("123");
    operand.delete();
    assertEquals("12", operand.getNumber());
  }

  @Test
  void testClear() {
    Operand operand = new Operand("123");
    operand.clear();
    assertEquals("", operand.getNumber());
    assertNull(operand.getCompoundUnit());
  }

  @Test
  void testParseDouble() {
    Operand operand = new Operand();
    double result = operand.parseDouble("123.456");
    assertEquals(123.456, result);
  }

  @Test
  void testToString() {
    Operand operand = new Operand("123.456");
    assertEquals("123.456", operand.toString());
  }

  @Test
  void testSetNumberDigits() {
      Widgets widgets = new Widgets();
      CalculatorWindow.setWidget(widgets);

      Operand operand = new Operand("123456789.123456");
      widgets.setNumDigits("3"); // Assuming this is how you set the number of digits in Widgets
      String formattedString = operand.addThousandsSeparator(); // This indirectly tests addThousandsSeparator()
      assertEquals("123,456,789.123456", formattedString);

  @Test
  void testSetNumberDigitsMaxButtonClick() {
      Widgets widgets = new Widgets();
      CalculatorWindow.setWidget(widgets);
      widgets.setMaxButtonClick(true);
      widgets.setNumDigits("2");

      Operand operand = new Operand();
      String formattedString = operand.setNumberDigits("12.345");
      assertEquals("12.35", formattedString);
  }

  @Test
  void testSetNumberDigitsFixedButtonClick() {
      Widgets widgets = new Widgets();
      CalculatorWindow.setWidget(widgets);
      widgets.setFixedButtonClick(true);
      widgets.setNumDigits("3");

      Operand operand = new Operand();
      String formattedString = operand.setNumberDigits("12.345");
      assertEquals("12.345", formattedString);
  }
}