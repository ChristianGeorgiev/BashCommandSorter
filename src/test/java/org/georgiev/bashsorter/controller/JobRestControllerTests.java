package org.georgiev.bashsorter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.georgiev.bashsorter.BashSorterApp;
import com.georgiev.bashsorter.dto.TaskDTO;
import com.georgiev.bashsorter.service.TaskSortService;
import com.georgiev.bashsorter.template.JobRestTemplate;
import com.georgiev.bashsorter.template.TaskRestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BashSorterApp.class)
@AutoConfigureMockMvc(addFilters = false)
public class JobRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskSortService taskSortService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void sortTasksInJob_withCorrectInput_shouldReturnOk() throws Exception {
        JobRestTemplate body = getTemplate();

        when(taskSortService.sortTasks(any())).thenReturn(List.of(new TaskDTO("a", "b")));

        mockMvc.perform(
                MockMvcRequestBuilders
                .post("/sort")
                .characterEncoding(StandardCharsets.UTF_8.name())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(body))
        ).andExpect(status().isOk());

    }

    @Test
    void sortTasksInJob_withMissingNameOfTask_shouldReturnBadRequest() throws Exception {
        JobRestTemplate body = new JobRestTemplate(
                List.of(
                        new TaskRestTemplate(null, "command", List.of("name1"))
                )
        );

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/sort")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(body))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void sortTasksInJobAsBashScript_withCorrectInput_shouldReturnOk() throws Exception {
        JobRestTemplate body = getTemplate();

        when(taskSortService.sortTasksAsBashScript(any())).thenReturn("some_bash_script");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/sort-as-bash")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(body))
        ).andExpect(status().isOk());
    }

    @Test
    void sortTasksInJobAsBashScript_withMissingNameOfTask_shouldReturnBadRequest() throws Exception {
        JobRestTemplate body = new JobRestTemplate(
                List.of(
                        new TaskRestTemplate(null, "command", List.of("name1"))
                )
        );

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/sort-as-bash")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(body))
        ).andExpect(status().isBadRequest());
    }

    private JobRestTemplate getTemplate(){
        return new JobRestTemplate(
                List.of(
                        new TaskRestTemplate("name", "command", List.of("name1"))
                )
        );
    }
}
