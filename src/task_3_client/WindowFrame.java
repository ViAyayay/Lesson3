package task_3_client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class WindowFrame extends JFrame {
    private final DataInputStream in;
    private final DataOutputStream out;
    private boolean isAuth = false;
    String name = "";
    JTextArea jta = new JTextArea();

    public WindowFrame(DataInputStream in, DataOutputStream out){
        this.in = in;
        this.out = out;

        getInterface();

        readMsg();


    }

    private void readMsg() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = in.readUTF();
                        if(strFromServer.startsWith("/authok")) {
                            String[] parts = strFromServer.split("\\s");
                            name = parts[1];
                            setAuthorized(true);
                            break;
                        }
                        jta.append(strFromServer + "\n");
                    }

                    File history = new File("history_"+name+".txt");
                    readHistory(history);

                    while (true) {
                        String strFromServer = in.readUTF();
                        if (strFromServer.equalsIgnoreCase("/end")) {
                            break;
                        }
                        if (strFromServer.startsWith("/cnok ")){
                            String[] part = strFromServer.split("\\s");
                            File newFile = new File("history_"+part[1]+".txt");
                            if(history.renameTo(newFile)){
                                name = part[1];
                                history = newFile;
                                continue;
                            }else{
                                break;
                            }
                        }
                        try(BufferedWriter writer = new BufferedWriter(new FileWriter(history, true))) {
                            writer.write(strFromServer+"\n");
                        }

                        jta.append(strFromServer);
                        jta.append("\n");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private void readHistory(File history){
        StringBuilder sb = new StringBuilder();
        try(RandomAccessFile raf=new RandomAccessFile(history, "r")){
            long seek = raf.length()-1;
            int number =0;
            while (seek>=0){
                raf.seek(seek);
                char symbol = (char)raf.read();
                if(symbol=='\n'){
                    number++;
                    if(number>100){
                        break;
                    }
                }
                sb.append(symbol);
                seek -=1;
            }
            sb.reverse();
            jta.setText(sb.toString());
            sb=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAuthorized(boolean b) {
        isAuth = b;
    }

    private void getInterface() {
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 400 , 400);

        jta.setEnabled(false);
        jta.setDisabledTextColor(new Color(0x09409C));

        JPanel authPanel = new JPanel();
        JPanel chatPanel = new JPanel();
        JPanel enterPanel = new JPanel();

        getAuthField(authPanel);
        getTextField(chatPanel, jta);
        getEnterField(enterPanel, jta);

        setVisible(true);
    }

    private void getAuthField(JPanel panel) {
        JPanel upPanel = new JPanel();
        JPanel loginPanel = new JPanel();
        JPanel passPanel = new JPanel();

        JTextArea loginTextArea = new JTextArea("login:");
        loginTextArea.setEnabled(false);
        JTextField loginTextField = new JTextField();
        JTextArea passTextArea = new JTextArea("pass:");
        passTextArea.setEnabled(false);
        JTextField passTextField = new JTextField();
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginTextField.getText().trim() != "" && passTextField.getText().trim()!="") {
                    try {
                        out.writeUTF(String.format("/auth %s %s", loginTextField.getText().trim(), passTextField.getText().trim()));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        loginPanel.setLayout(new BorderLayout());
        loginPanel.add(loginTextArea, BorderLayout.WEST);
        loginPanel.add(loginTextField, BorderLayout.CENTER);

        passPanel.setLayout(new BorderLayout());
        passPanel.add(passTextArea, BorderLayout.WEST);
        passPanel.add(passTextField, BorderLayout.CENTER);

        upPanel.setLayout(new GridLayout());
        upPanel.add(loginPanel);
        upPanel.add(passPanel);

        panel.setLayout(new BorderLayout());
        panel.add(upPanel, BorderLayout.CENTER);
        panel.add(insertButton, BorderLayout.AFTER_LINE_ENDS);
        panel.setEnabled(false);
        add(panel, BorderLayout.PAGE_START);
    }

    private void getEnterField(JPanel panel, JTextArea jta) {
        JTextField enterTextField = new JTextField();
        JButton insertButton = new JButton("Insert");
        ActionListener ActList = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outMsg(enterTextField.getText());
                enterTextField.setText("");
            }
        };
        insertButton.addActionListener(ActList);
        panel.setLayout((new BorderLayout()));
        panel.add(enterTextField, BorderLayout.CENTER);
        enterTextField.addActionListener(ActList);
        panel.add(insertButton, BorderLayout.AFTER_LINE_ENDS);
        panel.setEnabled(false);
        add(panel, BorderLayout.PAGE_END);
    }

    private void outMsg(String text) {
        try {
            out.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTextField(JPanel panel, JTextArea jta) {
        panel.setLayout(new BorderLayout());
        jta.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jta);
        panel.add(jsp);
        add(panel);
    }
}
