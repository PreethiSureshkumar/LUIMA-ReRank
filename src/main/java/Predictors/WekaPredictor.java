package Predictors;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.misc.SerializedClassifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.core.SerializationHelper;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import java_cup.internal_error;

/**
 * User: Ran Chen <ranc@cs.cmu.edu> Date: 10/13/14 Time: 8:18 PM
 */
public abstract class WekaPredictor extends Predictor {
	Classifier classifier;

	Instances train;

	Instances test;

	Instances preTest;

	String testLabel;

	Evaluation evaluation;

	public void loadTrainData(String fileName) throws Exception {
		String[] fileNames = fileName.split(";");
		for (int i = 0; i < fileNames.length; i++) {
			BufferedReader br = new BufferedReader(new FileReader(fileNames[i]));
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileNames[i]
					+ "_mod"));
			String line;
			String pattern = "([A-Za-z]*([\\.\\(\\)\\[\\-]*[0-9]+[\\.\\(\\)\\[\\-]+)+[A-Za-z]*)+";
			while ((line = br.readLine()) != null) {
				String line1 = line.replaceAll(pattern, "");
				bw.write(line1 + "\n");
			}
			bw.close();

			BufferedReader br1 = new BufferedReader(new FileReader(fileNames[i]
					+ "_mod"));
			if (train == null) {
                  train = new Instances(br1);
			} else {
				Instances tmpTrain = new Instances(br1);
				for (int j = 0; j < tmpTrain.numInstances(); j++) {
					Instance inst = tmpTrain.instance(j);
					train.add(inst);
				}
			}
			train.setClassIndex(train.numAttributes() - 1);
			br.close();
			br1.close();
			File tmpFile = new File(fileNames[i] + "_mod");
			tmpFile.delete();
		}
	}

	public void loadTrainData(String fileName, int last) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		train = new Instances(br);
		train.setClassIndex(train.numAttributes() - last);
		br.close();
	}

	public void loadTestData(String fileName) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(fileName + "_mod"));
		String line;
		String pattern = "([A-Za-z]*([\\.\\(\\)\\[\\-]*[0-9]+[\\.\\(\\)\\[\\-]+)+[A-Za-z]*)+";
		while ((line = br.readLine()) != null) {
			line.replaceAll(pattern, "");
			bw.write(line + "\n");
		}
		bw.close();

		BufferedReader br1 = new BufferedReader(new FileReader(fileName
				+ "_mod"));
		test = new Instances(br1);
		test.setClassIndex(test.numAttributes() - 1);
		testLabel = test.classAttribute().name();
		br.close();
		br1.close();
		File tmpFile = new File(fileName + "_mod");
		tmpFile.delete();
	}

	public void loadModel(String fileName) throws Exception {
		SerializedClassifier classifier = new SerializedClassifier();
		classifier.setModelFile(new File(fileName));
		this.classifier = classifier.getCurrentModel();
	}

	public void saveModel(String fileName) throws Exception {
		SerializationHelper.write(fileName, classifier);
	}

	public void trainModel() throws Exception {
		classifier.buildClassifier(train);

	}

	public void predict() throws Exception {
	}

	public void trainAndPrintResult() throws Exception {
		classifier.buildClassifier(train);
		evaluation = new Evaluation(train);
		evaluation.evaluateModel(classifier, test);
		System.out.println(evaluation.toSummaryString());
	}

	public void trainAndPrintResultInDetail() throws Exception {
		classifier.buildClassifier(train);
		int TP = 0, FP = 0, TN = 0, FN = 0;
		for (int i = 0; i < test.numInstances(); i++) {
			int trueValue = Integer.valueOf(test.get(i).stringValue(
					test.attribute(testLabel)));
			int predictValue = (int) classifier.classifyInstance(test
					.instance(i));
			TP += (trueValue == 1 && predictValue == 1) ? 1 : 0;
			FP += (trueValue == 0 && predictValue == 1) ? 1 : 0;
			TN += (trueValue == 0 && predictValue == 0) ? 1 : 0;
			FN += (trueValue == 1 && predictValue == 0) ? 1 : 0;
			System.out
					.println("true:" + trueValue + " predict:" + predictValue);
			System.out.println("Sentence " + (i + 1) + ": "
					+ preTest.instance(i).stringValue(0));
		}
		System.out.println("-----------------");
		System.out.println("TP:" + TP + "\t" + "FP:" + FP);
		System.out.println("FN:" + FN + "\t" + "TN:" + TN);
		System.out.println("-----------------");
		evaluation = new Evaluation(train);
		evaluation.evaluateModel(classifier, test);
		System.out.println(evaluation.toSummaryString(true));
	}

	public void crossValidationAndPrintResult(int folds) throws Exception {
		evaluation = new Evaluation(train);
		evaluation.crossValidateModel(classifier, train, folds, new Random(
				System.currentTimeMillis()));
		System.out.println(evaluation.toSummaryString());
	}

	public void stringToVector() throws Exception {
		StringToWordVector stringToWordVector = new StringToWordVector();
		stringToWordVector.setInputFormat(train);
		train = Filter.useFilter(train, stringToWordVector);
		preTest = new Instances(test);
		stringToWordVector.setInputFormat(test);
		test = Filter.useFilter(test, stringToWordVector);
	}
}
