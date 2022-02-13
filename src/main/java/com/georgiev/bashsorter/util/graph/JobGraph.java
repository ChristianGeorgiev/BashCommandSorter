package com.georgiev.bashsorter.util.graph;

import java.util.ArrayList;
import java.util.List;

public class JobGraph {
    private final List<TaskNode> taskNodes;

    public JobGraph() {
        this.taskNodes = new ArrayList<>();
    }

    public void addNode(TaskNode taskNode){
        this.taskNodes.add(taskNode);
    }

    public void removeNode(TaskNode taskNode){
        this.taskNodes.remove(taskNode);
    }

    public TaskNode getNode(String taskName){
        return this.taskNodes.stream()
                .filter(taskNode -> taskNode.getName().equalsIgnoreCase(taskName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no such task with name: " + taskName));
    }

    public int getNodeCount(){
        return taskNodes.size();
    }

    public List<TaskNode> getTaskNodes() {
        return taskNodes;
    }
}
