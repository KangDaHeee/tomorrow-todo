package com.example.tomorrowtodo.todo;


import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(nullable = false)
    private LocalDate todoDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Todo(){

    }

    public Todo(String title, LocalDate todoDate){
        this.title = title;
        this.todoDate = todoDate;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public LocalDate getTodoDate() { return todoDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void update(String title, LocalDate todoDate){
        this.title = title;
        if(todoDate != null){
            this.todoDate = todoDate;
        }
    }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    public void changeTodoDate(LocalDate todoDate) {
        this.todoDate = todoDate;
    }
}
