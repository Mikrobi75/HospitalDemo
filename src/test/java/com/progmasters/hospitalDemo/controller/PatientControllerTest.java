package com.progmasters.hospitalDemo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.dto.incoming.PatientCommand;
import com.progmasters.hospitalDemo.dto.outgoing.PatientListItem;
import com.progmasters.hospitalDemo.exceptionhandling.GlobalExceptionHandler;
import com.progmasters.hospitalDemo.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    private MockMvc mockMvc;

    private MockHttpSession session;

    @Mock
    private PatientService patientServiceMock;

    @BeforeEach
    public void setUp() {
        PatientController patientController = new PatientController(patientServiceMock);
        session = new MockHttpSession();
        session.setAttribute("userName", "robi");

        mockMvc = MockMvcBuilders.standaloneSetup(patientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetPatientList () throws Exception {
        Patient patient1 = new Patient();
        patient1.setFirstName("Géza");
        patient1.setLastName("Kis");
        patient1.setTajNumber("212141234");

        Patient patient2 = new Patient();
        patient2.setFirstName("Béla");
        patient2.setLastName("Nagy");
        patient2.setTajNumber("4535353534");


        List<PatientListItem> patientListItemList = Stream.of(patient1, patient2).
                map(PatientListItem::new).collect(Collectors.toList());

        when(patientServiceMock.findAll()).thenReturn(patientListItemList);

        this.mockMvc.perform(
                    get("/api/patients").session(session)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2) ))
                .andExpect(jsonPath("$[0].firstName", is("Géza")))
                .andExpect(jsonPath("$[0].lastName", is("Kis")))
                .andExpect(jsonPath("$[0].tajNumber", is("212141234")))
                .andExpect(jsonPath("$[1].firstName", is("Béla")))
                .andExpect(jsonPath("$[1].lastName", is("Nagy")))
                .andExpect(jsonPath("$[1].tajNumber", is("4535353534")))
        ;

        verify(patientServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(patientServiceMock);
    }

    @Test
    public void testPostPatient() throws Exception {
        Patient patient = new Patient();
        patient.setFirstName("Géza");
        patient.setLastName("Kis");
        patient.setTajNumber("212141234");

        PatientCommand patientCommand = new PatientCommand(patient);

        this.mockMvc.perform(
                        post("/api/patients").session(session)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(patientCommand))
                )
                .andExpect(status().isCreated())
        ;

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
