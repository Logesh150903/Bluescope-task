package demoarray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

public class StudentData {
	public static void main(String[] args) {

		HashMap<String, Object> st1semester1Marks = new LinkedHashMap<>();
		st1semester1Marks.put("English", 65);
		st1semester1Marks.put("Maths", 85);
		st1semester1Marks.put("Tamil", 55);
		st1semester1Marks.put("Python", 54);
		System.out.println(" English "+st1semester1Marks.get("English"));
		System.out.println();
		
		HashMap<String, Object> st1semester2Marks = new LinkedHashMap<>();
		st1semester2Marks.put("Semeter", 2);
		st1semester2Marks.put("English", 60);
		st1semester2Marks.put("Maths", 85);
		st1semester2Marks.put("Computer", 54);
		st1semester2Marks.put("Tamil", 55);
		//System.out.println("HashMap 2 st1semester2Marks  "+st1semester2Marks.get("English"));
		
		HashMap<String, Object> st1semester = new LinkedHashMap<>();
		st1semester.put("Semester1",st1semester1Marks);
		st1semester.put("Semester2",st1semester2Marks);
		
		HashMap<String, Object>  English =(HashMap<String, Object>) st1semester.get("Semester1");
		System.out.println("1 "+English);
		System.out.println();	
		System.out.println("semester 1 English "+English.get("English"));
		System.out.println();
		
		
		ArrayList<HashMap<String, Object>> student1Marks = new ArrayList<>();
		student1Marks.add(st1semester);
		
		HashMap<String, Object>  st1Semester1english = (HashMap<String, Object>) student1Marks.get(0).get("Semester1");
		System.out.println("2 "+ st1Semester1english);
		System.out.println();
		System.out.println("student1 semester 1 English  "+st1Semester1english.get("English"));
		System.out.println();
		
		
		

		HashMap<String, Object> student1 = new LinkedHashMap<>();
		student1.put("Name", "Logesh");
		student1.put("Gender", "Male");
		student1.put("Marks", student1Marks);
		
		ArrayList<HashMap<String, Object>> st1Marksst1Semester1english = (ArrayList<HashMap<String, Object>>) student1.get("Marks");
		System.out.println("3 "+st1Marksst1Semester1english);
		System.out.println();
		
		HashMap<String, Object> student1Marksst1Semester1english = (HashMap<String, Object>) st1Marksst1Semester1english.get(0).get("Semester1");
		System.out.println("4 "+student1Marksst1Semester1english);
		System.out.println();
		
		System.out.println("Marks Semester 1 English "+student1Marksst1Semester1english.get("English"));
		System.out.println();
		
		//ArrayList<HashMap<String, Object>> studentMarks = (ArrayList<HashMap<String, Object>>) student1.get("Marks");
		//System.out.println("HashMap 2.1  "+studentMarks);
		
		HashMap<String, Object> st2semester1Marks = new LinkedHashMap<>();
		st2semester1Marks.put("English", 95);
		st2semester1Marks.put("Maths", 87);
		st2semester1Marks.put("Tamil", 50);
		st2semester1Marks.put("Python", 54);
		//System.out.println("HashMap 3 st2semester1Marks "+st2semester1Marks.get("English"));
		
		HashMap<String, Object> st2semester2Marks = new LinkedHashMap<>();
		st2semester2Marks.put("English", 55);
		st2semester2Marks.put("Maths", 88);
		st2semester2Marks.put("Computer", 74);
		st2semester2Marks.put("Tamil", 50);
		//System.out.println("HashMap 4 st2semester2Marks "+st2semester2Marks.get("English"));
	
		HashMap<String, Object> st1semester1 = new LinkedHashMap<>();
		st1semester1.put("Semester1",st1semester1Marks);
		st1semester1.put("Semester2",st1semester2Marks);
		
		ArrayList<HashMap<String, Object>> student2Marks = new ArrayList<>();
		student2Marks.add(st1semester1);
		
	//System.out.println("ArrayList 3  "+student2Marks.get(0).get("English"));
//		System.out.println("ArrayList 4 "+student2Marks.get(1).get("English"));

		HashMap<String, Object> student2 = new LinkedHashMap<>();
		student2.put("Name", "Logeshkumar");
		student2.put("Gender", "Male");
		student2.put("Marks", student2Marks);

		HashMap<String, Object> student = new LinkedHashMap<>();
		student.put("Student1", student1);
		student.put("Student2", student2);
		
		HashMap<String, Object> StusStu1MarksSemester1english = (HashMap<String, Object>) student.get("Student1");
		System.out.println(StusStu1MarksSemester1english);
		System.out.println();
		
		ArrayList<HashMap<String, Object>> StusStud1MarksSemester1english = (ArrayList<HashMap<String, Object>>) StusStu1MarksSemester1english.get("Marks");
		System.out.println(StusStud1MarksSemester1english);
		System.out.println();
		HashMap<String, Object> StusStudent1MarksSemester1english = (HashMap<String, Object>) StusStud1MarksSemester1english.get(0).get("Semester1");
		System.out.println(StusStudent1MarksSemester1english);
		System.out.println();
		System.out.println(StusStudent1MarksSemester1english.get("English"));
		System.out.println();

		
		ArrayList<HashMap<String, Object>> students = new ArrayList<>();
		students.add(student);
		System.out.println(students);
		System.out.println();
		System.out.println(students.get(0).get("Student1"));
		
		HashMap<String, Object> StudsStu1MarksSemester1english =(HashMap<String, Object>) students.get(0).get("Student1");
		System.out.println();
		System.out.println(StudsStu1MarksSemester1english);
		System.out.println();
		ArrayList<HashMap<String, Object>> StsStu1MarksSemester1english = (ArrayList<HashMap<String, Object>>)StudsStu1MarksSemester1english.get("Marks");
		System.out.println(StsStu1MarksSemester1english);
		System.out.println();
		HashMap<String, Object> StudentsStudent1MarksSemester1english = (HashMap<String, Object>) StsStu1MarksSemester1english.get(0).get("Semester1");
		System.out.println(StudentsStudent1MarksSemester1english.get("English"));
//		System.out.println(student69);
//		System.out.println();
//		
//		System.out.println(student68);
//		System.out.println();
//		System.out.println(student68.get("English"));
//		System.out.println();
//
//		
		HashMap<String, Object> Students = new LinkedHashMap<>();
		Students.put("Students", students);
//		System.out.println(Students.get("Students"));
//		System.out.println();
//		
		ArrayList<HashMap<String, Object>> rootStusStu1MarksSemester1english = (ArrayList<HashMap<String, Object>>) Students.get("Students");
		HashMap<String, Object> rootStusSt1MarksSemester1english =(HashMap<String, Object>) rootStusStu1MarksSemester1english.get(0).get("Student1");
		System.out.println();
		System.out.println(rootStusSt1MarksSemester1english);
		System.out.println();
		ArrayList<HashMap<String, Object>> rootStusStudent1MarksSemester1english = (ArrayList<HashMap<String, Object>>)rootStusSt1MarksSemester1english.get("Marks");
		System.out.println(rootStusStudent1MarksSemester1english);
		System.out.println();
		HashMap<String, Object> rootStudentsStudent1MarksSemester1english = (HashMap<String, Object>) rootStusStudent1MarksSemester1english.get(0).get("Semester1");
		System.out.println(rootStudentsStudent1MarksSemester1english.get("English"));
//		System.out.println(student689.get(0).get("Student1"));
//		System.out.println();
//		
//		System.out.println(student69);
//		System.out.println();
//		
//		System.out.println(student68);
//		System.out.println();
//		System.out.println(student68.get("English"));
//		System.out.println();


//		System.out.println(Students);
		
//		JSONObject studentData = new JSONObject(Students);
//		System.out.println(studentData);
		
//	 
	}
}
