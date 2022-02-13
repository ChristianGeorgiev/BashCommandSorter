package com.georgiev.bashsorter.util.graph;

import java.util.HashSet;
import java.util.Set;

public class TaskNode {
    private final String name;

    private final String command;

    private final Set<String> requires;

    private boolean isVisited;

    public TaskNode(String name, String command) {
        this.name = name;
        this.command = command;
        this.requires = new HashSet<>();
    }

    public void addRequirement(String requirement){
        this.requires.add(requirement);
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public Set<String> getRequires() {
        return requires;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
