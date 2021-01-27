package be.hvwebsites.beveiligd.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String MANAGER= "manager";
    private static final String HELPDESKMEDEWERKER = "helpdeskmedewerker";
    private static final String MAGAZIJNIER = "magazijnier";

    @Bean
    JdbcDaoImpl jdbcDaoImpl(DataSource dataSource){
        JdbcDaoImpl impl = new JdbcDaoImpl();
        impl.setDataSource(dataSource);
        impl.setUsersByUsernameQuery("select naam as username, paswoord as password, actief as enabled" +
                " from gebruikers where naam = ?");
        impl.setAuthoritiesByUsernameQuery("select gebruikers.naam as username, rollen.naam as authorities" +
                " from gebruikers inner join gebruikersrollen" +
                " on gebruikers.id = gebruikersrollen.gebruikerid" +
                " inner join rollen" +
                " on rollen.id = gebruikersrollen.rolid" +
                " where gebruikers.naam= ?");
        return impl;
    }
//    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        return new InMemoryUserDetailsManager(
//                User.builder().username("joe").password("{noop}theboss")
//                        .authorities(MANAGER).build(),
//                User.builder().username("averelle").password("{noop}hungry")
//                        .authorities(HELPDESKMEDEWERKER, MAGAZIJNIER).build()
//        );
//    }

    @Override
    public void configure(WebSecurity web){
       web.ignoring()
               .mvcMatchers("/images/**")
               .mvcMatchers("/css/**")
               .mvcMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin(login -> login.loginPage("/login"));
        http.authorizeRequests(requests -> requests
                .mvcMatchers("/", "/login").permitAll()
                .mvcMatchers("/**").authenticated());
        http.logout(logout -> logout.logoutSuccessUrl("/"));
    }
}
