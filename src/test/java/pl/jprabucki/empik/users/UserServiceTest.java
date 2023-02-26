package pl.jprabucki.empik.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.jprabucki.empik.github.GithubService;
import pl.jprabucki.empik.github.GithubService.GithubUser;

/**
 * @author Jakub Prabucki
 */
class UserServiceTest {

  @Mock
  private GithubService githubService;

  private UserService userService;

  @BeforeEach
  void setUp() {
    openMocks(this);
    userService = new UserService(githubService);
  }

  @Test
  void provideGithubUserInfo() {
    final String login = "test";
    final LocalDateTime now = LocalDateTime.now();
    GithubUser githubUser = new GithubUser(0L, login, "name", "type", "url", now, 12, 15);
    when(githubService.provideUserInfo(login)).thenReturn(Optional.of(githubUser));

    final User actualUser = userService.provideGithubUserInfo(login);

    final User expectedUser = new User(0L, login, "name", "type", "url", now, 12, 15);
    assertEquals(expectedUser, actualUser);

    assertThrows(IllegalArgumentException.class, () -> userService.provideGithubUserInfo(null));
    assertThrows(IllegalArgumentException.class, () -> userService.provideGithubUserInfo(""));

    {
      when(githubService.provideUserInfo(login)).thenReturn(Optional.empty());
      assertThrows(UserNotExists.class, () -> userService.provideGithubUserInfo(login));
      when(githubService.provideUserInfo(login)).thenReturn(Optional.of(githubUser));
    }
  }
}
