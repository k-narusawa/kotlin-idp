package com.knarusawa.idp.infrastructure.controller

import com.knarusawa.idp.application.service.UserDtoQueryService
import com.knarusawa.idp.application.service.getAllClient.ClientGetAllService
import com.knarusawa.idp.application.service.registerClient.ClientRegisterInputData
import com.knarusawa.idp.application.service.registerClient.ClientRegisterService
import com.knarusawa.idp.application.service.registerUser.UserRegisterInputData
import com.knarusawa.idp.application.service.registerUser.UserRegisterService
import com.knarusawa.idp.domain.model.user.Role
import com.knarusawa.idp.infrastructure.dto.ClientForm
import com.knarusawa.idp.infrastructure.dto.UserForm
import java.security.Principal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class HomeController(
  private val userRegisterService: UserRegisterService,
  private val userDtoQueryService: UserDtoQueryService,
  private val clientGetAllService: ClientGetAllService,
  private val clientRegisterService: ClientRegisterService
) {
  @GetMapping("/login")
  fun login(): String {
    return "login"
  }

  @GetMapping("/")
  fun getProfile(model: Model, principal: Principal): String {
    val user = userDtoQueryService.findByUserId(userId = principal.name)
    model.addAttribute("user", user)
    return "index"
  }

  @GetMapping("/user/list")
  @PreAuthorize("hasRole('ADMIN')")
  fun getUserList(model: Model, principal: Principal): String {
    val users = userDtoQueryService.findAll()
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
    val command = UserRegisterInputData(
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
    val clients = clientGetAllService.execute()
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
    clientRegisterService.execute(
      input = ClientRegisterInputData(
        clientId = clientForm.clientId,
        clientSecret = clientForm.clientSecret,
        clientAuthenticationMethods = clientForm.clientAuthenticationMethods.map { it.to() },
        clientAuthenticationGrantTypes = clientForm.clientAuthenticationGrantTypes.map { it.to() },
        redirectUrls = clientForm.redirectUrls.split(",").map { it.trim() }
          .filter { it.isNotEmpty() },
        scopes = clientForm.scopes.map { it.to() }
      )
    )
    return "redirect:/client/list"
  }
}