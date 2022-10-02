package ejercicio6;
import java.sql.*;
import java.util.Scanner;

public class Ex6 {
	
	
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
	 * Funcion encargada de leer por pantalla 5 Strings y almacenarlos. 
	 * 
	 */
	
	public static String[] lecturaString() {
		String arrayString[]=new String[5];
		Scanner sc=new Scanner(System.in);
		for(int i=0;i<5;i++) {
			System.out.println("Introduzca un nombre");
			arrayString[i]=sc.nextLine();
		}
		return(arrayString);
	}
	
	//Funcion encargada de leer por pantalla 5 enteros y almacenarlos
	
	public static int[] lecturaInt() {
		int arrayInt[]=new int[5];
		Scanner sc=new Scanner(System.in);
		for(int i=0;i<5;i++) {
			System.out.println("Introduzca un numero entero");
			arrayInt[i]=sc.nextInt();
		}
		return(arrayInt);
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
	
	public void borrarTabla(String name_table,Statement st){
		try {
			st.executeUpdate("DROP TABLE IF EXISTS "+name_table);
			System.out.println("-Borrar tabla contacto - Ok");
		}
		catch (SQLException ex) {
			System.out.println("Se ha de borrar respetando la relacion del modelo");
		}
	}


	public static void main(String[] args) {
		
		//Variables que llena el usuario por pantalla
		
		String Nombre_pieza []= new String[5]; 
		String Id_proveedor[]=new String [5];
		String Nombre_proveedor[]=new String[5];
		int Codigo_pieza[]=new int[5];
		String Id_pro_suministro[]=new String[5];
		int precio[]=new int[5];
		
		//String con las variables
		
		String valores_piezas="";
		String valores_prov="";
		String valores_sum="";
		
		//Codigo creación tabla SQL   en formato String

		final String query_piezas="Codigo INT not null AUTO_INCREMENT,Nombre NVARCHAR(100) not null,PRIMARY KEY(Codigo)";
		final String query_suministra="CodigoPieza INT not null,IdProveedor char(4) not null,Precio  INT not null,PRIMARY KEY(CodigoPieza,IdProveedor),FOREIGN KEY (CodigoPieza) REFERENCES Piezas (Codigo),FOREIGN KEY (IDProveedor) REFERENCES Proveedores (ID)";
		final String query_proveedores="ID char(4) not null,Nombre NVARCHAR(100) not null,PRIMARY KEY(ID)";
		
		
		//Strings para generar la inserción de datos. 
		
		String campo_piezas="Nombre";
		String campo_prov="ID,Nombre";
		String campo_sum="CodigoPieza,IdProveedor,Precio";
		
		
		try {
			
			//Generacion de conexión 
			Connection c;
			c=crearConexion("root","Tsystems22","ex6");
			Statement st = c.createStatement();			
			
			//Creacion de las tablas
			creaTabla(c,"Piezas",query_piezas,st);
			creaTabla(c,"Proveedores",query_proveedores,st);
			creaTabla(c,"Suministros",query_suministra,st);
			
			//Lectura de los nombres de las piezas introducidos por pantalla
			System.out.println("Introduzca los nombres de las piezas (tabla piezas)");
			Nombre_pieza=lecturaString().clone();
			
			/*
			 * Se llena el String valores_piezas con los nombres de las piezas
			 * introducidas por el usuario, teniendo en cuenta el formato para su insercion.  
			 */
			
			for (int i=0; i<Nombre_pieza.length;i++) {
				if (i==4) {
					valores_piezas=valores_piezas+"('"+Nombre_pieza[i]+"')";
				}
				else {
					valores_piezas=valores_piezas+"('"+Nombre_pieza[i]+"'),";
				}
			}
			
			//Se realiza la inserción de los nombres de las piezas. 
			
			insertarTabla("Piezas",campo_piezas,valores_piezas,st);
			
			//Permite visualizar el contenido total de la tabla por pantalla. 
			
			ResultSet rs = st.executeQuery("select * from Piezas");
			while (rs.next()) {
				System.out.println(
						rs.getString(1)+" "+
						rs.getString(2));
			}
			
			
			
			//Lectura de los identificadores de los proveedores introducidos por pantalla
			
			System.out.println("Introduzca el identificador del proveedor(tabla proveedor)");
			Id_proveedor=lecturaString().clone();
			
			//Lectura de los nombres de los proveedores introducidos por pantalla
			
			System.out.println("\nIntroduzca el nombre del proveedor");
			Nombre_proveedor=lecturaString().clone();
			
			/*
			 * Se llena el String valores_prov con los id y nombres de los proveedores
			 * introducidos por el usuario, teniendo en cuenta el formato para su insercion.  
			 */
			
			for (int i=0;i<Id_proveedor.length;i++) {
				if (i==4) {
					valores_prov=valores_prov+"('"+Id_proveedor[i]+"'"+","+"'"+Nombre_proveedor[i]+"')";
				}
				else {
					valores_prov=valores_prov+"('"+Id_proveedor[i]+"'"+","+"'"+Nombre_proveedor[i]+"'),";
				}
			}
			
			//Se realiza la inserción de los datos
			insertarTabla("Proveedores",campo_prov,valores_prov,st);
			
			
			//Visualizacion del contenido total de la tabla 
			ResultSet rs2 = st.executeQuery("select * from Proveedores");
			while (rs2.next()) {
				System.out.println(
						rs2.getString(1)+" "+
						rs2.getString(2));
			}
			
			//Lectura de los identificadores de las piezas introducidos por pantalla
			System.out.println("Introduzca el identificador de la pieza(tabla suministro)");
			Codigo_pieza=lecturaInt().clone();
			
			//Lectura de los identificadores de los proveedores introducidos por pantalla
			System.out.println("\nIntroduzca el identificador del proveedor(tabla suministro)");
			Id_pro_suministro=lecturaString().clone();
			
			//Lectura de los precios introducidos por pantalla
			System.out.println("\nIntroduzca el precio(tabla suministro)");
			precio=lecturaInt().clone();
			
			/*
			 * Se llena el String valores_sum con los id de pieza y proveedor junto con su correspondiente precio
			 * introducidos por el usuario, teniendo en cuenta el formato para su insercion.  
			 */
			for (int i=0;i<Codigo_pieza.length;i++) {
				if (i==4) {
					valores_sum=valores_sum+"('"+Codigo_pieza[i]+"'"+","+"'"+Id_pro_suministro[i]+"'"+","+"'"+precio[i]+"')";
				}
				else {
					valores_sum=valores_sum+"('"+Codigo_pieza[i]+"'"+","+"'"+Id_pro_suministro[i]+"'"+","+"'"+precio[i]+"'),";
				}
			}
			
			//Insercion
			insertarTabla("Suministros",campo_sum,valores_sum,st);
			
			//Visualizacion
			ResultSet rs3 = st.executeQuery("select * from Suministros");
			while (rs3.next()) {
				System.out.println(
						rs3.getString(1)+" "+
						rs3.getString(2)+" "+
						rs3.getString(3));
			}
			c.close();
		}
		catch (SQLException ex) {
			System.out.println(ex);
		}
	}
}

