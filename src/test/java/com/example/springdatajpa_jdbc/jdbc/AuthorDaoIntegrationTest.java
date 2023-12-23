package com.example.springdatajpa_jdbc.jdbc;

import com.example.springdatajpa_jdbc.domain.*;
import com.example.springdatajpa_jdbc.dao.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
//@Import(AuthorDaoImpl.class)
@ComponentScan(basePackages = {"com.example.springdatajpa_jdbc"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

	@Autowired
	AuthorDao authorDao;

	@Test
	void testGetAuthor() {
		Author author = authorDao.getById(1L);
		assertThat(author).isNotNull();
	}

	@Test
	void testGetAuthorByName() {
		Author author  = authorDao.findAuthorByName("Craig", "Walls");
		assertThat(author).isNotNull();
	}

	@Test
	void testSaveNewAuthor() {
		Author atr = new Author();
		atr.setFirstName("AAA");
		atr.setLastName("BBB");

		Author savedAtr = authorDao.saveNewAuthor(atr);
		
		assertThat(authorDao).isNotNull();
	}

	@Test
	void testUpdateAuthor() {
		Author atr = new Author();
		atr.setFirstName("CCC");
		atr.setLastName("DDDD");

		Author savedAtr = authorDao.saveNewAuthor(atr);
		savedAtr.setLastName("EEEEE");
		Author updatedAtr = authorDao.updateAuthor(savedAtr);

		assertThat(updatedAtr.getLastName()).isEqualTo("EEEEE");
	}

	@Test
	void testDeleteAuthor() {
		Author atr = new Author();
		atr.setFirstName("CCdsdfsC");
		atr.setLastName("DDDDdsf");

		Author savedAtr = authorDao.saveNewAuthor(atr);

		authorDao.deleteAuthorById(savedAtr.getId());
		Author deleted = authorDao.getById(savedAtr.getId());

		assertThat(deleted).isNull();
	}
}
