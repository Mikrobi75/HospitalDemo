package com.progmasters.hospitalDemo.service;

import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonCommand;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonUpdateCommand;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonDetail;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonListItem;
import com.progmasters.hospitalDemo.repository.SurgeonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SurgeonService {
    private SurgeonRepository surgeonRepository;
    private OperationService operationService;

    @Autowired
    public SurgeonService(SurgeonRepository surgeonRepository,OperationService operationService) {
        this.surgeonRepository = surgeonRepository;
        this.operationService = operationService;
    }

    public Surgeon findById(Long id) {
        return surgeonRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public SurgeonDetail findSurgeonById(Long id) {
        Surgeon surgeon = findById(id);
        return new SurgeonDetail(surgeon);
    }

    public List<SurgeonListItem> findAll() {
        return surgeonRepository.findAll().stream().map(SurgeonListItem::new).collect(Collectors.toList());
    }

    public Surgeon saveSurgeon(SurgeonCommand surgeon) {
        return surgeonRepository.save(new Surgeon(surgeon));
    }

    public Surgeon updateSurgeon(Long surgeonId, SurgeonUpdateCommand surgeonUpdateCommand) {
        Surgeon actualSurgeon = findById(surgeonId);
        actualSurgeon.setFirstName(surgeonUpdateCommand.getFirstName());
        actualSurgeon.setLastName(surgeonUpdateCommand.getLastName());
        actualSurgeon.setBeginOfPractice(surgeonUpdateCommand.getBeginOfPractice());
        return surgeonRepository.save(actualSurgeon);
    }

    public void deleteSurgeon(Long id) {
        operationService.deleteOperationBySurgeonId(id);
        surgeonRepository.delete(findById(id));
    }

    public List<Surgeon> findAllSurgeonAllData() {
        return surgeonRepository.findAll();
    }

}
