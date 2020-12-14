package Client;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientGUI extends JFrame implements ActionListener {

    public ClientGUI() {
        setResizable(false);
        setSize(1300, 920);
        setLocation(300, 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setForeground(Color.WHITE);
        contentPane.setBackground(SystemColor.controlHighlight);
        //tạo cái nút start và join
        start = createJButton("START");
        start.setBounds(0, 0, 150, 150);
        start.setBackground(Color.cyan);
        contentPane.add(start);
        //tạo khung hiển thị task đang estimate
        TaskLabel = new JLabel("Estimating task: 1");
        TaskLabel.setBounds(200, 200, 390, 70);
        TaskLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        contentPane.add(TaskLabel);
        //tạo list hiển thị task đã estimate
        EstimatedList = new JList<>();
        EstimatedList.setModel(TaskList);
        TaskPane = new JScrollPane(EstimatedList);
        TaskPane.setLocation(800,50);
        TaskPane.setSize(400,500);
        contentPane.add(TaskPane);
        // tạo 10 cái nút
        Vbtn1 = createJButton("1");
        Vbtn1.setBounds(200, 650, 100, 70);
        Vbtn1.setBackground(Color.green);
        Vbtn2 = createJButton("2");
        Vbtn2.setBounds(400, 650, 100, 70);
        Vbtn2.setBackground(Color.green);
        Vbtn3 = createJButton("3");
        Vbtn3.setBounds(600, 650, 100, 70);
        Vbtn3.setBackground(Color.green);
        Vbtn4 = createJButton("5");
        Vbtn4.setBounds(800, 650, 100, 70);
        Vbtn4.setBackground(Color.yellow);
        Vbtn5 = createJButton("8");
        Vbtn5.setBounds(1000, 650, 100, 70);
        Vbtn5.setBackground(Color.yellow);
        Vbtn6 = createJButton("13");
        Vbtn6.setBounds(200, 800, 100, 70);
        Vbtn6.setBackground(Color.yellow);
        Vbtn7 = createJButton("21");
        Vbtn7.setBounds(400, 800, 100, 70);
        Vbtn7.setBackground(Color.yellow);
        Vbtn8 = createJButton("34");
        Vbtn8.setBounds(600, 800, 100, 70);
        Vbtn8.setBackground(Color.red);
        Vbtn9 = createJButton("55");
        Vbtn9.setBounds(800, 800, 100, 70);
        Vbtn9.setBackground(Color.red);
        Vbtn10 = createJButton("89");
        Vbtn10.setBounds(1000, 800, 100, 70);
        Vbtn10.setBackground(Color.red);
        contentPane.add(Vbtn1);
        contentPane.add(Vbtn2);
        contentPane.add(Vbtn3);
        contentPane.add(Vbtn4);
        contentPane.add(Vbtn5);
        contentPane.add(Vbtn6);
        contentPane.add(Vbtn7);
        contentPane.add(Vbtn8);
        contentPane.add(Vbtn9);
        contentPane.add(Vbtn10);
        //tạo khung nhập số port, ip, tên, số task để bắt đầu vote
    }

    /**
     * Attributes...
     */
    //các biến dùng cho giao diện
    JPanel contentPane, Spanel, Vpanel;
    JButton Vbtn1, Vbtn2, Vbtn3, Vbtn4, Vbtn5, Vbtn6, Vbtn7, Vbtn8, Vbtn9, Vbtn10;
    JButton start, join;
    JTextField InputRoom, InputName, InputNumOfTask;
    Client client;
    JLabel dimension, TaskLabel;
    JScrollPane TaskPane;
    JList<String> EstimatedList;
    DefaultListModel<String> TaskList = new DefaultListModel<>();
    //các biến dùng cho tính toán
    int TaskEstimating=1;
    private JButton createJButton(String title) {
        JButton btn = new JButton(title);
        // add action for JButton
        btn.addActionListener(this);
        return btn;
    }

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
    }

    public static void setDis(JButton btn) {
        btn.setEnabled(false);
    }

    public static void setTaskLabel(JLabel label, int i) {
        label.setText("Estimating task: " + i);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Vbtn1) {
            System.out.println("1");
            client.sendToServer("ok,Dat.1");
        } else if (e.getSource() == Vbtn2) {
            System.out.println("2");
            client.sendToServer("ok,Dat.2");
        } else if (e.getSource() == Vbtn3) {
            System.out.println("3");
            client.sendToServer("ok,Dat.3");
        } else if (e.getSource() == Vbtn4) {
            System.out.println("5");
        } else if (e.getSource() == Vbtn5) {
            System.out.println("8");
        } else if (e.getSource() == Vbtn6) {
            System.out.println("13");
        } else if (e.getSource() == Vbtn7) {
            System.out.println("21");
        } else if (e.getSource() == Vbtn8) {
            System.out.println("34");
        } else if (e.getSource() == Vbtn9) {
            System.out.println("55");
        } else if (e.getSource() == Vbtn10) {
            System.out.println("89");
        } else if (e.getSource() == start) {
            //tạo một client đến server
            client = new Client(this);
            client.start();
            //tạo luồng nhận dữ liệu
            ReceivingThread t = new ReceivingThread(client.getClientSocket(), this);
            t.start();
            //xoá nút start
            start.setEnabled(false);
        } else if (e.getSource() == join) {
        }
    }

    /////////////////////////////////////////////////////////
    public static class ReceivingThread extends Thread {

        Socket clientSocket;
        ClientGUI clientGUI;
        DataInputStream reader;

        public ReceivingThread(Socket socket, ClientGUI clientGUI) {
            this.clientSocket = socket;
            this.clientGUI = clientGUI;
            try {
                reader = new DataInputStream(clientSocket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            while (true) {
//                System.out.println("ClientGUI.ClientRecivingThread.run()");
                String sentence = "";
                try {
                    sentence = reader.readUTF();
                    System.out.println("from Server: " + sentence);
                    //chạy lệnh điều khiển khi nhận được gói tin
                    if (sentence.startsWith("OK")) {
                        int pos2 = sentence.indexOf(".");
                        String value = sentence.substring(pos2+1,sentence.length());
                        clientGUI.TaskEstimating+=1;
                        setTaskLabel(clientGUI.TaskLabel, clientGUI.TaskEstimating);
                        clientGUI.TaskList.addElement("You estimated task "+ Integer.toString(clientGUI.TaskEstimating-1)+" for: "+value);
                        clientGUI.EstimatedList.setModel(clientGUI.TaskList);
                        clientGUI.TaskPane.repaint();
                    } else if (sentence.startsWith("KO")) {

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                yield();
            }
        }
    }
}
