package Client;

import javax.swing.*;
import java.awt.*;

public class TestPanel extends JFrame {

    public TestPanel(){
        JPanel content = new JPanel();
        this.setContentPane(content);
        JPanel test = new JPanel();
        test.setLayout(new BoxLayout(content,1));
//        .add(test1());
        test1().setLocation(0,0);
        this.add(test2());
        test2().setLocation(50,50);
        this.setSize(300,300);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        TestPanel test = new TestPanel();
//        System.out.println(validateBikeID("1"));
    }
//
//    public static boolean validateBikeID(String ID){
//        if(ID.length()>=10) return false;
//        try{
//            Integer.parseInt(ID);
//        } catch(Exception exception){
//            return false;
//        }
//        return true;
//    }

    public JPanel test1(){
        JPanel temp = new JPanel();
        temp.setSize(50,50);
        temp.setBackground(Color.red);
        JButton btn1 = new JButton("1");
        temp.add(btn1);
        JButton btn2 = new JButton("2");
        temp.add(btn2);
        return temp;
    }

    public JPanel test2(){
        JPanel temp = new JPanel();
        temp.setSize(50,50);
        temp.setBackground(Color.blue);
        JButton btn1 = new JButton("1");
        temp.add(btn1);
        JButton btn2 = new JButton("2");
        temp.add(btn2);
        return temp;
    }


}
