package yapp.study.todolist.domain.test

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.study.todolist.common.const.BULK_SIZE

@RestController
@RequestMapping(value = ["/v1/tests"], produces = [MediaType.APPLICATION_JSON_VALUE])
class IndexTest(
    private val jdbcTemplate: JdbcTemplate,
    private val testRepository: TestRepository,
) {
    @Scheduled(
        initialDelay = 0,
        fixedDelay = 100000000000
    )
    fun initTest() {
        val tests = mutableListOf<Test>()
        for (i in 1..100000) {
            tests.add(Test(a = i.toLong(), b = i.toLong()))
        }

        val sql = """
            insert into test(b)
                values (?)
        """.trimIndent()
        jdbcTemplate.batchUpdate(sql, tests, BULK_SIZE) { preparedStatement, test ->
            preparedStatement.setLong(1, test.b)
        }
    }

    @GetMapping("/indexed")
    fun indexed(): Test? {
        return testRepository.findByIdOrNull(50000)
    }

    @GetMapping("/notIndexed")
    fun notIndexed(): Test? {
        return testRepository.findByB(50000)
    }
}

@Entity
class Test(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val a: Long,
    val b: Long
)

interface TestRepository : JpaRepository<Test, Long> {
    fun findByB(b: Long): Test?
    fun findAllByB(b: Long): List<Test>
}