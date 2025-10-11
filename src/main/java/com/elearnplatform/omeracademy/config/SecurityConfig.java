package com.elearnplatform.omeracademy.config;

import com.elearnplatform.omeracademy.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
    private final JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http.authorizeHttpRequests(
                configure -> configure
                        // ========== Public Endpoints ==========
                        // Authentication - مفتوحة للجميع
                        .requestMatchers("/api/auth/**").permitAll()
                        // Grade Levels - القراءة مفتوحة للجميع
                        .requestMatchers(HttpMethod.GET, "/api/grade-levels/**").permitAll()
                        // Courses - القراءة مفتوحة للجميع
                        .requestMatchers(HttpMethod.GET, "/api/courses/**").permitAll()
                        // Lessons - القراءة مفتوحة للجميع
                        .requestMatchers(HttpMethod.GET, "/api/lessons/**").permitAll()
                        // Quizzes - القراءة مفتوحة (بدون الإجابات الصحيحة)
                        .requestMatchers(HttpMethod.GET, "/api/quizzes/**").authenticated()
                        // Error endpoints
                        //.requestMatchers("/error").permitAll()
                        // Swagger/OpenAPI (إذا تم تفعيله)
                        //.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // ========== Grade Level Management ==========
                        // إنشاء وتحديث وحذف
                        .requestMatchers(HttpMethod.POST, "/api/grade-levels/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/grade-levels/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/grade-levels/**").hasRole("ADMIN")
                        // ========== Course Management ==========
                        // إنشاء و تحديث وحذف مقررات
                        .requestMatchers(HttpMethod.POST, "/api/courses").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN")
                        // ========== Lesson Management ==========
                        // إنشاء وتحديث وحذف دروس
                        .requestMatchers(HttpMethod.POST, "/api/lessons/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/lessons/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/lessons/**").hasRole("ADMIN")
                        // ========== User Management ==========
                        // عرض وحذف جميع المستخدمين و الطلاب
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/students/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                        // ========== Default ==========
                        // جميع الطلبات الأخرى تتطلب مصادقة
                        .anyRequest().authenticated()

                )
                .csrf(AbstractHttpConfigurer::disable)
                //
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}