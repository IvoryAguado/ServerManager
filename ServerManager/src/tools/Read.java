package tools;

import java.io.*;

public class Read{
	
 public static String dato()
  {
	 String sdato=null;
  try{
  	 InputStreamReader isr=new InputStreamReader(System.in);
  	 BufferedReader flujoE = new BufferedReader(isr);
  	 sdato=flujoE.readLine();
  	 }
  catch (IOException e)
  	{
  	System.out.println("Error "+e.getMessage());
  	}
  
  return sdato;
  
  }

 public static int datoInt()
 {
	String column=dato();
	try{
		  Integer.parseInt(column);
		  if (column.isEmpty() ) {
				 column="100";
				return Integer.parseInt(column);
			}else{
				return Integer.parseInt(column);
			}
		} catch (NumberFormatException e) {
			column="100";
			return Integer.parseInt(column);
		}
 }

 public static float datoFloat()
  {
  return Float.parseFloat(dato());	
  }
//Leer un char por teclado

public static char datoChar(){
	char c = ' ';
		try {
			java.io.BufferedInputStream b = new BufferedInputStream(System.in);
			 c = (char) b.read();
		} catch (IOException e) {
			System.out.println("Error al leer");
			e.printStackTrace();
		}
	return c;
	}
  
 public static long datoLong()
  {
  return Long.parseLong(dato());
  }

public static double datoDouble() {
	return Double.parseDouble(dato());
}
}