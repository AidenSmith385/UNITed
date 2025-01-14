package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import calculate.*;
import units.Unit;

/**
 * Display and InputArea bars.
 * 
 * @author S24Team3F
 * @version 4/1/24
 */
public class Display extends JLabel implements ActionListener
{

  private static final long serialVersionUID = 1L;
  private static final String sc = Runner.stringsForDayz.getString("STARTC");
  private boolean removeSC;

  /**
   * Constructor.
   */
  public Display()
  {
    super(" ", SwingConstants.LEFT);

    setBorder(BorderFactory.createEtchedBorder());
    setBackground(Color.GRAY);
    setForeground(Color.BLACK);
    setText(sc);
  }

  /**
   * Updates the display.
   */
  public void updateDisplay()
  {

    setText(InputArea.getDisplayString());
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    String str = e.getActionCommand();
    if (str != "+" && str != "-" && str != "x" && str != "\u00F7" && str != "C" && str != "\u00B1")
    {
      updateDisplay();
    }

  }

}