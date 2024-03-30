import json

# 输入的JSON文件路径和输出的JSON文件路径
input_json_file = 'hf_metadata.json'
output_json_file = 'sample.json'

# 截取的元素数量
limit = 100

# 打开输入的JSON文件并解析JSON数据
with open(input_json_file, 'r') as infile:
    data = json.load(infile)

# 截取前100个元素
sample_data = data[:limit]

# 将截取后的数据写入到输出的JSON文件中
with open(output_json_file, 'w') as outfile:
    json.dump(sample_data, outfile, indent=4)

print("done")
