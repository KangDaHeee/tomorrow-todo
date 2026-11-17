package com.example.tomorrowtodo.todo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)   // 기본은 읽기 전용, 쓰기 메서드에만 @Transactional을 따로 붙임
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // 날짜별 조회 (날짜를 안 주면 오늘)
    public List<TodoResponse> findByDate(LocalDate date) {
        LocalDate target = (date != null) ? date : LocalDate.now();
        return todoRepository.findByTodoDate(target).stream()
                .map(TodoResponse::from)
                .toList();
    }

    // 등록 (날짜를 안 주면 오늘)
    @Transactional
    public TodoResponse create(TodoRequest request) {
        validateTitle(request.title());
        LocalDate date = (request.todoDate() != null) ? request.todoDate() : LocalDate.now();
        Todo saved = todoRepository.save(new Todo(request.title().trim(), date));
        return TodoResponse.from(saved);
    }

    // 수정
    @Transactional
    public TodoResponse update(Long id, TodoRequest request) {
        validateTitle(request.title());
        Todo todo = getTodo(id);
        todo.update(request.title().trim(), request.todoDate());
        return TodoResponse.from(todo);
    }

    // 완료 토글
    @Transactional
    public TodoResponse toggleCompleted(Long id) {
        Todo todo = getTodo(id);
        todo.toggleCompleted();
        return TodoResponse.from(todo);
    }

    // 내일로 미루기 (오늘 기준 내일 날짜로 변경)
    @Transactional
    public TodoResponse postponeToTomorrow(Long id) {
        Todo todo = getTodo(id);
        todo.changeTodoDate(LocalDate.now().plusDays(1));
        return TodoResponse.from(todo);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        todoRepository.delete(getTodo(id));
    }

    private Todo getTodo(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "할 일을 찾을 수 없습니다. id=" + id));
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 비워둘 수 없습니다.");
        }
    }
}