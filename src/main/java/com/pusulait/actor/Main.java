package com.pusulait.actor;

import akka.actor.typed.ActorSystem;
import kamon.Kamon;
import kamon.prometheus.PrometheusReporter;
import kamon.zipkin.ZipkinReporter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {

        Kamon.addReporter(new PrometheusReporter());
        Kamon.addReporter(new ZipkinReporter());

        ActorSystem<PersonManagerBehavior.Command> people =
                ActorSystem.create(PersonManagerBehavior.create(), "People");
        people.tell(new PersonManagerBehavior.CreateCommand(Utils.COMMAND_NAME));
    }
}
