package com.imd.mercearia.enums;

public enum Role {
    CLIENTE("cliente"),
    CAIXA("caixa"),
    REPOSITOR("repositor"),
    GERENTE("gerente");

    private final String name;

    private Role(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }

}
