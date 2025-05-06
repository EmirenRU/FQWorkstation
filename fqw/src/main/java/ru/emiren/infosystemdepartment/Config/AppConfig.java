package ru.emiren.infosystemdepartment.Config;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.Repository.SQL.*;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
@Slf4j
public class AppConfig {

//    private final StudentRepository studentRepository;
//    private final LecturerRepository lecturerRepository;
//    private final OrientationRepository orientationRepository;
//    private final DepartmentRepository departmentRepository;
//    private final DecreeRepository decreeRepository;
//    private final FQWRepository fqwRepository;
//    private final StudentLecturerRepository studentLecturerRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final ConfigurableEnvironment environment;
    @Value("${save.dummy.flag}")
    private boolean flagForSavingEntities;

    private ClassPathResource classPathResource;

    @Autowired
    public AppConfig(ConfigurableEnvironment environment) {
//        this.studentRepository = studentRepository;
//        this.lecturerRepository = lecturerRepository;
//        this.orientationRepository = orientationRepository;
        this.environment = environment;
//        this.departmentRepository = departmentRepository;
//        this.decreeRepository = decreeRepository;
//        this.fqwRepository = fqwRepository;
//        this.studentLecturerRepository = studentLecturerRepository;
    }

    @Bean
    public DateFormat dateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
    }

    @Bean()
    public Gson gson(){
        return new Gson();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void evictCache() {
        entityManager.getEntityManagerFactory().getCache().evictAll();
        log.info("Cache evicted.");
    }

    @PostConstruct
    public void init() {
        log.info("Initializing AppConfig with Cache Eviction");
        evictCache();

        if (flagForSavingEntities){
//            saveEntitiesUntilMax(Student.class, studentRepository, 89);
//            saveEntitiesUntilMax(Lecturer.class, lecturerRepository, 24);
//            saveEntitiesUntilMax(Orientation.class, orientationRepository, 7);
//            saveEntitiesUntilMax(Department.class, departmentRepository, 1);
//            saveEntitiesUntilMax(Decree.class, decreeRepository, 89);
//            saveEntitiesUntilMax(FQW.class, fqwRepository, 89);
//            saveEntitiesUntilMax(StudentLecturers.class, studentLecturerRepository, 89);
//            log.info("Injecting flag to application properties");
//            modifyProperties("save.dummy.flag", "false");
        }
    }

// Not sure that it works (it does not change property
//    public void modifyProperties(String key, Object value) {
//        Properties properties = new Properties();
//
//        classPathResource = new ClassPathResource("application.properties");
//        try (FileInputStream fis = new FileInputStream(classPathResource.getFile())){
//            properties.load(fis);
//        } catch (IOException e){
//            log.error("Properties not loaded with error: {}",e.getMessage());
//        }
//        properties.setProperty(key, (String) value);
//        log.info("Properties data: {}",properties.toString());
//        try (FileOutputStream fos = new FileOutputStream(classPathResource.getFile())) {
//            properties.store(fos, null);
//        }
//        catch (IOException e){
//            log.error("Properties not stored with error: {}", e.getMessage());
//        }
//
//        MutablePropertySources propertySources = environment.getPropertySources();
//        propertySources.addFirst(new PropertiesPropertySource("application.properties", properties));
//    }

    @Transactional
    public <T, ID> void saveEntitiesUntilMax(Class<T> entityClass, JpaRepository<T, ID> repository, int maxEntities) {
        int counter = 0;

        if (counter < maxEntities) {
            while (counter < maxEntities) {
                try {
                    Constructor<T> constructor = entityClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    T entity = constructor.newInstance();

                    T savedEntity = repository.save(entity);

                    ID id = (ID) savedEntity.getClass().getMethod("getId").invoke(savedEntity);

                    log.info("Saved " + entityClass.getSimpleName() + " with ID: " + id);

                } catch (Exception e) {
                    log.warn("Error saving " + entityClass.getSimpleName() + ": " + e.getMessage());
                }
                counter++;
            }

            log.info("Finished saving " + counter + " " + entityClass.getSimpleName() + " instances.");
        }
    }





}
