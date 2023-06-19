package com.progmasters.hospitalDemo.service;

import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.dto.incoming.PatientCommand;
import com.progmasters.hospitalDemo.dto.incoming.PatientUpdateCommand;
import com.progmasters.hospitalDemo.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    private PatientService patientService;

    @Mock
    private PatientRepository patientRepositoryMock;

    @Mock
    private OperationService operationServiceMock;

    @BeforeEach
    public void setUp () {
        patientService = new PatientService(patientRepositoryMock, operationServiceMock);
    }

    @Test
    public void testUpdatePatient() {
        Patient originalPatient = new Patient((long) 1,
                "Béla",
                "Nagy",
                "11234444",
                null);

        PatientUpdateCommand patientUpdateCommand = new PatientUpdateCommand();
        patientUpdateCommand.setFirstName("Béla");
        patientUpdateCommand.setLastName("Nagy");
        patientUpdateCommand.setTajNumber("11234444");

        when(patientRepositoryMock.findById((long)1)).thenReturn(Optional.of(originalPatient));
        when(patientRepositoryMock.save(Mockito.any(Patient.class))).thenAnswer(returnsFirstArg());

        Patient updatedPatient = patientService.updatePatient((long)1, patientUpdateCommand);

        assertEquals("Béla", updatedPatient.getFirstName());
        assertEquals("Nagy", updatedPatient.getLastName());
        assertEquals("11234444", updatedPatient.getTajNumber());

        verify(patientRepositoryMock,times(1)).findById((long)1);
        verify(patientRepositoryMock,times(1)).save(Mockito.any(Patient.class));
        verifyNoMoreInteractions(patientRepositoryMock);
    }

    @Test
    public void testInsertPatient() {
        PatientCommand patientCommand = new PatientCommand();
        patientCommand.setFirstName("Béla");
        patientCommand.setLastName("Nagy");
        patientCommand.setTajNumber("2342342");

        when(patientRepositoryMock.save(Mockito.any(Patient.class))).thenAnswer(returnsFirstArg());

        Patient insertedPatient = patientService.savePatient(patientCommand);

        assertEquals("Béla", insertedPatient.getFirstName());
        assertEquals("Nagy", insertedPatient.getLastName());
        assertEquals("2342342", insertedPatient.getTajNumber());

        verify(patientRepositoryMock,times(1)).save(Mockito.any(Patient.class));
        verifyNoMoreInteractions(patientRepositoryMock);
    }

}
