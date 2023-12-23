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
				return getAuthorFromRS(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(rs, pst, cn);
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
				return getAuthorFromRS(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(rs, pst, cn);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
			
		return null;
	}

	@Override
	public Author saveNewAuthor(Author author) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
				
		try {
			cn = ds.getConnection();
			pst = cn.prepareStatement("insert into author(first_name, last_name) values(?, ?)");
			pst.setString(1, author.getFirstName());
			pst.setString(2, author.getLastName());
			pst.execute();

			Statement st = cn.createStatement();
			rs = st.executeQuery("select last_insert_id()");

			if(rs.next()) {
				Long savedId = rs.getLong(1);
				return this.getById(savedId);
			}

			st.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(rs, pst, cn);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
			
		return null;
	}

	@Override
    public Author updateAuthor(Author author) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
        	cn = ds.getConnection();
            pst = cn.prepareStatement("update author set first_name = ?, last_name = ? where author.id = ?");
            pst.setString(1, author.getFirstName());
            pst.setString(2, author.getLastName());
            pst.setLong(3, author.getId());
            pst.execute();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
            	closeAll(rs, pst, cn);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return this.getById(author.getId());
    }

	private Author getAuthorFromRS(ResultSet rs) throws SQLException {
		Author author = new Author();
		author.setId(rs.getLong("id"));
		author.setFirstName(rs.getString("first_name"));
		author.setLastName(rs.getString("last_name"));
		return author;
	}

	private void closeAll(ResultSet rs, PreparedStatement pst, Connection cn) throws SQLException {
		if(rs != null) {
			rs.close();
		}
		
		if(pst != null) {
			pst.close();
		}
		
		if(cn != null) {
			cn.close();
		}
	}

}
