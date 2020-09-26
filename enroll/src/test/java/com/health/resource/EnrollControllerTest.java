package com.health.resource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.Optional;
import com.health.jpa.Enroll;
import com.health.repository.DependentRepository;
import com.health.repository.EnrollRepository;
import com.health.util.Mapper;
import com.health.view.DependentView;
import com.health.view.EnrollView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
public class EnrollControllerTest {

    private EnrollRepository enrollRepository = mock(EnrollRepository.class);
    private DependentRepository dependentRepository = mock(DependentRepository.class);
    private EnrollController enrollController = new EnrollController(enrollRepository, dependentRepository);
    private Mapper mapper = new Mapper();

    public void set() {

    }

    @Test
    public void test_add_enroll() {
        Enroll enroll = new Enroll("centene", true, new Date(), "123");
        EnrollView enrollView = new EnrollView("centene", true, new Date(), "123");

        when(enrollRepository.saveEnroll(enroll)).thenReturn(enroll);
        ResponseEntity responseEntity = enrollController.enroll(enrollView);
        Object response = responseEntity.getBody();

        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNotNull(response);
    }

    @Test
    public void test_bad_request_add_enroll() {
        Enroll enroll = new Enroll("", true, new Date(), "123");
        when(enrollRepository.saveEnroll(enroll)).thenReturn(enroll);
        ResponseEntity responseEntity = enrollController.enroll((EnrollView) mapper.convert(enroll, EnrollView.class));
        Object response = responseEntity.getBody();

        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertNotNull(response.toString(), "name is a required field");

        enroll.setBirthDate(null);
        ResponseEntity responseEntity2 = enrollController.enroll((EnrollView) mapper.convert(enroll, EnrollView.class));
        Assert.assertNotNull(responseEntity2.getBody().toString(), "birth date is a required field");
    }

    @Test
    public void test_update_enroll() {
        Enroll enroll = new Enroll("", true, new Date(), "1234");
        when(enrollRepository.findById(enroll.getId())).thenReturn(Optional.of(enroll));
        Enroll enroll2 = new Enroll("", true, new Date(), "1234");

        enroll2.setPhoneNumber("1233");
        ResponseEntity responseEntity = enrollController.update((EnrollView) mapper.convert(enroll2, EnrollView.class));

        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void test_delete_enroll() {
        Enroll enroll = new Enroll("", true, new Date(), "1234");
        when(enrollRepository.findById(enroll.getId())).thenReturn(Optional.of(enroll));

        ResponseEntity responseEntity = enrollController.delete((1L));

        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void test_add_dependent_bad_enroll() {
        when(enrollRepository.findById(1L)).thenReturn(null);
        DependentView dependentView = new DependentView("name", new Date());

        ResponseEntity responseEntity = enrollController.addDependent(1l, dependentView);

        Assert.assertEquals(400, responseEntity.getStatusCodeValue());

    }

    @Test
    public void test_update_dependent_bad_enroll() {
        when(enrollRepository.findById(1L)).thenReturn(null);
        DependentView dependentView = new DependentView("name", new Date());

        ResponseEntity responseEntity = enrollController.updateDependent(1L, 1L, dependentView);

        Assert.assertEquals(400, responseEntity.getStatusCodeValue());

    }

    @Test
    public void test_delete_dependent_enroll() {
        Enroll enroll = new Enroll("", true, new Date(), "1234");

        when(enrollRepository.findById(1L)).thenReturn(Optional.of(enroll));

        ResponseEntity responseEntity = enrollController.deleteDependent(1L, 1L);

        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

    }
}
