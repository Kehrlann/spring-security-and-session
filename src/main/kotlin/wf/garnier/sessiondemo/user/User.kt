package wf.garnier.sessiondemo.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(@Id @GeneratedValue val id: Long = 0L,
           val username: String = "",
           val encryptedPassword: String = "") {

    constructor(username: String, clearTextPassword: String):
            this(username = username, encryptedPassword = BCryptPasswordEncoder(11).encode(clearTextPassword))
}