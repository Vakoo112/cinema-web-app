package com.example.ticket_um.v1.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {

  SESSION("მომხმარებლის სესია"),
  OTP("ავტორიზაციის მეორე ფაქტორის კოდი"),
  PWD_CHNG("პაროლის სავალდებულო შეცვლა");

  private final String label;
}