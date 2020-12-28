package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    static Socket clientSocket;
    static DataOutputStream writer;
    static DataInputStream reader;
    ClientGUI clientGUI;

    public Client(ClientGUI clientGUI, String name) {
        this.clientGUI = clientGUI;
    }

    public static Socket getClientSocket() {
        return clientSocket;
    }

    public static void sendToServer(String message) {
        try {
            clientSocket = new Socket("127.0.0.1", 11111);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("contacting..");
        try {
            writer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
