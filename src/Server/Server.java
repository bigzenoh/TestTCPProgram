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

    static ArrayList<ClientInfo> clients;
//    static ArrayList<ClientInfo>[] rooms = new ArrayList<ClientInfo>[100];
    private ServerSocket serverSocket;
    private int serverPort = 11111;
    Random generator = new Random();
    private DataInputStream reader;
    private DataOutputStream writer;

    private Protocol protocol;
    private boolean running = true;

    //thông tin .....52673547512
    int NumOfTask = 3, CountingTask=0;
    int NumOfResource = 1, CountingResource=0; //biến couting để đếm số task hiện tại đã vote

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

            System.out.println(sentence);
            if (sentence.startsWith("Hello")) {
                System.out.println("sending Back");
                for(int i=0;i<clients.size();i++){
                    System.out.print(clients.get(i).name+" - ");
                }
                    int pos1 = sentence.indexOf(',');

                String name = sentence.substring(pos1 + 1, sentence.length());

                    try {
                        writer = new DataOutputStream(clientSocket.getOutputStream());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
//                  sendToClient(writer,name+"How arre you?");
                try {

                    for(int i=0;i<clients.size();i++){
                        if(clients.get(i)!=null){
                            sendToClient(writer,"new,"+clients.get(i).name);
                            System.out.println("add exist name");
                        }

                    }
                    BroadCastMessage("new,"+name);
                    System.out.println("broadcast new name");
                    sendToClient(writer,"AddYourself");
                    System.out.println("require add YOurself");
                    clients.add(new ClientInfo(writer,name));
//                    if(clients.size()==NumOfResource) {
//                        BroadCastMessage("NextTask");
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (sentence.startsWith("ok")) {
                int pos1 = sentence.indexOf(',');
                    int pos2 = sentence.indexOf('.');
                    String name = sentence.substring(pos1 + 1, pos2);
                    int value = Integer.parseInt(sentence.substring(pos2+1,sentence.length()));
                try {
                    writer = new DataOutputStream(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
//                  sendToClient(writer,name+"How arre you?");
                try {
                    BroadCastMessage("OK,"+name+"."+value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                CountingResource+=1;
//                if(CountingResource==clients.size()){
//                    try {
//                        BroadCastMessage("NextTask");
//                        CountingResource=0;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
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
        if(clients.size()>0){
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
