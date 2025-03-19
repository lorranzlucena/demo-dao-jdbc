package aplication;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department dep = new Department(1, "Books");
		Seller sel = new Seller(21, "Bob", "bobo@gmail.com", new Date(), 3000.00, dep);
		
		System.out.println(sel);
		
	}

	
}
