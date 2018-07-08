package com.test.squadra.api.jobs.api.repositorys;

import com.test.squadra.api.jobs.api.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {
    Job findByName(String name);

    List<Job> findAllByOrderByWeightJobDesc();

}
