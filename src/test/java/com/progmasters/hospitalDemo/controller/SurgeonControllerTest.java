package com.progmasters.hospitalDemo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonCommand;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonListItem;
import com.progmasters.hospitalDemo.exceptionhandling.GlobalExceptionHandler;
import com.progmasters.hospitalDemo.repository.SurgeonRepository;
import com.progmasters.hospitalDemo.service.SurgeonService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class SurgeonControllerTest {

    private MockMvc mockMvc;

    private MockHttpSession session;

    @Mock
    private SurgeonService surgeonServiceMock;

    @BeforeEach
    public void setUp() {
        SurgeonController surgeonController = new SurgeonController(surgeonServiceMock);
        session = new MockHttpSession();
        session.setAttribute("userName", "robi");

        mockMvc = MockMvcBuilders.standaloneSetup(surgeonController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetSurgeonList () throws Exception {
        Surgeon surgeon1 = new Surgeon((long) 1,
                "Béla",
                "Nagy",
                LocalDate.of(2000, 11, 20),
                null);

        Surgeon surgeon2 = new Surgeon((long) 2,
                "Géza",
                "Kis",
                LocalDate.of(1995, 9, 10),
                null);

        List<SurgeonListItem> surgeonListItemList = Stream.of(surgeon1, surgeon2).
                map(SurgeonListItem::new).collect(Collectors.toList());

        when(surgeonServiceMock.findAll()).thenReturn(surgeonListItemList);

        this.mockMvc.perform(get("/api/surgeons").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Béla")))
                .andExpect(jsonPath("$[0].lastName", is("Nagy")))
                .andExpect(jsonPath("$[0].beginOfPractice[0]", is(LocalDate.of(2000, 11, 20).getYear())))
                .andExpect(jsonPath("$[0].beginOfPractice[1]", is(LocalDate.of(2000, 11, 20).getMonthValue())))
                .andExpect(jsonPath("$[0].beginOfPractice[2]", is(LocalDate.of(2000, 11, 20).getDayOfMonth())))
                .andExpect(jsonPath("$[1].firstName", is("Géza")))
                .andExpect(jsonPath("$[1].lastName", is("Kis")))
                .andExpect(jsonPath("$[1].beginOfPractice[0]", is(LocalDate.of(1995, 9, 10).getYear())))
                .andExpect(jsonPath("$[1].beginOfPractice[1]", is(LocalDate.of(1995, 9, 10).getMonthValue())))
                .andExpect(jsonPath("$[1].beginOfPractice[2]", is(LocalDate.of(1995, 9, 10).getDayOfMonth())))
        ;

        verify(surgeonServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(surgeonServiceMock);
    }

    @Test
    public void testPostSurgeon() throws Exception {
        Surgeon surgeon = new Surgeon();
        surgeon.setFirstName("Géza");
        surgeon.setLastName("Kis");
        surgeon.setBeginOfPractice(LocalDate.of(1995, 9, 10));

        SurgeonCommand surgeonCommand = new SurgeonCommand(surgeon);

        this.mockMvc.perform(
                        post("/api/surgeons").session(session)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(surgeonCommand))

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
