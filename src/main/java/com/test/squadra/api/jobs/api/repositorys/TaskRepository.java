package com.test.squadra.api.jobs.api.repositorys;

import com.test.squadra.api.jobs.api.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
