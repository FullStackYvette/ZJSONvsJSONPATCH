import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.flipkart.zjsonpatch.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class JsonComp {

    private JsonNode originNode;
    private JsonNode targetNode;
    static ObjectMapper objectMapper = new ObjectMapper();
    private com.github.fge.jsonpatch.JsonPatch jsonPatch = null;

    private String[] compareResult(String origin, String target) throws Exception
    {
        originNode = objectMapper.readTree(origin);
        targetNode = objectMapper.readTree(target);
        if(originNode.isArray())
            originNode=(ArrayNode)originNode;
        if(targetNode.isArray())
            targetNode = (ArrayNode)targetNode;
        JsonNode zjsonPath = com.flipkart.zjsonpatch.JsonDiff.asJson(originNode,targetNode);
        JsonNode  jsonPatchPath= com.github.fge.jsonpatch.diff.JsonDiff.asJson(originNode,targetNode);
        return new String[]{zjsonPath.toString(),jsonPatchPath.toString()};
    }

    private String[] compareJsonResult(JsonNode origin, JsonNode target) throws Exception
    {
        JsonNode zjsonPath = com.flipkart.zjsonpatch.JsonDiff.asJson(origin,target);
        JsonNode  jsonPatchPath= com.github.fge.jsonpatch.diff.JsonDiff.asJson(origin,target);
        return new String[]{zjsonPath.toString(),jsonPatchPath.toString()};
    }

    //Evaluate jsonPatch using JsonNode origin and target for n times
    private double jsonPatchPerf(int n, JsonNode origin, JsonNode target) throws Exception
    {
        Timer timer = new Timer();
        long startTime = System.nanoTime();
        for(int i=0;i<n;i++)
        {
            JsonNode  jsonPatchPath= com.github.fge.jsonpatch.diff.JsonDiff.asJson(origin,target);
        }
        long estimatedTime = System.nanoTime() - startTime;
        n=n*1000;
        return Math.floor((double)(estimatedTime/n));
    }

    //Evaluate zjsonPatch using JsonNode origin and JsonNode target for n times
    private double zjsonPatchPerf(int n, JsonNode origin, JsonNode target) throws Exception
    {
        Timer timer = new Timer();
        long startTime = System.nanoTime();
        for(int i=0;i<n;i++)
        {
            JsonNode zjsonPath = com.flipkart.zjsonpatch.JsonDiff.asJson(origin,target);
        }
        long estimatedTime = System.nanoTime() - startTime;
        n=n*1000;
        return Math.floor((double)(estimatedTime/n));
    }

    //Evaluate zjsonPatch library using String json files including origin and target for n time
    private double zjsonPatchPerf(int n, String origin, String target) throws Exception
    {
        originNode = objectMapper.readTree(origin);
        targetNode = objectMapper.readTree(target);
        if(originNode.isArray())
            originNode=(ArrayNode)originNode;
        if(targetNode.isArray())
            targetNode = (ArrayNode)targetNode;
        Timer timer = new Timer();
        long startTime = System.nanoTime();
        for(int i=0;i<n;i++)
        {
            JsonNode zjsonPath = com.flipkart.zjsonpatch.JsonDiff.asJson(originNode,targetNode);
        }
        long estimatedTime = System.nanoTime() - startTime;
        n=n*1000;
        return Math.floor((double)(estimatedTime/n));
    }

    //Evaluate jsonPatch library using String json files including origin and target for n time
    private double jsonPatchPerf(int n, String origin, String target) throws Exception
    {
        originNode = objectMapper.readTree(origin);
        targetNode = objectMapper.readTree(target);
        if(originNode.isArray())
            originNode=(ArrayNode)originNode;
        if(targetNode.isArray())
            targetNode = (ArrayNode)targetNode;
        Timer timer = new Timer();
        long startTime = System.nanoTime();
        for(int i=0;i<n;i++)
        {
            JsonNode  jsonPatchPath= com.github.fge.jsonpatch.diff.JsonDiff.asJson(originNode,targetNode);
        }
        long estimatedTime = System.nanoTime() - startTime;
        n=n*1000;
        return Math.floor((double)(estimatedTime/n));
    }


    private String generate(String[] arr) throws Exception
    {
        TestData dataSet = new TestData();
        HashMap<String, Object> map = generateMap(arr);
        StringBuilder builder = new StringBuilder();
        String origin=map.get("origin").toString();
        for(Map.Entry<String,Object> entry:map.entrySet())
        {
            if(entry.getKey().equals("origin"))
                continue;
            String target = entry.getValue().toString();
            builder.append("Origin: "+origin+"\n");
            builder.append("Target: "+target+"\n");
            builder.append("Op: "+entry.getKey()+"\n");
            String[] compareResult = compareResult(origin,target);
            builder.append("zjsonPatch: " + compareResult[0]+"\t");
            builder.append("1000:" + zjsonPatchPerf(1000,origin,target)+"\t");
            builder.append("2000:" + zjsonPatchPerf(2000,origin,target)+"\t");
            builder.append("5000:" + zjsonPatchPerf(5000,origin,target)+"\n");
            builder.append("jsonPatch: " + compareResult[1]+"\t");
            builder.append("1000:" + jsonPatchPerf(1000,origin,target)+"\t");
            builder.append("2000:" + jsonPatchPerf(2000,origin,target)+"\t");
            builder.append("5000:" + jsonPatchPerf(5000,origin,target)+"\n");
            builder.append("==================================\n");
        }
        builder.append("############################\n");
        return builder.toString();
    }

    public String generateJsonNode(Object[] arr) throws Exception
    {
        TestData dataSet = new TestData();
        HashMap<String, Object> map = generateMap(arr);
        StringBuilder builder = new StringBuilder();
        JsonNode origin=(JsonNode)map.get("origin");
        for(Map.Entry<String,Object> entry:map.entrySet())
        {
            if(entry.getKey().equals("origin"))
                continue;
            JsonNode target = (JsonNode)entry.getValue();
            builder.append("Op: "+entry.getKey()+"\n");
            String[] compareResult = compareJsonResult(origin,target);
            builder.append("zjsonPatch: " + compareResult[0]+"\t");
            builder.append("1000:" + zjsonPatchPerf(1000,origin,target)+"\t");
            builder.append("2000:" + zjsonPatchPerf(2000,origin,target)+"\t");
            builder.append("5000:" + zjsonPatchPerf(5000,origin,target)+"\n");
            builder.append("jsonPatch: " + compareResult[1]+"\t");
            builder.append("1000:" + jsonPatchPerf(1000,origin,target)+"\t");
            builder.append("2000:" + jsonPatchPerf(2000,origin,target)+"\t");
            builder.append("5000:" + jsonPatchPerf(5000,origin,target)+"\n");
            builder.append("==================================\n");
        }
        builder.append("############################\n");
        return builder.toString();
    }

    public String compareLoanFile(JsonNode[] arr) throws Exception
    {
        StringBuilder builder = new StringBuilder();
        JsonNode origin = arr[0];
        JsonNode modified = arr[1];
        String[] compareResult = compareJsonResult(origin,modified);
        builder.append("zjsonPatch: " + compareResult[0]+"\t\n");
        builder.append("1000:" + zjsonPatchPerf(1000,origin,modified)+"\t");
        builder.append("2000:" + zjsonPatchPerf(2000,origin,modified)+"\t");
        builder.append("5000:" + zjsonPatchPerf(5000,origin,modified)+"\n");
        builder.append("jsonPatch: " + compareResult[1]+"\t");
        builder.append("1000:" + jsonPatchPerf(1000,origin,modified)+"\t");
        builder.append("2000:" + jsonPatchPerf(2000,origin,modified)+"\t");
        builder.append("5000:" + jsonPatchPerf(5000,origin,modified)+"\n");
        return builder.toString();
    }

    public String experimentSet() throws Exception
    {
        StringBuilder builder = new StringBuilder();
        builder.append(generate(TestData.numSet));
        builder.append(generate(TestData.strSet));
        builder.append(generate(TestData.boolSet));
        builder.append(generate(TestData.nullSet));
        builder.append(generate(TestData.objectSet));
        builder.append(generateJsonNode(TestData.JsonArraySet()));
        builder.append(generateJsonNode(TestData.HighlyNestedData()));
        builder.append(generateJsonNode(TestData.HighlyNestedDataArray()));
        //builder.append(compareLoanFile(TestData.originModifiedLoan()));
        return builder.toString();
    }

    public HashMap<String, Object> generateMap(Object[] arr)
    {
        String key="";
        HashMap<String,Object> map = new HashMap<String,Object>();
        for(int i=0;i<arr.length;i++)
        {
            if(i==0)
                key = "origin";
            else if(i==1)
                key = "add";
            else if(i==2)
                key = "remove";
            else if(i==3)
                key = "replace";
            map.put(key,arr[i]);
        }
        return map;
    }


}



