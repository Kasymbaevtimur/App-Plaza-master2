package peaksoft.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.model.Application;
import peaksoft.model.Genre;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ApplicationService implements ModelService<Application> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Application application) {
        Genre genre = entityManager.find(Genre.class, application.getGenre().getId());
        application.setGenre(genre);
        application.setGenreName(genre.getGenreName());
        application.setLocalDate(LocalDate.now());
        entityManager.persist(application);
    }

    @Override
    public Application findById(Long id) {
        return entityManager.find(Application.class, id);
    }

    @Override
    public List<Application> findAll() {
        return entityManager.createQuery("from Application").getResultList();
    }

    @Override
    public void update(Long id, Application application) {
        Application oldApplication = findById(id);
        oldApplication.setGenreName(application.getGenreName());
        oldApplication.setDescription(application.getDescription());
        oldApplication.setGenre(application.getGenre());
        oldApplication.setGenreName(application.getGenreName());
        entityManager.persist(oldApplication);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    public List<Application> getApplicationByUser(Long userId){
        return entityManager.createQuery("select app from Application app join app.users u where u.id=:id", Application.class)
                .setParameter("id",userId).getResultList();
    }
    public List<Application> findApplicationByName(String name){
        return entityManager.createQuery("select  app from Application app where app.name=:name", Application.class)
                .setParameter("name",name).getResultList();

    }
}
