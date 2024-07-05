package saci.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saci.domain.model.Knowledge;
import saci.domain.model.Role;
import saci.domain.service.exceptions.AlreadyExistsException;
import saci.domain.service.exceptions.NotFoundException;
import saci.infrastructure.KnowledgeRepository;
import saci.infrastructure.RoleRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeService {

    private final KnowledgeRepository knowledgeRepository;
    private final RoleRepository roleRepository;

    public Knowledge createKnowledge(Knowledge knowledge) {
        Role role =
                roleRepository
                        .findById(knowledge.getRoleId())
                        .orElseThrow(
                                () -> {
                                    String errorMessage = "Role Not Found";
                                    return new NotFoundException(errorMessage);
                                });

        boolean knowledgeWithNameExists =
                role.getKnowledges().stream()
                        .anyMatch(
                                existingKnowledge ->
                                        existingKnowledge.getName().equals(knowledge.getName()));

        if (knowledgeWithNameExists) {
            String errorMessage = "Knowledge with the same name already exists  ";
            throw new AlreadyExistsException(errorMessage);
        }

        return knowledgeRepository.save(knowledge);
    }

    public List<Knowledge> getKnowledges() {
        return knowledgeRepository.findAll();
    }

    public void deleteKnowledgeById(long knowledgeId) {
        Optional<Knowledge> knowledge = knowledgeRepository.findById(knowledgeId);
        if (knowledge.isPresent()) {
            knowledgeRepository.deleteById(knowledgeId);
        } else {
            String errorMessage = "Knowledge not found with ID: " + knowledgeId;
            throw new NotFoundException(errorMessage);
        }
    }

    public Optional<Knowledge> findById(long knowledgeId) {
        return knowledgeRepository.findById(knowledgeId);
    }

    public List<Knowledge> getKnowledgesByRoleId(Long roleId) {
        return knowledgeRepository.findByRoleId(roleId);
    }

    public Knowledge editKnowledge(Long knowledgeId, Knowledge updatedKnowledge) {
        Knowledge existingKnowledge =
                knowledgeRepository
                        .findById(knowledgeId)
                        .orElseThrow(
                                () -> {
                                    String errorMessage =
                                            "Knowledge not found with ID: " + knowledgeId;
                                    return new NotFoundException(errorMessage);
                                });

        Optional<Knowledge> knowledgeWithSameName =
                knowledgeRepository.findByName(updatedKnowledge.getName());
        if (knowledgeWithSameName.isPresent()
                && !knowledgeWithSameName.get().getId().equals(knowledgeId)) {
            String errorMessage = "Another knowledge with the same name already exists";
            throw new AlreadyExistsException(errorMessage);
        }

        existingKnowledge.setName(updatedKnowledge.getName());
        existingKnowledge.setWeight(updatedKnowledge.getWeight());

        Knowledge savedKnowledge = knowledgeRepository.save(existingKnowledge);
        return savedKnowledge;
    }
}
