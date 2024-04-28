from pyhive import hive

# 连接到 Hive
conn = hive.Connection(host="10.119.6.225", port=10000, username="root")
cursor = conn.cursor()

# 插入数据的示例数据
data = [
    ("model1", "tag1", "author1", 10, 20, "library1"),
    ("model2", "tag2", "author2", 15, 25, "library2")
]

# 执行插入数据的 SQL 语句
for row in data:
    insert_sql = f"INSERT INTO ptm.model VALUES {row}"
    cursor.execute(insert_sql)

# 提交事务并关闭连接
conn.commit()
conn.close()


