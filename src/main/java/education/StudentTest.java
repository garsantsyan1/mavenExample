package education;


import education.exception.UserNotFoundException;
import education.model.Lesson;
import education.model.Student;
import education.model.Type;
import education.model.User;
import education.storage.LessonStorage;
import education.storage.StudentStorage;
import education.storage.UserStorage;
import education.util.DateUtil;


import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class StudentTest implements UserCommands {

    static Scanner scanner = new Scanner(System.in);
    static StudentStorage studentStorage = new StudentStorage();
    static LessonStorage lessonStorage = new LessonStorage();
    static UserStorage userStorage = new UserStorage();

    public static void main(String[] args) throws ParseException {
        initData();
        boolean isRun = true;
        while (isRun) {
            UserCommands.printCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    login();
                    break;
                case REGISTER:
                    register();
                    break;
                default:
                    System.err.println("Invalid command!");
            }
        }

    }

    private static void register() {
        System.out.println("please input name, surname, email, password, type");
        String userDateStr = scanner.nextLine();
        String[] userDate = userDateStr.split(" ");
        if (userDate.length == 5) {
            try {
                userStorage.getByEmail(userDate[2]);
                System.err.println("this email already exist");
            } catch (UserNotFoundException e) {
                try {
                    User user = new User(userDate[0], userDate[1], userDate[2], userDate[3], Type.valueOf(userDate[4].toUpperCase()));
                    userStorage.add(user);
                } catch (IllegalArgumentException exp) {
                    System.err.println("type must be admin or user");
                }
            }
        } else {
            System.err.println("invalid date");
        }
    }

    private static void login() throws ParseException {
        System.out.println("please input email and password");
        String credentialStr = scanner.nextLine();
        String[] credential = credentialStr.split(" ");
        User user = null;
        try {
            user = userStorage.getByEmail(credential[0]);
            userStorage.getByEmail(credential[0]);
            if (user.getPassword().equals(credential[1])) {
                if (user.getType() == Type.ADMIN) {
                    adminCase();
                } else if (user.getType() == Type.USER) {
                    userCase();
                }
            } else {
                System.err.println("password is wrong!");
            }
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }


    }

    private static void deleteStudentByEmail() {
        System.out.println("please input email for delete student by email");
        String email = scanner.nextLine();
        if (studentStorage.getByEmail(email) != null) {
            studentStorage.deleteStudentByEmail(email);
            System.out.println("student successfully deleted");
        } else {
            System.err.println("There isn't student by this email");
        }
    }

    private static void deleteLessonByName() {
        System.out.println("please input lesson name for delete lesson by name");
        String name = scanner.nextLine();
        if (lessonStorage.getByName(name) != null) {
            lessonStorage.deleteLessonByName(name);
            System.out.println("lesson successfully deleted");
        } else {
            System.err.println("lesson doesn't exist");
        }

    }

    private static void printStudentsByLesson() {
        System.out.println("please input lesson name");
        String name = scanner.nextLine();
        if (lessonStorage.getByName(name) != null) {
            studentStorage.printStudentsByLesson(name);
        } else {
            System.out.println("lesson doesn't exist, add lesson");
            addLesson();
        }

    }

    private static void addStudent() throws ParseException {

        System.out.println("Please input name, surname, age, email, phone, date, lessons(name)");
        String studentDataStr = scanner.nextLine();
        String[] studentData = studentDataStr.split(" ");

        if (!lessonStorage.lessonsIsEmpty()) {
            if (studentData.length >= 7) {
                if (studentStorage.getByEmail(studentData[3]) == null) {
                    Lesson[] lessons = new Lesson[studentData.length - 6];
                    for (int i = 6, j = 0; i < studentData.length; i++, j++) {
                        if (lessonStorage.getByName(studentData[i]) != null) {
                            lessons[j] = lessonStorage.getByName(studentData[i]);
                        } else {
                            System.out.println("lesson by this name doesn't exist, add lesson");
                            addLesson();
                        }
                    }
                    int age = Integer.parseInt(studentData[2]);
                    Date date = DateUtil.stringToDate(studentData[5]);
                    Student student = new Student(studentData[0], studentData[1], age, studentData[3], studentData[4],
                            date, lessons);
                    studentStorage.add(student);
                } else {
                    System.out.println("The student already exist by this email");
                    addStudent();
                }
            } else {
                System.err.println("Invalid Date");
            }
        } else {
            System.out.println("lesson doesn't exist, add lesson");
            addLesson();
        }
    }

    private static void addLesson() {
        System.out.println("Please input name, duration, lecturerName, price");
        String lessonDataStr = scanner.nextLine();
        String[] lessonData = lessonDataStr.split(" ");

        if (lessonData.length == 4) {
            if (lessonStorage.getByName(lessonData[0]) == null) {
                Lesson lesson = new Lesson(lessonData[0], lessonData[1], lessonData[2], Integer.parseInt(lessonData[3]));
                lessonStorage.add(lesson);
            } else {
                System.out.println("lesson by this name already exist");
            }
        } else {
            System.err.println("Invalid Date");
        }

    }

    private static void adminCase() throws ParseException {
        boolean isRun = true;
        while (isRun) {
            UserCommands.printAdminCommands();
            String command = scanner.nextLine();
            switch (command) {
                case UserCommands.EXIT:
                    System.exit(0);
                    break;
                case ADD_LESSON:
                    addLesson();
                    break;
                case ADD_STUDENT:
                    addStudent();
                    break;
                case PRINT_STUDENTS:
                    studentStorage.print();
                    break;
                case PRINT_STUDENTS_BY_LESSON:
                    printStudentsByLesson();
                    break;
                case PRINT_LESSONS:
                    lessonStorage.print();
                    break;
                case DELETE_LESSON_BY_NAME:
                    deleteLessonByName();
                    break;
                case DELETE_STUDENT_BY_EMAIL:
                    deleteStudentByEmail();
                    break;
                case LOGOUT:
                    isRun = false;
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    private static void userCase() throws ParseException {
        boolean isRun = true;
        while (isRun) {
            UserCommands.printUserCommands();
            String command = scanner.nextLine();
            switch (command) {
                case UserCommands.EXIT:
                    System.exit(0);
                    break;
                case ADD_LESSON:
                    addLesson();
                    break;
                case ADD_STUDENT:
                    addStudent();
                    break;
                case PRINT_STUDENTS:
                    studentStorage.print();
                    break;
                case PRINT_STUDENTS_BY_LESSON:
                    printStudentsByLesson();
                    break;
                case PRINT_LESSONS:
                    lessonStorage.print();
                    break;
                case LOGOUT:
                    isRun = false;
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    private static void initData() {
        studentStorage.initData();
        lessonStorage.initData();
        userStorage.initData();
    }

}
