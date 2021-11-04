package com.kokabmedia.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Interface that gives access to to CRUD methods for handling data in a database,
 * the JPARepository interface has methods that perform SQL queries and lets the 
 * application create and update data in the database, it takes an entity class 
 * and the primary key type of that entity as argument.
 * 
 * JpaRepository is an abstraction over EntityManager.
 */
@Repository
public interface TodoJpaRepository extends JpaRepository<Todo, Long>{
	
	/* 
	 * JPA custom method with special designed names lets Spring understands that we 
	 * want to retrieve a specific column from the database. 
	 */
	List<Todo> findByUsername(String username);
}
