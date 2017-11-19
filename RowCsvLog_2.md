# PART 2 - Control Flow before Actural Data Flow

## ExecutionGraph & JobSubmissionClientActor
### 1. CREATED -> RUNNING -> FINISHED (at the end)
22:07:17,057 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph        
- Job Flink Java Job at Sat Nov 18 22:07:15 PST 2017 (45dd6e36b7ca174936f131ff7243130b) switched from state CREATED to RUNNING.

22:07:17,058 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      
- 11/18/2017 22:07:17	Job execution switched to status RUNNING.
11/18/2017 22:07:17	Job execution switched to status RUNNING.

### 2. [1/8] CREATED -> SCHEDULED -> DEPLOYING -> localhost
22:07:17,058 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph        
- CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) switched from CREATED to SCHEDULED.

22:07:17,063 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph        
- CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) switched from SCHEDULED to DEPLOYING.

22:07:17,064 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph        
- Deploying CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (attempt #0) to localhost

22:07:17,059 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      
- 11/18/2017 22:07:17	CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor)(1/8) switched to SCHEDULED 
11/18/2017 22:07:17	CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor)(1/8) switched to SCHEDULED 

22:07:17,064 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      
- 11/18/2017 22:07:17	CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor)(1/8) switched to DEPLOYING 
11/18/2017 22:07:17	CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor)(1/8) switched to DEPLOYING 


## TaskManager & Task
### 1. Received task CHAIN

22:07:17,094 INFO  org.apache.flink.runtime.taskmanager.TaskManager              
- Received task CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8)

### 2. [1/8] 
#### CREATED -> DEPLOYING
22:07:17,095 INFO  org.apache.flink.runtime.taskmanager.Task                     
- CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) switched from CREATED to DEPLOYING.

#### Creating FileSystem stream leak safety net for task CHAIN
22:07:17,095 INFO  org.apache.flink.runtime.taskmanager.Task                     
- Creating FileSystem stream leak safety net for task CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) [DEPLOYING]

#### Loading JAR files for task CHAIN
22:07:17,099 INFO  org.apache.flink.runtime.taskmanager.Task                     
- Loading JAR files for task CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) [DEPLOYING].

#### Registering task at network
22:07:17,102 INFO  org.apache.flink.runtime.taskmanager.Task                     
- Registering task at network: CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) [DEPLOYING].

#### DEPLOYING -> RUNNING -> FINISHED
22:07:17,107 INFO  org.apache.flink.runtime.taskmanager.Task                     
- CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) switched from DEPLOYING to RUNNING.

22:07:17,115 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph        
- CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) switched from DEPLOYING to RUNNING.

22:07:17,119 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      
- 11/18/2017 22:07:17	CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor)(1/8) switched to RUNNING 
11/18/2017 22:07:17	CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor)(1/8) switched to RUNNING 

22:09:17,664 INFO  org.apache.flink.runtime.taskmanager.Task                     
- CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) switched from RUNNING to FINISHED.

#### Freeing task resources for CHAIN & Ensuring all FileSystem streams are closed for task CHAIN
22:09:17,664 INFO  org.apache.flink.runtime.taskmanager.Task                     
- Freeing task resources for CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d).

22:09:17,664 INFO  org.apache.flink.runtime.taskmanager.Task                     
- Ensuring all FileSystem streams are closed for task CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) [FINISHED]

#### ExecutionGraph & JobSubmissionClientActor Finished
22:09:17,675 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph        
- CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor) (1/8) (d06580b8c1b6b56f5a75ae4854a1e49d) switched from RUNNING to FINISHED.

22:09:17,676 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      
- 11/18/2017 22:09:17	CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor)(1/8) switched to FINISHED 
11/18/2017 22:09:17	CHAIN DataSource (at createInput(ExecutionEnvironment.java:553) (org.apache.flink.api.java.io.RowCsvInputFormat)) -> Filter (Filter at main(RowCsvImpl.java:38)) -> Map (Key Extractor)(1/8) switched to FINISHED 

