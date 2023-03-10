package pl.jprabucki.empik.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.jprabucki.empik.users.UserNotExists;

/**
 * @author Jakub Prabucki
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  ErrorInfo handleBadRequest(Exception ex) {
    return new ErrorInfo((ex.getMessage()));
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotExists.class)
  @ResponseBody
  ErrorInfo handleUserNotExists(Exception ex) {
    return new ErrorInfo((ex.getMessage()));
  }

  private record ErrorInfo(String error) {}
}
