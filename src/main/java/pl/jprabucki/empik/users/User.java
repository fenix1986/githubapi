package pl.jprabucki.empik.users;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jakub Prabucki
 */
class User {

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

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final User user = (User) o;

    if (id != user.id) return false;
    if (followers != user.followers) return false;
    if (public_repos != user.public_repos) return false;
    if (!Objects.equals(login, user.login)) return false;
    if (!Objects.equals(name, user.name)) return false;
    if (!Objects.equals(type, user.type)) return false;
    if (!Objects.equals(avatarUrl, user.avatarUrl)) return false;
    return Objects.equals(createdAt, user.createdAt);
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (login != null ? login.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (int) (followers ^ (followers >>> 32));
    result = 31 * result + (int) (public_repos ^ (public_repos >>> 32));
    return result;
  }
}
