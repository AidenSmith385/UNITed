package calculate;

import java.util.LinkedHashMap;

import units.*;

/**
 * Converts two operands.
 * 
 * @author S24Team3F
 * @version 5/6/24
 */
public class Convert extends Evaluate
{
  
  private Operand firstConvertedOperand;
  private Operand secondConvertedOperand;
  private Operand convertedResult;
  private Operand finalResult;
  private boolean multiplication = false;
  private boolean division = false;
  
  /**
   * Constructor for Convert.
   * 
   * @param firstOperand the first operand.
   * @param secondOperand the second operand.
   */
  public Convert(Operand firstOperand, Operand secondOperand) {
    super(firstOperand, secondOperand);
  }
  
  /**
   * Default constructor.
   */
  public Convert() {
    super();
  }
  
  /**
   * Converts both units to the base unit of the same type.
   * lengths to inches.
   * weights to ounces.
   * times to seconds.
   * powers to watts.
   * 
   * @param firstUnit the fist unit.
   * @param secondUnit the second unit.
   */
  public void convertTo(Unit firstUnit, Unit secondUnit) {
	  double firstRate = 1.0;
	  double secondRate;
	  if (firstUnit instanceof CompoundUnit) {
		  LinkedHashMap<Unit, Integer> topUnits = ((CompoundUnit) firstUnit).getTopUnits();
		  LinkedHashMap<Unit, Integer> bottomUnits = ((CompoundUnit) firstUnit).getBottomUnits();
		  for (Unit unit : topUnits.keySet()) {
			  if (unit.getType().equals(firstUnit.getType())) {
				  firstRate = unit.getConversionRate();
			  }
		  }
		  for (Unit unit : bottomUnits.keySet()) {
			  if (unit.getType().equals(firstUnit.getType())) {
				  firstRate = unit.getConversionRate();
			  }
		  }
	  } else {
		  firstRate = firstUnit.getConversionRate();
	  }
    secondRate = secondUnit.getConversionRate();
    
    double firstConverted = Double.parseDouble(firstOperand.getNumber());
    double secondConverted = Double.parseDouble(secondOperand.getNumber());
    
    firstConverted = firstConverted * firstRate;
    secondConverted = secondConverted * secondRate;
    
    firstOperand = new Operand(Double.toString(firstConverted));
    secondOperand = new Operand(Double.toString(secondConverted));
  }
  
  /**
   * Converts units from base unit to the left unit.
   * 
   * @param firstUnit left unit.
   * @param secondUnit the second unit.
   */
  public void convertFrom(Unit firstUnit, Unit secondUnit) {
    double firstRate = firstUnit.getConversionRate();
    double secondRate = secondUnit.getConversionRate();
    
    double unconverted = Double.parseDouble(convertedResult.getNumber());
    
    // Multiply the rate for compound units.
    if (multiplication && firstUnit instanceof CompoundUnit) {
    	for (int i = 1; i <= ((CompoundUnit) firstUnit).getTypeExponent(firstUnit.getType()); i++) {
    		firstRate *= firstRate;
    	}
    }
    else if (multiplication) {
    	firstRate *= firstRate;
    }
    
	unconverted = unconverted / firstRate;
	finalResult = new Operand(Double.toString(unconverted), firstUnit);
  }
  
  /**
   * Performs addition operation.
   * 
   * @return the result of addition.
   */
  public Operand addition() {
    Unit firstUnit = firstOperand.getUnit();
    Unit secondUnit = secondOperand.getUnit();
    
    if (firstUnit.equals(secondUnit)) {
      return super.addition();
    }
    
    else {
      convertTo(firstUnit, secondUnit);
      this.convertedResult = super.addition();
      convertFrom(firstUnit, secondUnit);
    }
    return finalResult;
    
  }
  
  /**
   * Performs subtraction operation.
   * 
   * @return the result of subtraction.
   */
  public Operand subtraction() {
    Unit firstUnit = firstOperand.getUnit();
    Unit secondUnit = secondOperand.getUnit();
    
    if (firstUnit.equals(secondUnit)) {
      return super.subtraction();
    }
    
    else {
      convertTo(firstUnit, secondUnit);
      this.convertedResult = super.subtraction();
      convertFrom(firstUnit, secondUnit);
    }
    return finalResult;
  }
  
  /**
   * Performs multiplication operation.
   * 
   * @return the result of multiplication.
   */
  public Operand multiplication() {
		Unit firstUnit = firstOperand.getUnit();
		Unit secondUnit = secondOperand.getUnit();
		String conjunction = "-";
		multiplication = true;

		Unit convertedUnit;
		boolean resultSign;
		String resultNumber;
		CompoundUnit compoundUnit;
		
		// No compound when only one unit.
		if (firstUnit.equals(Unit.UNITLESS) || (secondUnit.equals(Unit.UNITLESS) ) ) {
			this.finalResult = super.multiplication();
			
			// Determine the unitless, set unit.
			if (firstUnit.equals(Unit.UNITLESS)) {
				finalResult.setUnit(secondUnit);
			} else {
				finalResult.setUnit(firstUnit);
			}
			return finalResult;
		}
		
		if (firstUnit instanceof CompoundUnit) {
			LinkedHashMap<UnitType, Integer> exponents = ((CompoundUnit) firstUnit).getExponents();
			for (UnitType type : exponents.keySet()) {
				if (type.equals(secondUnit.getType()) && exponents.get(type) > 0) {
					convertTo(firstUnit, secondUnit);
					this.convertedResult = multiplication();
					convertFrom(firstUnit, secondUnit);
					
					compoundUnit = new CompoundUnit(conjunction, firstUnit, secondUnit);
					
					resultSign = finalResult.getSign();
					resultNumber = finalResult.getNumber();

					this.finalResult = new Operand(resultSign, resultNumber, compoundUnit);

					return finalResult;
					
				}
			}
		}
		// Convert if units are of the same type but not the same unit.
		if (firstUnit.getType().equals(secondUnit.getType()) && (!firstUnit.equals(secondUnit))) {
			convertTo(firstUnit, secondUnit);
			this.convertedResult = super.multiplication();
			if (firstUnit instanceof CompoundUnit) {
				System.out.print(true);
			}
			convertFrom(firstUnit, secondUnit);

			convertedUnit = finalResult.getUnit();

			compoundUnit = new CompoundUnit(conjunction, convertedUnit, convertedUnit);
		} else {
			this.finalResult = super.multiplication();

			compoundUnit = new CompoundUnit(conjunction, firstUnit, secondUnit);
		}
		resultSign = finalResult.getSign();
		resultNumber = finalResult.getNumber();

		this.finalResult = new Operand(resultSign, resultNumber, compoundUnit);

		return finalResult;
  }
  
  /**
   * Performs division operation.
   * 
   * @return the result of division.
   */
  public Operand division() {
    Unit firstUnit = firstOperand.getUnit();
    Unit secondUnit = secondOperand.getUnit();
    String conjunction = "/";
    division = true;
    
    Unit convertedUnit;
	boolean resultSign;
	String resultNumber;
	CompoundUnit compoundUnit;
	
	if (firstUnit instanceof CompoundUnit) {
		LinkedHashMap<UnitType, Integer> exponents = ((CompoundUnit) firstUnit).getExponents();
		for (UnitType type : exponents.keySet()) {
			if (type.equals(secondUnit.getType()) && exponents.get(type) > 0) {
				convertTo(firstUnit, secondUnit);
				this.convertedResult = division();
				convertFrom(firstUnit, secondUnit);
				
				compoundUnit = new CompoundUnit(conjunction, firstUnit, secondUnit);
				
				resultSign = finalResult.getSign();
				resultNumber = finalResult.getNumber();

				this.finalResult = new Operand(resultSign, resultNumber, compoundUnit);

				return finalResult;
				
			}
		}
	}
	
	// No compound when only either unit is unitless.
	if (firstUnit.equals(Unit.UNITLESS) || secondUnit.equals(Unit.UNITLESS) ) {
		this.finalResult = super.division();
		
		// Determine the unitless, set unit.
		if (firstUnit.equals(Unit.UNITLESS)) {
			finalResult.setUnit(secondUnit);
		} else {
			finalResult.setUnit(firstUnit);
		}
		return finalResult;
	}

	// Convert if units are of the same type.
	else if (firstUnit.getType().equals(secondUnit.getType() ) ) {
		
		// Cancel same units.
		if (firstUnit.equals(secondUnit)) {
			this.finalResult = super.division();
			finalResult.setUnit(Unit.UNITLESS);
		    return finalResult;
		}
		
		convertTo(firstUnit, secondUnit);
		this.convertedResult = super.division();
		finalResult = convertedResult;

		finalResult.setUnit(Unit.UNITLESS);
		return finalResult;
		
	} else {
		this.finalResult = super.division();

		compoundUnit = new CompoundUnit(conjunction, firstUnit, secondUnit);
		
		resultSign = finalResult.getSign();
		resultNumber = finalResult.getNumber();

		this.finalResult = new Operand(resultSign, resultNumber, compoundUnit);

		return finalResult;
	}
	
  }
  
  /**
   * Changes the first operand.
   * 
   * @param op the new first operand.
   */
  public void changeFirst(Operand op)
  {
    firstOperand = op;
    super.changeFirst(op);
  }
  
  /**
   * Changes the second operand.
   * 
   * @param op the new second operand.
   */
  public void changeSecond(Operand op)
  {
    secondOperand = op;
    super.changeSecond(op);
  }
  
  /**
   * Determines if the first operand is greater than the second operand.
   * 
   * @return true if the first operand is greater.
   */
  public boolean greaterThan() {
	  Unit firstUnit = firstOperand.getUnit();
	  Unit secondUnit = secondOperand.getUnit();
	  
	  if (firstUnit.getType().equals(secondUnit.getType())) {
		  convertTo(firstUnit, secondUnit);
		  
		  Double firstNumber = Double.parseDouble(firstOperand.getNumber());
		  Double secondNumber = Double.parseDouble(secondOperand.getNumber());
		  
		  return (firstNumber > secondNumber);	  
	  } else {
		  return false;
	  }  
  }
  
  /**
   * Determines if the first operand is less than the second operand.
   * 
   * @return true if the first operand is less than.
   */
  public boolean lessThan() {
	  Unit firstUnit = firstOperand.getUnit();
	  Unit secondUnit = secondOperand.getUnit();
	  
	  if (firstUnit.getType().equals(secondUnit.getType())) {
		  convertTo(firstUnit, secondUnit);
		  
		  Double firstNumber = Double.parseDouble(firstOperand.getNumber());
		  Double secondNumber = Double.parseDouble(secondOperand.getNumber());
		  
		  return (firstNumber < secondNumber);	  
	  } else {
		  return false;
	  }	  
  }
  
  /**
   * Determines if the operands are equal.
   * 
   * @return true if the operands are equal.
   */
  public boolean equals() {
	  Unit firstUnit = firstOperand.getUnit();
	  Unit secondUnit = secondOperand.getUnit();
	  
	  if (firstUnit.getType().equals(secondUnit.getType())) {
		  convertTo(firstUnit, secondUnit);
		  
		  Double firstNumber = Double.parseDouble(firstOperand.getNumber());
		  Double secondNumber = Double.parseDouble(secondOperand.getNumber());
		  
		  return (firstNumber.equals(secondNumber));	  
	  } else {
		  return false;
	  }
  }    

}