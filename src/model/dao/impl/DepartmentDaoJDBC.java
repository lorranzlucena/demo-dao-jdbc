package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			st = conn.prepareStatement(" UPDATE department " + " set Name = ? " + " where id = ? ");

			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			int row = st.executeUpdate();

			
		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;
		
		try {
			//se por acaso não tiver o departamento cai na exceção
			String dep_del = findById(id).getName();
			
			st = conn.prepareStatement(" delete from department where id = ? ");
			st.setInt(1, id);
			int row = st.executeUpdate();
			
			if(row == 0) {
				throw new DbException("ID não existe");
			}else {
				System.out.println("O Departmento : "+ dep_del +" Foi deletado com Sucesso");
			}

		} catch (SQLException n) {
			throw new DbException(n.getMessage());

		}catch ( NullPointerException e) {
			System.out.println("Deparmento não encontrado" ); 
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(" SELECT * FROM Department  WHERE Id = ? ");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;

			}
			return null;
		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Department> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM department");
			rs = st.executeQuery();

			List<Department> depResult = new ArrayList<>();

			while (rs.next()) {

				depResult.add(instantiateDepartment(rs));
			}

			return depResult;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

}
