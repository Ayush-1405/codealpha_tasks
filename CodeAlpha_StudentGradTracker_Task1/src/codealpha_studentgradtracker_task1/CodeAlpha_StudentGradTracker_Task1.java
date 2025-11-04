
package codealpha_studentgradtracker_task1;

import java.util.ArrayList;
import java.util.Scanner;

public class CodeAlpha_StudentGradTracker_Task1 {

    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        while (true){
            System.out.println("\n===== Student Grade Tracker =====");
            System.out.println("1. Add Student");
            System.out.println("2. Enter Grades");
            System.out.println("3. View Report");
            System.out.println("4. Exit");
            System.out.println("Chose an option");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> enterGrades();
                case 3 -> viewReport();
                case 4 -> {
                    System.out.println("Exiting....");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice...Try Again.");
            }
        }
    }
    
    //Adding Student 
    private static void addStudent() {
        System.out.println("Enter Student Name: ");
        String name = sc.next();
        students.add(new Student(name));
        System.out.println("Student Add Successfull...");
    }
    
    //enter Grade
    private static void enterGrades() {
        if(students.isEmpty()){
            System.out.println("No Student Found .. Add Student First");
            return ;
        }
        
        System.out.println("Enter Student Name to add Grades : ");
        String name = sc.next();
        
        Student target = findStudentsByName(name);
        if(target  == null){
            System.out.println("Student Not Found ..! ");
            return;
        }
        while(true){
            System.out.println("Enter Grade (or -1 to stop): ");
            int grade = sc.nextInt();
            if(grade == -1) break;
            target.addGrade(grade);
        }
        sc.nextLine(); //consume newline
        System.out.println("Grades added for " + name);
    }
    
    private static void viewReport() {
        if (students.isEmpty()) {
            System.out.println("No Data to Display ..!");
        }
        
        double totalAverage = 0;
        double highestOverall = Double.MIN_VALUE;
        double lowestOverall = Double.MAX_VALUE;
        String topStudent = null,lowStudent = null;
        
        System.out.println("\n----- Summary Report -----");
        for (Student s : students) {
            System.out.println(s);
            double avg = s.getAverage();
            
            if (avg > highestOverall){
                highestOverall = avg;
                topStudent = s.getName();
            }
            if (avg < lowestOverall) {
                lowestOverall = avg;
                lowStudent = s.getName();
            }
            totalAverage += avg;
        }
        if (topStudent == null) topStudent = "N/A";
        if (lowStudent == null) lowStudent = "N/A";
        System.out.println("----------------------------------");
        System.out.println("Class Average: %.2f%n"+ totalAverage / students.size());
        System.out.println("Top Student: %s (%.2f)%n"+ topStudent+ highestOverall);
        System.out.println("Lowest Student: %s (%.2f)%n"+ lowStudent+ lowestOverall);
        
    }

    private static Student findStudentsByName(String name) {
       for (Student s : students)
           if(s.getName().equalsIgnoreCase(name))
               return s;
       return null;
    }
}
