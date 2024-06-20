<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamebingo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author TRAN HUY
 */
public class BingoCard extends RandomArray{
    private List<JButton> listButton ;
    private RandomArray ra = new RandomArray();
    // constructor
    public BingoCard(){}
    // method
    public List<JButton> getCard(){
        return listButton;
    }
    public RandomArray getRA(){
        return ra;
    }
    public void newCard(JPanel jpn){
        listButton =  new ArrayList<>();
        ra.setArray();
        int[] rdNumber = ra.getArray();
        for(int i = 0;i<25;i++){
            JButton btn = new JButton(String.valueOf(rdNumber[i]));
            listButton.add(btn);
            jpn.add(btn);
        }
    }
    
}
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamebingo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author TRAN HUY
 */
public class BingoCard extends RandomArray{
    private List<JButton> listButton ;
    private RandomArray ra = new RandomArray();
    private int[][] cardNumber = new int[5][5];
    // constructor
    public BingoCard(){}
    // method
    public List<JButton> getCard(){
        return listButton;
    }
    public RandomArray getRA(){
        return ra;
    }
    public int[][] getCardNumber(){
        return cardNumber;
    }
    public void newCard(JPanel jpn){
        
        listButton =  new ArrayList<>();
        ra.setArray();
        int[] rdNumber = ra.getArray();
        for(int i = 0;i<25;i++){
            JButton btn  = new javax.swing.JButton();
            btn.setText(String.valueOf(rdNumber[i]));
            btn.setBackground(new java.awt.Color(255,255,255));
            btn.setForeground(new java.awt.Color(0,0,0));
            cardNumber[i/5][i%5] = rdNumber[i];
            btn.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if(btn.getBackground().equals(new java.awt.Color(255, 255, 255)))
                    {
                        btn.setBackground(new java.awt.Color(0, 0, 0));
                        btn.setForeground(new java.awt.Color(255,255,255));
                    }
                    else {
                        btn.setBackground(new java.awt.Color(255,255,255));
                        btn.setForeground(new java.awt.Color(0,0,0));
                    }
                }
            });
            listButton.add(btn);
            jpn.add(btn);
        }
    }
    
}
>>>>>>> de89614f9158a98721e9df4c8afbde694356fc9b
