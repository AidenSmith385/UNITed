package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import calculate.*;
import units.*;

/**
 * Display and InputArea bars.
 * 
 * @author S24Team3F
 * @version 4/1/24
 */
public class InputArea extends JLabel implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static String displayString = " ";
  private static Operand firstOp = new Operand();
  private boolean isFirstOpSet = false;
  private static boolean calculationStarted = false;
  private boolean running = false;
  private static Operand op;
  private static final String sc = Runner.stringsForDayz.getString("STARTC");
  private static final String en = Runner.stringsForDayz.getString("ENTERN");
  private static String operator;
  private Convert cv;
  private Display display;
  private static ArrayList<String> recording = new ArrayList<String>();
  private static ArrayList<String> history = new ArrayList<String>();

  /**
   * Constructor.
   */
  public InputArea()
  {
    super(" ", SwingConstants.LEFT);
    op = new Operand();
    cv = new Convert();
    operator = "";
    setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // 3 pixels thick border

    setBackground(Color.WHITE);  // Set background color to white
    setForeground(Color.BLACK);  // Set text color to black for readability
    setOpaque(true);  // Make the JLabel opaque to show the background color
    setText(en);
  }

  /**
   * Sets the display.
   * 
   * @param display the display to be set to.
   */
  public void setDisplay(Display display)
  {
    this.display = display;
  }

  /**
   * Updates the display.
   */
  private void updateDisplay()
  {
    if (op.getNumber().isEmpty())
    {
      setText(" ");
    }
    if (operator == null)
    {
      setText(op.toString());
    }
    else
    {
      setText(op.toString() + " ");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    String str = e.getActionCommand();
    setUnit(CalculatorWindow.getSelectedUnit());
    boolean result;
    
    switch (str)
    {
      case "C": // Clear
        if (RecordingWindow.getRecording()) {
           recording.add("C");
           writeToFile(RecordingWindow.getFileName(), recording);
        }
        clear();
        break;
      case "R": // Reset
        if (RecordingWindow.getRecording()) {
          recording.add("R");
          writeToFile(RecordingWindow.getFileName(), recording);
        }
        clear();
        displayString = " ";
        break;
      case "\u232B": // Backspace arrow
        op.delete();
        break;
      case "\u00B1": // positive / negative sign
        if (this.getText() != en)
        {
          op.signChange();
        }
        break;
      case "+":
        if (RecordingWindow.getRecording()) {
          recording.add(op.toString());
          recording.add("+");
          writeToFile(RecordingWindow.getFileName(), recording);
        }
        if (op.getCompound() == true)
        {
          error(Runner.stringsForDayz.getString("ERRORZERO"));
          break;
        }
        if (calculationStarted == false && running == true)
        {
          operator = "";
          displayString = " ";
        }
        calculationStarted = true;
        // Addition
        if (isFirstOpSet == false)
        {
          firstOp = op;
        }
        op = Operand.ParseOperand(op.getNumber(), op.getUnit());
        clear();
        operator = "+";
        displayString += firstOp.toString();
        displayString += " + ";
        updateDisplay();

        break;
      case "-":
        if (RecordingWindow.getRecording()) {
          recording.add(op.toString());
          recording.add("-");
          writeToFile(RecordingWindow.getFileName(), recording);
        }
        if (op.getCompound() == true)
        {
          error(Runner.stringsForDayz.getString("ERROR"));
          break;
        }
        if (calculationStarted == false && running == true)
        {
          operator = "";
          displayString = " ";
        }
        calculationStarted = true;
        // Subtraction
        if (isFirstOpSet == false)
        {
          firstOp = op;
        }
        op = Operand.ParseOperand(op.getNumber(), op.getUnit());

        clear();
        operator = "-";
        displayString += firstOp.toString();
        displayString += " - ";
        updateDisplay();
        break;
      case "x":
        if (RecordingWindow.getRecording()) {
          recording.add(op.toString());
          recording.add("x");
          writeToFile(RecordingWindow.getFileName(), recording);
        }
        if (calculationStarted == false && running == true)
        {
          operator = "";
          displayString = " ";
        }
        calculationStarted = true;
        // Multiplication
        if (isFirstOpSet == false)
        {
          firstOp = op;
        }
        op = Operand.ParseOperand(op.getNumber(), op.getUnit());
        clear();
        operator = "x";
        displayString += firstOp.toString();
        displayString += " x ";
        updateDisplay();

        break;
      case "\u00F7":
        if (RecordingWindow.getRecording()) {
          recording.add(op.toString());
          recording.add("\u00F7");
          writeToFile(RecordingWindow.getFileName(), recording);
        }
        if (calculationStarted == false && running == true)
        {
          operator = "";
          displayString = " ";
        }
        calculationStarted = true;
        // Division
        if (isFirstOpSet == false)
        {
          firstOp = op;
        }
        op = Operand.ParseOperand(op.getNumber(), op.getUnit());
        clear();
        operator = "\u00F7";
        displayString += firstOp.toString();
        displayString += " \u00F7 ";
        updateDisplay();

        break;
      case ">":
        if (RecordingWindow.getRecording()) {
          recording.add(op.toString());
          recording.add(">");
          writeToFile(RecordingWindow.getFileName(), recording);
        }
          if (calculationStarted == false && running == true)
          {
            operator = "";
            displayString = " ";
          }
          calculationStarted = true;
          // GreaterThan
          if (isFirstOpSet == false)
          {
            firstOp = op;
          }
          op = Operand.ParseOperand(op.getNumber(), op.getUnit());
          clear();
          operator = ">";
          displayString += firstOp.toString();
          displayString += " > ";
          updateDisplay();

          break;
      case "<":
          if (calculationStarted == false && running == true)
          {
            operator = "";
            displayString = " ";
          }
          calculationStarted = true;
          // LessThan
          if (isFirstOpSet == false)
          {
            firstOp = op;
          }
          op = Operand.ParseOperand(op.getNumber(), op.getUnit());
          clear();
          operator = "<";
          displayString += firstOp.toString();
          displayString += " < ";
          updateDisplay();

          break;
      case "≝":
          if (calculationStarted == false && running == true)
          {
            operator = "";
            displayString = " ";
          }
          calculationStarted = true;
          // Equals
          if (isFirstOpSet == false)
          {
            firstOp = op;
          }
          op = Operand.ParseOperand(op.getNumber(), op.getUnit());
          clear();
          operator = "≝"; 
          displayString += firstOp.toString();
          displayString += " ≝ ";
          updateDisplay();

          break;
      case "=":
        if (RecordingWindow.getRecording()) {
          recording.add(op.toString());
          recording.add("=");
          writeToFile(RecordingWindow.getFileName(), recording);
        }
        if (op == null || firstOp == null || op.getNumber().isEmpty()
            || this.firstOp.getNumber().isEmpty())
        {
          return;
        }
        displayString += op.toString();
        displayString += " = ";
        calculationStarted = true;
        switch (operator)
        {
          case "+":
            cv.changeFirst(firstOp);
            cv.changeSecond(op);
            op = cv.addition();
            displayString += op.toString();
            operator = null;
            updateDisplay();
            history.add(displayString);
            writeToFile(Runner.getHistoryPath() + "/history.txt", history);
            ExtendableWindow.addHistoryEntry(displayString);
            break;
          case "x", "*":
            cv.changeFirst(firstOp);
            cv.changeSecond(op);
            op = cv.multiplication();
            displayString += op.toString();
            operator = null;
            updateDisplay();
            history.add(displayString);
            writeToFile(Runner.getHistoryPath() + "/history.txt", history);
            ExtendableWindow.addHistoryEntry(displayString);
            break;
          case "-":
            cv.changeFirst(firstOp);
            cv.changeSecond(op);
            op = cv.subtraction();
            displayString += op.toString();
            operator = null;
            updateDisplay();
            history.add(displayString);
            writeToFile(Runner.getHistoryPath() + "/history.txt", history);
            ExtendableWindow.addHistoryEntry(displayString);
            break;
          case "\u00F7", "/":
            cv.changeFirst(firstOp);
            cv.changeSecond(op);
            try
            {
              op = cv.division();
              displayString += op.toString();
              operator = null;
              updateDisplay();
            }
            catch (IllegalArgumentException e1)
            {
              error(Runner.stringsForDayz.getString("ERRORZERO"));
            }
            history.add(displayString);
            System.out.println(history);
            writeToFile(Runner.getHistoryPath() + "/history.txt", history);
            ExtendableWindow.addHistoryEntry(displayString);
            break;
          case ">":
              cv.changeFirst(firstOp);
              cv.changeSecond(op);
              result = cv.greaterThan();
              operator = null;
              relationalResult(displayString + result);
              displayString = " ";
              break;
          case "<":
              cv.changeFirst(firstOp);
              cv.changeSecond(op);
              result = cv.lessThan();
              operator = null;
              relationalResult(displayString + result);
              displayString = " ";
              break;
          case "≝":
              cv.changeFirst(firstOp);
              cv.changeSecond(op);
              result = cv.equals();
              operator = null;
              updateDisplay();    	  
              relationalResult(displayString + result);
              displayString = " ";
              break;    	  
        }
        calculationStarted = false;
        running = true;
        break;
      default:
        if (calculationStarted == false && running != false && this.getText() != sc)
        {
          displayString = " ";
          op.clear();
        }

        op.add(str);

        calculationStarted = false;
        running = false;
        break;
    }
    updateDisplay();
    if (display != null && (display.getText() != sc || PlaybackWindow.getPlaying()))
    {

      display.updateDisplay();
    }
  }
  
  /**
   * Returns the result for a relation.
   * 
   * @param result the result to be displayed.
   */
  public void relationalResult(String result) {
	  CalculatorWindow calcWindow = (CalculatorWindow) this.getParent().getParent().getParent().getParent().getParent().getParent();
	  calcWindow.relationalResult(result);
  }

  /**
   * Returns the display string.
   * 
   * @return the display string.
   */
  public static String getDisplayString()
  {
    return displayString;
  }

  /**
   * Clears everything.
   */
  public void clear()
  {
    op.clear();
    operator = "";
    setText(" ");
  }

  /**
   * Sets the unit to something.
   * 
   * @param unit the unit to be set to.
   */
  public void setUnit(String unit)
  {
	  for (UnitType type : UnitType.values()) {
		  ArrayList<String> unitNames = Unit.getNamesByType(type);
		  
		  //System.out.println(unit);
		  for (String unitName : unitNames) {
		    
			  if (unit.equals(unitName)) {
			    
				  op.setUnit(Unit.getUnitByName(unit));
			  }
		  }
	  }
    updateDisplay();
  }
  
  /**
   * Sets the unit to something.
   * 
   * @param unit the unit to be set to.
   */
  public void setUnit(Unit unit) {
		op.setUnit(unit);
		updateDisplay();
	}
  

  /**
   * Disables and enabled buttons. 
   * 
   * @param np the numberpad with buttons.
   * @param str the string.
   * @param inputArea the inputArea.
   */
  public static void handleDisableEnable(NumberPad np, String str, InputArea inputArea)
  {
    if (op.getCompound()) {
      np.disableEnable("+", false);
      np.disableEnable("-", false);
    }
    switch (str)
    {
      case "C": // Clear
        disableEnableOther(np, false);
        disableEnableOps(np, false);
        np.disableEnable("=", false);
        break;
      case "R": // Reset
        disableEnableOther(np, false);
        disableEnableOps(np, false);
        np.disableEnable("=", false);
        break;
      case "\u232B": // Backspace arrow

        break;
      case "\u00B1": // positive / negative sign

        break;
      case ".":
        np.disableEnable(".", false);
        break;
      case "+":
        np.disableEnable("=", true);
        disableEnableOther(np, false);
        disableEnableOps(np, false);
        break;
      case "-":
        np.disableEnable("=", true);
        disableEnableOther(np, false);
        disableEnableOps(np, false);
        break;
      case "x":
        np.disableEnable("=", true);
        disableEnableOther(np, false);
        disableEnableOps(np, false);
        break;
      case "\u00F7":
        np.disableEnable("=", true);
        disableEnableOther(np, false);
        disableEnableOps(np, false);

        break;
      case ">":
    	  np.disableEnable("=", true);
          disableEnableOther(np, false);
          disableEnableOps(np, false);

          break;
      case "<":
    	  np.disableEnable("=", true);
          disableEnableOther(np, false);
          disableEnableOps(np, false);

          break;
      case "≝":
    	  np.disableEnable("=", true);
          disableEnableOther(np, false);
          disableEnableOps(np, false);

          break;
      case "=":

        switch (operator)
        {
          case "+":

            break;
          case "x":

            break;
          case "-":

            break;
          case "\u00F7":

            break;
        }
        np.disableEnable("=", false);

      default:
        disableEnableOps(np, true);
        disableEnableOther(np, true);
        if (calculationStarted)
        {
          disableEnableOps(np, false);
        }
    }
//    System.out.println(inputArea.getText());
//    if (op.getNumber() == null || inputArea.getText() == en) {
//      System.out.println("1");
//      disableEnableOther(np, false);
//    }
  }

  /**
   * Disengages the disable.
   * 
   * @param np NumberPad.
   * @param disableEnable if it should be disabled or enabled.
   */
  public static void disableEnableOps(NumberPad np, boolean disableEnable)
  {
    np.disableEnable("+", disableEnable);
    np.disableEnable("-", disableEnable);
    np.disableEnable("x", disableEnable);
    np.disableEnable("\u00F7", disableEnable);
    np.disableEnable(">", disableEnable);
    np.disableEnable("<", disableEnable);
    np.disableEnable("≝", disableEnable);
  }

  /**
   * Disengages the disable.
   * 
   * @param np NumberPad.
   * @param disableEnable if it should be disabled or enabled.
   */
  public static void disableEnableOther(NumberPad np, boolean disableEnable)
  {
    np.disableEnable("\u00B1", disableEnable);
    np.disableEnable("\u232B", disableEnable);
    np.disableEnable(".", disableEnable);
  }

  
  /**
   * Handles when a key is pressed.
   * 
   * @param keyChar the key character.
   */
  public void handleKeyPress(char keyChar)
  {
    switch (keyChar)
    {
      case '+':
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "+"));
        break;
      case '-':
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "-"));
        break;
      case '*':
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "x"));
        break;
      case '/':
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "\u00F7"));
        break;
      case KeyEvent.VK_ENTER:
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "="));
        break;
      case '\b': // Backspace
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "\u232B"));
        break;
      case '.': // Decimal point
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "."));
        break;
      case ',':
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ","));
        break;
      default:
        if (Character.isDigit(keyChar))
        {
          actionPerformed(
              new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(keyChar)));
        }
        break;
    }
  }
  
  /**
   * Sets the first operand.
   * 
   * @param operand the operand to be set to.
   */
  public void setFirstOp(Operand operand) {
	  firstOp = operand;
  }
  
  /**
   * Sets the operand.
   * 
   * @param operand the operand to be set to.
   */
  public void setOp(Operand operand) {
	  op = operand;
  }
  
  
  /**
   * Method called when an error is reached.
   * 
   * @param message message to be displayed.
   */
  private void error(String message)
  {
    op.clear();
    firstOp.clear();
    operator = "";
    displayString = message;
    return;
  }
  
  /**
   * Writes to a file.
   * 
   * @param file the write to.
   * @param array what is being written.
   */
  private void writeToFile(String file, ArrayList<String> array) {
    try
    {
      FileWriter writer = new FileWriter(file);
      for (String item: array) {
        writer.write(item + "\n");
      }
      writer.close();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Gets the recording.
   * 
   * @return the recording.
   */
  public static ArrayList<String> getRecording() {
    return recording;
  }
  
  /**
   * Returns the history.
   * 
   * @return the history.
   */
  public static ArrayList<String> getHistory() {
    return history;
  }
  
}