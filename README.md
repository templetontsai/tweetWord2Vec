# TweetWord2Vec

This is a maven project application with GUI for running several features against word2vec library.

### Prerequsite

The application is built with several presumptions. Please make sure you have the following packages installed before you run the package.

  - MongoDB: If you are running the feature from MongoDB collection to text file. You need to have the property file in where you are running the application. The contect of the property file is shown as follow:
    
    Ex: sharon.property
    - hostname: localhost
    - port: 27017
    - databaseName: sharonDB
    - databaseUser: sharon
    - databasePassword: xxxx

  - Python: The visualization of data is implemented in a different program. The implementation is in plot.py. It uses the plotting library, matlibplot. Therefore, please ensure the following python packages are installed as well.
    - numpy
    - matplotlib


### Build application

```sh
$mvn package
```

### Run Application

```sh
$java -jar -Xms1G <jar-file>
```
Ex:

```sh
$java -jar -Xms1G target/tweetWord2Vec-1.0-SNAPSHOT.jar
```
### Applocation Manual

The following sections demonstrate how each feature is executed.

## TrainWord2Vec Tab

In this tab panel, it performs the training process for vectorization. If you leave the any of the text filed blank, the following default values will be used.

  - windowSize: 5
  - minWordFeq: 5
  - numIteration: 5
  - layerSize: 5
  - seed: 5
  - learning rate: 0.025

In GUI, TrainingFilePath should indicate where the training corpus is located.
In Gui, Word2VecFilepath indications where the output of vectorized file should be saved. If leave blank the default path is where the program is run, the file name is word2vec.txt.

## TweetDBToFile Tab

In this tab panel, it performs the transformation of data from MongoDB to text file. The tab exists for the purpose of experimients. However, it uses the database configuration exists in Sharon project which might not be applicable in the running enviroment of yours. Please read Prerequsite for more info.

  - In GUI, it is required where the output file will be stored and the property file located where the program is run.

## TweetReshuffle Tab

In this tab panel, it performs the reshuffling of lines in the source tweet text file and output it to another file.

  - In GUI, Src Tweet File should indicate where the original tweet file is.
  - In GUI, Dst Tweet File should indicate where the output tweet file will be.

## Similarity Tab

In this tab panel, it performs the Jacard similarity measurement between two different word2vec files. List of words is to list the words/terms for n-nearest words against word2vec files. The nearest word field is to set the n-nearest words with the respect to the word2vec files.

Output is the result of Jacard similarity measurement. It can be visualized with this applicaiton with an external Python prgram, plot.py. The figure will be stored where the similarity file is generated.



  - In GUI, word2vec File 1 should indicate where the word2vec file 1 is.
  - In GUI, word2vec File 2 should indicate where the word2vec file 2 is.
  - In GUI, Nearest words indicates number of n in nearest words. The default is 10 if it is not suggested in the field.
  - In GUI, Load the list of words from a file. The list of words file is a plain text file and the format of text is as below:

```sh
greece
eumigration
naval
nato
syria
turkey
aegan
refugees
refugee
crisis
migrant
refugeegr
germany
```

  - In GUI, Visualized button is to visualize the result of the similarity by
  - the output of the similarity file. It will save a png file under the same
  - folder where the similarity file is suggested. See the example below for
  - file name indication. To note, it is important for plot.py in where you store your similarity file:

```sh
simlarity file: jcard_sim_1iter_top10.txt
png file: jcard_sim_1iter_top10.txt.png
```