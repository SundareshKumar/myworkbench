package datastructure.array.cloning;

public class CloneArray {
	public static void main(String args[]) {
		int intArray[] = { 1, 2, 3 };

		int cloneArray[] = intArray.clone();

		// will print false as deep copy is created
		// for one-dimensional array
		System.out.println(intArray == cloneArray);
		
		// the changes will not reflect in the clone array
		intArray[2] = 10;

		for (int i = 0; i < cloneArray.length; i++) {
			System.out.print(cloneArray[i] + " ");
		}

		// Shollow clone 
		cloneArray = intArray;
		
		// Will print true for the shallow clone
		System.out.println(intArray == cloneArray);
		
		// the changes will affect in the inint array and in clone array, changes in both arrays will reflect
		cloneArray[1] = 10;

		for (int i = 0; i < cloneArray.length; i++) {
			System.out.print(cloneArray[i] + " ");
		}

	
	}
}
