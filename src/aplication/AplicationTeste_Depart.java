package aplication;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class AplicationTeste_Depart {

	public static void main(String[] args) {
	
		//Pegando a conexcão
		DepartmentDao depDao = DaoFactory.CreateDepartment();
	
		//insert
		System.out.println("=== Teste 1 : Insert Department");
		Department dep = new Department(null, "Game");
	//	depDao.insert(dep);// aqui eu acesso a interface e não a classe
		System.out.println("Novo Departamento add: "+ dep.getId() +" Nome  "+ dep.getName());
		
		System.out.println("--------------------------");
		//Buscar por ID
		System.out.println("=== Teste 2 : findByID Department");
		Department depfyndId = depDao.findById(2);
		System.out.println(depfyndId);
		
		
		System.out.println("--------------------------");

		
		
		//UPDATE
		System.out.println("=== Teste 3 : UPDATE Department");
		Department dep2 = depDao.findById(5);// peguei o departamento
		
		dep2.setName("CELULARES");
		//depDao.update(dep2);
		System.out.println("UPDATE COMPLETED");
		

		System.out.println("--------------------------");
		
		System.out.println("=== Teste 4 : findByALL Department");
		
		List<Department> listDep = new ArrayList<Department>();
		listDep = depDao.findAll();
		for (Department department : listDep) {
			System.out.println(department);
		}
		
		System.out.println("--------------------------");
		
		System.out.println("=== Teste 5 : Delete Department");
		
		depDao.deleteById(8);
			
	}

}
