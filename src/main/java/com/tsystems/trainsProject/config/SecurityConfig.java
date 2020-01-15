package com.tsystems.trainsProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@ComponentScan("com.tsystems.trainsProject")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService          jwtUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/websocket/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().antMatchers("/google").permitAll().antMatchers("/logout").permitAll().antMatchers("/home").permitAll().antMatchers("/").permitAll()
                .antMatchers("/restStations").permitAll().antMatchers("/trackIndicator").permitAll().antMatchers("/getTrackIndicator")
                .permitAll().antMatchers("/findStation").permitAll().antMatchers("/registration").permitAll().antMatchers("/schedule")
                .permitAll().antMatchers("/chooseTicket/{id}").hasAuthority("USER").antMatchers("/seeTicket/{id}").hasAuthority("USER")
                .antMatchers("/tickets").hasAuthority("USER").antMatchers("/updateSchedule/{id}").hasAuthority("WORKER")
                .antMatchers("/deleteSchedule/{id}").hasAuthority("WORKER").antMatchers("/createSchedule").hasAuthority("WORKER")
                .antMatchers("/seeTickets/{id}").hasAuthority("WORKER").antMatchers("/branches").hasAuthority("WORKER")
                .antMatchers("/createBranch").hasAuthority("WORKER").antMatchers("/updateBranch/{id}").hasAuthority("WORKER")
                .antMatchers("/deleteBranch/{id}").hasAuthority("WORKER").antMatchers("/detailedInf/{id}").hasAuthority("WORKER")
                .antMatchers("/stations").hasAuthority("WORKER").antMatchers("/createStation").hasAuthority("WORKER")
                .antMatchers("/updateStation/{id}").hasAuthority("WORKER").antMatchers("/deleteStation/{id}").hasAuthority("WORKER")
                .antMatchers("/trains").hasAuthority("WORKER").antMatchers("/createTrain").hasAuthority("WORKER")
                .antMatchers("/updateTrain/{id}").hasAuthority("WORKER").antMatchers("/deleteTrain/{id}").hasAuthority("WORKER").

                anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        httpSecurity.cors();
        httpSecurity.logout().permitAll().logoutUrl("/logout").logoutSuccessUrl("https://localhost:5001/").invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
