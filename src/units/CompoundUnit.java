package units;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Compound units to be used for Operands.
 * 
 * @author S24Team3F
 * @version 5/6/24
 */
public class CompoundUnit extends Unit
{

  private Unit firstUnit;
  private Unit secondUnit;
  private String conjunction;
  private final UnitType unitType = UnitType.COMPOUND;
  private Integer exponent;
  private LinkedHashMap<Unit, Integer> topUnits;
  private LinkedHashMap<Unit, Integer> bottomUnits;
  private LinkedHashMap<UnitType, Integer> exponents;
  private final static String dash = "-"; 

  /**
   * Constructor.
   * 
   * @param conjunction
   *          the conjunction as a string.
   * @param firstUnit
   *          the first unit.
   * @param secondUnit
   *          the second unit.
   */
  public CompoundUnit(final String conjunction, final Unit firstUnit, final Unit secondUnit)
  {

    super();
    this.conjunction = conjunction;
    this.firstUnit = firstUnit;
    this.secondUnit = secondUnit;
    exponentsBuilder();
    unitMap();
    nameBuilder();

  }

  /**
   * Default Constructor.
   * 
   */
  private CompoundUnit()
  {
    super();
    topUnits = new LinkedHashMap<>();
    bottomUnits = new LinkedHashMap<>();
    exponents = new LinkedHashMap<>();

  }

  /**
   * Maps the units.
   */
  private void unitMap()
  {
    Integer curExponent;
    if (firstUnit instanceof CompoundUnit)
    {
      topUnits = ((CompoundUnit) firstUnit).getTopUnits();
      bottomUnits = ((CompoundUnit) firstUnit).getBottomUnits();
      ArrayList<UnitType> topTypes = getTypes(topUnits);
      ArrayList<UnitType> bottomTypes = getTypes(bottomUnits);
      boolean topType = false;
      boolean bottomType = false;

      for (UnitType type : topTypes)
      {
        if (type.equals(secondUnit.getType()))
        {
          topType = true;
          Unit unit = getUnit(topUnits, type);
          curExponent = topUnits.get(unit);
          if (conjunction.equals(dash))
          {
            topUnits.put(unit, curExponent + 1);
          }
          else
          {
            topUnits.put(unit, curExponent - 1);
          }
        }
      }

      for (UnitType type : bottomTypes)
      {
        if (type.equals(secondUnit.getType()))
        {
          bottomType = true;
          Unit unit = getUnit(bottomUnits, type);
          curExponent = bottomUnits.get(unit);
          if (conjunction.equals(dash))
          {
            bottomUnits.put(unit, curExponent - 1);
          }
          else
          {
            bottomUnits.put(unit, curExponent + 1);
          }
        }
      }

      if (!topType && !bottomType)
      {
        if (conjunction.equals(dash))
        {
          topUnits.put(secondUnit, 1);
        }
        else
        {
          bottomUnits.put(secondUnit, 1);
        }
      }

    }
    else
    {
      this.topUnits = new LinkedHashMap<>();
      this.bottomUnits = new LinkedHashMap<>();
      if (firstUnit.equals(secondUnit))
      {
        if (conjunction.equals(dash))
        {
          topUnits.put(firstUnit, 2);
        }
      }
      else
      {
        topUnits.put(firstUnit, 1);
        if (conjunction.equals("/"))
        {
          bottomUnits.put(secondUnit, 1);
        }
        else
        {
          topUnits.put(secondUnit, 1);
        }
      }
    }
  }

  /**
   * Builds a name.
   */
  private void nameBuilder()
  {
    String topString = "";
    String bottomString = "";
    Integer curExponent;
    boolean topEmpty = true;
    boolean bottomEmpty = true;

    for (Unit unit : topUnits.keySet())
    {
      curExponent = topUnits.get(unit);
      if (curExponent > 1)
      {
        topString = topString + dash + unit.getName() + stringExponent(curExponent);
        topEmpty = false;
      }
      else if (curExponent < 1)
      {
        continue;
      }
      else
      {
        topString = topString + dash + unit.getName();
        topEmpty = false;
      }
    }

    for (Unit unit : bottomUnits.keySet())
    {
      curExponent = bottomUnits.get(unit);
      if (curExponent > 1)
      {
        bottomString = bottomString + dash + unit.getName() + stringExponent(curExponent);
        bottomEmpty = false;
      }
      else if (curExponent < 1)
      {
        continue;
      }
      else
      {
        bottomString = bottomString + dash + unit.getName();
        bottomEmpty = false;
      }
    }

    if (topString.length() > 1)
    {
      topString = topString.substring(1);
    }
    if (bottomString.length() > 1)
    {
      bottomString = bottomString.substring(1);
    }

    if (bottomUnits.size() == 0 || bottomEmpty)
    {
      name = topString;
    }
    else if (topUnits.size() == 0 || topEmpty)
    {
      name = bottomString;
    }
    else
    {
      name = topString + "/" + bottomString;
    }
  }

  /**
   * Builds the exponent.
   */
  private void exponentsBuilder()
  {
    exponents = new LinkedHashMap<>();
    for (UnitType type : UnitType.values())
    {
      exponents.put(type, 1);
    }
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
   * Returns the unit type.
   * 
   * @return the unit type.
   */
  public UnitType getType()
  {
    return this.unitType;
  }

  /**
   * Returns the conjunction.
   * 
   * @return the conjunction.
   */
  public String getConjunction()
  {
    return conjunction;
  }

  /**
   * Returns the first unit.
   * 
   * @return the first unit.
   */
  public Unit getFirst()
  {
    return this.firstUnit;
  }

  /**
   * Returns the second unit.
   * 
   * @return the second unit.
   */
  public Unit getSecond()
  {
    return this.secondUnit;
  }

  /**
   * Returns the exponent.
   * 
   * @return the exponent.
   */
  public int getExponent()
  {
    return this.exponent;
  }

  /**
   * Gets the types of the units.
   * 
   * @param units
   *          the units.
   * @return the list of unit types.
   */
  public ArrayList<UnitType> getTypes(final LinkedHashMap<Unit, Integer> units)
  {
    ArrayList<UnitType> types = new ArrayList<>();
    for (Unit unit : units.keySet())
    {
      types.add(unit.getType());
    }
    return types;
  }

  /**
   * Returns the unit based on type.
   * 
   * @param units
   *          the units.
   * @param type
   *          the type.
   * @return the unit.
   */
  private Unit getUnit(final LinkedHashMap<Unit, Integer> units, final UnitType type)
  {
    for (Unit unit : units.keySet())
    {
      if (type.equals(unit.getType()))
      {
        return unit;
      }
    }
    return null;
  }

  /**
   * Returns the exponents map.
   * 
   * @return the exponents map.
   */
  public LinkedHashMap<UnitType, Integer> getExponents()
  {
    return this.exponents;
  }

  /**
   * Returns a string representation of the compound unit.
   * 
   * @return the name.
   */
  public String toString()
  {
    nameBuilder();
    return name;
  }

  /**
   * Returns the top units.
   * 
   * @return the top units.
   */
  public LinkedHashMap<Unit, Integer> getTopUnits()
  {
    return this.topUnits;
  }

  /**
   * Returns the bottom units.
   * 
   * @return the bottom units.
   */
  public LinkedHashMap<Unit, Integer> getBottomUnits()
  {
    return this.bottomUnits;
  }

  /**
   * Returns the exponent for a specific unit type.
   * 
   * @param type
   *          the unit type.
   * @return the exponent for the given unit type.
   */
  public Integer getTypeExponent(final UnitType type)
  {
    Integer typeExponent;
    if (exponents.containsKey(type))
    {
      typeExponent = exponents.get(type);
    }
    else
    {
      typeExponent = null;
    }
    return typeExponent;
  }

  /**
   * Converts an integer exponent to a string representation.
   * 
   * @param exponent
   *          the integer exponent.
   * @return the string representation of the exponent.
   */
  private String stringExponent(final Integer exponent)
  {
    String number = String.valueOf(exponent);
    number = number.replaceAll("0", "⁰");
    number = number.replaceAll("1", "¹");
    number = number.replaceAll("2", "²");
    number = number.replaceAll("3", "³");
    number = number.replaceAll("4", "⁴");
    number = number.replaceAll("5", "⁵");
    number = number.replaceAll("6", "⁶");
    number = number.replaceAll("7", "⁷");
    number = number.replaceAll("8", "⁸");
    number = number.replaceAll("9", "⁹");
    return number;
  }

  /**
   * Converts the super.
   * 
   * @param exponent
   *          an exponent.
   * @return a String.
   */
  private static String convertSuper(final String exponent)
  {
    String number = exponent.replaceAll("⁰", "0").replaceAll("¹", "1").replaceAll("²", "2")
        .replaceAll("³", "3").replaceAll("⁴", "4").replaceAll("⁵", "5").replaceAll("⁶", "6")
        .replaceAll("⁷", "7").replaceAll("⁸", "8").replaceAll("⁹", "9");
    return number;
  }

  /**
   * Adds a unit and its exponent to topUnits.
   * 
   * @param unitString
   *          the unit in String form
   * @param exponent
   *          the integer exponent.
   */
  private void addTopUnit(final String unitString, final Integer exponent)
  {
    Unit unit = Unit.getUnitByName(unitString);
    UnitType type = Unit.getTypeByName(unitString);
    exponents.put(type, exponent);
    topUnits.put(unit, exponent);
  }

  /**
   * Adds a unit and its exponent to bottomUnits.
   * 
   * @param unitString
   *          the unit in String form
   * @param exponent
   *          the integer exponent.
   */
  private void addBottomUnit(final String unitString, final Integer exponent)
  {
    Unit unit = Unit.getUnitByName(unitString);
    UnitType type = Unit.getTypeByName(unitString);
    exponents.put(type, exponent);
    bottomUnits.put(unit, exponent);
  }

  /**
   * Gets the exponent for a unitString.
   * 
   * @param unitString
   *          the unit string.
   * @return the exponent.
   */
  private static Integer getExponent(final String unitString)
  {
    String string = CompoundUnit.convertSuper(unitString);
    String exponent = "";
    boolean foundDigit = false;

    for (char ch : string.toCharArray())
    {
      if (Character.isDigit(ch))
      {
        foundDigit = true;
        exponent += ch;
      }
    }
    if (!foundDigit)
    {
      exponent = "1";
    }

    return Integer.parseInt(exponent);
  }

  /**
   * Filters.
   * 
   * @param unitString
   *          the string of units to be filtered.
   * @return the filtered unitString.
   */
  private static String filter(final String unitString)
  {
    String regex = "[^a-zA-Z]";
    return unitString.replaceAll(regex, "");
  }

  /**
   * Gets unit by the name.
   * 
   * @param name
   *          the name.
   * @return the unit.
   */
  public static Unit getUnitByName(final String name)
  {
    CompoundUnit compoundUnit = new CompoundUnit();
    String topString = "";
    String bottomString = "";
    String[] topUnits;
    String[] bottomUnits;
    Integer curExponent;

    if (name.contains("/"))
    {
      topString = name.split("/")[0];
      bottomString = name.split("/")[1];
      if (topString.contains(dash))
      {
        topUnits = topString.split(dash);

        for (String unit : topUnits)
        {
          curExponent = getExponent(unit);
          String unit1 = filter(unit);
          compoundUnit.addTopUnit(unit1, curExponent);
        }
      }
      else
      {
        String unit = topString;
        curExponent = getExponent(unit);
        unit = filter(unit);
        compoundUnit.addTopUnit(unit, curExponent);
      }
      if (bottomString.contains(dash))
      {
        bottomUnits = bottomString.split(dash);

        for (String unit : bottomUnits)
        {
          curExponent = getExponent(unit);
          String unit1 = filter(unit);
          compoundUnit.addBottomUnit(unit1, curExponent);
        }
      }
      else
      {
        String unit = bottomString;
        curExponent = getExponent(unit);
        unit = filter(unit);
        compoundUnit.addBottomUnit(unit, curExponent);
      }
    }
    else
    {
      String unit = name;
      curExponent = getExponent(unit);
      unit = filter(unit);
      if (curExponent == 1)
      {
        return Unit.getUnitByName(unit);
      }
      compoundUnit.addTopUnit(unit, curExponent);
    }
    compoundUnit.nameBuilder();
    return compoundUnit;
  }

}
