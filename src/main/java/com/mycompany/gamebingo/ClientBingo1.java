/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gamebingo;


import java.awt.Color;
import java.awt.HeadlessException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author TRAN HUY
 */
public class ClientBingo1 extends javax.swing.JFrame {
    boolean isPlaySound = true;
    String id;
    String clientID = "";
    static DataInputStream datain;
    static DataOutputStream dataout;
    DefaultListModel dlm;
    long diemSo;
    SoundHelper sh ;
    boolean isPlay = false;
    public static List<JButton> lBT;
    static BingoCard card = new BingoCard();
    static Socket sk;
    static int[] rows = new int[5];
    static int[] cols = new int[5];
    static int mainCross = 0;
    static int otherCross = 0;
    static boolean[][] status = new boolean[5][5];
    

    

    private static void fillCard(String msgin) {
        boolean check = false;
        int x = Integer.parseInt(msgin);
        
        int[][] cn = card.getCardNumber();
        for(int i = 0; i<5; i++){
            for(int j = 0; j < 5;j++){
                if(cn[i][j]==x){
                    check = true;
                    status[i][j] = true;
                    rows[i]++;
                    cols[j]++;
                    if(i==j) mainCross++;
                    if(i+j == 4) otherCross++;
                    break;
                }
            }
            if(check) break;
        }
    }
    /**
     * Creates new form ClientBingo1
     */
    public ClientBingo1() {
        
        
        initComponents();
        card.newCard(jpn_card);
    }

    ClientBingo1(String i, Socket s, String hinhnen, long diemSo)
    {
        try {
            sh = new SoundHelper("/nhacnen.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(ClientBingo1.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.diemSo = diemSo;
        id = i;
        try {
            initComponents();
            if ("mot".equals(hinhnen)) {
                hinhnen1.setVisible(true);
                hinhnen2.setVisible(false);
                hinhnen3.setVisible(false);
                hinhnen4.setVisible(false);
            }
            if ("hai".equals(hinhnen)) {
                hinhnen1.setVisible(false);
                hinhnen2.setVisible(true);
                hinhnen3.setVisible(false);
                hinhnen4.setVisible(false);
            }
            if ("ba".equals(hinhnen)) {
                hinhnen1.setVisible(false);
                hinhnen2.setVisible(false);
                hinhnen3.setVisible(true);
                hinhnen4.setVisible(false);
            }
            if ("bon".equals(hinhnen)) {
                hinhnen1.setVisible(false);
                hinhnen2.setVisible(false);
                hinhnen3.setVisible(false);
                hinhnen4.setVisible(true);
            }
            
            txt_area.setBackground(new Color(0, 0, 0, 50));
            this.setTitle("Bingo");
            txtDiemSo.setText(String.valueOf(diemSo));
            lb_userID.setText("PLAYER: " + i);
            dlm = new DefaultListModel();
            datain = new DataInputStream(s.getInputStream());
            dataout = new DataOutputStream(s.getOutputStream());
            new Read().start();
            card.newCard(jpn_card);
        }
        catch (IOException ex)
        {
        }
    }
    public void restart_btn(){
        jpn_card.removeAll();
        jpn_card.revalidate();                    
        jpn_card.repaint();
        card.newCard(jpn_card);
    }
    public void restart_status(){
        rows = new int[5];
        cols = new int[5];
        mainCross = 0;
        otherCross = 0;
        status = new boolean[5][5];
        isPlay = false;
    }
    class Read extends Thread
    {
        @Override
        public void run()
        {
            while (true) 
            {
                try{
                    String datainClient = datain.readUTF();
                    if (datainClient.contains(":;.,/=")) {
                        datainClient = datainClient.substring(6);
                        StringTokenizer st = new StringTokenizer(datainClient, ",");
                        while (st.hasMoreTokens()) {
                            String u = st.nextToken();
                            if (!id.equals(u)) {
                                dlm.addElement(u);
                            }
                        }
                    } 
                    else {
                        if (datainClient.contains("<")){
                            txt_area.append("" + datainClient + "\n");
                            if(datainClient.contains("server to all")){
                                diemSo = diemSo - 5;
                                JOptionPane.showMessageDialog(null,"Bạn đã thua, bạn bị trừ 5 điểm","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                                restart_status();
                                changeButton();
                                jNum.setText("Số");
                            }
                            else if (datainClient.equals("<Server cancel game!>")){
                                restart_status();
                                changeButton();
                                jNum.setText("Số");
                                JOptionPane.showMessageDialog(null,"Server hủy trận dấu","Error",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else{
                            isPlay = true;
                            jNum.setText(datainClient);
                            fillCard(datainClient);
                        }
                    }
                    
                }
                catch (HeadlessException | IOException ex)
                {
                    
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_userID = new javax.swing.JLabel();
        txtDiemSo = new javax.swing.JLabel();
        btnSound = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jpn_card = new javax.swing.JPanel();
        btn_change = new javax.swing.JButton();
        btn_bingo = new javax.swing.JButton();
        jNum = new javax.swing.JLabel();
        txt_field = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_area = new javax.swing.JTextArea();
        btn_send = new javax.swing.JButton();
        hinhnen3 = new javax.swing.JLabel();
        hinhnen4 = new javax.swing.JLabel();
        hinhnen1 = new javax.swing.JLabel();
        hinhnen2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("i");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(414, 485));
        setMinimumSize(new java.awt.Dimension(414, 485));
        setResizable(false);
        setSize(new java.awt.Dimension(414, 485));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                WindowHandler(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_userID.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lb_userID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_userID.setText("BINGO: PLAYER 1");
        getContentPane().add(lb_userID, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 220, 20));

        txtDiemSo.setBackground(new java.awt.Color(255, 255, 255));
        txtDiemSo.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtDiemSo.setToolTipText("");
        getContentPane().add(txtDiemSo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 180, 50));

        btnSound.setBackground(new java.awt.Color(250, 195, 235));
        btnSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sound_1.png"))); // NOI18N
        btnSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoundActionPerformed(evt);
            }
        });
        getContentPane().add(btnSound, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 60, -1));

        jLabel2.setBackground(new java.awt.Color(102, 102, 0));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Điểm số");
        jLabel2.setToolTipText("");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 70, 30));

        jpn_card.setLayout(new java.awt.GridLayout(5, 5));
        getContentPane().add(jpn_card, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 250, 249));

        btn_change.setText("Đổi số");
        btn_change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_changeActionPerformed(evt);
            }
        });
        getContentPane().add(btn_change, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 93, 35));

        btn_bingo.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btn_bingo.setText("BINGO");
        btn_bingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bingoActionPerformed(evt);
            }
        });
        getContentPane().add(btn_bingo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, -1, 63));

        jNum.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jNum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jNum.setText("số");
        jNum.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        getContentPane().add(jNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 114, 94));
        getContentPane().add(txt_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 160, 50));

        txt_area.setColumns(20);
        txt_area.setRows(5);
        txt_area.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_area.setDisabledTextColor(new java.awt.Color(242, 242, 242));
        jScrollPane1.setViewportView(txt_area);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 260, 118));

        btn_send.setText("Send");
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });
        getContentPane().add(btn_send, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 93, 35));

        hinhnen3.setBackground(null);
        hinhnen3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg2.png"))); // NOI18N
        getContentPane().add(hinhnen3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, 560));

        hinhnen4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg3.png"))); // NOI18N
        getContentPane().add(hinhnen4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        hinhnen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg4.png"))); // NOI18N
        getContentPane().add(hinhnen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        hinhnen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg1.png"))); // NOI18N
        getContentPane().add(hinhnen2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void send(String id_send){
        try
        {
            String m = txt_field.getText(),mm=m;
            m = m.toLowerCase();
            if (("win".equals(m) && id.equals(id_send))||m.contains("server")){
                JOptionPane.showMessageDialog(this, "chỉ có chữ win hoặc tồn tại chữ server trong đoạn chat", "ERROR", JOptionPane.ERROR_MESSAGE);
                txt_field.setText("");
                jNum.setText("");
            }
            else{
                String CI = clientID;
                if(!clientID.isEmpty())
                {
                    m = "#4344554@@@@@67667@@";
                    dataout.writeUTF(m);
                    txt_field.setText("");
                    txt_area.append("<you send to " + CI + ">" + mm + "\n");
                }
                else
                {
                    dataout.writeUTF(m);
                    txt_field.setText("");
                    txt_area.append("<" +id_send+">" + " : "+ mm + "\n");
                }
            }
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(this,"User does not exist");
        }
    }
    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        send(id);
    }//GEN-LAST:event_btn_sendActionPerformed

    private void btn_bingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bingoActionPerformed
        boolean check = bingoCheck();
        if (!isPlay) check = false;
        if(check){
            txt_field.setText("win");
            send("server");
            diemSo = diemSo + 5;
            txtDiemSo.setText(String.valueOf(diemSo));
            JOptionPane.showMessageDialog(null, "Chúc mừng "+ id + " ,bạn đã chiến thắng!", "CONGRATULATION", JOptionPane.INFORMATION_MESSAGE);
            restart_status();
            changeButton();
            jNum.setText("Số");
        } 
        else changeButton();
    }//GEN-LAST:event_btn_bingoActionPerformed

    private void btnSoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoundActionPerformed
        if (isPlaySound){
            sh.stop();
            isPlaySound = false;
            btnSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nosound.png")));
        }
        else{
            isPlaySound = true;
            sh.play();
            btnSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sound_1.png")));
        }
    }//GEN-LAST:event_btnSoundActionPerformed

    private void btn_changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_changeActionPerformed
        if(!isPlay){
            restart_btn();
            changeButton();
        }
        else{
            JOptionPane.showMessageDialog(null, "Bạn đang trong thời gian chơi","Warning",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_changeActionPerformed

    private void WindowHandler(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_WindowHandler
        if(isPlay){
            JOptionPane.showMessageDialog(null, "Bạn đang trong thời gian chơi, Nếu bạn thoát ra bạn bị mất 5 điểm","Warning",JOptionPane.WARNING_MESSAGE);
            WriteToFile(String.valueOf(diemSo-5),id);
        }
        WriteToFile(String.valueOf(diemSo),id);
        
    }//GEN-LAST:event_WindowHandler

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientBingo1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientBingo1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientBingo1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientBingo1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new ClientBingo1().setVisible(true);

            }
        });
        try
            {
                sk = new Socket("127.0.0.1",8080);
                
                String msgin = "";
                while (!msgin.equals("exit"))
                {
                    msgin = datain.readUTF();
                    fillCard(msgin);
                    jNum.setText(msgin); //hiện dữ liệu từ client lên msg_area
                }
            
            }
        catch (IOException e){}
        lBT = card.getCard();
        
        
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSound;
    private javax.swing.JButton btn_bingo;
    private static javax.swing.JButton btn_change;
    private static javax.swing.JButton btn_send;
    private javax.swing.JLabel hinhnen1;
    private javax.swing.JLabel hinhnen2;
    private javax.swing.JLabel hinhnen3;
    private javax.swing.JLabel hinhnen4;
    private javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jNum;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JPanel jpn_card;
    private javax.swing.JLabel lb_userID;
    private javax.swing.JLabel txtDiemSo;
    private static javax.swing.JTextArea txt_area;
    private static javax.swing.JTextField txt_field;
    // End of variables declaration//GEN-END:variables

    private boolean bingoCheck() {
        for(int i = 0;i<5;i++){
            if(rows[i]==5||cols[i]==5){
                return true;
            }
        }
        return mainCross==5||otherCross==5;
    }

    private void changeButton() {
        List<JButton> jb = card.getCard();
        for(int i=0;i<25;i++){
            JButton btn = jb.get(i);
            if(btn.getBackground().equals(new java.awt.Color(0, 0, 0))&&!status[i/5][i%5]){
                btn.setBackground(new java.awt.Color(255,255,255));
                btn.setForeground(new java.awt.Color(0,0,0));
            }
        }
    }
     public String getPath(String name){
        URL url =getClass().getResource("/11.wav");
        if(url!= null){
            String path = url.toString();
            return path.substring(6,path.length()-6) + name ;
        }
        return "";
    }
    public void WriteToFile(String t, String file_name){
        String path = file_name.replace(" ", "_");
        path = path+".txt";
        File file = new File(getPath(path));
        try {
            if(file.exists()) {file.delete();}
            file.createNewFile();
            OutputStream output_file = new FileOutputStream(file);
            OutputStreamWriter ghi = new OutputStreamWriter(output_file);
            ghi.write(t);
            ghi.flush();
            ghi.close();
        }
        catch (IOException e){
        }
        
    }
}

