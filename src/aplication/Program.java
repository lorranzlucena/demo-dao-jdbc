package aplication;

import java.util.List;

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
		
	}

}
