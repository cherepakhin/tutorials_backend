package ru.perm.v.tutorials.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.lang.String.format
import java.nio.file.Files
import java.nio.file.Paths


@RestController
@RequestMapping("/log")
@Api(tags = ["Log controller"])
class LogCtrl {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    var basedir = Paths.get("").toAbsolutePath().toString()

    val path = "log/spring.log"

    @GetMapping("/")
    @ApiOperation("Getting log")
    fun getLog(): String {
        val current = File(basedir + File.separator + path)
        val currentDir = current.getAbsolutePath()
        logger.info(format("Log file %s", currentDir))
        return String(Files.readAllBytes(current.toPath()))
    }

}