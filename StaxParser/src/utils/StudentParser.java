package utils;

import models.Student;
import models.Students;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventFactory;
import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class StudentParser {
    private JAXBContext ctx;

    public StudentParser() throws JAXBException {
        ctx = JAXBContext.newInstance(Students.class);
    }

    public List<Student> read(BufferedReader br) throws JAXBException {
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        Students students = (Students) unmarshaller.unmarshal(br);
        return students.getStudents();
    }

    public String exportData(List<Student> data) throws JAXBException {
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(new Students(data), sw);
        return sw.toString();
    }


}
