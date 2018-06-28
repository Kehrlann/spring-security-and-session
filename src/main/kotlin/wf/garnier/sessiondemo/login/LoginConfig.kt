package wf.garnier.sessiondemo.login

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices
import org.springframework.session.web.http.HeaderHttpSessionIdResolver
import org.springframework.session.web.http.HttpSessionIdResolver

@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession
class LoginConfig(val userDetailsService: CustomUserDetailsService): WebSecurityConfigurerAdapter() {

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
                .loginProcessingUrl("/login")
                .successHandler { _, response, _ -> response.status = HttpStatus.OK.value() }
                .failureHandler { _, response, _ -> response.status = HttpStatus.UNAUTHORIZED.value() }
                .and()
                .exceptionHandling()
                .authenticationEntryPoint { _, response, _ -> response.status = HttpStatus.UNAUTHORIZED.value() }

                // only create a session when trying to access endpoints
                .and()
                .requestCache()
                .requestCache(NullRequestCache())

                // allow same-origin frame options, to be able to access the embedded h2 console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // session is valid forever (here this is an embedded DB, so whenever the app restarts
                // the session will be purged)
                .and()
                .rememberMe()
                .rememberMeServices(
                        SpringSessionRememberMeServices()
                                .apply {
                                    this.setAlwaysRemember(true)
                                    this.setValiditySeconds(Int.MAX_VALUE)
                                }
                )
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(BCryptPasswordEncoder(11))
    }

    // Use X-Auth-Token instead of SESSION cookie
    @Bean
    fun sessionResolver(): HttpSessionIdResolver = HeaderHttpSessionIdResolver.xAuthToken()
}