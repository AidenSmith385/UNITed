package units;

import java.util.*;

import java.io.*;

/**
 * UnitFileReader.
 * 
 * @author S24Team3F
 * @version 1
 */
public class UnitFileReader
{
  /**
   * Constructor.
   */
  private UnitFileReader()
  {
    
  }


  /**
   * Reads units from a line.
   * 
   * @param line the line of code to be read.
   * @return the line as a unit.
   */
  private static Unit readUnit(final String line)
  {
    Unit unit;
    String name;
    Double conversionRate;
    UnitType type;

    String[] parts = line.split(",");

    name = parts[0];
    type = UnitType.parseUnitType(parts[2]);

    if (parts[1].contains("/"))
    {
      String[] fraction = parts[1].split("/");
      Double numerator = Double.parseDouble(fraction[0]);
      Double denominator = Double.parseDouble(fraction[1]);
      conversionRate = numerator / denominator;
    }
    else
    {
      conversionRate = Double.parseDouble(parts[1]);
    }

    unit = new Unit(name, conversionRate, type);
    return unit;
  }


  /**
   * Takes a file and turns every line into unit that it returns all in an ArrayList.
   * 
   * @param fileName
   * @return an ArrayList made of units from the file string.
   */
  public static ArrayList<Unit> readUnitFile(final String fileName)
  {
    ArrayList<Unit> unitList = new ArrayList<>();
    if (fileName.length() == 0)
    {
      return unitList;
    }
    try (BufferedReader in = new BufferedReader(new FileReader(fileName)))
    {
      String line;
      while ((line = in.readLine()) != null)
      {
        Unit unit = readUnit(line);
        unitList.add(unit);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return unitList;
  }
}
