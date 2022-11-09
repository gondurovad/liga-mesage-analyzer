package liga.medical.messageanalyzer.api.service;

import liga.medical.common.dto.RabbitMessageDTO;

public interface RabbitSenderService {

    void sendMessage(RabbitMessageDTO dto, String queue) throws com.fasterxml.jackson.core.JsonProcessingException;
}