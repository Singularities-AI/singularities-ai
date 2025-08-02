package com.singularities.api.controller;

import com.singularities.api.dto.response.ModelResponseDto;
import com.singularities.api.mapper.ModelMapper;
import com.singularities.api.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web/models")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModelController {

    private final ModelService modelService;
    private final ModelMapper modelMapper;

    //Models ----

    @GetMapping("/availables")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public List<ModelResponseDto> listAvailable() {
        return modelService.findAllAvailable().stream().map(modelMapper::toDto).toList();
    }

    //Admin --

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<ModelResponseDto> list() {
        return modelService.findAll().stream().map(modelMapper::toDto).toList();
    }

    @PostMapping("/{uuid}/download")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void download(@PathVariable UUID uuid) {
        modelService.download(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID uuid) {
        modelService.delete(uuid);
    }

    @PutMapping("/{uuid}/default")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void setDefault(@PathVariable UUID uuid) {
        modelService.setDefault(uuid);
    }
}