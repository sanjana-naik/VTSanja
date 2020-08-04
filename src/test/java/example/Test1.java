package example;

import java.util.Random;

public class Test1 {

	public static void main(String[] args) {
		Random ranObj = new Random();
		int randata =ranObj.nextInt(1000);
         System.out.println(randata);
	}

}