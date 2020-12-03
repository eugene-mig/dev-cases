package com.trustpoint.cases.constants;

public enum RoleKind {
  ADMIN("ADMIN"),
  MANAGER("MANAGER"),
  INVESTIGATOR("INVESTIGATOR");

  private final String text;

  RoleKind(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
