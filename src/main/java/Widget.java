/**
 * A widget object used in transferring product information. A subclass of Product
 *
 * @author Padraig O'Brien
 */
class Widget extends Product {

  /**
   * Creates an instance of a widget used for transferring product data.
   *
   * @param id           The id of the Widget/Product
   * @param name         The name of the Widget/Product
   * @param manufacturer The name of the manufacturer of the Widget/Product
   * @param type         The type of the Widget/Product
   */
  public Widget(int id, String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);
    this.id = id;

  }
}