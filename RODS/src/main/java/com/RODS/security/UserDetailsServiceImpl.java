package com.RODS.security;



import com.RODS.dto.LoginDTO;
import com.RODS.entity.Logins;
import com.RODS.entity.RoleType;
import com.RODS.service.LoginService;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final LoginService loginService;
    @Autowired
    private UserDetailsServiceImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostConstruct
    public void init() {
        if (!loginService.findByUserLogin("manager").isPresent()) {
            loginService.saveNewUser(LoginDTO.builder()
                    .login("manager")
                    .password("password")
                    .email("tro@gmail.com")
                    .build(), RoleType.ROLE_MANAGER);
        }
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Logins login = loginService.findByUserLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("Could not find user: " + username)
        );
        log.info(login.getLogin());

        return new UserDetailsImpl(login);
    }

}

