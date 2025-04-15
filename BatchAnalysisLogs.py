import os
import pm4py as p
import pandas as pd
import fnmatch
import argparse

from feeed.feature_extractor import extract_features

from datetime import datetime, timedelta




parser = argparse.ArgumentParser(description='Take a set of event logs and analyze them in bulk.')

parser.add_argument('--clean', nargs='?', default=False, type=bool,
                    help='indicate whether to clean the log or not, default is false', choices=[False,True])

parser.add_argument('--input', nargs='?',
                    help='specify the folder where the event logs can be found')

parser.add_argument('--makeModels', nargs='?', default=False, type=bool,
                    help='indicate whether to create BPMN models of the logs as well, default is false', choices=[False,True])

args = parser.parse_args()


folder = args.input
clean = args.clean
createModels = args.makeModels

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
DFRelationships = []
N3grams = []
N4grams = []


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

def determineDirectlyFollows(log):
    df_list = []

    concat = log.groupby("CASE", as_index=False).agg({'ACTIVITY': ''.join})

    traces = concat["ACTIVITY"].unique()

    for i in traces:
        for j in range(1, len(i)):
            df = i[j-1] + i[j]
            if df not in df_list:
                df_list.append(df)
    
    DFRelationships.append(len(df_list))

def determineNgrams(log, n):

    ngrams_list = []

    concat = log.groupby("CASE", as_index=False).agg({'ACTIVITY': ''.join})

    traces = concat["ACTIVITY"].unique()

    for i in traces:
        print(i)
        if n < len(i):
            for j in range(n, len(i)+1):
                df = i[j-n:j]
                if df not in ngrams_list:
                    ngrams_list.append(df)
        else:
            ngrams_list.append(0)

    print(ngrams_list)
    
    return len(ngrams_list)

    

if clean: 
    os.makedirs(os.path.join(folder, 'Filtered'), exist_ok=True)

if createModels:
    os.makedirs(os.path.join(folder, 'BPMN-models'), exist_ok=True)

# Main body: consists of a loop of all the events logs through all the functions mentioned above.
for i in logs:
    current_file = os.path.join(folder, i)

    log = p.read.read_xes(current_file)


    if clean:
        log = cleanLog(log)

        p.write.write_xes(log, file_path=os.path.join(folder, 'Filtered', i))

    if createModels:
        model = p.discovery.discover_bpmn_inductive(log,
                                                    activity_key='concept:name',
                                                    timestamp_key='time:timestamp',
                                                    case_id_key='case:concept:name',
                                                    noise_threshold=0.2)
        name_model = str(count) + ".bpmn"
        p.write.write_bpmn(model, file_path = os.path.join(folder, "BPMN-models", name_model))


    log = log.rename(columns={'concept:name':'ACTIVITY', 'time:timestamp':'TIME', 'case:concept:name':'CASE'})

    getAmountOfPaths(log)
    determineEntropy(current_file)
    getAvgLengthOfPaths(log)
    print(i) #to determine where the error occurs
    #determineAvgTime(log)
    determineDirectlyFollows(log)
    N3grams.append(determineNgrams(log, 3)) 
    N4grams.append(determineNgrams(log, 4)) 
    #print("Log " + str(count) + "/" + str(amountOfLogs) + " Done")
    count += 1

    

#Export lists to CSV files so they can get analyzed statistically in R
dict = {'amountsofPaths': amountsofPaths, 
        'entropies': entropies, 
        'averageLengthsPaths': averageLengthsPaths, 
        #'averageTimes': averageTimes, 
        'DFRelationships': DFRelationships,
        'N3grams': N3grams,
        'N4grams': N4grams}

df = pd.DataFrame(dict)

df.to_csv(os.path.join(folder, 'NumbersForAnalysis.csv'))

print(count-1)
















