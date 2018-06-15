package wf.garnier.sessiondemo.session

import javax.persistence.*

// Stolen from: /org/springframework/session/jdbc/schema-h2.sql

@Entity
data class SpringSession(
        @Id val primaryId: String = "",
        val sessionId: String = "",
        val creationTime: Long = 0L,
        val lastAccessTime: Long = 0L,
        val maxInactiveInterval: Int = 0,
        val expiryTime: Long = 0L,
        val principalName: String = ""
)
//CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
//CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
//CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

@Entity
data class SpringSessionAttributes(
    @Id val sessionPrimaryId: String = "",      // TODO : should be a composite PK, sessionPrimaryId + attributeName
    val attributeName: String = "",
    @Lob val attributeBytes: ByteArray
//CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
//CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
)
