package com.progmasters.hospitalDemo.integration;

import com.progmasters.hospitalDemo.domain.Operation;
import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.*;
import com.progmasters.hospitalDemo.dto.outgoing.OperationDetail;
import com.progmasters.hospitalDemo.dto.outgoing.OperationListItem;
import com.progmasters.hospitalDemo.service.OperationService;
import com.progmasters.hospitalDemo.service.PatientService;
import com.progmasters.hospitalDemo.service.SurgeonService;
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
public class OperationServiceIT {
    
    @Autowired
    private OperationService operationService;

    @Autowired
    private SurgeonService surgeonService;

    @Autowired
    private PatientService patientService;

    private long patientId;
    private long surgeonId;

    void init(String operatingRoom, LocalDate operationDate) {
        SurgeonCommand surgeonCommand = new SurgeonCommand();
        surgeonCommand.setFirstName("Béla");
        surgeonCommand.setLastName("Varga");
        surgeonCommand.setBeginOfPractice(LocalDate.of(2000, 1, 30));
        Surgeon surgeon = surgeonService.saveSurgeon(surgeonCommand);

        surgeonId = surgeon.getId();

        PatientCommand patientCommand = new PatientCommand();
        patientCommand.setFirstName("Géza");
        patientCommand.setLastName("Kovács");
        patientCommand.setTajNumber("1234124");
        Patient patient = patientService.savePatient(patientCommand);

        patientId = patient.getId();


        OperationCommand operationCommand = new OperationCommand();
        operationCommand.setOperatingRoom(operatingRoom);
        operationCommand.setOperationDate(operationDate);
        operationCommand.setPatientId(patient.getId());
        operationCommand.setSurgeonId(surgeon.getId());

        operationService.saveOperation(operationCommand);
    }

    @Test
    public void testListSurgeonListTwo() {
        List<OperationListItem> operationList = operationService.findAll();
        long sizeBefore = operationList.size();
        init("1/1",LocalDate.of(2000, 1, 30));
        init("3/4", LocalDate.of(1995, 9, 13));

        operationList = operationService.findAll();
        assertEquals(sizeBefore + 2, operationList.size());
    }

    @Test
    public void testListOperationOne() {
        init("2/1",LocalDate.of(2000, 11, 29));
        init("3/3", LocalDate.of(1990, 9, 14));
        List<Operation> operationList = operationService.findAllOperationAllData();

        OperationDetail operationDetail = operationService.findOperationById((long) operationList.get(1).getId());
        assertEquals("3/3", operationDetail.getOperatingRoom());
        assertEquals(LocalDate.of(1990, 9, 14), operationDetail.getOperationDate());
    }

    @Test
    public void testUpdateOperation() {
        init("4/2",LocalDate.of(1993, 11, 29));
        init("3/1", LocalDate.of(1990, 9, 14));
        List<Operation> operationList = operationService.findAllOperationAllData();

        OperationUpdateCommand operationUpdateCommand = new OperationUpdateCommand();
        operationUpdateCommand.setOperatingRoom("3/1");
        operationUpdateCommand.setOperationDate(LocalDate.of(1990, 9, 14));
        operationUpdateCommand.setPatientId(patientId);
        operationUpdateCommand.setSurgeonId(surgeonId);

        operationUpdateCommand.setOperatingRoom("111");
        operationService.updateOperation((long) operationList.get(1).getId(), operationUpdateCommand);

        OperationDetail operationDetail = operationService.findOperationById((long) operationList.get(1).getId());
        assertEquals("111", operationDetail.getOperatingRoom());
        assertEquals(LocalDate.of(1990, 9, 14), operationDetail.getOperationDate());
    }

    @Test
    public void testDeleteOperation() {
        init("10/10",LocalDate.of(1993, 11, 29));
        init("31/11", LocalDate.of(1990, 9, 14));
        List<Operation> operationList = operationService.findAllOperationAllData();

        operationService.deleteOperation((long) operationList.get(0).getId());

        operationList = operationService.findAllOperationAllData();
        assertEquals(1, operationList.size());
        assertEquals("31/11", operationList.get(0).getOperatingRoom());
    }
    
}
