package com.example.springdatajpa_jdbc.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Data
public class Book{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String isbn;
	private String publisher;

	private Long authorId;
}
