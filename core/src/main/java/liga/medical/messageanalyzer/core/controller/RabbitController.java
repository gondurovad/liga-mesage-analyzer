package liga.medical.messageanalyzer.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import liga.medical.common.dto.RabbitMessageDTO;
import liga.medical.messageanalyzer.api.service.RabbitSenderService;
import liga.medical.messageanalyzer.core.annotations.DBLog;
import liga.medical.messageanalyzer.core.config.RabbitConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messageanalyzer")
public class RabbitController {

    private final RabbitSenderService rabbitSenderService;

    public RabbitController(RabbitSenderService rabbitSenderService) {
        this.rabbitSenderService = rabbitSenderService;
    }

    @DBLog
    @PostMapping("/send")
    public void sendMessage(@RequestBody RabbitMessageDTO dto) throws JsonProcessingException {
        rabbitSenderService.sendMessage(dto, RabbitConfig.ROUTER_QUEUE_NAME);
    }
}