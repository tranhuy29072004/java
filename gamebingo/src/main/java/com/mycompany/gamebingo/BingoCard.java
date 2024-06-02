/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamebingo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author TRAN HUY
 */
public class BingoCard {
    int randomNumber;
    int[] numbers = new int[25];
    public void generateCard() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250,250);
        frame.setLayout(new GridLayout(5,5,0,0));
        int index = 0; // biến này dùng để lưu các giá trị random vào mảng
        for (int row = 0; row < 5; row++) 
        {
            for (int col = 0; col < 5; col++) 
            {
                randomNumber = new Random().nextInt(99) + 1; //tạo số từ 1 đến 99 gán vào biến randomNumber
                numbers[index] = randomNumber; // Assign random number to the current position in numbers
                index++; // Increment index for the next element in the array
                JButton numberButton = new JButton(String.valueOf(randomNumber)); // tạo button với giá trị lấy từ randomNumber
                frame.add(numberButton); // thêm các button vào gridlayout
                
            }
        }
        frame.setVisible(true); 
    }
        // Mark a number
    public void markNumber(int number) {
        
    }
}
