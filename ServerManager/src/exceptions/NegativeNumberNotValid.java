package exceptions;

@SuppressWarnings("serial")
public class NegativeNumberNotValid extends Exception {


	public NegativeNumberNotValid() {
		System.err.println("Numeros negativos no validos.");
	}
}
