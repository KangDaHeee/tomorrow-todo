package com.example.tomorrowtodo.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 클라이언트에게 돌려주는 값
public record TodoResponse(
        Long id,
        String title,
        boolean completed,
        LocalDate todoDate,
        LocalDateTime createdAt
) {
    // Entity -> 응답 객체 변환
    public static TodoResponse from(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.isCompleted(),
                todo.getTodoDate(),
                todo.getCreatedAt()
        );
    }
}