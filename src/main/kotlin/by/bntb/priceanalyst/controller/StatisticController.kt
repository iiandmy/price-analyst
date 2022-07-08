package by.bntb.priceanalyst.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/main_data")
class StatisticController {
    @GetMapping("/last_update")
    fun getLastUpdateTime(): String {
        return "JAN 05 12:00"
    }
}