/*
 * Copyright 2019-2021 M. Sean Gilligan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.supernaut.fx.test;

import app.supernaut.BackgroundApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Noop background app for testing
 */
public class NoopBackgroundApp implements BackgroundApp {
    private static final Logger log = LoggerFactory.getLogger(NoopBackgroundApp.class);

    @Override
    public void start() {
        log.info("Start");
    }

    @Override
    public void stop() {
        log.info("Stop");
    }
}
