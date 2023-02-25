package pl.jprabucki.empik.users;

import java.util.Optional;
import org.springframework.stereotype.Service;
import pl.jprabucki.empik.github.GithubService;
import pl.jprabucki.empik.github.GithubService.GithubUser;

/**
 * @author Jakub Prabucki
 */
@Service
class UserService {

  private final GithubService githubService;

  UserService(final GithubService githubService) {this.githubService = githubService;}

  User provideGithubUserInfo(final String login) {
    if (login == null || login.isEmpty())
      throw new IllegalArgumentException("Empty login");

    final Optional<GithubUser> githubUser = githubService.provideUserInfo(login);
    if (githubUser.isPresent()) {
      return fromGithubUser(githubUser.get());
    } else {
      throw new UserNotExists("User not exists: " + login);
    }
  }

  private User fromGithubUser(final GithubUser githubUser) {
    return new User(githubUser.id(), githubUser.login(), githubUser.name(), githubUser.type(), githubUser.avatar_url(),
        githubUser.created_at(), githubUser.followers(), githubUser.public_repos());
  }
}
