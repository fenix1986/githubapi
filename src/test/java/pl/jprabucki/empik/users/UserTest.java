package pl.jprabucki.empik.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

/**
 * @author Jakub Prabucki
 */
class UserTest {

  @Test
  void calculate() {
    {
      final User user = createUser(0L, 0L);
      final Double result = user.calculate();
      assertEquals(Double.NaN, result);
    }

    {
      final User user = createUser(1L, 0L);
      final Double result = user.calculate();
      assertEquals(12.0, result);
    }

    {
      final User user = createUser(1L, 1L);
      final Double result = user.calculate();
      assertEquals(18.0, result);
    }

    {
      final User user = createUser(5L, 0L);
      final Double result = user.calculate();
      assertEquals(2.4, result);
    }
  }

  private User createUser(final long followers, final long publicRepos) {
    final User user = new User(0, "login", "name", "type", "url", LocalDateTime.now(), followers, publicRepos);
    return user;
  }
}
