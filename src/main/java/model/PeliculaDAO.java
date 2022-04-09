package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import model.*;

public class PeliculaDAO {

	public String server = "";
	public String username = "";
	public String password = "";

	public final String COLUMNAS = "id, titulo, fechaPub, descripcion, director, escritor, ano, actores, clasificacion, funciones";

	
	protected Connection getConnection() {

		Connection result = null;

		try {
			// Cargamos el puente JDBC => Mysql
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Intentamos conectarnos a la base de Datos
			result = DriverManager.getConnection(server, username, password);

		} catch (SQLException ex) {
			System.out.println("Error de mysql");
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Imposible encontrar Driver...");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Se produjo un error inesperado: ");
			e.printStackTrace();
		}
		return result;
	}
	
	public PeliculaDAO(String server, String username, String password) {
		super();
		this.server = server;
		this.username = username;
		this.password = password;
	}

	public void guardar(Pelicula Pelicula) throws ModelException{
		
		Pelicula result = null;
		
		if(Pelicula == null){
			
			throw new ModelException("Intentaste guardar una pelicula nulo");
		}
		
		// validando campos necesarios
		if ( Pelicula.getTitulo() == null || Pelicula.getTitulo().length()<0){
			System.out.println("Intentaste guardar: "+Pelicula+" y el campo Titulo, es mandatorio");
			throw new ModelException("Intentaste guardar: "+Pelicula+" y el campo Titulo, es mandatorio");
		}
		
		if ( Pelicula.getId() == null){
			//Se asume que desea insertar un nuevo registro
			System.out.println("ejecutando insertar");
			
			// buscando si alguna llave candidata ya existe
			if ( buscarPorTitulo(Pelicula.getTitulo()) != null){
				throw  new ModelException("El titulo: "+Pelicula.getTitulo()+" ya existe");
			}
			
			result= this.insertar(Pelicula);
			
		}
		else{
			System.out.println("ejecutnado modificar con: "+Pelicula);
			result = this.modificar(Pelicula);			
		}
		System.out.println("Pelicula antes de asignar()-->"+Pelicula);
		System.out.println("result antes de asignar()-->"+result);
		Pelicula.asignar(result);
		System.out.println("Pelicula despues de asignar()-->"+Pelicula);
	}

	private Pelicula insertar(Pelicula pelicula) throws ModelException{

		Statement sentencia;
		String query = "";
		Connection conn = null;
		try {

			conn = getConnection();
			sentencia = conn.createStatement();
			String id = generarLlave();
			
			//titulo, fechaPub, descripcion, director, escritor, ano, actores, clasificacion, funciones			
			query = "INSERT INTO pelicula VALUES ('" + id + "','"
					+ pelicula.getTitulo()+"','"
					+ pelicula.getFechaPubAsText()+ "','" 
					+ pelicula.getDescripcion() + "','"
					+ pelicula.getDirector() + "','"
					+ pelicula.getEscritor() + "','"
					+ pelicula.getAno() + "','" 
					+ pelicula.getActores() + "','"
					+ pelicula.getClasificacion() + "','"
					+ pelicula.getFunciones() + "')";

			System.out.println("ejecutando query --> " + query);

			sentencia.execute(query);
			
			pelicula.setId(id);
			
			return buscarPorID(id+"");
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModelException("Error al ejecutar insertar Pelicula: " +pelicula);

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String generarLlave() {		
		return new java.util.Date().getTime()+"";
	}

	private Pelicula modificar(Pelicula pelicula) throws ModelException {

		PreparedStatement sentencia;
		String query = "";
		Connection conn = null;
		try {

			conn = getConnection();

			// titulo, fechaPub, descripcion, director, escritor, ano, actores, clasificacion, funciones
			sentencia = conn
					.prepareStatement("update pelicula set titulo=? , fechaPub=? , descripcion=? , director=? , escritor=? , ano=? , actores=? , clasificacion=? , funciones=?  where id = ?");

			sentencia.setString(1, pelicula.getTitulo());
			sentencia.setDate(2, new java.sql.Date(pelicula.getFechaPub().getTime()));  
			sentencia.setString(3, pelicula.getDescripcion());
			sentencia.setString(4, pelicula.getDirector());
			sentencia.setString(5, pelicula.getEscritor());
			sentencia.setString(6, pelicula.getAno());
			sentencia.setString(7, pelicula.getActores());
			sentencia.setString(8, pelicula.getClasificacion());
			sentencia.setString(9, pelicula.getFunciones());
			sentencia.setString(10, pelicula.getId());

			System.out.println("ejecutando query --> " + query);

			sentencia.executeUpdate();

			return buscarPorID(pelicula.getId());
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModelException("error al actualizar la pelicula: "+pelicula);

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public Pelicula buscarPorTitulo(String titulo) {

		PreparedStatement sentencia;
		String query = "";
		Connection conn = null;
		
		try {
			conn = getConnection();
			query = "select " + COLUMNAS + " from pelicula where titulo like ?";
			System.out.println("ejecutando query --> " + query);
			sentencia = conn.prepareStatement(query);
			sentencia.setString(1,"%" + titulo + "%");
			ResultSet resultado = sentencia.executeQuery();
			
			String id = null;
			String fechaPub = null; 
			String descripcion = null; 
			String director = null; 
			String escritor = null; 
			String ano = null; 
			String actores = null; 
			String clasificacion = null; 
			String funciones = null;

			Pelicula Pelicula = null;
			if (resultado.next() == true) {
				
				id = resultado.getString("id");								
				titulo = resultado.getString("titulo");
				fechaPub = resultado.getString("fechaPub"); 
				descripcion = resultado.getString("descripcion"); 
				director = resultado.getString("director"); 
				escritor = resultado.getString("escritor"); 
				ano = resultado.getString("ano"); 
				actores = resultado.getString("actores"); 
				clasificacion = resultado.getString("clasificacion"); 
				funciones = resultado.getString("funciones");
				
				Pelicula = new Pelicula();
								
				Pelicula.setId(id);		
				Pelicula.setTitulo(titulo);
				Pelicula.setFechaPubAsText(fechaPub);
				Pelicula.setDescripcion(descripcion);
				Pelicula.setDirector(director);
				Pelicula.setEscritor(escritor);
				Pelicula.setAno(ano);
				Pelicula.setActores(actores);
				Pelicula.setClasificacion(clasificacion);
				Pelicula.setFunciones(funciones);
				
			}

			return Pelicula;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Pelicula buscarPorID(String id) {

		PreparedStatement sentencia;
		String query = "";
		Connection conn = null;
		try {
			conn = getConnection();
			query = "select " + COLUMNAS + " from pelicula where id =?";
			System.out.println("ejecutando query --> " + query);
			sentencia = conn.prepareStatement(query);
			sentencia.setString(1, id);
			ResultSet resultado = sentencia.executeQuery();
			
			String titulo = null;
			String fechaPub = null; 
			String descripcion = null; 
			String director = null; 
			String escritor = null; 
			String ano = null; 
			String actores = null; 
			String clasificacion = null; 
			String funciones = null;

			Pelicula Pelicula = null;
			if (resultado.next() == true) {
				
				id = resultado.getString("id");								
				titulo = resultado.getString("titulo");
				fechaPub = resultado.getString("fechaPub"); 
				descripcion = resultado.getString("descripcion"); 
				director = resultado.getString("director"); 
				escritor = resultado.getString("escritor"); 
				ano = resultado.getString("ano"); 
				actores = resultado.getString("actores"); 
				clasificacion = resultado.getString("clasificacion"); 
				funciones = resultado.getString("funciones");
				
				Pelicula = new Pelicula();
								
				Pelicula.setId(id);		
				Pelicula.setTitulo(titulo);
				Pelicula.setFechaPubAsText(fechaPub);
				Pelicula.setDescripcion(descripcion);
				Pelicula.setDirector(director);
				Pelicula.setEscritor(escritor);
				Pelicula.setAno(ano);
				Pelicula.setActores(actores);
				Pelicula.setClasificacion(clasificacion);
				Pelicula.setFunciones(funciones);
				
			}

			if (resultado.next() == true) {
				throw new RuntimeException("Existen dos peliculas con el mismo titulo...");
			}
			return Pelicula;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Collection<Pelicula> traerTodos() {

		PreparedStatement sentencia;
		String query = "";
		Connection conn = null;
		Collection<Pelicula> coleccionPeliculas = new Vector<Pelicula>();
		try {
			conn = getConnection();
			query = "select " + COLUMNAS + " from pelicula";
			System.out.println("ejecutando query --> " + query);
			sentencia = conn.prepareStatement(query);
			ResultSet resultado = sentencia.executeQuery();
			
			String id = null;
			String titulo = null;
			String fechaPub = null; 
			String descripcion = null; 
			String director = null; 
			String escritor = null; 
			String ano = null; 
			String actores = null; 
			String clasificacion = null; 
			String funciones = null;
			
			while (resultado.next() == true) {
				
				id = resultado.getString("id");								
				titulo = resultado.getString("titulo");
				fechaPub = resultado.getString("fechaPub"); 
				descripcion = resultado.getString("descripcion"); 
				director = resultado.getString("director"); 
				escritor = resultado.getString("escritor"); 
				ano = resultado.getString("ano"); 
				actores = resultado.getString("actores"); 
				clasificacion = resultado.getString("clasificacion"); 
				funciones = resultado.getString("funciones");
				
				Pelicula Pelicula = new Pelicula();
								
				Pelicula.setId(id);		
				Pelicula.setTitulo(titulo);
				Pelicula.setFechaPubAsText(fechaPub);
				Pelicula.setDescripcion(descripcion);
				Pelicula.setDirector(director);
				Pelicula.setEscritor(escritor);
				Pelicula.setAno(ano);
				Pelicula.setActores(actores);
				Pelicula.setClasificacion(clasificacion);
				Pelicula.setFunciones(funciones);
				
				coleccionPeliculas.add(Pelicula);
				
			}
			return coleccionPeliculas;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return coleccionPeliculas;
	}
	
	public boolean borrar(Pelicula Pelicula) throws ModelException{

		PreparedStatement sentencia;
		String query = "";
		Connection conn = null;
		try {
			conn = getConnection();
			query = "delete from pelicula where id =?";
			System.out.println("ejecutando query --> " + query);
			sentencia = conn.prepareStatement(query);
			sentencia.setString(1, Pelicula.getId());
			int resultado = sentencia.executeUpdate();

			return resultado>0;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}