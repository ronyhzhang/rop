/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.tdmq.handlers.rocketmq.inner.rest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tencent.tdmq.handlers.rocketmq.utils.RestUtils;
import io.prometheus.client.exporter.common.TextFormat;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pulsar.common.lookup.data.LookupData;

/**
 * Nameserver servlet logic.
 */
public class NameserverServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType(TextFormat.CONTENT_TYPE_004);

        Writer writer = resp.getWriter();

        // TODO: hanmz 2021/4/26 根据host返回对应的broker列表
        writer.write(lookup(req.getHeader("Host")) + "\n\r");
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    private String lookup(String host) {
        Map<String, String> headers = Maps.newHashMap();
        String url = "127.0.0.1:8080/lookup/v2/topic/persistent/public/default/my-topic?listenerName=" + host;
        String rowResponse = RestUtils.get(url, headers);

        LookupData lookupData = JSON.parseObject(rowResponse, LookupData.class);

        String brokerUrl = lookupData.getBrokerUrl();

        brokerUrl = brokerUrl.replaceAll("pulsar://", "");
        brokerUrl = brokerUrl.replaceAll("6650", "9876");

        return brokerUrl;
    }
}
