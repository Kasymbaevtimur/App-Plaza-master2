package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import peaksoft.model.Genre;
import peaksoft.service.GenreService;

@Controller
@RequestMapping("genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/add")
    public String addGenre(Model model) {
        model.addAttribute("genre", new Genre());
        return "genre/save";
    }

    @PostMapping("/save")
    public String saveGenre(@ModelAttribute("genre") Genre genre) {
        genreService.save(genre);
        return "redirect:/genres";
    }

    @GetMapping()
    public String findAllGenre(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "genre/find-all";
    }
}
