package demoarray;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Demo {

	public void rootStu1() {
		HashMap<String, Object> sub1 = new LinkedHashMap<String, Object>();
		sub1.put("Tamil", 70);
		//System.out.println(sub1);
		//System.out.println(sub1.get("Tamil"));

		HashMap<String, Object> sub2 = new LinkedHashMap<String, Object>();
		sub2.put("English", 50);
//		System.out.println(sub2);
//		System.out.println(sub2.get("English"));

		HashMap<String, Object> sub3 = new LinkedHashMap<String, Object>();
		sub3.put("SSc", 90);
//		System.out.println(sub3);
//		System.out.println(sub3.get("SSc"));

		ArrayList<HashMap<String, Object>> marks = new ArrayList<HashMap<String, Object>>();
		marks.add(sub1);
		marks.add(sub2);
		marks.add(sub3);
//		System.out.println(marks);
//		System.out.println(marks.get(0));
//		System.out.println(marks.get(0).get("Tamil"));
//		System.out.println(marks.get(1));
//		System.out.println(marks.get(1).get("English"));
//		System.out.println(marks.get(2));
//		System.out.println(marks.get(2).get("SSc"));

		HashMap<String, Object> Student = new LinkedHashMap<String, Object>();
		Student.put("Name", "Logesh");
		Student.put("Gender", "Male");
		Student.put("Marks", marks);

//		System.out.println(Student);
//		System.out.println(Student.get("Marks"));

		ArrayList<HashMap<String, Object>> Studentstudent = (ArrayList<HashMap<String, Object>>) Student.get("Marks");
		System.out.println("Tamil "+Studentstudent.get(0).get("Tamil"));
		System.out.println("English "+Studentstudent.get(1).get("English"));
		System.out.println("1 SSc "+Studentstudent.get(2).get("SSc"));
	}

	public void rootStu2() {
		
		HashMap<String, Object> sem1 = new LinkedHashMap<String, Object>();
		sem1.put("Tamil","77");
		sem1.put("English", "51");
		sem1.put("Chemistry", "97");

		HashMap<String, Object> sem2 = new LinkedHashMap<String, Object>();
		sem2.put("Tamil","77");
		sem2.put("English", "76");
		sem2.put("Chemistry", "71");

		HashMap<String, Object> sem3 = new LinkedHashMap<String, Object>();
		sem3.put("Tamil","57");
		sem3.put("English", "80");
		sem3.put("Chemistry", "77");

		
		HashMap<String,Object> semA=new HashMap<>();
		semA.put("sem1", sem1);
		HashMap<String,Object> semB=new HashMap<>();
		semB.put("sem2", sem2);
		HashMap<String,Object> semC=new HashMap<>();
		semC.put("sem3", sem3);
		ArrayList<HashMap<String,Object>> mark= new ArrayList<>();
		mark.add(semA);
		mark.add(semB);
		mark.add(semC);


		HashMap<String, Object> Student = new LinkedHashMap<String, Object>();
		Student.put("Name", "Logesh");
		Student.put("Gender", "Male");
		Student.put("Marks", mark);

		//System.out.println(Student);
		//System.out.println(Student.get("Marks"));

		List<HashMap<String,Object>> stud=(List<HashMap<String, Object>>) Student.get("Marks");
		HashMap<String,Object> markOfStu=(HashMap<String, Object>) stud.get(0).get("sem1"); 
		System.out.println("2 Chemistry: "+markOfStu.get("Chemistry")); 

	}

	public void rootStu3() {
		HashMap<String, Object> sem1 = new LinkedHashMap<String, Object>();
		sem1.put("Tamil","77");
		sem1.put("English", "51");
		sem1.put("Chemistry", "97");

		HashMap<String, Object> sem2 = new LinkedHashMap<String, Object>();
		sem2.put("Tamil","77");
		sem2.put("English", "76");
		sem2.put("Chemistry", "71");

		HashMap<String, Object> sem3 = new LinkedHashMap<String, Object>();
		sem3.put("Tamil","57");
		sem3.put("English", "80");
		sem3.put("Chemistry", "77");

		
		HashMap<String,Object> Marks=new HashMap<>();
		Marks.put("sem1", sem1);
		Marks.put("sem2", sem2);
		Marks.put("sem3", sem3);


		HashMap<String, Object> Student = new LinkedHashMap<String, Object>();
		Student.put("Name", "Logesh");
		Student.put("Gender", "Male");
		Student.put("Marks", Marks);

//		System.out.println(Student);
//		System.out.println(Student.get("Marks"));


		HashMap<String,Object> stud=(HashMap<String, Object>) Student.get("Marks");
		HashMap<String,Object> markOfStu=(HashMap<String, Object>) stud.get("sem2"); 
		System.out.println("3 English: "+markOfStu.get("English")); 

		
	}

	public void rootStu4() {
		HashMap<String, Object> subject1 = new LinkedHashMap<String, Object>();
		subject1.put("subject", "tamil");
		subject1.put("mark", 50);
		subject1.put("status", "Pass");
		
		HashMap<String, Object> subject2 = new LinkedHashMap<String, Object>();
		subject2.put("subject", "English");
		subject2.put("mark", 30);
		subject2.put("status", "Fail");

		HashMap<String, Object> subject3 = new LinkedHashMap<String, Object>();
		subject3.put("subject", "Maths");
		subject3.put("mark", 50);
		subject3.put("status", "Pass");
		
		ArrayList<HashMap<String,Object>> Sem1= new ArrayList<>();
		Sem1.add(subject1);
		Sem1.add(subject2);
		Sem1.add(subject3);
		
		
		HashMap<String, Object> subject4 = new LinkedHashMap<String, Object>();
		subject4.put("subject", "tamil");
		subject4.put("mark", 50);
		subject4.put("status", "Pass");
		
		HashMap<String, Object> subject5 = new LinkedHashMap<String, Object>();
		subject5.put("subject", "English");
		subject5.put("mark", 50);
		subject5.put("status", "Pass");

		HashMap<String, Object> subject6 = new LinkedHashMap<String, Object>();
		subject6.put("subject", "Maths");
		subject6.put("mark", 50);
		subject6.put("status", "Pass");
		
		ArrayList<HashMap<String,Object>> Sem2= new ArrayList<>();
		Sem2.add(subject4);
		Sem2.add(subject5);
		Sem2.add(subject6);
		
		HashMap<String, Object> subject7 = new LinkedHashMap<String, Object>();
		subject7.put("subject", "tamil");
		subject7.put("mark", 30);
		subject7.put("status", "Fail");
		
		HashMap<String, Object> subject8 = new LinkedHashMap<String, Object>();
		subject8.put("subject", "English");
		subject8.put("mark", 30);
		subject8.put("status", "Fail");

		HashMap<String, Object> subject9 = new LinkedHashMap<String, Object>();
		subject9.put("subject", "Maths");
		subject9.put("mark", 30);
		subject9.put("status", "Fail");
		
		ArrayList<HashMap<String,Object>> Sem3= new ArrayList<>();
		Sem3.add(subject7);
		Sem3.add(subject8);
		Sem3.add(subject6);
		HashMap<String,Object> Marks=new HashMap<>();
		Marks.put("sem1", Sem1);
		Marks.put("sem2", Sem2);
		Marks.put("sem3", Sem3);
		
		HashMap<String, Object> Student = new LinkedHashMap<String, Object>();
		Student.put("Name", "Logesh");
		Student.put("Gender", "Male");
		Student.put("Marks", Marks);
		
		HashMap<String,Object> stud=(HashMap<String, Object>) Student.get("Marks");
		ArrayList<HashMap<String,Object>> stumark =(ArrayList<HashMap<String, Object>>) stud.get("sem1");
		System.out.print("4 "+stumark.get(0).get("subject"));
		System.out.print(" "+stumark.get(0).get("mark"));
		System.out.print(" "+stumark.get(0).get("status"));
	}

	public static void main(String[] args) {
		
		
		Demo de = new Demo();
		de.rootStu1();
		de.rootStu2();
		de.rootStu3();
		de.rootStu4();
		
		
		
		
		
//		HashMap<String, Object> st1semester1Marks = new LinkedHashMap<>();
//		st1semester1Marks.put("English", 65);
//		st1semester1Marks.put("Maths", 85);
//		st1semester1Marks.put("Tamil", 55);
//		st1semester1Marks.put("Python", 54);
//		System.out.println("HashMap1  "+st1semester1Marks.get("English"));
//		
//		HashMap<String, Object> st1semester2Marks = new LinkedHashMap<>();
//		st1semester2Marks.put("English", 60);
//		st1semester2Marks.put("Maths", 85);
//		st1semester2Marks.put("Computer", 54);
//		st1semester2Marks.put("Tamil", 55);
//		System.out.println("HashMap2  "+st1semester2Marks.get("English"));
//
//		
//		ArrayList<HashMap<String, Object>> student1Marks = new ArrayList<>();
//		student1Marks.add(st1semester1Marks);
//		student1Marks.add(st1semester2Marks);
//		
//		System.out.println("ArrayList1  "+student1Marks.get(0).get("English"));
//		System.out.println("ArrayList 2 "+student1Marks.get(1).get("English"));
//		
//		HashMap<String, Object> student1 = new LinkedHashMap<>();
//		student1.put("Name", "Logesh");
//		student1.put("Gender", "Male");
//		student1.put("Marks", student1Marks);
//		
//		
//		ArrayList<HashMap<String, Object>> studentMarks = (ArrayList<HashMap<String, Object>>) student1.get("Marks");
//		
//		System.out.println("HashMap3  "+studentMarks.get(0).get("English"));


	}
}
