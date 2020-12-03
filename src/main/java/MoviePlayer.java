/**
 * Represents a Movie Player with basic media controls. This class is a subclass of the Product
 * class and implements the MultimediaControl class.
 *
 * @author Padraig O'Brien
 */
public class MoviePlayer extends Product implements MultimediaControl {

  Screen screen;
  MonitorType monitorType;

  /**
   * A constructor method which creates an instance of a Movie Player.
   *
   * @param name         This Movie Player's name
   * @param manufacturer The manufacturer of this Movie Player
   * @param screen       The Screen on which the movie player is playing and it's information
   * @param monitorType  The type of monitor which the movie player is using
   */
  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * Notifies the user that the Movie player is playing the movie.
   */
  public void play() {
    System.out.println("Playing movie");
  }

  /**
   * Notifies the user that the movie player has stopped playing the movie.
   */
  public void stop() {
    System.out.println("Stopping movie");
  }

  /**
   * Notifies the user that the Movie player has switched to the previous movie.
   */
  public void previous() {
    System.out.println("Previous movie");
  }

  /**
   * Notifies the user that the Movie Player has switched to the next movie.
   */
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * A method which prints the super classes toString, then prints screen and monitor information.
   */
  public String toString() {
    return
        super.toString()
            + "Screen: " + screen + "\n"
            + "MonitorType: " + monitorType;
  }

}

