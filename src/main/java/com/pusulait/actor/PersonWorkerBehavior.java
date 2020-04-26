package com.pusulait.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
public class PersonWorkerBehavior extends AbstractBehavior<PersonWorkerBehavior.Command> {

    @Value
    @AllArgsConstructor
    public static class Command implements Serializable {
        private static final long serialVersionUID = 1L;
        private String message;
        private ActorRef<PersonManagerBehavior.Command> sender;

    }

    private PersonWorkerBehavior(ActorContext<PersonWorkerBehavior.Command> context) {
        super(context);
    }

    public static Behavior<PersonWorkerBehavior.Command> create() {
        return Behaviors.setup(PersonWorkerBehavior::new);
    }

    Faker faker = new Faker();

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onAnyMessage(command -> {
                    if (command.getMessage().equals(Utils.COMMAND_NAME)) {
                        Person person = new Person();
                        person.setFirstName(faker.name().firstName());
                        person.setLastName(faker.name().lastName());
                        person.setAge(faker.random().nextInt(75));
                        person.setId(UUID.randomUUID());
                        log.debug("Created Person: {}", person);
                        command.getSender().tell(new PersonManagerBehavior.ResultCommand(person));
                    }
                    return this;
                })
                .build();
    }
}
