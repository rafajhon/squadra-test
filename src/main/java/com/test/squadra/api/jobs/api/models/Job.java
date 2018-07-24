package com.test.squadra.api.jobs.api.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Job {
    @Id
    @NotNull
    public Long id;
    @NotBlank(message = "required name is unique")
    @Column(unique=true)
    public String name;

    public Boolean active;

    @OneToOne(cascade = CascadeType.PERSIST)
    public Job parentJob;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Task> tasks;

    public Integer weightJob;

    public Integer getWeightJob() {
        return weightJob;
    }

    public void setWeightJob(Integer weightJob) {
        this.weightJob = weightJob;
    }

    public Job() {
        this.tasks = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Job getParentJob() {
        return parentJob;
    }

    public void setParentJob(Job parentJob) {
        this.parentJob = parentJob;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
