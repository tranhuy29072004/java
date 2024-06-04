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
