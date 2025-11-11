package com.rayo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrearSchema {
	private static final String SQL_CREATE="create Schema if not exists Cajero90";
	private static final String SQL_DELETE="Drop Schema if  exists Cajero90";
	
	public static void main(String[] args) {
		try(
				Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","root");
				PreparedStatement sentencia=conn.prepareStatement(SQL_DELETE);
				PreparedStatement sentencia1=conn.prepareStatement(SQL_CREATE);
				){
			sentencia.execute();
			sentencia1.execute();
			System.out.println("Database creada");
		} catch (SQLException e) {
		System.out.format("%s %s",e.getMessage(),e.getSQLState());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
//Crear schema banco, dentro una tabla Cajero:PK Moneda,Cantidad,rellenar tabla
//con 10 cantidad de cada una de las monedas en euros.
