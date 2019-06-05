package crudgw.dominio.pessoa;

import crudgw.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class PessoaDAO {
    PreparedStatement prepSt = null;
    String sql = "";
    public Connection con = null;
    Conexao conexao = new Conexao();
    Collection<PreparedStatement> prepSts = null;
    
    protected PreparedStatement prepareStatement(String sql) throws SQLException {
        if (prepSts == null) {
            prepSts = new ArrayList<PreparedStatement>();
        }
        prepSt = con.prepareStatement(sql);
        prepSts.add(prepSt);
        return prepSt;
    }
    
    public void cadastrarPessoa(Pessoa pessoa) throws SQLException, ClassNotFoundException{
        conexao.conectar("jdbc:postgresql://192.168.0.70:5432/K4STR0", "postgres", "postgres");
        
        try {
            sql = "INSERT INTO pessoa(id, nome, cpf, idusuario) VALUES (?,?,?,?)";
            
            prepSt = conexao.getCon().prepareStatement(sql);
            prepSt.setInt(1, pessoa.getId());
            prepSt.setString(2, pessoa.getNome());
            prepSt.setString(3, pessoa.getCpf());
            prepSt.setInt(4, pessoa.getIdUsuario());
            
            prepSt.executeUpdate();
        
        } catch (SQLException sx){
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
            conexao.setCon(null);
            System.out.println(sx);
        }
    }
    public void editarPessoa(Pessoa pessoa) throws ClassNotFoundException, SQLException {
        conexao.conectar("jdbc:postgresql://192.168.0.70:5432/K4STR0", "postgres", "postgres");
        try {
            sql = "UPDATE pessoa SET nome = ?, cpf = ? WHERE id = ?";
            prepSt = prepareStatement(sql);
            int index = 0;
            prepSt.setString(++index, pessoa.getNome());
            prepSt.setString(++index, pessoa.getCpf());
            prepSt.setInt(++index, pessoa.getIdUsuario());

            prepSt.executeUpdate();
        } catch(SQLException sx) {
            System.out.println(sx);
        }
    }
}