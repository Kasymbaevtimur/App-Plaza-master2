package peaksoft.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.model.Genre;

import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class GenreService implements ModelService<Genre>{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void save(Genre genre) {
        genre.setCreateDate(LocalDate.now());
        entityManager.persist(genre);
    }

    @Override
    public Genre findById(Long id) {
        return entityManager.find(Genre.class,id);
    }

    @Override
    public List<Genre> findAll() {
        return entityManager.createQuery("from Genre").getResultList();
    }

    @Override
    public void update(Long id, Genre genre) {
    Genre oldGenre = findById(id);
    oldGenre.setGenreName(genre.getGenreName());
    oldGenre.setDescription(genre.getDescription());
    entityManager.persist(oldGenre);
    }

    @Override
    public void deleteById(Long id) {
    entityManager.remove(findById(id));
    }
}
