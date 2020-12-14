package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Mohamed Talaat Saad
 */
public class ServerGUI extends JFrame implements ActionListener {

    private JTextField InputPort;
    private JButton startServerButton;
    private JButton stopServerButton;
    private JLabel statusLabel;

    private Server server;

    /**
     * Creates a new instance of ServerGUI
     */
    public ServerGUI() {
        setTitle("Game Server GUI");
        setBounds(350, 200, 300, 250);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);
        startServerButton = new JButton("Start Server");
        startServerButton.setBounds(20, 30, 120, 25);
        startServerButton.addActionListener(this);

        stopServerButton = new JButton("Stop Server");
        stopServerButton.setBounds(150, 30, 120, 25);
        stopServerButton.addActionListener(this);

        InputPort = new JTextField();
        InputPort.setBounds(20, 70, 120, 25);


        statusLabel = new JLabel();
        statusLabel.setBounds(80, 90, 200, 25);

        getContentPane().add(statusLabel);
        getContentPane().add(startServerButton);
        getContentPane().add(stopServerButton);
        try {
            server = new Server();
        } catch (SocketException ex) {
            ex.printStackTrace();

        }

        setVisible(true);
    }


    public static void main(String[] args) {
        ServerGUI serverGUI = new ServerGUI();
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startServerButton) {

            server.start();
            startServerButton.setEnabled(false);
            statusLabel.setText("Server is running.....");

        }

        if (e.getSource() == stopServerButton) {
            try {

                server.stopServer();
                statusLabel.setText("Server is stopping.....");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
