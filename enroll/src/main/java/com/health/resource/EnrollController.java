package com.health.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.health.repository.DependentRepository;
import com.health.repository.EnrollRepository;
import com.health.jpa.Dependent;
import com.health.jpa.Enroll;
import com.health.util.Mapper;
import com.health.view.DependentView;
import com.health.view.EnrollView;
import org.apache.logging.log4j.util.Strings;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health/data")
public class EnrollController {

    private EnrollRepository repo;
    private DependentRepository depRepo;
    private static final Logger log = LoggerFactory.getLogger(EnrollController.class);
    private Mapper mapper = new Mapper();

    @Autowired
    public EnrollController(EnrollRepository repo, DependentRepository depRepo) {
        this.repo = repo;
        this.depRepo = depRepo;
    }

    @GetMapping(value = "/enroll")
    public ResponseEntity<?> getEnroll(){
        final String methodName = "enroll";
        log.info("enter method", methodName);
        List<Enroll> enrolls = repo.findAll();
        log.info("exit method", methodName);
        return ResponseEntity.ok(enrolls);
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody EnrollView enrollView) {
        final String methodName = "enroll";
        log.info("enter method", methodName, enrollView);

        Enroll enroll = (Enroll) mapper.convert(enrollView, Enroll.class);
       if(!Strings.isNotBlank(enrollView.getName())){
           return new ResponseEntity<>("name is a required field", HttpStatus.BAD_REQUEST);
        }
       if(enrollView.getBirthDate() == null){
           return new ResponseEntity<>("birth date is a required field", HttpStatus.BAD_REQUEST);
       }
        try {
            depRepo.saveAll(enroll.getDependents());
            repo.saveEnroll((Enroll) mapper.convert(enroll, Enroll.class));
            return ResponseEntity.ok(mapper.convert(enroll, EnrollView.class));

        } catch (Exception e) {
            log.error("failed to create an enroll", enroll);
        }
        log.info("exit method", methodName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/enroll")
    public ResponseEntity<?> update(@RequestBody EnrollView enroll){
        final String methodName = "update";
        log.info("enter method", methodName, enroll);
        Optional<Enroll> beforeEnroll = repo.findById(enroll.getId());

        if(beforeEnroll.equals(enroll)){
            return new ResponseEntity<>("No changes in the request", HttpStatus.BAD_REQUEST);
        }

        Enroll updateEnroll = repo.saveEnroll((Enroll) mapper.convert(enroll, Enroll.class));
        log.info("exit method", methodName, updateEnroll);
        return ResponseEntity.ok(mapper.convert(updateEnroll, EnrollView.class));
    }


    @DeleteMapping("/enroll/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        final String methodName = "delete";
        log.info("enter method", methodName, id);

        Optional<Enroll> enroll = repo.findById(id);
        if(enroll == null) {
            return new ResponseEntity<>("No enroll found with the id", HttpStatus.BAD_REQUEST);
        }

        repo.deleteEnrollById(id);
        log.info("exit method", methodName);

        return ResponseEntity.ok().body("deleted Successfully");

    }

    @PostMapping("/enroll/{id}/dependent")
    public ResponseEntity<?> addDependent(@PathVariable("id") Long id, @RequestBody DependentView dependent){
        final String methodName = "addDependent";
        log.info("enter method", methodName, id);

        Optional<Enroll> enroll = repo.findById(id);
        if(enroll == null) {
            return new ResponseEntity<>("No enroll found with the id", HttpStatus.BAD_REQUEST);
        }

        if(!Strings.isNotBlank(dependent.getName())){
            return new ResponseEntity<>("name is a required field", HttpStatus.BAD_REQUEST);
        }
        if(dependent.getBirthDate() == null){
            return new ResponseEntity<>("birth date is a required field", HttpStatus.BAD_REQUEST);
        }
        Set<Dependent> dependentSet = new HashSet<>();
        dependentSet.add((Dependent) mapper.convert(dependent, Dependent.class));
        enroll.get().setDependents(dependentSet);
        repo.saveAndFlush(enroll.get());

        Optional<Enroll> updated = repo.findById(enroll.get().getId());
        log.info("exit method", methodName, updated);

        return ResponseEntity.ok(mapper.convert(updated, EnrollView.class));
    }

    @PutMapping("/enroll/{enrollId}/dependent/{dependentId}")
    public ResponseEntity<?> updateDependent(@PathVariable("enrollId") Long enrollId,
                                             @PathVariable("dependentId") Long dependentId,
                                             @RequestBody DependentView dependent){
        final String methodName = "addDependents";
        log.info("enter method", methodName, enrollId, dependentId);

        Optional<Enroll> enroll = repo.findById(enrollId);
        if(enroll == null) {
            return new ResponseEntity<>("No enroll found with the enrollId", HttpStatus.BAD_REQUEST);
        }
        Optional<Dependent> beforeDependent = depRepo.findById(dependentId);
        if(beforeDependent == null) {
            return new ResponseEntity<>("No dependent found with the dependentId", HttpStatus.BAD_REQUEST);
        }
        try {
            Set<Dependent> dependentSet = new HashSet<>();
            dependentSet.add((Dependent) mapper.convert(dependent, Dependent.class));
            enroll.get().setDependents(dependentSet);
            repo.saveAndFlush(enroll.get());
        }
        catch (Exception e){

            Optional<Enroll> updated = repo.findById(enroll.get().getId());
            if(e instanceof PSQLException) {
                String message = e.getCause().getMessage();
                if(message.contains("conflict")) {
                    return new ResponseEntity<>(updated, HttpStatus.CONFLICT);
                }
            }
            return new ResponseEntity<>(mapper.convert(updated, EnrollView.class), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<Enroll> updated = repo.findById(enroll.get().getId());
        log.info("exit method", methodName, updated);
        return ResponseEntity.ok(mapper.convert(updated, EnrollView.class));
    }

    @DeleteMapping("/enroll/{enrollId}/dependent/{dependentId}")
    public ResponseEntity<?> deleteDependent(@PathVariable("enrollId") Long enrollId,
                                             @PathVariable("dependentId") Long dependentId){
        final String methodName = "addDependents";
        log.info("enter method", methodName, enrollId, dependentId);

        Optional<Enroll> enroll = repo.findById(enrollId);
        if(enroll == null) {
            return new ResponseEntity<>("No enroll found for the id", HttpStatus.BAD_REQUEST);
        }

        depRepo.deleteByIdAndEnrollId(dependentId, enrollId);
        repo.saveEnroll(enroll.get());
        log.info("exit method", methodName, dependentId, enrollId);

        return ResponseEntity.ok().body("deleted Successfully");

    }

}
