package com.example.springdatajpa_jdbc.dao;

import com.example.springdatajpa_jdbc.domain.Author;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final DataSource ds;

	public AuthorDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public Author getById(Long id) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			cn = ds.getConnection();
			pst = cn.prepareStatement("select * from author where id = ?");
			pst.setLong(1, id);
			rs = pst.executeQuery();

			if(rs.next()) {
				Author author = new Author();
				author.setId(id);
				author.setFirstName(rs.getString("first_name"));
				author.setLastName(rs.getString("last_name"));
				return author;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pst != null) {
					pst.close();
				}
				
				if(cn != null) {
					cn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public Author findAuthorByName(String firstName, String lastName) {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
			
		try {
			cn = ds.getConnection();
			pst = cn.prepareStatement("select * from author where first_name = ? and last_name = ?");
			pst.setString(1, firstName);
			pst.setString(2, lastName);
			rs = pst.executeQuery();

			if(rs.next()) {
				Author author = new Author();
				author.setId(rs.getLong("id"));
				author.setFirstName(rs.getString("first_name"));
				author.setLastName(rs.getString("last_name"));
				return author;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pst != null) {
					pst.close();
				}
				
				if(cn != null) {
					cn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
			
		return null;
	}
}
