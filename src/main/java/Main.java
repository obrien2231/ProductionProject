/*
 * This file is the main file for the program
 *
 * @author Padraig O'Brien
 * @since 2020-09-19
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class contains the main method for the program.
 *
 * @author Padraig O'Brien
 */
public class Main extends Application {

  /**
   * Main method is the main entrypoint for the application.
   */
  public static void main(String[] args) {
    launch(args);

  }

  /**
   * Start Method sets up our Graphical User Interface.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Scene scene = new Scene(root, 600, 600);
    scene.getStylesheets().add("demo.css");

    primaryStage.setTitle("O'Brien Production Project");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

}

