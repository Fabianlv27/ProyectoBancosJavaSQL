package Monedas;

import java.util.HashMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import com.rayo.menu;
public class banco {
		public static HashMap<Double,Integer> Moneda_Valores=new HashMap<>();
		public static String url="jdbc:mysql://localhost:3306/";
		public static String usuario="root";
		public static String contraseña="root";
		
	
		public static Scanner sc=new Scanner(System.in);
	 public static String Schema_Actual="BBVA";
		private static final double[] monedas= {5,2,1};	
		
		
public static ArrayList<String> Bancos=new ArrayList<String>();

public static ArrayList<String> GetBancos(){
	  String Sql_Crear_Schema="create Schema if not exists ";

	  //Creamos el schema primero en un try diferente
	  try (
			  Connection conn=DriverManager.getConnection(url,usuario,contraseña);
				PreparedStatement Sschema=conn.prepareStatement(Sql_Crear_Schema);

			  ){
		 conn.setAutoCommit(false);
		 Sschema.execute();
		conn.commit();
		
		//Schema_Actual=Nombre;
		
	}catch(SQLException e) {
		System.out.println(e.getMessage());
	} 
	catch (Exception e) {
		System.out.println(e.getMessage());
	}
	 //Si se crea con exito , creamos la tabla
}
		
		
	public static void Crear_DB(String Nombre,Boolean ResetCantidad) {
		
	  String Sql_Crear_Schema="create Schema if not exists "+Nombre;

	  //Creamos el schema primero en un try diferente
	  try (
			  Connection conn=DriverManager.getConnection(url,usuario,contraseña);
				PreparedStatement Sschema=conn.prepareStatement(Sql_Crear_Schema);

			  ){
		 conn.setAutoCommit(false);
		 Sschema.execute();
		conn.commit();
		
		Schema_Actual=Nombre;
		
	}catch(SQLException e) {
		System.out.println(e.getMessage());
	} 
	catch (Exception e) {
		System.out.println(e.getMessage());
	}
	 //Si se crea con exito , creamos la tabla
	  Añadir_Monedas(Nombre,ResetCantidad);
	}
	
public static void Añadir_Monedas(String Nombre,Boolean Reset) {
	boolean ResetCantidad=false;
	
	  String SQL_Cajero="create table if not exists Cajero(\r\n"
			+ "Moneda double primary key ,\r\n"
			+ "Cantidad int Not null\r\n"
			+ ");";
	  String SQL_InsertMonedas="insert into Cajero(Moneda,Cantidad) Values(?,?)";
	  
	  try (
			  Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Nombre,"root","root");

				PreparedStatement Scajero=conn.prepareStatement(SQL_Cajero);
			  PreparedStatement Smonedas=conn.prepareStatement(SQL_InsertMonedas);
			  
			  ){
		 conn.setAutoCommit(false);
		 Scajero.execute();
		 
		 if (ResetCantidad) {

			for (int i = 2; i >=-2; i--) {
				for (Double m : monedas) {
					Moneda_Valores.put(m, 100);
					Smonedas.setDouble(1, (m*(Math.pow(10, i))));
					Smonedas.setInt(2, 100);
					Smonedas.addBatch();
					Smonedas.execute();
				}
			}
			
		}
		
		
		conn.commit();
		
		Schema_Actual=Nombre;
		
	}catch(SQLException e) {
		System.out.println(e.getMessage());
	} 
	catch (Exception e) {
		System.out.println(e.getMessage());
	}	


}

public static ResultSet Obtener_Monedas() {
	double moneda;
	int cantidad;
	String SQL_Select="Select * from Cajero";
	  String SQL_InsertMonedas="insert into Cajero(Moneda,Cantidad) Values(?,?)";
	  
	  try (
			  Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Schema_Actual,"root","root");

				PreparedStatement Sobtener_monedas=conn.prepareStatement(SQL_Select);
			
			  
			  ){
		 conn.setAutoCommit(false);
		 
		 ResultSet resultados=Sobtener_monedas.executeQuery();
		 LLenarHash( resultados);
		 //LLena el hash map con los valores actuales
		 return resultados;

		
	}catch(SQLException e) {
		System.out.println(e.getMessage());
		return null;
	} 
	catch (Exception e) {
		System.out.println(e.getMessage());
		return null;
	}	
}

public static void LLenarHash( ResultSet resultados) throws SQLException {
	
	while (resultados.next()) {
		Moneda_Valores.put(resultados.getDouble(1), resultados.getInt(2));
	}

}

public static void ListarCaja() {
	System.out.println("-------CAJA 🗃️---------");
	for (Map.Entry<Double, Integer> m : Moneda_Valores.entrySet()) {
	    double key = m.getKey();
	    int val = m.getValue();
	    
	    // %-6s -> símbolo (🟢/⚠️) alineado a la izquierda
	    // %6.2f -> key con 2 decimales, ancho 6
	    // %5d   -> val, ancho 5
	    
	    System.out.println(
	        String.format("%-2s %6.2f   | %5d |" ,
	                      (val > 0 ? "|🟢 " : "|⚠️ "),
	                      key,
	                      val)
	    );
	}
	System.out.println("-----------------------");
}



	
public static void Mostrar_Menu() throws SQLException {
	System.out.println("...............🏦 Banco "+Schema_Actual+"..................");
	
	final String[] opciones = {
		    "✅ 1.... Listar",
		    "🛒 2.....Realizar compra",
		    "💰 3.... Introducir dinero",
		    "💸 4.... Transferir fondos",
		    "🏦 5.... Cambiar BBDD",
		    "🔙 6.... Salir"
		};
                                                                                                            
	int opt=menu.menu(sc, opciones);
	
	switch (opt) {
	case 1:
		Obtener_Monedas();
		ListarCaja();
		break;
	case 2:
		//listar();
		break;
	case 3:
		//listar();
		break;
	case 4:
		//listar();
		break;
	case 5:
		//listar();
		break;
	case 6:
		//listar();
		break;

	default:
		break;
	}
}

	public static void main(String[] args) throws SQLException {
		Crear_DB("BBVA",false);
		Mostrar_Menu();
	}
}
