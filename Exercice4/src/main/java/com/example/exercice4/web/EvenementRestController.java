package com.example.exercice4.web;

import com.example.exercice4.model.Evenement;
import com.example.exercice4.service.EvenementService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/evenements")
@Tag(name = "Evènements", description = "Gestion du catalogue des évènements")
public class EvenementRestController {

    private final EvenementService eventService;

    public EvenementRestController(EvenementService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @Operation(summary="Lister les évènements")
    @ApiResponse(responseCode = "200", description = "Liste des évènements, éventuellement vide")
    @ApiResponse(responseCode = "404", description = "Aucun évènement trouvé")
    public List<Evenement> lister(
            @Parameter(description = "Lieu des évènements recherché", required = false)
            @RequestParam(value="lieu", required = false) String lieu) {
        if(lieu != null) {
            return eventService.listerParLieu(lieu);
        }
        return eventService.lister();
    }

    @GetMapping("/{id}")
    @Operation(summary="Trouver un évènement")
    @ApiResponse(responseCode = "200", description = "Evènement rechercher trouvé")
    @ApiResponse(responseCode = "404", description = "Aucun évènement trouvé")
    public Evenement trouver(@PathVariable("id") Long id) {
        return eventService.trouver(id);
    }

    @PostMapping
    @Operation(summary="Ajouter un évènement")
    @ApiResponse(responseCode = "201", description = "Evènement créé")
    @ApiResponse(responseCode = "422", description = "Données de l'évènement invalides")
    public ResponseEntity<Evenement> ajouter(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content=@Content(examples=@ExampleObject(
                name = "Exemple ajout",
                summary = "Exemple d'ajout de d'évènement",
                value = """
                        {
                            "titre": "JNM", 
                            "lieu": "Mulhouse",
                            "date": "2026-11-15"
                        }
                        """
    ))) @RequestBody Evenement event) {
        Evenement cree = eventService.ajouter(event);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cree.getId())
                .toUri();
        return ResponseEntity.created(location).body(cree);
    }

    @PutMapping("/{id}")
    @Operation(summary="Modifier un évènement")
    @ApiResponse(responseCode = "200", description = "Modifier avec succès")
    @ApiResponse(responseCode = "404", description = "Evènement inexistant en base")
    public Evenement modifier(@PathVariable("id") Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(content=@Content(examples=@ExampleObject(
            name = "Exemple modification",
            summary = "Exemple de modification d'évènement",
            value = """
                        {
                            "titre": "JNM", 
                            "lieu": "Mulhouse",
                            "date": "2026-11-15"
                        }
                        """
    )))@RequestBody Evenement event) {
        return eventService.modifier(id, event);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Supprimer un évènement")
    @ApiResponse(responseCode = "204", description = "Evènement supprimé")
    @ApiResponse(responseCode = "404", description = "Evènement non trouvé")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable("id") Long id) {
        eventService.supprimer(id);
    }
}
