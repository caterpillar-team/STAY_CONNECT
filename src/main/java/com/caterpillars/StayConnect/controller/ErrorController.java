package com.caterpillars.StayConnect.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class ErrorController {

  @Autowired
  private ErrorAttributes errorAttributes;

  @GetMapping
  public String handleError(HttpServletRequest request, WebRequest webRequest,
      Model model) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());

      if (statusCode == 403) {
        return "error/403";
      } else if (statusCode == 404) {
        return "error/404";
      } else {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest,
            ErrorAttributeOptions.of(Include.MESSAGE));
        model.addAttribute("errorAttributes", errorAttributes);
        model.addAttribute("statusCode", statusCode);
        return "error/default";
      }
    }
    return "error/default";
  }
}