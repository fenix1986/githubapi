package pl.jprabucki.empik.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import pl.jprabucki.empik.github.GithubService.GithubUser;

/**
 * @author Jakub Prabucki
 */

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void getByLogin() throws Exception {
    final LocalDateTime now = LocalDateTime.of(2023, 2, 26, 20, 51, 12);
    final String login = "octocat";

    GithubUser githubUser = new GithubUser(583231L, "octocat", "The Octocat", "User",
        "https://avatars.githubusercontent.com/u/583231?v=4", now, 8468, 8);
    ResponseEntity<GithubUser> response = new ResponseEntity<>(githubUser, HttpStatus.OK);
    when(restTemplate.getForEntity("https://api.github.com/users/" + login, GithubUser.class)).thenReturn(response);

    mvc.perform(get("/users/" + login)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            "{\"id\":583231,\"login\":\"octocat\",\"name\":\"The Octocat\",\"type\":\"User\",\"avatarUrl\":\"https://avatars.githubusercontent.com/u/583231?v=4\",\"createdAt\":\"2023-02-26T20:51:12\",\"calculations\":0.007085498346717053}"));

    Long actualRequstCount = jdbcTemplate.queryForObject("SELECT REQUEST_COUNT FROM AUDIT WHERE LOGIN = ?", Long.class,
        login);
    assertEquals(1L, actualRequstCount);
  }
}
