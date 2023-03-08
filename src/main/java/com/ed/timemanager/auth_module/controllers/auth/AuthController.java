package com.ed.timemanager.auth_module.controllers.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ed.timemanager.auth_module.models.User;
import com.ed.timemanager.commons.components.authorization_interceptor.RequestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ed.timemanager.auth_module.controllers.auth.requests.LoginRequest;
import com.ed.timemanager.auth_module.controllers.auth.requests.RegisterRequest;
import com.ed.timemanager.auth_module.exceptions.AuthException;
import com.ed.timemanager.auth_module.services.AuthService;
import com.ed.timemanager.commons.controllers.AbstractControllerBase;


@RestController
@RequestMapping("/api")
public class AuthController extends AbstractControllerBase {
    //region Fields

    private final AuthService authService;

    @SuppressWarnings("java:S1170")
    @Value("${application.jwt-token-lifetime}")
    private final int jwtLifetime = 0;

    //endregion
    //region Ctor

    @Autowired
    public AuthController(RequestUser requestUser, AuthService authService) {

        super(requestUser);
        this.authService = authService;
    }

    //endregion
    //region Public

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        User user = authService.login(loginRequest);
        response.addCookie(this.createAuthCookie(user.getToken()));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(
        @Valid @RequestBody RegisterRequest registerRequest,
        HttpServletResponse response
    ) {
        User user = authService.register(registerRequest);
        response.addCookie(this.createAuthCookie(user.getToken()));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuthError(AuthException e) {

        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    //endregion
    //region Private

    private Cookie createAuthCookie(String token) {

        Cookie cookie = new Cookie("Authorization", token);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setDomain("edtmmngr.com");
        cookie.setMaxAge(this.jwtLifetime);
        cookie.setPath("/");

        return cookie;
    }

    //endregion
}
