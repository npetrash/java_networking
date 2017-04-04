import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server implements Runnable {
    private static Socket conn;
    private static ServerSocket server;
    private static ObjectOutputStream output;
    private static ObjectInputStream input;

    @Override
    public void run() {
        try {
            server = new ServerSocket(5678, 10);
            while (true) {
                conn = server.accept();
                output = new ObjectOutputStream(conn.getOutputStream());
                input = new ObjectInputStream(conn.getInputStream());
                //JOptionPane.showMessageDialog(null, input.readObject().toString());
                output.writeObject("Вы прислали вот это: " + input.readObject().toString());
            }
        } catch (UnknownHostException e) {

        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
