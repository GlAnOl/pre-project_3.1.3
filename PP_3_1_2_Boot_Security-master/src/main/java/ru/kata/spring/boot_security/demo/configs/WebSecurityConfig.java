package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final SuccessUserHandler successUserHandler;


    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }


    //конфигурируем сам Spring Security
    //Конфигурируем авторизацию
    @Override
    protected void configure(HttpSecurity http) throws Exception {

         http.csrf().disable() // отключаем CSRF защиту
                 //настраиваем правила авторизации
                 .authorizeRequests() // теперь все запросы которые приходят в приложение будут проходить через авторизацию
                 .antMatchers("/admin/**").hasRole("ADMIN")

                 .antMatchers("/","/auth/login","/error", "/auth/registration").permitAll() //не авторизованных пользователей мы пускаем на эти странички
                 .anyRequest().hasAnyRole("USER", "ADMIN")// на все остальные страницы получают доступ пользователи с такими ролями
                 //.anyRequest().authenticated() на все другие странички пользователь попадет только если аутентифицирован
                 .and()
                 //Настраиваем логин пейдж
                 .formLogin()
                 .successHandler(successUserHandler)
                 .loginPage("/auth/login") //по этому адресу находится наша логин пейдж
                 .loginProcessingUrl("/process_login")//по этому адресу мы передаем данные с логин пейдж
                 //.defaultSuccessUrl("/hello", true) //после авторизации мы перекидываем пользователя на эту страницу
                 .failureUrl("/auth/login?error")//если авторизация не успешная перекидываем обратно на страницу логин с параметром error
                 .and()
                 .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");//настраиваем логаут



    }


    //Настраивает утентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    private final SuccessUserHandler successUserHandler;
//
//    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
//        this.successUserHandler = successUserHandler;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/index").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(successUserHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}