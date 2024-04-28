package org.example;

import org.apache.storm.tuple.Tuple;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HiveWriter {
    private static final String JDBC_URL = "jdbc:hive2://10.119.6.225:10000/ptm";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    public HiveWriter() {
        try {
            // 在构造函数中创建连接
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to Hive successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//    public void insertData(String modelId, String pipeline_tag, String author, String library_name, int likes, int downloads) throws SQLException {
//        Statement statement = null;
//        try {
//            statement = connection.createStatement();
//
//            // 执行插入数据的SQL语句
//            String insertQuery = "INSERT INTO model (modelid, pipeline_tag, author, library_name, likes, downloads) " +
//                    "VALUES ('" + modelId + "', '" + pipeline_tag + "', '" + author + "', '" + library_name + "', '" + likes + "', '" + downloads + "'))";
//            System.out.println("execute query: " + insertQuery);
//            statement.executeUpdate(insertQuery);
//        } finally {
//            // 关闭资源
//            if (statement != null) {
//                statement.close();
//            }
//        }
//    }

    public void insertData(String modelId, String pipeline_tag, String author, String library_name, int likes, int downloads, String[] datasets) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String datasetsStr = Arrays.stream(datasets).collect(Collectors.joining("', '"));
            String insertQuery = "INSERT INTO TABLE model1 VALUES('" + modelId + "', '" + pipeline_tag + "', '"  + likes + "', '" + downloads + "', ARRAY('" + datasetsStr + "'), '" + author + "', '" + library_name + "')";
            statement.executeUpdate(insertQuery);
        } finally {
            // 关闭资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void insertDataList(List<Tuple> tuples) throws SQLException {
        if (tuples.isEmpty()) {
            return; // 如果列表为空，则直接返回，无需执行插入操作
        }

        Statement statement = null;
        try {
            statement = connection.createStatement();
            StringBuilder insertQuery = new StringBuilder("INSERT INTO TABLE model2 VALUES ");
            for (int i = 0; i < tuples.size(); i++) {
                Tuple tuple = tuples.get(i);
                String modelId = tuple.getStringByField("modelId");
                String pipeline_tag = tuple.getStringByField("pipeline_tag");
                String author = tuple.getStringByField("author");
                String library_name = tuple.getStringByField("library_name");
                int likes = tuple.getIntegerByField("likes");
                int downloads = tuple.getIntegerByField("downloads");
                String datasetsStr = tuple.getStringByField("datasetsStr");
                String[] datasets = datasetsStr.split(",");

                String datasetsConcat = Arrays.stream(datasets).collect(Collectors.joining("', '"));

                insertQuery.append("('").append(modelId).append("', '").append(pipeline_tag).append("', '").append(likes)
                        .append("', '").append(downloads).append("', ARRAY('").append(datasetsConcat).append("'), '")
                        .append(author).append("', '").append(library_name).append("')");

                if (i != tuples.size() - 1) {
                    insertQuery.append(", "); // 如果不是最后一个元素，则添加逗号
                }
            }
            statement.executeUpdate(insertQuery.toString());
        } finally {
            // 关闭资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void insertDatasetList(List<Tuple> tuples) throws SQLException {
        if (tuples.isEmpty()) {
            return; // 如果列表为空，则直接返回，无需执行插入操作
        }

        Statement statement = null;
        try {
            statement = connection.createStatement();
            StringBuilder insertQuery = new StringBuilder("INSERT INTO TABLE dataset1 VALUES ");
            for (int i = 0; i < tuples.size(); i++) {
                Tuple tuple = tuples.get(i);
                String modelId = tuple.getStringByField("modelId");
                int downloads = tuple.getIntegerByField("downloads");

                String datasetsStr = tuple.getStringByField("datasetsStr");
                if ("null_datasets".equals(datasetsStr)) continue;
                String[] datasets = datasetsStr.split(",");
                for (String dataset : datasets) {
                    insertQuery.append("('").append(modelId).append("', ").append(downloads).append(", '").append(dataset.trim()).append("'),");
                }
            }
            if (insertQuery.length() > 0) {
                insertQuery.deleteCharAt(insertQuery.length() - 1);
            }
            statement.executeUpdate(insertQuery.toString());
        } finally {
            // 关闭资源
            if (statement != null) {
                statement.close();
            }
        }
    }

}
