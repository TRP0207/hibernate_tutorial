package com.practice.hibernate.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project_table")
public class Project {

    @Id
    @Column(name="project_id")
    private int projectId;

    @Column(name="project_name")
    private String projectName;

    @ManyToMany(mappedBy = "projects")
    private List<Developer> developers;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }
}
