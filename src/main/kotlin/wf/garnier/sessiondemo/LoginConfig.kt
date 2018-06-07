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
import org.springframework.security.config.http.SessionCreationPolicy
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

        http
                // Add authentication for everything under /api
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()

                // disable CSRF, which only useful when you're doing web stuff
                .and()
                .csrf().disable()

                // enable form-login, to be able to call /login with username and password and get a cookie
                .formLogin()
                .permitAll()
                .loginProcessingUrl("/login")
                .permitAll()

                // only create a session when trying to access endpoints
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                // allow same-origin frame options, to be able to access the embedded h2 console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
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