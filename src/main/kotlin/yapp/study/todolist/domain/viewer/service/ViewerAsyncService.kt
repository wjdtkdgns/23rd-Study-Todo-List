package yapp.study.todolist.domain.viewer.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import yapp.study.todolist.common.adaptor.syncAdaptor
import yapp.study.todolist.domain.viewer.entity.ViewerCount
import yapp.study.todolist.domain.viewer.repository.ViewerCountRepository

@Service
class ViewerAsyncService(
    private val viewerCountRepository: ViewerCountRepository,
) {
    @Async
    fun asyncUpdateCount(increase: Boolean) {
        syncAdaptor {
            viewerCountRepository.findByIdOrNull(1)
                ?. let {
                    if (increase) {
                        it.increaseCount()
                    } else {
                        it.decreaseCount()
                    }
                    viewerCountRepository.save(it)
                }
                ?: viewerCountRepository.save(ViewerCount())
        }
    }
}
