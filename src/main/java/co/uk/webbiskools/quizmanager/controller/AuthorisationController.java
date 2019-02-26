package co.uk.webbiskools.quizmanager.controller;

import co.uk.webbiskools.quizmanager.dao.Authorisation;
import co.uk.webbiskools.quizmanager.resource.AuthorisationToken;
import co.uk.webbiskools.quizmanager.service.AuthorisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/authorisation")
public class AuthorisationController {

    AuthorisationService authorisationService;

    @Autowired
    public AuthorisationController(AuthorisationService authorisationService) {
        this.authorisationService = authorisationService;
    }

    @GetMapping
    public ResponseEntity getUser(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "password") String password){

        Authorisation authorisationToken = authorisationService.authoriseUserLogin(username, password);
        if (authorisationToken.getAuthorisationToken() == AuthorisationToken.AUTHORISED){
            return ResponseEntity.ok(authorisationToken);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

}
