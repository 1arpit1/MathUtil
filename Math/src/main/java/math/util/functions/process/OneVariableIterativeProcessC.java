package math.util.functions.process;

import math.util.Constants;
import math.util.complex.Complex;
import math.util.complex.ComplexUtil;
import math.util.functions.interfaces.OneVariableFunctionC;
import math.util.exceptions.IllegalArgumentException;

public abstract class OneVariableIterativeProcessC {
	private int noOfIterations;
	private Complex desiredPrecision=new Complex(0.000001, 0.001, true);
	private Complex precision;
	private int maximumIterations=1000;
	protected Complex result =new Complex();
	public void setInitialValue( Complex x)
	{
		result = x;
	}
	public Complex getResult( )
	{
		return result;
	}
	protected OneVariableFunctionC func;

	public OneVariableIterativeProcessC() {}

	public OneVariableIterativeProcessC(int maximumIterations, OneVariableFunctionC func) {
		this.maximumIterations = maximumIterations;
		this.func = func;
	}
	public OneVariableIterativeProcessC(OneVariableFunctionC func){
		this.func=func;
	}
	public int getNoOfIterations() {
		return noOfIterations;
	}
	public void setFunction( OneVariableFunctionC func)
	{
		this.func = func;
	}
	public int getMaximumIterations( )
	{
		return maximumIterations;
	}
	public void setMaximumIterations( int maxIter)
	{
		if ( maxIter < 1 )
		{
			throw new IllegalArgumentException( "Non-positive maximum iteration: "+maxIter);
		}
		maximumIterations = maxIter;
	}
	public Complex getDesiredPrecision( )
	{
		return desiredPrecision;
	}
	public void setDesiredPrecision( Complex precision )
	{
		if ( precision.getArg() <= 0 )
		{
			throw new IllegalArgumentException( "Non-positive theta precision: "+precision.getArg());
		}
		desiredPrecision = precision;
	}
	public Complex getPrecision()
	{
		return precision;
	}
	protected void initializeIterations() {}
	abstract protected Complex evaluateIteration() ;
	protected void finalizeIterations ( ){}
	public boolean hasConverged()
	{return ComplexUtil.compare(precision,desiredPrecision);}

	public void evaluate() 
	{
		
		noOfIterations = 0;
		initializeIterations();
		while ( noOfIterations++ < maximumIterations )
		{  
			precision = evaluateIteration();
			if ( hasConverged() )
			{break;}
		}
		
		finalizeIterations();
	}
	public Complex relativePrecision( Complex epsilon)
	{
		return relativePrecision( epsilon, this.result.getAbs());
	}
	public Complex relativePrecision( Complex epsilon, double x)
	{
		return (x>Constants.defaultNumericalPrecision? ComplexUtil.div(epsilon,new Complex(x)):epsilon);
	}

}
