package datastructure;

public class HelloWorld {

	public static void main(String[] args) {
		System.out.println("HelloWorld.main()");
		System.out.println("HelloWorld.main()");
	}
	
}


public class HexToASCIIExample {
	public static void main(String[] args) {
		String hex = "7236C58000E38000";

		if (hex.length() % 2 != 0) {
			System.err.println("Invlid hex string.");
			return;
		}

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < hex.length(); i = i + 2) {
			// Step-1 Split the hex string into two character group
			String s = hex.substring(i, i + 2);
			// Step-2 Convert the each character group into integer using valueOf method
			int n = Integer.valueOf(s, 16);
			// Step-3 Cast the integer value to char
			builder.append((char) n);
		}

		System.out.println("Hex = " + hex);
		System.out.println("ASCII = " + builder.toString());
		 String ascii = builder.toString();
		 builder = new StringBuilder();
		 char[] ch = ascii.toCharArray();
	      for (char c : ch) {
	         int i = (int) c;
	         // Step-3 Convert integer value to hex using toHexString() method.
	         builder.append(Integer.toHexString(i).toUpperCase());
	      }

	      System.out.println("ASCII = " + ascii);
	      System.out.println("Hex = " + builder.toString());
	}
}
