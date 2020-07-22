package task_3_client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

public class ButtonListener implements ActionListener {
    private JTextField textField;
    private JTextArea textArea;
    private DataOutputStream out;

    public ButtonListener(JTextField jtf, JTextArea jta, DataOutputStream out) {
        textField = jtf;
        textArea = jta;
    }

//    public ButtonListener(JTextField jtf, JTextField jta) {
//        textField = jtf;
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textArea.append(textField.getText()+"\n");
        textField.setText("");
        try {
            out.writeUTF(textField.getText());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
