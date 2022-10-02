package ejercicio6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex9 {
	
	/*
	 *	Funcion que permite generar la conexion con la base de datos
	 *Requiere como parámetros nombre usuario(root), contraseña y nombre de la base de datos
	 */
	
	
	public static Connection crearConexion(String login,String password,String bd) {
		Connection conn = null;
		String url="jdbc:mysql://localhost:3306/" + bd;
		try {
			String sURL = url;
			conn = DriverManager.getConnection(sURL,login,password);
			if (conn != null) {
			System.out.println("-Abierta base de datos " + url + " - Ok");
				return(conn);
			}
			else {
				return(conn);
			}
		}
		catch (SQLException ex) {
			System.out.println(ex);
			return(conn);
		}
	}
	
	/*
	 * Funcion que permte generar tablas en la base de datos
	 * Requiere pasar una conexión(obtenida de la función anterior),
	 * el nombre de la tabla, el codigo de la tabla SQL como string, y un 
	 * objeto st que permite comprender sql en entorno java. 
	 */
	
	
	public static void creaTabla(Connection conn,String Name_Table, String query,Statement st) {
		try {
			st.executeUpdate(
			"CREATE TABLE IF NOT EXISTS "+Name_Table+ "("+query+");");
			System.out.println("-Creada tabla (contacto) - Ok");
		}
		catch (SQLException ex) {
			System.out.println(ex);
		}
	}
	
	/*
	 * Funcion que permite insertar informacion en una tabla. Recibe como parámetro el nombre
	 * de la tabla sobre la cual se realiza la inserción, los parametros de la tabla como string(formato SQL),
	 * y los valores como string (formato SQL). Tambien recibe un objeto Statement. 
	 */
	
	public static void insertarTabla(String name_tabla, String campos,String valores,Statement st) {
		try {
				String query="INSERT INTO "+name_tabla+"("+campos+")"+"Values"+valores+";";
				st.executeUpdate(query);
				System.out.println("-Añadir registros a la tabla - Ok");
		}
		catch (SQLException ex) {
			System.out.println(ex);
		}

	}
	
	/*
	 * Funcion para borrar una tabla de una base de datos. 
	 * Recibe como parametro el nombre de la tabla y un objeto Statement.
	 */
	
	
	public static void borrarTabla(String name_table,Statement st){
		try {
			st.executeUpdate("DROP TABLE IF EXISTS "+name_table);
			System.out.println("-Borrar tabla contacto - Ok");
		}
		catch (SQLException ex) {
			System.out.println("Se ha de borrar respetando la relacion del modelo");
		}
	}
	
	
	public static void main(String[] args) {
		
		//Definicion de constantes y variables
		
		Connection c;
		
		/*
		 * Se adjunta:
		 * 1-Codigo de creacion de tabla facultad
		 * 2-Nombre de tabla
		 * 3-Campos en los cuales se realiza las inserciones
		 * 4-Valores a insertar
		 */
		
		final String query_Facultad="Codigo INT not null, Nombre NVARCHAR(100) not null,PRIMARY KEY(Codigo)";
		final String facultad="Facultad";
		final String campo_facultad="Codigo,Nombre";
		final String valores_facultad="('1000','URV'),('1001','UPC'),('1002','Harvard'),('1003','MIT'),('1004','UPV')";
		
		/*
		 * Se adjunta:
		 * 1-Codigo de creacion de tabla Investigadores
		 * 2-Nombre de tabla
		 * 3-Campos en los cuales se realiza las inserciones
		 * 4-Valores a insertar
		 */
		
		final String query_Investigadores="DNI varchar(8) not null,NomApels NVARCHAR(255) not null,Facultad INT not null,PRIMARY KEY(DNI),FOREIGN KEY (Facultad)REFERENCES Facultad(Codigo)";
		final String investigadores="Investigadores";
		final String campo_investigadores="DNI,NomApels,Facultad";
		final String valores_investigadores="('12345678','Rafael Nadal','1000'),('12345679','Roger Federer','1001'),('11234567','Carlos Alcaraz','1002'),('11223344','Carlos Moya','1003'),('11111111','Caasper Ruud','1004')";
		
		/*
		 * Se adjunta:
		 * 1-Codigo de creacion de tabla Equipos
		 * 2-Nombre de tabla
		 * 3-Campos en los cuales se realiza las inserciones
		 * 4-Valores a insertar
		 */
		
		final String query_Equipos="NumSerie char(4) not null,Nombre nvarchar(100) not null,Facultad INT not null,PRIMARY KEY(NumSerie),FOREIGN KEY (Facultad) REFERENCES Facultad(Codigo)";
		final String equipos="Equipos";
		final String campo_equipos="NumSerie,Nombre,Facultad";
		final String valores_equipos="('0000','Fotonica','1000'),('0001','Mecanica','1001'),('0002','Matematica','1002'),('0003','Robotica','1003'),('0004','VoIP','1004')";
		
		
		/*
		 * Se adjunta:
		 * 1-Codigo de creacion de tabla Reserva
		 * 2-Nombre de tabla
		 * 3-Campos en los cuales se realiza las inserciones
		 * 4-Valores a insertar
		 */
		
		final String query_Reserva="DNI varchar(8) not null,NumSerie char(4) not null,Comienzo DATETIME not null,Fin DATETIME not null,PRIMARY KEY(DNI,NumSerie),FOREIGN KEY (DNI) REFERENCES Investigadores (DNI),FOREIGN KEY (NumSerie) REFERENCES Equipos (NumSerie)";
		final String reserva="Reserva";
		final String campo_reserva="DNI,NumSerie,Comienzo,Fin";
		final String valores_reserva="('12345678','0000','2022-09-13 23:28:00','2022-09-13 23:48:00'),('12345679','0001','2022-09-13 23:28:00','2022-09-14 23:48:00'),('11234567','0002','2022-09-16 23:28:00','2022-09-18 23:48:00'),('11223344','0003','2022-09-13 23:28:00','2022-09-13 23:48:00'),('11111111','0004','2022-09-13 23:28:00','2022-09-13 23:48:00')";
		
		
		
				
		try {
			//Se genera la conexion
			c=crearConexion("root","Tsystems22","ex9");
			
			//Creacion objeto Statement.
			Statement st = c.createStatement();
			
			//Creacion 4 tablas
			
			creaTabla(c,facultad,query_Facultad,st);
			creaTabla(c,investigadores,query_Investigadores,st);
			creaTabla(c,equipos,query_Equipos,st);
			creaTabla(c,reserva,query_Reserva,st);
			
			//Insercion de datos en las tablas
			
			insertarTabla(facultad,campo_facultad,valores_facultad,st);
			insertarTabla(investigadores,campo_investigadores,valores_investigadores,st);
			insertarTabla(equipos,campo_equipos,valores_equipos,st);
			insertarTabla(reserva,campo_reserva,valores_reserva,st);
			
			//Se muestran por pantalla los resultados. 
			
			System.out.println("\nTabla de facultades");
			ResultSet rs = st.executeQuery("select * from Facultad");
			while (rs.next()) {
				System.out.println(
						rs.getString(1)+" "+
						rs.getString(2));
			}
			
			
			System.out.println("\nTabla de investigadores");
			ResultSet rs2 = st.executeQuery("select * from Investigadores");
			while (rs2.next()) {
				System.out.println(
						rs2.getString(1)+" "+
						rs2.getString(2)+" "+
						rs2.getString(3));
			}
			
			System.out.println("\nTabla de equipos");
			ResultSet rs3 = st.executeQuery("select * from Equipos");
			while (rs3.next()) {
				System.out.println(
						rs3.getString(1)+" "+
						rs3.getString(2)+" "+
						rs3.getString(3));
			}
			

			
			System.out.println("\nTabla de reserva");
			ResultSet rs4 = st.executeQuery("select * from Reserva");
			while (rs4.next()) {
				System.out.println(
						rs4.getString(1)+" "+
						rs4.getString(2)+" "+
						rs4.getString(3)+" "+
						rs4.getString(3));
			}
		c.close();
			
		}
		catch (SQLException ex) {
			System.out.println(ex);
		}
	}
}
