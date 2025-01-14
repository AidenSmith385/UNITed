package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Properties;

import resourceclasspackage.ResourceCopier;

/**
 * Reads the preferences from a file.
 * 
 * @author S24Team3F
 * @version 8/5/24
 */
public class PreferencesFileReader
{
  private static final String CONFIG_FILE = "config.properties";
  private static final String PREFS_RESOURCE_DIR = "prefs/";

  /**
   * Loads the preferences.
   * 
   * @return the properties to be loaded.
   */
  public static Properties loadPreferences() {
      Properties properties = new Properties();
      try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
          properties.load(inputStream);
      } catch (IOException e) {
          e.printStackTrace();
      }
      return properties;
  }

  /**
   * Saves the preferences.
   * 
   * @param properties the preferences to be saved.
   */
  public static void savePreferences(Properties properties) {
    try {
      File file = new File("preferences.txt");
      if (file.createNewFile()) {
          System.out.println("File created: " + file.getAbsolutePath());
      } else {
          System.out.println("File already exists.");
      }
      FileWriter writer = new FileWriter(file);
      String maxButton = String.valueOf(properties.getProperty("maxButton", "false"));
      String fixedButton = String.valueOf(properties.getProperty("fixedButton", "false"));
      String numDigits = String.valueOf(properties.getProperty("numDigits", ""));
      String thousandSeparator = String.valueOf(properties.getProperty("thousandSeparator", "false"));
      writer.write(String.format("%s,%s,%s,%s", maxButton, fixedButton, numDigits, thousandSeparator));
      writer.close();
  } catch (IOException ex) {
      ex.printStackTrace();
  }
}

  /**
   * Saves the temppath.
   * 
   * @param tempPath the path to be saved.
   */
  public static void saveTempPath(Path tempPath) {
      Properties properties = loadPreferences();
      properties.setProperty("tempPath", tempPath.toString());
      savePreferences(properties);
  }

  /**
   * Saves the preferences to the file.
   * 
   * @param filename the filename to be saved to.
   * @param properties the properities to be saved.
   */
  public static void savePreferencesToFile(String filename, Properties properties) {
      try (FileOutputStream outputStream = new FileOutputStream(filename)) {
          properties.store(outputStream, null);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  /**
   * Copies the preferences to the temp directory.
   */
  public static void copyPrefsToTempDir() {
      try {
          Path tempDir = ResourceCopier.copyResourcesToTemp("prefs", PREFS_RESOURCE_DIR);
          Path prefsFile = tempDir.resolve(CONFIG_FILE);
          savePreferencesToFile(prefsFile.toString(), loadPreferences());
      } catch (IOException | URISyntaxException e) {
          e.printStackTrace();
      }
  }
  
  /**
   * loads the preferences from the file.
   * 
   * @return the properties slash the preferences.
   */
  public static Properties loadPreferencesFromFile() {
    Properties properties = new Properties();
    try (BufferedReader reader = new BufferedReader(new FileReader(Runner.getPreferencesPath()))) {
      String timeDate = reader.readLine(); 
      String[] maxButton = lineSplit(reader.readLine());
        CalculatorWindow.getWidget().setMaxButtonClick(Boolean.parseBoolean(maxButton[1]));
        String[] fixedButton = lineSplit(reader.readLine());
        CalculatorWindow.getWidget().setFixedButtonClick(Boolean.parseBoolean(fixedButton[1]));
        String[] numDigits = lineSplit(reader.readLine());
        CalculatorWindow.getWidget().setNumDigits(String.valueOf(numDigits[1]));
        String logoPath = reader.readLine();
        String[] seperator = lineSplit(reader.readLine());
        CalculatorWindow.getWidget().setThousandsSeperator(Boolean.parseBoolean(seperator[1]));
        
    } catch (IOException e) {
        e.printStackTrace();
    }
    return properties;
  }
  
  /**
   * Splits the line at the equals operator.
   * 
   * @param line the line to be split.
   * @return the split line.
   */
  private static String[] lineSplit(String line) {
    return line.split("=");
  }
}
