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

package org.streamnative.rocketmq.example.namespace;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * Producer with namespace.
 */
public class ProducerWithNamespace {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("test1|InstanceTest", "pidTest");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("topicTest", "TagA", "Hello world".getBytes("GBK"));
            try {
                SendResult result = producer.send(message);
                System.out.printf("Topic:%s send success, misId is:%s%n", message.getTopic(), result.getMsgId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        producer.shutdown();
    }
}
