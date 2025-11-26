package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author landr
 */
public class ServerMain {

    private static final int port = 5555;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor Kanban escuchando en puerto " + port);
            while (true) {
                Socket client = server.accept();
                System.out.println("Cliente conectado: " + client.getRemoteSocketAddress());
                pool.submit(new ClientHandler(client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
