package datastructure.array.cloning;

public class CloningMultiDimensionalArray {
	
	public static void main(String args[]) {
		int intArray[][] = { { 1, 2, 3 }, { 4, 5 } };

		int cloneArray[][] = intArray.clone();

		// will print false
		System.out.println(intArray == cloneArray);

		// will print true as shallow copy is created
		// i.e. sub-arrays are shared
		System.out.println(intArray[0] == cloneArray[0]);
		System.out.println(intArray[1] == cloneArray[1]);
		
		// this changes will  change the inint array, because the single dimensional is cloned shallow
		cloneArray[0][0] = 98;
		
		// this changes will not change the inint array, because the two dimensional array cloned deeply
		int arr[] = {8,9};
		cloneArray[0] = arr;
		
		// this changes will not change the inint array, because the two dimensional array cloned deeply
		cloneArray[0][0] = 0;
		System.out.println("Clone Array Iteraation");
		System.out.println("***************************************");
		for (int i = 0; i < cloneArray.length; i++) {
			int arr1[] = cloneArray[i];
			for (int j = 0; j < arr1.length; j++) {
				System.out.println(cloneArray[i][j]);
			}
		}
		System.out.println("***************************************");
		System.out.println("init Array Iteraation");
		for (int i = 0; i < intArray.length; i++) {
			int arr1[] = intArray[i];
			for (int j = 0; j < arr1.length; j++) {
				System.out.println(intArray[i][j]);
			}
		}
		

	}
	
}
