package com.proyecto.proyectoBuscador.repositories;

import com.proyecto.proyectoBuscador.entities.WebPage;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chuky
 */
@Repository
public class SearchRepositoryImp implements SearchRepository{
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional
    @Override
    public List<WebPage> search(String textSearch) {
        String query = "FROM WebPage WHERE description like : textSearch";
        return entityManager.createQuery(query)
                .setParameter("textSearch", "%"+textSearch+"%")
                .getResultList();
    }
    
    @Transactional
    @Override
    public void save(WebPage webPage) {
        entityManager.merge(webPage);
    }

    @Override
    public boolean exist(String link) {
        String query = "FROM WebPage WHERE url like : link";
        List<WebPage> webFinded = entityManager.createQuery(query)
                                               .setParameter("link", link)
                                               .getResultList();
        return getByUrl(link) != null;
    }

    @Override
    public WebPage getByUrl(String url) {
        String query = "FROM WebPage WHERE url = :link";
        List<WebPage> webFinded = entityManager.createQuery(query)
                                               .setParameter("link", url)
                                               .getResultList();
        return webFinded.isEmpty() ? null : webFinded.get(0);
    }

    @Override
    public List<WebPage> getLinksToIndex() {
        String query = "FROM WebPage WHERE title is null AND description is null";
        return entityManager.createQuery(query)
                .setMaxResults(300)
                .getResultList();
    }
    
    
}
