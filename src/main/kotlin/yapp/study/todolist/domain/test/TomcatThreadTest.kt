package yapp.study.todolist.domain.test

import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/tests"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TomcatThreadTest(
    private val asyncClass: AsyncClass,
) {
    @GetMapping("/tomcatThread")
    fun tomcatThreadTest(): String {
        Thread.sleep(100)
        return "tomcatThreadTest"
    }

    @GetMapping("/tomcatThread2")
    fun tomcatThreadTest2() {
        Thread.sleep(90)
        asyncClass.async()
    }
}

@Component
class AsyncClass {
    @Async(value = "testTaskExecutor")
    fun async() {
        Thread.sleep(30)
    }
}