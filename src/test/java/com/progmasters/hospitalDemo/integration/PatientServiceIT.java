package com.progmasters.hospitalDemo.integration;

import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.dto.incoming.PatientCommand;
import com.progmasters.hospitalDemo.dto.incoming.PatientUpdateCommand;
import com.progmasters.hospitalDemo.dto.outgoing.PatientDetail;
import com.progmasters.hospitalDemo.dto.outgoing.PatientListItem;
import com.progmasters.hospitalDemo.dto.outgoing.PatientDetail;
import com.progmasters.hospitalDemo.dto.outgoing.PatientListItem;
import com.progmasters.hospitalDemo.service.PatientService;
import com.progmasters.hospitalDemo.service.PatientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class PatientServiceIT {
    
    @Autowired
    private PatientService patientService;

    void init(String firstName, String lastName, String tajNumber) {
        PatientCommand patientCommand = new PatientCommand();
        patientCommand.setFirstName(firstName);
        patientCommand.setLastName(lastName);
        patientCommand.setTajNumber(tajNumber);

        patientService.savePatient(patientCommand);
    }

    @Test
    public void testListPatientListTwo() {
        List<PatientListItem> patientList = patientService.findAll();
        long sizeBefore = patientList.size();
        init("Lajos", "Nagy", "111222233");
        init("Béla", "Kis", "423122223");

        patientList = patientService.findAll();
        assertEquals(sizeBefore + 2, patientList.size());
    }

    @Test
    public void testListPatientOne() {
        init("Ádám", "Kovács", "342343216");
        init("Ferenc", "Varga", "345689564");
        List<Patient> patientList = patientService.findAllPatientAllData();

        PatientDetail patientDetail = patientService.findPatientById((long) patientList.get(1).getId());
        assertEquals("Varga", patientDetail.getLastName());
        assertEquals("Ferenc", patientDetail.getFirstName());
        assertEquals("345689564", patientDetail.getTajNumber());
    }

    @Test
    public void testUpdatePatient() {
        init("Géza", "Kerékgyártó", "345678346");
        init("János", "Tóth", "346898675");
        List<Patient> patientList = patientService.findAllPatientAllData();

        PatientUpdateCommand patientUpdateCommand = new PatientUpdateCommand();
        patientUpdateCommand.setFirstName("János");
        patientUpdateCommand.setLastName("Tóth");
        patientUpdateCommand.setTajNumber("346898675");
        patientUpdateCommand.setFirstName("Lajoska");
        patientService.updatePatient((long) patientList.get(1).getId(), patientUpdateCommand);

        PatientDetail patientDetail = patientService.findPatientById((long) patientList.get(1).getId());
        assertEquals("Lajoska", patientDetail.getFirstName());
        assertEquals("Tóth", patientDetail.getLastName());
        assertEquals("346898675", patientDetail.getTajNumber());
    }

    @Test
    public void testDeletePatient() {
        init("Ágota", "Almási", "123343560");
        init("Noémi", "Jánosi", "023567234");
        List<Patient> patientList = patientService.findAllPatientAllData();

        patientService.deletePatient((long) patientList.get(0).getId());

        patientList = patientService.findAllPatientAllData();
        assertEquals(1, patientList.size());
        assertEquals("Noémi", patientList.get(0).getFirstName());
    }
}
