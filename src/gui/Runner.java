package gui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import javax.swing.*;

import java.util.Locale;
import java.util.ResourceBundle;

import resourceclasspackage.*;

/**
 * Runs the program.
 * 
 * @author S24Team3F
 * @version Sprint 1  
 */
public class Runner implements Runnable {

    public static ResourceBundle stringsForDayz;
    public static Path tempPath;
    public static Path customPath;
    public static Path historyPath;
    public static Path preferencesPath;

    @Override
    public void run() {
        Locale userLocale = Locale.getDefault();
        if (userLocale.equals(Locale.ITALY)) {
            System.out.println("italy");
            stringsForDayz = ResourceBundle.getBundle("gui.Strings_it_IT");
        } else if (userLocale.equals(Locale.FRANCE)) {
            System.out.println("france");
            stringsForDayz = ResourceBundle.getBundle("gui.Strings_fr_FR");
        } else {
            stringsForDayz = ResourceBundle.getBundle("gui.Strings");
        }
        CalculatorWindow window = new CalculatorWindow();
        window.setTitle("UnitED");
        window.setVisible(true);
        
    }


    /**
     * Runs the calculator.
     * 
     * @param args idk what this is.
     * @throws InvocationTargetException target located.
     * @throws InterruptedException oh no we have been interrupted.
     */
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        try
        {
          tempPath = ResourceCopier.copyResourcesToTemp("tempPath", "resources");
        }
        catch (IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        catch (URISyntaxException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        customPath = ResourceCopier.fileCreator("CustomUnits.txt");
        historyPath = ResourceCopier.fileCreator("history.txt");
        preferencesPath = ResourceCopier.fileCreator("preferences.txt");
        SwingUtilities.invokeAndWait(new Runner());
    }
    
    
    /**
     * Returns the file path.
     * 
     * @return the file path as a string.
     */
    public static String getPath() {
      return tempPath.toString();
    }
    
    /**
     * Returns the custom path.
     * 
     * @return the custom path.
     */
    public static String getCustomPath() {
      return customPath.toString();
    }
    
    /**
     * Returns the history path.
     * 
     * @return the history path.
     */
    public static String getHistoryPath() {
      return historyPath.toString();
    }
    
    /**
     * Returns the preferences path.
     * 
     * @return the preferences path.
     */
    public static String getPreferencesPath() {
      return preferencesPath.toString();
    }
    
      
}