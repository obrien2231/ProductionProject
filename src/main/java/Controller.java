/*
 * In this file the controller class is created
 * The controller for the program, it is used to control the user interface
 * Also is used as a way to grab data from the database, and put new data into the database
 *
 * @author Padraig O'Brien
 * @since 2020-09-19
 */
import java.time.Instant;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.util.Date;
import java.sql.Timestamp;



/**
 * The controller for the program, it is used to control the user interface Combines the components
 * created in the sample.fxml file together Also is used as a way to grab data from the database,
 * and put new data into the database
 */
public class Controller {

  ObservableList<Product> productLine = FXCollections.observableArrayList();
  ArrayList<ProductionRecord> productionLog = new ArrayList<ProductionRecord>();
  ArrayList<ItemType> itemTypesCount = new ArrayList<ItemType>();



  @FXML
  private TextField columnOneProductName;

  @FXML
  private TextField columnOneProductManufacturer;

  @FXML
  private ChoiceBox<String> columnOneItemType;

  @FXML
  private TableView<Product> productLineTable;

  @FXML
  private TableColumn<?, ?> productLineTableCol1;

  @FXML
  private TableColumn<?, ?> productLineTableCol2;

  @FXML
  private TableColumn<?, ?> productLineTableCol3;

  @FXML
  private TableColumn<?,?> productLineTableCol4;

  @FXML
  private ListView<Product> produceTabListView;

  @FXML
  private ComboBox<String> produceCmbQuantity;

  @FXML
  private TextArea productionRecord;


  @FXML
  public void addProduct(){
    connectToDb();
    productLineTable.getItems().clear();
    try {
      loadProductList();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    System.out.println("Product Added!");
  }

  @FXML
  public void recordProduction() throws SQLException {
    Product pr = produceTabListView.getSelectionModel().getSelectedItem();
    int quantityCount = Integer.parseInt(produceCmbQuantity.getSelectionModel().getSelectedItem());
    System.out.println(pr);
    System.out.println(quantityCount);
    ArrayList<ProductionRecord> productionRun = new ArrayList<ProductionRecord>();
    int itemCount = 0;
      if (pr.getType() == ItemType.AUDIO){
        itemTypesCount.add(ItemType.AUDIO);
      }
      if (pr.getType() == ItemType.AUDIO_MOBILE){
        itemTypesCount.add(ItemType.AUDIO_MOBILE);
      }
      if (pr.getType() == ItemType.VISUAL){
        itemTypesCount.add(ItemType.VISUAL);
      }
      if (pr.getType() == ItemType.VISUAL_MOBILE){
        itemTypesCount.add(ItemType.VISUAL_MOBILE);
      }

    for(int i = 0; i<quantityCount;i++){
      for(int j = 0;i < itemTypesCount.size(); i++){
        if(pr.getType() == ItemType.AUDIO){
          if(itemTypesCount.get(j) == ItemType.AUDIO){
            itemCount++;
            }
        }
        if(pr.getType() == ItemType.VISUAL){
        if(itemTypesCount.get(j) == ItemType.VISUAL){
          itemCount++;
        }}
        if(pr.getType() == ItemType.AUDIO_MOBILE){
        if(itemTypesCount.get(j) == ItemType.AUDIO_MOBILE){
          itemCount++;
        }}
        if(pr.getType() == ItemType.VISUAL_MOBILE){
        if(itemTypesCount.get(j) == ItemType.VISUAL_MOBILE){
          itemCount++;
        }}}
        ProductionRecord pr1 = new ProductionRecord(pr,itemCount);
        productionRun.add(pr1);
      }


    addToProductionDB(productionRun);
    System.out.println("Recording has Started!");
    productionLog.clear();
    loadProductionLog();
  }


  /**
   * This method runs right away any time the application is run. It is currently being usd to
   * populate the choice and combo box options in the UI
   *
   * @author Padraig O'Brien
   */
  public void initialize(){

    productLineTableCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
    productLineTableCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
    productLineTableCol3.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    productLineTableCol4.setCellValueFactory(new PropertyValueFactory<>("type"));
    productLineTable.setItems(productLine);

    produceTabListView.setItems(productLine);

    try {
      loadProductList();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    try {
      loadProductionLog();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    showProduction(productionLog);

    productionRecord.setEditable(false);

    for (int count = 1; count <= 10; count++) {
      produceCmbQuantity.getItems().add(String.valueOf(count));
    }
    produceCmbQuantity.setEditable(true);   // allows users to edit info in the combo box
    produceCmbQuantity.getSelectionModel().selectFirst(); // makes default in combo box 1

    // adds options into the choice boxes
    for (ItemType it : ItemType.values()) {
      columnOneItemType.getItems().add(it.code);
    }


  }

  /**
   * This method is called when the add product button is hit. The purpose of this method is to
   * connect to the database and add new products into the database. As well as, pull info from the
   * database and print it into the console
   *
   * @author Padraig O'Brien
   */
  public void connectToDb() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/PD";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER,
          PASS); // spot bugs flags this for no password, will receive password later

      //STEP 3: Execute a query
      stmt = conn.createStatement();
      // spot bugs flags this however it is a false positive, statement is closed in step 4

      // used the jdbc prepared statement tutorial off of tutorials.jenkov.com for section below
      String addProduct = "Insert INTO PRODUCT set name=? , type=? ,"
          + " manufacturer=?";

      PreparedStatement preparedStatement = conn.prepareStatement(addProduct);

      preparedStatement.setString(1, columnOneProductName.getText());
      preparedStatement.setString(2,
          columnOneItemType.getSelectionModel().getSelectedItem()); // used stack overflow
      preparedStatement.setString(3, columnOneProductManufacturer.getText());
      // adds new product into the database
      preparedStatement.executeUpdate();

      String printDatabase = "SELECT * FROM PRODUCT";
      ResultSet rs = stmt.executeQuery(printDatabase);

      while (rs.next()) {
        System.out.println(rs.getString(3) + " " + rs.getString(4)
            + " " + rs.getString(
            2));
        // adjusted column order above so product would print in console type, manufacturer, name
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
      preparedStatement
          .close(); //closed prepared statement because it could cause problems in the future

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }
  private void loadProductList() throws SQLException{
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/PD";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;


    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER,
          PASS); // spot bugs flags this for no password, will receive password later

      //STEP 3: Execute a query
      stmt = conn.createStatement();
      // spot bugs flags this however it is a false positive, statement is closed in step 4


      String sql = "SELECT * FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {

        // these lines correspond to the database table columns

        int id = rs.getInt(1);

        String name = rs.getString(2);

        String manufacturer = rs.getString(4);

        String type = rs.getString(3);


        // create object
        switch (type) {
          case "AU":
            Product product1 = new Widget(id, name,
                manufacturer, ItemType.AUDIO);
            System.out.println(product1);
            productLine.add(product1);
            break;
          case "VI":
            Product product2 = new Widget(id, name,
                manufacturer, ItemType.VISUAL);
            System.out.println(product2);
            productLine.add(product2);
            break;
          case "AM":
            Product product3 = new Widget(id, name,
                manufacturer, ItemType.AUDIO_MOBILE);
            System.out.println(product3);
            productLine.add(product3);
            break;
          case "VM":
            Product product4 = new Widget(id, name,
                manufacturer, ItemType.VISUAL_MOBILE);
            System.out.println(product4);
            productLine.add(product4);
            break;
        }

        // save to observable list
      }
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
  private void loadProductionLog() throws SQLException{
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/PD";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;


    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER,
          PASS); // spot bugs flags this for no password, will receive password later

      //STEP 3: Execute a query
      stmt = conn.createStatement();
      // spot bugs flags this however it is a false positive, statement is closed in step 4


      String sql = "SELECT * FROM PRODUCTIONRECORD";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {

        // these lines correspond to the database table columns

        int productionNum = rs.getInt(1);

        int prodID = rs.getInt(2);

        String serialNum = rs.getString(3);

        Date dateProduced = rs.getDate(4);

        ProductionRecord pr = new ProductionRecord(productionNum,prodID,serialNum,dateProduced);
        productionLog.add(pr);
        }

      showProduction(productionLog);

        // save to observable list
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
  public void showProduction(ArrayList<ProductionRecord> productionLog) {
    productionRecord.clear();
    for (ProductionRecord pr1 : productionLog) {
      productionRecord.appendText(pr1.toString());
    }
  }
  public void addToProductionDB(ArrayList<ProductionRecord> productionRun){
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/PD";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER,
          PASS); // spot bugs flags this for no password, will receive password later

      //STEP 3: Execute a query
      stmt = conn.createStatement();
      // spot bugs flags this however it is a false positive, statement is closed in step 4

      // used the jdbc prepared statement tutorial off of tutorials.jenkov.com for section below
      for(int i = 0; i<productionRun.size();i++) {
        String addProductionRecord =
            "Insert INTO PRODUCTIONRECORD set product_id=? ,"
                + " serial_num=? , date_produced=?";

        PreparedStatement preparedStatement = conn.prepareStatement(addProductionRecord);

        int prodId = productionRun.get(i).getProductID();

        preparedStatement.setString(1, String.valueOf(prodId)); // used stack overflow
        preparedStatement.setString(2, productionRun.get(i).getSerialNum());
        preparedStatement.setString(3,
            String.valueOf(productionRun.get(i).getProdDate()));
        // adds new product into the database
        preparedStatement.executeUpdate();
      }




      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }
}