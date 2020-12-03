/**
 * Represents an Employee of the Production company.
 *
 * @author Padraig O'Brien
 */
public class Employee {

  StringBuilder name;
  String username;
  String password;
  String email;

  /**
   * Sets the employee's username using their full name.
   *
   * @param name the employee's name
   */
  public void setUsername(String name) {
    int index = name.indexOf(" ");
    this.username = name.substring(0, 1).toLowerCase()
        + name.substring(index + 1).toLowerCase();
  }

  /**
   * Sets the employee's email using their full name.
   *
   * @param name the employee's name
   */
  public void setEmail(String name) {
    int index = name.indexOf(" ");
    email = name.substring(0, index).toLowerCase() + "." + name.substring(index + 1).toLowerCase()
        + "@oracleacademy.Test";
  }

  /**
   * Checks if the name given to the program fills the documentation requirements.
   *
   * @param name the employee's name
   */
  private boolean checkName(String name) {
    boolean hasSpace;
    int index = name.indexOf(" ");
    hasSpace = index > 0;
    return hasSpace;
  }

  /**
   * Checks if the password fulfills the given requirements.
   *
   * @param password the password given by the Employee
   */
  private boolean isValidPassword(String password) {
    boolean hasLower = false;
    boolean hasUpper = false;
    boolean hasSpecial = false;
    boolean pwIsValid;
    int count = 1;
    for (int i = 0; i < password.length(); i++) {

      if (password.substring(i, count).matches("[a-z]")) {
        hasLower = true;
      }
      if (password.substring(i, count).matches("[A-Z]")) {
        hasUpper = true;
      }
      if (password.substring(i, count).matches("[^a-zA-Z0-9]")) {
        hasSpecial = true;
      }
      count++;
    }

    pwIsValid = hasLower && hasUpper && hasSpecial;
    return pwIsValid;
  }

  /**
   * Creates an instance of employee, and sets all the employee info.
   *
   * @param name     the employee's first and last name
   * @param password the password given by the Employee
   */
  public Employee(String name, String password) {
    StringBuilder name1 = new StringBuilder();
    this.name = name1;
    name1.append(name);
    boolean hasSpace = checkName(name);
    if (hasSpace) {
      setUsername(name);
      setEmail(name);
    } else {
      this.username = "default";
      this.email = "user@oracleacademy.Test";
    }
    boolean pwIsValid = isValidPassword(password);
    if (pwIsValid) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * This method creates a Production record for a Select Product from the GUI.
   */
  @Override
  public String toString() {
    return "Employee Details\n"
        + "Name : " + name + "\n"
        + "Username : " + username + "\n"
        + "Email : " + email + "\n"
        + "Initial Password : " + password;
  }
}
