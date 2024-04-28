# PTM Data Analysis Service Development
This project focuses on PTMtorrent dataset, which contains model information available on the Hugging Face platform. Through a big data analysis service, we will analyze the usage of models in the pre-trained model repository.

Considering the large scale of the data, we utilized a distributed server cluster for parallel computing. This enabled us to preprocess, store, analyze, and visualize the data effectively. 


We utilized a dashboard to present the final results of the analysis service.

### Generating Message stream
Using Python script `send.py` to periodically send a stream of messages to Kafka message middleware.

### Preprocessing
Using Storm Topology program `preprocess` to clean data in a streaming manner, including deduplication and filling missing fields.

### Storing
Storing the processed data in a structured format in a distributed Hive database. Extracted dimensions include model name, download count, model family, publishing author, model task, and model dataset.

### Analyzing
Analyzing the processed data in Hive database with Fine-BI platform and presenting the final results on a dashboard.

### Citation for the dataset we use
> Jiang W, Synovic N, Jajal P, et al. PTMTorrent: a dataset for mining open-source pre-trained model packages[C]//2023 IEEE/ACM 20th International Conference on Mining Software Repositories (MSR). IEEE, 2023: 57-61.

### Team member
- lfwang-sjtu
- Colmnh
- Deng Ke (we haven't get his github account yet, he mainly contribute to the analyzing part of this project)