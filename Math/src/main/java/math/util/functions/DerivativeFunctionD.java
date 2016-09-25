package math.util.functions;

import math.util.functions.interfaces.OneVariableFunctionD;

public class DerivativeFunctionD implements OneVariableFunctionD {
	/**
	 * THIS IS USED AS FUNCTION OF WHICH WE HAVE TO FIND DERIVATIVE OF.
	 */
	private OneVariableFunctionD function;
	private double relativePrecision = 0.000001;

	public DerivativeFunctionD(OneVariableFunctionD function, double relativePrecision) {
		this.function = function;
		this.relativePrecision = relativePrecision;
	}

	public DerivativeFunctionD(OneVariableFunctionD function) {
		this.function = function;
	}

	public OneVariableFunctionD getFunction() {
		return function;
	}

	/**
	 * THIS RETURN THE DERIVATIVE VALUE OF THE FUNCTION <br>
	 * USING FORMULA
	 * 
	 * <pre>
	 * dF(x)       lim    f(x1)  -  f(x2)    
	 * ----     =  e->0   -----------------
	 *  dx                     x1-x2
	 *  
	 *  where 
	 *  
	 *  x1    =  x * (1+e)
	 *  x2    =  x * (1-e)
	 *  x1-x2 =  2 * x * e
	 * </pre>
	 * 
	 * @param x=derivative
	 *            at value
	 * @return
	 */
	public double value(double x) {
		double x1 = x == 0 ? relativePrecision : x * (1 + relativePrecision);
		double x2 = 2 * x - x1;
		return ((this.function.value(x1) - this.function.value(x2)) / (x1 - x2));
	}
}
