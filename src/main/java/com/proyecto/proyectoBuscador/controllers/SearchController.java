package com.proyecto.proyectoBuscador.controllers;

import com.proyecto.proyectoBuscador.entities.WebPage;
import com.proyecto.proyectoBuscador.services.SearchService;
import com.proyecto.proyectoBuscador.services.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author chuky
 */
@RestController
@RequestMapping("/api")
public class SearchController {
    
    @Autowired
    private SearchService service;
    @Autowired
    private SpiderService spiderService;

    @CrossOrigin(origins = "*") //Soluciona el problema cors
    @GetMapping("/search")
    public List<WebPage> search(@RequestParam Map<String, String> params){
        String query = params.get("query");
        return service.search(query);
    }
    
    @GetMapping("/test")
    public void search(){
        spiderService.indexWebPages();
    }

    @GetMapping("/clean")
    public ResponseEntity<String> clean(){
        return ResponseEntity.ok(service.cleanWebPages());
    }
}
