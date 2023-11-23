package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Application;
import peaksoft.model.User;
import peaksoft.service.ApplicationService;
import peaksoft.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping()
public class UserController {
    private final UserService userService;
    private final ApplicationService applicationService;

    @Autowired
    public UserController(UserService userService, ApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }
    @GetMapping()
    public String mainPage(){
        return "/user/main-page";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/save";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        userService.save(user);
        model.addAttribute("apps", applicationService.findAll());
        return "user/main-page";

    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user1", user);
        return "user/update";
    }

    @PostMapping("/save-update/{id}")
    public String saveUpdateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "user/profile";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.deleteById(id);
        model.addAttribute("users", userService.findAll());
        return "user/find-all";
    }

    @GetMapping("/find-all")
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/find-all";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("download/{appId}")
    public String addApplication(@PathVariable("appId") Long appId, Model model,Principal principal) {
        User user = userService.findByEmail(principal.getName());
        userService.addApplication(user.getId(), appId);
        return "redirect:/applications/my-applications";
    }


}