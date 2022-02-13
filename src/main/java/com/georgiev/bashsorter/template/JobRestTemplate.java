package com.georgiev.bashsorter.template;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class JobRestTemplate {

    @NotEmpty(message = "There should be at least 1 task in the job.")
    @Valid
    private List<TaskRestTemplate> tasks;

    public JobRestTemplate() {
    }

    public JobRestTemplate(List<TaskRestTemplate> tasks) {
        this.tasks = tasks;
    }

    public List<TaskRestTemplate> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskRestTemplate> tasks) {
        this.tasks = tasks;
    }
}
