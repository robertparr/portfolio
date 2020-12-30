package com.jrp.portfolio.controller;

import com.jrp.portfolio.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class TransactionMvcController {

  private final TransactionService service;

  @GetMapping("/index")
  public String showUserList(Model model) {
    model.addAttribute("transactions", service.get());
    return "index";
  }

  @GetMapping("/portfolio")
  public String portfolio(Model model) {
    model.addAttribute("holdings", service.getHoldings());
    return "portfolio";
  }
}
