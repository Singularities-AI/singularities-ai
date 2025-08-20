package com.singularities.api.controller;

import com.singularities.api.dto.request.AgentRequestDto;
import com.singularities.api.dto.response.AgentResponseDto;
import com.singularities.api.mapper.AgentMapper;
import com.singularities.api.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web/agents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AgentController {

    private final AgentService agentService;
    private final AgentMapper agentMapper;


    @GetMapping("/agents")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Page<AgentResponseDto> list(Pageable pageable) {
        return agentMapper.toDto(agentService.findAll(pageable));
    }


    //Admin --

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public AgentResponseDto create(@RequestBody AgentRequestDto form) {
        return agentMapper.toDto(agentService.create(form));
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public AgentResponseDto update(@PathVariable UUID uuid, @RequestBody AgentRequestDto form) {
        return agentMapper.toDto(agentService.update(uuid, form));
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID uuid) {
        agentService.delete(uuid);
    }
}