package tracker.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Task {

    private int taskId;
    private String taskName;
    private String description;
    private int userId;
    private List<Subtask> subtaskList = new ArrayList<>();

    public Task() {
    }

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public Task(String taskName, String description) {
        this.taskName = taskName;
        this.description = description;
    }

    public Task(int taskId, String taskName, String description) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
    }

    public Task(int taskId, String taskName, String description, int userId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
    }

    public List<Subtask> getSubtaskList() {
        return subtaskList;
    }

    public void addSubtaskToList(Subtask subtask) {
        if (!subtaskList.stream().anyMatch(subtaskL -> subtaskL.getSubtaskId() == subtask.getSubtaskId()))
            subtaskList.add(subtask);
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && userId == task.userId && Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, description, userId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("TASKID= " + taskId)
                .add("TASKNAME= '" + taskName + "'")
                .add("DESCRIPTION= '" + description + "'")
                .add("USERID= " + userId)
                .add("\n")
                .toString();
    }
}
