package cinemalambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Pelicula;
import model.PeliculaDAO;

import java.util.Map;

// Handler value: example.Handler
public class CinemaLambda implements RequestHandler<Map<String, String>, String> {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public String handleRequest(Map<String, String> event, Context context) {
		
		LambdaLogger logger = context.getLogger();
		String response = "";
		String titulo = event.get("TituloPelicula"); 
	
		String server = System.getenv().get("server");
		String username = System.getenv().get("username");
		String password = System.getenv().get("password");
		
		
		PeliculaDAO miPeliculaDAO = new PeliculaDAO(server,username,password);
		Pelicula miPelicula = miPeliculaDAO.buscarPorTitulo(titulo);
		
		response = miPelicula.getTitulo();
		
		return response;
	}
}