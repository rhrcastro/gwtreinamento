package crudgw.dominio.usuario;
        
import crudgw.dominio.pessoa.PessoaControlador;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/home/configUser"})
public class UsuarioControlador extends HttpServlet {
    
    private static final String EDITAR_USUARIO = "editarUsuario";
    private static final String EXCLUIR_USUARIO = "excluirUsuario";
    private static final String LISTAR_USUARIOS = "listarUsuarios";
    private static final String LOGIN = "login";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse resp) throws
            ServletException, IOException, SQLException, ClassNotFoundException {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            try {
                String acao = request.getParameter("acao");
                if (acao.equals(EDITAR_USUARIO)) {
                    Usuario usuario = doUsuario(request, resp);
                    editarUsuario(usuario);
            } else if(acao.equals(EXCLUIR_USUARIO)) {
                Usuario usuario = doUsuario(request, resp);
                deletarUsuario(usuario);
            } else if(acao.equals(LISTAR_USUARIOS)) {
                    listarUsuarios();
            } else if (acao.equals(LOGIN)) {
               Usuario usuario = doUsuario(request, resp);
               loginUsuario(usuario);
            }
            } catch(Exception ex){
                System.out.println(ex);
            } finally {
                out.close();
            }
    }
    public Usuario doUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        Usuario usuario = new Usuario();
        usuario.setLogin(request.getParameter("login"));
        usuario.setSenha(request.getParameter("senha"));
        if (request.getParameter("idusuario") != null ){
            int id = Integer.parseInt(request.getParameter("idusuario"));
            usuario.setId(id);
        }
        return usuario;
             
    }
    public void deletarUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.deletarUsuario(usuario);
   }
    public void listarUsuarios() throws ClassNotFoundException, SQLException {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();
       
       for (Usuario usuario: listaUsuarios) {
           System.out.println("ID do usuário: " + usuario.getId() + " " + "Login do Usuário: " + usuario.getLogin());
    }
   }
    public void editarUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.editarUsuario(usuario);
    }
    public void cadastrarUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.cadastrarUsuario(usuario);
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
   public void loginUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       Usuario usuarioLogado = usuarioDAO.getUsuarioByEmaileSenha(usuario);
       if (usuarioLogado != null) {
           System.out.println("logado com sucesso");
       } else if (usuarioLogado == null) {
           System.out.println("Login ou senha incorretos.");
       }
   }
}
