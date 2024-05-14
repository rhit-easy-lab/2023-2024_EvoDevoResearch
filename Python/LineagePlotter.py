import numpy as np
import matplotlib.pyplot as plt
import matplotlib as mpl
import pandas as pd
import statistics

from tkinter.filedialog import askopenfilename

#Read in our data from the file
file = askopenfilename()
df = pd.read_csv(file)

ask = input("Do you want to read in potentiation as well? Enter 0 for no, 1 for yes")


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
        
        plt.axvline(x = gen, color = '0.8' )
        metA.append(maxNum*0.25)
    else:
        metA.append(-5)
    if(gendata.at[numGen - gen,'Cond B'] == True):
        
        plt.axvline(x = gen, color = '0.8' )
        metB.append(maxNum*0.5)
    else:
        metB.append(-5)
    if(gendata.at[numGen - gen,'Cond C'] == True):
        
        plt.axvline(x = gen, color = '0.8' )
        metC.append(maxNum*0.75)
    else:
        metC.append(-5)


ax = plt.subplot()

ax.plot(xaxis, fit, label = "Fitness")
ax.scatter(xaxis, metA, label = "BCM",color = 'r', s=40,zorder=2)
ax.scatter(xaxis, metB, label = "BFM",color = 'b', s=40, zorder=2 )
ax.scatter(xaxis, metC, label = "BIM",color = 'g', s=40, zorder=2)


#This sets the y axis to minimum of 0. If we want negative fitness, we must change this
axis = plt.gca()
axis.set_ylim([0,None])

if(ask != "0"):
    file2 = askopenfilename()
    df2 = pd.read_csv(file2)

    generationsAll2 = df2.loc[:, 'Generation'].tolist()
    generations2 = [*set(generationsAll2)]

    pot = []
    for gen in generations2:
        gendata2 = df2.loc[df['Generation'] == gen]
        pot.append(gendata2.at[numGen - gen,'potentiation'] / 100 * maxNum)
        

    ax.plot(xaxis, pot, label = "cloning pot", color='pink')


#Make our plot
plt.title('Fitness and Mutation Occurrences over Generational Time',size=30)
ax.set_xlabel('Generations', fontsize=30)

ax.set_ylabel('Fitness', fontsize=30)
plt.legend(loc="lower right", fontsize=25)
plt.rcParams['font.size'] = 18
ax.tick_params(axis='both', labelsize=15)

# Display our plot
plt.show()


