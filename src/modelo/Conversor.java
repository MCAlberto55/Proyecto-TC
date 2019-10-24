package modelo;
import java.util.ArrayList;
import java.util.List;

public class Conversor {
	
	// para guardar las partes enteras y fraccionarias de los números.
	private static List<Object> listaParteEntera = new ArrayList<Object>();
	private static List<Object> listaParteFraccionaria = new ArrayList<Object>();
	
	
	public Conversor(){
	
	}
	
	/*
	private void imprimirLista(List<Object> lista) {
		System.out.println("\n");
		for(int i=0; i<lista.size(); i++) {
			System.out.println("" + lista.get(i));
		}
		
		System.out.println("\n");
	}
	*/
	
	//public String toBaseN(float numeroDecimal, int baseObjetivo) {
		
		//List<Object> nuevoNumero = new ArrayList
	//}
	
	
	// conversión de decimal a baseN.
	public static String toBaseN(float numeroDecimal, int baseObjetivo) {
		
		List<Integer> ListaParteEntera = new ArrayList<Integer>();
		List<Integer> ListaParteFraccionaria = new ArrayList<Integer>();
		
		// convertimos de flotante string para poder usar los métodos ordenar.
		String numero = Float.toString(numeroDecimal); 
		String auxString = "";
		int parteFraccionaria;
		int modulo;
		//int auxInt;
		//char auxChar;
		
		int parteEntera = (int)numeroDecimal; // sacamos la parte entera del número.
		ordenarParteEntera(numero);
		ordenarParteFraccionaria(numero);
		
		
		// conversión de decimal a baseN.
		
		//parte entera
		if(listaParteEntera.size() > 0) {
			
			while(parteEntera >= baseObjetivo) {
				modulo = parteEntera%baseObjetivo; // sacamos el modulo
				// lo guardamos en la parte entera del nuevo numero.
				ListaParteEntera.add(modulo);
				
				parteEntera = parteEntera/baseObjetivo;
			}
			
			ListaParteEntera.add(parteEntera); // añadimos el ultimo cociente.
			
		}
		
		
		// parte fraccionaria
		if(listaParteFraccionaria.size() > 0) {
			// pasamos la parte fraccionaria a un string.
			auxString = "";
			for(int i=0; i<listaParteFraccionaria.size(); i++) {
				auxString = auxString + listaParteFraccionaria.get(i); // 0.xxx
			}
			// ahora pasamos de string a float;
			//parteFraccionaria = Float.parseFloat(auxString);
			parteFraccionaria = Integer.parseInt(auxString);
			while(parteFraccionaria >= baseObjetivo) {
				
				modulo = parteFraccionaria%baseObjetivo;
				ListaParteFraccionaria.add(modulo);
				parteFraccionaria = parteFraccionaria/baseObjetivo;
				// = parteFraccionaria * baseObjetivo;
				//auxInt = (int)parteFraccionaria; // sacamos la parte entera del resultado de multiplicar.
				//agregamos esa parte entera al arreglo del nuevo numero.
				//ListaParteFraccionaria.add(auxInt);
			}
			ListaParteFraccionaria.add(parteFraccionaria);
		}
		
		
		// una vez tenemos los arreglos, tenemos que interpretar los resultados.
		numero = ""; // para el nuevo número.
		// primero la parte entera:
		if(ListaParteEntera.size() > 0) {
			for(int i=ListaParteEntera.size()-1; i>= 0; i--) {
				if((int)ListaParteEntera.get(i) < 10) { // si el número es menor que 10, se interpreta como ese número.
					numero = numero + ListaParteEntera.get(i);
				}
				else {
					// si es mayor a 10, obtenemos la letra que lo representa y lo guardamos en un auxiliar.
					auxString = obtenerLetra((int)ListaParteEntera.get(i)); 
					numero = numero + auxString;
				}
			}
		}
		
		/*
		// parte fraccionaria:
		if(ListaParteFraccionaria.size() > 0) {
			numero = numero + "."; // agregamos el punto.
			for(int i=0; i<ListaParteFraccionaria.size(); i++) {
				if((int)ListaParteFraccionaria.get(i) < 10) {
					numero = numero + ListaParteFraccionaria.get(i);
				}
				else {
					auxString = obtenerLetra((int)ListaParteFraccionaria.get(i));
					numero = numero + auxString;
				}
			}
		}
		*/
		
		borrarLista(listaParteEntera);
		borrarLista(listaParteFraccionaria);
		
		
		return numero;
	}

	
	public static float toDecimal(String numero, int base) {
		
		float numeroDecimal=0;
		int auxInt;
		char auxChar;
		
		ordenarParteEntera(numero);
		ordenarParteFraccionaria(numero);
		
		//imprimirLista(this.listaParteEntera);
		//imprimirLista(this.listaParteFraccionaria);
		
		// conversión
		if(listaParteEntera.size() > 0) {
			// convertimos la parte entera.
			int potencia = 0;
			// empezamos desde el final de la lista debimo al orden en el que quedan los digitos. 
			// usamos la variable potencia para ir elevando la base.
			for(int i=listaParteEntera.size() - 1; i>=0; i--) {
				
				if(Character.isDigit((char)listaParteEntera.get(i))) {
					auxInt = Character.getNumericValue((char) listaParteEntera.get(i)); // guardamos el dato en una variable entera.
					numeroDecimal = (float)(numeroDecimal + (auxInt * (Math.pow(base, potencia))));
				}
				// sino es numero, tenemos que encontrar el valor de la letra.
				else {
					auxChar = (char) listaParteEntera.get(i); // guardamos la letra en un auxiliar.
					auxInt = obtenerValor(auxChar); // obtenemos el valor.
					numeroDecimal = (float)(numeroDecimal + (auxInt * (Math.pow(base, potencia))));
				}
				
				potencia++;
			}
		}
		
		if(listaParteFraccionaria.size() > 0) {
			// convertimos la parte fraccionaria.
			for(int i=0; i<listaParteFraccionaria.size(); i++) {
				
				if(Character.isDigit((char)listaParteFraccionaria.get(i))) {
					auxInt = Character.getNumericValue((char)listaParteFraccionaria.get(i)); // guardamos el dato en una variable entera.
					numeroDecimal = (float)(numeroDecimal + (auxInt * (Math.pow(base, -(i+1)))));
				}
				// sino es numero, tenemos que encontrar el valor de la letra.
				else {
					auxChar = (char) listaParteFraccionaria.get(i); // guardamos la letra en un auxiliar.
					auxInt = obtenerValor(auxChar); // obtenemos el valor.
					
					numeroDecimal = (float)(numeroDecimal + (auxInt * (Math.pow(base, -(i+1)))));
				}
			}
		}
		// convertir la parte fraccionaria.
		
		borrarLista(listaParteEntera);
		borrarLista(listaParteFraccionaria);
		
		return numeroDecimal;
	}
	
	private static void ordenarParteEntera(String numero) {
		// obtenemos la parte entera.
		
		/*
		// separamos el numero a partir del punto.
		String[] partes = numero.split(".");
		String parteAux;
		// la parte entera se guarda en la posición 0.
		// convetimos todo a un arreglo de caracteres.
		System.out.println("" + partes.length);
		parteAux = partes[0];
		char[] parteEntera = parteAux.toCharArray();
		*/
		
		int index = numero.indexOf(".");
		char[] arregloNumero = numero.toCharArray();
		if(index != -1) { // indexOf devuelve -1 si no encuentra el caracter
			for(int i=0; i<index; i++) {
				listaParteEntera.add(arregloNumero[i]);
			}
		}
		else {
			for(int i=0; i<arregloNumero.length; i++) {
				listaParteEntera.add(arregloNumero[i]);
			}
		}
		
		/*
		// ahora copiamos el arreglo a la lista de parte entera.
		for(int i=0; i<parteEntera.length; i++) {
			this.listaParteEntera.add(parteEntera[i]);
		}
		*/
		
	}
	
	private static void ordenarParteFraccionaria(String numero) {
		
		int index = numero.indexOf(".");
		char[] arregloNumero = numero.toCharArray();
		if(index != -1) { // indexOf devuelve -1 si no encuentra el caracter
			for(int i=index+1; i<arregloNumero.length; i++) {
				listaParteFraccionaria.add(arregloNumero[i]);
			}
		}
		
		/*
		// separamos la cadena a partir del punto.
		String[] partes = numero.split(".");
		// ahora tomamos la parte fraccional 
		char[] parteFraccionaria = partes[1].toCharArray();
		
		// copiamos el arreglo a la lista de parte fraccionaria
		for(int i=0; i<parteFraccionaria.length; i++) {
			this.listaParteFraccionaria.add(parteFraccionaria[i]);
		}
		*/
	}
	
	
	private static String obtenerLetra(int valor) {
		String letra = "";
		
		switch(valor) {
		case 10:
			letra = "A";
			break;
		case 11:
			letra = "B";
			break;
		case 12: 
			letra = "C";
			break;
		case 13:
			letra = "D";
			break;
		case 14: 
			letra = "E";
			break;	
		case 15:
			letra = "F";
			break;
		}
		
		return letra;
	}
	
	
	private static int obtenerValor(char letra) {
		// lista de valores de las letras en sistema Hexadecimal 
		int valor=0;
		
		switch(letra) {
		case 'A':
			valor = 10;
			break;
		case 'B':
			valor = 11;
			break;
		case 'C':
			valor = 12;
			break;
		case 'D':
			valor = 13;
			break;
		case 'E':
			valor = 14;
			break;
		case 'F':
			valor = 15;
			break;
		}
		
		return valor; // para evitar error de sintaxis, nunca se llegará a esta instrucción.
	}
	
	
	private static void borrarLista(List<Object> lista) {
		
		for (int i = lista.size()-1; i>=0; i--) {
			lista.remove(i);
		}
	}
	
	
}
