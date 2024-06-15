package cadastroclientv2;

import controller.MovimentoJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;
import model.Movimento;
import model.Produto;
import model.Usuario;

public class CadastroThreadV2 extends Thread {
    private Socket socket;
    private ProdutoJpaController produtoCtrl;
    private UsuarioJpaController usuarioCtrl;
    private MovimentoJpaController movimentoCtrl;

    public CadastroThreadV2(Socket socket, ProdutoJpaController produtoCtrl, UsuarioJpaController usuarioCtrl, MovimentoJpaController movimentoCtrl) {
        this.socket = socket;
        this.produtoCtrl = produtoCtrl;
        this.usuarioCtrl = usuarioCtrl;
        this.movimentoCtrl = movimentoCtrl;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String login = (String) in.readObject();
            String senha = (String) in.readObject();
            Usuario usuario = usuarioCtrl.findUsuario(login, senha);

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
                } else if ("E".equals(command) || "S".equals(command)) {
                    Movimento movimento = new Movimento();
                    movimento.setTipo(command);
                    movimento.setUsuario(usuario);

                    movimento.setPessoaId((Integer) in.readObject());
                    movimento.setProdutoId((Integer) in.readObject());
                    movimento.setQuantidade((Integer) in.readObject());
                    movimento.setValorUnitario((BigDecimal) in.readObject());

                    movimentoCtrl.create(movimento);

                    Produto produto = (Produto) produtoCtrl.findProduto(movimento.getProdutoId());
                    if ("E".equals(command)) {
                        produto.setQuantidade(produto.getQuantidade() + movimento.getQuantidade());
                    } else {
                        produto.setQuantidade(produto.getQuantidade() - movimento.getQuantidade());
                    }
                    produtoCtrl.edit(produto);

                    out.writeObject("Movimento registrado com sucesso.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
