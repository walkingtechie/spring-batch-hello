Spring Batch is a framework for batch processing - execution series of jobs. In Spring Batch, A job consist of many steps and each step consist of READ-PROCESS-WRITE task and single operation task (tasklet).
READ-PROCESS-WRITE process means It read data from resources like ( csv, xml and database etc.) , process on the data/item according to requirement and write it to the resources like (csv, xml and database).

Tasklet (single task/operation), It is used to do clean up resources after or before step started or completed.

A job is sequence of steps.
Find more datail on https://walkingtechie.blogspot.com/2017/03/spring-batch-hello-world-example.html
