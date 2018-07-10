package com.test.squadra.api.jobs.api.models;

import org.hibernate.annotations.OnDelete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @NotNull
    public Long id;
    @NotBlank(message = "field name required")
    public String name;
    @NotNull(message = "field weight required")
    public Integer weight;
    @NotNull(message = "field completed required")
    public Boolean completed;
    @NotNull(message = "field createdAt required")
    public LocalDate createdAt;

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this.getId() == ((Task)o).getId()) {
            result = true;
        }
        return result;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
