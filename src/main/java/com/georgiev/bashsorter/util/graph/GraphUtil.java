package com.georgiev.bashsorter.util.graph;

import com.georgiev.bashsorter.template.JobRestTemplate;


/**
 * Utility class for working with graphs.
 */
public final class GraphUtil {

    private GraphUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Transforms the {@link JobRestTemplate} instance to match a typical graph structure.
     * @param jobRestTemplate rest template, which represents a job
     * @return custom implementation of a graph data structure.
     */
    public static JobGraph toGraph(JobRestTemplate jobRestTemplate){
        JobGraph jobGraph = new JobGraph();

        jobRestTemplate.getTasks().stream()
                .map(taskRestTemplate -> {
                    TaskNode taskNode = new TaskNode(taskRestTemplate.getName(), taskRestTemplate.getCommand());

                    if (taskRestTemplate.getRequires() != null){
                        taskRestTemplate.getRequires().forEach(taskNode::addRequirement);
                    }

                    return taskNode;
                })
                .forEach(jobGraph::addNode);

        return jobGraph;
    }
}
