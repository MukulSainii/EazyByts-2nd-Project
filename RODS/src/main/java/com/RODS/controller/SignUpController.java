package com.RODS.controller;


import com.RODS.configuration.Bundler;
import com.RODS.dto.LoginDTO;
import com.RODS.entity.RoleType;
import com.RODS.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final LoginService loginService;
    private final Bundler bundler;
    @Autowired
    public SignUpController(LoginService loginService,
                            Bundler bundler){
        this.loginService = loginService;
        this.bundler = bundler;
    }

    /**
     * Registration
     * @param loginDTO get all data, make validation
     * @param response send redirect to login page or errorMsg (validation or DB exceptions)
     */
    @PostMapping
    public void postSignUp(@Valid @RequestBody LoginDTO loginDTO,
                           HttpServletResponse response) {
        log.info(bundler.getLogMsg("create.user.log") + loginDTO.toString());
        try {
            loginService.saveNewUser(loginDTO, RoleType.ROLE_CUSTOMER);
            response.sendRedirect("login");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}

