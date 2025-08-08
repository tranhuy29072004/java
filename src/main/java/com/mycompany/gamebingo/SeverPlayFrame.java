package com.mycompany.gamebingo;

import static com.mycompany.gamebingo.SeverFrame.dataout;
import static com.mycompany.gamebingo.SeverFrame.serverSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author TRAN HUY
 */
public class SeverPlayFrame extends javax.swing.JFrame implements Runnable{

    static ServerSocket serverSocket;
    HashMap clientlist = new HashMap();
    boolean exit = false;
    static DataOutputStream dataout;
    boolean status = false;
    int rand_num = 0;
    int[] random;
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
                    Set k = clientlist.keySet();
                    Iterator itr = k.iterator();
                    while(itr.hasNext())
                    {
                        String key = (String) itr.next();
                        if(!key.equalsIgnoreCase(UserName))
                        {
                            try
                            {
                                if (i.equals("win")){
                                    stop_thread();
                                    new DataOutputStream(((Socket)clientlist.get(key)).getOutputStream()).writeUTF("< server to all > Player "+ UserName+" "+ i);
                                }
                                else{
                                    new DataOutputStream(((Socket)clientlist.get(key)).getOutputStream()).writeUTF("< " + UserName + "> : " + i);
                                }
                            }
                            catch (IOException ex)
                            {
                                clientlist.remove(key);
                                txt_SVarea.append(key + " IS REMOVED!!"+"\n");
                                new PrepareClientList().start();
                            }
                        }
                    }                                      
                }
            } 
            catch (IOException ex) 
            {
                
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
                    txt_SVarea.append(key + " IS REMOVED!!"+"\n");
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_stop = new javax.swing.JButton();
        btn_start = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_SVarea = new javax.swing.JTextArea();
        jNum = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setMinimumSize(new java.awt.Dimension(383, 485));
        setResizable(false);
        setSize(new java.awt.Dimension(383, 485));

        btn_stop.setText("Stop");
        btn_stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stopActionPerformed(evt);
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

        jNum.setEditable(false);
        jNum.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jNum.setText("Số");
        jNum.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), null));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btn_start, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_stop, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
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
                    .addComponent(btn_stop, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addGap(102, 102, 102))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startBTN(){
        rand_num = 0;
        RandomArray ra = new RandomArray();
        ra.setArrayServer();
        random = ra.getArrayServer();
        exit = false;
        status = true;
        new Thread(this).start(); 
    }
    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        startBTN();
    }//GEN-LAST:event_btn_startActionPerformed

    private void btn_stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stopActionPerformed
        if(status){
            status = false;
            stop_thread();
            Set k = clientlist.keySet();
            Iterator itr = k.iterator();
            while(itr.hasNext())
            {
                String key = (String) itr.next();
                try
                {
                    new DataOutputStream(((Socket)clientlist.get(key)).getOutputStream()).writeUTF("<Server cancel game!>");
                }
                catch (IOException ex)
                {
                    System.out.println("lỖI");
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(this,"Chương trình chưa chạy", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_stopActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SeverPlayFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btn_start;
    public static javax.swing.JButton btn_stop;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jNum;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txt_SVarea;
    // End of variables declaration//GEN-END:variables
    // Chạy thread gửi số cho client
    @Override
    public void run() {
        while(rand_num<75&&!exit){
            jNum.setText(String.valueOf(random[rand_num]));
            System.out.print(String.valueOf(random[rand_num])+" ");
            Set k = clientlist.keySet();
            Iterator itr = k.iterator();
            while(itr.hasNext())
            {
                String key = (String) itr.next();
                try
                {
                    new DataOutputStream(((Socket)clientlist.get(key)).getOutputStream()).writeUTF(String.valueOf(random[rand_num]));
                }
                catch (IOException ex)
                {
                    System.out.println("lỖI");
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SeverPlayFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            rand_num++;
        }
        jNum.setText("Số");
    }
    // hàm stop thread gửi số cho client
    public void stop_thread(){
        exit = true;
        jNum.setText("Số");
    }
    
}
