package org.sas.benchmark.sm.spo.published;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.ssase.model.Delegate;

public class BenchmarkDelegate implements Delegate{

	static String prefix = "/Users/" + System.getProperty("user.name") + "/research/monitor/";
	private int obj_index = 0;
	public static Set<String> set = new HashSet<String>();
	
	
	public BenchmarkDelegate(int obj_index) {
		super();
		this.obj_index = obj_index;
	}

	
	

	@Override
	public double predict(double[] xValue) {
		String v = "";
		for(int i = 0; i < xValue.length; i++) {
			v += v.equals("")? (int)xValue[i] : ":" + (int)xValue[i];
		}
	
		HashMap<String, Double> map = obj_index == 0? Parser.map1 : Parser.map2;

		
		if(map.containsKey(v)) {
			
			
			
			
			double r = map.get(v);
			
			if(!Parser.map1.containsKey(v) || !Parser.map2.containsKey(v)) {
				return Double.MAX_VALUE;
			}
			
			if(Parser.map1.get(v) == 0 || Parser.map2.get(v) == 0) {
				return Double.MAX_VALUE;
			}
			// Only needed for certain benchmarks
			if(obj_index == 0 && !"lstm".equals(AutoRun.benchmark)) {
				r = -1.0*r;
			}
			
			if(!set.contains(v)) {
				try {
					System.out.print("New measurement of configuration " + v + " starts\n");
					Thread.sleep(emulatedTime());
					System.out.print("New measurement completed\n");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				set.add(v);
			} 			
			
			
			return r*100;
		} else {
			return Double.MAX_VALUE;
		}
		
	
	}
	
    public double predict2(double[] xValue) {
		
		String v = "";
		for(int i = 0; i < xValue.length; i++) {
			v += v.equals("")? (int)xValue[i] : ":" + (int)xValue[i];
		}
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		// Windows
		processBuilder.command("sudo sh", prefix + "system-interface", v);
		processBuilder.redirectErrorStream(true);
		
		double r = 0.0;

		try {

			Process process = processBuilder.start();
			int exitCode = process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				r =  Double.parseDouble(line);
				break;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		return r;
	}


    private long emulatedTime() {
    	
    	long budget = 7200000;
    	if ("trimesh".equals(AutoRun.benchmark)) {
    		return budget/1000;
    	} else if ("x264".equals(AutoRun.benchmark)) {
    		return budget/2500;
    	} else if ("storm-wc".equals(AutoRun.benchmark)) {
    		return budget/600;
    	} else if ("storm-rs".equals(AutoRun.benchmark)) {
    		return budget/900;
    	} else if ("storm-sol".equals(AutoRun.benchmark)) {
    		return budget/700;
    	} else if ("dnn-dsr".equals(AutoRun.benchmark)) {
    		return budget/800;
    	} else if ("dnn-coffee".equals(AutoRun.benchmark)) {
    		return budget/900;
    	} else if ("LSTM".equals(AutoRun.benchmark)) {
    		return budget/400;
    	}
    	
    	return 0;
    }
}
