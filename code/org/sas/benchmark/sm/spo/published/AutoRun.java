package org.sas.benchmark.sm.spo.published;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.femosaa.core.EAConfigure;
import org.femosaa.core.SASAlgorithmAdaptor;
import org.ssase.util.Repository;
import org.ssase.util.Ssascaling;
 
public class AutoRun {

	private static String[] weights = new String[] { "1.0-0.0", "0.0-1.0" };
	private static String[] single_algs = new String[] { "sa", "ga", "hc", "rs" };
	private static String[] multi_algs = new String[] { "nsgaii" };
	public static String benchmark = "LSTM";
	public static String form = "linear";// linear, sqrt, square
	public static int index = 0;
	public static int pop = 0;

	private static double[] w_a = new double[] { 0.01, 0.1, 0.3, 0.5, 0.7, 0.9, 10 };

	public static void main(String[] args) {

		String s = args[0];

		if ("trimesh".equals(s)) {
			benchmark = "trimesh";
		} else if ("x264".equals(s)) {
			benchmark = "x264";
		} else if ("storm-wc".equals(s)) {
			benchmark = "storm-wc";
		} else if ("storm-rs".equals(s)) {
			benchmark = "storm-rs";
		} else if ("storm-sol".equals(s)) {
			benchmark = "storm-sol";
		} else if ("dnn-dsr".equals(s)) {
			benchmark = "dnn-dsr";
		} else if ("dnn-coffee".equals(s)) {
			benchmark = "dnn-coffee";
		} else if ("LSTM".equals(s)) {
			benchmark = "LSTM";
		} else {
			System.out.print("The entered configurable system is not supported at the moment!");
			return;
		}

		if (args.length == 1) {
			Simulator.n = 30;
		} else {

			try {

				int n = Integer.parseInt(args[1]);
				Simulator.n = n;
			} catch (Throwable t) {
				System.out.print("The second argument needs to be an integer (the number of runs)!");
			}

		}

		prepare();

		mo();
		so();

	}

	public static void mo() {

		Parser.selected = benchmark;
		Simulator.setup();
		// SASAlgorithmAdaptor.isSeedSolution =false;
		SASAlgorithmAdaptor.isFuzzy = true;
		SASAlgorithmAdaptor.logGenerationOfObjectiveValue = -1;

		File f = new File(System.getProperty("user.dir") + "/results/temp");

		try {
			if (f.exists()) {
				delete(f);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] forms = new String[] { "linear", "sqrt", "square" };
		int[] ii = new int[] { 0, 1 };
		// index = 0;
		// double best = Double.MAX_VALUE;
		// double best_w = 0.0;

		for (String ff : forms) {

			for (int i : ii) {
				index = i;
				for (double w : w_a) {
					form = ff;
					CombineProposition.beta = w;
					run_MOEA(index, w, form);
					/*
					 * double a12 = Data.readObjective(index, form);
					 * 
					 * if (a12 > best) { best = a12; best_w = w; }
					 */

					/*
					 * double m = Data.readObjectiveMedian(index, ff);
					 * 
					 * if (m < best) { best = m; best_w = w; }
					 */
					System.out.print("Objective: " + index + ", on form: " + form + ", w: " + w + ", i: " + i);
				}

			}
		}

		SASAlgorithmAdaptor.isFuzzy = false;
		run_MOEA(-1, -1, "none");

		/*
		 * form = "linear"; CombineProposition.beta = 10; run_MOEA(form); form = "sqrt";
		 * CombineProposition.beta = 0.8; run_MOEA(form); form = "square";
		 * CombineProposition.beta = 0.01; run_MOEA(form);
		 */
	}

	public static void so() {
		// Parser.selected = benchmark;
		// Simulator.setup();
		// SASAlgorithmAdaptor.isSeedSolution = false;
		SASAlgorithmAdaptor.isFuzzy = false;
		SASAlgorithmAdaptor.logGenerationOfObjectiveValue = -1;
		SASAlgorithmAdaptor.isWeightedSumNormalized = false;

		File f = new File(System.getProperty("user.dir") + "/results/temp");

		try {
			if (f.exists()) {
				delete(f);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String p : weights) {

			String[] s = p.split("-");
			double[] w = new double[s.length];
			for (int i = 0; i < s.length; i++) {
				w[i] = Double.parseDouble(s[i]);
			}

			for (String alg : single_algs) {

				/*
				 * if("ga".equals(alg) && (benchmark.equals("SS-M") ||
				 * benchmark.equals("SS-N"))) { SASAlgorithmAdaptor.isSeedSolution = true; }
				 * else { SASAlgorithmAdaptor.isSeedSolution = true; }
				 */

				Simulator.alg = alg;
				Simulator.weights = w;

				Simulator.main_test();

				File source = new File(System.getProperty("user.dir") + "/results/temp");
				File r = new File(
						System.getProperty("user.dir") + "/results/" + p + "/" + benchmark + "/" + alg + "/" + "/sas");
				File dest = new File(
						System.getProperty("user.dir") + "/results/" + p + "/" + benchmark + "/" + alg + "/" + "/sas");

				if (r.exists()) {
					System.out.print("Remove " + r + "\n");
					try {
						delete(r);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (!dest.exists()) {
					dest.mkdirs();
				}

				try {
					copyFolder(source, dest);
					if (source.exists()) {
						System.out.print("Remove " + source + "\n");
						delete(source);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.print("End of " + "/Users/" + System.getProperty("user.dir") + "/results/" + p + "/"
						+ benchmark + "/" + alg + "/" + "\n");
				// try {
				// Thread.sleep((long)2000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

			}
		}

	}

	public static void run_MOEA(int i, double w, String form) {
		for (String alg : multi_algs) {
			Simulator.alg = alg;

			Simulator.main_test();

			File source = new File(System.getProperty("user.dir") + "/results/temp");
			File r = new File(System.getProperty("user.dir") + "/results/" + benchmark + "/" + form + "/" + alg + "/"
					+ i + "/" + w + "/" + "/sas");
			File dest = new File(System.getProperty("user.dir") + "/results/" + benchmark + "/" + form + "/" + alg + "/"
					+ i + "/" + w + "/" + "/sas");

			if (r.exists()) {
				System.out.print("Remove " + r + "\n");
				try {
					delete(r);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (!dest.exists()) {
				dest.mkdirs();
			}

			try {
				copyFolder(source, dest);
				if (source.exists()) {
					System.out.print("Remove " + source + "\n");
					delete(source);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.print("End of " + "/Users/" + System.getProperty("user.dir") + "/results" + "/" + benchmark + "/"
					+ form + "/" + alg + "/" + i + "/" + w + "/" + "\n");

		}
		File f = new File(System.getProperty("user.dir") + "/results/temp");

		try {
			if (f.exists()) {
				delete(f);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void prepare() {

		org.ssase.util.Logger.prefix = System.getProperty("user.dir") + "/results/temp/";
		org.femosaa.util.Logger.prefix = System.getProperty("user.dir") + "/results/temp/";
		Repository.mac_path = System.getProperty("user.dir") + "/weight";
		Repository.other_path = System.getProperty("user.dir") + "/weight";
		if ("trimesh".equals(AutoRun.benchmark)) {
			pop = 20;
			EAConfigure.getInstance().measurement = 1000;
			Ssascaling.dom0 = "benchmark/flash/dom0_ssm.xml";
			Ssascaling.feature_model = "benchmark/flash/feature_model_ssm.xml";
			SASAlgorithmAdaptor.isSeedSolution = true;
		} else if ("x264".equals(AutoRun.benchmark)) {
			pop = 50;
			EAConfigure.getInstance().measurement = 2500;
			Ssascaling.dom0 = "benchmark/flash/dom0_ssn.xml";
			Ssascaling.feature_model = "benchmark/flash/feature_model_ssn.xml";
			SASAlgorithmAdaptor.isSeedSolution = true;
		} else if ("storm-wc".equals(AutoRun.benchmark)) {
			pop = 50;
			EAConfigure.getInstance().measurement = 600;
			Ssascaling.dom0 = "benchmark/flash/dom0_ssk.xml";
			Ssascaling.feature_model = "benchmark/flash/feature_model_ssk.xml";
		} else if ("storm-rs".equals(AutoRun.benchmark)) {
			pop = 50;
			EAConfigure.getInstance().measurement = 900;
			Ssascaling.dom0 = "benchmark/flash/dom0_ssj.xml";
			Ssascaling.feature_model = "benchmark/flash/feature_model_ssj.xml";
		} else if ("storm-sol".equals(AutoRun.benchmark)) {
			pop = 50;
			EAConfigure.getInstance().measurement = 700;
			Ssascaling.dom0 = "benchmark/flash/dom0_feature7.xml";
			Ssascaling.feature_model = "benchmark/flash/feature_model_feature7.xml";
		} else if ("dnn-dsr".equals(AutoRun.benchmark)) {
			pop = 60;
			EAConfigure.getInstance().measurement = 800;
			Ssascaling.dom0 = "benchmark/flash/dom0_dnn_diatomsizereduction.xml";
			Ssascaling.feature_model = "benchmark/flash/feature_model_dnn_diatomsizereduction.xml";
		} else if ("dnn-coffee".equals(AutoRun.benchmark)) {
			pop = 50;
			EAConfigure.getInstance().measurement = 900;
			Ssascaling.dom0 = "benchmark/flash/dom0_dnn_coffee.xml";
			Ssascaling.feature_model = "benchmark/flash/feature_model_dnn_coffee.xml";
		} else if ("LSTM".equals(AutoRun.benchmark)) {
			pop = 20;
			EAConfigure.getInstance().measurement = 400;
			Ssascaling.dom0 = "benchmark/flash/dom0_lstm.xml";
			Ssascaling.feature_model = "benchmark/flash/feature_model_lstm.xml";
			SASAlgorithmAdaptor.isSeedSolution = true;
		}
	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to " + dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}

	}

	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				// System.out.println("Directory is deleted : "
				// + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					// System.out.println("Directory is deleted : "
					// + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			// System.out.println("File is deleted : " +
			// file.getAbsolutePath());
		}
	}
}
