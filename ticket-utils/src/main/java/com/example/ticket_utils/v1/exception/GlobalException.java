package com.example.ticket_utils.v1.exception;

import com.example.ticket_utils.v1.domain.dto.Resp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

  private static final String ERROR = "ERROR HAS OCCURED";
  private static final String UNAUTHORIZED = "UNAUTHORIZED ERROR";

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Resp> handleNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
    Resp.Error error = new Resp.Error(ex.getKeyword().name(), ex.getMessage());
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Resp> handleForbiddenException(HttpServletRequest request, ForbiddenException ex) {
    Resp.Error error = new Resp.Error(ex.getKeyword().name(), ex.getMessage());
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Resp> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
    Resp.Error error = new Resp.Error("PERMISSION_DENIED", ex.getMessage());
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Resp> handleConflictException(HttpServletRequest request, ConflictException ex) {
    Resp.Error error = new Resp.Error(ex.getKeyword().name(), ex.getMessage());
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(RequestProblemException.class)
  public ResponseEntity<Resp> handleBadRequestException(HttpServletRequest request, RequestProblemException ex) {
    Resp.Error error = new Resp.Error(ex.getKeyword().name(), ex.getMessage());
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Resp> handleUnauthorizedException(HttpServletRequest request, UnauthorizedException ex) {
    Resp.Error error = new Resp.Error(ex.getKeyword().name(), ex.getMessage());
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Resp> handleBadCredentialsException(HttpServletRequest request, BadCredentialsException ex) {
    Resp.Error error = new Resp.Error(UNAUTHORIZED, "მომხმარებლის სახელი ან პაროლი არასწორია!");
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(LockedException.class)
  public ResponseEntity<Resp> handleLockedException(HttpServletRequest request, LockedException ex) {
    Resp.Error error = new Resp.Error(UNAUTHORIZED, "მომხმარებელი დაბლოკილია!");
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Resp> handleAuthenticationException(HttpServletRequest request, AuthenticationException ex) {
    Resp.Error error = new Resp.Error(UNAUTHORIZED, ex.getMessage());
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Resp> handleInternalServerError(HttpServletRequest request, Exception ex) {
    Resp.Error error = new Resp.Error("INTERNAL_SERVER_ERROR", ex.getMessage());
    Resp response = new Resp(error);

    return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
