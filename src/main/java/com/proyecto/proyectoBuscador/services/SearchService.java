package com.proyecto.proyectoBuscador.services;

import com.proyecto.proyectoBuscador.entities.WebPage;
import com.proyecto.proyectoBuscador.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 * @author chuky
 */

@Service
public class SearchService {
    
    @Autowired
    private SearchRepository repository;

    @Transactional
    public List<WebPage> search(String textSearch){
        
        return repository.findByDescriptionLike("%"+textSearch+"%");
    }

    @Transactional
    public void save(WebPage webPage){
        repository.save(webPage);
    }

    public boolean exist(String link) {
        return repository.findByUrl(link).isPresent();
    }
    
    public List<WebPage> getLinksToIndex(){
        //Setear max result a 300 despues
        return repository.findAll();
       // return repository.findByTitleIsNullAndDescriptionIsNull();

    }
}
