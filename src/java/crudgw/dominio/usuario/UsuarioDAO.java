package crudgw.dominio.usuario;

import crudgw.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import crudgw.dominio.usuario.Usuario;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
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
            conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        prepSt = conexao.getCon().prepareStatement(sql);
        prepSts.add(prepSt);
        return prepSt;
    }
    public void cadastrarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException{
        conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        try {
            sql = "INSERT INTO usuario(login, senha) VALUES (?,?)";
            prepSt = conexao.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepSt.setString(1, usuario.getLogin());
            prepSt.setString(2, usuario.getSenha());
            prepSt.execute();
            ResultSet rs =  prepSt.getGeneratedKeys();
        
            
            if (rs.next()) {
                usuario.setId(rs.getInt("id"));
            }
        } catch (SQLException sx){
            sx.printStackTrace();
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
            System.out.println(sx);
        conexao.setCon(null);
        }
    }
    public void editarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        try {
            sql = "UPDATE usuario SET login = ?, senha = ? WHERE id = ?";
            prepSt = prepareStatement(sql);
            int index = 0;
            prepSt.setString(++index, usuario.getLogin());
            prepSt.setString(++index, usuario.getSenha());
            prepSt.setInt(++index, usuario.getId());

            prepSt.executeUpdate();
        } catch(SQLException sx) {
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
            System.out.println(sx);
        conexao.setCon(null);
        }
    }
    public void deletarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        
        try {
            sql = "delete from usuario where id = ?";
            prepSt =  conexao.getCon().prepareStatement(sql);
            prepSt.setInt(1, usuario.getId());

            prepSt.executeUpdate();
        
        } catch(SQLException sx) {
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
            System.out.println(sx);
        conexao.setCon(null);
        }
    }
    public List<Usuario> listarUsuarios() throws ClassNotFoundException, SQLException {
        conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        List<Usuario> listaUsuarios = null;
        try {
            sql = "SELECT * from usuario";
            prepSt = conexao.getCon().prepareStatement(sql);
            
            ResultSet rs = prepSt.executeQuery();
            listaUsuarios = new ArrayList();
            
            while(rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                
                listaUsuarios.add(usuario);
            }
            rs.close();
            prepSt.close();
            
        } catch (SQLException sx) {
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
        }
        conexao.setCon(null);
        return listaUsuarios;
    }
    public Usuario getUsuarioByEmaileSenha(Usuario usuario) throws ClassNotFoundException, SQLException {
        conexao.conectar("jdbc:postgresql://192.168.0.71:5432/K4STR0", "postgres", "postgres@2018@");
        Usuario usuarioRetornado;
        try {
            sql = "select * from usuario where login = ? and senha = ?";
            prepSt = conexao.getCon().prepareStatement(sql);
            int index = 0;
            prepSt.setString(++index, usuario.getLogin());
            prepSt.setString(++index, usuario.getSenha());
            usuarioRetornado = new Usuario();
            
            System.out.println(prepSt);
            
            ResultSet rs = prepSt.executeQuery();
            if(rs.next()) {
                usuarioRetornado.setId(rs.getInt("id"));
                usuarioRetornado.setLogin(rs.getString("login"));
                usuarioRetornado.setSenha(rs.getString("senha"));
                return usuarioRetornado;
            } 
        } catch(SQLException sx) {
            if (conexao.getCon() != null) {
                conexao.getCon().close();
            }
        }
        conexao.setCon(null);
        return usuarioRetornado = null;
    }
}
