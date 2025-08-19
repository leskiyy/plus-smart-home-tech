package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = Integer.MAX_VALUE)
    private ConditionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", length = Integer.MAX_VALUE)
    private ConditionOperation operation;

    @Column(name = "value")
    private Integer value;

}