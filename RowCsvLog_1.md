# PART 1 - Prepare cluster
Connected to the target VM, address: '127.0.0.1:63654', transport: 'socket'
22:07:15,259 INFO  org.apache.flink.api.java.ExecutionEnvironment                - The job has 0 registered types and 0 default Kryo serializers
22:07:15,534 INFO  org.apache.flink.runtime.minicluster.FlinkMiniCluster         - Disabled queryable state server
22:07:15,587 INFO  org.apache.flink.runtime.minicluster.FlinkMiniCluster         - Starting FlinkMiniCluster.
22:07:16,001 INFO  akka.event.slf4j.Slf4jLogger                                  - Slf4jLogger started
22:07:16,033 INFO  org.apache.flink.runtime.blob.BlobServer                      - Created BLOB server storage directory /var/folders/qy/vlt1fsw51plb43mdjz_w__y80000gq/T/blobStore-e852e777-09ca-4b10-a608-f30cbf988ace
22:07:16,046 INFO  org.apache.flink.runtime.blob.BlobServer                      - Started BLOB server at 0.0.0.0:63657 - max concurrent requests: 50 - max backlog: 1000
22:07:16,059 INFO  org.apache.flink.runtime.metrics.MetricRegistry               - No metrics reporter configured, no metrics will be exposed/reported.
22:07:16,067 INFO  org.apache.flink.runtime.jobmanager.MemoryArchivist           - Started memory archivist akka://flink/user/archive_1
22:07:16,072 INFO  org.apache.flink.runtime.jobmanager.JobManager                - Starting JobManager at akka://flink/user/jobmanager_1.
22:07:16,073 INFO  org.apache.flink.runtime.highavailability.nonha.embedded.EmbeddedLeaderService  - Proposing leadership to contender org.apache.flink.runtime.jobmanager.JobManager@3678e293 @ akka://flink/user/jobmanager_1
22:07:16,081 INFO  org.apache.flink.runtime.taskexecutor.TaskManagerConfiguration  - Messages have a max timeout of 10000 ms
22:07:16,088 INFO  org.apache.flink.runtime.taskexecutor.TaskManagerServices     - Temporary file directory '/var/folders/qy/vlt1fsw51plb43mdjz_w__y80000gq/T': total 465 GB, usable 321 GB (69.03% usable)
22:07:16,130 INFO  org.apache.flink.runtime.jobmanager.JobManager                - JobManager akka://flink/user/jobmanager_1 was granted leadership with leader session ID Some(8264110b-a285-4396-8bf5-e9ec81e35a6d).
22:07:16,157 INFO  org.apache.flink.runtime.highavailability.nonha.embedded.EmbeddedLeaderService  - Received confirmation of leadership for leader akka://flink/user/jobmanager_1 , session=8264110b-a285-4396-8bf5-e9ec81e35a6d
22:07:16,253 INFO  org.apache.flink.runtime.clusterframework.standalone.StandaloneResourceManager  - Trying to associate with JobManager leader akka://flink/user/jobmanager_1
22:07:16,263 INFO  org.apache.flink.runtime.clusterframework.standalone.StandaloneResourceManager  - Resource Manager associating with leading JobManager Actor[akka://flink/user/jobmanager_1#-1374176694] - leader session 8264110b-a285-4396-8bf5-e9ec81e35a6d
22:07:16,572 INFO  org.apache.flink.runtime.io.network.buffer.NetworkBufferPool  - Allocated 363 MB for network buffer pool (number of memory segments: 11627, bytes per segment: 32768).
22:07:16,584 INFO  org.apache.flink.runtime.io.network.NetworkEnvironment        - Starting the network environment and its components.
22:07:16,586 INFO  org.apache.flink.runtime.taskexecutor.TaskManagerServices     - Limiting managed memory to 1144 MB, memory will be allocated lazily.
22:07:16,590 INFO  org.apache.flink.runtime.io.disk.iomanager.IOManager          - I/O manager uses directory /var/folders/qy/vlt1fsw51plb43mdjz_w__y80000gq/T/flink-io-c667b5ee-6da0-49ff-8fee-6b7d776fb362 for spill files.
22:07:16,591 INFO  org.apache.flink.runtime.metrics.MetricRegistry               - No metrics reporter configured, no metrics will be exposed/reported.
22:07:16,643 INFO  org.apache.flink.runtime.filecache.FileCache                  - User file cache uses directory /var/folders/qy/vlt1fsw51plb43mdjz_w__y80000gq/T/flink-dist-cache-a27aaaa8-de5d-48fa-a0ee-2d378f4eec78
22:07:16,651 INFO  org.apache.flink.runtime.filecache.FileCache                  - User file cache uses directory /var/folders/qy/vlt1fsw51plb43mdjz_w__y80000gq/T/flink-dist-cache-b69550f0-c760-4f9b-8787-986f06a56014
22:07:16,658 INFO  org.apache.flink.runtime.taskmanager.TaskManager              - Starting TaskManager actor at akka://flink/user/taskmanager_1#1914258305.
22:07:16,658 INFO  org.apache.flink.runtime.taskmanager.TaskManager              - TaskManager data connection information: 016870d5daf1a8cb6691e46148098d8c @ localhost (dataPort=-1)
22:07:16,659 INFO  org.apache.flink.runtime.taskmanager.TaskManager              - TaskManager has 8 task slot(s).
22:07:16,660 INFO  org.apache.flink.runtime.taskmanager.TaskManager              - Memory usage stats: [HEAP: 377/674/3641 MB, NON HEAP: 24/25/-1 MB (used/committed/max)]
22:07:16,788 INFO  org.apache.flink.runtime.taskmanager.TaskManager              - Trying to register at JobManager akka://flink/user/jobmanager_1 (attempt 1, timeout: 500 milliseconds)
22:07:16,791 INFO  org.apache.flink.runtime.clusterframework.standalone.StandaloneResourceManager  - TaskManager 016870d5daf1a8cb6691e46148098d8c has started.
22:07:16,794 INFO  org.apache.flink.runtime.instance.InstanceManager             - Registered TaskManager at localhost (akka://flink/user/taskmanager_1) as f5c053b1c5d8d4497a6892cd35d6e7f8. Current number of registered hosts is 1. Current number of alive task slots is 8.
22:07:16,799 INFO  org.apache.flink.runtime.taskmanager.TaskManager              - Successful registration at JobManager (akka://flink/user/jobmanager_1), starting network stack and library cache.
22:07:16,802 INFO  org.apache.flink.runtime.taskmanager.TaskManager              - Determined BLOB server address to be localhost/127.0.0.1:63657. Starting BLOB cache.
22:07:16,803 INFO  org.apache.flink.runtime.blob.BlobCache                       - Created BLOB cache storage directory /var/folders/qy/vlt1fsw51plb43mdjz_w__y80000gq/T/blobStore-9082eb37-99a6-4a15-9d8b-3cda311ae557
22:07:16,950 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Disconnect from JobManager null.
22:07:16,952 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Received SubmitJobAndWait(JobGraph(jobId: 45dd6e36b7ca174936f131ff7243130b)) but there is no connection to a JobManager yet.
22:07:16,953 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Received job Flink Java Job at Sat Nov 18 22:07:15 PST 2017 (45dd6e36b7ca174936f131ff7243130b).
22:07:16,953 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Connect to JobManager Actor[akka://flink/user/jobmanager_1#-1374176694].
22:07:16,953 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Connected to JobManager at Actor[akka://flink/user/jobmanager_1#-1374176694] with leader session id 8264110b-a285-4396-8bf5-e9ec81e35a6d.
Connected to JobManager at Actor[akka://flink/user/jobmanager_1#-1374176694] with leader session id 8264110b-a285-4396-8bf5-e9ec81e35a6d.
22:07:16,953 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Sending message to JobManager akka://flink/user/jobmanager_1 to submit job Flink Java Job at Sat Nov 18 22:07:15 PST 2017 (45dd6e36b7ca174936f131ff7243130b) and wait for progress
22:07:16,954 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Upload jar files to job manager akka://flink/user/jobmanager_1.
22:07:16,956 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Submit job to the job manager akka://flink/user/jobmanager_1.
22:07:16,959 INFO  org.apache.flink.runtime.jobmanager.JobManager                - Submitting job 45dd6e36b7ca174936f131ff7243130b (Flink Java Job at Sat Nov 18 22:07:15 PST 2017).
22:07:17,004 INFO  org.apache.flink.runtime.jobmanager.JobManager                - Using restart strategy NoRestartStrategy for 45dd6e36b7ca174936f131ff7243130b.
22:07:17,016 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph        - Job recovers via failover strategy: full graph restart
22:07:17,037 INFO  org.apache.flink.runtime.jobmanager.JobManager                - Running initialization on master for job Flink Java Job at Sat Nov 18 22:07:15 PST 2017 (45dd6e36b7ca174936f131ff7243130b).
22:07:17,040 INFO  org.apache.flink.runtime.jobmanager.JobManager                - Successfully ran initialization on master in 3 ms.
22:07:17,057 INFO  org.apache.flink.runtime.client.JobSubmissionClientActor      - Job 45dd6e36b7ca174936f131ff7243130b was successfully submitted to the JobManager akka://flink/deadLetters.
22:07:17,057 INFO  org.apache.flink.runtime.jobmanager.JobManager                - Scheduling job 45dd6e36b7ca174936f131ff7243130b (Flink Java Job at Sat Nov 18 22:07:15 PST 2017).
