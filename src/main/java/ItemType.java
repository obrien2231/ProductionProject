/**
 * An Interface which keeps track of the different product Item types.
 *
 * @author Padraig O'Brien
 */
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  public final String code;

  /**
   * takes in the item code, and assigns it to a final string.
   *
   * @author Padraig O'Brien
   */
  ItemType(String code) {
    this.code = code;
  }
}
