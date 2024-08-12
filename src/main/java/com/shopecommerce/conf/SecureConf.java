package com.shopecommerce.conf;

import com.shopecommerce.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecureConf extends WebSecurityConfigurerAdapter {

	//inject
	private final PasswordEncoder passwordEncoder;
//	private final UserRepo userRepo;

	public SecureConf (PasswordEncoder passwordEncoder, UserRepo userRepo) {
		this.passwordEncoder = passwordEncoder;
//		this.userRepo = userRepo;
	}
//	public void updateAdminPassword() {
//		String adminUsername = "testadmin";
//		String plainPassword = "123456";
//		String encodedPassword = passwordEncoder.encode(plainPassword);
//
//		User adminUser = userRepo.findByUsername(adminUsername);
//		if (adminUser != null) {
//			adminUser.setPassword(encodedPassword);
//			userRepo.save(adminUser);
//		}
//	}
	@Autowired private UserDetailsService userDetailsService;
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}



	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests() // thực hiện xác thực request ngưười dùng gửi lên.
			
			// không thực hiện xác thực đối với các ur/ này.
            .antMatchers("/css/**", "/js/**", "/img/**", "/vendor/**"
            		, "/font-awesome/**", "/summernote/**", "/files/**").permitAll()
            
//             thực hiện xác thực với các url kiểu ..../admin/....
            .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
            .antMatchers("/admin/**").authenticated()
            
            .and() // kết hợp với điều kiện.
            
            // khi click vào button logout thì không cần login.
            // khi click vào button này thì dữ liệu user trên session sẽ bị xoá.
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/home")
            .invalidateHttpSession(true) // xoá hết dữ liệu trên seesion
            .deleteCookies("JSESSIONID") // xoá hết dữ liệu trên cokies.
            .permitAll()
            
            .and() // kết hợp với điều kiện.
            
            .formLogin() // thực hiện xác thực qua form(username và password)
            .loginPage("/login") // trang login do mình thiết kế.
            .loginProcessingUrl("/perform_login") // link action for form post.
				.defaultSuccessUrl("/home", true)
            .defaultSuccessUrl("/home", true) // when user success authenticated then go to this url.
            .failureUrl("/login?login_error=true") // nhập username, password sai thì redirect về trang nào.
            .permitAll();
	}

//	@Bean
//    public PasswordEncoder passwordEncoder() {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
//		return encoder;
//    }


	
}
