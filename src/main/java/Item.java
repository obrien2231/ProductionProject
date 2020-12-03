/**
 * An Interface which is used when developing products.
 *
 * @author Padraig O'Brien
 */
public interface Item {

  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();

}
