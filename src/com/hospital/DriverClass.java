package com.hospital;

import java.sql.*;
import java.util.Scanner;

public class DriverClass {
    private static final String url="jdbc:mysql://localhost:3307/hospital";
    private static final String username="root";
    private static final String password="sasi@new1234";
    public static void main(String []args)
    {
        Scanner scanner=new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection);
            while(true)
            {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patients");
                System.out.println("3.View Doctors");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Enter your choice");
                int choice=scanner.nextInt();
                switch(choice)
                {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient,doctor,connection);
                        break;
                    case 5:
                        System.out.println("Thank you for using Hospital Management System");
                        return;
                    default:
                        System.out.println("Enter a valid choice");
                        break;
                }
            }


        }
        catch(Exception e)
        {
            System.out.println(e);
        }


    }
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection)
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter patient id:");
        int patientid=scanner.nextInt();
        System.out.println("Enter doctor id:");
        int doctorid=scanner.nextInt();
        System.out.println("Enter Appointment Date in format (YYYY-MM-DD):");
        String appointmentdate=scanner.next();
        if(patient.getPatientById(patientid) && doctor.getDoctorById(doctorid))
        {
            if(checkAvailability(doctorid,appointmentdate,connection))
            {
                try
                {
                    PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)");
                    preparedStatement.setInt(1,patientid);
                    preparedStatement.setInt(2,doctorid);
                    preparedStatement.setString(3,appointmentdate);
                    int affectedRows=preparedStatement.executeUpdate();
                    if(affectedRows>0)
                    {
                        System.out.println("Appointment Booked");
                    }
                    else
                    {
                        System.out.println("Appointment not Booked");
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e);
                }

            }
            else {
                System.out.println("Doctor is not available on this date");
            }
        }
        else {
            System.out.println("Either patient or Doctor doesn't exist");
        }

    }
    public static boolean checkAvailability(int doctorid,String appointmentdate,Connection connection)
    {
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?");
            preparedStatement.setInt(1,doctorid);
            preparedStatement.setString(2,appointmentdate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int count=resultSet.getInt(1);
                if(count==0)
                {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return false;
    }
}
