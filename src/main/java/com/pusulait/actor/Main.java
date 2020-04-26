package com.pusulait.actor;

import akka.actor.typed.ActorSystem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {

        ActorSystem<PersonManagerBehavior.Command> people =
                                            ActorSystem.create(PersonManagerBehavior.create(), "People");

        people.tell( new PersonManagerBehavior.CreateCommand(Utils.COMMAND_NAME));

    }
}
