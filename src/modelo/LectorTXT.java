
package modelo;

import java.io.*;
import java.util.StringTokenizer;

public class LectorTXT {
	
	

    public static void LeerTXT() throws IOException{
        
        String valor = null;
        String linea=null;

        //Almacena el número de línea
        int contador=0;
           
       try {
    	   //Buscar el txt
        FileReader fr = new FileReader("Direccion\\Nombre.txt");
        //crear el buffer y pasarle el FileReader
        BufferedReader bf = new BufferedReader(fr);  
        while ((linea = bf.readLine())!=null) { 
        	contador++;
        /*Dentro del while igualo "linea" a bf.readLine, de tal forma que line almacena
         la linea del txt como un String
        Entonces el while funcionará hasta que el valor de linea sea nulo(no hay mas líneas con datos)
        Uso el contador para que me de el número de línea	
        */
       
        //Declaro el objeto tipo StringTokenizer	
      StringTokenizer st =  new  StringTokenizer (linea); 
      /*
       While anidado para la interacion del StringTokenizer
       Es parecido al FileReader pero palabra por palabra
       Este while se ejecuta mientras la línea tenga tokens
       */
       while  (st.hasMoreTokens ()) {  
    	   valor=st.nextToken();
    	   
    	   //Llenar vector de tokens 
    	   AnaLex.listaTokens.add(valor);
    	   //se crea el ojeto token
    	   Token token=new Token(valor,contador);
    	   //Guardo todos los token en la lista
    	   AnaLex.listaObjetosToken.add(token);

       }
       
       }
     
        } catch (FileNotFoundException ex) {
        System.out.println("falló");
               }    
           

           
   }
	
}
