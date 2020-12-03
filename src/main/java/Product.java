/**
 * Represents a Product being created.
 *
 * @author Padraig O'Brien
 */
public abstract class Product implements Item {

  int id;
  ItemType type;
  String manufacturer;
  String name;

  /**
   * Gets the product's ID.
   *
   * @return the product's ID
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the product's name.
   *
   * @return the product's name
   */
  public String getName() {
    return name;
  }

  /**
   * Changes the name of the Product.
   *
   * @param name This product's new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the product's Manufacturer.
   *
   * @return the product's Manufacturer
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Changes the manufacturer of the Product.
   *
   * @param manufacturer This product's new manufacturer
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Creates an instance of Product, and sets all the Product's information.
   *
   * @param name         The product's name
   * @param manufacturer The Product's manufacturer
   * @param type         The product's type
   */
  public Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Gets the product's type or function.
   *
   * @return the product's type
   */
  public ItemType getType() {
    return type;
  }

  /**
   * Changes the type of the Product.
   *
   * @param type This product's new type
   */
  public void setType(ItemType type) {
    this.type = type;
  }

  /**
   * Prints all the product's details in the console.
   */
  public String toString() {
    return "\nName: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: "
        + type;
  }
}
