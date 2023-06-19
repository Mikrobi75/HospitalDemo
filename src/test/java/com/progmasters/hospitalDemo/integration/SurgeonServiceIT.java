package com.progmasters.hospitalDemo.integration;

import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonCommand;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonUpdateCommand;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonDetail;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonListItem;
import com.progmasters.hospitalDemo.service.SurgeonService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class SurgeonServiceIT {
    @Autowired
    private SurgeonService surgeonService;

    void init(String firstName, String lastName, LocalDate beginOfPractice) {
        SurgeonCommand surgeonCommand = new SurgeonCommand();
        surgeonCommand.setFirstName(firstName);
        surgeonCommand.setLastName(lastName);
        surgeonCommand.setBeginOfPractice(beginOfPractice);

        surgeonService.saveSurgeon(surgeonCommand);
    }

    @Test
    public void testListSurgeonListTwo() {
        List<SurgeonListItem> surgeonList = surgeonService.findAll();
        long sizeBefore = surgeonList.size();
        init("Lajos", "Nagy", LocalDate.of(2000, 1, 30));
        init("Béla", "Kis", LocalDate.of(1995, 9, 13));

        surgeonList = surgeonService.findAll();
        assertEquals(sizeBefore + 2, surgeonList.size());
    }

    @Test
    public void testListSurgeonOne() {
        init("Ádám", "Kovács", LocalDate.of(2003, 11, 20));
        init("Ferenc", "Varga", LocalDate.of(1990, 10, 10));
        List<Surgeon> surgeonList = surgeonService.findAllSurgeonAllData();

        SurgeonDetail surgeonDetail = surgeonService.findSurgeonById((long) surgeonList.get(1).getId());
        assertEquals("Varga", surgeonDetail.getLastName());
        assertEquals("Ferenc", surgeonDetail.getFirstName());
        assertEquals(LocalDate.of(1990, 10, 10), surgeonDetail.getBeginOfPractice());
    }

    @Test
    public void testUpdateSurgeon() {
        init("Géza", "Kerékgyártó", LocalDate.of(1989, 3, 22));
        init("János", "Tóth", LocalDate.of(1985, 4, 15));
        List<Surgeon> surgeonList = surgeonService.findAllSurgeonAllData();

        SurgeonUpdateCommand surgeonUpdateCommand = new SurgeonUpdateCommand();
        surgeonUpdateCommand.setFirstName("János");
        surgeonUpdateCommand.setLastName("Tóth");
        surgeonUpdateCommand.setBeginOfPractice(LocalDate.of(1985, 4, 15));
        surgeonUpdateCommand.setFirstName("Lajoska");
        surgeonService.updateSurgeon((long) surgeonList.get(1).getId(), surgeonUpdateCommand);

        SurgeonDetail surgeonDetail = surgeonService.findSurgeonById((long) surgeonList.get(1).getId());
        assertEquals("Lajoska", surgeonDetail.getFirstName());
        assertEquals("Tóth", surgeonDetail.getLastName());
        assertEquals(LocalDate.of(1985, 4, 15), surgeonDetail.getBeginOfPractice());
    }

    @Test
    public void testDeleteSurgeon() {
        init("Ágota", "Almási", LocalDate.of(1979, 4, 21));
        init("Noémi", "Jánosi", LocalDate.of(1975, 5, 17));
        List<Surgeon> surgeonList = surgeonService.findAllSurgeonAllData();

        surgeonService.deleteSurgeon((long) surgeonList.get(0).getId());

        surgeonList = surgeonService.findAllSurgeonAllData();
        assertEquals(1, surgeonList.size());
        assertEquals("Noémi", surgeonList.get(0).getFirstName());
    }
}
