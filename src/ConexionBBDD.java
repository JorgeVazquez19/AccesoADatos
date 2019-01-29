import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class ConexionBBDD implements InterfazBBDD {

	private String bd = "abd_coches?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String login = "";
	private String pwd = "";
	private String url = "jdbc:mysql://localhost/" + bd;
	private Connection conexion;
	Marca marca = new Marca("", "", "");
	Coche coche = new Coche("", "", "", "", "", marca);
	HashMap<String, Marca> marcaMap = new HashMap<>();
	HashMap<String, Coche> cocheMap = new HashMap<>();

	public ConexionBBDD() {

		try {
			Properties p = new Properties();
			p.load(new FileInputStream("param.ini"));
			login = p.getProperty("Usuario");
			pwd = p.getProperty("Contrasena");
		} catch (Exception e) {
			System.out.println("error al leer el archivo .ini:" + e.getMessage());
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(url, login, pwd);
			// Quitamos esta instrucción: conexion.close();
			System.out.println(" - Conexión con MySQL establecida -");
		} catch (Exception e) {
			System.out.println(" – Error de Conexión con MySQL -");
			e.printStackTrace();

		}
	}

	@Override
	public void Leer() throws SQLException {

		Scanner sc = new Scanner(System.in);

		System.out.println("1. Leer Coches");
		System.out.println("2. Leer Fabricantes");
		String eleccion = sc.nextLine();

		switch (eleccion) {
		case "1":
			String Query = "SELECT * FROM coches";
			PreparedStatement preparedStmt = conexion.prepareStatement(Query);
			java.sql.ResultSet resultSet;
			resultSet = preparedStmt.executeQuery(Query);

			while (resultSet.next()) {
				System.out.println("ID: " + " " + resultSet.getString("ID") + "\n" + "Nombre: " + " "
						+ resultSet.getString("Nombre") + "\n" + "Descripcion" + " "
						+ resultSet.getString("Descripcion") + "\n" + "Caracteristica1: " + " "
						+ resultSet.getString("Caracteristica1") + "\n" + "Caracteristica2: " + " "
						+ resultSet.getString("Caracteristica2") + "\n" + "\n" + "Fabricante: " + " "
						+ resultSet.getString("ID_Fabricante") + "\n");
			}
			break;
		case "2":

			Query = "SELECT * FROM marca";
			preparedStmt = conexion.prepareStatement(Query);

			resultSet = preparedStmt.executeQuery(Query);

			while (resultSet.next()) {
				System.out.println("ID Fabricante: " + " " + resultSet.getString("ID_Fabricante") + "\n" + "Nombre: "
						+ " " + resultSet.getString("Nombre") + "\n" + "Sede: " + " " + resultSet.getString("Sede")
						+ "\n");
			}
			break;

		}
		
	}

	@Override
	public void Escribir() throws SQLException {
		Scanner sc = new Scanner(System.in);

		System.out.println("1. A�adir Coche");
		System.out.println("2. A�adir Fabricante");
		String eleccion = sc.nextLine();

		switch (eleccion) {

		case "1":
			System.out.println("Estos son los IDs de las respectivas marcas");
			String Query = "SELECT * FROM marca";
			PreparedStatement preparedStmt1 = conexion.prepareStatement(Query);
			java.sql.ResultSet resultSet;
			resultSet = preparedStmt1.executeQuery(Query);

			while (resultSet.next()) {
				System.out.println(resultSet.getString("ID_Fabricante") + " : " + resultSet.getString("Nombre") + "\n");
			}

			System.out.print("1:ID" + "\n");
			String iD = sc.nextLine();
			coche.setId(iD);
			System.out.print("2:Nombre" + "\n");
			String nombre = sc.nextLine();
			coche.setNombre(nombre);
			System.out.print("3:Desripcion" + "\n");
			String descripcion = sc.nextLine();
			coche.setDescripcion(descripcion);
			System.out.print("4:Caracteristica 1" + "\n");
			String caracteristicas1 = sc.nextLine();
			coche.setCaracteristica1(caracteristicas1);
			System.out.print("5:Caracteristica 2" + "\n");
			String caracteristicas2 = sc.nextLine();
			coche.setCaracteristica2(caracteristicas2);
			System.out.print("6:ID Frabicante" + "\n");
			String IDFabricante = sc.nextLine();

			Marca marcacreada = marcaMap.get(IDFabricante);
			coche.setMarca(marcacreada);

			cocheMap.put(coche.getId(), coche);

			String query = " insert into coches (Id, Nombre, Descripcion, Caracteristica1, Caracteristica2, ID_Fabricante)"
					+ " values (?, ?, ?, ?, ?, ?)";

			int r = 0;

			PreparedStatement preparedStmt = conexion.prepareStatement(query);
			preparedStmt.setString(1, iD);
			preparedStmt.setString(2, nombre);
			preparedStmt.setString(3, descripcion);
			preparedStmt.setString(4, caracteristicas1);
			preparedStmt.setString(5, caracteristicas2);
			preparedStmt.setString(6, IDFabricante);

			try {
				r = preparedStmt.executeUpdate();
				break;
			} catch (Exception e) {

				System.err.println("Introduzca un ID del fabricante existente xfa plis");

			}

		case "2":

			System.out.print("1:ID" + "\n");
			String ID = sc.nextLine();
			marca.setId_fabricante(ID);
			System.out.print("2:Nombre" + "\n");
			String Nombre = sc.nextLine();
			marca.setNombre(Nombre);
			System.out.print("3:Sede" + "\n");
			String Sede = sc.nextLine();
			marca.setSede(Sede);

			marcaMap.put(marca.getId_fabricante(), marca);

			String query1 = " insert into marca (ID_Fabricante, Nombre, Sede)" + " values (?, ?, ?)";

			r = 0;

			PreparedStatement preparedStmt2 = conexion.prepareStatement(query1);
			preparedStmt2.setString(1, ID);
			preparedStmt2.setString(2, Nombre);
			preparedStmt2.setString(3, Sede);

			r = preparedStmt2.executeUpdate();

			break;

		default:
			break;
		}

	}

	@Override
	public void TXTABBDD() throws FileNotFoundException, SQLException {

		Scanner sc1 = new Scanner(System.in);
		int eleccion;

		System.out.print("pulse 1 si desea pasar los coches de fichero a la base de datos" + "\n");
		System.out.print("Pulse 2 si desea pasar las marcas de fichero a la base de datos" + "\n");
		eleccion = sc1.nextInt();

		switch (eleccion) {
		case 1:
			BorrarBBDDcoches();
			ArrayList<String> aux = new ArrayList();

			String cadena = "coches.txt";

			File archivo = new File(cadena);
			Scanner sc = new Scanner(archivo);

			while (sc.hasNextLine()) {
				aux.add(sc.nextLine());
			}

			int aux2 = 1;

			String query = " insert into coches(Id, Nombre, Descripcion, Caracteristica1, Caracteristica2, ID_Fabricante)"
					+ " values (?, ?, ?, ?, ?, ?)";

			int r = 0;

			PreparedStatement preparedStmt = conexion.prepareStatement(query);

			for (int i = 0; i < aux.size(); i++) {

				if (aux2 == 1) {
					String iD = aux.get(i);
					preparedStmt.setString(1, iD);
					aux2++;
					coche.setId(iD);
				} else if (aux2 == 2) {

					String nombre = aux.get(i);
					preparedStmt.setString(2, nombre);
					aux2++;
					coche.setNombre(nombre);
				} else if (aux2 == 3) {

					String descripcion = aux.get(i);
					preparedStmt.setString(3, descripcion);
					aux2++;
					coche.setDescripcion(descripcion);
				} else if (aux2 == 4) {

					String caracteristicas1 = aux.get(i);
					preparedStmt.setString(4, caracteristicas1);
					aux2++;
					coche.setCaracteristica1(caracteristicas1);
				} else if (aux2 == 5) {

					String caracteristicas2 = aux.get(i);
					preparedStmt.setString(5, caracteristicas2);
					aux2++;
					coche.setCaracteristica2(caracteristicas2);
				} else if (aux2 == 6) {

					String IDFabricante = aux.get(i);
					preparedStmt.setString(6, IDFabricante);
					aux2 = 1;
					Marca marcacreada = marcaMap.get(IDFabricante);

					coche.setMarca(marcacreada);

					cocheMap.put(coche.getId(), coche);

					r += preparedStmt.executeUpdate();

				}
			}

		case 2:

			BorrarBBDDmarcas();
			ArrayList<String> arrayMarcas = new ArrayList();

			String ficheroMarcas = "marcas.txt";

			File archivoMarcas = new File(ficheroMarcas);
			Scanner escanerMarcas = new Scanner(archivoMarcas);

			while (escanerMarcas.hasNextLine()) {
				arrayMarcas.add(escanerMarcas.nextLine());
			}

			int contadorMarcas = 1;

			String queryMarcas = " insert into marca(ID_Fabricante, Nombre, Sede)" + " values (?, ?, ?)";

			int r1 = 0;

			PreparedStatement preparedStmt1 = conexion.prepareStatement(queryMarcas);

			for (int i = 0; i < arrayMarcas.size(); i++) {

				if (contadorMarcas == 1) {
					String iD = arrayMarcas.get(i);
					preparedStmt1.setString(1, iD);
					contadorMarcas++;
					marca.setId_fabricante(iD);
				} else if (contadorMarcas == 2) {

					String nombre = arrayMarcas.get(i);
					preparedStmt1.setString(2, nombre);
					contadorMarcas++;
					marca.setNombre(nombre);
				} else if (contadorMarcas == 3) {

					String sede = arrayMarcas.get(i);
					preparedStmt1.setString(3, sede);
					contadorMarcas = 1;
					marca.setSede(sede);
					marcaMap.put(marca.getId_fabricante(), marca);
					r1 += preparedStmt1.executeUpdate();
				}
			}

		}
	}

	public void BorrarBBDDcoches() throws SQLException {

		String query = "Delete From coches";

		int r = 0;

		PreparedStatement preparedStmt = conexion.prepareStatement(query);

		r += preparedStmt.executeUpdate();

	}

	public void BorrarBBDDmarcas() throws SQLException {

		String query = "Delete From marca";

		int r = 0;

		PreparedStatement preparedStmt = conexion.prepareStatement(query);

		r += preparedStmt.executeUpdate();

	}

	@Override
	public void BBDDTXT() throws SQLException, IOException {

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			File file = new File("fichero.txt");

			if (!file.exists()) {
				file.createNewFile();
			} else {
				// file.delete();
			}

			fw = new FileWriter(file.getAbsoluteFile(), false);
			bw = new BufferedWriter(fw);
			String sql = "Select * from coches";
			PreparedStatement pstm;
			pstm = conexion.prepareStatement(sql);
			ResultSet rset = pstm.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			int numColumns = rsmd.getColumnCount();
			bw.write("\n");
			while (rset.next()) {
				for (int i = 1; i <= numColumns; i++) {
					bw.write(rset.getString(i) + "\n");
				}
				bw.write("\n");
			}
			bw.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void Actualizar() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Pulse 1 si desea pasar los datos de los coches" + "\n");
		System.out.print("Puse 2 si desea pasar los datos de las marcas" + "\n");
		int eleccion = sc.nextInt();

		switch (eleccion) {
		case 1:
			String query = "update coches set ID=?,Nombre=?, Descripcion=?, Caracteristica1=?,Caracteristica2=?, ID_Fabricante=? where ID = ?";
			String cambiar1 = sc.nextLine();
			System.out.print("Introduzca el ID del Coche que quiere actualizar: " + "\n");
			String cambiar = sc.nextLine();
			coche.setId(cambiar);
			System.out.print("2:Nombre" + "\n");
			String nombre = sc.nextLine();
			coche.setNombre(nombre);
			System.out.print("3:Desripcion" + "\n");
			String descripcion = sc.nextLine();
			coche.setDescripcion(descripcion);
			System.out.print("4:Caracteristica 1" + "\n");
			String caracteristicas1 = sc.nextLine();
			coche.setCaracteristica1(caracteristicas1);
			System.out.print("5:Caracteristica 2" + "\n");
			String caracteristicas2 = sc.nextLine();
			coche.setCaracteristica2(caracteristicas2);
			System.out.print("6:ID Frabicante" + "\n");
			String IDFabricante = sc.nextLine();

			Marca marcacreada = marcaMap.get(IDFabricante);
			coche.setMarca(marcacreada);

			cocheMap.replace(coche.getId(), coche);

			int r = 0;

			PreparedStatement preparedStmt = conexion.prepareStatement(query);
			preparedStmt.setString(1, cambiar);
			preparedStmt.setString(2, nombre);
			preparedStmt.setString(3, descripcion);
			preparedStmt.setString(4, caracteristicas1);
			preparedStmt.setString(5, caracteristicas2);
			preparedStmt.setString(6, IDFabricante);
			preparedStmt.setString(7, cambiar);

			try {
				r = preparedStmt.executeUpdate();

			} catch (Exception e) {

				System.err.println("Introduzca un ID del fabricante existente xfa plis");
			}
			break;

		case 2:
			String mQuery = "update marca set ID_Fabricante=?,Nombre=?, Sede=? where ID_Fabricante = ?";
			String mId1 = sc.nextLine();
			System.out.print("Introduzca el ID del Fabricante que quiere actualizar: ");
			String mId = sc.nextLine();
			coche.setId(mId);
			System.out.print("2:Nombre" + "\n");
			String mnombre = sc.nextLine();
			coche.setNombre(mnombre);
			System.out.print("3:Sede" + "\n");
			String mSede = sc.nextLine();
			coche.setDescripcion(mSede);

			Marca mMarcacreada = marcaMap.get(mId);
			coche.setMarca(mMarcacreada);

			cocheMap.replace(coche.getId(), coche);

			int mr = 0;

			PreparedStatement mPreparedStmt = conexion.prepareStatement(mQuery);
			mPreparedStmt.setString(1, mId);
			mPreparedStmt.setString(2, mnombre);
			mPreparedStmt.setString(3, mSede);
			mPreparedStmt.setString(4, mId);

			try {
				mr = mPreparedStmt.executeUpdate();

			} catch (Exception e) {

				System.err.println("Introduzca un ID del fabricante existente xfa plis");
			}
			break;
		}

	}

	@Override
	public void Borrar() throws SQLException {

		Scanner sc = new Scanner(System.in);
		System.out.print("Pulse 1 si desea borrar un coche" + "\n");
		System.out.print("Puse 2 si desea borrar una marca" + "\n");
		int eleccion = sc.nextInt();

		switch (eleccion) {
		case 1:
			System.out.println("Estos son los IDs de los respectivos coches");
			String Query = "SELECT * FROM coches";
			PreparedStatement preparedStmt1 = conexion.prepareStatement(Query);
			java.sql.ResultSet resultSet;
			resultSet = preparedStmt1.executeQuery(Query);

			while (resultSet.next()) {
				System.out.println(resultSet.getString("ID") + " : " + resultSet.getString("Nombre") + "\n");
			}
			String query = "delete from coches where ID = ?";
			String eliminar1 = sc.nextLine();
			System.out.print("Introduzca el ID del Coche que quiere borrar: " + "\n");
			String eliminar = sc.nextLine();
			coche.setId(eliminar);

			cocheMap.remove(coche.getId(), coche);

			int r = 0;

			PreparedStatement preparedStmt = conexion.prepareStatement(query);
			preparedStmt.setString(1, eliminar);

			try {
				r = preparedStmt.executeUpdate();

			} catch (Exception e) {

				System.err.println("Introduzca un ID del coche existente por favor");
			}
			break;

		case 2:
			System.out.println("Estos son los IDs de las respectivas marcas");
			String Query1 = "SELECT * FROM marca";
			PreparedStatement preparedStmt2 = conexion.prepareStatement(Query1);
			java.sql.ResultSet resultSet1;
			resultSet1 = preparedStmt2.executeQuery(Query1);

			while (resultSet1.next()) {
				System.out.println(resultSet1.getString("ID_Fabricante") + " : " + resultSet1.getString("Nombre") + "\n");
			}
			String mQuery = "delete from marca where ID_Fabricante = ?";
			String mId1 = sc.nextLine();
			System.out.print("Introduzca el ID del Fabricante que quiere borrar: ");
			String mId = sc.nextLine();
			marca.setId_fabricante(mId);

			marcaMap.remove(marca.getId_fabricante(), marca);

			int mr = 0;

			PreparedStatement mPreparedStmt = conexion.prepareStatement(mQuery);
			mPreparedStmt.setString(1, mId);

			try {
				mr = mPreparedStmt.executeUpdate();

			} catch (Exception e) {

				System.err.println("Actualmente hay coches los cuales tienen este identificador del fabricante asignado");
				System.err.println("Pruebe a introducir un ID del fabricante el cual no tenga coches asignados");
			}
			break;
		}

	
		
	}

	@Override
	public void PasarDatos() {
		// TODO Auto-generated method stub
		
	}


}