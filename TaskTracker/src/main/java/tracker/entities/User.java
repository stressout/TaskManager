package tracker.entities;

import java.util.Objects;
import java.util.StringJoiner;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private int projectId;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(int userId, String firstName, String lastName, int projectId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getProjectId() {
        return projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && projectId == user.projectId && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, projectId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("USERID= " + userId)
                .add("FIRSTNAME= '" + firstName + "'")
                .add("LASTNAME= '" + lastName + "'")
                .add("PROJECTID= " + projectId)
                .add("\n")
                .toString();
    }
}
