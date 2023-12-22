package com.example.springdatajpa_jdbc.dao;

import com.example.springdatajpa_jdbc.domain.Author;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final DataSource ds;

	public AuthorDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public Author getById(Long id) {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			cn = ds.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery("select * from author where id = " + id);

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
				
				if(st != null) {
					st.close();
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
