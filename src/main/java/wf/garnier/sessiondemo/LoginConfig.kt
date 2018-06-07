package wf.garnier.sessiondemo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession
class LoginConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .permitAll()
                .loginProcessingUrl("/login")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        val user = User.withUsername("user").password("{noop}password").roles("USER").build()
        auth.inMemoryAuthentication()
                .withUser(user)
    }


    @Bean
    fun dataSource(): EmbeddedDatabase {
        return EmbeddedDatabaseBuilder() // <2>
                .setType(EmbeddedDatabaseType.H2)
                .addScript("org/springframework/session/jdbc/schema-h2.sql").build()
    }

    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource) // <3>
    }
}

class Initializer : AbstractHttpSessionApplicationInitializer(WebSecurityConfigurerAdapter::class.java)