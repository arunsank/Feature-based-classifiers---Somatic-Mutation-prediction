##DataPreprocessing##
library("caret")
library("stringr")
library("ggplot2")
set.seed(1)

#Load data
raw<-as.data.frame(read.csv2(file="Cosmic_sampled_withprotein.csv", header=TRUE))


#Data Transformation
raw2<-raw[,-c(1:4,7:26)]
colnames(raw2)<-c("Antecedant",	"Consequent",	"F_5",	"L_5",	"I_5",	"M_5",	"V_5",	"S_5",	"P_5",	"T_5",	"A_5",	"Y_5",	"H_5",	"Q_5",	"N_5",	"K_5",	"D_5",	"E_5",	"C_5",	"W_5",	"R_5",	"G_5",	"prot_1",	"Entropy_5",	"F_7",	"L_7",	"I_7",	"M_7",	"V_7",	"S_7",	"P_7",	"T_7",	"A_7",	"Y_7",	"H_7",	"Q_7",	"N_7",	"K_7",	"D_7",	"E_7",	"C_7",	"W_7",	"R_7",	"G_7",	"prot_2",	"Entropy_7",	"F_9",	"L_9",	"I_9",	"M_9",	"V_9",	"S_9",	"P_9",	"T_9",	"A_9",	"Y_9",	"H_9",	"Q_9",	"N_9",	"K_9",	"D_9",	"E_9",	"C_9",	"W_9",	"R_9",	"G_9",	"prot_3",	"Entropy_9","Class")
raw2$charged_5<-(str_count(raw2$prot_1,"K") + str_count(raw2$prot_1,"R") + str_count(raw2$prot_1,"D") + str_count(raw2$prot_1,"E"))
raw2$aromatic_5<-(str_count(raw2$prot_1,"F") + str_count(raw2$prot_1,"W") + str_count(raw2$prot_1,"Y"))
raw2$charged_7<-(str_count(raw2$prot_2,"K") + str_count(raw2$prot_2,"R") + str_count(raw2$prot_2,"D") + str_count(raw2$prot_2,"E"))
raw2$aromatic_7<-(str_count(raw2$prot_2,"F") + str_count(raw2$prot_2,"W") + str_count(raw2$prot_2,"Y"))
raw2$charged_9<-(str_count(raw2$prot_3,"K") + str_count(raw2$prot_3,"R") + str_count(raw2$prot_3,"D") + str_count(raw2$prot_3,"E"))
raw2$aromatic_9<-(str_count(raw2$prot_3,"F") + str_count(raw2$prot_3,"W") + str_count(raw2$prot_3,"Y"))
raw3<-raw2[,-c(23,45,67) ]
raw3$Class<-"N"


for (i in c(3:22))
{
  raw3[,i]<-gsub("[^0-9]","",raw3[,i])
  
}

for (i in c(24:43))
{
  raw3[,i]<-gsub("[^0-9]","",raw3[,i])
  
}

for (i in c(45:64))
{
  raw3[,i]<-gsub("[^0-9]","",raw3[,i])
  
}

raw3$Class<-round(runif(nrow(raw3),0,1))

raw3$Entropy_5 <- round(as.numeric(raw3$Entropy_5),digits=3)
raw3$Entropy_7<-round(as.numeric(raw3$Entropy_7),digits=3)
raw3$Entropy_9<-round(as.numeric(raw3$Entropy_9),digits=3)

#output preprocessed
write.csv(raw3,"Neutral_preprocessed.csv")



#Read second file

raw3<-read.csv("final.csv")

sampsiz<-floor(0.8*nrow(raw3))
train_ind <- sample(seq_len(nrow(raw3)), size = sampsiz)
final_train <- raw3[train_ind,]
final_test <- raw3[-train_ind,]

#crossvalidate 
rtc <- trainControl(method="repeatedcv",number=10)
selected_rf_model<-train(final_train[,-70], as.factor(final_train$Class),trControl=rtc,method='ranger')
selected_rf_result<-predict(selected_rf_model, final_test[,-70])


sample<-read.table(file="output",header=FALSE)
sample<-sample[,-1]
sample2<-sample[,-1]
sample2[,69]<-1
colnames(sample2)<-c("Antecedant",	"Consequent",	"F_5",	"L_5",	"I_5",	"M_5",	"V_5",	"S_5",	"P_5",	"T_5",	"A_5",	"Y_5",	"H_5",	"Q_5",	"N_5",	"K_5",	"D_5",	"E_5",	"C_5",	"W_5",	"R_5",	"G_5",	"prot_1",	"Entropy_5",	"F_7",	"L_7",	"I_7",	"M_7",	"V_7",	"S_7",	"P_7",	"T_7",	"A_7",	"Y_7",	"H_7",	"Q_7",	"N_7",	"K_7",	"D_7",	"E_7",	"C_7",	"W_7",	"R_7",	"G_7",	"prot_2",	"Entropy_7",	"F_9",	"L_9",	"I_9",	"M_9",	"V_9",	"S_9",	"P_9",	"T_9",	"A_9",	"Y_9",	"H_9",	"Q_9",	"N_9",	"K_9",	"D_9",	"E_9",	"C_9",	"W_9",	"R_9",	"G_9",	"prot_3",	"Entropy_9","Class")


sample2$charged_5<-(str_count(sample2$prot_1,"K") + str_count(sample2$prot_1,"R") + str_count(sample2$prot_1,"D") + str_count(sample2$prot_1,"E"))
sample2$aromatic_5<-(str_count(sample2$prot_1,"F") + str_count(sample2$prot_1,"W") + str_count(sample2$prot_1,"Y"))

sample2$charged_7<-(str_count(sample2$prot_2,"K") + str_count(sample2$prot_2,"R") + str_count(sample2$prot_2,"D") + str_count(sample2$prot_2,"E"))
sample2$aromatic_7<-(str_count(sample2$prot_2,"F") + str_count(sample2$prot_2,"W") + str_count(sample2$prot_2,"Y"))

sample2$charged_9<-(str_count(sample2$prot_3,"K") + str_count(sample2$prot_3,"R") + str_count(sample2$prot_3,"D") + str_count(sample2$prot_3,"E"))
sample2$aromatic_9<-(str_count(sample2$prot_3,"F") + str_count(sample2$prot_3,"W") + str_count(sample2$prot_3,"Y"))


sample_sampled2<-sample_n(sample2,200000)
write.csv(sample_sampled2,"Cosmic_sampled_200.csv",row.names = F)

sample3<-sample2[,-c(23,45,67) ]

sample_sampled<-sample_n(sample3,350000)
write.csv(sample_sampled,"Cosmic_sampled.csv")

sample_sampled_200<-sample_n(sample3,200000)
write.csv(sample_sampled_200,"Cosmic_sampled_200.csv",row.names = F)

sample2<-unique(sample2)
write.csv(sample_sampled2,"Cosmic_sampled_withprotein.csv")

write.csv(sample3,"Cosmic_finaloutput.csv")

# sampsiz<-floor(0.5*nrow(sample2))
# train_ind <- sample(seq_len(nrow(sample2)), size = sampsiz)
# final_train <- sample2[train_ind,]
# final_test <- sample2[-train_ind,]
# 
# rtc <- trainControl(method="repeatedcv",number=10)
# selected_rf_model<-train(final_train[,-66], final_train$Class,trControl=rtc,method='ranger')
# selected_rf_result<-predict(selected_rf_model, final_test[,-66])
# 

sample_a<-read.csv(file="Output_new.csv",header=TRUE)
sample_b<-sample_a[,-c(1,22,44,66)]
write.csv(sample_c,"Cosmbined_finaloutput_200.csv",row.names = F)


sample_c<-sample_n(sample_b,200000)
write.csv(sample_c,"Cosmic_finaloutput_200.csv",row.names = F)



neutral_a<-read.csv(file="Neutral_finaloutput.csv",header=TRUE)

final<-rbind(sample_b,neutral_a)
final_2 <- final[sample(nrow(final)),]

#Final Sampled data

write.csv(final_2,"Combined_final_200.csv",row.names = F)

