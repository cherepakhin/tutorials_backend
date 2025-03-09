package ru.perm.v.tutorials.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/echo")
@Api(tags = ["Echo for test"])
class EchoCtrl {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    private var counter = 0L
    private val MaxValueCounter = 100000L // for yandex_tank test

    @GetMapping("/{mes}")
    @ApiOperation("echo message")
    fun echoStr(
        @PathVariable("mes")
        @ApiParam(name = "mes", value = "any string") mes: String
    ): String {
        if (counter + 1 > MaxValueCounter) {
            counter = 0L
        } else {
            counter += 1L
        }
        logger.info("$counter GET $mes")
        return mes
    }
}