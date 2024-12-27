package com.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    Doctor(Connection connection)
    {
        this.connection=connection;
    }
    public void viewDoctors()
    {
        try
        {
            PreparedStatement preparedStatement= connection.prepareStatement("SELECT * FROM doctors");
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Doctors:");
            System.out.println("+----+--------------------+---------+-----------+");
            System.out.println("| Doctor Id |  Name              |  Specialization     |");
            System.out.println("+----+------------------------------------------+");
            while(resultSet.next())
            {
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String specialization=resultSet.getString("specialization");
                System.out.printf("|%-11s|%-20s|%-20s|\n",id,name,specialization);

            }
            System.out.println("+-----------+------------------------------------------+");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    public boolean getDoctorById(int id)
    {
        try
        {
            PreparedStatement preparedStatement= connection.prepareStatement("SELECT * FROM doctors WHERE id=?");
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
