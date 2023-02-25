package pl.jprabucki.empik.users;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jakub Prabucki
 */
@RestController
@RequestMapping("/users")
public class UserRestController {

  @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto getByLogin(@PathVariable final String login) {
    if (login == null || login.isEmpty())
      throw new IllegalArgumentException ("Empty login");

    return new UserDto(0, login, "name", "type", "avatarUrl", LocalDateTime.now(), Double.NaN);
  }

  private record UserDto(long id, String login, String name, String type, String avatarUrl, LocalDateTime createdAt,
                         Double calculations) {}
}
