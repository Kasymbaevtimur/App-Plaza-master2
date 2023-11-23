package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Application;
import peaksoft.model.Genre;
import peaksoft.model.User;
import peaksoft.service.ApplicationService;
import peaksoft.service.GenreService;
import peaksoft.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final GenreService genreService;
    private final UserService userService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, GenreService genreService, UserService userService) {
        this.applicationService = applicationService;
        this.genreService = genreService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public String addApplication(Model model) {
        model.addAttribute("app", new Application());
        return "application/save";
    }

    @PostMapping("/save")
    public String saveApplication(@ModelAttribute("app") Application application) {
        System.out.println("Hello save");
        applicationService.save(application);
        return "redirect:/applications";
    }

    @GetMapping()
    public String findAll(Model model) {
        model.addAttribute("appList", applicationService.findAll());
        return "application/find-all";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        applicationService.deleteById(id);
        return "redirect:/applications";
    }
    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        Application application = applicationService.findById(id);
        model.addAttribute("app", application);
        return "application/update";
    }
    @PostMapping("/save-update/{id}")
    public String saveUpdateUser(@PathVariable("id") Long id, @ModelAttribute("app")Application application) {
        applicationService.update(id, application);
        return "redirect:/applications";
    }
    @ModelAttribute("genreList")
    public List<Genre> genreList() {
        return genreService.findAll();
    }
    @GetMapping("/my-applications")
    public String getApplicationByUserId(Model model,Principal principal){
        User user = userService.findByEmail(principal.getName());
        List<Application> myApplications = applicationService.getApplicationByUser(user.getId());
        model.addAttribute("myApplications",myApplications);
        return "application/userApplication";
    }
    @GetMapping("/search")
    public String findApplicationByName(String name,Model model){
        System.out.println("name "+name);
        if (name == null){
            model.addAttribute("appList",applicationService.findAll());
        }else {
            List<Application> applicationList = applicationService.findApplicationByName(name);
            model.addAttribute("appList",applicationList);
        }
        return "application/search";
    }


}
