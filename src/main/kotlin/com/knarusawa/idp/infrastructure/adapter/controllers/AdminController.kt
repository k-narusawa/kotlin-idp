package com.knarusawa.idp.infrastructure.adapter.controllers

import com.knarusawa.idp.application.service.UserDtoQueryService
import com.knarusawa.idp.application.service.getAllClient.ClientGetAllService
import com.knarusawa.idp.application.service.registerClient.ClientRegisterInputData
import com.knarusawa.idp.application.service.registerClient.ClientRegisterService
import com.knarusawa.idp.infrastructure.dto.ClientForm
import java.security.Principal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminController(
  private val userDtoQueryService: UserDtoQueryService,
  private val clientGetAllService: ClientGetAllService,
  private val clientRegisterService: ClientRegisterService
) {
  @GetMapping("/user/list")
  @PreAuthorize("hasRole('ADMIN')")
  fun getUserList(model: Model, principal: Principal): String {
    val users = userDtoQueryService.findAll()
    model.addAttribute("users", users)
    return "user_list"
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