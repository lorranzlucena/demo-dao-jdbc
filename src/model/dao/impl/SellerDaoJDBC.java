package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoJDBC (Connection con) {
		this.conn = con;
	}
	
	
	
	@Override
	public void insert(Seller obj) {
		
	}

	@Override
	public void update(Seller obj) {
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement st= null;
		ResultSet rs = null;
		
		try {
		
			st = conn.prepareStatement(
					" SELECT seller.*, department.Name as DepName "
					+ " FROM seller INNER JOIN department "
					+ " ON seller.DepartmentId = department.Id "
					+ " WHERE seller.Id = ? ");

			st.setInt(1, id);
			/**
			 * o result recebe o retorno em forma de tabela porem é necessario separar os objetos para cada objeto
			 * se vier um innner join de duas tabelas logo tera que serpara cada um para seu objeto
			 */
			rs = st.executeQuery();
			// esse IF é pra testar se veio algum resultado, pois o rs começa na posição 0 onde não tem registro
			if(rs.next()) {
				//separando cada ojbeto para seu devido local
				
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				
				Seller sel = new Seller();
				sel.setId(rs.getInt("Id"));
				sel.setName(rs.getString("Name"));
				sel.setEmail(rs.getString("Email"));
				sel.setBaseSalary(rs.getDouble("BaseSalary"));
				sel.setBirth(rs.getDate("BirthDate"));
				sel.setDerpartment(dep);
				
				return sel;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			// Não precisa fechar a Conexão pois ele pode servir para outra operação
		}
		
		
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}

	
}
