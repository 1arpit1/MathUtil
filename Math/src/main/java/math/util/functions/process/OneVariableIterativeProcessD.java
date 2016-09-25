package math.util.functions.process;

import math.util.Constants;
import math.util.exceptions.UnperformableActionException;
import math.util.functions.interfaces.OneVariableFunctionD;
import math.util.exceptions.IllegalArgumentException;

public abstract class OneVariableIterativeProcessD {
	/**
	 * Number of iterations performed.
	 */
	private int noOfIterations;
	/**
	 * Maximum allowed number of iterations.
	 */
	private int maximumIterations = 100;
	private double desiredPrecision = 1.0536712127723509E-8;
	/**
	 * Achieved precision.
	 */
	private double precision;
	/**
	 * Best approximation of the zero.
	 */
	protected double result = 0;
	/**
	 * @param x double
	 */
	public void setInitialValue( double x)
	{
		result = x;
	}
	public double getResult( )
	{
		return result;
	}
	/**
	 * Function for which the zero will be found.
	 */
	protected OneVariableFunctionD func;

	/**
	 * Generic constructor.
	 */	
	public OneVariableIterativeProcessD() {}

	public OneVariableIterativeProcessD(int maximumIterations, OneVariableFunctionD func) {
		this.maximumIterations = maximumIterations;
		this.func = func;
	}
	public OneVariableIterativeProcessD(OneVariableFunctionD func){
		this.func=func;
	}
	public int getNoOfIterations() {
		return noOfIterations;
	}
	public void setFunction( OneVariableFunctionD func)
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
	public double getDesiredPrecision( )
	{
		return desiredPrecision;
	}
	public void setDesiredPrecision( double precision )
	{
		if ( precision <= 0 )
		{
			throw new IllegalArgumentException( "Non-positive precision: "+precision);
		}
		desiredPrecision = precision;
	}
	public double getPrecision()
	{
		return precision;
	}
	/**
	 * Initializes internal parameters to start the iterative process.
	 * OVERRIDE THIS IF WE HAVE TO INITIALIZE ITERATIONS WITH SOME
	 * STARTING POINT
	 * @throws UnperformableAction 
	 */
	protected void initializeIterations() {
		throw new UnperformableActionException("not implemented!!");
	}
	/**
	 * @return the estimated precision of the result.
	 * THIS IS METHOD WHICH WILL EVALUATE A ITERATION HAS TO BE IMPLEMENTED.
	 * @throws UnperformableAction 
	 */
	abstract protected double evaluateIteration();
	/**
	 * Perform eventual clean-up operations
	 * (must be implement by subclass when needed).
	 */
	protected void finalizeIterations ( ){}
	/**
	 * Check to see if the result has been attained.
	 * @return boolean
	 */
	public boolean hasConverged()
	{return precision < desiredPrecision;}
	/**
	 * @return double
	 * @param epsilon double
	 */
	public double relativePrecision( double epsilon)
	{
		return relativePrecision( epsilon, Math.abs( this.result));
	}
	public double relativePrecision( double epsilon, double x)
	{
		return (x>Constants.defaultNumericalPrecision? epsilon/x:epsilon);
	}
	/**
	 * Performs the iterative process.
	 * Note: this method does not return anything because Java does not
	 * allow mixing double, int ,or objects
	 */
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
}
