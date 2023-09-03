package com.knarusawa.idp.infrastructure.controller

import com.knarusawa.idp.application.service.UserActivityDtoQueryService
import com.knarusawa.idp.application.service.UserDtoQueryService
import com.knarusawa.idp.application.service.getAllClient.ClientGetAllService
import com.knarusawa.idp.application.service.registerClient.ClientRegisterInputData
import com.knarusawa.idp.application.service.registerClient.ClientRegisterService
import com.knarusawa.idp.infrastructure.dto.ClientForm
import java.security.Principal
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class HomeController(
  private val userDtoQueryService: UserDtoQueryService,
  private val userActivityDtoQueryService: UserActivityDtoQueryService,
  private val clientGetAllService: ClientGetAllService,
  private val clientRegisterService: ClientRegisterService
) {
  @GetMapping("/")
  fun getProfile(model: Model, principal: Principal): String {
    val user = userDtoQueryService.findByUserId(userId = principal.name)
    val userActivities = userActivityDtoQueryService.findByUserId(
      userId = principal.name,
      pageable = PageRequest.of(0, 3, Sort.by("timestamp").descending())
    )
    model.addAttribute("user", user)
    model.addAttribute("userActivities", userActivities)
    return "index"
  }

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