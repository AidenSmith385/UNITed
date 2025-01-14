package calculate;

/**
 * Evaluates two operands.
 * 
 * @author S24Team3F
 * @version 5/6/24
 */
public class Evaluate
{

  protected Operand firstOperand;
  protected Operand secondOperand;
  private double result;

  /**
   * Constructs the evaluation with the operands to be used.
   * 
   * @param firstOperand the first operand.
   * @param secondOperand the second operand.
   */
  public Evaluate(final Operand firstOperand, final Operand secondOperand)
  {
    this.firstOperand = firstOperand;
    this.secondOperand = secondOperand;
    this.result = 0.0;
  }

  /**
   * Default constructor.
   */
  public Evaluate()
  {
    this.result = 0.0;
  }

  /**
   * Addition of the two operands.
   * 
   * @return the sum of the two operands.
   */
  public Operand addition()
  {
    this.result = firstOperand.parseDouble(firstOperand.getNumber())
        + secondOperand.parseDouble(secondOperand.getNumber());
    Operand op = new Operand(Double.toString(result), firstOperand.getUnit());
    return op;
  }

  /**
   * Subtraction of the two operands.
   * 
   * @return the difference.
   */
  public Operand subtraction()
  {
    this.result = firstOperand.parseDouble(firstOperand.getNumber())
        - secondOperand.parseDouble(secondOperand.getNumber());
    Operand op = new Operand(Double.toString(result), firstOperand.getUnit());
    return op;
  }

  /**
   * Multiplication of the two operands.
   * 
   * @return the result.
   */
  public Operand multiplication()
  {
    this.result = firstOperand.parseDouble(firstOperand.getNumber())
        * secondOperand.parseDouble(secondOperand.getNumber());
    Operand op = new Operand(Double.toString(result), firstOperand.getUnit());
    return op;
  }

  /**
   * Division of the two operands.
   * 
   * @return the dividend.
   */
  public Operand division()
  {
    if (secondOperand.parseDouble(secondOperand.getNumber()) == 0)
    {
      throw new IllegalArgumentException("Undefined");
    }
    this.result = firstOperand.parseDouble(firstOperand.getNumber())
        / secondOperand.parseDouble(secondOperand.getNumber());
    ;
    Operand op = new Operand(Double.toString(result), firstOperand.getUnit());
    return op;
  }

  /**
   * Changes the first operand.
   * 
   * @param op the operand to change it to.
   */
  public void changeFirst(Operand op)
  {
    
    this.firstOperand = op;
  }

  /**
   * Changes the second operand.
   * 
   * @param op the operand to change it to.
   */
  public void changeSecond(Operand op)
  {
    this.secondOperand = op;
  }

  /**
   * Returns the first operand.
   * 
   * @return the first operand.
   */
  public Operand getFirstOperand() {
    return firstOperand;
  }
  
  /**
   * Returns the second operand.
   * 
   * @return the second operand.
   */
  public Operand getSecondOperand() {
    return secondOperand;
  }
}
