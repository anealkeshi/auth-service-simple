package me.anilkc.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class UserController {

  @GetMapping("/user")
  public Principal getUser(Principal user) {
    return user;
  }

  @GetMapping("/admin")
  public Principal getAdmin(Principal admin) {
    return admin;
  }

}
