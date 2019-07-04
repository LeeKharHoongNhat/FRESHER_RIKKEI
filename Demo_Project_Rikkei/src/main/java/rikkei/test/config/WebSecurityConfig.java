package rikkei.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurityConfig
 *
 * @author NhatLKH
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /** userDetailsService **/
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * passwordEncoder
     *
     * @return mật khẩu đã được mã hóa BCrypt
     */
    // mã hóa pass bằng thuật toán BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * configureGlobal
     *
     * @param auth ( chứa thông tin idUser. pass. role của User)
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * configure
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
                .antMatchers("/login").permitAll()// trang mở cho mọi đối tượng
                .antMatchers("/admin/**").hasRole("ADMIN")// chỉ cho phép các user có rolename là Admin truy cập
                .antMatchers("/css/**",
                            "/fonts/**",
                            "/images/**",
                            "/js/**",
                            "/partials/**",
                            "/PSD/**",
                            "/scss/**",
                            "/vendors/**")
                .permitAll().anyRequest().access("hasAnyRole('MEMBER', 'ADMIN')")// chỉ cho phép các user có rolename là Member và ADMIN truy cập
                .and()
                .formLogin()
                    .loginProcessingUrl("/login") // Submit URL của trang login
                    .loginPage("/login") // đường dẫn đến file login
                    .usernameParameter("idUser")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/") // đường dẫn tới trang đăng nhập thành công
                    .failureUrl("/login?error") // đường dẫn khi trang đăng nhập thất bại
                .and()
                .logout()
                    .logoutUrl("/logout").permitAll()
                    .logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/403");// quay về trang 403 khi k có đủ quyền truy cập

    }

}
