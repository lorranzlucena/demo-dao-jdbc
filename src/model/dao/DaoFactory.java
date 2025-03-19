package model.dao;

import model.dao.impl.SellerDaoJDBC;

/**
 * Classe responsavel por ter as operações de instancia dos DAOs
 */
public class DaoFactory {

	/**
	 * desse tipo faz com que o programa não conheça a implementação, apenas a inteface
	 * @return
	 */
	public static SellerDao createSellerDao() {
		
		return new SellerDaoJDBC();
	}
	
	
	
}
