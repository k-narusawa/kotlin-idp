package com.knarusawa.demo.idp.idpdemo.infrastructure.web.controller

import com.knarusawa.demo.idp.idpdemo.application.dto.ClientForm
import com.knarusawa.demo.idp.idpdemo.application.dto.UserForm
import com.knarusawa.demo.idp.idpdemo.application.mapper.ClientMapper
import com.knarusawa.demo.idp.idpdemo.application.mapper.UserMapper
import com.knarusawa.demo.idp.idpdemo.application.service.ClientService
import com.knarusawa.demo.idp.idpdemo.application.service.UserService
import com.knarusawa.demo.idp.idpdemo.domain.model.user.Role
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

@Controller
class HomeController(
  private val userService: UserService,
  private val clientService: ClientService
) {
  @GetMapping("/")
  fun getProfile(model: Model, principal: Principal): String {
    val user = userService.getByUserId(userId = principal.name)
    model.addAttribute("user", user)
    return "index"
  }

  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  fun getUserList(model: Model, principal: Principal): String {
    val users = userService.getAllUsers()
    model.addAttribute("users", users)
    return "user_list"
  }

  @GetMapping("/user/register")
  @PreAuthorize("hasRole('ADMIN')")
  fun registerUser(model: Model): String {
    model.addAttribute("userForm", UserForm("", "", listOf()))
    model.addAttribute("roleItems", Role.items())
    return "user_register"
  }

  @PostMapping("/user/register")
  @PreAuthorize("hasRole('ADMIN')")
  fun registerUser(@ModelAttribute userForm: UserForm): String {
    userService.registerUser(UserMapper.toUser(userForm))
    return "redirect:/users"
  }

  @GetMapping("/clients")
  @PreAuthorize("hasRole('ADMIN')")
  fun getClientLIst(model: Model): String {
    val clients = clientService.getClients()
    model.addAttribute("clients", clients)
    return "client_list"
  }

  @GetMapping("/client/register")
  @PreAuthorize("hasRole('ADMIN')")
  fun registerClient(model: Model): String {
    model.addAttribute(
      "clientForm", ClientForm(
        clientId = "",
        clientSecret = "",
        clientAuthenticationMethods = listOf(),
        clientAuthenticationGrantTypes = listOf(),
        redirectUrls = "",
        scopes = listOf()
      )
    )
    model.addAttribute("clientAuthenticationMethods", ClientForm.ClientAuthenticationMethodForm.items())
    model.addAttribute("clientAuthenticationGrantTypes", ClientForm.AuthorizationGrantTypeForm.items())
    model.addAttribute("oidcScopesForm", ClientForm.OidcScopesForm.items())
    return "client_register"
  }

  @PostMapping("/client/register")
  @PreAuthorize("hasRole('ADMIN')")
  fun registerClient(@ModelAttribute clientForm: ClientForm): String {
    clientService.registerClient(
      ClientMapper.toClient(clientForm)
    )
    return "redirect:/clients"
  }
}