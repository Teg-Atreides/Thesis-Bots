import os
import pm4py as p
import pandas as pd
import fnmatch

from feeed.feature_extractor import extract_features

from datetime import datetime, timedelta




folder = r"C:\Users\peete\OneDrive\Documenten\School\2e master BI\Masterproef\Coding\Logs for testing\Declare Startpunt\Level3\Logs\ResultsImperative\Logs"

files = os.listdir(folder)

logs = []

for i in files:
    if fnmatch.fnmatch(i, "*.xes"):
        logs.append(i)

print(logs)

amountOfLogs = len(logs)

count = 1

#Create lists to output values that will be used during the statistical analysis
amountsofPaths = []
entropies = []
averageLengthsPaths = []
averageTimes = []


# Functions to make the code easier
def getAmountOfPaths(log):
    concat = log.groupby("CASE", as_index=False).agg({'ACTIVITY': ' '.join})
    amountsofPaths.append(len(concat['ACTIVITY'].value_counts()))

def determineEntropy(log_path):
    e = extract_features(log_path, ["eventropy_global_block_flattened"])
    entropies.append(e['eventropy_global_block_flattened'])

def getAvgLengthOfPaths(log):
    concat = log.groupby("CASE", as_index=False).agg({'ACTIVITY': ' '.join})

    traces = concat["ACTIVITY"].unique()

    amountOfTraces = len(traces)

    lengthOfTraces = []

    for i in traces:
        count = 1
        for j in range(len(i)):
            if i[j] == " ":
                count += 1
        lengthOfTraces.append(count)

    averageLengthsPaths.append(sum(lengthOfTraces)/amountOfTraces)

def determineAvgTime(log):
    firstTime = log.groupby("CASE").agg({'TIME': min})
    lastTime = log.groupby("CASE").agg({'TIME': max})
    timedifferences = []

    for i in range(len(lastTime["TIME"])):
       timedifferences.append(lastTime["TIME"][i] - firstTime["TIME"][i])

    average_timedelta = sum(timedifferences, timedelta(0)) / len(timedifferences)

    averageTimes.append(average_timedelta)

def cleanLog(log):
    logA_F = log[log['lifecycle:transition'] == 'start']
    return logA_F
    



# Main body: consists of a loop of all the events logs through all the functions mentioned above.
for i in logs:
    current_file = os.path.join(folder, i)

    log = p.read.read_xes(current_file)


    
    """log = cleanLog(log)

    p.write.write_xes(log, file_path=os.path.join(folder, 'Filtered', i))


    model = p.discovery.discover_bpmn_inductive(log,
                                                activity_key='concept:name',
                                                timestamp_key='time:timestamp',
                                                case_id_key='case:concept:name',
                                                noise_threshold=0.2)
    name_model = str(count) + ".bpmn"
    p.write.write_bpmn(model, file_path = os.path.join(folder, "ResultsImperative", name_model))"""


    log = log.rename(columns={'concept:name':'ACTIVITY', 'time:timestamp':'TIME', 'case:concept:name':'CASE'})
    getAmountOfPaths(log)
    determineEntropy(current_file)
    getAvgLengthOfPaths(log)
    print(i) #to determine where the error occurs
    determineAvgTime(log)
    #print("Log " + str(count) + "/" + str(amountOfLogs) + " Done")
    count += 1

    

#Export lists to CSV files so they can get analyzed statistically in R
dict = {'amountsofPaths': amountsofPaths, 'entropies': entropies, 'averageLengthsPaths': averageLengthsPaths, 'averageTimes': averageTimes}

df = pd.DataFrame(dict)

df.to_csv(os.path.join(folder, 'NumbersForAnalysis.csv'))

print(count-1)
















