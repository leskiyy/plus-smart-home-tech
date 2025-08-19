package ru.yandex.practicum.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.entity.Scenario;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.repository.ActionRepository;
import ru.yandex.practicum.repository.ConditionRepository;
import ru.yandex.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.repository.SensorRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HubListener {

    private final Map<Class<?>, HubEventHandler> hubEventHandlers;

    public HubListener(Set<HubEventHandler> hubEventHandlers) {
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getPayloadClass, Function.identity()));
    }

    @KafkaListener(topics = "${topic.hub-event}", containerFactory = "hubEventListenerFactory")
    public void listenHubEvents(HubEventAvro hubEventAvro) {
        Class<?> key = hubEventAvro.getPayload().getClass();
        HubEventHandler hubEventHandler = hubEventHandlers.get(key);
        hubEventHandler.handle(hubEventAvro);
    }

}
