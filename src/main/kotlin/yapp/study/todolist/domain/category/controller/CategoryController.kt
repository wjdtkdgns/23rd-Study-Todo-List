package yapp.study.todolist.domain.category.controller

import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import yapp.study.todolist.common.extension.*
import yapp.study.todolist.domain.category.dto.CategoryNameDto
import yapp.study.todolist.domain.category.service.CategoryService

@RestController
@RequestMapping(value = ["/v1/categories"],
//        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
class CategoryController(
        private val categoryService: CategoryService
) {
    @PostMapping
    fun createCategory(@RequestBody categoryNameDto: CategoryNameDto) =
        categoryService.createCategory(categoryNameDto).let {
            Unit.toCategoryModel(it)
        }.wrapCreated()


    @GetMapping
    fun getCategories(@ParameterObject @PageableDefault(size = 10) pageable: Pageable) =
        categoryService.getCategories(pageable)
            .toCategoryListModel()
            .wrapOk()

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable("id") id: Long) =
        categoryService.deleteCategory(id)
            .wrapVoid()

    @PatchMapping("/{id}")
    fun updateCategory(@PathVariable("id") id: Long,
                       @RequestBody categoryNameDto: CategoryNameDto) =
        categoryService.updateCategory(id, categoryNameDto)
            .toCategoryModel(id)
            .wrapCreated()

    @GetMapping("/todos")
    fun getCategoriesWithTodo(@ParameterObject @PageableDefault(size = 10) pageable: Pageable) =
        categoryService.getCategoriesWithTodo(pageable)
            .toCategoryListModel()
            .wrapOk()
}