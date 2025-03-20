package aplication;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDao selerDao = DaoFactory.createSellerDao();
	
		System.out.println("=== Teste 1 : Seller FindById");
		Seller sel = selerDao.findById(3);
		System.out.println(sel);

		System.out.println("\n === Teste 2 : Seller FindByDepartment ");
		Department dep = new Department(2, null);
		List<Seller> list = selerDao.findBydepartment(dep);

		for (Seller seller : list) {
			System.out.println(seller);
		}

		System.out.println("\n === Teste 3 : Seller FindALL");
		List<Seller> listAll = selerDao.findAll();

		for (Seller sellerAll : listAll) {
			System.out.println(sellerAll);
		}

		System.out.println("\n === Teste 4 : Seller INSERT");

		Seller newSeller = new Seller(null, "Lorranz", "lorranz@gmail.com", new Date(), 4000.0, dep);
		selerDao.insert(newSeller);
		System.out.println("o novo seller tem o ID " + newSeller.getId());

		
		System.out.println("\n === Teste 5 : Seller UPDATE");

		sel = selerDao.findById(1);// fiz uma busca por um seller(vendedor)
		sel.setName("Marta coelho");
		selerDao.update(sel);// atutalizei
		System.out.println("UPDATE COMPLETED");
		

		System.out.println("\n === Teste 6 : Seller DELETE");
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o ID");
		int id  = sc.nextInt();
		selerDao.deleteById(id);
		System.out.println("DELETED");
		sc.close();
	}

}
