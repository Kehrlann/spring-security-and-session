package wf.garnier.sessiondemo.user

import org.springframework.context.annotation.Configuration

@Configuration
class UserInitializer(userRepository: UserRepository) {

    init {
        val user = User(username = "user", clearTextPassword = "password")
        userRepository.save(user)
    }
}