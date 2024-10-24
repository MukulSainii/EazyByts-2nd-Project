package com.RODS.service;


import com.RODS.configuration.Bundler;
import com.RODS.dto.LoginDTO;
import com.RODS.entity.Logins;
import com.RODS.entity.RoleType;
import com.RODS.repository.LoginsRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class LoginService {
    private final LoginsRepository loginsRepository;
    private final Bundler bundler;
    @Autowired
    public LoginService(LoginsRepository loginsRepository,
                        Bundler bundler) {
        this.loginsRepository = loginsRepository;
        this.bundler = bundler;
    }

    /**
     * For login user
     * Also use in creating manager if doesn't exists
     * @param login String login
     * @return Login object
     */
    public Optional<Logins> findByUserLogin (@NonNull String login){
        return loginsRepository.findByLogin(login);
    }

    /**
     * Registration
     * @param loginDTO parameters of new user
     * @param role role
     * @return Saved user
     * @throws IllegalArgumentException
     *          if login already exists in database
     */
    public Logins saveNewUser (@NonNull LoginDTO loginDTO, RoleType role) throws NoSuchElementException {
        try {
            return loginsRepository.save(Logins.builder()
                    .login(loginDTO.getLogin())
                    .email(loginDTO.getEmail())
                    .role(role)
                    .time(LocalDateTime.now())
                    .password(new BCryptPasswordEncoder().encode(loginDTO.getPassword())).build());
        }
        catch (Exception e) {
            String message = "error.signup.login.exists";
            log.warn(bundler.getLogMsg(message) + loginDTO.getLogin());
            throw new IllegalArgumentException(bundler.getMsg(message) + loginDTO.getLogin());
        }
    }

}
