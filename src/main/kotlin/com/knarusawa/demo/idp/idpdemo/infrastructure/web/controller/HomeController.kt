package com.knarusawa.demo.idp.idpdemo.infrastructure.web.controller

import com.knarusawa.demo.idp.idpdemo.application.mapper.ClientMapper
import com.knarusawa.demo.idp.idpdemo.application.service.ClientService
import com.knarusawa.demo.idp.idpdemo.application.service.UserRoleService
import com.knarusawa.demo.idp.idpdemo.application.service.user.getAll.UserGetAllService
import com.knarusawa.demo.idp.idpdemo.application.service.user.getById.UserGetByUserIdService
import com.knarusawa.demo.idp.idpdemo.application.service.user.register.UserRegisterCommand
import com.knarusawa.demo.idp.idpdemo.application.service.user.register.UserRegisterService
import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.Role
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.ClientForm
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserForm
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

@Controller
class HomeController(
    private val userGetByUserIdService: UserGetByUserIdService,
    private val userRegisterService: UserRegisterService,
    private val userGetAllService: UserGetAllService,
    private val userRoleService: UserRoleService,
    private val clientService: ClientService
) {
  @GetMapping("/")
  fun getProfile(model: Model, principal: Principal): String {
    val user = userGetByUserIdService.execute(userId = principal.name)
    val userRole = userRoleService.getUserRole(userId = principal.name)
    model.addAttribute("user", user)
    model.addAttribute("userRole", userRole)
    return "index"
  }

  @GetMapping("/user/list")
  @PreAuthorize("hasRole('ADMIN')")
  fun getUserList(model: Model, principal: Principal): String {
    val users = userGetAllService.execute()
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
    val command = UserRegisterCommand(
        loginId = userForm.loginId,
        password = userForm.password,
        roles = userForm.roles
    )
    userRegisterService.execute(command)
    return "redirect:/user/list"
  }

  @GetMapping("/client/list")
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
    model.addAttribute(
        "clientAuthenticationMethods",
        ClientForm.ClientAuthenticationMethodForm.items()
    )
    model.addAttribute(
        "clientAuthenticationGrantTypes",
        ClientForm.AuthorizationGrantTypeForm.items()
    )
    model.addAttribute("oidcScopesForm", ClientForm.OidcScopesForm.items())
    return "client_register"
  }

  @PostMapping("/client/register")
  @PreAuthorize("hasRole('ADMIN')")
  fun registerClient(@ModelAttribute clientForm: ClientForm): String {
    clientService.registerClient(
        ClientMapper.toClient(clientForm)
    )
    return "redirect:/client/list"
  }
}