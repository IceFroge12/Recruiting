package ua.kpi.nc.service;

import ua.kpi.nc.domain.model.Student;

/**
 * Created by Chalienko on 14.04.2016.
 */
public interface StudentService {
    Student getStudentByID(Long ID);

    void insertStudent(Student student);
}
