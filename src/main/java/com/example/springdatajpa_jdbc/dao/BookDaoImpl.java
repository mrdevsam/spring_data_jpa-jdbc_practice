package com.example.springdatajpa_jdbc.dao;

import com.example.springdatajpa_jdbc.domain.Book;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {

	private final DataSource ds;
	private final AuthorDao authorDao;

	public BookDaoImpl(DataSource ds, AuthorDao authorDao) {
		this.ds = ds;
		this.authorDao = authorDao;
	}

	@Override
	public Book getById(Long id) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			cn = ds.getConnection();
			pst = cn.prepareStatement("select * from book where id = ?");
			pst.setLong(1, id);
			rs = pst.executeQuery();

			if(rs.next()) {
				return getBookFromRS(rs);
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
	public Book findBookByTitle(String title) {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
			
		try {
			cn = ds.getConnection();
			pst = cn.prepareStatement("select * from book where title = ?");
			pst.setString(1, title);
			rs = pst.executeQuery();

			if(rs.next()) {
				return getBookFromRS(rs);
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
	public Book saveNewBook(Book book) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
				
		try {
			cn = ds.getConnection();
			pst = cn.prepareStatement("insert into book(isbn, publisher, title, author_id) values(?, ?, ?, ?)");
			pst.setString(1, book.getIsbn());
			pst.setString(2, book.getPublisher());
			pst.setString(3, book.getTitle());
			
			if(book.getAuthorId() != null) {
				pst.setLong(4, book.getAuthorId().getId());
			} else {
				pst.setNull(4, -5);
			}
			
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
    public Book updateBook(Book book) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
        	cn = ds.getConnection();
            pst = cn.prepareStatement("update book set isbn = ?, publisher = ?, title = ?, author_id = ? where id = ?");
			pst.setString(1, book.getIsbn());
			pst.setString(2, book.getPublisher());
			pst.setString(3, book.getTitle());
			
			if(book.getAuthorId() != null) {
				pst.setLong(4, book.getAuthorId().getId());
			}
			pst.setLong(5, book.getId());
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

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
    	Connection cn = null;
    	PreparedStatement pst = null;

    	try {
    		cn = ds.getConnection();
    		pst = cn.prepareStatement("delete from book where id = ?");
    		pst.setLong(1, id);
    		pst.execute();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			closeAll(null, pst, cn);
    		} catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }

	private Book getBookFromRS(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setId(rs.getLong(1));
		book.setIsbn(rs.getString(2));
		book.setPublisher(rs.getString(3));
		book.setTitle(rs.getString(4));
		book.setAuthorId(authorDao.getById(rs.getLong(5)));
		return book;
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
