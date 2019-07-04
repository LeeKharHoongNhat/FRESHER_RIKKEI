package rikkei.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * LoginController
 *
 * @author NhatLKH
 *
 */
@Controller
public class LoginController {
    /**
     * viewHome
     *
     * @param model (dùng để truyền dữ liệu sang view)
     * @return view: home
     */
    @RequestMapping("/")
    public String viewHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("role", auth.getAuthorities());
        model.addAttribute("user", auth.getName());
        return "home";
    }

    /**
     * viewLogin
     *
     * @return view: login hoặc home
     */
    @RequestMapping("/login")
    public String viewLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String x = auth.getName();
        if (x.equals("anonymousUser")) {
            return "login";
        } else {
            return "redirect:/";
        }

    }

    /**
     * accessDenied
     *
     * @return view: 403
     */
    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    /**
     * logout
     *
     * @param request  (yêu cầu lên sever)
     * @param response ( sever trả về client)
     * @return view: home
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.err.println("auth: " + auth.getAuthorities());
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
