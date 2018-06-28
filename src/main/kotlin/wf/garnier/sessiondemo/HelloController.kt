package wf.garnier.sessiondemo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    // Open
    @GetMapping("/hello")
    fun greet() = "Hello world"


    // Authenticated
    @GetMapping("/api/hello")
    fun authorizedGreet() = "Well hello, there, World !"
}