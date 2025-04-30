Hello!

This github repository has been developped as part of my master thesis at the UHasselt. During my thesis I researched if the underlying process mining paradigm (declarative vs imperative) had an influence on the generation of artificial event logs. 
For my experiment, I had to generate a high number of event logs and I automated to process.

In the repository, you will find a python script (BatchAnalysisLog.py) with its required packages and a couple of Java bots with the Robot package from java in the src-folder. The important bots are the following:
- LogGeneratorForRum.java: Automizes the RuM tool to generate logs based on a folder of declare models with the MP Minerful method.
- ModelGeneratorForRum.java: Automizes the RuM tool to generate declare models based on a folder of event logs with the MP Minerful method.
- PLG2Generator.java: Automizes the PLG2 tool to generate event logs based on a folder of BPMN models.

I will now explain how to use the python script and the three java files.

# BatchAnalysisLog.py
This script is accessed through the command line and was created to calculate the needed variables for a batch of event logs through PM4Py. It can also clean the event log and create BPMN-models based on event logs.
The cleaning part is mostly about getting duplicate entries out of my data. Some of my start data had two entries per activity: when it started and when it was completed. Since I was not interested in timing and the algorithms struggled with with those "duplicate" lines, this filters out the 'completion' lines. It can be used by using '--clean True' and results in a subfolder containing the filtered event logs.
In order to make BPMN models, the inductive miner algorithm that has been built into PM4Py was used. In order to acces that functionality, use --makeModels True and it wil result in a subfolder containing the BPMN-models.
Lastly, in order to input the folder that contains the event logs, use --i "absolute/path/to/event/logs"

Example use cases:

````
python "path/to/BatchAnalysisLogs.py" --i "Path/to/event/logs"
````
````
python "path/to/BatchAnalysisLogs.py" --i "Path/to/event/logs" --makeModels True
````
````
python "path/to/BatchAnalysisLogs.py" --i "Path/to/event/logs" --makeModels True --clean True
````


#LogGeneratorForRum.java
