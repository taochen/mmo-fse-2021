package org.sas.benchmark.sm.spo.published;

import org.ssase.requirement.froas.RequirementPrimitive;
import org.ssase.requirement.froas.RequirementProposition;
import org.ssase.util.Repository;

import jmetal.core.Solution;

public class CombineProposition extends RequirementProposition{
	//private double min = Double.MAX_VALUE;
	//private double max = Double.MIN_VALUE;
	
    public static double beta = 0.1;

	public CombineProposition(RequirementPrimitive asGoodAsPossible) {
		super(asGoodAsPossible);
	}

	public void fuzzilize(Solution s, int index) {
		if(s.getObjective(index) == Double.MAX_VALUE/100) {
			//s.setObjective(index, -1); //for p5 only
			return;
		}
		
		double v1 = 0.0 ;
		double v2 = 0.0 ;
		//double beta = 0.1;
		//double o1 =  normalize(s.getObjective(0));
		//double o2 =  normalize(s.getObjective(1));
		
		if(index == 0) {
			double original1 =  normalize(s.getObjective(0));
			double original2 =  Repository.getRequirementProposition("sas-rubis_software-P2").normalize(s.getObjective(1));
			
			double o1 = AutoRun.index == 0? original1 : original2;
			double o2 = AutoRun.index == 0? original2 : original1;
				
		    
		    
		    if(AutoRun.form.equals("linear")) {
			    v1 = o1 + beta * o2; 
			    v2 = o1 - beta * o2;
		    } else if(AutoRun.form.equals("sqrt")) {
			    v1 = o1 + beta * Math.sqrt(o2); 
			    v2 = o1 - beta * Math.sqrt(o2);
		    } else if(AutoRun.form.equals("square")) {
		    	v1 = o1 + beta * o2 * o2; 
				v2 = o1 - beta * o2 * o2;
		    }
		    
		    
			s.setObjective(0, v1);
			s.setObjective(1, v2);
		} else {
			 //v = (1.0-beta) * o2 + (0+beta) * o1;
			 //v = o2 - beta * o1;
			 //System.out.print("min " + min +"**\n");
			 //System.out.print("max " + max +"**\n");
		}
		
		
	
		//- function.fuzzilize(normalize(s.getObjective(index)),
			//	normalize(d));
		//System.out.print("max: " + max + " = min: " + min + "= normalized: " + normalize(s.getObjective(index)) + " = original: " + s.getObjective(index) + " = fuzzie: " + v +"\n");
	
	}

}
