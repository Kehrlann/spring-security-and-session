package wf.garnier.sessiondemo.login

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import wf.garnier.sessiondemo.user.UserRepository

@Component
class CustomUserDetailsService(val userRepo: UserRepository): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username)!!  // <-- will throw if user not found

        return User.withUsername(user.username)
                .password(user.encryptedPassword)
                .roles("USER")
                .build()
    }
}