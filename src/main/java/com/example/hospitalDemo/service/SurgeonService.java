package com.example.hospitalDemo.service;

import com.example.hospitalDemo.domain.Surgeon;
import com.example.hospitalDemo.dto.incoming.SurgeonCommand;
import com.example.hospitalDemo.dto.incoming.SurgeonUpdateCommand;
import com.example.hospitalDemo.dto.outgoing.SurgeonDetail;
import com.example.hospitalDemo.dto.outgoing.SurgeonListItem;
import com.example.hospitalDemo.repository.SurgeonRepository;
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

    public List<SurgeonListItem> findAllAccount() {
        return surgeonRepository.findAll().stream().map(SurgeonListItem::new).collect(Collectors.toList());
    }

    /*public void saveSurgeon(Surgeon surgeon) {
        surgeonRepository.save(surgeon);
    }*/
    public void saveSurgeon(SurgeonCommand surgeon) {
        surgeonRepository.save(new Surgeon(surgeon));
    }

    public void updateSurgeon(Long surgeonId, SurgeonUpdateCommand surgeonUpdateCommand) {
        Surgeon actualSurgeon = findById(surgeonId);
        actualSurgeon.setFirstName(surgeonUpdateCommand.getFirstName());
        actualSurgeon.setLastName(surgeonUpdateCommand.getLastName());
        actualSurgeon.setBeginOfPractice(surgeonUpdateCommand.getBeginOfPractice());
        surgeonRepository.save(actualSurgeon);
    }

    public void deleteSurgeon(Long id) {
        operationService.deleteOperationBySurgeonId(id);
        surgeonRepository.delete(findById(id));
    }
    
}
