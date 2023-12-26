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
public interface SearchRepository extends ListCrudRepository<WebPage, Long> {

    List<WebPage> findByDescriptionLike(String textSearch);

    Optional<WebPage> findByUrl(String link);

    List<WebPage> findByTitleIsNullAndDescriptionIsNull();
}
