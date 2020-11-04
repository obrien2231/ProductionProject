import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
public class ProductionRecord {
  int productionNumber;
  int ProductID;
  String serialNumber;
  Date dateProduced;

  public ProductionRecord(int productID) {
    this.productionNumber = 0;
    ProductID = productID;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  public ProductionRecord(int productionNumber, int productID, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    ProductID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  public ProductionRecord(Product pr, int count){
    this.ProductID = pr.getId();
    this.serialNumber = pr.getManufacturer().substring(0,3) +
        pr.getType().code + String.format("%05d",count);
    this.dateProduced = Timestamp.from(Instant.now());

  }

  public int getProductionNum() {
    return productionNumber;
  }

  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductID() {
    return ProductID;
  }

  public void setProductID(int productID) {
    ProductID = productID;
  }

  public String getSerialNum() {
    return serialNumber;
  }

  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Date getProdDate() {
    return dateProduced;
  }

  public void setProdDate(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  @Override
  public String toString() {
    return
        "Prod. Num: " + productionNumber +
        " Product ID: " + ProductID +
        " Serial Num: " + serialNumber +
        " Date: " + dateProduced + "\n";
  }

}
