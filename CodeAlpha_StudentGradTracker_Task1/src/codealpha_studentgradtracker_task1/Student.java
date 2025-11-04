/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codealpha_studentgradtracker_task1;

import java.awt.Image;
import java.util.ArrayList;

public class Student {
    
    private String name;
    private ArrayList<Integer> grades;
    
    public Student(String name){
        this.name = name;
        this.grades = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void addGrade(int grade){
        grades.add(grade);
    }
    public double getAverage() {
        if (grades.isEmpty()) return 0.0;
        int total = 0;
        for (int g : grades) total += g;
        return (double) total / grades.size();
    }
    
    public int getHighestGrade(){
        int max = Integer.MIN_VALUE;
        for (int g : grades) if (g > max) max = g;
        return max;
    }
    
    public int getLowestGrade(){
        int min = Integer.MAX_VALUE;
        for (int g : grades) if (g < min) min = g;
        return min;
    }
    
    @Override
    public String toString(){
       
        return name +  " | Avg : " + String.format("%.2f", getAverage()) +
                " | High : " + getHighestGrade() +
                " | Low : " + getLowestGrade();
    }
}