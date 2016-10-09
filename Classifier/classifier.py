
"""
Team Mates:
    1. Mithilesh
    2. Arun Ram
    3. Amol

Masters in Data Science @Indiana University.
Start date: 27th April, 2016
End date: 27th April, 2016
Objective: Develop classifiers to predict somatic mutation
in human protein sequences.
"""

import numpy as np
import pandas as pd
from sys import argv
from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVC
from sklearn.linear_model import LogisticRegression
from sklearn.learning_curve import learning_curve
from sklearn import cross_validation
from sklearn import metrics
import matplotlib.pyplot as plot
from sklearn.datasets import load_digits

def get_input_data(input_file):
    """
    This function role is to get the features
    and class labels from the input file and returns them.

    Arguments:
        1. input_file: The input file name as a string.
    Return values:
        1. train_features: The features of the data as a
        numpy array.
        2. train_labels: The class labels of the data as a
        numpy array.
    """

    df = pd.read_csv(input_file)
    # read the input file as a pandas DF
    train_labels = np.array(df['Class'])
    # get the class labels of the data
    train_features = np.array(df.drop('Class', axis = 1))
    # get the training features as a numpy array
    return train_features, train_labels

def random_forest_classifier(train_features, train_labels):
    """
    This function helps to build a random forest classifier
    with the input data and returns them.
    Arguments:
        1. train_features: The features of the data as a
        numpy array.
        2. train_labels: The class labels of the data as numpy array.

    Return values:
        1.The classifier object.
    """

    clf = RandomForestClassifier(n_estimators=100,n_jobs = -1).fit(train_features, train_labels)
    # fit the random forest classifier
    return clf

def svm_classifier(train_features, train_labels):
    """
    This function helps to build a SVM classifier
    with the input data and returns them.
    Arguments:
        1. train_features: The features of the data as a
        numpy array.
        2. train_labels: The class labels of the data as numpy array.

    Return values:
        1.The classifier object.
    """

    clf = SVC().fit(train_features, train_labels,)
    # build a SVM classifier model
    return clf

def logistic_regression_classifier(train_features, train_labels):
    """
    This function helps to build a Logistic regression classifier
    with the input data and returns them.
    Arguments:
        1. train_features: The features of the data as a
        numpy array.
        2. train_labels: The class labels of the data as numpy array.

    Return values:
        1.The classifier object.
    """

    clf = LogisticRegression().fit(train_features, train_labels)
    # build a logistic regression model
    return clf

def plot_roc_curve(fpr, tpr, threshold, auc,model):
    """
    This function helps to plot the ROC curve
    with the input values.
        1.fpr: The False Positive Rate as a list.
        2.tpr: The True Positive Rate as a list.
        3.threshold: The threshold values as a list.
        4.auc: The area under the ROC curve as a float.
        5. model: The model name of the classifier as a string.

    Return values:
        1. None
    """

    plot.figure()
    # create a new figure
    plot.plot(fpr, tpr, label = 'Area = {0}'.format(auc))
    # plot the ROC curve
    plot.plot([0,1], [0,1], 'k--')
    # plot the diagonal line
    plot.xlim([0, 1])
    # set the xlimit for the graph
    plot.ylim([0, 1])
    # set the ylimit for the graph
    plot.xlabel("False Positive Rate")
    # set the x label for the graph
    plot.ylabel("True Positive Rate")
    # set the y label for the graph
    plot.legend(loc = "lower right")
    # set the legend for the graph
    plot.title("ROC curve for the model: {}".format(model))
    # set the title for the graph
    plot.savefig(model + '_ROC.png')
    # save the ROC curve file

def plot_precision_recall_curve(precision, recall, threshold, model):
    """
    This function helps to plot the precision-recall curve.
    Arguments:
        1. precision: The precision values as a list.
        2. recall: The recall values as a list.
        3. threshold: The threshold values.
        4. The model name.
    Return values:
        1. None
    """

    plot.figure()
    # new fig
    plot.plot(recall, precision)
    # plot the precision recall curve
    plot.plot([0,1], [0,1], 'k--')
    # plot the diagonal line
    #plot.xlim([0, 1])
    ## set the xlimit for the graph
    #plot.ylim([0, 1])
    ## set the ylimit for the graph
    plot.xlabel("Recall")
    # set the x label for the graph
    plot.ylabel("Precision")
    # set the y label for the graph
    plot.title("Precision-Recall curve for model: {}".format(model))
    plot.savefig(model + '_PR.png')
    # save the ROC curve file

def performance_evaluation(models_dict, train_features, train_labels):
    """
    This function helps to evaluate the perform of the
    classifier developed and prints the results.
    Arguments:
        1. models_dict: The dictionary which has the various
        classifier models.
        2. train_features: The training features data as a numpy
        array.
        3. train_labels: The training labels data as a numpy array.
    Return values:
        1.None
    """

    for model in models_dict:
        # iterate through each model
        print "\nResults for model: {0}\n".format(model)
        classifier = models_dict[model]
        # get the classifier object
        predicted = cross_validation.cross_val_predict(classifier, train_features,
                                                        train_labels, cv = 10)
        # predict values based on 10 fold cross validation
        accuracy = np.mean(predicted == train_labels)*100
        # calculate the accuracy of the model
        print 'Accuracy: {0}'.format(accuracy)
        print metrics.classification_report(train_labels, predicted)
        # print out the classification report for the data
        fpr, tpr, threshold = metrics.roc_curve(train_labels, predicted)
        # get the FPR, TPR and thresholds to plot ROC curve
        auc = metrics.roc_auc_score(train_labels, predicted)
        # calculate the area under the ROC curve
        plot_roc_curve(fpr, tpr, threshold, auc,model)
        # function call to plot the ROC curve
        print "Confusion Matrix"
        print metrics.confusion_matrix(train_labels, predicted)
        # print the confusion matrix of the data
        precision, recall, threshold = metrics.precision_recall_curve(train_labels,
                                        predicted)
        # get the precision, recall and threshold values
        plot_precision_recall_curve(precision, recall, threshold, model)
        # function call to plot precision recall curve



#def main():
    """
    The main function call.
    Arguments:
        1. None.
    Returns:
        1. None
    """

input_file = "Combined_final_200.csv"
    # get the input file as a sys argv
train_features, train_labels = get_input_data(input_file)
    # function call to load the data for processing
classifier_dict = {}
    # empty dict
#random_forest = random_forest_classifier(train_features, train_labels)
    # function call to develop a random forest classifier
#classifier_dict['Random Forest'] = random_forest
    # add the classifier object to the dictionary
svm = svm_classifier(train_features, train_labels)
    ## function call to develop a SVM classifier
classifier_dict['SVM'] = svm
    ## add the classifier to the dictionary
    #logistic = logistic_regression_classifier(train_features, train_labels)
    ## function call to develop logistic regression
    #classifier_dict['Logistic Regression'] = logistic
    # add the classifier to the dictionary
performance_evaluation(classifier_dict, train_features, train_labels)
#random_forest.predict(
    # function call to evaluate performance
#train_sizes_rf,train_scores_rf, valid_scores_rf = learning_curve(random_forest,train_features,train_labels,cv=None,train_sizes=[0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1])
#train_sizes_rf,train_scores_rf, valid_scores_rf = learning_curve(logistic,train_features,train_labels,cv=None,train_sizes=[0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1])
#train_sizes_svm,train_scores_svm, valid_scores_svm = learning_curve(svm,train_features,train_labels,cv=None,train_sizes=np.linspace(.1, 1.0, 5))

#if __name__ == '__main__':
#    # start of the program
#    main()
#    # function call - main

#def plot_learning_curve(estimator, title, X, y, ylim=None, cv=None,
#                        n_jobs=1, train_sizes=np.linspace(0.1,0.5,1)):
#
#    plot.figure()
#    plot.title(title)
#    if ylim is not None:
#        plot.ylim(*ylim)
#    plot.xlabel("Training examples")
#    plot.ylabel("Score")
#    train_sizes, train_scores, test_scores = learning_curve(
#        estimator, X, y, cv=cv, n_jobs=n_jobs, train_sizes=train_sizes)
#    train_scores_mean = np.mean(train_scores, axis=1)
#    train_scores_std = np.std(train_scores, axis=1)
#    test_scores_mean = np.mean(test_scores, axis=1)
#    test_scores_std = np.std(test_scores, axis=1)
#    plot.grid()
#
#    plot.fill_between(train_sizes, train_scores_mean - train_scores_std,
#                     train_scores_mean + train_scores_std, alpha=0.1,
#                     color="r")
#    plot.fill_between(train_sizes, test_scores_mean - test_scores_std,
#                     test_scores_mean + test_scores_std, alpha=0.1, color="g")
#    plot.plot(train_sizes, train_scores_mean, 'o-', color="r",
#             label="Training score")
#    plot.plot(train_sizes, test_scores_mean, 'o-', color="g",
#             label="Cross-validation score")
#
#    plot.legend(loc="best")
#    return plot
#plot_learning_curve(svm,title, train_features, train_labels, ylim=(0.7, 1.01), cv=5)
#plot.show()