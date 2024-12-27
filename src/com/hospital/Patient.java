package com.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    Patient(Connection connection,Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatient()
    {
        System.out.println("Enter the patient name:");
        String name=scanner.next();
        System.out.println("Enter the patient age:");
        int age=scanner.nextInt();
        System.out.println("Enter the patient gender:");
        String gender=scanner.next();
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO patients(name,age,gender) VALUES(?,?,?)");
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows>0)
            {
                System.out.println("Patient added successfully");
            }
            else {
                System.out.println("Failed to add patient");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    public void viewPatients()
    {
        try
        {
            PreparedStatement preparedStatement= connection.prepareStatement("SELECT * FROM patients");
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients:");
            System.out.println("+----+--------------------+---------+-----------+");
            System.out.println("| Patient Id |  Name              |  Age    |  Gender   |");
            System.out.println("+----+--------------------+---------+-----------+");
            while(resultSet.next())
            {
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("|%-12s|%-20s|%-9s|%-11s|\n",id,name,age,gender);

            }
            System.out.println("+------------+--------------------+---------+-----------+");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    public boolean getPatientById(int id)
    {
        try
        {
            PreparedStatement preparedStatement= connection.prepareStatement("SELECT * FROM patients WHERE id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return false;

    }
}
