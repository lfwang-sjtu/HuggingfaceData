from pyhive import hive   # or import hive
conn = hive.Connection(host='10.119.6.225', port=10000, username='root', database='default')
# conn = hive.Connection(host='10.119.8.222', port=10000, username='root', database='default')

# 查询
cursor = conn.cursor()
cursor.execute('select * from models limit 10')
for result in cursor.fetchall():
    print(result)

cursor.close()
conn.close()


# # 加载包
# from pyhive import hive

# # 建立连接
# conn = hive.connect(host = '10.119.6.225',    
#                     port = 10000,                  
#                     auth = 'root',                  
#                     kerberos_service_name = 'hive', 
#                     database = 'ptm',           
#                     password = '123456')
                    
# # 查询
# cursor = conn.cursor()
# cursor.execute('select * from model limit 10')
# for result in cursor.fetchall():
#     print(result)
    
# # 关闭连接
# cursor.close()
# conn.close()