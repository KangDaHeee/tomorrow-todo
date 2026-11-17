package com.example.tomorrowtodo.todo;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 날짜별 조회: GET /api/todos?date=2026-11-17
    @GetMapping
    public List<TodoResponse> list(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return todoService.findByDate(date);
    }

    // 등록: POST /api/todos
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse create(@RequestBody TodoRequest request) {
        return todoService.create(request);
    }

    // 수정: PUT /api/todos/{id}
    @PutMapping("/{id}")
    public TodoResponse update(@PathVariable Long id, @RequestBody TodoRequest request) {
        return todoService.update(id, request);
    }

    // 완료 토글: PATCH /api/todos/{id}/done
    @PatchMapping("/{id}/done")
    public TodoResponse toggleDone(@PathVariable Long id) {
        return todoService.toggleCompleted(id);
    }

    // 내일로 미루기: PATCH /api/todos/{id}/postpone
    @PatchMapping("/{id}/postpone")
    public TodoResponse postpone(@PathVariable Long id) {
        return todoService.postponeToTomorrow(id);
    }

    // 삭제: DELETE /api/todos/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }
}