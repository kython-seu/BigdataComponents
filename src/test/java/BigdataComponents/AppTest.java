package BigdataComponents;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
        /**
        
        import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.FetchResponse;
import kafka.consumer.SimpleConsumer;
import kafka.coordinator.GroupMetadataManager;
import kafka.message.ByteBufferMessageSet;
import kafka.message.Message;
import kafka.message.MessageAndOffset;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Utils;
import scala.collection.Iterator;


/**
 * Created by zhangkai12 on 2017/12/21.
 */
public class ReadOffset {

    public static void main(String[] args) throws Exception{

        //Class klass = Class.forName("kafka.coordinator.group.GroupMetadataManager$OffsetsMessageFormatter");
        Class klass = Class.forName("kafka.coordinator.GroupMetadataManager$OffsetsMessageFormatter");
        GroupMetadataManager.OffsetsMessageFormatter formatter = (GroupMetadataManager.OffsetsMessageFormatter) klass.newInstance();
        String topic = "__consumer_offsets";
        String groupID = "group_test"; // 换成你的group ID
        int leader = Math.abs(groupID.hashCode()) % 50;

        SimpleConsumer consumer = new SimpleConsumer("localhost", 9092, 30000, 30000, "test");

        FetchRequest request = new FetchRequestBuilder()
                .addFetch(topic, leader, 0, 1024 * 1024).build();

        FetchResponse resp = consumer.fetch(request);
        ByteBufferMessageSet messages = resp.messageSet(topic, leader);
        Iterator<MessageAndOffset> iter = messages.iterator();
        while (iter.hasNext()) {
            MessageAndOffset messageAndOffset = iter.next();
            Message message = messageAndOffset.message();
            byte[] key = message.hasKey() ? Utils.readBytes(message.key()) : null;
            byte[] value = message.isNull() ? null : Utils.readBytes(message.payload());
            int serializedKeySize = message.hasKey() ? key.length : -1;
            int serializedValueSize = message.isNull() ? -1 : value.length;

            ConsumerRecord<byte[], byte[]> record = new ConsumerRecord<>(
                    topic, leader, 0, message.timestamp(),
                    message.timestampType(), message.checksum(), serializedKeySize, serializedValueSize, key, value);
            formatter.writeTo(record, System.out);
        }
        consumer.close();
    }
}

        **/
    }
}
