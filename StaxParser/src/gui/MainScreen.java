package gui;

import controller.StudentsManager;

import javax.swing.*;
import java.util.Collections;
import java.util.Locale;

public class MainScreen extends JPanel {


    private FormScreen ft_form;
    private DataScreen ft_viewer;
    private StudentsManager studentsManager;

    public MainScreen(StudentsManager manager) {
        studentsManager = manager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ft_form = new FormScreen(
                e -> e != null && manager.add(e) && ft_viewer.update(StudentsManager.toTableData(studentsManager.getStudents())),
                e -> e != null && manager.remove(e) && ft_viewer.update(StudentsManager.toTableData(studentsManager.getStudents())),
                e -> e != null && manager.update(e) && ft_viewer.update(StudentsManager.toTableData(studentsManager.getStudents())),
                (id, name) -> id == -1 ? ft_viewer.update(StudentsManager.toTableData(studentsManager.findStudentByName(name))) : ft_viewer.update(StudentsManager.toTableData(studentsManager.findStudentById(id).filter(student -> student.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))).map(Collections::singletonList).orElse(Collections.emptyList())))
        );

        ft_viewer = new DataScreen(StudentsManager.toTableData(studentsManager.getStudents()), ft_form::setFields);
        add(ft_form);
        add(ft_viewer);
    }


}
