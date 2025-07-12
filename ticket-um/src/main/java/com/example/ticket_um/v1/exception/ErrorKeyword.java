package com.example.ticket_um.v1.exception;

import com.example.ticket_utils.v1.exception.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorKeyword implements Error {

  USER_NOT_FOUND("User not found!"),
  USER_ALREADY_EXISTS("User already exists!"),
  PERM_NOT_FOUND("Permission not found!"),
  ROLE_NOT_FOUND("Role not found!"),
  USER_LOCKED("User_locked"),
  SESSION_NOT_FOUND("Session_not_found!"),
  SESSION_EXPIRED("Session_expired"),
  GROUP_NOT_FOUND("Group_not_found!"),
  INCORRECT_PWD("Incorrect password!"),
  OLD_AND_NEW_PWDS_MATCH("Old and new passwords match!"),
  NEW_PWDS_NOT_MATCH("New passwords do not match!"),;

  private final String message;
}
