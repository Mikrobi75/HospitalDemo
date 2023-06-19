package com.progmasters.hospitalDemo.service;

import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonCommand;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonUpdateCommand;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonDetail;
import com.progmasters.hospitalDemo.repository.SurgeonRepository;
import com.progmasters.hospitalDemo.service.OperationService;
import com.progmasters.hospitalDemo.service.SurgeonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SurgeonServiceTest {

    private SurgeonService surgeonService;

    @Mock
    private SurgeonRepository surgeonRepositoryMock;
    @Mock
    private OperationService operationServiceMock;

    @BeforeEach
    public void setUp() {
        surgeonService = new SurgeonService(surgeonRepositoryMock, operationServiceMock);
    }

    @Test
    public void testUpdateSurgeon() {
        Surgeon originalSurgeon = new Surgeon((long) 1,
                "Béla",
                "Nagy",
                LocalDate.of(2003, 11, 20),
                null);

        SurgeonUpdateCommand surgeonUpdateCommand = new SurgeonUpdateCommand();
        surgeonUpdateCommand.setFirstName("Béla");
        surgeonUpdateCommand.setLastName("Nagy");
        surgeonUpdateCommand.setBeginOfPractice(LocalDate.of(2003, 11, 20));

        when(surgeonRepositoryMock.findById((long)1)).thenReturn(Optional.of(originalSurgeon));
        when(surgeonRepositoryMock.save(Mockito.any(Surgeon.class))).thenAnswer(returnsFirstArg());

        Surgeon updatedSurgeon = surgeonService.updateSurgeon((long)1, surgeonUpdateCommand);

        assertEquals("Béla", updatedSurgeon.getFirstName());
        assertEquals("Nagy", updatedSurgeon.getLastName());
        assertEquals(LocalDate.of(2003, 11, 20), updatedSurgeon.getBeginOfPractice());

        verify(surgeonRepositoryMock,times(1)).findById((long)1);
        verify(surgeonRepositoryMock,times(1)).save(Mockito.any(Surgeon.class));
        verifyNoMoreInteractions(surgeonRepositoryMock);
    }

    @Test
    public void testInsertSurgeon() {
        SurgeonCommand surgeonCommand = new SurgeonCommand();
        surgeonCommand.setFirstName("Béla");
        surgeonCommand.setLastName("Nagy");
        surgeonCommand.setBeginOfPractice(LocalDate.of(2003, 11, 20));

        when(surgeonRepositoryMock.save(Mockito.any(Surgeon.class))).thenAnswer(returnsFirstArg());

        Surgeon insertedSurgeon = surgeonService.saveSurgeon(surgeonCommand);

        assertEquals("Béla", insertedSurgeon.getFirstName());
        assertEquals("Nagy", insertedSurgeon.getLastName());
        assertEquals(LocalDate.of(2003, 11, 20), insertedSurgeon.getBeginOfPractice());

        verify(surgeonRepositoryMock,times(1)).save(Mockito.any(Surgeon.class));
        verifyNoMoreInteractions(surgeonRepositoryMock);
    }
    
/*    @Test
    public void testUpdateSurgeon_surgeonDoesntExist() {

        SurgeonUpdateCommand surgeonUpdateCommand = new SurgeonUpdateCommand();
        surgeonUpdateCommand.setFirstName("Béla");
        surgeonUpdateCommand.setLastName("Nagy");
        surgeonUpdateCommand.setBeginOfPractice(LocalDate.of(2003, 11, 20));

        when(surgeonRepositoryMock.findById((long)2)).thenReturn(Optional.empty());

        Surgeon updatedSurgeon = surgeonService.updateSurgeon((long)2, surgeonUpdateCommand);

        assertNull(updatedSurgeon);

        //verify(surgeonRepositoryMock,times(1)).findById((long)2);
        //verifyNoMoreInteractions(surgeonRepositoryMock);

    }*/

}
