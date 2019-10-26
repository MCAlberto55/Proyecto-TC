package modelo;

import java.util.ArrayList;
import java.util.List;

// import java.io.*;

public class AnaLex {
	
	//Lista de tokens. Aquí se encuentran los tokens en el orden del código fuente.
	public static List<String> listaTokens = new ArrayList<String>();
	//Matriz de identificadores.
	public static List<List<String>> IDS = new ArrayList<List<String>>();
	//Matriz de literales de texto.
	public static List<List<String>> TXT = new ArrayList<List<String>>();
	//Matriz de literales numericas. 
	public static List<List<String>> VAL = new ArrayList<List<String>>();
	//Lista de tokens
	public static List<Token> listaObjetosToken = new ArrayList<Token>();
	
	private Clasificador clasificador;
	
	
	public static void actualizarMatriz(List<List<String>> matriz, List<String> lista1, List<String> lista2) {
		borrarMatriz(matriz);
		setMatriz(matriz, lista1, lista2);
	}
	
	private static void borrarMatriz(List<List<String>> matriz) {
			matriz.clear(); // limpia la matriz.
	}
	
	private static void setMatriz(List<List<String>> matriz, List<String> lista1, List<String> lista2) {
		matriz.add(lista1);
		matriz.add(lista2);
	}
	
}
