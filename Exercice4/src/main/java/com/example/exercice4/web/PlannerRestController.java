package com.example.exercice4.web;

import com.example.exercice4.model.Evenement;
import com.example.exercice4.model.Planner;
import com.example.exercice4.service.PlannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Planners", description = "Gestion des planners")
@RestController
@RequestMapping("/api/planners")
public class PlannerRestController {

    private final PlannerService plannerService;

    public PlannerRestController(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    @Operation(summary = "Lister les planners, ou les rechercher par nom ou par prénom")
    @ApiResponse(responseCode = "200", description = "Liste des planners, éventuellement vide")
    @GetMapping
    public List<Planner> lister(
            @Parameter(description = "Mot-clé cherché dans le nom et le prénom")
            @RequestParam(name = "q", required = false) String motCle) {
        return plannerService.rechercher(motCle);
    }

    @Operation(summary = "Trouver un planner par son identifiant")
    @ApiResponse(responseCode = "200", description = "Planner trouvé")
    @ApiResponse(responseCode = "404", description = "Aucun planner avec cet identifiant")
    @GetMapping("/{id}")
    public Planner trouver(@PathVariable("id") Long id) {
        return plannerService.trouver(id);
    }

    @Operation(summary = "Lister les evenements d'un planner")
    @ApiResponse(responseCode = "200", description = "Evènements du planner, éventuellement aucun")
    @ApiResponse(responseCode = "404", description = "Aucun planner avec cet identifiant")
    @GetMapping("/{id}/events")
    public List<Evenement> events(@PathVariable("id") Long id) {
        return plannerService.evenements(id);
    }

    @Operation(summary = "Ajouter un planner", description = "L'identifiant éventuellement fourni est ignoré.")
    @ApiResponse(responseCode = "201", description = "Planner créé, son URL est dans l'en-tête Location")
    @PostMapping
    public ResponseEntity<Planner> ajouter(@RequestBody Planner planner) {
        Planner cree = plannerService.ajouter(planner);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cree.getId())
                .toUri();
        return ResponseEntity.created(location).body(cree);
    }

    @Operation(summary = "Remplacer un planner")
    @ApiResponse(responseCode = "200", description = "Planner modifié")
    @ApiResponse(responseCode = "404", description = "Aucun planner avec cet identifiant")
    @PutMapping("/{id}")
    public Planner modifier(@PathVariable("id") Long id, @RequestBody Planner planner) {
        return plannerService.modifier(id, planner);
    }

    @Operation(summary = "Supprimer un planner")
    @ApiResponse(responseCode = "204", description = "Planner supprimé")
    @ApiResponse(responseCode = "404", description = "Aucun planner avec cet identifiant")
    @ApiResponse(responseCode = "409", description = "Le planner a encore des évènements")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable("id") Long id) {
        plannerService.supprimer(id);
    }
}

