package Server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

/*
 * Server.java
 *
 * Created on 21 ãÇÑÓ, 2008, 09:41 Õ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author Mohamed Talaat Saad
 */
public class Server extends Thread {

    /**
     * Creates a new instance of Server
     */
//    public Enemy[] enemy = new Enemy[20];
    static ArrayList<ClientInfo> clients;
    private ServerSocket serverSocket;
    private int serverPort = 11111;
    Random generator = new Random();
    private DataInputStream reader;
    private DataOutputStream writer;

    private Protocol protocol;
    private boolean running = true;

    DatagramSocket UDPSocket;
    InetAddress address;

    public Server() throws SocketException {
        clients = new ArrayList<ClientInfo>();
        protocol = new Protocol();
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        String mess = "Begin!";
        Socket clientSocket = null;
        while (running) {

            System.out.println("Server.run()");
            try {
                System.out.println("broadcast..");
                BroadCastMessage("Server still running..");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            try {
                System.out.println("create clientSocket");
                clientSocket = serverSocket.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
                continue;
            }
            String sentence = "";
            try {
                System.out.println("reading input");
                reader = new DataInputStream(clientSocket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
                continue;
            }
            try {
                System.out.println("create reader");
                sentence = reader.readUTF();
            } catch (IOException ex) {
                ex.printStackTrace();
                continue;
            }


            if (sentence.startsWith("Hello")) {
                System.out.println("sending Back");
                    int pos = sentence.indexOf(',');
                    String name = sentence.substring(pos + 1, sentence.length());

                    try {
                        writer = new DataOutputStream(clientSocket.getOutputStream());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    clients.add(new ClientInfo(writer,name));
//                  sendToClient(writer,name+"How arre you?");
                try {
                    BroadCastMessage("Hello"+name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (sentence.startsWith("ok")) {
                int pos = sentence.indexOf(',');
                String name = sentence.substring(pos + 1, sentence.length());

                try {
                    writer = new DataOutputStream(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
//                  sendToClient(writer,name+"How arre you?");
                try {
                    BroadCastMessage("OK "+name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            yield();
        }

        try {
            reader.close();
            writer.close();
            serverSocket.close();
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void stopServer() throws IOException {
        running = false;
    }

    public static void BroadCastMessage(String mess) throws IOException {
        if(clients.size()>1){
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i) != null) {
                    clients.get(i).getWriterStream().writeUTF(mess);
                }
            }
        }

    }

    public void sendToClient(DataOutputStream writer, String message) {
        if (message.equals("exit")) {
            System.exit(0);
        } else {
            try {
                writer.writeUTF(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class ClientInfo {

        DataOutputStream writer;
        String name;

        public ClientInfo(DataOutputStream writer, String name) {
            this.writer = writer;
            this.name = name;
        }



        public DataOutputStream getWriterStream() {
            return writer;
        }


        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
    }

    public boolean getRunning() {
        return running;
    }

}
