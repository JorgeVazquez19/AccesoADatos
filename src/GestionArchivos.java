import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionArchivos implements InterfazBBDD {

	@Override
	public void Leer() throws FileNotFoundException {

		Scanner sp = new Scanner(System.in);
		System.out.print("Escriba nombre del fichero" + "\n");
		System.out.print("Recuerde el fichero tendra que ser de tipo .txt" + "\n");
		String cadena = sp.nextLine();
		File archivo = new File(cadena + ".txt");
		Scanner sc = new Scanner(archivo);
		while (sc.hasNextLine()) {
			System.out.print(sc.nextLine() + "\n");

		}
	}

	@Override
	public void TXTABBDD() throws FileNotFoundException, SQLException {

	}

	@Override
	public void BBDDTXT() throws SQLException, IOException {

	}

	@Override
	public void Actualizar() throws SQLException {

	}

	@Override
	public void Borrar() throws SQLException {

	}

	@Override
	public void Escribir() {
		FileWriter fichero = null;
		Scanner sp = new Scanner(System.in);
		System.out.println("Introduzca 1 para escribir coches, 2 para marcas");
		String eleccion = sp.nextLine();
		switch (eleccion) {
		case "1":
			String id = "";
			String nombre = "";
			String descripcion = "";
			String caracteristicas1 = "";
			String caracteristicas2 = "";
			String IDFabricante = "";

			try {

				Scanner sc = new Scanner(System.in);
				System.out.print("Escriba el id");
				id = sc.nextLine();
				System.out.print("Escriba el nombre");
				nombre = sc.nextLine();
				System.out.print("Escriba el descripcion");
				descripcion = sc.nextLine();
				System.out.print("Escriba la primera caracteristicas");
				caracteristicas1 = sc.nextLine();
				System.out.print("Escriba la segunda caracteristicas");
				caracteristicas2 = sc.nextLine();
				System.out.print("Escriba el ID del Fabricante");
				IDFabricante = sc.nextLine();

				fichero = new FileWriter("coches.txt", true);

				fichero.write(id + "\n" + nombre + "\n" + descripcion + "\n" + caracteristicas1 + "\n"
						+ caracteristicas2 + "\n" + IDFabricante + "\n");

				fichero.close();

			} catch (Exception ex) {
				System.out.println("Mensaje de la excepción: " + ex.getMessage());
			}
			break;
		case "2":
			String id_fabricante = "";
			String Nombre = "";
			String Descripcion = "";

			try {

				Scanner sc = new Scanner(System.in);
				System.out.print("Escriba el id");
				id_fabricante = sc.nextLine();
				System.out.print("Escriba el nombre");
				Nombre = sc.nextLine();
				System.out.print("Escriba el descripcion");
				Descripcion = sc.nextLine();

				fichero = new FileWriter("marcas.txt", true);

				fichero.write(id_fabricante + "\n" + Nombre + "\n" + Descripcion + "\n");

				fichero.close();

			} catch (Exception ex) {
				System.out.println("Mensaje de la excepción: " + ex.getMessage());
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void PasarDatos() {
		// TODO Auto-generated method stub
		
	}

}
