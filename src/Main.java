import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main extends JFrame implements Runnable {

    private static Socket conn;
    private static ObjectOutputStream output;
    private static ObjectInputStream input;

    public Main(String name) {
        super(name);
        setLayout(new FlowLayout());
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        final JTextField tf1 = new JTextField(10);
        final JButton b1 = new JButton("send");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b1) {
                    sendDate(tf1.getText());
                }
            }
        });

        add(tf1);
        add(b1);
    }

    public static void main(String[] args) {
        new Thread(new Main("test")).start();
        new Thread(new Server()).start();
    }

    private static void sendDate(Object obj) {
        try {
            output.flush();
            output.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                conn = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
                output = new ObjectOutputStream(conn.getOutputStream());
                input = new ObjectInputStream(conn.getInputStream());
                JOptionPane.showMessageDialog(null, input.readObject().toString());
            }
        } catch (UnknownHostException e) {

        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

