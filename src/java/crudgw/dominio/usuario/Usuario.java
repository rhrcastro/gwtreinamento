package crudgw.dominio.usuario;

public class Usuario {
    private int id;
    private String login;
    private String senha;
    
    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return login;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getSenha() {
        return senha;
    }
}
