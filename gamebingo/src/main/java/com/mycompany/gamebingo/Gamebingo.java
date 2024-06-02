/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gamebingo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author TRAN HUY
 */
public class Gamebingo extends JFrame {

    public Gamebingo() {
        
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250,250);
        frame.setLayout(new GridLayout(5,5,0,0));
        for (int row = 0; row < 5; row++) 
        {
            for (int col = 0; col < 5; col++) 
            {
                int randomNumber = new Random().nextInt(99) + 1; //tạo số từ 1 đến 99 gán vào biến randomNumber
                JButton numberButton = new JButton(String.valueOf(randomNumber)); // tạo button với giá trị lấy từ randomNumber
                frame.add(numberButton); // thêm các button vào gridlayout
            }
        }
        frame.setVisible(true); // hiển thị file này lên
    }
    
}
