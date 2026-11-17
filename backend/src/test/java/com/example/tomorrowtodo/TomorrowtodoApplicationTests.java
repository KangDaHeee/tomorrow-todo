package com.example.tomorrowtodo;

import com.example.tomorrowtodo.todo.Todo;
import com.example.tomorrowtodo.todo.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TomorrowtodoApplicationTests {

	@Autowired
	private TodoRepository todoRepository;

	@Test
	void 저장하고_날짜로_조회한다() {
		LocalDate today = LocalDate.now();
		todoRepository.save(new Todo("테스트 할 일", today));

		List<Todo> result = todoRepository.findByTodoDate(today);

		assertEquals(1, result.size());
		assertEquals("테스트 할 일", result.get(0).getTitle());
		assertFalse(result.get(0).isCompleted());
		assertNotNull(result.get(0).getCreatedAt());
	}
}
