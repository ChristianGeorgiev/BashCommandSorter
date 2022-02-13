package com.georgiev.bashsorter.template;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

public class TaskRestTemplate {

    @NotEmpty(message = "Field 'name' is a mandatory property of a task.")
    private String name;

    @NotEmpty(message = "Field 'command' is a mandatory property of a task.")
    private String command;

    private List<String> requires;

    public TaskRestTemplate() {
    }

    public TaskRestTemplate(String name, String command, List<String> requires) {
        this.name = name;
        this.command = command;
        this.requires = requires;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Collection<String> getRequires() {
        return requires;
    }

    public void setRequires(List<String> requires) {
        this.requires = requires;
    }
}
