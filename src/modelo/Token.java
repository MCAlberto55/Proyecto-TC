package modelo;

/*
 * Elementos propios del lenguaje MIO.
 * 
 * palabras reservadas.
 * operadores realacionales.
 * operadores aritmeticos.
 * asignación.
 * comentario.
 * 
 * 
 */

public class Token {

	private String valor; // valor del token.
	private int numeroDeLinea; // linea en la que se encuentra.
	private int tipoDeToken; 
	/* 
	 * TIPOS DE TOKEN
	 * 1: palabra reservada.
	 * 2: operador relacional.
	 * 3: operador artimetico.
	 * 4: el simbolo de asignación.
	 * 5: el simbolo de comentario.
	 * 6: identificador.
	 * 7: literal numerica.
	 * 8: literal de texto.
	 * 0: palabra no recoconocida. EL TOKEN NO ES VÁLIDO.
	 */
	private String claveAlfanumerica;
	/*
	 * la clave alfanumerica de las literales de texto empieza en TX01.
	 * y sigue en serie (TX01, TX02, ..., TXN).
	 * 
	 * la clave alfanumerica de los identificadores empieza en ID01.
	 * y sigue en serie (ID01, ID02, ..., IDN).
	 * 
	 * para literales numericas, claveAlfanumerica representa su valor en decimal.
	 * 
	 * para elementos propios del lenguaje MIO, claveAlfanumerica no se utiliza.
	 */
	
	//  CONSTRUCTOR. 
	//El tipo de token y la clave alfanumerica se definen al pasar por el clasificador.
	public Token(String valor, int numeroDeLinea) {
		//super();
		this.valor = valor;
		this.numeroDeLinea = numeroDeLinea;
		//this.tipoDeToken = tipoDeToken;
		//Clasificador.setTipoToken(this);
	}
	
	// GETTERS
	public String getValor() {
		return valor;
	}

	public int getNumeroDeLinea() {
		return numeroDeLinea;
	}

	public int getTipoDeToken() {
		return tipoDeToken;
	}
	
	public String getClaveAlfanumerica() {
		return claveAlfanumerica;
	}

	
	// SETTERS
	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setNumeroDeLinea(int numeroDeLinea) {
		this.numeroDeLinea = numeroDeLinea;
	}

	public void setTipoDeToken(int tipoDeToken) {
		this.tipoDeToken = tipoDeToken;
	}
	
	public void setClaveAlfanumerica(String claveAlfanumerica) {
		this.claveAlfanumerica = claveAlfanumerica;
	}
	
}
