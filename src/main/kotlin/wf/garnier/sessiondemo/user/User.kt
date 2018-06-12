package wf.garnier.sessiondemo.user

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(@Id @GeneratedValue val id: Long = 0L,
           val username: String = "",
           val password: String = "")