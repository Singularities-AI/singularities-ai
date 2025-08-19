package com.singularities.api.service;

import com.singularities.api.data.constant.ESettingKey;
import com.singularities.api.data.entity.SettingModel;
import com.singularities.api.data.entity.SettingValueModel;
import com.singularities.api.data.repository.SettingRepository;
import com.singularities.api.data.repository.SettingValueRepository;
import com.singularities.api.exception.SingularitiesAINotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.singularities.api.exception.ExceptionMessage.SETTING_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;
    private final SettingValueRepository settingValueRepository;

    public SettingModel getByKey(ESettingKey key) {
        return settingRepository.findByKey(key).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(SETTING_NOT_FOUND, key))
        );
    }


    public Optional<SettingModel> getOptionalByKey(ESettingKey key) {
        return settingRepository.findByKey(key);
    }


    @Transactional
    public SettingModel createOrUpdateValues(ESettingKey key, List<String> values) {
        SettingModel setting = getOptionalByKey(key).orElse(new SettingModel(key));

        if (!setting.isMultiple()) {
            setting.getValues().clear();
        }

        Set<String> existingValues = setting.getValues()
                .stream()
                .map(SettingValueModel::getValue).collect(Collectors.toSet());

        for (String v : values) {
            if (!existingValues.contains(v)) {
                SettingValueModel val = new SettingValueModel();
                val.setValue(v);
                val.setSetting(setting);
                setting.getValues().add(val);
            }
        }

        return settingRepository.save(setting);
    }


    @Transactional
    public void deleteValue(ESettingKey key, String value) {
        settingValueRepository.deleteBySettingAndValue(getByKey(key), value);
    }
}
