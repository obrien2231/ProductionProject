/*
 * In this file the controller class is created
 * The controller for the program, it is used to control the user interface
 * Also is used as a way to grab data from the database, and put new data into the database
 *
 * @author Padraig O'Brien
 * @since 2020-09-19
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The controller for the program, it is used to control the user interface Combines the components
 * created in the sample.fxml file together Also is used as a way to grab data from the database,
 * and put new data into the database
 */
public class Controller {

  @FXML
  private TextField columnZeroProductName;

  @FXML
  private TextField columnOneProductName;

  @FXML
  private TextField columnZeroProductManufacturer;

  @FXML
  private TextField columnOneProductManufacturer;

  @FXML
  private ChoiceBox<String> columnZeroItemType;

  @FXML
  private ChoiceBox<String> columnOneItemType;

  @FXML
  private ComboBox<String> produceCmbQuantity;

  @FXML
  public void addProduct() {
    connectToDb();
    System.out.println("Product Added!");
  }

  @FXML
  public void recordProduction() {
    System.out.println("Recording has Started!");
  }

  /**
   * This method runs right away any time the application is run. It is currently being usd to
   * populate the choice and combo box options in the UI
   *
   * @author Padraig O'Brien
   */
  public void initialize() {
    for (int count = 1; count <= 10; count++) {
      produceCmbQuantity.getItems().add(String.valueOf(count));
    }
    produceCmbQuantity.setEditable(true);   // allows users to edit info in the combo box
    produceCmbQuantity.getSelectionModel().selectFirst(); // makes default in combo box 1

    // adds options into the choice boxes
    columnZeroItemType.getItems().add("Audio");
    columnZeroItemType.getItems().add("Video");

    columnOneItemType.getItems().add("Audio");
    columnOneItemType.getItems().add("Video");
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
    Connection conn = null;
    Statement stmt = null;

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

      preparedStatement.setString(1, columnZeroProductName.getText());
      preparedStatement.setString(2, columnZeroItemType.getSelectionModel()
          .getSelectedItem()); // used stack overflow to find correct way to String

      preparedStatement.setString(3, columnZeroProductManufacturer.getText());

      // adds new product into the database
      preparedStatement.executeUpdate();

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

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }


  }


}