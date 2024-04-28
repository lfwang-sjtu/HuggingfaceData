package org.example.Spout;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class KafkaJsonSpout extends BaseRichSpout {
    private SpoutOutputCollector spoutOutputCollector;
    private static final String[] staticJsonData =
            new String[] {
            "{\"modelId\": \"albert-base-v1\", \"sha\": \"aeffd769076a5c4f83b2546aea99ca45a15a5da4\", \"lastModified\": \"2021-01-13T15:08:24.000Z\", \"pipeline_tag\": \"fill-mask\", \"private\": false, \"author\": null, \"config\": {\"architectures\": [\"AlbertForMaskedLM\"], \"model_type\": \"albert\"}, \"securityStatus\": null, \"_id\": \"621ffdc036468d709f174328\", \"id\": \"albert-base-v1\", \"cardData\": {\"tags\": [\"exbert\"], \"language\": \"en\", \"license\": \"apache-2.0\", \"datasets\": [\"bookcorpus\", \"wikipedia\"]}, \"likes\": 1, \"downloads\": 73054, \"library_name\": \"transformers\"}",
            "{\"modelId\": \"albert-base-v2\", \"sha\": \"51dbd9db43a0c6eba97f74b91ce26fface509e0b\", \"lastModified\": \"2021-08-30T12:04:48.000Z\", \"pipeline_tag\": \"fill-mask\", \"private\": false, \"author\": null, \"config\": {\"architectures\": [\"AlbertForMaskedLM\"], \"model_type\": \"albert\"}, \"securityStatus\": null, \"_id\": \"621ffdc036468d709f174329\", \"id\": \"albert-base-v2\", \"cardData\": {\"language\": \"en\", \"license\": \"apache-2.0\", \"datasets\": [\"bookcorpus\", \"wikipedia\"]}, \"likes\": 37, \"downloads\": 2578721, \"library_name\": \"transformers\"}",
            "{\"modelId\": \"albert-large-v1\", \"sha\": \"17aeb59edfd7c7732fb96248a95f5c271a9fa28f\", \"lastModified\": \"2021-01-13T15:29:06.000Z\", \"pipeline_tag\": \"fill-mask\", \"private\": false, \"author\": null, \"config\": {\"architectures\": [\"AlbertForMaskedLM\"], \"model_type\": \"albert\"}, \"securityStatus\": null, \"_id\": \"621ffdc036468d709f17432a\", \"id\": \"albert-large-v1\", \"cardData\": {\"language\": \"en\", \"license\": \"apache-2.0\", \"datasets\": [\"bookcorpus\", \"wikipedia\"]}, \"likes\": 0, \"downloads\": 739, \"library_name\": \"transformers\"}"
    };
    private Random random;
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector = spoutOutputCollector;
        this.random = new Random();
    }

    @Override
    public void nextTuple() {
        Utils.sleep(3000);
        String jsonString = staticJsonData[this.random.nextInt(staticJsonData.length)];
        System.out.println("*******************" + jsonString + "**************");
        this.spoutOutputCollector.emit(new Values(jsonString));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("json_data"));
    }
}
