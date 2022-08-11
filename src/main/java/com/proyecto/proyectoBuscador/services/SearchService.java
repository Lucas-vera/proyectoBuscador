package com.proyecto.proyectoBuscador.services;

import com.proyecto.proyectoBuscador.entities.WebPage;
import com.proyecto.proyectoBuscador.repositories.SearchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author chuky
 */

@Service
public class SearchService {
    
    @Autowired
    private SearchRepository repository;
    
    public List<WebPage> search(String textSearch){
        
        return repository.search(textSearch);
    }
    
    public void save(WebPage webPage){
        repository.save(webPage);
    }

    public boolean exist(String link) {
        return repository.exist(link);
    }
    
    public List<WebPage> getLinksToIndex(){
        return repository.getLinksToIndex();
    }
}
