package yapp.study.todolist.domain.viewer.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import yapp.study.todolist.domain.viewer.service.ViewerService

@RestController
@RequestMapping(value = ["/v1/viewers"],
    produces = [MediaType.APPLICATION_JSON_VALUE])
class ViewerController(
    private val viewerService: ViewerService
) {
    @PatchMapping
    fun updateCount(
                    @RequestParam("increase") increase: Boolean
    ) {
        viewerService.updateCount(increase)
    }

    @PatchMapping("/extends")
    fun extendTtl() {
        viewerService.extendTtl()
    }
}