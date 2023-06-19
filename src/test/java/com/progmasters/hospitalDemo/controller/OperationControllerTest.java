package com.progmasters.hospitalDemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.progmasters.hospitalDemo.domain.Operation;
import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.OperationCommand;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonCommand;
import com.progmasters.hospitalDemo.dto.outgoing.OperationListItem;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonListItem;
import com.progmasters.hospitalDemo.exceptionhandling.GlobalExceptionHandler;
import com.progmasters.hospitalDemo.service.OperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class OperationControllerTest {

    private MockMvc mockMvc;

    private MockHttpSession session;

    @Mock
    private OperationService operationServiceMock;

    @BeforeEach
    public void setUp() {
        OperationController operationController = new OperationController(operationServiceMock);
        session = new MockHttpSession();
        session.setAttribute("userName", "robi");

        mockMvc = MockMvcBuilders.standaloneSetup(operationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetOperationList() throws Exception {
        Operation operation1 = new Operation((long) 1,
                LocalDate.of(2000, 11, 20),
                "1/1",
                null,
                null);

        Operation operation2 = new Operation((long) 1,
                LocalDate.of(2003, 12, 20),
                "2/2",
                null,
                null);

        List<OperationListItem> operationListItemList = Stream.of(operation1, operation2).
                map(OperationListItem::new).collect(Collectors.toList());

        when(operationServiceMock.findAll()).thenReturn(operationListItemList);

        this.mockMvc.perform(get("/api/operations").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].operatingRoom", is("1/1")))
                .andExpect(jsonPath("$[0].operationDate[0]", is(LocalDate.of(2000, 11, 20).getYear())))
                .andExpect(jsonPath("$[0].operationDate[1]", is(LocalDate.of(2000, 11, 20).getMonthValue())))
                .andExpect(jsonPath("$[0].operationDate[2]", is(LocalDate.of(2000, 11, 20).getDayOfMonth())))
                .andExpect(jsonPath("$[1].operatingRoom", is("2/2")))
                .andExpect(jsonPath("$[1].operationDate[0]", is(LocalDate.of(2003, 12, 20).getYear())))
                .andExpect(jsonPath("$[1].operationDate[1]", is(LocalDate.of(2003, 12, 20).getMonthValue())))
                .andExpect(jsonPath("$[1].operationDate[2]", is(LocalDate.of(2003, 12, 20).getDayOfMonth())))
        ;

        verify(operationServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(operationServiceMock);
    }

    @Test
    public void testPostOperation() throws Exception {
        Surgeon surgeon = new Surgeon((long) 1,
                "Béla",
                "Nagy",
                LocalDate.of(2000, 11, 20),
                null);

        Patient patient = new Patient((long) 1,
                "Elemér",
                "Csontos",
                "12343214",
                null);

        Operation operation = new Operation();
        operation.setOperatingRoom("1/3");
        operation.setOperationDate(LocalDate.of(1995, 9, 10));
        operation.setSurgeon(surgeon);
        operation.setPatient(patient);

        OperationCommand operationCommand = new OperationCommand(operation);

        this.mockMvc.perform(
                        post("/api/operations").session(session)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(operationCommand))
                )
                .andExpect(status().isCreated())
        ;

    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
