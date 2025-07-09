package com.progym.progym;

public enum UsuarioRole {
    // ADMIN,
    ALUNO,
    PROFESSOR;

    private String role;

    void UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
