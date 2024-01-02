package yapp.study.todolist.domain.test

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/tests"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CacheTest (
    private val testRepository: TestRepository,
    private val cacheClass: CacheClass,
){


    @GetMapping("/cached")
    fun cached(): Test? {
        return cacheClass.findByIdCache()
    }

    @GetMapping("/notCached")
    fun notCached(): Test? {
        return testRepository.findByIdOrNull(50000)
    }
}

@Component
class CacheClass(
    private val testRepository: TestRepository,
){
    @Cacheable(cacheNames = ["test"], cacheManager = "redisCacheManager")
    fun findByIdCache(): Test? {
        return testRepository.findByIdOrNull(50000)
    }
}