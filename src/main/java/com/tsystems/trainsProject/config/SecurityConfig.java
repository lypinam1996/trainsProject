package com.tsystems.trainsProject.config;

import com.tsystems.trainsProject.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@ComponentScan("com.tsystems.trainsProject")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getShaPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/restStations").permitAll()
                .antMatchers("/websocket").permitAll()
                .antMatchers("/topic/greetings").permitAll()
                .antMatchers("/greeting").permitAll()
                .antMatchers("/findStation").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/schedule").permitAll()
                .antMatchers("/chooseTicket/{id}").hasAuthority("USER")
                .antMatchers("/seeTicket/{id}").hasAuthority("USER")
                .antMatchers("/tickets").hasAuthority("USER")
                .antMatchers("/updateSchedule/{id}").hasAuthority("WORKER")
                .antMatchers("/deleteSchedule/{id}").hasAuthority("WORKER")
                .antMatchers("/createSchedule").hasAuthority("WORKER")
                .antMatchers("/seeTickets/{id}").hasAuthority("WORKER")
                .antMatchers("/branches").hasAuthority("WORKER")
                .antMatchers("/createBranch").hasAuthority("WORKER")
                .antMatchers("/updateBranch/{id}").hasAuthority("WORKER")
                .antMatchers("/deleteBranch/{id}").hasAuthority("WORKER")
                .antMatchers("/detailedInf/{id}").hasAuthority("WORKER")
                .antMatchers("/stations").hasAuthority("WORKER")
                .antMatchers("/createStation").hasAuthority("WORKER")
                .antMatchers("/updateStation/{id}").hasAuthority("WORKER")
                .antMatchers("/deleteStation/{id}").hasAuthority("WORKER")
                .antMatchers("/trains").hasAuthority("WORKER")
                .antMatchers("/createTrain").hasAuthority("WORKER")
                .antMatchers("/updateTrain/{id}").hasAuthority("WORKER")
                .antMatchers("/deleteTrain/{id}").hasAuthority("WORKER")
                .anyRequest()
                .authenticated().and().csrf().disable()
        .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll()
                .and()
       .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");;
    }


    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**");
    }
    @Bean
    public BCryptPasswordEncoder getShaPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}