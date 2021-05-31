package org.sas.benchmark.sm.spo.published;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.femosaa.core.EAConfigure;
/**
 * 
 * @author tao
 *
 */
public class Parser {
 
	
	public static String[] p = new String[] {
			
			"-4.0,85.391",
			"-8.0,57.898",
			"-6.0,88.161"
			
			
	};
	
	
     public static String[] l = new String[] {
			
    		 "6_L2.csv",
    		 "6_L3.csv",
    		 "4_L4.csv",
    		 "8_L4.csv",
    		 "6_L1.csv",
    		 "4_L1.csv",
    		 "6_L4.csv",
    		 "8_L1.csv",
    		 "4_L2.csv",
    		 "8_L2.csv",
    		 "8_L3.csv",
    		 "4_L3.csv",
    		 "2_L4.csv",
    		 "2_L3.csv",
    		 "2_L2.csv",
    		 "2_L1.csv",
    		 "5_L4.csv",
    		 "7_L1.csv",
    		 "7_L3.csv",
    		 "7_L2.csv",
    		 "5_L3.csv",
    		 "5_L2.csv",
    		 "5_L1.csv",
    		 "7_L4.csv",
    		 "1_L2.csv",
    		 "1_L3.csv",
    		 "3_L4.csv",
    		 "1_L1.csv",
    		 "3_L1.csv",
    		 "1_L4.csv",
    		 "3_L2.csv",
    		 "3_L3.csv"
			
			
	};
	
	//public static String[] keepZero = {"BDBCAll","BDBJAll","X264All"};
	// two objectives
	public static LinkedHashMap<String, Double> map1 = new LinkedHashMap<String, Double>();
	public static LinkedHashMap<String, Double> map2 = new LinkedHashMap<String, Double>();
	public static LinkedHashMap<String, String> reverse_map = new LinkedHashMap<String, String>();
	public static List<String> seeds = new ArrayList<String>();
	public static String selected = "LSTM";

    public static void main( String[] args )
    {
    	
    	if(selected.equals("CONEX")) {
    		readConex();
    	} else if(selected.equals("LSTM")) {
    		readLSTM();
    	} else {
    		read(selected);
    	}
    	
    	//output();
    }
    
    
    public static void output(){
    	System.out.print("\n\n\n\n");
    	for (String s : p) {
    		String st = reverse_map.get(s);
    		String v = "";
    		if (st != null) {
    			v = st.split(":")[st.split(":").length-2] + " " +  st.split(":")[st.split(":").length-1];
    		} else {
    			v = "7 8";
    			System.out.print(v + " " + s.split(",")[1] + "\n");
    		}
    		
    	}
    }
    
    public static void readLSTM(){
    	// We only need to get rid of the mandatory one or those that do not change at all.
    	Set<String> set_str = new HashSet<String>();
    	ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
    	String[] names = null;
    	double time = 0.0;
    	
		
    	for(String f : l) {
    		try {
    			InputStream in = Parser.class.getResourceAsStream("/LSTM/"+f); 
    		    
    			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    			String line = null; 
    			
    		
    			if(f.endsWith("1.csv")) {

        			int o = 0;
        			
        			while ((line = reader.readLine()) != null) {
        				
        				if(line.contains("$") || o==0) {
        					String[] dd = line.split(",");
        					names = dd;
        					for(String s : dd) {
        						System.out.print("\"" + s + "\",\n");
        							
        					}
        					o++;
        					continue;
        				}
        				String r = "";
        				String[] data = line.split(",");
        				int k = 0;
        				int index = 0;
        				//System.out.print( data.length+"**\n");
        				for(int i = 0; i < data.length+3; i++) {
        					
        					if(i == 6 || i ==7) {
        						index++;
        						continue;
        					}
        					
        					if(list.size() < data.length+1) {
        						list.add(new ArrayList<Double>());
        					}
        					ArrayList<Double> subList = list.get(k);
        					
        					if(i == 2 || i == 3 || i == 4) {
        						
        						if(!subList.contains(0.0)) {
            						subList.add(0.0);
            					}
        						k++;
        						continue;
        					}
        					
        					///r += r.equals("")? data[i] : ":" + data[i];
        					
        					
        					
        					double v = 0.0;
        					if("L1".equals(data[index])) {
        					 v = 0.0;
        					} else {
        					 v = Double.parseDouble(data[index].replace("\"[", "").replace("]\"", ""));
        					}
        					
        					if(!subList.contains(v)) {
        						//System.out.print(v+"**\n");
        						subList.add(v);
        					}
        					k++;
        					index++;
        				}
        				
        				
        			}
    				
    			} else if(f.endsWith("2.csv")) {
    				

        			int o = 0;
        		
        			while ((line = reader.readLine()) != null) {
        				
        				if(line.contains("$") || o==0) {
        					String[] dd = line.split(",");
        					names = dd;
        					for(String s : dd) {
        						System.out.print("\"" + s + "\",\n");
        							
        					}
        					o++;
        					continue;
        				}
        				String r = "";
        				String[] data = line.split(",");
        				int k = 0;
        				int index = 0;
        				for(int i = 0; i < data.length+2; i++) {
        					
        					if(i == 6 || i ==7) {
        						index++;
        						continue;
        					}
        					
        					if(list.size() < data.length) {
        						list.add(new ArrayList<Double>());
        					}
        					ArrayList<Double> subList = list.get(k);
        					
        					if(i == 3 || i == 4) {
        						
        						if(!subList.contains(0.0)) {
            						subList.add(0.0);
            					}
        						k++;
        						continue;
        					}
        					
        					///r += r.equals("")? data[i] : ":" + data[i];
        					
        				
        					
        					double v = 0.0;
        					if("L2".equals(data[index])) {
        					 v = 1.0;
        					} else {
        					 v = Double.parseDouble(data[index].replace("\"[", "").replace("]\"", ""));
        					}
        					
        					if(!subList.contains(v)) {
        						subList.add(v);
        					}
        					k++;
        					index++;
        				}
        				
        				
        			}
    				
    			} else if(f.endsWith("3.csv")) {
    				
    				int o = 0;
    			
        			while ((line = reader.readLine()) != null) {
        				
        				if(line.contains("$") || o==0) {
        					String[] dd = line.split(",");
        					names = dd;
        					for(String s : dd) {
        						System.out.print("\"" + s + "\",\n");
        							
        					}
        					o++;
        					continue;
        				}
        				String r = "";
        				String[] data = line.split(",");
        				int k = 0;
        				int index = 0;
        				for(int i = 0; i < data.length+1; i++) {
        					
        					if(i == 6 || i ==7) {
        						index++;
        						continue;
        					}
        					
        					if(list.size() < data.length-1) {
        						list.add(new ArrayList<Double>());
        					}
        					ArrayList<Double> subList = list.get(k);
        					
        					if(i == 4) {
        						
        						if(!subList.contains(0.0)) {
            						subList.add(0.0);
            					}
        						k++;
        						continue;
        					}
        					
        					///r += r.equals("")? data[i] : ":" + data[i];
        					
        					
        					
        					double v = 0.0;
        					if("L3".equals(data[index])) {
        					 v = 2.0;
        					} else {
        					 v = Double.parseDouble(data[index].replace("\"[", "").replace("]\"", ""));
        					}
        					
        					if(!subList.contains(v)) {
        						subList.add(v);
        					}
        					k++;
        					index++;
        				}
        				
        				
        			}
    				
    			} else if(f.endsWith("4.csv")) {
    				int o = 0;
    			
        			while ((line = reader.readLine()) != null) {
        				
        				if(line.contains("$") || o==0) {
        					String[] dd = line.split(",");
        					names = dd;
        					for(String s : dd) {
        						System.out.print("\"" + s + "\",\n");
        							
        					}
        					o++;
        					continue;
        				}
        				String r = "";
        				String[] data = line.split(",");
        				int k = 0;
        				int index = 0;
        				for(int i = 0; i < data.length; i++) {
        					
        					if(i == 6 || i ==7) {
        						index++;
        						continue;
        					}
        					
        					if(list.size() < data.length-2) {
        						list.add(new ArrayList<Double>());
        					}
        					ArrayList<Double> subList = list.get(k);
        					
        					/*if(i == 4) {
        						
        						if(!subList.contains(0.0)) {
            						subList.add(0.0);
            					}
        						
        						continue;
        					}*/
        					
        					///r += r.equals("")? data[i] : ":" + data[i];
        					
        					
        					
        					double v = 0.0;
        					if("L4".equals(data[index])) {
        					 v = 3.0;
        					} else {
        					 v = Double.parseDouble(data[index].replace("\"[", "").replace("]\"", ""));
        					}
        					
        					if(!subList.contains(v)) {
        						subList.add(v);
        					}
        					k++;
        					index++;
        				}
        				
        				
        			}
    			}
    			
    			
    			
    			
    			reader.close();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
    	
		
		
		HashSet<Integer> set = new HashSet<Integer>();
		
		
		for(int i = 0; i < list.size(); i++) {
			ArrayList<Double> subList = list.get(i);
			// means it cannot be changed and has no variability
			if (subList.size() == 1) {
				set.add(i);
			} else {
				double[] d = new double[subList.size()];
				for(int j = 0; j < subList.size(); j++) {
					d[j] = subList.get(j);
				}
				
				
				Arrays.sort(d);
				
				subList.clear();
				for(int j = 0; j < d.length; j++) {
					subList.add((Double)d[j]);
					System.out.print("Oringal index: " + i + "=" + d[j] + "\n");
				}
				
				
			}
		}
		
		names = new String[] {"a","b","c","d","e","f","size","arch","link"};
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			System.out.print("<item name=\""+ names[i] +"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\""+(list.get(i).size()-1)+"\" price_per_unit=\"0.5\"  />\n");
			}
		
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			if(list.get(i).size() <= 2) {
				System.out.print("<feature name=\""+names[i]+"\" type=\"categorical\" optional=\"true\"/>\n");
			} else {
				System.out.print("<feature name=\""+names[i]+"\" type=\"numeric\" range=\"0 "+(list.get(i).size()-1)+"\" gap=\"1\" />\n");
			}
			}
		}
		
		System.out.print("Unchanged ones: " + set.toString() + "\n");
		//if (1==1)return;
		double[] v1_d = new double[] {Double.MAX_VALUE,Double.MIN_VALUE};
		double[] v2_d = new double[] {Double.MAX_VALUE,Double.MIN_VALUE};
		
		
		for(String f : l) {
			
			
		
    	
    	try {
    		InputStream in = Parser.class.getResourceAsStream("/LSTM/"+f); 
		    
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    		
    		String line = null; 
			
			
			
			
			int o = 0;
			
			while ((line = reader.readLine()) != null) {
				
				if(line.contains("$") || o==0) {
					o++;
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				double v1 = 0;
				double v2 = 0;
				
				if(f.endsWith("1.csv")) {
					int index = 0;
					int k  = 0;
					for(int i = 0; i < data.length + 3; i++) {
						

    					if(i == 6 || i ==7) {
    						index++;
    						continue;
    					}
    					
    					
    					if(i == 2 || i == 3 || i == 4) {
    						
    						r += ":" + "0";
    						k++;
    						continue;
    					}
				
						
    					if(!set.contains(i)) {
							ArrayList<Double> subList = list.get(k);
							String s = data[index].replace("\"[", "").replace("]\"", "");
							if("L1".equals(s)) {
								s = "0";
							}
							int v = subList.indexOf(Double.parseDouble(s));
							r += r.equals("")? v : ":" + v;
						}
						k++;
						index++;
						
					}
					
					v1 = "nan".equals(data[3]) ? 0.0 : Double.valueOf(data[3]);
					v2 = "nan".equals(data[4]) ? 0.0 : Double.valueOf(data[4]);
					
				} else if(f.endsWith("2.csv")) {
					int index = 0;
					int k  = 0;
					for(int i = 0; i < data.length + 2; i++) {
						

    					if(i == 6 || i ==7) {
    						index++;
    						continue;
    					}
    					
    					
    					if(i == 3 || i == 4) {
    						
    						r += ":" + "0";
    						k++;
    						continue;
    					}
				
						
    					if(!set.contains(i)) {
							ArrayList<Double> subList = list.get(k);
							String s = data[index].replace("\"[", "").replace("]\"", "");
							if("L2".equals(s)) {
								s = "1";
							}
							int v = subList.indexOf(Double.parseDouble(s));
							r += r.equals("")? v : ":" + v;
						}
						k++;
						index++;
						
					}
					
					v1 = "nan".equals(data[4]) ? 0.0 : Double.valueOf(data[4]);
					v2 = "nan".equals(data[5]) ? 0.0 : Double.valueOf(data[5]);
					
				} else if(f.endsWith("3.csv")) {
					int index = 0;
					int k  = 0;
					for(int i = 0; i < data.length + 1; i++) {
						

    					if(i == 6 || i ==7) {
    						index++;
    						continue;
    					}
    					
    					
    					if(i == 4) {
    						
    						r += ":" + "0";
    						k++;
    						continue;
    					}
				
						
    					if(!set.contains(i)) {
							ArrayList<Double> subList = list.get(k);
							String s = data[index].replace("\"[", "").replace("]\"", "");
							if("L3".equals(s)) {
								s = "2";
							}
							int v = subList.indexOf(Double.parseDouble(s));
							r += r.equals("")? v : ":" + v;
						}
						k++;
						index++;
						
					}
					
					v1 = "nan".equals(data[5]) ? 0.0 : Double.valueOf(data[5]);
					v2 = "nan".equals(data[6]) ? 0.0 : Double.valueOf(data[6]);
				} else if(f.endsWith("4.csv")) {
					int index = 0;
					int k  = 0;
					for(int i = 0; i < data.length; i++) {
						

    					if(i == 6 || i ==7) {
    						index++;
    						continue;
    					}
    					
    					
						
						if(!set.contains(i)) {
							ArrayList<Double> subList = list.get(k);
							String s = data[index].replace("\"[", "").replace("]\"", "");
							if("L4".equals(s)) {
								s = "3";
							}
							int v = subList.indexOf(Double.parseDouble(s));
							r += r.equals("")? v : ":" + v;
						}
						k++;
						index++;
						
					}
					
					v1 = "nan".equals(data[6]) ? 0.0 : Double.valueOf(data[6]);
					v2 = "nan".equals(data[7]) ? 0.0 : Double.valueOf(data[7]);
				}
				
				
				
				
				if(map1.containsKey(r)) {
					//System.out.print(line + " : " + r+ ", current "  +map1.get(r) +" duplicate\n");
				}
				seeds.add(r);
				
			
				
				if(v1 < 0) {
					v1 = Math.abs(v1);
				}
				
				if(v2 < 0) {
					v2 = Math.abs(v2);
				}
				
				if(v1 < v1_d[0] && v1 != 0) {
					v1_d[0] = v1;
				}
				
				if(v1 > v1_d[1] && v1 != 0) {
					v1_d[1] = v1;
				}
				
				if(v2 < v2_d[0]) {
					v2_d[0] = v2;
				}
				
				if(v2 > v2_d[1]) {
					v2_d[1] = v2;
				}
						
				map1.put(r, v1);
				map2.put(r, v2);
		
				//System.out.print(/*line + " : " + */r + "=" + map1.get(r)+ " and " + map2.get(r) +"\n");
				System.out.print("(" + map1.get(r)+ "," + map2.get(r) +")\n");
				set_str.add("("+(Math.round(map1.get(r) * 100.0) / 100.0)+ "," + (Math.round(map2.get(r) * 100.0) / 100.0) +")\n");
				
				if(!"nan".equals(data[data.length-1]))
				  time += Double.valueOf(data[data.length-1]);
			}
			
			System.out.print(map1.size() + "\n");
			
			
			System.out.print(v1_d[0] + " , " + v1_d[1] + "\n");
			System.out.print(1.0/v1_d[1] + " , " + 1.0/v1_d[0] + "\n");
			System.out.print(v2_d[0] + " , " + v2_d[1] + "\n");
			//System.out.print("Mean runtime: " + time/map1.size() + "\n");
			//getSeeds();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
        System.out.print("\n\n\n" + set_str.size() + "\n\n\n");
    	
    	for (String s : set_str) {
    		System.out.print(s);
    	}
		
    	
    }

    public static void readConex(){
    	// We only need to get rid of the mandatory one or those that do not change at all.
    	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    	ArrayList<String> names = new ArrayList<String>();
    	//String[] names = null;
    	double time = 0.0;
    	try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/conex-perf.csv"));
			String line = null; 
			
		
			int o = 0;
			while ((line = reader.readLine()) != null) {
				
				if(o==0) {
					String[] dd = line.split(",");
				
					for(int k = 1; k < dd.length; k++) {
						if(!dd[k].equals("") && !dd[k].equals("performance")) {
							names.add(dd[k]);
							System.out.print("\"" + dd[k] + "\",\n");
						}
						
							
					}
					o++;
					
					
					System.out.print("names " + names.size()+"\n");
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				for(int i = 1; i < data.length - 1; i++) {
					///r += r.equals("")? data[i] : ":" + data[i];
					if(list.size() <= i-1) {
						
						list.add(new ArrayList<String>());
					}
					//System.out.print(data.length + " " + list.size() + "\n");
					ArrayList<String> subList = list.get(i-1);
					if(!subList.contains(data[i])) {
						//System.out.print(data.length + " " + list.size() + "\n");
						subList.add(data[i]);
					}
					
				}
				
				
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HashSet<Integer> set = new HashSet<Integer>();
		
		
		for(int i = 0; i < list.size(); i++) {
			ArrayList<String> subList = list.get(i);
			// means it cannot be changed and has no variability
			if (subList.size() == 1) {
				set.add(i);
			} else {
				/*String[] d = new String[subList.size()];
				for(int j = 0; j < subList.size(); j++) {
					d[j] = subList.get(j);
				}*/
				
				
				//Arrays.sort(d);
				
				//subList.clear();
				for(int j = 0; j < subList.size(); j++) {
					//subList.add(d[j]);
					System.out.print(names.get(i) + " Oringal index: " + i + "=" + subList.get(j) + "\n");
				}
				
				
			}
			//System.out.print(" <feature name=\""+names.get(i)+"\" type=\"numeric\" range=\"0 "+(subList.size()-1)+"\" gap=\"1\"/>\n");
			//System.out.print("<item name=\""+names.get(i)+"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\""+(subList.size()-1)+"\" price_per_unit=\"0.5\"  />\n");
		}
		
		/*for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			System.out.print("<item name=\""+ names[i] +"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\""+(list.get(i).size()-1)+"\" price_per_unit=\"0.5\"  />\n");
			}
		
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			if(list.get(i).size() <= 2) {
				System.out.print("<feature name=\""+names[i]+"\" type=\"categorical\" optional=\"true\"/> />\n");
			} else {
				System.out.print("<feature name=\""+names[i]+"\" type=\"numeric\" range=\"0 "+(list.get(i).size()-1)+"\" gap=\"1\" />\n");
			}
			}
		}*/
		
		System.out.print("Unchanged ones: " + set.toString() + "\n");
		//if (1==1)return;
		
	
    	
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/conex-perf.csv"));
			String line = null; 
			
			int o = 0;
			while ((line = reader.readLine()) != null) {
				
				
				if(o==0) {
					o++;
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				for(int i = 1; i < data.length - 1; i++) {
					
					if(!set.contains(i)) {
						ArrayList<String> subList = list.get(i-1);
						int v = subList.indexOf(data[i]);
						/*for(String s : subList) {
							System.out.print(s + "**\n");
						}
						System.out.print(data[i] + " : " + " " + subList.contains(data[i])+"\n");*/
						r += r.equals("")? v : ":" + v;
					}
					
					
					
				}
				
				
				if(map1.containsKey(r)) {
					System.out.print(line + " : " + r+ ", current "  +map1.get(r) +" duplicate\n");
				}
				seeds.add(r);
				map1.put(r, Double.valueOf(data[data.length-1]));
		
				System.out.print(/*line + " : " + */r + "=" + map1.get(r)+ " and "+"\n");
				time += Double.valueOf(data[data.length-1]);
			}
			
			System.out.print(map1.size() + "\n");
			System.out.print("Mean runtime: " + time/map1.size() + "\n");
			//getSeeds();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/conex.txt"));
			String line = null; 
			
			int o = 0;
			ArrayList<Integer> new_ids= new ArrayList<Integer>();
			while ((line = reader.readLine()) != null) {
				
				
				if(o==0) {
					o++;
					String[] data = line.split("	");
					
					for (int k = 0; k < names.size(); k++) {
						
						int l = -1;
						for(int i = 0; i < data.length; i++) {
							if(names.get(k).equals(data[i])) {
								
								l = i;
								System.out.print(new_ids.size() + " : " + data[i] + " " + l+"***\n");
							}
						}
						
						if(l == -1) {
							System.out.print(names.get(k) + " has no found\n");
						}
						
						new_ids.add(l);
						//System.out.print(data.length + " " + l+"***\n");
					}
					
					System.out.print(list.size() + " : " + new_ids.size() + " " +"size***\n");
					continue;
				}
				String r = "";
				String[] data = line.split("	");
				
				/*for(String s : data) {
					System.out.print(s+"\n");
				}
				System.out.print("-----\n");
				*/
				for (int k = 0; k < new_ids.size();k++) {
					ArrayList<String> subList = list.get(k);
					//System.out.print(data.length + " : " + names.get(k) + " : " + data[new_ids.get(k)] + "***\n");
					int v = subList.indexOf(convert(data[new_ids.get(k)]));
					r += r.equals("")? v : ":" + v;
				}
				
				if(map2.containsKey(r)) {
					System.out.print(r+ ", current "  +map2.get(r) +" duplicate\n");
				}
				
				if(!"".equals(data[1])) {
					map2.put(r, Double.valueOf(data[1]));
				}
				
				
		
				System.out.print(/*line + " : " + */r + "=" + map1.get(r)+ " and "+ map2.get(r)+"\n");
				
				//time += Double.valueOf(data[data.length-1]);
			}
			
			System.out.print(map2.size() + "\n");
			
			int p = 0;
			for (String s : map1.keySet()) {
				if(map2.containsKey(s)) {
					p++;
				}
			}
			System.out.print(p);
			//System.out.print("Mean runtime: " + time/map1.size() + "\n");
			//getSeeds();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static String convert(String s) {
    	if("FALSE".equals(s)) {
    		return "False";
    	}
    	
    	if("TRUE".equals(s)) {
    		return "True";
    	}
    	
    	return s;
    }
    
    public static void read(String name){
    	
    	Set<String> set_str = new HashSet<String>();
    	// We only need to get rid of the mandatory one or those that do not change at all.
    	ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
    	String[] names = null;
    	double time = 0.0;
    	try {
    		InputStream in = Parser.class.getResourceAsStream("/"+name+".csv"); 
    		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    	    String line = null; 
			
		
			int o = 0;
			while ((line = reader.readLine()) != null) {
				
				if(line.contains("$") || o==0) {
					String[] dd = line.split(",");
					names = dd;
					for(String s : dd) {
						System.out.print("\"" + s + "\",\n");
							
					}
					o++;
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				for(int i = 0; i < data.length - 2; i++) {
					///r += r.equals("")? data[i] : ":" + data[i];
					if(list.size() <= i) {
						list.add(new ArrayList<Double>());
					}
					
					ArrayList<Double> subList = list.get(i);
					if(!subList.contains(Double.parseDouble(data[i]))) {
						subList.add(Double.parseDouble(data[i]));
					}
					
				}
				
				
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HashSet<Integer> set = new HashSet<Integer>();
		
		
		for(int i = 0; i < list.size(); i++) {
			ArrayList<Double> subList = list.get(i);
			// means it cannot be changed and has no variability
			if (subList.size() == 1) {
				set.add(i);
			} else {
				double[] d = new double[subList.size()];
				for(int j = 0; j < subList.size(); j++) {
					d[j] = subList.get(j);
				}
				
				
				Arrays.sort(d);
				
				subList.clear();
				for(int j = 0; j < d.length; j++) {
					subList.add((Double)d[j]);
					System.out.print("Oringal index: " + i + "=" + d[j] + "\n");
				}
				
				
			}
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			System.out.print("<item name=\""+ names[i] +"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\""+(list.get(i).size()-1)+"\" price_per_unit=\"0.5\"  />\n");
			}
		
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			if(list.get(i).size() <= 2) {
				System.out.print("<feature name=\""+names[i]+"\" type=\"categorical\" optional=\"true\"/>\n");
			} else {
				System.out.print("<feature name=\""+names[i]+"\" type=\"numeric\" range=\"0 "+(list.get(i).size()-1)+"\" gap=\"1\" />\n");
			}
			}
		}
		
		System.out.print("Unchanged ones: " + set.toString() + "\n");
		//if (1==1)return;
    	
		double[] v1_d = new double[] {Double.MAX_VALUE,Double.MIN_VALUE};
		double[] v2_d = new double[] {Double.MAX_VALUE,Double.MIN_VALUE};
    	try {
    		
    		InputStream in = Parser.class.getResourceAsStream("/"+name+".csv"); 
    		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    		//BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/"+name+".csv"));
			String line = null; 
			int o = 0;
			
			while ((line = reader.readLine()) != null) {
				
				if(line.contains("$") || o==0) {
					o++;
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				for(int i = 0; i < data.length - 2; i++) {
					
					if(!set.contains(i)) {
						ArrayList<Double> subList = list.get(i);
						int v = subList.indexOf(Double.parseDouble(data[i]));
						r += r.equals("")? v : ":" + v;
					}
					
					
					
				}
				
				
				if(map1.containsKey(r)) {
					//System.out.print(line + " : " + r+ ", current "  +map1.get(r) +" duplicate\n");
				}
				seeds.add(r);
				
				double v1 = "nan".equals(data[data.length-2]) ? 0.0 : Double.valueOf(data[data.length-2]);
				double v2 = "nan".equals(data[data.length-1]) ? 0.0 : Double.valueOf(data[data.length-1]);
				
				if(v1 < 0) {
					v1 = Math.abs(v1);
				}
				
				if(v2 < 0) {
					v2 = Math.abs(v2);
				}
						
				map1.put(r, v1);
				map2.put(r, v2);
				reverse_map.put((-1*v1)+","+v2, r);
				
				if(v1 < v1_d[0] && v1 != 0) {
					v1_d[0] = v1;
				}
				
				if(v1 > v1_d[1] && v1 != 0) {
					v1_d[1] = v1;
				}
				
				if(v2 < v2_d[0]) {
					v2_d[0] = v2;
				}
				
				if(v2 > v2_d[1]) {
					v2_d[1] = v2;
				}
		
				//System.out.print(/*line + " : " + */r + "=" + map1.get(r)+ " and " + map2.get(r) +"\n");
				System.out.print("("+map1.get(r)+ "," + map2.get(r) +")\n");
				set_str.add("("+(Math.round(map1.get(r) * 1000.0) / 1000.0)+ "," + (Math.round(map2.get(r) * 100.0) / 100.0) +")\n");
				if(!"nan".equals(data[data.length-1]))
				  time += Double.valueOf(data[data.length-1]);
			}
			
			System.out.print(map1.size() + "\n");
			System.out.print("Mean runtime: " + time/map1.size() + "\n");
			
			System.out.print(v1_d[0] + " , " + v1_d[1] + "\n");
			System.out.print(1.0/v1_d[1] + " , " + 1.0/v1_d[0] + "\n");
			System.out.print(v2_d[0] + " , " + v2_d[1] + "\n");
			//getSeeds();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    	System.out.print("\n\n\n" + set_str.size() + "\n\n\n");
    	
    	for (String s : set_str) {
    		System.out.print(s);
    	}
		
	}
	
    public static void validateUnchanged(){
    	
    	
    }
    
	public static void validate(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/fuzzy-requirement/single-objective-dataset/"+selected+".csv"));
			String line = null; 
			
			int[] store = null;
			int total = 0;
			while ((line = reader.readLine()) != null) {
				
				if(line.startsWith("$")) {
					String[] d = line.split(",");
					for (int i = 0; i < d.length; i++) {
						//System.out.print("\""+d[i].substring(1) + "\",\n");
					}
					
					continue;
				}
				
				String[] data = line.split(",");
				
				if(store == null) {
					store = new int[data.length - 1];
					for(int i = 0; i < store.length; i++) {
						store[i] = 0;
					}
				}
				
				for(int i = 0; i < store.length; i++) {
					
					if(data[i].equals("1")) {
						store[i] += 1;
					} 
				}
				
				total++;
		
			}
			
			String r = "";
			for(int i = 0; i < store.length; i++) {
				
				if(store[i] == total) { 
					r += i + ",";
				}
			}
			
			System.out.print(r);
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static List<String> getSeeds(){
		
		int no = EAConfigure.getInstance().pop_size;
		List<String> list = new ArrayList<String>();
		
		/*for (int i = 0; i < seeds.size(); i++ ) {
			   System.out.print(i+"\n");
			   list.add(seeds.get(i));	
			}*/
		
		
		
		int gap = seeds.size() / no;
		
		for (int i = 0; i < seeds.size(); i=i+gap ) {
		   System.out.print(i+"\n");
		   list.add(seeds.get(i));	
		}
		
		if (list.size() < no) {
			list.add(seeds.get(seeds.size()-1));
		}
		
		if (list.size() > no) {
			list.remove(list.size()-1);
		}
		
		for (int i = 0; i < list.size(); i++ ) {
			System.out.print(list.get(i) + "\n");
		}
		System.out.print(list.size());
		return list;
		
	}
	
	private static void normalize(){
		double max =  17.894581279143072;
		double v = 4.1823277703510335;
		double min = 0;
		
		v = (v - min) / (max - min);
		
		System.out.print((0.3 * v) + 1.2);
	}
	private static void run_normalize(){
		String[] a = new String[]{"13.0", "14.5", "15.5"};
		double max = 16.851;
		
		double min = 12.513;
		
		for (String s : a) {
			
			double v = Double.parseDouble(s);
			v = (v - min) / (max - min);
			
			System.out.print(v+";");
		}
		
	}
}
