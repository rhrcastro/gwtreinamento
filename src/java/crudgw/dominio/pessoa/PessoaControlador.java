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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/home"})
public class PessoaControlador extends HttpServlet {
    private static final String CADASTRAR = "cadastrar";
    private static final String EDITAR_PESSOA = "editarPessoa";
    private static final String EDITAR_USUARIO = "editarUsuario";
    private static final String EXCLUIR_USUARIO = "excluirUsuario";
    private static final String LISTAR_PESSOAS = "listarPessoas";
    private static final String LISTAR_USUARIOS = "listarUsuarios";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
            try {
                String acao = request.getParameter("acao");
                if (acao.equals(EDITAR_PESSOA)) {
                    Usuario usuario = doUsuario(request, response);
                    Pessoa pessoa = doPessoa(request, response, usuario);
                    editarPessoa(pessoa);
                }else if (acao.equals(CADASTRAR)) {
                    Usuario usuario = doUsuario(request, response);
                    cadastrarUsuario(usuario);
                    
                    Pessoa pessoa = doPessoa(request, response, usuario);
                    cadastrarPessoa(pessoa);   
                } else if(acao.equals(EXCLUIR_USUARIO)) {
                    Usuario usuario = doUsuario(request, response);
                    deletarUsuario(usuario);
            }   else if(acao.equals(EDITAR_USUARIO)) {
                    Usuario usuario = doUsuario(request, response);
                    editarUsuario(usuario);
            }   else if(acao.equals(LISTAR_PESSOAS)) {
                    listarPessoas();
            }   else if(acao.equals(LISTAR_USUARIOS)) {             
                    listarUsuarios();
            }
        } catch (Exception ex) {
                System.out.println(ex);
        } finally {
                out.close();
            }
    }
    private Usuario doUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        Usuario usuario = new Usuario();
        usuario.setLogin(request.getParameter("login"));
        usuario.setSenha(request.getParameter("senha"));
        if (request.getParameter("idusuario") != null ){
            int id = Integer.parseInt(request.getParameter("idusuario"));
            usuario.setId(id);
        }
        return usuario;
             
    }
   private Pessoa doPessoa(HttpServletRequest request, HttpServletResponse response, Usuario usuario)  {
       Pessoa pessoa = new Pessoa();
       pessoa.setNome(request.getParameter("nomePessoa"));
       pessoa.setCpf(request.getParameter("cpfPessoa"));
       pessoa.setIdUsuario(usuario.getId());
       if (request.getParameter("idpessoa") != null ){
            int id = Integer.parseInt(request.getParameter("idpessoa"));
            pessoa.setId(id);
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
   public void editarPessoa(Pessoa pessoa) throws ClassNotFoundException, SQLException {
       PessoaDAO pessoaDAO = new PessoaDAO();
       pessoaDAO.editarPessoa(pessoa);
   }
   public void deletarUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.deletarUsuario(usuario);
   }
   public void cadastrarUsuario(Usuario usuario) throws ClassNotFoundException, SQLException{
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.cadastrarUsuario(usuario);
   }
   public void cadastrarPessoa(Pessoa pessoa) throws ClassNotFoundException, SQLException {
       PessoaDAO pessoaDAO = new PessoaDAO();
       pessoaDAO.cadastrarPessoa(pessoa);
   }
   public void editarUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.editarUsuario(usuario);
   }
   public void listarPessoas() throws ClassNotFoundException, SQLException {
       PessoaDAO pessoaDAO = new PessoaDAO();
       List<Pessoa> listaPessoas = pessoaDAO.listarPessoas();
        
       for (Pessoa pessoa: listaPessoas) {
           System.out.println("ID pessoa:" + pessoa.getId() + " " + "Nome da pessoa:" + pessoa.getNome() + " " + "CPF da pessoa:" + pessoa.getCpf());
       }
   }
   public void listarUsuarios() throws ClassNotFoundException, SQLException {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();
       
       for (Usuario usuario: listaUsuarios) {
           System.out.println("ID do usuário: " + usuario.getId() + " " + "Login do Usuário: " + usuario.getLogin());
    }
   }
}