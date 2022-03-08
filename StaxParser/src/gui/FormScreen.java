package gui;

import components.FormField;
import enums.EnumFormBtn;
import enums.EnumStudentXMLIndex;
import models.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FormScreen extends JPanel {

    private final Predicate<Student> onCreate;
    private final Predicate<Student> onDelete;
    private final Predicate<Student> onUpdate;
    private final BiFunction<Integer, String, Boolean> onSearch;


    private List<JButton> btns;


    private List<FormField> ffs;

    private JPanel pnl_fields;
    private JPanel pnl_btns;


    public FormScreen(Predicate<Student> onCreate, Predicate<Student> onDelete, Predicate<Student> onUpdate, BiFunction<Integer, String, Boolean> onSearch) {
        this.onCreate = onCreate;
        this.onDelete = onDelete;
        this.onUpdate = onUpdate;
        this.onSearch = onSearch;

        setLayout(new BorderLayout(5, 5));

        __init__fields();
        __init__pnl_fields();

        __init_btns();
        __init_pnl_btns();

        setDefaultMode();

        add(pnl_fields, BorderLayout.CENTER);
        add(pnl_btns, BorderLayout.EAST);

    }

    private void reset() {
        ffs.forEach(f -> {
            f.clear();
            f.setFieldStatus(true);
        });

        btns.forEach(b -> b.setEnabled(true));
    }

    public void setDefaultMode() {
        reset();
        btns.get(EnumFormBtn.UPDATE.ordinal()).setEnabled(false);
        btns.get(EnumFormBtn.DELETE.ordinal()).setEnabled(false);
    }

    public void setFields(Vector<Object> data) {
        reset();
        if (data.size() != btns.size()) throw new IllegalArgumentException();
        IntStream.range(0, data.size()).forEach(i -> ffs.get(i).setValue(data.get(i).toString()));
        // Enter Update Mode or Delete Mode
        btns.get(EnumFormBtn.ADD.ordinal()).setEnabled(false);
        btns.get(EnumFormBtn.SEARCH.ordinal()).setEnabled(false);
        ffs.get(EnumStudentXMLIndex.ID.ordinal()).setFieldStatus(false);


    }


    private void __init__fields() {
        ffs = Arrays.stream(EnumStudentXMLIndex.values()).sequential().map(e -> e.name().substring(0, 1).toUpperCase(Locale.ROOT) + e.name().substring(1).toLowerCase(Locale.ROOT)).map(FormField::new).collect(Collectors.toList());
    }

    private void __init__pnl_fields() {
        pnl_fields = new JPanel();
        pnl_fields.setLayout(new GridLayout(EnumStudentXMLIndex.values().length, 1, 15, 15));
        ffs.forEach(pnl_fields::add);
    }


    private void __init_btns() {
        btns = Arrays.stream(EnumFormBtn.values()).sequential().map(e -> e.name().substring(0, 1).toUpperCase(Locale.ROOT) + e.name().substring(1).toLowerCase(Locale.ROOT)).map((s) -> {
            JButton btn = new JButton(s);
            btn.setBorder(new EmptyBorder(0, 0, 0, 10));
            return btn;
        }).collect(Collectors.toList());

        btns.get(EnumFormBtn.ADD.ordinal()).addActionListener(e -> {
            if (!onCreate.test(getInputStudent())) showDialog();

        });
        btns.get(EnumFormBtn.CLEAR.ordinal()).addActionListener(e -> setDefaultMode());
        btns.get(EnumFormBtn.EXIT.ordinal()).addActionListener(e -> {
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
        });
        btns.get(EnumFormBtn.DELETE.ordinal()).addActionListener(e -> {
            if (!onDelete.test(getInputStudent())) showDialog();
        });
        btns.get(EnumFormBtn.UPDATE.ordinal()).addActionListener(e -> {
            if (!onUpdate.test(getInputStudent())) showDialog();
        });
        btns.get(EnumFormBtn.SEARCH.ordinal()).addActionListener(e -> {
            try {
                String idVal = ffs.get(EnumStudentXMLIndex.ID.ordinal()).getValue();
                Integer id = idVal.isEmpty() ? -1 : Integer.parseInt(idVal);
                onSearch.apply(id, ffs.get(EnumStudentXMLIndex.NAME.ordinal()).getValue());
            } catch (NumberFormatException ex) {
                showDialog();
            }
        });

    }

    private void __init_pnl_btns() {
        pnl_btns = new JPanel();
        pnl_btns.setLayout(new GridLayout(EnumFormBtn.values().length, 1, 15, 15));
        btns.forEach(pnl_btns::add);
    }

    public Student getInputStudent() {
        try {
            return new Student(ffs.stream().sequential().map(FormField::getValue).toArray(String[]::new));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void showDialog() {
        JOptionPane.showMessageDialog(this, "Cannot Execute");
    }
}
