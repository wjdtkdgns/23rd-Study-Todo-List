package yapp.study.todolist.common.extension

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

// 200
fun <T> T.wrapOk() = ResponseEntity.ok().body(this)
// 201
fun <T> T.wrapCreated() = ResponseEntity.status(HttpStatus.CREATED).body(this)
// 204
fun <T> T.wrapVoid() = ResponseEntity.status(HttpStatus.NO_CONTENT).build<T>()

fun <T> T.toTodoModel(id: Long) = this.toEntityModel()
    .add(Link.of("http://localhost:8080/api/v1/todos", "list"))
    .add(Link.of("http://localhost:8080/api/v1/todos/${id}/comment", "detail"))
    .add(Link.of("http://localhost:8080/api/v1/todos/${id}", "update"))
    .add(Link.of("http://localhost:8080/api/v1/todos/${id}/done", "update done"))
    .add(Link.of("http://localhost:8080/api/v1/todos/${id}", "delete"))

fun <T> T.toTodoListModel() = this.toEntityModel()
    .add(Link.of("http://localhost:8080/api/v1/todos", "list"))

fun <T> T.toCommentModel(id: Long) = this.toEntityModel()
    .add(Link.of("http://localhost:8080/api/v1/comments/${id}", "update"))
    .add(Link.of("http://localhost:8080/api/v1/comments/${id}", "delete"))

fun <T> T.toCategoryModel(id: Long) = this.toEntityModel()
    .add(Link.of("http://localhost:8080/api/v1/categories", "list"))
    .add(Link.of("http://localhost:8080/api/v1/categories/${id}", "detail"))
    .add(Link.of("http://localhost:8080/api/v1/categories/${id}", "update"))
    .add(Link.of("http://localhost:8080/api/v1/categories/${id}", "delete"))

fun <T> T.toCategoryListModel() = this.toEntityModel()
    .add(Link.of("http://localhost:8080/api/v1/categories", "list"))

private fun <T> T.toEntityModel() = EntityModel.of(this)
    .add(Link.of("http://localhost:8080/api/swagger-ui/index.html#/","docs"))
