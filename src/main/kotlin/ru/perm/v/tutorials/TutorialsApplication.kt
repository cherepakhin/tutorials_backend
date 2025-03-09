package ru.perm.v.tutorials

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class TutorialsApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<TutorialsApplication>(*args)
        }
    }
}

