public abstract class Product implements Item {

  int id;
  ItemType type;
  String manufacturer;
  String name;


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public Product( String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: "
        + type + "\n";
  }
}
class Widget extends Product{

  public Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer,type);


  }
}
