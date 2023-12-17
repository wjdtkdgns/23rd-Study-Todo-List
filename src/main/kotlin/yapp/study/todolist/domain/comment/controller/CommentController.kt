package yapp.study.todolist.domain.comment.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import yapp.study.todolist.common.extension.toCommentModel
import yapp.study.todolist.common.extension.wrapCreated
import yapp.study.todolist.common.extension.wrapVoid
import yapp.study.todolist.domain.comment.dto.CommentContentDto
import yapp.study.todolist.domain.comment.dto.CommentDetailDto
import yapp.study.todolist.domain.comment.service.CommentService

@RestController
@RequestMapping(value = ["/v1/comments"],
        produces = [MediaType.APPLICATION_JSON_VALUE])
class CommentController(
        private val commentService: CommentService
) {
    @PostMapping
    fun createComment(@RequestBody commentDetailDto: CommentDetailDto) =
        commentService.createComment(commentDetailDto).let {
            Unit.toCommentModel(it)
        }.wrapCreated()


    @PatchMapping("/{id}")
    fun updateComment(@PathVariable("id") id: Long,
                      @RequestBody commentContentDto: CommentContentDto) =
        commentService.updateComment(id, commentContentDto)
            .toCommentModel(id)
            .wrapCreated()

    @DeleteMapping("/{id}")
    fun deleteComment(@PathVariable("id") id: Long) =
        commentService.deleteComment(id)
            .wrapVoid()
}