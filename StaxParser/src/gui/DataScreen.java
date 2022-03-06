package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DataScreen extends JScrollPane {


    private Vector<Vector<Object>> data;

    private final JTable tbl;
    private final Consumer<Vector<Object>> onSelectRow;

    private final static Vector<String> headers = Arrays.stream(new String[]{
            "ID",
            "Name",
            "Age",
            "Gender",
            "Major",
            "Grade"
    }).collect(Collectors.toCollection(Vector::new));

    public DataScreen(Vector<Vector<Object>> rawData, Consumer<Vector<Object>> onSelectRow) {
        data = rawData;
        this.onSelectRow = onSelectRow;
        tbl = new JTable();
        tbl.setDefaultEditor(Object.class, null);
        display();

        tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbl.getSelectedRow() != -1)
                this.onSelectRow.accept(data.get(tbl.getSelectedRow()));
        });
        setViewportView(tbl);
    }

    public boolean update(Vector<Vector<Object>> data) {
        this.data = data;
        display();
        return true;
    }

    public void display() {
        tbl.setModel(new DefaultTableModel(data, headers));
    }


}
