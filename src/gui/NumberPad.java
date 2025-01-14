package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Is the numberPad.
 * 
 * @author S24Team3F
 * @version 1
 */
public class NumberPad extends JPanel
{

  private static final long serialVersionUID = 1L;
  private static final Font BUTTON_FONT = new Font("DejaVu Sans", Font.PLAIN, 17);
  private ActionListener listenerA;
  private ActionListener listenerB;
  private ActionListener listenerC;

  /**
   * Constructor.
   * 
   * @param a an ActionListener.
   * @param b an ActionListener.
   * @param c an ActionListener.
   */
  public NumberPad(ActionListener a, ActionListener b, ActionListener c)
  {
    super();
    listenerA = a;
    listenerB = b;
    listenerC = c;
    setupLayout();

  }

  /**
   * Setup and layout this NumberPad
   */
  private void setupLayout()
  {
    setLayout(new GridLayout(5, 4));
    addButton("\u00B1");
    addButton("C");
    addButton("R");
    addButton("+");
    addButton(">");
    addButton("7");
    addButton("8");
    addButton("9");
    addButton("-");
    addButton("<");
    addButton("4");
    addButton("5");
    addButton("6");
    addButton("x");
    addButton("≝");
    addButton("1");
    addButton("2");
    addButton("3");
    addButton("\u00F7");
    add(new JLabel());
    addButton("0");
    addButton(Runner.stringsForDayz.getString("DECIMAL"));
    addButton("\u232B");
    addButton("=");
  }

  /**
   * Adds a button to the layout.
   * 
   * @param s string to be on the button.
   */
  private void addButton(String s) {
      JButton button = new JButton(s);
      button.setFont(BUTTON_FONT);
      
      
      
      button.setBackground(new Color(255, 102, 0)); // Orange background
      //button.setBackground(new Color(153, 0, 0)); // Dark Red background
      
      
      
      button.setForeground(Color.WHITE); // White text
      button.setOpaque(true); // For MacOS and some LAFs that might ignore bg color
      button.setBorderPainted(true); // Optional: for buttons to not show borders
      button.setFocusPainted(false); // Optional: if you want to disable the focus border
      
      Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
      button.setBorder(lineBorder);
      
      button.addActionListener(listenerA);
      button.addActionListener(listenerB);
      button.addActionListener(listenerC);
      button.setActionCommand(s);

      add(button);
  }
  
  /**
   * Handles disabling and enabling.
   * 
   * @param buttonText button pressed.
   * @param isEnabled if it is enabled.
   */
  public void disableEnable(String buttonText, boolean isEnabled) {
    for (int i = 0; i < getComponentCount(); i++) {
        if (getComponent(i) instanceof JButton) {
            JButton button = (JButton) getComponent(i);
            if (button.getText().equals(buttonText)) {
                button.setEnabled(isEnabled);
            }
        }
    }
}
  
  /**
   * Handles when a physical key is pressed.
   * 
   * @param keyChar the character pressed.
   */
  public void handleKeyPress(char keyChar) {
    switch (keyChar) {
        case '+':
            this.disableEnable("=", true);
            break;
        case '-':
            disableEnable("=", true);
            break;
        case '*':
            disableEnable("=", true);
            break;
        case '/':
            disableEnable("=", true);
            break;
        case '=':
            disableEnable("=", false);
            break;
        case '\b': // Backspace
            disableEnable("\u232B", false);
            break;
        case '.': // Needs to use the Runner.stringsForDayz.getString("DECIMAL") but its a String not char
            disableEnable(".", false); // Same here and it will either be "." or "," if that helps
            break;
        case ',':
          disableEnable(",", false);
          break;
        default:
            if (Character.isDigit(keyChar)) {
              this.disableEnable("+", true);
              this.disableEnable("-", true);
              this.disableEnable("x", true);
              this.disableEnable("\u00F7", true); 
            }
            break;
    }
}

}