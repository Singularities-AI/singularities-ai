package com.singularities.api.controller;

import com.singularities.api.data.constant.ESettingKey;
import com.singularities.api.dto.response.SettingResponseDto;
import com.singularities.api.mapper.SettingMapper;
import com.singularities.api.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/web/settings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SettingController {

    private final SettingService settingService;
    private final SettingMapper settingMapper;

    @GetMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public SettingResponseDto getByKey(@PathVariable ESettingKey key) {
        return settingMapper.toDto(settingService.getByKey(key));
    }


    //Values ----

    @PostMapping("/{key}/values")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public SettingResponseDto createOrUpdateValues(@PathVariable ESettingKey key, @RequestBody List<String> values) {
        return settingMapper.toDto(settingService.createOrUpdateValues(key, values));
    }

    @DeleteMapping("/{key}/values")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteValue(@PathVariable ESettingKey key, @RequestParam String value) {
        settingService.deleteValue(key, value);
    }
}