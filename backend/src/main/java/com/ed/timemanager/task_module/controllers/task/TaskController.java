package com.ed.timemanager.task_module.controllers.task;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ed.timemanager.commons.components.authorization_interceptor.Authorized;
import com.ed.timemanager.commons.controllers.AbstractControllerBase;
import com.ed.timemanager.commons.dto.PagedResponse;
import com.ed.timemanager.task_module.controllers.task.requests.CreateTaskRequest;
import com.ed.timemanager.task_module.controllers.task.responses.TaskResponse;
import com.ed.timemanager.task_module.services.TaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/task")
public class TaskController extends AbstractControllerBase {
    //region Fields

    private final TaskService taskService;

    //endregion
    //region Public

    //region Get

    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable("id") String taskId) {

        TaskResponse response = this.taskService.getTask(taskId, this.getUser());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Authorized
    @GetMapping("/all")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
     
        List<TaskResponse> response = this.taskService.getAllTasks(getUser());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Authorized
    @GetMapping
    public ResponseEntity<PagedResponse<TaskResponse>> getTaskBatch(
        @RequestParam(value = "page", required = true) int page,
        @RequestParam(value = "pageSize", required = true) int pageSize
    ) {    
        PagedResponse<TaskResponse> response = this.taskService.getTaskPage(page, pageSize, getUser());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //endregion
    //region Post

    @Authorized
    @PostMapping("/find")
    public ResponseEntity<TaskResponse> findTask(
        @RequestParam(value = "page", required = true) int page,
        @RequestParam(value = "pageSize", required = true) int pageSize,
        @RequestParam(value = "number", required = false) String taskNumber,
        @RequestParam(value = "startDate", required = false) String startDate,
        @RequestParam(value = "endDate", required = false) String endDate
    ) {

        return null;
    }

    @Authorized
    @PostMapping("/find/all")
    public ResponseEntity<TaskResponse> findTask(
        @RequestParam(value = "number", required = false) String taskNumber,
        @RequestParam(value = "startDate", required = false) String startDate,
        @RequestParam(value = "endDate", required = false) String endDate
    ) {

        return null;
    }

    //endregion
    //region Put

    @Authorized
    @PutMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {

        TaskResponse response = this.taskService.createTask(request, this.getUser());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //endregion

    //endregion
}