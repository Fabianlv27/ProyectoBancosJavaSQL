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
	public static HashMap<Double, Integer> Moneda_Valores = new HashMap<>();
	public static String url = "jdbc:mysql://localhost:3306/";
	public static String usuario = "root";
	public static String contraseña = "Brandis1206_";

	public static Scanner sc = new Scanner(System.in);
	public static String Schema_Actual = "BBVA";
	private static final double[] monedas = { 5, 2, 1 };

	public static ArrayList<String> Bancos = new ArrayList<String>();

	public static ArrayList<String> GetBancos() {
		ArrayList<String> res = new ArrayList<>();
		String Sql_Get_Schemas = "Select schema_name from information_schema.schemata where schema_name like 'b_%'";

		// Creamos el schema primero en un try diferente
		try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
				PreparedStatement Sschema = conn.prepareStatement(Sql_Get_Schemas);

		) {
			conn.setAutoCommit(false);
			ResultSet resultados = Sschema.executeQuery();

			while (resultados.next()) {
				res.add(resultados.getString("schema_name"));
			}
			conn.commit();


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return res;
	}

	public static void Crear_DB(String Nombre, Boolean ResetCantidad) {

		String Sql_Crear_Schema = "create Schema if not exists b_" + Nombre;

		// Creamos el schema primero en un try diferente
		try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
				PreparedStatement Sschema = conn.prepareStatement(Sql_Crear_Schema);

		) {
			conn.setAutoCommit(false);
			Sschema.execute();
			conn.commit();

			Schema_Actual = Nombre;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// Si se crea con exito , creamos la tabla
		Añadir_Monedas(Nombre, ResetCantidad);
	}

	public static void Añadir_Monedas(String Nombre, Boolean Reset) {
		

		String SQL_Cajero = "create table if not exists Cajero(\r\n" + "Moneda double primary key ,\r\n"
				+ "Cantidad int Not null\r\n" + ");";
		String SQL_InsertMonedas = "insert into Cajero(Moneda,Cantidad) Values(?,?)";

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/b_" + Nombre, usuario,
				contraseña);

				PreparedStatement Scajero = conn.prepareStatement(SQL_Cajero);
				PreparedStatement Smonedas = conn.prepareStatement(SQL_InsertMonedas);

		) {
			conn.setAutoCommit(false);
			Scajero.execute();

			if (Reset) {

				for (int i = 2; i >= -2; i--) {
					for (Double m : monedas) {
						Moneda_Valores.put(m, 100);
						Smonedas.setDouble(1, (m * (Math.pow(10, i))));
						Smonedas.setInt(2, 100);
						Smonedas.addBatch();
						Smonedas.execute();
					}
				}

			}

			conn.commit();

			Schema_Actual = Nombre;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static ResultSet Obtener_Monedas() {
		double moneda;
		int cantidad;
		String SQL_Select = "Select * from Cajero";

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/b_" + Schema_Actual, usuario,
				contraseña);

				PreparedStatement Sobtener_monedas = conn.prepareStatement(SQL_Select);

		) {
			conn.setAutoCommit(false);

			ResultSet resultados = Sobtener_monedas.executeQuery();
			LLenarHash(resultados);
			conn.commit();
			// LLena el hash map con los valores actuales
			return resultados;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static void LLenarHash(ResultSet resultados) throws SQLException {

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
			// %5d -> val, ancho 5

			System.out.println(String.format("%-2s %6.2f   | %5d |", (val > 0 ? "|🟢 " : "|⚠️ "), key, val));
		}
		System.out.println("-----------------------");
	}
	
	public static void Listar_Bancos() {
		ArrayList<String>bancos=GetBancos();
		System.out.println("********************BANCOS****************");
		for (int i = 0; i < bancos.size(); i++) {
			System.out.println("🌐 "+(i+1)+") "+bancos.get(i).substring(2));
		}
		System.out.println("Selecciona un Banco");
		int op=sc.nextInt();
		Schema_Actual=bancos.get((op-1)).substring(2);
		Obtener_Monedas();
		ListarCaja();
	}

	public static void Mostrar_Menu() throws SQLException {
		System.out.println("...............🏦 Banco " + Schema_Actual + "..................");

		final String[] opciones = { "✅ 1.... Listar", "🛒 2.....Realizar compra", "💰 3.... Introducir dinero",
				"💸 4.... Transferir fondos", "🏦 5.... Cambiar BBDD", "🔙 6.... Salir" };

		int opt = menu.menu(sc, opciones);

		switch (opt) {
		case 1:
			Listar_Bancos();
			break;
		case 2:
			// listar();
			break;
		case 3:
			// listar();
			break;
		case 4:
			// listar();
			break;
		case 5:
			// listar();
			break;
		case 6:
			// listar();
			break;

		default:
			break;
		}
	}

	public static void main(String[] args) throws SQLException {
		Crear_DB("BBVA", true);
		Mostrar_Menu();
	}
}
