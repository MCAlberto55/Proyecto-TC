package modelo;

import java.util.ArrayList;
import java.util.List;


/*
 * El Clasificador sirve para identificar el tipo de token
 * y para asignarle una clave alfanumerica.
 */

public class Clasificador {
	
	private List<String> listaPalabrasReservadas; 

	//CONSTRUCTOR
	public Clasificador() {
		listaPalabrasReservadas = new ArrayList<String>();
		setPalabrasReservadas(); // inicializa la lista de palabras reservadas.
	}

	
	public void setClaveAlfanumerica(Token token) {
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
		
		switch(token.getTipoDeToken()) {
		case 6: // identificador.
			String claveAlfanumerica = "ID"; // base para cualquier clave.
			boolean continuar = true;
			//comprobar que el identificador no esté repetido. 
			//Revisando el valor y usando la matriz de identificadores.
			//Obtenemos la lista de valores de los identificadores. (primera columna de la matriz IDS)
			// y la lista de sus correspondientes claves alfanumericas.
			List <String> listaValores = AnaLex.IDS.get(0);
			List <String> listaClaves = AnaLex.IDS.get(1);
 			
			if( listaValores.size() > 0) {
				for(int i=0; i< listaValores.size() - 1; i++) {
					//List<Token> list = AnaLex.IDS.get(i);
					// si el valor del token ya existe en la matriz, le asignamos una clave ya existente.
					// la que le corresponde.
					if(token.getValor().equals(listaValores.get(i))) {
						token.setClaveAlfanumerica(listaClaves.get(i));
						continuar = false; // para que termine.
						break;
					}
				}
			}
			
			if(continuar == true) {
				if(listaValores.size() < 9) {
					// asignamos la nueva clave alfanumerica al token.
					token.setClaveAlfanumerica(claveAlfanumerica + "0" + (listaValores.size()+1));// ID0X, 0<X>10
					// agregamos el valor y la clave alfanumerica del token a la matriz.
					listaValores.add(token.getValor());
					listaClaves.add(token.getClaveAlfanumerica());
					
					// borrar la matriz de identificadores
					// y actualizarla con los nuevos datos.
					// función actualizarMatriz del AnaLex hace esto.
					AnaLex.actualizarMatriz(AnaLex.IDS, listaValores, listaClaves);
				}
			}
			
			break;
		case 7: // literal numerica.
			// obtenemos la lista de numeros en hexadecimal y sus valores en decimal
			// de la matriz de literales numericas.
			List<String> valoresHexadecimales = AnaLex.VAL.get(0);
			List<String> valoresDecimales = AnaLex.VAL.get(1);
			float valorDecimal; //guarda el valor decimal del número.
		    //String  valordecimal;
			String valorHexadecimal = token.getValor().substring(2); 
			// obtener el numero en hexadecimal (subcadena sin "0x"). 
			valorDecimal = Conversor.toDecimal(valorHexadecimal, 16); // devuelve un float.
			
			// añadimos la información a las listas.
			valoresHexadecimales.add(valorHexadecimal);
			valoresDecimales.add(String.valueOf((int)valorDecimal)); 
			// convertimos float a int, luego int a string.
			
			//actualizar la matriz de literales numericas.
			AnaLex.actualizarMatriz(AnaLex.VAL,valoresHexadecimales , valoresDecimales);
			
			break;
		case 8: // literal de texto.
			// MISMA IDEA DEL CASO 6.
			break;
		default: 
			break;
		}
	}
	
	
	public int setTipoToken(Token token) {
		
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
		
		
		// ELEMENTOS DE LENGUAJE MIO
			//PALABRA RESERVADA
		for(int i=0; i<listaPalabrasReservadas.size()-1; i++) {
			if(token.getValor().equals(listaPalabrasReservadas.get(i))) {
				return 1;
			}
		}
			//OPERADOR RELACIONAL
		if(token.getValor().equals("<") || token.getValor().equals(">") || token.getValor().equals("==")) {
			return 2;
		}
		
			//OPERADOR ARITMETICO
		if(token.getValor().equals("+") || token.getValor().equals("*") 
				|| token.getValor().equals("-") || token.getValor().equals("/")) {
			return 3;
		}
		
			//ASIGNACIÓN
		if(token.getValor().equals("=")) {
			return 4;
		}
		
			//COMENTARIO
		if(token.getValor().equals("#")) {
			return 5;
		}
		// FIN BLOQUE DE ELEMENTOS DEL LENGUAJE MIO.
		
		// para siguiente sección, pasaremos el valor del token a un char array.
		// pasamos el valor del token a un char array.
		char[] valorToken = token.getValor().toCharArray();
		
		
		//IDENTIFICADOR
		if(Character.isAlphabetic(valorToken[0])) {
			if(valorToken.length <= 16) {
				if(isAlfanumerico(valorToken)) {
					return 6;
				}
			}
		}
		
		// LITERAL NUMERICA 
		// revisamos que empieze con "0x"
		if(valorToken[0] == '0' && valorToken[1] == 'x') {
			// revisamos que el numero dado sea hexadecimal
			// obtener el número (subcadena sin "0x")
			String numero = token.getValor().substring(2); // desde la posición 2.
			if(numeroEsValido(numero,16)) {
				return 7;
			}
		}
		
		
		// LITERAL DE TEXTO
		// si empieza y termina en comilla (está encerrado en comillas)
		if(valorToken[0] == '"' && valorToken[(valorToken.length)-1] == '"') {
			return 8; // token de tipo 8: literal de texto.
		}
		
		// si no cumple con ningún tipo de token válido.
		return 0;
	}
	
	// verifica si el valor del token  está compuesto por caracteres alfanumericos.
	private boolean isAlfanumerico(char[] valorToken) {
		int contador=0; // cuenta los caracteres alfanumericos.
		
		for(int i=0; i<valorToken.length -1; i++) {
			if(Character.isAlphabetic(valorToken[i]) || Character.isDigit(valorToken[i]) ) {
				contador++;
			}
		}
		
		// si todos los caracteres son alfanumericos.
		if(contador == valorToken.length) {
			return true;
		}
		
		return false;
	}
	
	// VERIFICA QUE EL NUMERO ESTÉ ESCRITO EN HEXADECIMAL. (en el caso de las literales numericas)
	public boolean numeroEsValido(String numero, int base) {
		
		char[] hexadecimal = {'A','B','C','D','E','F'}; // CARACTERES ESPECIALES ACEPTADOR POR EL SISTEMA HEXADECIMAL
		char[] arrayNumero = numero.toCharArray(); // convertimos el numero a un arreglo de caracteres.
		int contador=0; 
		String auxString, auxString2; // para comparar.
		
		// si la base es diferente de 16.
		if(base != 16) {
			// para bases del 2-10.
			// desde 0 hasta la longitud del numero.
			for(int i=0; i<numero.length(); i++) {
				auxString = "" + arrayNumero[i];
				//System.out.println("" + numero.length());
				// numeros del 0 hasta base-1 (números aceptados por esa base)
				for(int j=0; j<base; j++){
					auxString2 = Integer.toString(j);
					if(auxString.equals(auxString2)) {
						contador++;
						//System.out.println("" + contador);
						break;
					}
				}	
			}
		}
		else {
			// contamos numeros
			// para base hexadecimal.
			// puede aceptar numeros del 1-9 y las letras A,B,C,D,E,F.
			for(int i=0; i<numero.length(); i++) {
				auxString = "" + arrayNumero[i];
				for(int j=0; j<=9; j++) {
					auxString2 = Integer.toString(j);
					if(auxString.equals(auxString2)) {
						contador++;
						break;
					}
				}
			}
			// contamos letras
			// si no coincide con alguno de los números, probamos con las letras.
			for(int i=0; i<numero.length(); i++) {
				for(int k=0; k<hexadecimal.length; k++) {
						if(arrayNumero[i] == hexadecimal[k]) {
							contador++;
							break;
						}
				}
			}
			
		}
		
		//System.out.println("" + contador);
		// si no todos los numeros coincidieron alguno de los numeros aceptados por la base.
		if(contador != numero.length()) {
			return false;
		}
		
		return true; // si todos coincidieron, entonces el número es válido.
	}
	
	// LISTA DE PALABRAS RESERVADAS.
	private void setPalabrasReservadas() {
		
		listaPalabrasReservadas.add("PROGRAMA");
		listaPalabrasReservadas.add("FINPROG");
		listaPalabrasReservadas.add("SI");
		listaPalabrasReservadas.add("ENTONCES");
		listaPalabrasReservadas.add("SINO");
		listaPalabrasReservadas.add("FINSI");
		listaPalabrasReservadas.add("REPITE");
		listaPalabrasReservadas.add("VECES");
		listaPalabrasReservadas.add("FINREP");
		listaPalabrasReservadas.add("IMPRIME");
		listaPalabrasReservadas.add("LEE");
		
	}
}
