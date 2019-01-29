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
	int id = 6;
	MongoCollection<Document> coll;
    Scanner sn = new Scanner(System.in);

	public Mongo() {
		MongoClient mongoClient = new MongoClient();
		System.out.println("Connected");
		MongoDatabase database = mongoClient.getDatabase("jaime");
		coll = database.getCollection("coches");
	}

	@Override
	public void Leer() throws SQLException, FileNotFoundException {
        System.out.println("Has seleccionado leer todos los protagonistas");
        for (Document object : coll.find()) {
            System.out.println("------->Protagonista: " + object.get("id"));
            System.out.println("Nombre: " + object.get("nombre"));
            System.out.println("Descripcion: " + object.get("descripcion"));
            System.out.println("Caracteristica1: " + object.get("caracteristica1"));
            System.out.println("Caracteristica2: " + object.get("caracteristica2"));
            System.out.println("Marca: " + object.get("marca"));

        }
    }

	public void annadirProtgonista() {

	}

	public void annadirSerie() {

	}

	@Override
	public void Escribir() throws SQLException {

		System.out.println("1. A�adir Marca");
		System.out.println("2. A�adir Coche");

		int eleccion = teclado.nextInt();

		switch (eleccion) {
		case 1:
            System.out.println("Escribe un nombre de coche al que quiera anadir una marca: ");
            nombre = sn.next();
            System.out.println("Escribe un nombre de marca: ");
            String prota = sn.next();
            System.out.println("Escribe una sede: ");
            String sede = sn.next();
            Bson filter3 = Filters.eq("nombre", nombre);
            Document firstDocument3 = coll.find(filter3).first();
            Document document2 = new Document();
            document2.put("id", id++);
            document2.put("nombre", prota);
            document2.put("sede", sede);
            Document updateCommand1 = new Document();
            updateCommand1.put("$addToSet", new Document("marca", document2));
            coll.updateOne(firstDocument3,updateCommand1);

			break;
		case 2:
			id++;
            System.out.println("Has seleccionado la opcion insert");
            System.out.println("Escribe un nombre: ");
            nombre = sn.next();
            System.out.println("Escribe la descripcion: ");
            String descripcion = sn.next();
            System.out.println("Escribe una caracteristica 1: ");
            String carac1 = sn.next();
            System.out.println("Escribe una caracteristica 2: ");
            String carac2 = sn.next();
            id++;
            coll.insertOne(

                    new Document()
                            .append("id",String.valueOf(id))
                            .append("nombre",nombre)
                            .append("descripcion",descripcion)
                            .append("caracteristica1",carac1)
                            .append("caracteristica2",carac2)
                            .append("marca",new Document())
            );

            System.out.println("Enhorabuena has insertado protagonista");

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
		System.out.println("Introduce una nueva caracteristica");
		String caracteristica1 = teclado.next();
        System.out.println("Introduce una nueva caracteristica");
        String caracteristica2 = teclado.next();
		Document queryDocument = new Document();
		queryDocument.append("caracteristica1", caracteristica1);
        queryDocument.append("caracteristica2", caracteristica2);
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
