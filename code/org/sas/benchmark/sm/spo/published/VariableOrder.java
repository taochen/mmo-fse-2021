package org.sas.benchmark.sm.spo.published;

import java.util.ArrayList;
import java.util.List;


public class VariableOrder {

	private static List<String> x264 = new ArrayList<String>();
	private static List<String> trimesh = new ArrayList<String>();
	private static List<String> storm_wc = new ArrayList<String>();
	private static List<String> storm_rs = new ArrayList<String>();
	private static List<String> DiatomSizeReduction = new ArrayList<String>();
	private static List<String> Coffee = new ArrayList<String>();
	private static List<String> storm_sol = new ArrayList<String>();
	private static List<String> LSTM = new ArrayList<String>();
	
	static {
		// The excluded ones are still here but they do not affect the order
		String[] array = new String[]{
				"no_mbtree",
				"no_asm",
				"no_cabac",
				"no_scenecut",
				"aq_strength",
				"bframes",
				"qcomp",
				"qp",
				"ref",
				"rc_lookahead",
				"b_bias",
				"threads",
				"keyint",
				"crf",
				"scenecut",
				"seek",
				"ipratio"			
		};
		
		attach(x264, array);
		
	
		array = new String[]{
				"F",
				"smoother",
				"colorGS",
				"relaxParameter",
				"V",
				"Jacobi",
				"line",
				"zebraLine",
				"cycle",
				"alpha",
				"beta",
				"preSmoothing",
				"postSmoothing"
				
		};
		
		attach(trimesh, array);
		
		array = new String[]{
				"spouts",
				"max_spout",
				"spout_wait",
				"spliters",
				"counters",
				"netty_min_wait"
				
		};
		
		attach(storm_wc, array);
		
		
		array = new String[]{
				"spouts",
				"max_spout",
				"sorters",
				"emit_freq",
				"chunk_size",
				"message_size"
				
		};
		
		attach(storm_rs, array);
		
	
	    
	    
		array = new String[]{
				"﻿vm_type",
				"a",
				"b",
				"c",
				"d",
				"e",
				"f",
				"g",
				"h",
				"i",
				"j",
				"k",
				"l"
			};
			
	    attach(DiatomSizeReduction, array);
	 
	    
	    array = new String[]{
				"﻿vm_type",
				"a",
				"b",
				"c",
				"d",
				"e",
				"f",
				"g",
				"h",
				"i",
				"j",
				"k",
				"l"
			};
			
	    attach(Coffee, array);
	    
	
	    
	    
	    
	  
	    
	    array = new String[]{
	    		"topology.workers",
	    		"component.bolt_num",
	    		"topology.acker.executors",
	    		"message.size",
	    		"component.spout_num",
	    		"topology.serialized.message.size.metrics",
	    		"topology.max.spout.pending",
	    		"storm.messaging.netty.min_wait_ms",
	    		"topology.transfer.buffer.size",
	    		"storm.messaging.netty.max_wait_ms",
	    		"topology.level",
	    		"topology.priority"
			};
			
	    attach(storm_sol, array);
	    
	    array = new String[]{
	    		"a","b","c","d","e","f","size","arch","link"
			};
			
	    attach(LSTM, array);
	    
	}
	
	
	
	
	public static List<String> getList(){
		

		if("x264".equals(Parser.selected)) {
			return x264;
		
		} else if("trimesh".equals(Parser.selected)) {
			return trimesh;
		} else if("storm-wc".equals(Parser.selected)) {
			return storm_wc;
		} else if("storm-rs".equals(Parser.selected)) {
			return storm_rs;
		
		} else if("dnn-dsr".equals(Parser.selected)) {
			return DiatomSizeReduction;
		
		} else if("dnn-coffee".equals(Parser.selected)) {
			return Coffee;
		
		} else if("storm-sol".equals(Parser.selected)) {
			return storm_sol;
		
		} else if("LSTM".equals(Parser.selected)) {
			return LSTM;
		}
		
		
		
		return null;
	}
	
	private static void attach(List<String> list, String[] array){
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		
	}
	
	public static void main(String[] arg) {
		for (int i = 0; i < x264.size(); i++) {
			//System.out.print(" <feature name=\""+X264.get(i)+"\" type=\"categorical\" optional=\"true\"/>\n");
			System.out.print("<item name=\""+x264.get(i)+"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\"1\" price_per_unit=\"0.5\"  />\n");
		}
	}
}
