package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Enums.CardStatus;
import com.example.librarymanagementsystem.Enums.Department;
import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Models.Student;
import com.example.librarymanagementsystem.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String addStudent(Student student)throws Exception{

        if(student.getRollNo()!=null) {
            throw new Exception("Id should not be sent as a parameter");
        }
        studentRepository.save(student);
        return "Student has been added successfully";
    }

    public Department getDepartmentById(Integer rollNo)throws Exception{

        Optional<Student> optionalStudent = studentRepository.findById(rollNo);

        if(!optionalStudent.isPresent()) {
            throw new Exception("Roll No Entered is Invalid");
        }
        Student student = optionalStudent.get();

        return student.getDepartment();
    }
    public CardStatus getCardStatusByStudentId(Integer Id) throws Exception
    {
        Optional<Student> optionalStudent = studentRepository.findById(Id);
        if(!optionalStudent.isPresent()) {
            throw new Exception("Roll No Entered is Invalid");
        }
        Student student = optionalStudent.get();
        LibraryCard card= student.getLibraryCard();
        return card.getCardStatus();



    }
    public List<String> getStudentNames() {
        List<Student> allStudents = studentRepository.findAll();
        List<String> studentList = new ArrayList<>();
        for (Student student : allStudents) {
            if (!student.getLibraryCard().getCardStatus().equals("Active")) {
                studentList.add(student.getName());
            }
        }
        return studentList;
    }

}
