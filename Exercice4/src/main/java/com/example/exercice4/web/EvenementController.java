package com.example.exercice4.web;
import com.example.exercice4.model.Evenement;
import com.example.exercice4.service.EvenementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events")
public class EvenementController {

    private final EvenementService eventService;

    public EvenementController(EvenementService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("events", eventService.lister());
        return "evenements/liste";
    }

    @GetMapping("/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("event", new Evenement());
        return "evenements/formulaire";
    }

    @PostMapping
    public String ajouter(@ModelAttribute("event") Evenement event, RedirectAttributes redirection) {
        eventService.ajouter(event);
        redirection.addFlashAttribute("message", "Evènement ajouté.");
        return "redirect:/events";
    }

    @GetMapping("/{id}/modifier")
    public String modifier(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", eventService.trouver(id));
        return "evenements/formulaire";
    }

    @PostMapping("/{id}")
    public String enregistrer(@PathVariable("id") Long id,
                              @ModelAttribute("event") Evenement event,
                              RedirectAttributes redirection) {
        eventService.modifier(id, event);
        redirection.addFlashAttribute("message", "Evènement modifié.");
        return "redirect:/events";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable("id") Long id, RedirectAttributes redirection) {
        eventService.supprimer(id);
        redirection.addFlashAttribute("message", "Evènement supprimé.");
        return "redirect:/events";
    }
}

