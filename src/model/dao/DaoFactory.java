package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

/**
 * Classe responsavel por ter as opera��es de instancia dos DAOs
 */
public class DaoFactory {

	/**
	 * desse tipo faz com que o programa n�o conhe�a a implementa��o, apenas a
	 * inteface
	 * 
	 * @return
	 */
	public static SellerDao createSellerDao() {

		return new SellerDaoJDBC(DB.getConnection());
	}

}
