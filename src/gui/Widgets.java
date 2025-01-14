package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The widgets.
 * 
 * @author S24Team3F
 * @version 5/8/24
 */
public class Widgets extends JPanel
{
  private JTextField numberOfDigits;
  private JCheckBox maxCheck;
  private JCheckBox fixedCheck;
  private Display display;
  private JToggleButton thousandSeperatorOn = new JToggleButton("Off");
  private boolean thousandSeperatorBool = false;
  private boolean maxBool = false;
  private boolean fixedBool = false;
  private String numOfDigits;

  /**
   * Constructor.
   */
  public Widgets()
  {
    setLayout(new FlowLayout(FlowLayout.LEADING));
    add(new JLabel("Number of Digits: "));
    numberOfDigits = new JTextField(20);
    numberOfDigits.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
          updateNumber();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
          updateNumber();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
          updateNumber();
      }

      private void updateNumber() {
          try {
              numberOfDigits.getText();
          } catch (NumberFormatException e) {
              System.out.print("Invalid number format");
          }
      }
  });
    add(numberOfDigits);
    
    add(new JLabel("Maximum: "));
    maxCheck = new JCheckBox();     
    add(maxCheck);
    
    add(new JLabel("Fixed: "));
    fixedCheck = new JCheckBox();
    add(fixedCheck);

    ButtonGroup group = new ButtonGroup();
    group.add(maxCheck);
    group.add(fixedCheck);
    
  }
  
  /**
   * Sets up the window about thousands seperator.
   */
  public void setUpThousandSeperatorWindow() {
    JFrame frame = new JFrame(Runner.stringsForDayz.getString("THOUSANDSEPERATOR"));
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frame.setSize(new Dimension(300, 150));

    thousandSeperatorOn.setPreferredSize(new Dimension(150, 75)); 
    thousandSeperatorOn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (thousandSeperatorOn.isSelected()) {
          thousandSeperatorOn.setText("On");
          thousandSeperatorBool = true;
        } else {
          thousandSeperatorOn.setText("Off");
          thousandSeperatorBool = false;
        }
      }
    });
    
    frame.getContentPane().setLayout(new FlowLayout());
    frame.getContentPane().add(thousandSeperatorOn);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setVisible(true);
    
  }
  
  /**
   * Returns the number of digits.
   * 
   * @return the number of digits.
   */
  public String getNumDigits() {
    numOfDigits = numberOfDigits.getText();
    return numOfDigits;
  }
  
  /**
   * Returns the max button click.
   * 
   * @return the max button click.
   */
  public boolean getMaxButtonClick() {
    maxBool = maxCheck.isSelected();
    return maxBool;
  }
  
  /**
   * Returns if the button is fixed clicked.
   * @return
   */
  public boolean getFixedButtonClick() {
    fixedBool = fixedCheck.isSelected();
    return fixedBool;
  }
  
  /**
   * Returns if the thousands separator is selected.
   * 
   * @return if thousands separatora are selected.
   */
  public boolean thousandSeperatorSelected() {
    return thousandSeperatorBool;
  }
  
  /**
   * Sets the max button click.
   * 
   * @param click what to set max button click to.
   */
  public void setMaxButtonClick(boolean click) {
    maxBool = click;
  }
  
  /**
   * Sets the fixed button click.
   * 
   * @param click what to set the fixed button click to.
   */
  public void setFixedButtonClick(boolean click) {
    fixedBool = click;
  }
  
  /**
   * Sets the number of digits.
   * 
   * @param num what to set the number of digits to.
   */
  public void setNumDigits(String num) {
    numOfDigits = num;
  }
  
  /**
   * Sets if there should be the thousands separators.
   * 
   * @param seperator whether or not there should be thousands separators.
   */
  public void setThousandsSeperator(Boolean seperator) {
    thousandSeperatorBool = seperator;
  }
}
  
