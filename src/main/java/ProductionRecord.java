import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

/**
 * Represents a Product Record being created.
 *
 * @author Padraig O'Brien
 */
public class ProductionRecord {

  int productionNumber;
  int productID;
  String serialNumber;
  Date dateProduced;

  /**
   * creates an instance of a blank Production Record.
   *
   * @param productID the Production record product's ID
   */
  public ProductionRecord(int productID) {
    this.productionNumber = 0;
    this.productID = productID;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  /**
   * creates an instance of a detailed Production Record.
   *
   * @param productionNumber The production number of the produced product
   * @param productID        the Production record product's ID
   * @param serialNumber     The serial number of the product being produced
   * @param dateProduced     The date the product was produced
   */
  public ProductionRecord(int productionNumber, int productID, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date(dateProduced.getTime());
  }

  /**
   * creates an instance of a detailed Production Record.
   *
   * @param pr    The Product being produced
   * @param count the count of how many of this product have been produced
   */
  public ProductionRecord(Product pr, int count) {
    this.productID = pr.getId();
    this.serialNumber = pr.getManufacturer().substring(0, 3)
        + pr.getType().code + String.format("%05d", count);
    this.dateProduced = Timestamp.from(Instant.now());
  }

  /**
   * Gets the Production Number of the Production Record.
   *
   * @return the Production Number
   */
  public int getProductionNum() {
    return productionNumber;
  }

  /**
   * Changes the production number of the production record.
   *
   * @param productionNumber the production record's new production number
   */
  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Gets the Production Record Product's ID.
   *
   * @return The ID of the product being produced
   */
  public int getProductID() {
    return productID;
  }

  /**
   * Changes the Id of the product being produced.
   *
   * @param productID This product's new ID
   */
  public void setProductID(int productID) {
    this.productID = productID;
  }

  /**
   * Gets serial number of the Product being produced.
   *
   * @return the product's serial number
   */
  public String getSerialNum() {
    return serialNumber;
  }

  /**
   * Changes the serial number of the product being produced.
   *
   * @param serialNumber This product's new serial number.
   */
  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Gets the date which the product was produced.
   *
   * @return the product's Manufactured date
   */
  public Date getProdDate() {
    return new Timestamp(dateProduced.getTime());
  }

  /**
   * Changes the date the product was produced.
   *
   * @param dateProduced The product's new production date
   */
  public void setProdDate(Date dateProduced) {
    this.dateProduced = new Date(dateProduced.getTime());
  }

  /**
   * Prints all the product being produced details into the console.
   */
  @Override
  public String toString() {
    return
        "Prod. Num: " + productionNumber
            + " Product ID: " + productID
            + " Serial Num: " + serialNumber
            + " Date: " + dateProduced + "\n";
  }

}
