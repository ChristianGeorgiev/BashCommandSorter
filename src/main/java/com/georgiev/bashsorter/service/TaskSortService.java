package com.georgiev.bashsorter.service;

import com.georgiev.bashsorter.dto.TaskDTO;
import com.georgiev.bashsorter.template.JobRestTemplate;

import java.util.Collection;

/**
 * This service is responsible for sorting the corresponding tasks in a job.
 */
public interface TaskSortService {
    /**
     * Sorts the tasks in the job, based on their requirements.
     * Uses topological sort algorithm to efficiently sort the tasks
     * @param jobRestTemplate rest template, which represents a job
     * @return Collection with sorted tasks, represented as {@link TaskDTO}
     */
    Collection<TaskDTO> sortTasks(JobRestTemplate jobRestTemplate);

    /**
     * Sorts the tasks in the job, based on their requirements.
     * Extracts the commands from the tasks and formats them as a single String.
     * @param jobRestTemplate rest template, which represents a job
     * @return String representation of the commands in the tasks; ready-for-use bash script
     */
    String sortTasksAsBashScript(JobRestTemplate jobRestTemplate);
}
