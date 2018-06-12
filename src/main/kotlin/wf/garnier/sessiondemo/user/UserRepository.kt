package wf.garnier.sessiondemo.user

import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
}