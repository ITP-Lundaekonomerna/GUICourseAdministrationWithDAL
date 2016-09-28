package app.controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import app.dal.DALCourse;
import app.dal.DALStudent;
import app.model.Row;
import app.model.Student;


public class Controller {

	JFrame customerFrame; 		//Refererar till det grafiska gränssnittet
	DALStudent dalStudent;
	DALCourse dalCourse;

	public Controller() {
		dalStudent = new DALStudent();
		dalCourse = new DALCourse();
	}
	
	public ArrayList<Student> findAllStudents() {
		
		ArrayList<Student> valuesFromDatabase = dalStudent.findAllStudents();
		return valuesFromDatabase;
		
		/**
		 * You can also write:	
		 * return dalStudent.findAllStudents(); 
		 * 
		 * **/
	}
	
	/** Can also be void, the int-value is number of affected rows in database after query **/
	public int addStudent(Student s) {
		return dalStudent.addStudent(s);
	}	
	
	
	
	public ArrayList<Row> findAllCoursesByStudentID(String studentID) {
		return dalCourse.findCoursesByStudentID(studentID);
	}
	
	
	public Student findStudentByStudentID(String studentID) {
		return dalStudent.findStudentByStudentID(studentID);
	}

	
	
}