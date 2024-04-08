package kinoap.app.securityConfig;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/filmShows/getAll").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/filmShows/getFilmShows").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/filmShows/getFilmShow").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/filmShows/addFilmShow").hasAuthority("ADMIN")
                        .requestMatchers("/filmShows/editFilmShow").hasAuthority("ADMIN")
                        .requestMatchers("/filmShows/deleteFilmShow").hasAuthority("ADMIN")
                        .requestMatchers("/movies/getAll").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/movies/getMovies").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/movies/getMovie").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/movies/addMovie").hasAuthority("ADMIN")
                        .requestMatchers("/movies/editMovie").hasAuthority("ADMIN")
                        .requestMatchers("/movies/deleteMovie").hasAuthority("ADMIN")
                        .requestMatchers("/reservation/add").hasAuthority("ADMIN")
                        .requestMatchers("/reservation/getAll").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/reservation/getReservation").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/reservation/edit").hasAuthority("ADMIN")
                        .requestMatchers("/reservation/delete").hasAuthority("ADMIN")
                        .requestMatchers("/rooms/getAll").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/rooms/getRoom").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/rooms/addRoom").hasAuthority("ADMIN")
                        .requestMatchers("/rooms/editRoom").hasAuthority("ADMIN")
                        .requestMatchers("/rooms/deleteRoom").hasAuthority("ADMIN")
                        .requestMatchers("/snacks/getAll").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/snacks/getSnack").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/snacks/addSnack").hasAuthority("ADMIN")
                        .requestMatchers("/snacks/editSnack").hasAuthority("ADMIN")
                        .requestMatchers("/snacks/deleteSnack").hasAuthority("ADMIN")
                        .requestMatchers("/tickets/getAll").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/tickets/getTicket").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/tickets/addTicket").hasAuthority("ADMIN")
                        .requestMatchers("/tickets/editTicket").hasAuthority("ADMIN")
                        .requestMatchers("/tickets/deleteTicket").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
        )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        return http.build();
    }
}











