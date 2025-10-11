ALTER TABLE models
    ADD COLUMN icon VARCHAR(50) NULL;

UPDATE models
SET icon = 'simple-icons:openai'
WHERE code = 'gpt-oss:20b';

UPDATE models
SET icon = 'simple-icons:openai'
WHERE code = 'gpt-oss:120b';

UPDATE models
SET icon = 'ri:deepseek-fill'
WHERE code = 'deepseek-r1:1.5b';

UPDATE models
SET icon = 'ri:deepseek-fill'
WHERE code = 'deepseek-r1:7b';

UPDATE models
SET icon = 'ri:deepseek-fill'
WHERE code = 'deepseek-r1:8b';

UPDATE models
SET icon = 'ri:deepseek-fill'
WHERE code = 'deepseek-r1:14b';

UPDATE models
SET icon = 'ri:deepseek-fill'
WHERE code = 'deepseek-r1:32b';

UPDATE models
SET icon = 'ri:deepseek-fill'
WHERE code = 'deepseek-r1:70b';

UPDATE models
SET icon = 'simple-icons:meta'
WHERE code = 'llama3.1:8b';

UPDATE models
SET icon = 'simple-icons:mistralai'
WHERE code = 'mistral:7b';

UPDATE models
SET icon = 'simple-icons:meta'
WHERE code = 'llama3.2:latest';