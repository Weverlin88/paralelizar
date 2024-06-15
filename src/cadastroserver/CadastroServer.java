package cadastroserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class CadastroServer {
    private static List<Produto> produtos = new ArrayList<>();

    public static List<Produto> getProdutos() {
        return produtos;
    }

    public static void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public static boolean removerProduto(Produto produto) {
        return produtos.remove(produto);
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4321)) {
            System.out.println("Servidor aguardando conexão...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    System.out.println("Conexão estabelecida com cliente.");

                    String login = (String) in.readObject(); // Recebe login
                    String senha = (String) in.readObject(); // Recebe senha

                    if ("op1".equals(login) && "op1".equals(senha)) {
                        out.writeObject("Login successful");

                        ThreadServer threadServer = new ThreadServer(socket, in, out);
                        threadServer.start();

                        System.out.println("Cliente conectado: " + socket.getInetAddress());
                    } else {
                        out.writeObject("Login failed");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadServer extends Thread {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    public ThreadServer(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String comando = (String) in.readObject();

                switch (comando.toUpperCase()) {
                    case "L":
                        enviarListaProdutos();
                        break;
                    case "E":
                        processarEntrada();
                        break;
                    case "S":
                        processarSaida();
                        break;
                    case "X":
                        encerrarConexao();
                        return; // Encerra a thread quando o cliente solicita sair
                    default:
                        out.writeObject("Comando inválido");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void enviarListaProdutos() throws IOException {
        out.writeObject(CadastroServer.getProdutos());
        out.flush(); // Garante que todos os dados sejam enviados imediatamente
    }

    private void processarEntrada() throws IOException, ClassNotFoundException {
        Produto produto = (Produto) in.readObject();
        CadastroServer.adicionarProduto(produto);
        out.writeObject("Produto adicionado com sucesso: " + produto.getNome());
        out.flush(); // Garante que todos os dados sejam enviados imediatamente
    }

    private void processarSaida() throws IOException, ClassNotFoundException {
        Produto produto = (Produto) in.readObject();
        boolean removido = CadastroServer.removerProduto(produto);
        if (removido) {
            out.writeObject("Produto removido com sucesso: " + produto.getNome());
        } else {
            out.writeObject("Produto não encontrado para remoção: " + produto.getNome());
        }
        out.flush(); // Garante que todos os dados sejam enviados imediatamente
    }

    private void encerrarConexao() {
        try {
            socket.close();
            System.out.println("Conexão encerrada com cliente: " + socket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
