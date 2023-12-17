package yapp.study.todolist.domain.todo.controller

import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import yapp.study.todolist.common.extension.*
import yapp.study.todolist.domain.todo.dto.TodoDetailDto
import yapp.study.todolist.domain.todo.service.TodoService

@RestController
@RequestMapping(value = ["/v1/todos"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
)
class TodoController(
        private val todoService: TodoService
) {
    @PostMapping
    fun createTodo(@RequestBody todoDetailDto: TodoDetailDto) = todoService.createTodo(todoDetailDto).let {
        Unit.toTodoModel(it)
    }.wrapCreated()

    @GetMapping
    fun getTodos(@ParameterObject @PageableDefault(size = 10) pageable: Pageable) =
        todoService.getTodos(pageable)
            .toTodoListModel()
            .wrapOk()

    @PatchMapping("/{id}")
    fun updateTodo(
        @PathVariable("id") id: Long,
        @RequestBody todoDetailDto: TodoDetailDto,
    ) = todoService.updateTodo(id, todoDetailDto)
        .toTodoModel(id)
        .wrapCreated()

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable("id") id: Long) = todoService.deleteTodo(id)
        .wrapVoid()

    @PatchMapping("/{id}/done")
    fun updateTodoDone(
        @PathVariable("id") id: Long,
        @RequestParam("done") done: Boolean
    ) = todoService.updateDoneTodo(id, done)
        .toTodoModel(id)
        .wrapCreated()

    @GetMapping("/{id}/comments")
    fun getTodoWithComments(@PathVariable("id") id: Long) = todoService.getTodoWithComments(id)
        .toTodoModel(id)
        .wrapOk()
}