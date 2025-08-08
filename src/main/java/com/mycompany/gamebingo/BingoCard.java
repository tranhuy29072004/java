package com.mycompany.gamebingo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author TRAN HUY
 */
public class BingoCard extends RandomArray{
    private List<JButton> listButton ;
    private int[][] cardNumber = new int[5][5];
    // constructor
    public BingoCard(){}
    // method
    public List<JButton> getCard(){
        return listButton;
    }
    public int[][] getCardNumber(){
        return cardNumber;
    }
    public void newCard(JPanel jpn){
        listButton =  new ArrayList<>();
        RandomArray ra = new RandomArray();
        ra.setArray();
        int[] rdNumber = ra.getArray();
        for(int i = 0;i<25;i++){
            JButton btn  = new javax.swing.JButton();
            btn.setText(String.valueOf(rdNumber[i]));
            btn.setBackground(new java.awt.Color(255,255,255));
            btn.setForeground(new java.awt.Color(0,0,0));
            cardNumber[i/5][i%5] = rdNumber[i];
            btn.addActionListener((java.awt.event.ActionEvent evt) -> {
                if(btn.getBackground().equals(new java.awt.Color(255, 255, 255)))
                {
                    btn.setBackground(new java.awt.Color(0, 0, 0));
                    btn.setForeground(new java.awt.Color(255,255,255));
                }
                else {
                    btn.setBackground(new java.awt.Color(255,255,255));
                    btn.setForeground(new java.awt.Color(0,0,0));
                }
            });
            listButton.add(btn);
            jpn.add(btn);
        }
    }
    
}
