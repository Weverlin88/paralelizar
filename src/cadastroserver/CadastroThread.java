package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;
import model.Usuario;
public class CadastroThread extends Thread {
    private Socket socket;
    private ProdutoJpaController produtoCtrl;
    private UsuarioJpaController usuarioCtrl;

    public CadastroThread(Socket socket, ProdutoJpaController produtoCtrl, UsuarioJpaController usuarioCtrl) {
        this.socket = socket;
        this.produtoCtrl = produtoCtrl;
        this.usuarioCtrl = usuarioCtrl;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String login = (String) in.readObject();
            String senha = (String) in.readObject();
            Usuario usuario = (Usuario) usuarioCtrl.findUsuario(login, senha);

            if (usuario == null) {
                out.writeObject("Invalid credentials");
                socket.close();
                return;
            } else {
                out.writeObject("Login successful");
            }

            String command;
            while ((command = (String) in.readObject()) != null) {
                if ("L".equals(command)) {
                    List<Produto> produtos = (List<Produto>) produtoCtrl.findProdutoEntities();
                    out.writeObject(produtos);
                }
                // Adicione outros comandos conforme necessário
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
