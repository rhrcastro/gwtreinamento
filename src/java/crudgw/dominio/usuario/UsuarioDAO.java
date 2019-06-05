package crudgw.dominio.usuario;

import crudgw.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import crudgw.dominio.usuario.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {
    PreparedStatement prepSt = null;
    String sql = "";
    public Connection con = null;
    Conexao conexao = new Conexao();
    Collection<PreparedStatement> prepSts = null;
    protected PreparedStatement prepareStatement(String sql) throws SQLException {
        if (prepSts == null) {
            prepSts = new ArrayList<PreparedStatement>();
        }
        try {
            conexao.conectar("jdbc:postgresql://192.168.0.70:5432/K4STR0", "postgres", "postgres");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        prepSt = conexao.getCon().prepareStatement(sql);
        prepSts.add(prepSt);
        return prepSt;
    }
    public void cadastrarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException{
        conexao.conectar("jdbc:postgresql://192.168.0.70:5432/K4STR0", "postgres", "postgres");
        try {
            sql = "INSERT INTO usuario(id, login, senha) VALUES (?,?,?)";
            prepSt = prepareStatement(sql);
            prepSt.setInt(1, usuario.getId());
            prepSt.setString(2, usuario.getLogin());
            prepSt.setString(3, usuario.getSenha());
            
            prepSt.execute();
        
        } catch (SQLException sx){
            System.out.println(sx);
        }
    }
    public void editarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        conexao.conectar("jdbc:postgresql://192.168.0.70:5432/K4STR0", "postgres", "postgres");
        try {
            sql = "UPDATE usuario SET login = ?, senha = ? WHERE id = ?";
            prepSt = prepareStatement(sql);
            int index = 0;
            prepSt.setString(++index, usuario.getLogin());
            prepSt.setString(++index, usuario.getSenha());
            prepSt.setInt(++index, usuario.getId());

            prepSt.executeUpdate();
        } catch(SQLException sx) {
            System.out.println(sx);
        }
    }
    public void deletarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        conexao.conectar("jdbc:postgresql://192.168.0.70.5432/K4STR0", "postgres", "postgres");
        sql = "delete from usuario where id = ?";
        
        prepSt =  conexao.getCon().prepareStatement(sql);
    }
}
