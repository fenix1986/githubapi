package pl.jprabucki.empik.audit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Jakub Prabucki
 */
@Repository
class AuditRepository {

  final JdbcTemplate jdbcTemplate;

  AuditRepository(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  void updateLoginRequests(final String login) {
    if (login == null || login.isEmpty())
      throw new IllegalArgumentException("Empty login");

    jdbcTemplate.update("""
        MERGE INTO AUDIT AS TARGET
        USING (SELECT CAST(? AS VARCHAR) AS LOGIN) AS SOURCE
          ON SOURCE.LOGIN = TARGET.LOGIN
        WHEN MATCHED THEN UPDATE SET TARGET.REQUEST_COUNT = TARGET.REQUEST_COUNT + 1
        WHEN NOT MATCHED THEN INSERT VALUES (?, 1)
        """, login, login);
  }
}
