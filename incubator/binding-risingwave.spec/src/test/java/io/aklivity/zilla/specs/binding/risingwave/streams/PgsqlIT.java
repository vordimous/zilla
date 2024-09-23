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
package io.aklivity.zilla.specs.binding.risingwave.streams;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import io.aklivity.k3po.runtime.junit.annotation.Specification;
import io.aklivity.k3po.runtime.junit.rules.K3poRule;

public class PgsqlIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("app", "io/aklivity/zilla/specs/binding/risingwave/streams/pgsql");

    private final TestRule timeout = new DisableOnDebug(new Timeout(10, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "${app}/create.table.with.primary.key/client",
        "${app}/create.table.with.primary.key/server"
    })
    public void shouldCreateTableWithPrimaryKey() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/create.table/client",
        "${app}/create.table/server"
    })
    public void shouldCreateTable() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/create.materialized.view/client",
        "${app}/create.materialized.view/server"
    })
    public void shouldCreateMaterializedView() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/query.with.multiple.statements/client",
        "${app}/query.with.multiple.statements/server"
    })
    public void shouldHandleQueryWithMultiStatements() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/create.function/client",
        "${app}/create.function/server"
    })
    public void shouldCreateFunction() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/create.function.embedded/client",
        "${app}/create.function.embedded/server" })
    public void shouldCreateFunctionEmbedded() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/create.table.with.includes/client",
        "${app}/create.table.with.includes/server" })
    public void shouldCreateTableWithIncludes() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/create.table.with.primary.key.and.includes/client",
        "${app}/create.table.with.primary.key.and.includes/server" })
    public void shouldCreateTableWithPrimaryKeyAndIncludes() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/show.tables.with.newline/client",
        "${app}/show.tables.with.newline/server" })
    public void shouldShowTablesWithNewline() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${app}/create.tables/client",
        "${app}/create.tables/server" })
    public void shouldCreateTables() throws Exception
    {
        k3po.finish();
    }
}