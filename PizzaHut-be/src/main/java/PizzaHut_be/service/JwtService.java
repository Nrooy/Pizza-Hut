package PizzaHut_be.service;

import lombok.CustomLog;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@CustomLog
@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String jwtSecret;

    @Value("${spring.jwt.expire}")
    private int jwtExpirationMs;


}
