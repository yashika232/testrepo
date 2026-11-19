package com.learn.student.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.learn.student.entity.Student;
 
public interface Studentrepository extends JpaRepository<Student, Integer> {
 
}
 