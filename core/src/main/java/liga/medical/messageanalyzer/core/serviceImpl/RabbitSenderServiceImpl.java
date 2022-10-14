package liga.medical.messageanalyzer.core.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.medical.messageanalyzer.api.service.RabbitSenderService;
import liga.medical.messageanalyzer.dto.MessageDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitSenderServiceImpl implements RabbitSenderService {

    private final AmqpTemplate amqpTemplate;

    private final ObjectMapper objectMapper;

    public RabbitSenderServiceImpl(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(MessageDTO dto, String queue) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(dto);
        amqpTemplate.convertAndSend(queue, message);
        System.out.println(String.format("The message [%s] sent to the queue [%s]", message, queue));
    }
}