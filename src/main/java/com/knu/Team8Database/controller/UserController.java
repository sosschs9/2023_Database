package com.knu.Team8Database.controller;

import com.knu.Team8Database.dto.UserReq;
import com.knu.Team8Database.entity.Users;
import com.knu.Team8Database.repository.UserRepository;
import com.knu.Team8Database.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        session.removeAttribute("loginUser");
        return "login";
    }

    @PostMapping("/login")
    public String login(String userId, String password,
                        HttpServletRequest request, Model model) {

        Users loginUser = userService.findByUsersId(userId);
        if (loginUser == null) {
            model.addAttribute("loginFail", "존재하지 않는 사용자입니다.");
            return "login";
        }
        else if (loginUser.getUsersPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);

            return "redirect:/main";
        }
        else {
            model.addAttribute("loginFail", "비밀번호가 틀렸습니다.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/main";
    }

    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(UserReq userReq, Model model) {
        userService.saveUser(userReq);
        return "login";
    }
}
