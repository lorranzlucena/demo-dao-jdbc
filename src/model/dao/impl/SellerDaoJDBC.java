package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentID) "
					+ "VALUES(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);//retornar o ID
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirth().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDerpartment().getId());
		
		
			int rowsAfected = st.executeUpdate();
			
			if(rowsAfected > 0) {
				//é usado para recuperar chaves geradas automaticamente pelo banco de dados após a execução de uma instrução SQL de inserção
				ResultSet rs = st.getGeneratedKeys();
				
				if (rs.next()) {
					int id = rs.getInt(1);// pegou id gerado
					//como ja temos o ID agora vou colocar ele no OBjeto
					obj.setId(id);
					
				}else {
					// se alguma coisa acontec de errado e não inseriu 
					throw new DbException("Nenhuma linha foi afetada");
				}
				DB.closeResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		
		}finally {
			DB.closeStatement(st);
			
		}
		
	}

	@Override
	public void update(Seller obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					  " UPDATE seller"
					+ " SET Name = ?, Email = ?,BirthDate = ? ,BaseSalary = ?, DepartmentId = ?"
					+ " WHERE id = ? ");
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirth().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDerpartment().getId());
			st.setInt(6, obj.getId());
			
		int rows =st.executeUpdate();
		
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM seller WHERE id = ? ");

			st.setInt(1, id);
			int rows = st.executeUpdate();
			if (rows == 0) {
				throw new DbException("ID não existe");
			}
			
		} catch (SQLException e) {
			throw  new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
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
