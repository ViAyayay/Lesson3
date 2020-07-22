package task_3_client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WindowFrame extends JFrame {
    private final DataInputStream in;
    private final DataOutputStream out;
    private boolean isAuth = false;
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
                            setAuthorized(true);
                            break;
                        }
                        jta.append(strFromServer + "\n");
                    }
                    while (true) {
                        String strFromServer = in.readUTF();
                        if (strFromServer.equalsIgnoreCase("/end")) {
                            break;
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
