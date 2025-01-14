package calculate;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JToggleButton;

import gui.CalculatorWindow;
import gui.Runner;
import gui.Widgets;
import units.*;

/**
 * The number and unit created by the calculator.
 * 
 * @author S24Team3F
 * @version 5/6/24
 */
public class Operand
{
  private boolean sign = true;
  private String number;
  private static final String sc = Runner.stringsForDayz.getString("STARTC");
  private static final String en = Runner.stringsForDayz.getString("ENTERN");
  private static final String dec = Runner.stringsForDayz.getString("DECIMAL");
  private static String tho = Runner.stringsForDayz.getString("SEPERATOR");
  private Unit unit;
  private CompoundUnit compoundUnit;
  private boolean compound;

  /**
   * Constructor.
   */
  public Operand()
  {
    this.number = "";
    this.unit = Unit.UNITLESS;
    this.compound = false;
    if (tho.equals("EMPTY")) tho = " ";
  }

  /**
   * Constructor.
   * 
   * @param number the number.
   */
  public Operand(String number) 
  {
    this.number = number;
    this.unit = Unit.UNITLESS;
    this.compound = false;
  }

  /**
   * Constructor.
   * 
   * @param number the number.
   * @param unit the unit.
   */
  public Operand(String number, Unit unit)
  {
    this.number = number;
    this.unit = unit;
    this.compound = false;
  }
  
  /**
   * Constructor.
   * 
   * @param number the number.
   * @param unit the unit.
   */
  public Operand(String number, CompoundUnit unit)
  {
    this.number = number;
    this.compoundUnit = unit;
    this.compound = true;
  }

  /**
   * Constructor.
   * 
   * @param sign the sign.
   * @param number the number.
   * @param unit the unit.
   */
  public Operand(Boolean sign, String number, Unit unit)
  {
    this.number = number;
    this.unit = unit;
    this.sign = sign;
    this.compound = false;
  }

  /**
   * Constructor.
   * 
   * @param sign the sign.
   * @param number the number.
   * @param unit the unit.
   */
  public Operand(boolean sign, String number, CompoundUnit unit)
  {
    this.sign = sign;
    this.number = number;
    this.compoundUnit = unit;
    this.compound = true;
  }

  /**
   * Changes the sign.
   */
  public void signChange()
  {
    sign = !sign;
    if (sign)
    {
      number = number.substring(1);
    }
    else
    {
      number = "-" + number;
    }
  }

  /**
   * Adds a number to the end of the number variable.
   * 
   * @param s the number to be added onto the end.
   */
  public void add(String s)
  {
    if (number.equals(sc))
    {
      number = "";
    }
    if (s.equals(dec) && number.contains(dec))
    {
      return;
    }

    number += s;
  }

  /**
   * Deletes the last number.
   */
  public void delete()
  {
	  if (number.length() > 0) {
    number = number.substring(0, number.length() - 1);
	  }
  }

  /**
   * Clears the number and unit.
   */
  public void clear()
  {
    number = "";
    compoundUnit = null;

  }

  /**
   * Parses a double.
   * 
   * @param number the number to be parsed.
   * @return the number as a double.
   */
  public double parseDouble(final String number)
  {
    return Double.parseDouble(number);
  }

  @Override
  public String toString()
  {
    if (this.number.equals(sc) || this.number.isEmpty())
    {
      return en;
    }
    String ret = this.addThousandsSeparator();
    if (this.compound = true && this.compoundUnit != null)
    {
      ret += " " + this.compoundUnit.getName();
    } else if (this.unit != null) 
    {
      ret += " " + this.unit.getName();
    }
    return ret;
  }
  
  /**
   * Adds the separators to the number.
   * 
   * @return the number with separators.
   */
  private String addThousandsSeparator() 
  {
    String[] parts = number.split("\\.");
    String iPart = parts[0];
    String dPart = parts.length > 1 ? "." + parts[1] : "";   
    StringBuilder result = new StringBuilder();
    int length = iPart.length();
    for (int i = 0; i < length; i++) {
      if (iPart.startsWith("-") && i == 0) {
        result.append("-");
        i++;
      }
      result.append(iPart.charAt(i));
      if ((length - i - 1) % 3 == 0 && i != length - 1 && 
          (CalculatorWindow.getWidget().thousandSeperatorSelected())) {
        result.append(tho);
      }
    }
    System.out.println("Before Result Append" + result.toString());
    System.out.println("Result Append Line: " + result.append(setNumberDigits(dPart)));
    System.out.println("Before result returned seperator: " + result.toString());
    return result.toString();
  }

  private String setNumberDigits(String afterDecimal)
  {
    Widgets widgets = CalculatorWindow.getWidget();
    String numDigit = widgets.getNumDigits();
    String formattedString = afterDecimal;
    System.out.println("Before If Statements: " + formattedString);
    if (afterDecimal.isEmpty()) {
      return "";
    }
    if (formattedString.isEmpty() && numDigit.isEmpty()) {
      return "";
    }
    if (widgets.getMaxButtonClick())
    {
      double num = Double.parseDouble(formattedString);
      System.out.print("Before num: " + num);
      String stringFormat = "%." + numDigit + "f";
      formattedString = String.format(stringFormat, num);
      System.out.println("AfterStringFormatMaxButton: " + formattedString);
      if (formattedString.endsWith("0")) {
        formattedString = formattedString.substring(0, 
            formattedString.length() - 1);
      }
      System.out.println("Max Click Output: " + formattedString);
      String.valueOf(formattedString);
      

      
    }
    else if (widgets.getFixedButtonClick())
    {
      double num = Double.parseDouble(formattedString);
      String stringFormat = "%." + numDigit + "f";
      System.out.println(formattedString);
      formattedString = String.format(stringFormat, num);
      System.out.println(formattedString);
      formattedString.substring(0);
    }
    System.out.println("BeforeReturnedSetDigits: " + formattedString);
    if (formattedString.charAt(0) == '0') {
      return formattedString.substring(1);
    }
    return formattedString;
    

  }

  /**
   * Returns the number.
   * 
   * @return the number.
   */
  public String getNumber()
  {
    return number;
  }

  /**
   * Returns the unit.
   * 
   * @return the unit.
   */
  public Unit getUnit()
  {
	  if (compound) {
		  return this.compoundUnit;
	  }
    return unit;
  }

  /**
   * Returns if there is a negative sign or not.
   * 
   * @return whether or not there is a negative sign
   */
  public boolean getSign()
  {
    return this.sign;
  }

  /**
   * Sets the unit to a different unit.
   * 
   * @param unit the unit to be changed to.
   */
  public void setUnit(Unit unit)
  {
    this.unit = unit;
  }
  
  /**
   * Returns true if it is a compound unit, otherwise false.
   * 
   * @return whether or not it is a compound unit.
   */
  public boolean getCompound() {
    return compound;
  }
  
  /**
   * Returns the compound unit.
   * 
   * @return the compound unit.
   */
  public CompoundUnit getCompoundUnit() {
    return compoundUnit;
  }

  /**
   * Takes in a number and a unit and parses it into an operand.
   * 
   * @param number a number.
   * @param unit a unit.
   * @return a new Operand.
   */
  public static Operand ParseOperand(String number, Unit unit)
  {
    return new Operand(number, unit);
  }

}
