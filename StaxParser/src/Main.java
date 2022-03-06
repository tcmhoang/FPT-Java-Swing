import controller.StudentsManager;
import gui.MainScreen;
import io.FileManager;
import utils.StudentParser;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            JFrame searchCanvas = new JFrame("Stax ASST 3");
            searchCanvas.setLayout(new BorderLayout(10, 10));
            searchCanvas.setPreferredSize(new Dimension(500, 700));
            searchCanvas.setFocusTraversalKeysEnabled(false);
            searchCanvas.setResizable(false);

            searchCanvas.add(new JLabel("IS1412 - tuanhmhe141540"), BorderLayout.NORTH);

            searchCanvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            searchCanvas.setLocationRelativeTo(null);

            FileManager fileManager = new FileManager("data.xml");
            StudentParser studentParser = null;
            try {
                studentParser = new StudentParser();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            StudentsManager studentsManager;
            MainScreen mainScreen;
            try {
                studentsManager = new StudentsManager(studentParser.read(fileManager.getReader()));
            } catch (JAXBException e) {
                e.printStackTrace();
                studentsManager = new StudentsManager(new ArrayList<>());
            }
            mainScreen = new MainScreen(studentsManager);

            searchCanvas.add(mainScreen, BorderLayout.CENTER);


            searchCanvas.pack();
            searchCanvas.setVisible(true);
            searchCanvas.setFocusable(true);

            final StudentsManager finalStudentsManager = studentsManager;
            final StudentParser finalStudentParser = studentParser;
            Runnable saveStates = () -> {
                try {
                    fileManager.write(finalStudentParser.exportData(finalStudentsManager.getStudents()));
                } catch (IOException | JAXBException e) {
                    e.printStackTrace();
                }
            };

            searchCanvas.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    saveStates.run();
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    saveStates.run();
                }
            });
        });

    }
}
