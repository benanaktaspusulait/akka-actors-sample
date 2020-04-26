package com.pusulait.actor;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Person {

    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
}
