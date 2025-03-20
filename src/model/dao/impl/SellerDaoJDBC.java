package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				
				Department dep = instantiateDepartment(rs);
				Seller sel = instantiateSeller(rs, dep);
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

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sel = new Seller();
		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBaseSalary(rs.getDouble("BaseSalary"));
		sel.setBirth(rs.getDate("BirthDate"));
		sel.setDerpartment(dep);
		return sel;
	}



	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}



	@Override
	public List<Seller> findAll() {

		PreparedStatement st= null;
		ResultSet rs = null;
		
		try {
		
			st = conn.prepareStatement(
					" SELECT seller.*, department.Name as DepName "
					+ " FROM seller INNER JOIN department "
					+ " ON seller.DepartmentId = department.Id "
					+ " ORDER BY department.Id ");

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				// se o Dep ja existir ja pega aqui para poder não instanciar OUTRO DEP.
				Department dep = map.get(rs.getInt("DepartmentID"));
				
				
				if(dep == null) {
					// se o DEP ainda nao existir irá fazer a instancia aqui
					dep = instantiateDepartment(rs);					
					map.put(rs.getInt("DepartmentID"), dep);
				}
				
				Seller sel = instantiateSeller(rs, dep);
				list.add(sel);
				
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			// Não precisa fechar a Conexão pois ele pode servir para outra operação
		}
		
		
		
	}


	/**
	 * Aqui o seller precisa apontar para o mesmo objeto Department, pois se não vai criar um objeto para cada Department
	 */
	@Override
	public List<Seller> findBydepartment(Department department) {
		
		PreparedStatement st= null;
		ResultSet rs = null;
		
		try {
		
			st = conn.prepareStatement(
					" SELECT seller.*, department.Name as DepName "
					+ " FROM seller INNER JOIN department "
					+ " ON seller.DepartmentId = department.Id "
					+ " WHERE DepartmentId = ? "
					+ " ORDER BY Name ");

			st.setInt(1, department.getId());
			/**
			 * o result recebe o retorno em forma de tabela porem é necessario separar os objetos para cada objeto
			 * se vier um innner join de duas tabelas logo tera que serpara cada um para seu objeto
			 */
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			
			//é para evita colunas repetidas de departamento
			Map<Integer, Department> map = new HashMap<>();
			
			//como é uma lista tem que ser while
			while(rs.next()) {
				
				// se o Dep ja existir ja pega aqui para poder não instanciar OUTRO DEP.
				Department dep = map.get(rs.getInt("DepartmentID"));
				
				
				if(dep == null) {
					// se o DEP ainda nao existir irá fazer a instancia aqui
					dep = instantiateDepartment(rs);					
					map.put(rs.getInt("DepartmentID"), dep);
				}
				
				Seller sel = instantiateSeller(rs, dep);
				list.add(sel);
				
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			// Não precisa fechar a Conexão pois ele pode servir para outra operação
		}
		
		
	}

	
}
