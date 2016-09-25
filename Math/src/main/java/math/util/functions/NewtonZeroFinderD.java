package math.util.functions;

import math.util.Constants;
import math.util.exceptions.IllegalArgumentException;
import math.util.exceptions.UnperformableActionException;
import math.util.functions.interfaces.OneVariableFunctionD;
import math.util.functions.process.OneVariableIterativeProcessD;

/**
 * THIS IS NEWTON METHOD TO FIND ZERO OF A POLINOMIAL<br>
 * -----------------------------------------------------------------------------
 * -----------------<br>
 * Newton-Raphson method, is a root-finding algorithm that uses the first few
 * terms of the Taylor<br>
 * series of a function f(x) in the vicinity of a suspected root.<br>
 * -----------------------------------------------------------------------------
 * -----------------<br>
 * using taylor series approximation to first derivative only<br>
 * f(x+e)=f(x)+f'(x)(e)<br>
 * where x1=x+e<br>
 * if we assume f(x+e)=0<br>
 * then<br>
 * e=x1-x <br>
 * e=-f(x)/f'(x)<br>
 * so x1=x-(f(x)/f'(x))<br>
 * which is a iterative procedure<br>
 * and if value of x(n+1) converges to x(n) <br>
 * then f(x) is zero<br>
 * with the assumption that f'(x) is not zero.<br>
 * 
 */
public class NewtonZeroFinderD extends OneVariableIterativeProcessD {

	/**
	 * Derivative of the function for which the zero will be found.
	 */
	private OneVariableFunctionD df;

	/**
	 * Defines the initial value for the search.
	 */
	public NewtonZeroFinderD(OneVariableFunctionD func, double start) {
		super(func);
		setInitialValue(start);
	}

	/**
	 * Constructor method.
	 * 
	 * @param func
	 *            the function for which the zero will be found.
	 * @param dFunc
	 *            derivative of func.
	 * @param start
	 *            the initial value for the search.
	 */
	public NewtonZeroFinderD(OneVariableFunctionD func, OneVariableFunctionD dFunc, double start) {
		this(func, start);
		setDerivative(dFunc);
	}

	/**
	 * THIS WILL SET THE DERIVATIVE FUNCTION ,IF THE DERIVATIVE IS NOT CORRECT
	 * IT WILL THROW AN EXCEPTION
	 */
	public void setDerivative(OneVariableFunctionD dFunc) {
		df = new DerivativeFunctionD(func);
		if (!Constants.equal(df.value(result), dFunc.value(result), 0.01)) {
			throw new IllegalArgumentException("Supplied derivative function is inaccurate");
		}
		df = dFunc;
	}

	/**
	 * SET FUNCTION TO INPUT, DERIVATIVE TO NULL
	 */
	public void setFunction(OneVariableFunctionD func) {
		super.setFunction(func);
		df = null;
	}

	/**
	 * Initializes internal parameters to start the iterative process. Assigns
	 * default derivative if necessary.
	 * 
	 * @throws UnperformableAction
	 */
	protected void initializeIterations() {
		// IF DERIVATIVE IS NULL,IT WILL ASSIGN A NEW DERIVATIVE FUNCTION
		if (df == null) {
			df = new DerivativeFunctionD(func);
		}
		if (Double.isNaN(result)) {
			result = 0;
		}
		int n = 0;
		// WHILE DERIVATIVE VALUE IS ZERO AT THE STATING POINT IT WILL SET
		// DERIVATIVE
		// VALUE TO RANDOM SO THAT IT IS NOT ZERO AT STARTING POINT
		while (Constants.equal(df.value(result), 0)) {
			if (++n > getMaximumIterations()) {
				throw new UnperformableActionException("unable to initialize the Iterations");
			}
			result += Math.random();
		}
	}

	/**
	 * Evaluate the result of the current interation.
	 * 
	 * @return the estimated precision of the result.
	 * @throws UnperformableAction
	 */
	protected double evaluateIteration() throws UnperformableActionException {
		double tempResult = result;
		int n = 0;
		while ((df.value(tempResult)) == 0) {
			tempResult = result + (Math.random() / 100);
			if (++n > getMaximumIterations()) {
				throw new UnperformableActionException("unable to perform the Iteration,derivative is zero");
			}
		}
		double delta = func.value(tempResult) / df.value(tempResult);
		result -= delta;
		return relativePrecision(Math.abs(delta));
	}

}
