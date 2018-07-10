package com.test.squadra.api.jobs.api.repositorys;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {
    Job findByName(String name);

    List<Job> findAllByOrderByWeightJobDesc();


    @Query("SELECT job " +
            "FROM Job job " +
            "INNER JOIN job.tasks tasks " +
            "WHERE tasks.id = ?1")
    Job findByIn_Tasks(Long taskDelete);
}
