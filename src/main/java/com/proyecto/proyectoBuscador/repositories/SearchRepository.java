package com.proyecto.proyectoBuscador.repositories;

import com.proyecto.proyectoBuscador.entities.WebPage;
import java.util.List;
/**
 *
 * @author chuky
 */

public interface SearchRepository {
    
    List<WebPage> search(String textSearch);
    
    void save (WebPage webPage);
    boolean exist(String link);
    WebPage getByUrl(String url);
    List<WebPage> getLinksToIndex();
}
