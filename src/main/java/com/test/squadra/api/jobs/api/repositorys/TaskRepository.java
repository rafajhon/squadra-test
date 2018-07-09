package com.test.squadra.api.jobs.api.repositorys;

import com.test.squadra.api.jobs.api.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedAt(LocalDate localDate);
}
