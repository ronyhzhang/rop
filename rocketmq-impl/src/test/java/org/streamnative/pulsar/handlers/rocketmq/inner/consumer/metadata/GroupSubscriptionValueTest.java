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

package org.streamnative.pulsar.handlers.rocketmq.inner.consumer.metadata;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import org.junit.Before;
import org.junit.Test;
import org.streamnative.pulsar.handlers.rocketmq.inner.exception.RopDecodeException;
import org.streamnative.pulsar.handlers.rocketmq.inner.exception.RopEncodeException;

/**
 * Test GroupOffsetValueTest Class.
 */
public class GroupSubscriptionValueTest {

    private GroupSubscriptionValue groupSubscriptionValue;

    @Before
    public void init() {
        groupSubscriptionValue = new GroupSubscriptionValue();
        groupSubscriptionValue.setVersion((short) 2);
        groupSubscriptionValue.setBrokerId(2);
        groupSubscriptionValue.setConsumeBroadcastEnable(true);
        groupSubscriptionValue.setConsumeEnable(true);
        groupSubscriptionValue.setConsumeFromMinEnable(true);
        groupSubscriptionValue.setGroupName("abc");
        groupSubscriptionValue.setNotifyConsumerIdsChangedEnable(true);
        groupSubscriptionValue.setRetryMaxTimes(10);
        groupSubscriptionValue.setRetryQueueNums(5);
        groupSubscriptionValue.setWhichBrokerWhenConsumeSlowly(9);
    }

    @Test
    public void encodeDecodeTest() throws RopEncodeException, RopDecodeException {
        ByteBuffer buffer = groupSubscriptionValue.encode();
        buffer.rewind();
        GroupSubscriptionValue temp = new GroupSubscriptionValue();
        temp.decode(buffer);
        assertEquals(groupSubscriptionValue, temp);
    }
}
