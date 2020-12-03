/**
 * Represents a Screen being created for a media Player.
 *
 * @author Padraig O'Brien
 */
public class Screen implements ScreenSpec {

  String resolution;
  int refreshRate;
  int responseTime;

  /**
   * An instance of a Screen being created.
   *
   * @param resolution   the resolution of the screen
   * @param refreshRate  the refresh rate of the screen
   * @param responseTime the response time of the screen
   */
  public Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * Gets the resolution of the Screen.
   *
   * @return the Resolution of the screen being created
   */
  public String getResolution() {
    return resolution;
  }

  /**
   * Gets the Refresh rate of the Screen.
   *
   * @return the refresh rate of the screen
   */
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * Gets the response time of the Screen.
   *
   * @return the response time of the screen
   */
  public int getResponseTime() {
    return responseTime;
  }

  /**
   * Prints the Screen info into the console.
   */
  public String toString() {
    return "Resolution: " + resolution + "\n"
        + "Refresh rate: " + refreshRate + "\n"
        + "Response time: " + responseTime;
  }
}
