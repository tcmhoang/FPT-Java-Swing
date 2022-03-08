package models;

import enums.EnumStudentXMLIndex;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Vector;

@XmlRootElement(name = "student")
@XmlType(propOrder = {"name", "gender", "age", "major", "grade"})


public class Student {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String major;
    private String grade;

    public Student(int id, String name, String gender, int age, String major, String grade) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.grade = grade;
    }

    public Student(String... data) throws NumberFormatException {
        this.id = Integer.parseInt(data[EnumStudentXMLIndex.ID.ordinal()]);
        this.name = data[EnumStudentXMLIndex.NAME.ordinal()];
        this.gender = data[EnumStudentXMLIndex.GENDER.ordinal()];
        this.age = Integer.parseInt(data[EnumStudentXMLIndex.AGE.ordinal()]);
        this.major = data[EnumStudentXMLIndex.MAJOR.ordinal()];
        this.grade = data[EnumStudentXMLIndex.GRADE.ordinal()];
    }

    public Student() {
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", major='" + major + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }

    public Vector<Object> toVector() {
        final Vector<Object> res = new Vector<>(6);
        res.add(EnumStudentXMLIndex.ID.ordinal(), getId());
        res.add(EnumStudentXMLIndex.NAME.ordinal(), getName());
        res.add(EnumStudentXMLIndex.AGE.ordinal(), getAge());
        res.add(EnumStudentXMLIndex.GENDER.ordinal(), getGender());
        res.add(EnumStudentXMLIndex.MAJOR.ordinal(), getMajor());
        res.add(EnumStudentXMLIndex.GRADE.ordinal(), getGrade());
        return res;
    }

    public static Student fromVector(Vector<Object> data) {
        return new Student(data.stream().map(Object::toString).toArray(String[]::new));
    }
}
