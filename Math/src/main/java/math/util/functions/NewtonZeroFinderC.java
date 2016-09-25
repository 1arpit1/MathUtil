package math.util.functions;

import math.util.complex.Complex;
import math.util.complex.ComplexUtil;
import math.util.exceptions.UnperformableActionException;
import math.util.functions.interfaces.OneVariableFunctionC;
import math.util.functions.process.OneVariableIterativeProcessC;
import math.util.exceptions.IllegalArgumentException;
/**
 * THIS IS NEWTON METHOD TO FIND ZERO OF A COMPLEX POLINOMIAL<br>
 * ----------------------------------------------------------------------------------------------<br>
 * Newton-Raphson method, is a root-finding algorithm that uses the first few terms of the Taylor<br>
 * series of a function f(x) in the vicinity of a suspected root.<br>
 * ----------------------------------------------------------------------------------------------<br>
 * using taylor series approximation to first derivative only<br>
 * f(x+e)=f(x)+f'(x)(e)<br>
 * where x1=x+e<br>
 * if we assume f(x+e)=0<br>
 * then<br>
 * e=x1-x <br>
 * e=-f(x)/f'(x)<br>
 * so x1=x-(f(x)/f'(x))<br>
 * which is a iterative procedure<br>
 * and if  value of x(n+1) converges to x(n) <br>
 * then f(x) is zero<br>
 * with the assumption that f'(x) is not zero.<br>
 *  
 */
public class NewtonZeroFinderC extends OneVariableIterativeProcessC{
	private OneVariableFunctionC df;
	/**
	 * Defines the initial value for the search.
	 */
	public NewtonZeroFinderC( OneVariableFunctionC func, Complex start)
	{
		super( func);
		setInitialValue( start);
	}
	public NewtonZeroFinderC( OneVariableFunctionC func,OneVariableFunctionC dFunc, Complex start)	{
		this( func, start);
		setDerivative( dFunc);
	}
	public void setDerivative( OneVariableFunctionC dFunc)
	{
		df = new DerivativeFunctionC(func);
		if (!ComplexUtil.compare(df.value(result),dFunc.value(result)))
		{

			throw new IllegalArgumentException( "Supplied derivative function is inaccurate");
		}
		df = dFunc;
	}
	public void setFunction( OneVariableFunctionC func)
	{
		super.setFunction( func);
		df = null;
	}
	protected void initializeIterations() 
	{   
		//IF DERIVATIVE IS NULL,IT WILL ASSIGN A NEW DERIVATIVE FUNCTION 
		if ( df == null)
		{df = new DerivativeFunctionC(func);}
		int n = 0;
		//WHILE DERIVATIVE VALUE IS ZERO AT THE STATING POINT IT WILL SET DERIVATIVE
		//VALUE TO RANDOM SO THAT IT IS NOT ZERO AT STARTING POINT
		while ( ComplexUtil.compare( df.value(result),new Complex()))
		{if ( ++n > getMaximumIterations())
		{throw new  UnperformableActionException("unable to initialize the Iterations");}
		result=ComplexUtil.add(result, ComplexUtil.RandomComplex(1000));
		}
	}
	protected Complex evaluateIteration() 
	{
		Complex tempResult=result;
		int n=0;
		while(ComplexUtil.compare( df.value(tempResult),new Complex())){
			tempResult=ComplexUtil.add(tempResult, ComplexUtil.RandomComplex(100));
			if ( ++n > getMaximumIterations())
			{
				throw new  UnperformableActionException("unable to perform the Iteration,derivative is zero");
			}
		}
		Complex delta = ComplexUtil.div(func.value( tempResult),df.value(tempResult));

		result =ComplexUtil.sub(result, delta);
		return relativePrecision(new Complex(delta.getAbs(),Math.abs(delta.getArg()),true));
	}

}
