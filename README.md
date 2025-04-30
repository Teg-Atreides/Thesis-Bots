Hello!

This github repository has been developped as part of my master thesis at the UHasselt. During my thesis I researched if the underlying process mining paradigm (declarative vs imperative) had an influence on the generation of artificial event logs. 
For my experiment, I had to generate a high number of event logs and I automated to process.

In the repository, you will find a python script (BatchAnalysisLog.py) with its required packages and a couple of Java bots with the Robot package from java in the src-folder. The important bots are the following:
- LogGeneratorForRum.java: Automizes the RuM tool to generate logs based on a folder of declare models with the MP Minerful method.
- ModelGeneratorForRum.java: Automizes the RuM tool to generate declare models based on a folder of event logs with the MP Minerful method.
- PLG2Generator.java: Automizes the PLG2 tool to generate event logs based on a folder of BPMN models.

I will now explain how to use the python script and the three java files.

For the record, all the scripts can be accessed through the command prompt. If you want to use them, it is best to download this whole repository.

# BatchAnalysisLog.py
This script was created to calculate the needed variables for a batch of event logs through PM4Py. It can also clean the event log and create BPMN-models based on event logs.
The cleaning part is mostly about getting duplicate entries out of my data. Some of my start data had two entries per activity: when it started and when it was completed. Since I was not interested in timing and the algorithms struggled with with those "duplicate" lines, this filters out the 'completion' lines. It can be used by using '--clean True' and results in a subfolder containing the filtered event logs.
In order to make BPMN models, the inductive miner algorithm that has been built into PM4Py was used. In order to acces that functionality, use --makeModels True and it wil result in a subfolder containing the BPMN-models.
Lastly, in order to input the folder that contains the event logs, use --i "absolute/path/to/event/logs"

Example use cases:

```
python "path/to/BatchAnalysisLogs.py" --i "Path/to/event/logs"
```
```
python "path/to/BatchAnalysisLogs.py" --i "Path/to/event/logs" --makeModels True
```
```
python "path/to/BatchAnalysisLogs.py" --i "Path/to/event/logs" --makeModels True --clean True
```


# LogGeneratorForRum.java
This script was created to generate logs through RuM based on a directory of declare models. Before you run it, you need to open the RuM application and open the Log Generation part of the application. This script simulates specific mouse button clicks, so the resolution and scaling of your screen is important to make sure that the clicks happen on the right places. So make sure that RuM is on full screen on your main screen, the resolution is 1920x1080 and the scaling is put to 100%. After that, the command prompt can be opened. Don't open the command prompt to full screen put keep it as a small window so the 'Open Model' button in RuM can be clicked right after you put in your commands. Than you can put in the following command:

```
java "Absolute/Path/to/LogGeneratorForRum.java"

```

Then you will be asked by some dialog to provide the bot with some variables. After that, you can let the bot run.

# ModelGeneratorForRum.java
This script was created to generate Declare models based on event logs. Again, before you run it, make sure that the RuM application is opened and the screen is in the right format: 1920x1080 with scaling 100% and RuM on the main screen. Also, open the 'Discover Model' part of RuM before you run the script and make sure the 'Open Log' button is free on the main screen while you enter the following command in the command prompt:

```
java "Absolute/Path/to/ModelGeneratorForRum.java"
```
Like the previous bot, some dialog will show up in the command prompt to ask for the directory where the event logs are stored. After putting that into the command prompt, the bot can start


# PLG2Generator.java
This script was created to generate event logs based on BPMN models in PLG2. Make sure to open PLG2 before the script is run and put the screen into the right format: 1920x1080 with scaling 100% and PLG2 on fullscreen on the main screen, while the command prompt is opened in front of it, with the 'Open' button of PLG2 is still visable. After that is all in order, you can run the following command in the command prompt:

```
java "Absolute/Path/to/PLG2Generator.java"
```
Again, some dialog will request the absolute path to the directory where the BPMN models are stored. After copying that into the command prompt, the bot will run.

# Final Remarks
I wrote these bots for pretty specific goals, but their logic can be used in other scenarios as well. So, I invite you to look at the code and edit it where you please, because it can allow researchers to automate a lot of different tasks, resulting in more efficient research.
