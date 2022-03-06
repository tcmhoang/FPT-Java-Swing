package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormField extends JPanel {

    private final JTextField txt;

    public FormField(String label) {


        setLayout(new BorderLayout());

        JLabel lbl = new JLabel(label, JLabel.LEFT);
        lbl.setSize(50, 20);
        lbl.setBorder(new EmptyBorder(0, 10, 0, 0));

        lbl.setPreferredSize(new Dimension(70, 20));

        txt = new JTextField(25);

        add(lbl, BorderLayout.WEST);
        add(txt, BorderLayout.CENTER);


    }

    public String getValue() {
        return txt.getText().trim();
    }

    public void setValue(String value) {
        txt.setText(value);
    }


    public void setFieldStatus(boolean status) {
        txt.setEnabled(status);
    }

    public void clear() {
        txt.setText("");
    }

}
