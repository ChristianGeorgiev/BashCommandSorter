package org.georgiev.bashsorter.service;

import com.georgiev.bashsorter.dto.TaskDTO;
import com.georgiev.bashsorter.service.impl.TaskSortServiceImpl;
import com.georgiev.bashsorter.template.JobRestTemplate;
import com.georgiev.bashsorter.template.TaskRestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = TaskSortServiceImpl.class)
public class TaskSortServiceImplTests {

    private final TaskSortServiceImpl taskSortService = new TaskSortServiceImpl();

    @Test
    public void sortTasks_withTasksPresentInTheJob_shouldReturnSortedTasks(){

        JobRestTemplate jobRestTemplate = new JobRestTemplate();
        jobRestTemplate.setTasks(List.of(
                new TaskRestTemplate("task-1", "command-a", Collections.emptyList()),
                new TaskRestTemplate("task-2", "command-b", List.of("task-4")),
                new TaskRestTemplate("task-3", "command-c", List.of("task-1", "task-2")),
                new TaskRestTemplate("task-4", "command-d", Collections.emptyList())
        ));

        List<TaskDTO> sortedTasks = new ArrayList<>(taskSortService.sortTasks(jobRestTemplate));

        Assertions.assertTrue(sortedTasks.get(0).getName().equalsIgnoreCase("task-1"));
        Assertions.assertTrue(sortedTasks.get(1).getName().equalsIgnoreCase("task-4"));
        Assertions.assertTrue(sortedTasks.get(2).getName().equalsIgnoreCase("task-2"));
        Assertions.assertTrue(sortedTasks.get(3).getName().equalsIgnoreCase("task-3"));
    }

    @Test
    public void sortTasks_withNoTasksInTheJob_shouldReturnEmptyList(){
        JobRestTemplate jobRestTemplate = new JobRestTemplate();
        jobRestTemplate.setTasks(Collections.emptyList());

        Collection<TaskDTO> sortedTasks = taskSortService.sortTasks(jobRestTemplate);

        Assertions.assertTrue(sortedTasks.isEmpty());
    }

    @Test
    public void sortTasksAsBashScript_withTasksPresentInTheJob_shouldReturnSortedCommands(){
        JobRestTemplate jobRestTemplate = new JobRestTemplate();
        jobRestTemplate.setTasks(List.of(
                new TaskRestTemplate("task-1", "command-a", Collections.emptyList()),
                new TaskRestTemplate("task-2", "command-b", List.of("task-4")),
                new TaskRestTemplate("task-3", "command-c", List.of("task-1", "task-2")),
                new TaskRestTemplate("task-4", "command-d", Collections.emptyList())
        ));

        StringBuilder sbExpected = new StringBuilder();
        sbExpected.append("command-a").append(System.lineSeparator());
        sbExpected.append("command-d").append(System.lineSeparator());
        sbExpected.append("command-b").append(System.lineSeparator());
        sbExpected.append("command-c");

        String bashCommands = taskSortService.sortTasksAsBashScript(jobRestTemplate);

        Assertions.assertEquals(sbExpected.toString(), bashCommands);
    }

    @Test
    public void sortTasksAsBashScript_withNoTasksInTheJob_shouldReturnEmptyString(){
        JobRestTemplate jobRestTemplate = new JobRestTemplate();
        jobRestTemplate.setTasks(Collections.emptyList());

        String bashCommands = taskSortService.sortTasksAsBashScript(jobRestTemplate);

        Assertions.assertEquals("", bashCommands);
    }
}
