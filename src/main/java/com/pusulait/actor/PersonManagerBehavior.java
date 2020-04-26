package com.pusulait.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PersonManagerBehavior extends AbstractBehavior<PersonManagerBehavior.Command> {

    public interface Command extends Serializable {
    }

    @Value
    @AllArgsConstructor
    public static class CreateCommand implements PersonManagerBehavior.Command {

        public static final long serialVersionUID = 1L;
        private String message;
    }

    @Value
    @AllArgsConstructor
    public static class ResultCommand implements PersonManagerBehavior.Command {
        public static final long serialVersionUID = 1L;
        private Person person;

    }

    public static Behavior<Command> create() {
        return Behaviors.setup(PersonManagerBehavior::new);
    }

    private PersonManagerBehavior(ActorContext<Command> context) {
        super(context);
    }

    private List<Person> personList = new ArrayList<>();


    @Override
    public Receive<PersonManagerBehavior.Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(CreateCommand.class, command -> {
                    if (command.getMessage().equals(Utils.COMMAND_NAME)) {
                        for (int i = 0; i < 1000 * 1; i++) {
                            ActorRef<PersonWorkerBehavior.Command> worker =
                                    getContext().spawn(PersonWorkerBehavior.create(), "worker" + i);

                            worker.tell(new PersonWorkerBehavior.Command(Utils.COMMAND_NAME, getContext().getSelf()));
                        }
                    }
                    return this;
                })
                .onMessage(ResultCommand.class, command -> {
                    personList.add(command.getPerson());
                    log.debug("Person List Size : {}", personList.size());
                    return this;
                })
                .build();
    }
}
