package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.imageio.ImageIO;
import javax.swing.*;
import units.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;

/**
 * 
 * Creates a Dive in Window.
 * 
 * @author S24 whatever
 * @version 3/27/2024
 */
public class CalculatorWindow extends JFrame implements ActionListener, KeyListener
{
  private static final long serialVersionUID = 1L;
  private Display display;
  private Container contentPane;
  private static InputArea inputArea;
  private NumberPad np;
  private static ArrayList<String> unitNames = Unit.getNames();
  private static JComboBox<String> units = new JComboBox<>(unitNames.toArray(new String[0]));
  private static String selectedUnit;
  private String operator;
  private ExtendableWindow extendableWindow = new ExtendableWindow(this, "History", false);
  private static Widgets widget = new Widgets();
  private String selectedOption = "Format of Results";

  /**
   * 
   * @throws HeadlessException
   */
  public CalculatorWindow() throws HeadlessException
  {
    super();
    this.addKeyListener(this);
    this.setFocusable(true);
    this.requestFocusInWindow();
    setUpLayout();
    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // pack();
  }

  /**
   * Sets up the layout.
   */
  private void setUpLayout()
  {
    contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout()); // Setting the main layout to a BorderLayout
    contentPane.setBackground(Color.WHITE);

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS)); // Using BoxLayout for
                                                                       // vertical stacking

    setUpLogo(northPanel); // Pass the new panel as a parameter
    setUpDisplay(northPanel); // Pass the new panel as a parameter

    contentPane.add(northPanel, BorderLayout.NORTH);

    setFileBar();
    setUpGrid();
    setUpExtendo();

  }

  /**
   * Sets up the logo in the North of the BorderLayout.
   */
  private void setUpLogo(JPanel northPanel)
  {
    // Parameters for the logo size (you can adjust these as per your requirements)
    final int LOGO_WIDTH = 70; // Desired width in pixels
    final int LOGO_HEIGHT = 30; // Desired height in pixels

    JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Align the logo to the left
    try
    {
      // Read the original logo image
      Image logoImage = ImageIO.read(new File(Runner.getPath() + "/logo.png"));
      // Scale the logo to the desired dimensions
      Image scaledLogoImage = logoImage.getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT,
          Image.SCALE_SMOOTH);
      ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);

      // Create a label to hold the scaled logo
      JLabel logoLabel = new JLabel(scaledLogoIcon);
      setIconImage(scaledLogoImage); // Set the frame icon to the smaller logo
      logoPanel.add(logoLabel); // Add logo label to the logo panel
    }
    catch (IOException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Logo file not found", "Image Load Error",
          JOptionPane.ERROR_MESSAGE);
    }

    northPanel.add(logoPanel); // Add the logo panel to the northPanel
  }

  /**
   * Set ups the extending window.
   */
  private void setUpExtendo()
  {
    JButton toggleExtendButton = new JButton("→"); // You can use a more appropriate icon or text
    toggleExtendButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (extendableWindow != null)
        {
          extendableWindow.toggle();
        }
      }
    });

    contentPane.add(toggleExtendButton, BorderLayout.EAST);

    setSize(350, 500);
  }

  /**
   * Sets up the File bar at the top of the screen and puts in the menu items.
   */
  private void setFileBar()
  {
    JMenuBar menuBar = new JMenuBar(); // Menu bar set up.
    JMenu file = new JMenu(Runner.stringsForDayz.getString("FILE")); // Doing File menu stuff
    JMenuItem exit = new JMenuItem(Runner.stringsForDayz.getString("EXIT"));
    exit.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        System.exit(0);
      }
    });
    JMenuItem record = new JMenuItem(Runner.stringsForDayz.getString("SAVERECORDING"));
    record.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION)
        {
          File fileToSave = fileChooser.getSelectedFile();
          String filePath = fileToSave.getAbsolutePath();

          // Add ".txt" extension if not already present
          if (!filePath.endsWith(".txt"))
          {
            filePath += ".txt";
          }
          try
          {
            FileWriter writer = new FileWriter(filePath);
            System.out.println("File saved successfully.");
          }
          catch (IOException e1)
          {
            System.out.println("An error occurred while saving the file: " + e1.getMessage());
          }
          new RecordingWindow(filePath);
        }
      }
    });
    JMenuItem replay = new JMenuItem(Runner.stringsForDayz.getString("OPENRECORDING"));
    replay.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Recording");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION)
          ;
        {
          File fileToPlay = fileChooser.getSelectedFile();
          String filePath = fileToPlay.getAbsolutePath();

          if (!filePath.endsWith(".txt"))
          {
            filePath += ".txt";
          }
          new PlaybackWindow(filePath, inputArea, np, CalculatorWindow.this);
        }

      }
    });
    JMenuItem newCalc = new JMenuItem(Runner.stringsForDayz.getString("NEWCALCULATOR"));
    newCalc.addActionListener(new ActionListener() // NO CONFIDENCE THAT THIS WORKS
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        createNewWindow();
      }
    });
    JMenuItem printSesh = new JMenuItem(Runner.stringsForDayz.getString("PRINT"));
    printSesh.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        printHistory(inputArea.getHistory());
      }
    });

    file.add(replay);
    file.add(record);
    file.add(newCalc);
    file.add(printSesh);
    file.add(exit);
    JMenu helpDropDown = new JMenu(Runner.stringsForDayz.getString("HELP")); // Doing Help menu
                                                                             // stuff.
    JMenuItem about = new JMenuItem(Runner.stringsForDayz.getString("ABOUT"));
    about.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        aboutWindow();
      }
    });
    JMenuItem help = new JMenuItem(Runner.stringsForDayz.getString("HELP"));
    help.addActionListener(new ActionListener()
    { // Has to open up the html file.
      @Override
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          File file;
          if (Locale.getDefault().equals(Locale.ITALIAN))
          {
            file = new java.io.File(Runner.getPath() + "/italianHelp.html");
          }
          else if (Locale.getDefault().equals(Locale.FRANCE))
          {
            file = new java.io.File(Runner.getPath() + "/frenchHelp.html");
          }
          else
          {
            file = new java.io.File(Runner.getPath() + "/help.html").getAbsoluteFile();
          }
          Desktop.getDesktop().open(file);
          System.out.println("End..");
        }
        catch (Exception e1)
        {
          e1.printStackTrace();
        }
      }
    });

    // Toggle Extendable Window MenuItem
    JMenuItem toggleExtend = new JMenuItem(Runner.stringsForDayz.getString("TOGGLE"));
    toggleExtend.addActionListener(e -> {
      if (extendableWindow != null)
      {
        extendableWindow.toggle();
      }
    });
    helpDropDown.add(about);
    helpDropDown.add(help);

    JMenu preferences = new JMenu(Runner.stringsForDayz.getString("PREFERENCES"));
    JMenuItem open = new JMenuItem(Runner.stringsForDayz.getString("OPEN"));
    open.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        JFrame frame = new JFrame(Runner.stringsForDayz.getString("PREFERENCES"));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel();
        String[] options = {Runner.stringsForDayz.getString("THOUSANDSEPERATOR"),
            Runner.stringsForDayz.getString("FORMATOFRESULTS")};
        JComboBox<String> comboBox = new JComboBox<>(options);

        comboBox.setPreferredSize(new Dimension(400, comboBox.getPreferredSize().height));
        panel.add(comboBox);

        frame.add(panel);
        panel.setPreferredSize(new Dimension(200, 100));
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setVisible(true);

        comboBox.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            selectedOption = (String) combo.getSelectedItem();
            if (selectedOption.equals(Runner.stringsForDayz.getString("FORMATOFRESULTS")))
            {
              JOptionPane.showOptionDialog(null, widget, "Edit Options",
                  JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            }
            if (selectedOption.equals(Runner.stringsForDayz.getString("THOUSANDSEPERATOR")))
            {
              widget.setUpThousandSeperatorWindow();
            }
          }
        });

      }

    });
    preferences.add(open);
    JMenuItem edit = new JMenuItem(Runner.stringsForDayz.getString("EDIT"));
    edit.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (selectedOption.equals(Runner.stringsForDayz.getString("FORMATOFRESULTS")))
        {
          JOptionPane.showOptionDialog(null, widget, "Edit Options", JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.PLAIN_MESSAGE, null, null, null);
        }
        if (selectedOption.equals(Runner.stringsForDayz.getString("THOUSANDSEPERATOR")))
        {
          widget.setUpThousandSeperatorWindow();
        }
      }

    });
    preferences.add(edit);
    JMenuItem save = new JMenuItem(Runner.stringsForDayz.getString("SAVE"));
    save.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Properties properties = new Properties();
        properties.setProperty("preferences", getPreferences());
        PreferencesFileReader.savePreferences(properties);
        JOptionPane.showMessageDialog(null, "Preferences saved successfully!");
        ;
        ;
      }
    });
    preferences.add(save);
    JMenuItem saveAs = new JMenuItem(Runner.stringsForDayz.getString("SAVEAS"));
    saveAs.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(CalculatorWindow.this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
          File file = fileChooser.getSelectedFile();
          String filePath = file.getAbsolutePath();
          Properties properties = new Properties();
          properties.setProperty("preferences", getPreferences());
          PreferencesFileReader.savePreferencesToFile(filePath, properties);
        }
      }
    });
    preferences.add(saveAs);
    menuBar.add(file); // Adding file and edit to the menu bar.
    menuBar.add(helpDropDown);
    menuBar.add(preferences);
    setJMenuBar(menuBar); // Setting it as the menu bar.

  }

  /**
   * Sets up the about window.
   */
  private void aboutWindow()
  {
    JFrame aboutFrame = new JFrame(Runner.stringsForDayz.getString("ABOUT"));
    JLabel imageLabel = new JLabel();
    try
    {
      Image img = ImageIO.read(new File(Runner.getPath() + "/about.PNG"));
      imageLabel.setIcon(new ImageIcon(img));
      Image img2 = ImageIO.read(new File(Runner.getPath() + "/logo.PNG"));
      aboutFrame.setIconImage(img2);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    aboutFrame.add(imageLabel);
    aboutFrame.pack();
    aboutFrame.setLocationRelativeTo(null);
    aboutFrame.setVisible(true);
  }

  /**
   * Sets up the display.
   */
  private void setUpDisplay(JPanel northPanel)
  {
    display = new Display();
    display.setOpaque(true);
    display.setBackground(Color.PINK);
    display.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    display.setHorizontalAlignment(SwingConstants.CENTER);
    display.setPreferredSize(new Dimension(300, 30));

    JPanel displayPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout for centering the
                                                           // display
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;
    displayPanel.add(display, gbc);

    northPanel.add(displayPanel); // Add the display panel to the northPanel

    // northPanel.add(display);
    // display.setBorder(BorderFactory.createCompoundBorder(
    // BorderFactory.createLineBorder(Color.BLACK, 2), // Outer border
    // BorderFactory.createEmptyBorder(5, 180, 5, 180) // Padding inside the border
    // ));
  }

  /**
   * Sets up the grid.
   */
  private void setUpGrid()
  {
    inputArea = new InputArea();
    np = new NumberPad(inputArea, display, this);
    inputArea.setDisplay(display);

    // Set up the units dropdown
    units.setSelectedItem("");
    setUpUnits(units, inputArea);

    // Set up the panel that contains the inputArea and units JComboBox
    JPanel subsubpanel = new JPanel(new BorderLayout());
    subsubpanel.add(inputArea, BorderLayout.CENTER); // Add input area to the center
    subsubpanel.add(units, BorderLayout.EAST); // Add the units dropdown to the right

    // Set up the panel that contains the subsubpanel and the number pads
    JPanel subpanel = new JPanel(new BorderLayout());
    subpanel.add(subsubpanel, BorderLayout.NORTH); // Add the subsubpanel to the top
    subpanel.add(np, BorderLayout.CENTER); // Add the numeric keypad to the center

    // Set the background colors for the panels
    subpanel.setBackground(Color.WHITE);

    subsubpanel.setBackground(Color.GRAY);

    // Add the subpanel to the content pane of the CalculatorWindow
    contentPane.add(subpanel, BorderLayout.CENTER);

    InputArea.disableEnableOps(np, false);
    np.disableEnable("=", false);
    InputArea.disableEnableOther(np, false);
  }

  /**
   * Sets up the units.
   * 
   * @param units
   *          in the JCombo box.
   * @param inputArea
   *          the inputArea.
   */
  public static void setUpUnits(JComboBox<String> units, InputArea inputArea)
  {
    selectedUnit = (String) units.getSelectedItem();
    units.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        selectedUnit = (String) units.getSelectedItem();
        System.out.println(selectedUnit);
        if (selectedUnit == (Runner.stringsForDayz.getString("CUS"))
            || selectedUnit == (Runner.stringsForDayz.getString("CUS") + ": "))
        {
          CustomUnitInputWindow customUnitInputWindow = new CustomUnitInputWindow();
        }
        else
        {
          inputArea.setUnit(selectedUnit);
        }

      }
    });
  }

  /**
   * When the customUnit input window closes.
   * 
   * @param customUnit
   *          the customUnit put in.
   */
  public static void customUnitInputWindowClosed(String customUnit)
  {
    units.addItem(customUnit);
  }

  /**
   * Updates the units.
   */
  public static void updateUnits()
  {
    units.setModel(new DefaultComboBoxModel<>(unitNames.toArray(new String[0])));
  }

  /**
   * Returns the selected unit.
   * 
   * @return the selected unit.
   */
  public static String getSelectedUnit()
  {
    return selectedUnit;
  }

  // disabling and enabling keys
  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    String str = e.getActionCommand();

    InputArea.handleDisableEnable(np, str, inputArea);

  }

  @Override
  public void keyTyped(KeyEvent e)
  {

  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    // TODO Auto-generated method stub
    char keyChar = e.getKeyChar();
    display.actionPerformed(
        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(keyChar)));
    inputArea.handleKeyPress(keyChar);
    np.handleKeyPress(keyChar);
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  /**
   * Creates a new window.
   */
  private void createNewWindow()
  {
    CalculatorWindow window = new CalculatorWindow();
    window.setTitle("unitED");
    window.setVisible(true);
  }

  /**
   * Is the result from a relational expression.
   * 
   * @param result
   *          the result.
   */
  public void relationalResult(String result)
  {
    RelationalResultWindow rrw = new RelationalResultWindow(this, "Result", true, result);
  }

  /**
   * Gets the widget.
   * 
   * @return the widget.
   */
  public static Widgets getWidget()
  {
    return widget;
  }

  /**
   * Returns the preferences.
   * 
   * @return the preferences.
   */
  public String getPreferences()
  {
    String maxButton = String.valueOf(widget.getMaxButtonClick());
    String fixedButton = String.valueOf(widget.getFixedButtonClick());
    String numDigit = widget.getNumDigits();
    String thousandSeparator = String.valueOf(widget.thousandSeperatorSelected());
    return String.format("%s,%s,%s,%s", maxButton, fixedButton, numDigit, thousandSeparator);
  }

  /*
   * Opens a printer dialog to print the history
   */
  public static void printHistory(ArrayList<String> history)
  {
    PrinterJob job = PrinterJob.getPrinterJob();
    
    job.setPrintable((graphics, pageFormat, pageIndex) -> 
    {
      if (pageIndex < 0 || pageIndex >= history.size()) 
      {
        return Printable.NO_SUCH_PAGE;
      }
      
      
      graphics.setFont(new Font("Times New Roman", Font.PLAIN, 12));
      int yLocation = 100;
      int lineHeight = graphics.getFontMetrics().getAscent() + graphics.getFontMetrics().getDescent() + graphics.getFontMetrics().getLeading();
      for (String expression : history) {
        graphics.drawString(expression, 100, yLocation);
        yLocation += lineHeight;
      }
      
      return Printable.PAGE_EXISTS;
    });
    
    if (job.printDialog())
    {
      try
      {
        job.print();
      }
      catch (PrinterException ex)
      {
        ex.printStackTrace();;
      }
    }
  }
}
