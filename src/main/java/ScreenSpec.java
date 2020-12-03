/**
 * An interface which keeps track of different specifications of a screen.
 *
 * @author Padraig O'Brien
 */
public interface ScreenSpec {

  String getResolution();

  int getRefreshRate();

  int getResponseTime();

}
