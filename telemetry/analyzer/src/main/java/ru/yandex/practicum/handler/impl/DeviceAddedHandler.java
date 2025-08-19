package ru.yandex.practicum.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.entity.Sensor;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.repository.SensorRepository;

@Component
@RequiredArgsConstructor
public class DeviceAddedHandler implements HubEventHandler {

    private final SensorRepository sensorRepository;

    @Override
    public Class<?> getPayloadClass() {
        return DeviceAddedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro event) {
        String hubId = event.getHubId();
        DeviceAddedEventAvro payload = (DeviceAddedEventAvro) event.getPayload();
        String sensorId = payload.getId();

        Sensor sensor = Sensor.builder()
                .id(sensorId)
                .hubId(hubId)
                .build();

        sensorRepository.saveAndFlush(sensor);
    }
}
