package br.com.picpay.domain.user;

public enum UserType {

    COMMON("common"),
    MERCHANT("merchant");

    private String role;

    UserType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
