package com.proyecto.proyectoBuscador.repositories;

import com.proyecto.proyectoBuscador.entities.WebPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;


public interface SearchRepositoryPaging extends ListPagingAndSortingRepository<WebPage, Long> {

    Page<WebPage> findByTitleIsNullAndDescriptionIsNull(Pageable pageable);
}
