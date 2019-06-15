package fi.haagahelia.mymcdtools.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fi.haagahelia.mymcdtools.service.UserServiceImpl;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(new BCryptPasswordEncoder());
        auth.setUserDetailsService(userServiceImpl);

        return auth;
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//	@Bean
//	public UserDetailsService userDetailsService() {
//	    return super.userDetailsService();
//	}
//	
//	@Autowired
//	private UserDetailsService userDetailsServiceImpl;
//	
//	@Autowired
//	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(userDetailsServiceImpl);
//	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("pages/index", "pages/index/**").hasAnyAuthority()
				.antMatchers("/pages/admin/**", "/admin/db/**", "/admin").hasAuthority("ADMIN")
				.antMatchers("/", "/registration", "/reg", "/activation/**", "/css/**", "/javascript/**", "/images/**", "/error").permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().ignoringAntMatchers("/admin/db/**")
				.and().headers().frameOptions().sameOrigin()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
			.logout()
			    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll();
	}	
	
//	 @Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
//	    }
//	
}