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
genA = []
genB = []
genC = []
genBC = []
avgFit = []
bestFit = []
lower_errors = []
upper_errors = []
xaxis = generations

for gen in generations:
    gendata = df.loc[df['Generation'] == gen]
    genA.append(gendata.loc[:,'A'].tolist())
    genB.append(gendata.loc[:,'B'].tolist())
    genC.append(gendata.loc[:, 'C'].tolist())
    genBC.append(gendata.loc[:, 'BC'].tolist())
    avgFit.append(gendata.loc[:, 'avgFit'].tolist())
    bestFit.append(gendata.loc[:, 'bestFit'].tolist())
    

ax = plt.subplot()
ax.plot(xaxis, genA, label = "%A")
ax.plot(xaxis, genB, label = "%B")
ax.plot(xaxis, genC, label = "%C")
ax.plot(xaxis, genBC, label = "%BC")
ax.plot(xaxis, avgFit, label = "%avg")
ax.plot(xaxis, bestFit, label = "%best")


#Make our plot
plt.title('Condition % met over Generational Time')
ax.set_xlabel('Generations')
ax.set_ylabel('% Condition Met')
plt.legend(loc="upper left")

# Display our plot
plt.show()
