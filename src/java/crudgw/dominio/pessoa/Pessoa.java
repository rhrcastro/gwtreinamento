package crudgw.dominio.pessoa;

public class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private int idUsuario;
    
    
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setIdUsuario(int id) {
        this.idUsuario = id;
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public int getIdUsuario(){
        return idUsuario;
    }
}
