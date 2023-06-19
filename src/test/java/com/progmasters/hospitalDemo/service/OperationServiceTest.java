package com.progmasters.hospitalDemo.service;

import com.progmasters.hospitalDemo.domain.Operation;
import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.*;
import com.progmasters.hospitalDemo.repository.OperationRepository;
import com.progmasters.hospitalDemo.repository.PatientRepository;
import com.progmasters.hospitalDemo.repository.SurgeonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {

    private OperationService operationService;

    @Mock
    private OperationRepository operationRepositoryMock;
    @Mock
    private SurgeonRepository surgeonRepositoryMock;
    @Mock
    private PatientRepository patientRepositoryMock;

    private ModelMapper modelMapper;

    private Patient patient;
    private Surgeon surgeon;


    @BeforeEach
    public void setUp() {

        surgeon = new Surgeon((long) 1,
                "Béla",
                "Nagy",
                LocalDate.of(2003, 11, 20),
                null);

        SurgeonUpdateCommand surgeonUpdateCommand = new SurgeonUpdateCommand();
        surgeonUpdateCommand.setFirstName("Béla");
        surgeonUpdateCommand.setLastName("Nagy");
        surgeonUpdateCommand.setBeginOfPractice(LocalDate.of(2003, 11, 20));

        when(surgeonRepositoryMock.findById((long)1)).thenReturn(Optional.of(surgeon));



        patient = new Patient((long) 1,
                "Béla",
                "Nagy",
                "11234444",
                null);

        PatientUpdateCommand patientUpdateCommand = new PatientUpdateCommand();
        patientUpdateCommand.setFirstName("Béla");
        patientUpdateCommand.setLastName("Nagy");
        patientUpdateCommand.setTajNumber("11234444");

        when(patientRepositoryMock.findById((long)1)).thenReturn(Optional.of(patient));

        modelMapper = modelMapperCreate();

        operationService = new OperationService(operationRepositoryMock,
                surgeonRepositoryMock,
                patientRepositoryMock,
                modelMapper);
    }

    @Test
    public void testUpdateOperation() {

        Operation originalOperation = new Operation((long) 1,
                LocalDate.of(2003, 11, 20),
                "2/2",
                patient,
                surgeon);

        OperationUpdateCommand operationUpdateCommand = new OperationUpdateCommand();
        operationUpdateCommand.setOperationDate(LocalDate.of(2003, 11, 20));
        operationUpdateCommand.setOperatingRoom("2/2");
        operationUpdateCommand.setSurgeonId(surgeon.getId());
        operationUpdateCommand.setPatientId(patient.getId());

        when(operationRepositoryMock.findById((long) 1)).thenReturn(Optional.of(originalOperation));
        when(operationRepositoryMock.save(Mockito.any(Operation.class))).thenAnswer(returnsFirstArg());

        Operation updatedOperation = operationService.updateOperation((long) 1, operationUpdateCommand);

        assertEquals(LocalDate.of(2003, 11, 20), updatedOperation.getOperationDate());
        assertEquals("2/2", updatedOperation.getOperatingRoom());
        assertEquals(patient.getId(), updatedOperation.getPatient().getId());
        assertEquals(surgeon.getId(), updatedOperation.getSurgeon().getId());

        verify(operationRepositoryMock, times(1)).findById((long) 1);
        verify(operationRepositoryMock, times(1)).save(Mockito.any(Operation.class));
        verifyNoMoreInteractions(operationRepositoryMock);
    }

    @Test
    public void testInsertOperation() {
        OperationCommand operationCommand = new OperationCommand();
        operationCommand.setOperationDate(LocalDate.of(2003, 11, 20));
        operationCommand.setOperatingRoom("2/2");
        operationCommand.setSurgeonId(surgeon.getId());
        operationCommand.setPatientId(patient.getId());

        when(operationRepositoryMock.save(Mockito.any(Operation.class))).thenAnswer(returnsFirstArg());

        Operation insertedOperation = operationService.saveOperation(operationCommand);

        assertEquals(LocalDate.of(2003, 11, 20), insertedOperation.getOperationDate());
        assertEquals("2/2", insertedOperation.getOperatingRoom());
        assertEquals(patient.getId(), insertedOperation.getPatient().getId());
        assertEquals(surgeon.getId(), insertedOperation.getSurgeon().getId());

        verify(operationRepositoryMock, times(1)).save(Mockito.any(Operation.class));
        verifyNoMoreInteractions(operationRepositoryMock);
    }

    public ModelMapper modelMapperCreate() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
