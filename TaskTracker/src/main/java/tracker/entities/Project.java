package tracker.entities;

import java.util.Objects;
import java.util.StringJoiner;

public class Project {

    private int projectId;
    private String projectName;

    public Project() {
    }

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public Project(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return projectId == project.projectId && Objects.equals(projectName, project.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("PROJECTID= " + projectId)
                .add("PROJECTNAME= '" + projectName + "'")
                .add("\n")
                .toString();
    }
}
