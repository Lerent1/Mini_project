package org.example.mini_project.repository;

import org.example.mini_project.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.id = :id")
    Todo findTodoById(@Param("id") Long id);

}
