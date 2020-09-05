package clases;
import java.io.File;
import java.util.Scanner;

public class Main {

	private static String PATH ="./Caso1/doc/config.txt";
	
	
	public static void main(String[] args) {
		try {
			System.out.println("Va a comenzar");
			Scanner sc = new Scanner(new File(PATH));
			System.out.println(sc.hasNextLine());
			while(sc.hasNextLine())
				System.out.println(sc.nextLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
