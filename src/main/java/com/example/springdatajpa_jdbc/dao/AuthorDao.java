package com.example.springdatajpa_jdbc.dao;

import com.example.springdatajpa_jdbc.domain.Author;

public interface AuthorDao {
	Author getById(Long id);
}
