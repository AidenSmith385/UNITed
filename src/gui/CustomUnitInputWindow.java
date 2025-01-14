package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Makes an input window for custom units.
 * 
 * @author S24Team3F
 * @version 5/8/24
 */
public class CustomUnitInputWindow extends JFrame
{
  private JTextField textField;
  private JButton okButton;

  /**
   * Constructor.
   */
  public CustomUnitInputWindow()
  {
    super("Custom Unit Input");
    initComponents();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(300, 100);
    setResizable(false);
    setVisible(true);
    try
    {
      Image iconImage = ImageIO.read(new File(Runner.getPath() + "/unitED_Logo.PNG"));
      setIconImage(iconImage);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    
  }

  /**
   * Sets up components of the window.
   */
  private void initComponents()
  {
    textField = new JTextField(20);
    okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        String customUnit = textField.getText().trim();
        if (!customUnit.isEmpty())
        {
          writeCustomUnitToFile(customUnit);
          dispose();
          CalculatorWindow.customUnitInputWindowClosed(customUnit);
        }
        else
        {
          JOptionPane.showMessageDialog(CustomUnitInputWindow.this,
              "Please enter a valid custom unit name", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    JPanel panel = new JPanel();
    panel.add(new JLabel(Runner.stringsForDayz.getString("CUSNAME") + ": "));
    panel.add(textField);
    panel.add(okButton);

    getContentPane().add(panel);
  }

  /**
   * Writes the custom unit to a file.
   * 
   * @param customUnit the customunit to be written to a file.
   */
  private void writeCustomUnitToFile(String customUnit)
  {
    try (BufferedWriter writer = new BufferedWriter(
        new FileWriter(Runner.getCustomPath() + "/CustomUnits.txt", true)))
    {
      writer.write(customUnit + ",1,custom");
      writer.newLine();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error writing custom unit to file", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }



}
