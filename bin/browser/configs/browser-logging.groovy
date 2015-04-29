/*
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.classic.jul.LevelChangePropagator
import static ch.qos.logback.classic.Level.*

context = new LevelChangePropagator()
context.resetJUL = true

def appenders = []

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%-5level %d{HH:mm:ss.SSS} %logger{36} - %msg%n%rEx"
    }
}
appenders << "CONSOLE"

/* Set up loggers */
logger("sorcer", INFO)
logger("sorcer.ssb", WARN)

root(INFO, appenders)

