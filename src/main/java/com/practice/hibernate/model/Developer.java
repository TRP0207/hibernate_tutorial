package com.practice.hibernate.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "developer")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Developer {
    @Id
    @Column(name="dev_id")
    private int id;

    private DeveloperName name;

    @Column(name = "dev_tech")
    private String tech;

    @ManyToMany
    private List<Project> projects = new ArrayList<>();

    public int getId() {        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeveloperName getName() {
        return name;
    }

    public void setName(DeveloperName name) {
        this.name = name;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tech='" + tech + '\'' +
                '}';
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
