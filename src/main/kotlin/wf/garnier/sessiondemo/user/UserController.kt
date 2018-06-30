package wf.garnier.sessiondemo.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController(val userRepository: UserRepository){

    @GetMapping("/api/me")
    fun myDetails(principal: Principal) = userRepository.findByUsername(principal.name)
}
