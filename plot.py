#!/usr/bin/env python
# Ting-Ying(Templeton) Tsai, 723957
import numpy as np
import matplotlib.pyplot as plt
import csv
import sys



def plotJcardSim(argv):
	filename = argv[0]
	terms = []
	jcard_sim = []

	with open(filename) as f:
		reader = csv.reader(f)
		for row in reader:
			terms.append(row[0])
			jcard_sim.append(float(row[1]))

	ind = np.arange(len(jcard_sim))  # the x locations for the groups
	width = 0.35       # the width of the bars
	fig, ax = plt.subplots()



	bar = ax.bar(ind, jcard_sim, width, color='r', align='center')
	
	# add some text for labels, title and axes ticks
	ax.set_ylabel('Jacard Similarity')
	ax.set_title('Jacard Similarity of Two Different Word2Vec Files')
	ax.set_xticks(ind)
	ax.set_xticklabels([item for item in terms], rotation='vertical')
	
	def autolabel(rects):
		# attach some text labels
		for rect in rects:
		    height = rect.get_height()
		    ax.text(rect.get_x() + rect.get_width()/2., 1.05*height,
		    	'%.2f' % float(height),ha='center', va='bottom')


	autolabel(bar)
	plt.tight_layout()
	plt.show()
	plt.figure(figsize=(3,4))
	fig.savefig(filename +'.png', dpi = 300)

def main(argv):
	plotJcardSim(argv)


if __name__ == "__main__":
   main(sys.argv[1:])
