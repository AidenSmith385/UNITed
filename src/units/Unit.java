package units;

import java.util.ArrayList;
import java.util.Objects;

import gui.Runner;

/**
 * Units that are being used in the calculator.
 * 
 * @author S24Team3F
 * @version 5/8/24
 */
public class Unit
{

  public static final Unit UNITLESS = new Unit("", 1, UnitType.UNITLESS);

  protected String name;
  protected double conversionRate;
  protected UnitType type;

  /**
   * Constructor.
   * 
   * @param name
   *          the name of the unit.
   * @param conversionRate
   *          the conversionRate of that unit.
   * @param type
   *          what the unit measures.
   */
  public Unit(String name, double conversionRate, UnitType type)
  {
    this.name = name;
    this.conversionRate = conversionRate;
    this.type = type;
  }

  /**
   * Constructor.
   */
  protected Unit()
  {
    this.name = "";
    this.conversionRate = 1.0;
    this.type = UnitType.COMPOUND;
  }

  /**
   * Returns the name.
   * 
   * @return the name.
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Returns the conversionRate.
   * 
   * @return the conversionRate.
   */
  public double getConversionRate()
  {
    return this.conversionRate;
  }

  /**
   * Returns the unitType.
   * 
   * @return the unitType.
   */
  public UnitType getType()
  {
    return this.type;
  }

  /**
   * Sets the name.
   * 
   * @param name
   *          the name to be set to.
   */
  public void setName(final String name)
  {
    this.name = name;
  }

  /**
   * Sets the conversionRate.
   * 
   * @param conversionRate
   *          the conversionRate to be set to.
   */
  public void setConversionRate(final double conversionRate)
  {
    this.conversionRate = conversionRate;
  }

  /**
   * Sets the type.
   * 
   * @param type
   *          the type to be set to.
   */
  public void setType(final UnitType type)
  {
    this.type = type;
  }

  /**
   * Gets the type from the name of the unit.
   * 
   * @param name
   *          the name of the unit.
   * @return the unitType.
   */
  public static UnitType getTypeByName(final String name)
  {
    for (Unit unit : UnitFileReader.readUnitFile(Runner.getPath() + "/BaseUnits.txt"))
    {
      if (unit.getName().equalsIgnoreCase(name))
      {
        return unit.getType();
      }
    }
    for (Unit unit : UnitFileReader.readUnitFile(Runner.getCustomPath() + "/CustomUnits.txt"))
    {
      if (unit.getName().equalsIgnoreCase(name))
      {
        return unit.getType();
      }
    }
    return null;
  }

  /**
   * Get unit from name.
   * 
   * @param name
   *          the name of the unit.
   * @return the unit.
   */
  public static Unit getUnitByName(final String name)
  {
    if (name.equals(UNITLESS.getName()))
    {
      return UNITLESS;
    }

    for (Unit unit : UnitFileReader.readUnitFile(Runner.getPath() + "/BaseUnits.txt"))
    {
      if (unit.getName().equalsIgnoreCase(name))
      {
        return unit;
      }
    }
    for (Unit unit : UnitFileReader.readUnitFile(Runner.getCustomPath() + "/CustomUnits.txt"))
    {
      if (unit.getName().equalsIgnoreCase(name))
      {
        return unit;
      }
    }
    return null;
  }

  /**
   * Gets all the names.
   * 
   * @return ArrayList of all the strings.
   */
  public static ArrayList<String> getNames()
  {
    ArrayList<String> unitNames = new ArrayList<>();
    ArrayList<Unit> baseUnits = UnitFileReader.readUnitFile(Runner.getPath() + "/BaseUnits.txt");
    ArrayList<Unit> customUnits = UnitFileReader
        .readUnitFile(Runner.getCustomPath() + "/CustomUnits.txt");
    unitNames.add(UNITLESS.getName());
    unitNames.add(Runner.stringsForDayz.getString("CUS"));

    for (Unit unit : baseUnits)
    {
      // if (Unit.values()[i].getType().equals(unitType)) {
      unitNames.add(unit.getName());
      // }
    }

    unitNames.add(Runner.stringsForDayz.getString("CUS") + ": ");

    for (Unit unit : customUnits)
    {
      // if (Unit.values()[i].getType().equals(unitType)) {
      unitNames.add(unit.getName());
      // }
    }

    return unitNames;
  }

  /**
   * Gets the names from a type of unit.
   * 
   * @param type
   *          the unitType.
   * @return the names.
   */
  public static ArrayList<String> getNamesByType(final UnitType type)
  {
    ArrayList<String> unitNames = new ArrayList<>();
    ArrayList<Unit> baseUnits = UnitFileReader.readUnitFile(Runner.getPath() + "/BaseUnits.txt");
    ArrayList<Unit> customUnits = UnitFileReader
        .readUnitFile(Runner.getCustomPath() + "/CustomUnits.txt");

    for (Unit unit : baseUnits)
    {
      if (unit.getType().equals(type))
      {
        unitNames.add(unit.getName());
      }
    }

    for (Unit unit : customUnits)
    {
      if (unit.getType().equals(type))
      {
        unitNames.add(unit.getName());
      }
    }
    return unitNames;
  }

  /**
   * @return the hashcode.
   */
  public int hashCode()
  {
    return Objects.hash(name, conversionRate, type);
  }

  @Override
  public boolean equals(final Object other)
  {
    if (this == other)
      return true;
    if (other == null || getClass() != other.getClass())
      return false;

    Unit otherUnit = (Unit) other;

    return Double.compare(otherUnit.conversionRate, conversionRate) == 0
        && name.equals(otherUnit.getName()) && type.equals(otherUnit.type);
  }

}
