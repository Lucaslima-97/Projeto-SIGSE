package br.com.projetosigse.service;

import br.com.projetosigse.dto.ChamadaDtos.PatioEvento;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatioNotificador {

    public static final String TOPICO_PATIO = "/topic/patio";

    private final SimpMessagingTemplate messagingTemplate;

    public PatioNotificador(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publicar(PatioEvento evento) {
        messagingTemplate.convertAndSend(TOPICO_PATIO, evento);
    }
}
