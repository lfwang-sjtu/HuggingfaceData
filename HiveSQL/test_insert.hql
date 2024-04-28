INSERT INTO TABLE model1 
SELECT * FROM 
    (SELECT 'albert-base-v1', 'fill-mask', '1', '73054', ARRAY('bookcorpus', 'wikipedia'), 'null_author', 'transformers' FROM (SELECT 1) t1 
    UNION ALL 
    SELECT 'model2', 'type2', '2', '12345', ARRAY('source1', 'source2'), 'author2', 'transformers' FROM (SELECT 1) t2 
    UNION ALL 
    SELECT 'model3', 'type3', '3', '98765', ARRAY('source3', 'source4'), 'author3', 'transformers' FROM (SELECT 1) t3
    UNION ALL 
    SELECT 'model4', 'type4', '4', '54321', ARRAY('source5', 'source6'), 'author4', 'transformers' FROM (SELECT 1) t4
    UNION ALL 
    SELECT 'model5', 'type5', '5', '13579', ARRAY('source7', 'source8'), 'author5', 'transformers' FROM (SELECT 1) t5
    UNION ALL 
    SELECT 'model6', 'type6', '6', '24680', ARRAY('source9', 'source10'), 'author6', 'transformers' FROM (SELECT 1) t6
    UNION ALL 
    SELECT 'model7', 'type7', '7', '11111', ARRAY('source11', 'source12'), 'author7', 'transformers' FROM (SELECT 1) t7
    UNION ALL 
    SELECT 'model8', 'type8', '8', '22222', ARRAY('source13', 'source14'), 'author8', 'transformers' FROM (SELECT 1) t8
    UNION ALL 
    SELECT 'model9', 'type9', '9', '33333', ARRAY('source15', 'source16'), 'author9', 'transformers' FROM (SELECT 1) t9
    UNION ALL 
    SELECT 'model10', 'type10', '10', '44444', ARRAY('source17', 'source18'), 'author10', 'transformers' FROM (SELECT 1) t10
) t;


INSERT INTO TABLE model1 
SELECT * FROM 
    (SELECT 'albert-base-v1', 'fill-mask', '1', '73054', ARRAY('bookcorpus', 'wikipedia'), 'null_author', 'transformers' FROM (SELECT 1) t1 
    UNION ALL 
    SELECT 'model2', 'type2', '2', '12345', ARRAY('source1', 'source2'), 'author2', 'transformers' FROM (SELECT 1) t2 
    UNION ALL 
    SELECT 'model3', 'type3', '3', '98765', ARRAY('source3', 'source4'), 'author3', 'transformers' FROM (SELECT 1) t3
    UNION ALL 
    SELECT 'model4', 'type4', '4', '54321', ARRAY('source5', 'source6'), 'author4', 'transformers' FROM (SELECT 1) t4
) t;


------------------------------------------------------------------------
-- 创建数据库
CREATE DATABASE IF NOT EXISTS ptm;

-- 使用数据库
USE ptm;

-- 创建表
CREATE TABLE IF NOT EXISTS model (
    modelId STRING,
    pipeline_tag STRING,
    author STRING,
    likes INT,
    downloads INT
)
PARTITIONED BY (library_name STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
------------------------------------------------


-- 创建数据库
CREATE DATABASE IF NOT EXISTS ptm;

-- 使用数据库
USE ptm;

CREATE TABLE IF NOT EXISTS model2 (
    modelId STRING,
    pipeline_tag STRING,
    likes INT,
    downloads INT,
    datasets ARRAY<STRING>
)
PARTITIONED BY (author STRING, library_name STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
COLLECTION ITEMS TERMINATED BY '|'
STORED AS TEXTFILE;
----------------------------------------------------------------------------
-- 创建数据库
CREATE DATABASE IF NOT EXISTS ptm;

-- 使用数据库
USE ptm;
CREATE TABLE IF NOT EXISTS dataset (
    modelid STRING
)
PARTITIONED BY (dataset STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;


------------------------------------
ALTER TABLE dataset1 add columns(downloads INT);