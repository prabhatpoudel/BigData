package Lab2;

public class Question2 {
	//Defining Function f
	public static int f(int x) {
		return (x == 4 || x ==3 ) ? x : 1;
	}

	//Defining Function g
	public static int g(int x, int y) {
		return x * y;
	}

	public static void main(String[] args) {
		int[] values = { 2, 5, 3, 0, 7, 10,11,13,1 }; //Not Nice Example
//		int[] values = { 2, 5, 3, 0, 7, 10,11,13,1,4 }; //Nice Array Example
		//Defining Mapping array same as length of the input array
		int[] mapped = new int[values.length];
		int reduce = 1;
		for (int i = 0; i < values.length; i++) {
			System.out.println(f(values[i]));
			//Mapping the values of array as defined in the Map Function f
			mapped[i] = f(values[i]);
		}
		for (int map : mapped) {
			reduce=g(reduce,map);
			System.out.println(reduce+" asd "+map);
		}
		if(reduce%2==0)
			System.out.println("Given Array is Nice Array");
		else 
			System.out.println("Given Array is Not Nice Array");
	}
}