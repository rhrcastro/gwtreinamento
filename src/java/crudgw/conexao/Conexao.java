package crudgw.conexao;

import java.sql.*;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Conexao {

	 /** Usada para a conexao com o banco de dados */
    private Connection con = null;
    
    /** Usada para realizar as instrucoes SQL */
    public PreparedStatement stmt; 
    
    /** Retorna os dados das tabelas do banco */
    public ResultSet rs; 
    private String endereco;
    private String usuario;
    private String senha; 
    
    public void conectar(String strEnd, String strUsuario, String strSenha) throws ClassNotFoundException {

        endereco = strEnd; 
        usuario = strUsuario;
        senha = strSenha;
        Properties jdbcProperties = new Properties();

        try {
            
            Class.forName("org.postgresql.Driver");
            jdbcProperties.put("user", usuario);
            jdbcProperties.put("password", senha);
            con = DriverManager.getConnection(endereco, jdbcProperties);
            
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "erro na query");
            sqlex.printStackTrace();

        }
    }

    /** Esse metodo quando invocado, realiza a desconexao com o banco */
    public void desconectar() {

        try {
            con.close();
            
        /** Retorna um erro caso nao consiga desconectar */    
        } catch (SQLException onConClose) {
            JOptionPane.showMessageDialog(null, "Erro ao desconectar o banco");
            onConClose.printStackTrace();
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    
}
