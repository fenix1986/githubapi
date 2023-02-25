package pl.jprabucki.empik.users;

/**
 * @author Jakub Prabucki
 */
public class UserNotExists extends RuntimeException {

  public UserNotExists(final String message) {
    super(message);
  }
}
