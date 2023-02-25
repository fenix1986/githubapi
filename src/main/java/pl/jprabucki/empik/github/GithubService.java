package pl.jprabucki.empik.github;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jakub Prabucki
 */
@Service
public class GithubService {

  private final static String GITHUB_URL = "https://api.github.com";

  final RestTemplate restTemplate;

  public GithubService(final RestTemplate restTemplate) {this.restTemplate = restTemplate;}

  public Optional<GithubUser> provideUserInfo(final String login) {
    if (login == null || login.isEmpty())
      throw new IllegalArgumentException("Empty login");

    try {
      final ResponseEntity<GithubUser> response = restTemplate.getForEntity(GITHUB_URL + "/users/" + login,
          GithubUser.class);

      if (response.getBody() == null)
        throw new RuntimeException("Unexpected response from github");

      return Optional.of(response.getBody());

    } catch (HttpClientErrorException.NotFound ex) {
      return Optional.empty();
    } catch (RestClientException ex) {
      throw new RuntimeException("Unexpected response", ex);
    }
  }

  public record GithubUser(long id, String login, String name, String type, String avatar_url, LocalDateTime created_at,
                           long followers, long public_repos) {}
}
