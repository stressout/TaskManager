package tracker.entities;

import java.util.Objects;
import java.util.StringJoiner;

public class Subtask {

    private int subtaskId;
    private int taskId;
    private String subtaskName;

    public Subtask() {
    }

    public Subtask(int taskId, String subtaskName) {
        this.taskId = taskId;
        this.subtaskName = subtaskName;
    }

    public Subtask(int subtaskId, int taskId, String subtaskName) {
        this.subtaskId = subtaskId;
        this.taskId = taskId;
        this.subtaskName = subtaskName;
    }

    public int getSubtaskId() {
        return subtaskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getSubtaskName() {
        return subtaskName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return subtaskId == subtask.subtaskId && taskId == subtask.taskId && Objects.equals(subtaskName, subtask.subtaskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtaskId, taskId, subtaskName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("SUBTASKID= " + subtaskId)
                .add("TASKID= " + taskId)
                .add("SUBTASKNAME= '" + subtaskName + "'")
                .add("\n")
                .toString();
    }
}
