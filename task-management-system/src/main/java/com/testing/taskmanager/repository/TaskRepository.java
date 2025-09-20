package com.testing.taskmanager.repository;

import com.testing.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Task entities.
 * Provides data access operations for tasks.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
