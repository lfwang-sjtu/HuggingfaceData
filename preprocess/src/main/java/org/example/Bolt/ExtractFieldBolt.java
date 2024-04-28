package org.example.Bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExtractFieldBolt extends BaseRichBolt {
    private OutputCollector outputCollector;
    private Set<String> processedModelIds;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
        this.processedModelIds = new HashSet<>();
    }

    @Override
    public void execute(Tuple tuple) {
        String jsonString = tuple.getStringByField("value");
        JSONObject jsonObject = new JSONObject(jsonString);
        String modelId = jsonObject.optString("modelId", "null_modelId");
        if (processedModelIds.contains(modelId)) {
            return;
        }
        if (!"null_modelId".equals(modelId)) processedModelIds.add(modelId);
        String pipeline_tag = jsonObject.optString("pipeline_tag", "null_pipeline_tag");
        String author = jsonObject.optString("author", "null_author");
        String library_name = jsonObject.optString("library_name", "null_library_name");
        int likes = jsonObject.optInt("likes", 0);
        int downloads = jsonObject.optInt("downloads", 0);

        String datasetsStr = "null_datasets";
        JSONObject cardDataObject = jsonObject.optJSONObject("cardData");
        if (cardDataObject != null) {
            JSONArray datasetsArray = cardDataObject.optJSONArray("datasets");
            if (datasetsArray != null) {
                String[] datasets = new String[datasetsArray.length()];
                for (int i = 0; i < datasetsArray.length(); i++) {
                    datasets[i] = datasetsArray.getString(i);
                }
                datasetsStr = String.join(",", datasets);
            }
        }

        outputCollector.emit(new Values(modelId, pipeline_tag, author, likes, downloads, library_name, datasetsStr));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("modelId", "pipeline_tag", "author", "likes", "downloads", "library_name", "datasetsStr"));
    }
}


//{
//        "modelId": "albert-base-v1",
//        "pipeline_tag": "fill-mask",
//        "author": null,
//        "likes": 1,
//        "downloads": 73054,
//        "library_name": "transformers"
//}