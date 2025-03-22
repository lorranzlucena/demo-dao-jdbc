package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

/**
 * Classe responsavel por ter as opera��es de instancia dos DAOs
 */
public class DaoFactory {

	/**
	 * desse tipo faz com que o programa n�o conhe�a a implementa��o, apenas a
	 * inteface
	 *  Responsavel por iniciar a conex�o com o banco
	 *  Se mudar de MySQL para PostgreSQL, por exemplo, basta alterar a DaoFactory
	 */
	
	public static SellerDao createSellerDao() {

		return new SellerDaoJDBC(DB.getConnection());
	}

	
	public static DepartmentDao CreateDepartment() {
		
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
