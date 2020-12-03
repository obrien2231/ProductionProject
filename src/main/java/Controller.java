/*
 * In this file the controller class is created
 * The controller for the program, it is used to control the user interface
 * Also is used as a way to grab data from the database, and put new data into the database
 *
 * @author Padraig O'Brien
 * @since 2020-09-19
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * The controller for the program, it is a multiuse class for displaying database info, and adding
 * info to the database.
 *
 * @author Padraig O'Brien
 */
public class Controller {

  ObservableList<Product> productLine = FXCollections.observableArrayList();
  ArrayList<ProductionRecord> productionLog = new ArrayList<>();
  ArrayList<ItemType> itemTypesCount = new ArrayList<>(); // keeps track of itemtypes for serialnum
  String reverse = ""; // string is created for password encryption used in reverse string method


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
  private TableColumn<?, ?> productLineTableCol4;

  @FXML
  private ListView<Product> produceTabListView;

  @FXML
  private ComboBox<String> produceCmbQuantity;

  @FXML
  private TextArea productionRecord;

  @FXML
  private TextField employeeName;

  @FXML
  private TextField employeePassword;

  /**
   * This method adds an employee to the database using the data obtained from the GUI, when the add
   * Employee button is pressed.
   */
  @FXML
  public void addEmployee() {
    final String JdbcDriver = "org.h2.Driver";
    final String DbUrl = "jdbc:h2:./res/PD";

    //  Database credentials
    final String user = "";
    final String pass = getPassword();
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JdbcDriver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DbUrl, user,
          pass); // spot bugs flags this for no password, will receive password later

      //STEP 3: Execute a query
      stmt = conn.createStatement();
      // spot bugs flags this however it is a false positive, statement is closed in step 4

      // used the jdbc prepared statement tutorial off of tutorials.jenkov.com for section below
      String addEmployee = "Insert INTO EMPLOYEES set name=? , username=? ,"
          + " email=? , password=?";
      // prevents error if a field is left blank
      if (employeeName.getText().length() == 0 || employeePassword.getText().length() == 0) {
        System.out.println("\nPlease fill both fields and try again!");
      } else {

        PreparedStatement preparedStatement = conn.prepareStatement(addEmployee);
        Employee newEmployee = new Employee(employeeName.getText(), employeePassword.getText());

        preparedStatement.setString(1, employeeName.getText());
        preparedStatement.setString(2, newEmployee.username); // used stack overflow
        preparedStatement.setString(3, newEmployee.email);
        preparedStatement.setString(4, newEmployee.password);
        // adds new product into the database
        preparedStatement.executeUpdate();

        System.out.println("\nEmployee Added! Details provided below \n");
        System.out.println(newEmployee.toString());

        preparedStatement
            .close(); //closed prepared statement because it could cause problems in the future
      }
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();


    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method add a product to the database using the data obtained from the GUI, when the add
   * Product button is pressed.
   */
  @FXML
  public void addProduct() {
    final String JdbcDriver = "org.h2.Driver";
    final String DbUrl = "jdbc:h2:./res/PD";

    //  Database credentials
    final String user = "";
    final String pass = getPassword();
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JdbcDriver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DbUrl, user,
          pass); // spot bugs flags this for no password, will receive password later

      //STEP 3: Execute a query
      stmt = conn.createStatement();
      // spot bugs flags this however it is a false positive, statement is closed in step 4

      // used the jdbc prepared statement tutorial off of tutorials.jenkov.com for section below
      String addProduct = "Insert INTO PRODUCT set name=? , type=? ,"
          + " manufacturer=?";

      PreparedStatement preparedStatement = conn.prepareStatement(addProduct);
      if (columnOneProductName.getText().length() == 0
          || columnOneProductManufacturer.getText().length() == 0) {
        System.out.println("\nPlease fill out both fields and try again!");
      } else {
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
      }
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
      preparedStatement
          .close(); //closed prepared statement because it could cause problems in the future

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    // prevents program from running items with empty fields
    if (columnOneProductName.getText().length() == 0
        || columnOneProductManufacturer.getText().length() == 0) {
      System.out.println(" ");
    } else {
      productLineTable.getItems().clear();
      try {
        loadProductList();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      System.out.println("Product Added!");
    }
  }

  /**
   * This method creates a Production record for a Select Product from the GUI.
   */
  @FXML
  public void recordProduction() throws SQLException {
    // used to prevent null pointer exception, code help from stack overflow
    if (produceTabListView.getSelectionModel().getSelectedItem() != null) {
      Product pr = produceTabListView.getSelectionModel().getSelectedItem();
      int quantityCount = Integer
          .parseInt(produceCmbQuantity.getSelectionModel().getSelectedItem());
      System.out.println(pr);
      System.out.println(quantityCount);
      ArrayList<ProductionRecord> productionRun = new ArrayList<>();
      int itemCount;
      if (pr.getType() == ItemType.AUDIO) {
        itemTypesCount.add(ItemType.AUDIO);
      }
      if (pr.getType() == ItemType.AUDIO_MOBILE) {
        itemTypesCount.add(ItemType.AUDIO_MOBILE);
      }
      if (pr.getType() == ItemType.VISUAL) {
        itemTypesCount.add(ItemType.VISUAL);
      }
      if (pr.getType() == ItemType.VISUAL_MOBILE) {
        itemTypesCount.add(ItemType.VISUAL_MOBILE);
      }

      for (int i = 0; i < quantityCount; i++) { // item count loop is used for the serialnum
        itemCount = i;
        for (ItemType itemType : itemTypesCount) {
          if (pr.getType() == ItemType.AUDIO) {
            if (itemType == ItemType.AUDIO) {
              itemCount++;
            }
          }
          if (pr.getType() == ItemType.VISUAL) {
            if (itemType == ItemType.VISUAL) {
              itemCount++;
            }
          }
          if (pr.getType() == ItemType.AUDIO_MOBILE) {
            if (itemType == ItemType.AUDIO_MOBILE) {
              itemCount++;
            }
          }
          if (pr.getType() == ItemType.VISUAL_MOBILE) {
            if (itemType == ItemType.VISUAL_MOBILE) {
              itemCount++;
            }
          }
        }
        ProductionRecord pr1 = new ProductionRecord(pr, itemCount);
        productionRun.add(pr1);
      }

      addToProductionDB(productionRun);
      System.out.println("Recording has Started!");
      productionLog.clear();
      loadProductionLog();
    } else {
      System.out.println("\nPlease select the Product you would like to produce");
    }
  }


  /**
   * This method runs right away any time the application is run. The method populates the tables
   * and text boxes with database info.
   */
  public void initialize() {

    productLineTableCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
    productLineTableCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
    productLineTableCol3.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    productLineTableCol4.setCellValueFactory(new PropertyValueFactory<>("type"));
    productLineTable.setItems(productLine);

    produceTabListView.setItems(productLine);

    testMultimedia(); // tests multimedia classes to check if working

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
    columnOneItemType.getSelectionModel().selectFirst(); // makes default audio

  }

  /**
   * This method loads the product line observable array list which will eventually be used to load
   * the product line table.
   */
  private void loadProductList() throws SQLException {
    final String JdbcDriver = "org.h2.Driver";
    final String DbUrl = "jdbc:h2:./res/PD";

    //  Database credentials

    final String user = "";
    final String pass = getPassword();
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JdbcDriver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DbUrl, user,
          pass); // spot bugs flags this for no password, will receive password later

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
          default:
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

  /**
   * This method pulls production records from the database and adds them to the production log
   * arraylist.
   */
  private void loadProductionLog() throws SQLException {
    final String JdbcDriver = "org.h2.Driver";
    final String DbUrl = "jdbc:h2:./res/PD";

    //  Database credentials
    final String user = "";
    final String pass = getPassword();
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JdbcDriver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DbUrl, user,
          pass); // spot bugs flags this for no password, will receive password later

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

        Date dateProduced = rs.getTimestamp(4);

        ProductionRecord pr = new ProductionRecord(productionNum, prodID, serialNum, dateProduced);

        productionLog.add(pr);
      }
      itemTypesCount.clear();
      for (ProductionRecord pr1 : productionLog) { // used to check for serial nums already in db
        switch (pr1.getSerialNum().substring(3, 5)) {
          case "AU":
            itemTypesCount.add(ItemType.AUDIO);
            break;
          case "VI":
            itemTypesCount.add(ItemType.VISUAL);
            break;
          case "AM":
            itemTypesCount.add(ItemType.AUDIO_MOBILE);
            break;
          case "VM":
            itemTypesCount.add(ItemType.VISUAL_MOBILE);
            break;
          default:
            break;
        }
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

  /**
   * This method pulls production records from the database and adds them to the production log
   * arraylist.
   */
  public void showProduction(ArrayList<ProductionRecord> productionLog) {
    productionRecord.clear();
    for (ProductionRecord pr1 : productionLog) {
      productionRecord.appendText(pr1.toString());
    }
  }

  /**
   * This method uses the production run arraylist to populate the production record Database.
   *
   * @param productionRun An array list filled with Production records
   */
  public void addToProductionDB(ArrayList<ProductionRecord> productionRun) {
    final String JdbcDriver = "org.h2.Driver";
    final String DbUrl = "jdbc:h2:./res/PD";

    //  Database credentials
    final String user = "";
    final String pass = getPassword();
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JdbcDriver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DbUrl, user,
          pass); // spot bugs flags this for no password, will receive password later

      //STEP 3: Execute a query
      stmt = conn.createStatement();
      // spot bugs flags this however it is a false positive, statement is closed in step 4

      // used the jdbc prepared statement tutorial off of tutorials.jenkov.com for section below
      for (ProductionRecord record : productionRun) {
        String addProductionRecord =
            "Insert INTO PRODUCTIONRECORD set product_id=? ,"
                + " serial_num=? , date_produced=?";

        PreparedStatement preparedStatement = conn.prepareStatement(addProductionRecord);

        int prodId = record.getProductID();

        preparedStatement.setString(1, String.valueOf(prodId)); // used stack overflow
        preparedStatement.setString(2, record.getSerialNum());
        preparedStatement.setTimestamp(3,
            (Timestamp) record.getProdDate());
        // adds new product into the database
        preparedStatement.executeUpdate();

        preparedStatement.close();
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();


    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * This method retrieves the database password from the text file containing it.
   */
  public String getPassword() {
    String password = "";
    // used w3schools read java files page for code used below
    try {
      File myPassword = new File("src/main/resources/password.txt");
      Scanner myReader = new Scanner(myPassword, "UTF-8");
      while (myReader.hasNextLine()) {
        password = myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    reverse = "";
    return reverseString(password);
  }


  /**
   * This method is used to reverse the text from the password text file for the database password.
   */
  public String reverseString(String pw) {
    if ((pw == null) || (pw.length() <= 0)) {  // Recursion Reversing a String
      return reverse;
    } else {
      reverse = reverse + pw.charAt(pw.length() - 1);
      reverseString(pw.substring(0, pw.length() - 1));
    }
    return reverse;
  }

  /**
   * This method is used to test the multimedia classes.
   */
  public static void testMultimedia() {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",

        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC",
        "M3U/PLS/WPL");

    Screen newScreen = new Screen("720x480", 40, 22);

    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101",
        "OracleProduction", newScreen,

        MonitorType.LCD);

    ArrayList<MultimediaControl> productList = new ArrayList<>();

    productList.add(newAudioProduct);

    productList.add(newMovieProduct);

    for (MultimediaControl p : productList) {

      System.out.println(p);

      p.play();

      p.stop();

      p.next();

      p.previous();

    }

  }
}