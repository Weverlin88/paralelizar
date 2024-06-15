
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;

public class CadastroClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4321);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("op1");
            out.writeObject("op1");

            String response = (String) in.readObject();
            System.out.println(response);

            if ("Login successful".equals(response)) {
                out.writeObject("L");
                List<Produto> produtos = (List<Produto>) in.readObject();
                for (Produto produto : produtos) {
                    System.out.println(produto.getNome());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
