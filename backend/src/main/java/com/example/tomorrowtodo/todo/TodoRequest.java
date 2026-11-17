package com.example.tomorrowtodo.todo;

import java.time.LocalDate;

// 클라이언트가 보내는 값 (등록/수정 공용)
public record TodoRequest(String title, LocalDate todoDate) {
}