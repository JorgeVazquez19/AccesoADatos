

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.login.Configuration;

import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.Query;
import org.hibernate.Session;


import java.util.Iterator;
import java.util.List;

public class gestionHibernate implements InterfazBBDD {
	
	Session session;
	int ID;
	String nombre;
    String descripcion;
    String caracteristica1;
    String caracteristica2;
    Marca marca;

	public void Leer() throws SQLException, FileNotFoundException {
		
	}

	public void Escribir() throws SQLException {
		
		
	}

	public void TXTABBDD() throws FileNotFoundException, SQLException {
		
	}

	public void BBDDTXT() throws SQLException, IOException {
		
	}

	public void Actualizar() throws SQLException {
		
	}

	public void Borrar() throws SQLException {
		
	}

	@Override
	public void PasarDatos() {
		// TODO Auto-generated method stub
		
	}

}
