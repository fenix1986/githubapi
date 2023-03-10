package pl.jprabucki.empik.users;

import java.time.LocalDateTime;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jprabucki.empik.audit.AuditService;

/**
 * @author Jakub Prabucki
 */
@RestController
@RequestMapping("/users")
public class UserRestController {

  private final UserService userService;
  private final AuditService auditService;

  public UserRestController(final UserService userService, final AuditService auditService) {
    this.userService = userService;
    this.auditService = auditService;
  }

  @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto getByLogin(@PathVariable final String login) {
    if (login == null || login.isEmpty())
      throw new IllegalArgumentException("Empty login");

    auditService.log(login);
    final User user = userService.provideGithubUserInfo(login);

    return fromUser(user, user.calculate());
  }

  private record UserDto(long id, String login, String name, String type, String avatarUrl, LocalDateTime createdAt,
                         Double calculations) {}

  private UserDto fromUser(final User user, final Double calculation) {
    return new UserDto(user.getId(), user.getLogin(), user.getName(), user.getType(), user.getAvatarUrl(),
        user.getCreatedAt(), calculation);
  }
}
