package com.project.allocation.model;

import java.util.HashSet;
import java.util.Set;

public class Staff extends User {

    /**
     * projects list proposed by staff.
     */
    private Set<Project> projects;

    /**
     * Staff constructor.
     */
    public Staff(String username, String password, String firstname, String lastname) {
        super(username, password, firstname, lastname);
        this.projects = new HashSet<>();
    }

    /**
     * get the projects proposed by the staff.
     * @return projects.
     */
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * staff propose project.
     * @param project Project.
     */
    public void proposeProject(Project project) {
        projects.add(project);
    }

    /**
     * staff view any students who have registered interest.
     * @param project Project.
     * @return user list
     */
    public Set<User> checkStudents(Project project) {
        // return project.getInterestStudents();
        throw new UnsupportedOperationException();
    }

    /**
     * staff can assign the student to the project.
     */
//    public void assignStudent(Student student, Project project) {
//        if (projects.contains(project) & project.getStatus()) {
//            // student 绑定 project
//            // project 设置 status
//        }
//
//    }
}
