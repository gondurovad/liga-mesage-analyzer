package liga.medical.messageanalyzer.dto;

import lombok.Data;

@Data
public class MessageDTO {

    private MessageType type;

    private String body;
}