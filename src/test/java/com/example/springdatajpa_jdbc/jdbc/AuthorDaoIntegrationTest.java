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
}
