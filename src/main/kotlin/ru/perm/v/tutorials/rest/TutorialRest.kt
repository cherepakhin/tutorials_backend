package ru.perm.v.tutorials.rest

import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.service.TutorialService
import java.lang.String.format
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolation
import javax.validation.Validation


@RestController
@RequestMapping("/tutorial")
class TutorialRest(val tutorialService: TutorialService) {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    val validator: javax.validation.Validator = Validation.buildDefaultValidatorFactory().validator

    @GetMapping("/echo/{mes}")
    @ApiOperation("Simple echo test")
    fun echoMessage(
        @Parameter(
            description = "Any string. will be returned in response."
        )
        @PathVariable mes: String,
    ): String {
        logger.info(format("echo %s", mes))
        return mes
    }

    /**
     * GET http :8980/api/tutorial/byGetParameter?name=AAAAAA
     * GET http :8980/api/tutorial/byGetParameter?name="Name Tutorial4"
     * Search by name: Name Tutorial4
     */
    @GetMapping("/by")
    fun byParameter(request: HttpServletRequest): List<TutorialDTO> {
        val searchName = request.getParameter("name")
        logger.info("Search by name: $searchName")
        return tutorialService.findByNameContainingOrderByName(searchName)
    }

    @PostMapping
    @ApiOperation("Create Tutorial from DTO")
    fun create(
        @Parameter(
            description = "DTO of Tutorial."
        )
        @RequestBody tutorialDTO: TutorialDTO,
    ): TutorialDTO {
        validate(tutorialDTO)
        return tutorialService.create(tutorialDTO)
    }

    @GetMapping("/{n}")
    @ApiOperation("Get Tutorial by N")
    @Cacheable("tutorials")
    fun getByN(
        @Parameter(
            description = "N(ID) Tutorial."
        )
        @PathVariable
        n: Long,
    ): TutorialDTO {
        return tutorialService.getByN(n)
    }

    @GetMapping("/")
    @ApiOperation("Get all tutorials")
    @Cacheable("tutorials")
    fun getAll(): List<TutorialDTO> {
        return tutorialService.getAll()
    }

    @PostMapping(path = ["/{n}"], consumes = ["application/json"], produces = ["application/json"])
//    @CacheEvict(value = ["tutorials", "allTutorialDTO"], allEntries = true)
    @ApiOperation("Update Tutorial")
    fun update(
        @PathVariable
        n: Long,
        @Parameter(
            description = "Tutorial."
        )
        @RequestBody
        tutorial: TutorialDTO,
    ): TutorialDTO {
        logger.info("UPDATE:$tutorial")
        validate(tutorial)
        return tutorialService.update(tutorial)
    }

    @DeleteMapping("/{n}")
//    @CacheEvict(value = ["tutorials"], key = "#n") // no error, variant 1
//    @CacheEvict(cacheNames = ["tutorials"], key = "#n") // no error, variant 2
    fun deleteById(
        @Parameter(
            description = "N(ID) Tutorial."
        )
        @PathVariable
        n: Long,
    ): String {
        if (tutorialService.existById(n)) {
            tutorialService.delete(n)
            return "OK"
        } else {
            throw Exception("Tutorial with ID=$n not found.")
        }
    }

    fun validate(tutorialDTO: TutorialDTO) {
        val violations: MutableSet<ConstraintViolation<TutorialDTO>> =
            validator.validate(tutorialDTO)

        if (violations.isNotEmpty()) {
            var messageError = ""
// OLD STYLE
//            for(violation in violations) {
//                messageError = messageError.plus(violation.message + "\n")
//            }

// USED STREAM
            violations.forEach { violation ->
                messageError = messageError.plus(violation.message + "\n")
            }
            throw Exception("$tutorialDTO has errors: $messageError")
        }
    }

}