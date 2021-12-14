/*
 * Copyright 2021-2021 Aklivity Inc.
 *
 * Aklivity licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.aklivity.zilla.runtime.engine.internal;

import static io.aklivity.zilla.runtime.engine.EngineConfiguration.ENGINE_DIRECTORY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import io.aklivity.zilla.runtime.engine.Engine;
import io.aklivity.zilla.runtime.engine.EngineConfiguration;
import io.aklivity.zilla.runtime.engine.EngineStats;
import io.aklivity.zilla.runtime.engine.ext.EngineExtContext;
import io.aklivity.zilla.runtime.engine.ext.EngineExtSpi;

public class EngineTest
{
    private EngineConfiguration config;

    @Before
    public void initConfig()
    {
        Properties properties = new Properties();
        properties.put(ENGINE_DIRECTORY.name(), "target/zilla-itests");
        config = new EngineConfiguration(properties);
    }

    @Test
    public void shouldConfigureEmpty() throws Exception
    {
        List<Throwable> errors = new LinkedList<>();
        try (Engine drive = Engine.builder()
                .config(config)
                .errorHandler(errors::add)
                .build())
        {
            drive.start().get();
        }
        catch (Throwable ex)
        {
            errors.add(ex);
        }
        finally
        {
            assertThat(errors, empty());
        }
    }

    @Test
    public void shouldConfigure() throws Exception
    {
        String resource = String.format("%s-%s.json", getClass().getSimpleName(), "configure");
        URL configURL = getClass().getResource(resource);
        List<Throwable> errors = new LinkedList<>();
        try (Engine drive = Engine.builder()
                .config(config)
                .configURL(configURL)
                .errorHandler(errors::add)
                .build())
        {
            drive.start().get();

            EngineStats stats = drive.stats("default", "test0");
            assertEquals(0L, stats.initialOpens());
            assertEquals(0L, stats.initialCloses());
            assertEquals(0L, stats.initialErrors());
            assertEquals(0L, stats.initialBytes());
            assertEquals(0L, stats.replyOpens());
            assertEquals(0L, stats.replyCloses());
            assertEquals(0L, stats.replyErrors());
            assertEquals(0L, stats.replyBytes());
        }
        catch (Throwable ex)
        {
            errors.add(ex);
        }
        finally
        {
            assertThat(errors, empty());
        }
    }

    @Test
    public void shouldNotConfigureUnknownScheme() throws Exception
    {
        List<Throwable> errors = new LinkedList<>();
        try (Engine drive = Engine.builder()
                .config(config)
                .configURL(URI.create("unknown://path").toURL())
                .errorHandler(errors::add)
                .build())
        {
            drive.start().get();
        }
        catch (Throwable ex)
        {
            errors.add(ex);
        }
        finally
        {
            assertThat(errors, not(empty()));
        }
    }

    public static final class TestEngineExt implements EngineExtSpi
    {
        @Override
        public void onConfigured(
            EngineExtContext context)
        {
        }

        @Override
        public void onClosed(
            EngineExtContext context)
        {
        }
    }
}