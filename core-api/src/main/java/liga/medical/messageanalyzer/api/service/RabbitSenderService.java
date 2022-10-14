package liga.medical.messageanalyzer.api.service;

import liga.medical.messageanalyzer.dto.MessageDTO;

public interface RabbitSenderService {

    void sendMessage(MessageDTO dto, String queue) throws com.fasterxml.jackson.core.JsonProcessingException;
}