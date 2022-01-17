package education.storage;

import education.model.Lesson;
import education.util.FileUtil;

import java.util.LinkedList;
import java.util.List;

public class LessonStorage {

    private List<Lesson> lessons = new LinkedList<>();

    public void add(Lesson lesson) {
        lessons.add(lesson);
        serialize();
    }


    public void print() {
        for (Lesson lesson : lessons) {
            System.out.println(lesson);
        }
        System.out.println();
    }

    public Lesson getByName(String name) {
        for (Lesson lesson : lessons) {
            if (lesson.getName().equals(name)) {
                return lesson;
            }
        }
        return null;
    }

    public boolean lessonsIsEmpty() {
        return lessons.isEmpty();
    }


    public void deleteLessonByName(String name) {
        for (Lesson lesson : lessons) {
            if (lesson.getName().equals(name)) {
                lessons.remove(lesson);
                break;
            }
        }
        serialize();
    }


    public void initData() {
        List<Lesson> lessonsList = FileUtil.deserializeLessons();
        if (lessonsList != null) {
            lessons = lessonsList;
        }
    }

    public void serialize() {
        FileUtil.serializeLessons(lessons);
    }
}
