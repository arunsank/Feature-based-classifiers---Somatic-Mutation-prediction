# Feature-based-classifiers---Somatic-Mutation-prediction

Folders

Preprocessing              - Contains files related to preprocessing of Cosmic data
classifier                 - Has the classification code - SVM, Random Forest and Logistic Regression.

Data:
Combined_final_200.csv - Input to the models - classifier.py
preliminary features   - Raw data from COSMIC 

Files:
Inferences_Appendix.docx        - Contains probability matrix inferences from the data.
BLOSUM62                        - Blosum62 matrix that was used as a feature
Blosum_from_data                - Probability matrix calculated from data

*Note - In the classifier.py code, each algorithm was run separately to avoid long runs. At a time, only one function call to a model is included and the rest are commented. 

