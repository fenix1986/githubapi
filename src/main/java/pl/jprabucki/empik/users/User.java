package pl.jprabucki.empik.users;

import java.time.LocalDateTime;

/**
 * @author Jakub Prabucki
 */
public class User {

  private final long id;
  private final String login;
  private final String name;
  private final String type;
  private final String avatarUrl;
  private final LocalDateTime createdAt;
  private final long followers;
  private final long public_repos;

  public User(final long id, final String login, final String name, final String type, final String avatarUrl,
      final LocalDateTime createdAt, final long followers, final long publicRepos) {
    this.id = id;
    this.login = login;
    this.name = name;
    this.type = type;
    this.avatarUrl = avatarUrl;
    this.createdAt = createdAt;
    this.followers = followers;
    public_repos = publicRepos;
  }

  long getId() {
    return id;
  }

  String getLogin() {
    return login;
  }

  String getName() {
    return name;
  }

  String getType() {
    return type;
  }

  String getAvatarUrl() {
    return avatarUrl;
  }

  LocalDateTime getCreatedAt() {
    return createdAt;
  }

  Double calculate() {
    if (followers == 0L)
      return Double.NaN;

    return 6.0 / followers * (2.0 + public_repos);
  }
}
