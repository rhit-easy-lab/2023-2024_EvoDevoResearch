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

for gen in generations:
    gendata = df.loc[df['Generation'] == gen]

    fit.append(gendata.loc[:,'Final_Fitness'].tolist())
    if(gendata.loc[0,'Cond A'].tolist == 'TRUE'):
        print('A')
        metA.append(maxNum*0.25)
    else:
        metA.append(0)
    if(gendata.at[:,'Cond B'] == 'True'):
        metB.append(maxNum*0.5)
    else:
        metB.append(0)
    if(gendata.at[:,'Cond C'] == 'True'):
        metC.append(maxNum*0.75)
    else:
        metC.append(0)


ax = plt.subplot()
ax.plot(xaxis, fit, label = "Fitness")
ax.plot(xaxis, metA, label = "Cond A occurred",markersize=12)
ax.plot(xaxis, metB, label = "Cond B occurred", linestyle='dotted')
ax.plot(xaxis, metC, label = "Cond C occurred", linestyle='dotted')



#Make our plot
plt.title('Lineage over Generational Time')
ax.set_xlabel('Generations')
ax.set_ylabel('% Condition Met')
plt.legend(loc="upper left")

# Display our plot
plt.show()


