import numpy as np
import matplotlib.pyplot as plt
import matplotlib as mpl
import pandas as pd
import statistics

from tkinter.filedialog import askopenfilename

#Read in our data from the file
file = askopenfilename()
df = pd.read_csv(file)

#Get the number of each generation
generationsAll = df.loc[:, 'Generation'].tolist()
generations = [*set(generationsAll)]

#Calculate the mean values and error of the fitness at each generation
plot_error = "standard_error"
metA = []
metB = []
metC = []
fit = []

lower_errors = []
upper_errors = []
xaxis = generations




maxNum = df.at[1,'Final_Fitness']

generations.sort()

numGen = len(generations) - 1

for gen in generations:
    gendata = df.loc[df['Generation'] == gen]

    fit.append(gendata.loc[:,'Final_Fitness'].tolist())

    if(gendata.at[numGen - gen,'Cond A'] == True):
        metA.append(maxNum*0.25)
    else:
        metA.append(-5)
    if(gendata.at[numGen - gen,'Cond B'] == True):
        metB.append(maxNum*0.5)
    else:
        metB.append(-5)
    if(gendata.at[numGen - gen,'Cond C'] == True):
        metC.append(maxNum*0.75)
    else:
        metC.append(-5)


ax = plt.subplot()

ax.plot(xaxis, fit, label = "Fitness")
ax.scatter(xaxis, metA, label = "Cond A occurred",)
ax.scatter(xaxis, metB, label = "Cond B occurred", )
ax.scatter(xaxis, metC, label = "Cond C occurred",)


#This sets the y axis to minimum of 0. If we want negative fitness, we must change this
axis = plt.gca()
axis.set_ylim([0,None])


#Make our plot
plt.title('Fitness and Condition Occurences over Generational Time')
ax.set_xlabel('Generations')
ax.set_ylabel('Fitness')
plt.legend(loc="lower right")
plt.rcParams['font.size'] = 18

# Display our plot
plt.show()


