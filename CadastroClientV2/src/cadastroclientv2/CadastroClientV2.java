package cadastroclientv2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class CadastroClientV2 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4321)) {
            System.out.println("Conexão estabelecida com o servidor.");
            
            try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 Scanner scanner = new Scanner(System.in)) {
                
                while (true) {
                    System.out.print("Digite o comando (L - Listar, E - Entrada, S - Saída, X - Sair): ");
                    String comando = scanner.nextLine().toUpperCase();
                    
                    // Enviar comando para o servidor
                    outputStream.writeObject(comando);
                    
                    // Receber resposta do servidor
                    Object resposta = inputStream.readObject();
                    
                    // Processar resposta do servidor conforme necessário
                    System.out.println("Resposta do servidor: " + resposta);
                    
                    if ("X".equals(comando)) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro de comunicação com o servidor: " + e.getMessage());
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao conectar com o servidor: " + e.getMessage());
        }
    }
}
