/*
 * Copyright 2021-2023 Aklivity Inc
 *
 * Licensed under the Aklivity Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 *   https://www.aklivity.io/aklivity-community-license/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.aklivity.zilla.runtime.catalog.apicurio.internal;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import io.aklivity.k3po.runtime.junit.annotation.Specification;
import io.aklivity.k3po.runtime.junit.rules.K3poRule;
import io.aklivity.zilla.runtime.engine.test.EngineRule;
import io.aklivity.zilla.runtime.engine.test.annotation.Configuration;

public class ApicurioCatalogIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("net", "io/aklivity/zilla/specs/engine/streams/network")
        .addScriptRoot("app", "io/aklivity/zilla/specs/engine/streams/application")
        .addScriptRoot("remote", "io/aklivity/zilla/specs/catalog/apicurio/streams");

    private final TestRule timeout = new DisableOnDebug(new Timeout(10, SECONDS));

    private final EngineRule engine = new EngineRule()
        .directory("target/zilla-itests")
        .countersBufferCapacity(4096)
        .configurationRoot("io/aklivity/zilla/specs/catalog/apicurio/config")
        .external("app0")
        .clean();

    @Rule
    public final TestRule chain = outerRule(engine).around(k3po).around(timeout);

    @Test
    @Configuration("resolve/artifact/global/id/zilla.yaml")
    @Specification({
        "${net}/handshake/client",
        "${app}/handshake/server",
        "${remote}/resolve.artifact.via.global.id" })
    public void shouldResolveArtifactViaGlobalId() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("resolve/artifact/id/subject/version/zilla.yaml")
    @Specification({
        "${net}/handshake/client",
        "${app}/handshake/server",
        "${remote}/resolve.artifact.via.artifactid.version" })
    public void shouldResolveArtifactIdViaSubjectAndVersion() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("resolve/artifact/id/subject/version/latest/zilla.yaml")
    @Specification({
        "${net}/handshake/client",
        "${app}/handshake/server",
        "${remote}/resolve.artifact.latest.version" })
    public void shouldResolveArtifactIdViaSubjectAndLatestVersion() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("resolve/artifact/global/id/cache/zilla.yaml")
    @Specification({
        "${net}/handshake/client",
        "${app}/handshake/server",
        "${remote}/resolve.artifact.via.global.id" })
    public void shouldResolveArtifactViaGlobalIdFromCache() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("resolve/artifact/id/subject/version/cache/zilla.yaml")
    @Specification({
        "${net}/handshake/client",
        "${app}/handshake/server",
        "${remote}/resolve.artifact.via.artifactid.version" })
    public void shouldResolveArtifactIdViaSubjectAndVersionFromCache() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("resolve/artifact/global/id/retry/zilla.yaml")
    @Specification({
        "${net}/handshake/client",
        "${app}/handshake/server",
        "${remote}/resolve.artifact.via.global.id.retry" })
    public void shouldResolveArtifactViaGlobalIdRetry() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("resolve/artifact/id/subject/version/retry/zilla.yaml")
    @Specification({
        "${net}/handshake/client",
        "${app}/handshake/server",
        "${remote}/resolve.artifact.via.artifactid.version.retry" })
    public void shouldResolveArtifactIdViaSubjectAndVersionFromCacheAndRetry() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("resolve/artifact/id/subject/version/failed/zilla.yaml")
    @Specification({
        "${net}/handshake/client",
        "${app}/handshake/server",
        "${remote}/resolve.artifact.via.artifactid.version.failed" })
    public void shouldResolveArtifactIdViaSubjectAndVersionFailed() throws Exception
    {
        k3po.finish();
    }
}