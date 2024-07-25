package com.lld4.userauthenticationservice.service;

import com.lld4.userauthenticationservice.models.Session;
import com.lld4.userauthenticationservice.models.SessionStatus;
import com.lld4.userauthenticationservice.models.User;
import com.lld4.userauthenticationservice.respository.SessionRepo;
import com.lld4.userauthenticationservice.respository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionRepo sessionRepo;
    private final SecretKey secretKey;

    public AuthService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder, SessionRepo sessionRepo, SecretKey secretKey) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRepo = sessionRepo;
        this.secretKey = secretKey;
    }

    @Override
    public User resister(String email, String password) {

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            return null; // user already exist;
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepo.save(newUser);
    }

    @Override
    public Pair<User, MultiValueMap<String, String>> login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty() || !bCryptPasswordEncoder.matches(password, userOptional.get().getPassword())) {
            return null;
        }
        User user = userOptional.get();

        //Token Generation

        Map<String,Object> payloads = new HashMap<>();
        payloads.put("user_id__",user.getId());
        payloads.put("roles",user.getRole());
        payloads.put("email",user.getEmail());
        long nowInMillis = System.currentTimeMillis();
        payloads.put("iat",nowInMillis);
        payloads.put("exp",nowInMillis+1000000);

        //byte[] content = message.getBytes(StandardCharsets.UTF_8);
       // MacAlgorithm algorithm = Jwts.SIG.HS256;
       // SecretKey secretKey = algorithm.key().build();
        //String token = Jwts.builder().content(content).signWith(secretKey).compact();
        String token = Jwts.builder().claims(payloads).signWith(secretKey).compact();

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        sessionRepo.save(session);

        return new Pair<User,MultiValueMap<String,String>>(user,headers);
    }

    @Override
    public Boolean validateToken(Long userId, String token) {

        Optional<Session> optionalSession = sessionRepo.findByToken(token);
        if(optionalSession.isEmpty())
            return false;

        String storedToken = optionalSession.get().getToken();


        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long tokenExpiry = (Long)claims.get("exp");

        Long currentTime = System.currentTimeMillis();

        System.out.println(tokenExpiry);
        System.out.println(currentTime);

        if(currentTime > tokenExpiry) {
            System.out.println(
                    "Token is expired");
            //set state to expired in my DB
            return false;
        }


        return null;
    }
}
