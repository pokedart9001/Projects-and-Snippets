import sys
import csv
import statistics

import math
import numpy


def read_data(csv_path):
	"""Read in the input data from csv.
	
	Args:
		csv_path: the path to the input file.  The data file is assumed to have a header row, and
				the class value is found in the last column.
		standardize: flag to indicate whether the attributes (not including the class label) should 
				be transformed into z-scores before returning

	Returns: a list of lists containing the labeled training examples, with each row representing a 
		single example of the format [ attr1, attr2, ... , class_label ]
	"""
	with open(csv_path, 'r') as csvfile:
		reader = csv.reader(csvfile)
		next(reader)  # header row
		features = []
		labels = []
		for row in reader:
			features.append([ float(r) for r in row[:-1] ])            
			labels.append(row[-1])
		examples = [ row + [labels[i]] for i, row in enumerate(features) ]
		return examples


def standardize(examples):
	"""Transform data to use z-scores instead of raw values.  
		
	Args:
		examples: a list of lists containing the training examples, with each row representing a 
			single example of the format [ attr1, attr2, ... , class_label ]
	
	Returns: a list of lists containing the transformed training examples, with each row 
		representing a single example of the format [ zscore1, zscore2, ... , class_label ]

	See: https://en.wikipedia.org/wiki/Standard_score for more detail on z-scores.  N.B.: the last
		field each row is assumed to contain the class label and is not transformed!    
	"""

	means = [numpy.mean([ex[i] for ex in examples]) for i in range(13)]
	stds = [numpy.std([ex[i] for ex in examples]) for i in range(13)]

	return [[(ex[i] - means[i])/stds[i] for i in range(13)] + [ex[13]] for ex in examples]


def learn_weights(examples):
	"""Learn attribute weights for a multiclass perceptron.

	Args:
		examples: a list of lists containing the training examples, with each row representing a 
			single example of the format [ attr1, attr2, ... , class_label ]
				  
	Returns: a dictionary containing the weights for each attribute, for each class, that correctly
			classify the training data.  The keys of the dictionary should be the class values, and
			the values should be lists attribute weights in the order they appear in the data file.
			For example, if there are four attributes and three classes (1-3), the output might take
			the form:
				{ 1 => [0.1, 0.8, 0.5, 0.01],
				  2 => [0.9, 0.01, 0.05, 0.4],
				  3 => [0.01, 0.2, 0.3, 0.85] }
	"""

	weights = {1: [0]*13, 2: [0]*13, 3: [0]*13}  # one set of weights for each class

	for iteration in range(1000):
		misclassifications = 0
		for example in examples:
			class_predict = 0
			class_max = -math.inf
			for i in range(1, 4):
				value = numpy.dot(weights[i], example[:-1])
				if value > class_max:
					class_max = value
					class_predict = i

			if (class_predict != int(example[-1])):
				weights[class_predict] = numpy.subtract(weights[class_predict], example[:-1])
				weights[int(example[-1])] = numpy.add(weights[int(example[-1])], example[:-1])
				misclassifications += 1
		
		if (misclassifications == 0):
			break

	return weights


def print_weights(class__weights):
	for c, wts in sorted(class__weights.items()):
		print("class {}: {}".format(c, ",".join([str(round(w, 2)) for w in wts])))


#############################################


if __name__ == '__main__':

	path_to_csv = "wine.csv"
	training_data = read_data(path_to_csv)

	class__weights = learn_weights(training_data)
	print_weights(class__weights)

	print()

	training_data = standardize(training_data)
	class__weights = learn_weights(training_data)
	print_weights(class__weights)