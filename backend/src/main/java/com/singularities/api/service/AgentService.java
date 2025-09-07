package com.singularities.api.service;

import com.singularities.api.data.entity.AgentModel;
import com.singularities.api.data.entity.ModelModel;
import com.singularities.api.data.repository.AgentRepository;
import com.singularities.api.data.repository.ChatRepository;
import com.singularities.api.dto.request.AgentRequestDto;
import com.singularities.api.exception.SingularitiesAINotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.singularities.api.exception.ExceptionMessage.AGENT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;
    private final ChatRepository chatRepository;
    private final ModelService modelService;

    public Page<AgentModel> findAll(Pageable pageable) {
        return agentRepository.findAll(pageable);
    }


    public AgentModel findById(UUID uuid) {
        return agentRepository.findById(uuid).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(AGENT_NOT_FOUND, uuid))
        );
    }


    @Transactional
    public void delete(UUID id) {
        //Unlink all conversations linked with this agent.
        chatRepository.unlinkAllChatByAgentId(id);

        agentRepository.deleteById(id);
    }


    public AgentModel update(UUID uuid, AgentRequestDto form) {
        AgentModel agent = findById(uuid);
        ModelModel model = modelService.findByUUID(form.getModelUUID());

        agent.setName(form.getName());
        agent.setDescription(form.getDescription());
        agent.setIcon(form.getIcon());
        agent.setPrompt(form.getPrompt());
        agent.setModel(model);
        return agentRepository.save(agent);
    }


    public AgentModel create(AgentRequestDto form) {
        ModelModel model = modelService.findByUUID(form.getModelUUID());

        AgentModel agent = new AgentModel();
        agent.setName(model.getName());
        agent.setDescription(model.getDescription());
        agent.setIcon(form.getIcon());
        agent.setPrompt(form.getPrompt());
        agent.setModel(model);
        return agentRepository.save(agent);
    }
}
