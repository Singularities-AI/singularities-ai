package com.singularities.api.service;

import com.singularities.api.data.entity.ChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromptService {

    public Prompt createPromptWithContextsAndHistories(ChatModel chat, String globalContext) {
        List<Message> messages = new ArrayList<>();

        if (chat.getAgent() != null) {
            messages.add(new SystemMessage("Prompt : " + chat.getAgent().getPrompt()));
        }

        if (globalContext != null && !globalContext.trim().isEmpty()) {
            messages.add(new SystemMessage("User context : " + globalContext));
        }

        if (chat.getContext() != null && !chat.getContext().trim().isEmpty()) {
            messages.add(new SystemMessage("Chat context : " + chat.getContext()));
        }

        //Markdown
        String markdownInstruction = """
        Always respond in **Markdown format**.
        Your response should be fully structured and ready to render in a Markdown viewer or frontend UI.
        Follow these guidelines:

        1. Use headings (#, ##, ###) for sections and sub-sections.
        2. Use bullet points (- or *) and numbered lists (1., 2., 3.) where appropriate.
        3. Use code blocks (```language ... ```) for code examples.
        4. Use tables with headers for structured data.
        5. Use bold (**text**) and italics (*text*) for emphasis.
        6. Include links ([title](url)) when referencing resources.
        7. Avoid plain text without Markdown formatting.
        8. Make the response clear, readable, and visually organized.

        Example of a properly formatted response:

        # Main Title
        ## Subsection
        Here is a bullet list:
        - Item 1
        - Item 2

        Here is a numbered list:
        1. First step
        2. Second step

        Code example:
        ```java
        System.out.println("Hello, world!");
        ```

        Table example:

        | Column 1 | Column 2 |
        |----------|----------|
        | Value 1  | Value 2  |
        | Value 3  | Value 4  |

        Link example: [OpenAI](https://www.openai.com)
        """;
        messages.add(new SystemMessage(markdownInstruction));

        //gen agent response with chat histories
        List<Message> chatMessages = chat.getMessages().stream()
                .map(msg -> {
                    return switch (msg.getRole()) {
                        case "USER" -> new UserMessage(msg.getContent());
                        case "AGENT" -> new AssistantMessage(msg.getContent());
                        default -> throw new IllegalArgumentException("Unknown message role: " + msg.getRole());
                    };
                })
                .collect(Collectors.toList());

        messages.addAll(chatMessages);

        //use chat ollama model
        OllamaOptions options = OllamaOptions.builder().model(chat.getModel().getCode()).build();
        return new Prompt(messages, options);
    }
}
