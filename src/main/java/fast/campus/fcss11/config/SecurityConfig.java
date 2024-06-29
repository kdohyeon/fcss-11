package fast.campus.fcss11.config;

import fast.campus.fcss11.filter.LoggingFilter;
import fast.campus.fcss11.filter.RequestValidationFilter;
import fast.campus.fcss11.filter.StaticKeyAuthenticationFilter;
import fast.campus.fcss11.filter.TestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;
    private final TestFilter testFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new LoggingFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(staticKeyAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAt(testFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c.anyRequest().permitAll());

        return httpSecurity.build();
    }
}

