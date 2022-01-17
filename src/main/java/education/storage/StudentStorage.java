package education.storage;

import education.model.Student;
import education.util.FileUtil;

import java.util.LinkedList;
import java.util.List;


public class StudentStorage {

    private List<Student> students = new LinkedList<>();


    public void add(Student student) {
        students.add(student);
        serialize();
    }

    public void print() {
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println();
    }

    public Student getByEmail(String email) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }

    public void printStudentsByLesson(String name) {
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < students.get(i).getLessons().length; j++) {
                if (students.get(i).getLessons()[j].getName().equals(name)) {
                    System.out.println(students.get(i) + " ");
                }
            }
        }
        System.out.println();
    }

    public void deleteStudentByEmail(String email) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                students.remove(student);
                break;
            }
        }
        serialize();
    }

    public void initData() {
        List<Student> studentsList = FileUtil.deserializeStudents();
        if (studentsList != null) {
            students = studentsList;
        }
    }

    public void serialize() {
        FileUtil.serializeStudents(students);
    }

}

