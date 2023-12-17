package yapp.study.todolist.domain.viewer.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import yapp.study.todolist.common.adaptor.syncAdaptor
import yapp.study.todolist.domain.viewer.entity.ViewerCount
import yapp.study.todolist.domain.viewer.repository.ViewerCountRepository

@Service
class ViewerService(
    private val viewerCountRepository: ViewerCountRepository,
) {
    fun updateCount(increase: Boolean) {
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

    fun extendTtl() {
        syncAdaptor {
            viewerCountRepository.findByIdOrNull(1)
                ?.let {
                    it.extendTtl()
                    viewerCountRepository.save(it)
                }
        }
    }
}
