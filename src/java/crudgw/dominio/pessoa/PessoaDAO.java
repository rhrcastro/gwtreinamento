package crudgw.dominio.pessoa;

import crudgw.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        
        try {
            sql = "INSERT INTO pessoa(nome, cpf, usuario_id) VALUES (?,?,?)";
            
            prepSt = conexao.getCon().prepareStatement(sql);
            prepSt.setString(1, pessoa.getNome());
            prepSt.setString(2, pessoa.getCpf());
            prepSt.setInt(3, pessoa.getIdUsuario());
            
            prepSt.executeQuery();
            
            ResultSet rs =  prepSt.getGeneratedKeys();
        
            if (rs.next()) {
                pessoa.setId(rs.getInt("id"));
            }
        } catch (SQLException sx){
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
            System.out.println(sx);
        conexao.setCon(null);
        }
    }
    public void editarPessoa(Pessoa pessoa) throws ClassNotFoundException, SQLException {
        conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        try {
            sql = "UPDATE pessoa SET nome = ?, cpf = ? WHERE id = ?";
            prepSt = conexao.getCon().prepareStatement(sql);
            int index = 0;
            prepSt.setString(++index, pessoa.getNome());
            prepSt.setString(++index, pessoa.getCpf());
            prepSt.setInt(++index, pessoa.getId());
            prepSt.executeUpdate();
        } catch(SQLException sx) {
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
        conexao.setCon(null);
        }
    }
    public List<Pessoa> listarPessoas() throws ClassNotFoundException, SQLException {
        conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        List<Pessoa> listaPessoas = null;
        try {
            sql = "SELECT * from pessoa";
            prepSt = conexao.getCon().prepareStatement(sql);
            
            ResultSet rs =  prepSt.executeQuery();
            listaPessoas = new ArrayList();
            
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setIdUsuario(rs.getInt("usuario_id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                
                listaPessoas.add(pessoa);
            }
            rs.close();
            prepSt.close();
        } catch (SQLException sx) {
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
        }
        conexao.setCon(null);
        return listaPessoas;
    }
}