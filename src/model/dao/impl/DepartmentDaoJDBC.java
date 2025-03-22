package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Department;

public class DepartmentDaoJDBC implements model.dao.DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department " + " (Name) " + " VALUES(?) ",
					+Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());

			// vamos recuperar a chve que foi gerada para que possamos add ao objto

			int rows = st.executeUpdate();

			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				} else {
					throw new DbException("Nenhuma linha foi afetada");
				}
				DB.closeResultSet(rs);

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department obj) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
			" UPDATE department " 
			+ " set Name = ? " 
			+ " where id = ? ");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			st.executeUpdate();
			
		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Department findById(Integer id) {
			PreparedStatement st=null;
			ResultSet rs = null;
			
			try {
				
				st = conn.prepareStatement(
						" SELECT * FROM Department  WHERE Id = ? ");
				
				st.setInt(1, id);
				rs = st.executeQuery();
				
				if(rs.next()) {
					Department dep = instantiateDepartment(rs);
					return dep;
					
				}
				return null;
			} catch (SQLException e) {
				
				throw new DbException(e.getMessage());
				
			}finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
			}
	
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}
	
}
