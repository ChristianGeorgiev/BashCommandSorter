package com.georgiev.bashsorter.controller;

import com.georgiev.bashsorter.dto.TaskDTO;
import com.georgiev.bashsorter.service.TaskSortService;
import com.georgiev.bashsorter.template.JobRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Entry point of the application.
 * Uses {@link TaskSortService} to sort tasks and return the results.
 */
@RestController
public class JobRestController {

    private final TaskSortService taskSortService;

    @Autowired
    public JobRestController(TaskSortService taskSortService) {
        this.taskSortService = taskSortService;
    }

    @PostMapping(path = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TaskDTO>> sortTasksInJob(@RequestBody @Valid JobRestTemplate jobRestTemplate){
        return ResponseEntity.ok(taskSortService.sortTasks(jobRestTemplate));
    }

    @PostMapping(path = "/sort-as-bash", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sortTasksInJobAsBashScript(@RequestBody @Valid JobRestTemplate jobRestTemplate){
        return ResponseEntity.ok(taskSortService.sortTasksAsBashScript(jobRestTemplate));
    }
}
