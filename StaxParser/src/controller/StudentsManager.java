package controller;

import models.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StudentsManager {
    List<Student> students;

    public StudentsManager(List<Student> data) {
        this.students = data;
    }

    public static Vector<Vector<Object>> toTableData(List<Student> data) {
        return data.stream().map(Student::toVector).collect(Collectors.toCollection(Vector::new));
    }

    public List<Student> getStudents() {
        students.sort(Comparator.comparingInt(Student::getId));
        return students;
    }

    public boolean add(Student student) {
        if (findStudentById(student.getId()).isPresent()) return false;
        students.add(student);
        return true;
    }

    public boolean remove(Student student) {
        return findStudentById(student.getId()).map(s -> students.remove(s)).orElse(false);
    }

    public Optional<Student> findStudentById(int id) {
        return students.stream().filter(s -> s.getId() == id).findAny();
    }

    public boolean update(Student newData) {
        return IntStream.range(0, students.size()).filter(i -> students.get(i).getId() == newData.getId()).boxed().findAny().map(i ->
                students.set(i, newData) != null
        ).orElse(false);
    }

    public List<Student> findStudentByName(String name) {
        final String stdName = name.toLowerCase(Locale.ROOT);
        return students.stream().filter(student -> student.getName().toLowerCase(Locale.ROOT).contains(stdName)).collect(Collectors.toList());
    }

}
