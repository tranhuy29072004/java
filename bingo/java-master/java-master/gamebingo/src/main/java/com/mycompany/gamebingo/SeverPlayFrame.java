/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gamebingo;

import static com.mycompany.gamebingo.ServerFrame.datain;
import static com.mycompany.gamebingo.ServerFrame.dataout;
import static com.mycompany.gamebingo.ServerFrame.serverSocket;
import static com.mycompany.gamebingo.ServerFrame.socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author TRAN HUY
 */
public class SeverPlayFrame extends javax.swing.JFrame{

    static ServerSocket serverSocket;
    HashMap clientlist = new HashMap();
    
    
    static Socket socket;
    static DataInputStream datain;
    static DataOutputStream dataout;

    /**
     * Creates new form SeverPlayFrame
     */
    public SeverPlayFrame() {
        try
        {
            initComponents();
            serverSocket = new ServerSocket(8080); //server chạy ở port 8080
            new ClientAccept().start(); // tạo 1 thread mới cho client
            
        }
        catch (IOException ex)
        {
            System.out.print("loi init components");
        }
    }

    class ClientAccept extends Thread
    {
        @Override
        public void run()
        {
            while(true)
            {
                try
                {
                    Socket clientsk = serverSocket.accept(); // server chấp nhận kết nối của client
                    String i = new DataInputStream(clientsk.getInputStream()).readUTF(); //lưu dữ liệu phản hồi từ sv
                    if(clientlist.containsKey(i)) // nếu đã có tên đăng nhập này (khóa chính của HashMap) rồi sẽ hiện thông báo "YOU ALREADY REGISTERED!!"
                    {
                        dataout = new DataOutputStream(clientsk.getOutputStream());
                        dataout.writeUTF("YOU ALREADY REGISTERED!!");
                    }
                    else // ngược lại sẽ lưu tên đăng nhập và socket của client đó vào hashmap hiện thông báo 
                    {
                        clientlist.put(i, clientsk);
                        txt_SVarea.append(i + " JOINED !!! \n");
                        DataOutputStream dataoutSV = new DataOutputStream(clientsk.getOutputStream());
                        dataoutSV.writeUTF("");
                        new MsgRead(clientsk, i).start();
                        new PrepareClientList().start();

                        //new MsgRead(sk,i).run();
                        //chuoiVaoSV = datainSV.readUTF();
                        //txt_SVarea.setText(txt_SVarea.getText().trim() + "\n" + i + ": " + chuoiVaoSV + "\n"); //hiện dữ liệu từ client lên msg_area
                    }
                    
                }
                catch (IOException ex)
                {
                    System.out.print("loi ten dang nhap");
                }
            }
        }  
    }
    class MsgRead extends Thread
    {
        Socket clientsk;
        String UserName;
        
        MsgRead(Socket clientsk, String UserName)
        {
            this.clientsk = clientsk;
            this.UserName = UserName;
        }
        
        @Override
        public void run()
        {
            try {
                while (!clientlist.isEmpty()) {
                    String i = new DataInputStream(clientsk.getInputStream()).readUTF();
                    if(i.equals("mkoihgteazdcvgyhujb096785542AXTY"))
                    {
                        clientlist.remove(UserName);
                        txt_SVarea.append(UserName + " IS REMOVED!! \n");
                        new PrepareClientList().start();
                        Set<String> k = clientlist.keySet();
                        

                        Iterator itr = k.iterator();
                        while(itr.hasNext())
                        {
                            String key = (String) itr.next();
                            if(!key.equalsIgnoreCase(UserName))
                            {
                                try
                                {
                                    new DataOutputStream(((Socket)clientlist.get(key)).getOutputStream()).writeUTF("< " + UserName + " to all > " + i);
                                }
                                catch (Exception ex)
                                {
                                    clientlist.remove(key);
                                    txt_SVarea.append(key + " IS REMOVED!!");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    }
                    else if(i.equals("#4344554@@@@@67667@@"))
                    {
                        i = i.substring(20);
                        StringTokenizer st = new StringTokenizer(i,":");
                        String id = st.nextToken();
                        i = st.nextToken();
                        try
                        {
                            new DataOutputStream(((Socket)clientlist.get(id)).getOutputStream()).writeUTF("< " + UserName + " to " + id + " > " + i);
                        }
                        catch (Exception ex)
                        {
                            clientlist.remove(id);
                            txt_SVarea.append(id + " IS REMOVED!!");
                            new PrepareClientList().start();
                        }
                    }
                    else
                    {
                        Set k = clientlist.keySet();
                        Iterator itr = k.iterator();
                        while(itr.hasNext())
                        {
                            String key = (String) itr.next();
                            if(!key.equalsIgnoreCase(UserName))
                            {
                                try
                                {
                                    new DataOutputStream(((Socket)clientlist.get(key)).getOutputStream()).writeUTF("< " + UserName + " to all > " + i);
                                }
                                catch (Exception ex)
                                {
                                    clientlist.remove(key);
                                    txt_SVarea.append(key + " IS REMOVED!!");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    }
                }
            } 
            catch (Exception ex) 
            {
                ex.printStackTrace();
            }
        }
    }
    
    class PrepareClientList extends Thread
    {
        @Override
        public void run()
        {
            String usernames ="";
            Set k = clientlist.keySet();
            Iterator itr = k.iterator();
            while (itr.hasNext())
            {
                String key = (String) itr.next();
                usernames += key + ",";
            }
            if(usernames.length() != 0)
            {
                usernames = usernames.substring(0,usernames.length() - 1);
            }
            itr = k.iterator();
            while (itr.hasNext())
            {
                String key = (String) itr.next();
                try
                {
                    new DataOutputStream(((Socket)clientlist.get(key)).getOutputStream()).writeUTF(":;.,/=" + usernames);
                    
                }
                catch (IOException ex)
                {
                    clientlist.remove(key);
                    txt_SVarea.append(key + " IS REMOVED!!");
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

        btn_next = new javax.swing.JButton();
        btn_start = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_SVfield = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_SVarea = new javax.swing.JTextArea();
        btn_SVsend = new javax.swing.JButton();
        jNum = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setMinimumSize(new java.awt.Dimension(383, 485));
        setResizable(false);
        setSize(new java.awt.Dimension(383, 485));

        btn_next.setText("Next");
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        btn_start.setText("Start");
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("BINGO SERVER");

        txt_SVarea.setColumns(20);
        txt_SVarea.setRows(5);
        jScrollPane1.setViewportView(txt_SVarea);

        btn_SVsend.setText("Send");
        btn_SVsend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SVsendActionPerformed(evt);
            }
        });

        jNum.setEditable(false);
        jNum.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jNum.setText("Số");
        jNum.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), null));
        jNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNumActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btn_start, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_SVfield, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_SVsend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jNum, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jNum, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_start, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_SVfield)
                    .addComponent(btn_SVsend, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    int i = 0;
    int[] random;
    /*private void startBTN(){
        i = 0;
        RandomArray ra = new RandomArray();
        ra.setArrayServer();
        random = ra.getArrayServer();
        Thread t = new Thread(this);
        t.start();  
    }*/
    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        //startBTN();
    }//GEN-LAST:event_btn_startActionPerformed

    private void btn_SVsendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SVsendActionPerformed
        // TODO add your handling code here:
        /*try
        {
            String msgout = "";
            msgout = txt_SVfield.getText().trim();
            dataoutSV.writeUTF(msgout);
        }
        catch (IOException e)
        {
            System.out.print("msgout error");
        }*/
    }//GEN-LAST:event_btn_SVsendActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_nextActionPerformed

    private void jNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jNumActionPerformed

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
            java.util.logging.Logger.getLogger(SeverPlayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SeverPlayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SeverPlayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SeverPlayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SeverPlayFrame().setVisible(true);
            }
        });
        /*String msgin = "";
        try
        {
            
            serverSocket = new ServerSocket(8080);// server chạy ở port 8080
            socket = serverSocket.accept(); // server chấp nhận kết nối
            datainSV = new DataInputStream(socket.getInputStream());
            dataoutSV = new DataOutputStream(socket.getOutputStream());
            while (!msgin.equals("exit"))
            {
                msgin = datainSV.readUTF();
                 txt_SVarea.setText(txt_SVarea.getText().trim() + "\n" + msgin); //hiện dữ liệu từ client lên msg_area
                
            }
        }
        catch (IOException e)
        {
            System.out.print("msgin error");
        }
        */
      
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton btn_SVsend;
    public static javax.swing.JButton btn_next;
    public static javax.swing.JButton btn_start;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jNum;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea txt_SVarea;
    private static javax.swing.JTextField txt_SVfield;
    // End of variables declaration//GEN-END:variables
    
    /*@Override
    public void run() {
        
        while(i<75){
            jNum.setText(String.valueOf(random[i]));
            try
            { 
                dataoutSV.writeUTF(String.valueOf(random[i]));
            }
            catch (IOException e)
            {
                System.out.print("msgout error");
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SeverPlayFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print(String.valueOf(random[i])+ " " );
            
            i++;
        }
    }*/
}
