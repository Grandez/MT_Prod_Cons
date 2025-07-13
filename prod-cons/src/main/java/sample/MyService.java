package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class MyService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void performDatabaseOperation() {
        // Aquí se gestionará una transacción automáticamente
        // Puedes realizar operaciones CRUD usando entityManager
        System.out.println("Operación de base de datos en el hilo: " + Thread.currentThread().getName());
        // Ejemplo: entityManager.persist(entity);
    }
}
