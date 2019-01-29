import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.conversions.Bson;

public class Mongo implements InterfazBBDD {
	MongoDatabase database;
	Scanner teclado = new Scanner(System.in);
	String nombre;
	String representante;
	int id = 4;
	MongoCollection<Document> coll;

	public Mongo() {
		MongoClient mongoClient = new MongoClient();
		System.out.println("Connected");
		MongoDatabase database = mongoClient.getDatabase("jaimebbdd");
		coll = database.getCollection("protagonistas");
	}

	@Override
	public void Leer() throws SQLException, FileNotFoundException {
		System.out.println("Has seleccionado leer todos los protagonistas");
		for (Document object : coll.find()) {
			System.out.println("------->Protagonista: " + object.get("id"));
			System.out.println("Nombre: " + object.get("nombre"));
			System.out.println("Edad: " + object.get("edad"));
			System.out.println("Representante: " + object.get("representante"));
			System.out.println("Serie: " + object.get("serie"));

		}

	}

	public void annadirProtgonista() {

	}

	public void annadirSerie() {

	}

	@Override
	public void Escribir() throws SQLException {

		System.out.println("1. Añadir Serie");
		System.out.println("2. Añadir Protagonista");

		int eleccion = teclado.nextInt();

		switch (eleccion) {
		case 1:

			break;
		case 2:
			id++;
			coll.insertOne(new Document("datos",
					asList(new Document().append("id", String.valueOf(id)).append("nombre", contacto.getNombre())
							.append("telefono", contacto.getTelefono()).append("email", contacto.getEmail())
							.append("web", contacto.getWeb()).append("notas", contacto.getNotas())))
			);
			break;
		default:
			break;
		}
	}

	@Override
	public void TXTABBDD() throws FileNotFoundException, SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void BBDDTXT() throws SQLException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void Actualizar() throws SQLException {
		System.out.println("Has seleccionado la opcion update");
		System.out.println("Escribe el nombre del que quieres updatear: ");
		nombre = teclado.next();
		Bson filter2 = Filters.eq("nombre", nombre);
		Document firstDocument2 = coll.find(filter2).first();
		System.out.println(firstDocument2.toString());
		Document newdocument = new Document();
		newdocument.append("nombre", nombre);
		System.out.println("Introduce la nueva representante");
		representante = teclado.next();
		Document queryDocument = new Document();
		queryDocument.append("representante", representante);
		Document engloba = new Document();
		engloba.append("$set", queryDocument);
		coll.findOneAndUpdate(newdocument, engloba);
		System.out.println("Enhorabuena has updateado");

	}

	@Override
	public void Borrar() throws SQLException {
		System.out.println("Has seleccionado borrar");
		System.out.println("Escribe el nombre del que quieres borrar: ");
		nombre = teclado.next();
		Bson filter = Filters.eq("nombre", nombre);
		Document firstDocument = coll.find(filter).first();
		System.out.println(firstDocument.toString());
		coll.findOneAndDelete(firstDocument);
		System.out.println("Enhorabuena has borrado");

	}

	@Override
	public void PasarDatos() {
		// TODO Auto-generated method stub

	}

}
