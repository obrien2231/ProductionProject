/**
 * Represents an audioplayer with basic media controls. This class is a subclass of the Product
 * class and implements the MultimediaControl class
 *
 * @author Padraig O'Brien
 */
public class AudioPlayer extends Product implements MultimediaControl {

  String supportedAudioFormats;
  String supportedPlaylistFormats;

  /**
   * A constructor method which creates an instance of Audio Player.
   *
   * @param name                     This Audio Player's name
   * @param manufacturer             The manufacturer of this Audio Player
   * @param supportedAudioFormats    The audio formats supported by this Audio Player
   * @param supportedPlaylistFormats The playlist formats supported by this Audio Player
   */
  public AudioPlayer(String name, String manufacturer,
      String supportedAudioFormats, String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Notifies the user that the Audio player is playing.
   */
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Notifies the user that the Audio player has stopped playing.
   */
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Notifies the user that the Audio player has switched to the previous song.
   */
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Notifies the user that the Audio player has switched to the next song.
   */
  public void next() {
    System.out.println("Next");
  }

  /**
   * A method which prints the super classes toString and then prints the supported formats.
   */
  public String toString() {
    return super.toString()
        + "Supported Audio Formats: " + supportedAudioFormats + "\n"
        + "Supported Playlist Formats: " + supportedPlaylistFormats;
  }
}
