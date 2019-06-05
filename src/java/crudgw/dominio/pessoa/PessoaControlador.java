package crudgw.dominio.pessoa;

import crudgw.dominio.usuario.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import crudgw.dominio.pessoa.Pessoa;
import crudgw.dominio.pessoa.PessoaDAO;
import crudgw.dominio.usuario.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/cadastroPessoa"})
public class PessoaControlador extends HttpServlet {
     int contadorUsuario = 27;
     int contadorPessoa = 27;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cadastro realizado com sucesso</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form action" + "=" + "/CrudGW/cadastroPessoa" + "method=" +"post"+ ">");
            out.println("Nome:" + "<input type" + "=" + "text" + "name =" + "editarNome" + "/>");
            out.println("<br>");
            out.println("CPF:" + "<input type" + "=" + "text" + "name =" + "editarCPF" + "/>");
            out.println("<br>");
            out.println("<input type = \"submit\" value = \"Login\"/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
            Usuario usuario = doUsuario(request, response);
            Pessoa pessoa = doPessoa(request, response, usuario);
            editarPessoa(request, response, pessoa, request.getParameter("editarNome"), request.getParameter("editarCPF"));
        }
    }
    private Usuario doUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario.setId(contadorUsuario);
        contadorUsuario++;
        usuario.setLogin(request.getParameter("login"));
        usuario.setSenha(request.getParameter("senha"));
        usuarioDAO.cadastrarUsuario(usuario);
        
        return usuario;
             
    }
   private Pessoa doPessoa(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws SQLException {
       PessoaDAO pessoaDAO = new PessoaDAO();
       Pessoa pessoa = new Pessoa();
       pessoa.setId(contadorPessoa);
       contadorPessoa++;
       pessoa.setNome(request.getParameter("nomePessoa"));
       pessoa.setCpf(request.getParameter("cpfPessoa"));
       pessoa.setIdUsuario(usuario.getId());
         try {
             pessoaDAO.cadastrarPessoa(pessoa);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PessoaControlador.class.getName()).log(Level.SEVERE, null, ex);
         }
       return pessoa;
   }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
         try {
             try {
                 processRequest(request, response);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(PessoaControlador.class.getName()).log(Level.SEVERE, null, ex);
             }
         } catch (SQLException ex) {
             Logger.getLogger(PessoaControlador.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
   public void editarPessoa(HttpServletRequest request, HttpServletResponse response, Pessoa pessoa, String nome, String CPF) throws ClassNotFoundException, SQLException {
       PessoaDAO pessoaDAO = new PessoaDAO();
       pessoa.setNome(nome);
       pessoa.setCpf(CPF);
       pessoaDAO.editarPessoa(pessoa);
   }
}
