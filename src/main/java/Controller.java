
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;




public class Controller implements Initializable {

  @FXML
  Tab productLineTab;

  @FXML
  Tab produceTab;
  @FXML
  Tab productionLogTab;


  @Override
  public void initialize(URL location, ResourceBundle resources) {

    FXMLLoader loader = new FXMLLoader();
    try {
      AnchorPane anch1 = loader.load(getClass().getResource("ProductLineTab.fxml"));
      productLineTab.setContent(anch1);
    } catch (IOException iex) {
      System.out.println("File not found");
    }
    loader = new FXMLLoader();
    try {
      AnchorPane anch2 = loader.load(getClass().getResource("ProduceTab.fxml"));
      produceTab.setContent(anch2);
    } catch (IOException iex) {
      System.out.println("File not found");
    }
    loader = new FXMLLoader();
    try {
      AnchorPane anch3 = loader.load(getClass().getResource("ProductionLogTab.fxml"));
      productionLogTab.setContent(anch3);
    } catch (IOException iex) {
      System.out.println("File not found");
    }
  }
}
