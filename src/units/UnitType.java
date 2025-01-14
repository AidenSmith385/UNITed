
package units;

/**
 * UnitTypes Enum.
 * @author S24Team3F
 * @version 2
 *
 */
public enum UnitType
{
  LENGTH("Length"), WEIGHT("Weight"), TIME("Time"), POWER("Power"), VOLUME("Volume"), MONEY(
      "Money"), COMPOUND("Compound"), UNITLESS("Unitless"), CUSTOM("Custom");

  private String name;

  /**
   * Creates units of a type.
   * 
   * @param name
   *          the name of the unit
   */
  UnitType(final String name)
  {
    this.name = name;
  }

  /**
   * Parses a string into a unit.
   * 
   * @param typeName
   *          the type of unit that it is.
   * @return the unitType.
   */
  public static UnitType parseUnitType(final String typeName)
  {
    for (UnitType unitType : UnitType.values())
    {
      if (unitType.name.equalsIgnoreCase(typeName))
      {
        return unitType;
      }
    }
    // No match
    return CUSTOM;
  }

}
