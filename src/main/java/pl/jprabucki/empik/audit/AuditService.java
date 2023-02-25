package pl.jprabucki.empik.audit;

import org.springframework.stereotype.Service;

/**
 * @author Jakub Prabucki
 */
@Service
public class AuditService {

  private final AuditRepository auditRepository;

  public AuditService(final AuditRepository auditRepository) {
    this.auditRepository = auditRepository;
  }

  public void log(final String login) {
    if (login == null || login.isEmpty())
      throw new IllegalArgumentException("Empty login");

    auditRepository.updateLoginRequests(login);
  }
}
