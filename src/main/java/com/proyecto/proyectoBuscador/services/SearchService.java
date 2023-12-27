package com.proyecto.proyectoBuscador.services;

import com.proyecto.proyectoBuscador.entities.WebPage;
import com.proyecto.proyectoBuscador.repositories.SearchRepositoryList;
import com.proyecto.proyectoBuscador.repositories.SearchRepositoryPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 *
 * @author chuky
 */

@Service
public class SearchService {
    
    @Autowired
    private SearchRepositoryList repository;

    @Autowired
    private SearchRepositoryPaging repositoryPaging;

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


    public Page<WebPage> getLinksToIndex(){
        //Setear max result a 300 despues
        Pageable pageRequest = PageRequest.ofSize(300);

       return repositoryPaging.findByTitleIsNullAndDescriptionIsNull(pageRequest);

    }

    public String cleanWebPages() {
        //Borro el titulo si solo eso contiene
        List<WebPage> paginasSinDescription = repository.findByDescriptionIsNull();
        paginasSinDescription.stream().parallel().forEach(pagina -> {
            pagina.setTitle(null);
            save(pagina);
        });

        //Obtengo las que se intento actualizar 3 veces y no se pudo.
        Optional<List<WebPage>> paginasViejas = repository.findByContadorRevision(3);
        paginasViejas.ifPresent(paginas -> {
            paginas.forEach(webPage -> repository.deleteById(webPage.getId()));
        });


        return paginasViejas.map(webPages -> webPages.size() + "--> Paginas eliminadas").orElse("No se encontraron paginas a eliminar");
    }
}
