import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface InterfazBBDD {

    public void Leer() throws SQLException, FileNotFoundException;

    public  void Escribir() throws SQLException;

    public void TXTABBDD() throws FileNotFoundException, SQLException;

    public void BBDDTXT() throws SQLException, IOException;
    
    public void PasarDatos();
    
    public void Actualizar() throws SQLException;
    
    public void Borrar() throws SQLException;





}
