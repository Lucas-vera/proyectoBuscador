package com.proyecto.proyectoBuscador.repositories;

import com.proyecto.proyectoBuscador.entities.WebPage;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author chuky
 */
@Repository
public interface SearchRepositoryList extends ListCrudRepository<WebPage, Long> {

    List<WebPage> findByDescriptionLike(String textSearch);

    Optional<WebPage> findByUrl(String link);

    Optional<List<WebPage>> findByContadorRevision(int contador);

    List<WebPage> findByDescriptionIsNull();
}
