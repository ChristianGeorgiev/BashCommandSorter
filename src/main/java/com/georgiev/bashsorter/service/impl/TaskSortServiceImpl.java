package com.georgiev.bashsorter.service.impl;

import com.georgiev.bashsorter.dto.TaskDTO;
import com.georgiev.bashsorter.service.TaskSortService;
import com.georgiev.bashsorter.template.JobRestTemplate;
import com.georgiev.bashsorter.util.graph.GraphUtil;
import com.georgiev.bashsorter.util.graph.JobGraph;
import com.georgiev.bashsorter.util.graph.TaskNode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskSortServiceImpl implements TaskSortService {

    public TaskSortServiceImpl() {
    }

    @Override
    public Collection<TaskDTO> sortTasks(JobRestTemplate jobRestTemplate) {
        if (jobRestTemplate.getTasks().isEmpty()) return Collections.emptyList();

        JobGraph jobGraph = GraphUtil.toGraph(jobRestTemplate);

        return topologicalSort(jobGraph);
    }

    @Override
    public String sortTasksAsBashScript(JobRestTemplate jobRestTemplate) {
        return sortTasks(jobRestTemplate).stream()
                .map(TaskDTO::getCommand)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private Collection<TaskDTO> topologicalSort(JobGraph jobGraph){

        List<TaskNode> orderedTasks = new ArrayList<>();

        jobGraph.getTaskNodes()
                .stream().filter(taskNode -> !taskNode.isVisited())
                .forEach(taskNode -> sort(jobGraph, taskNode, orderedTasks));

        return orderedTasks.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private void sort(JobGraph jobGraph, TaskNode taskNode, List<TaskNode> orderedTasks){
        taskNode.setVisited(true);

        taskNode.getRequires()
                .stream()
                .map(jobGraph::getNode)
                .filter(node -> !node.isVisited())
                .forEach(node -> sort(jobGraph, node, orderedTasks));

        orderedTasks.add(taskNode);
    }

    private TaskDTO toDTO(TaskNode taskNode){
        return new TaskDTO(
                taskNode.getName(),
                taskNode.getCommand()
        );
    }

}
