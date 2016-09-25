package math.util.matrix;

public class QRDecomposeD {
	DoubleMatrix m = null;
	private DoubleMatrix gramSchmidt = null;
	private DoubleMatrix uppertriangle = null;

	public QRDecomposeD(DoubleMatrix m) {
		this.m = m.clone();
		gramSchmidt = GramSchmidtOrthonormalizationD.solve(m);
		uppertriangle = DoubleMatrixUtil.matrixMultiply(DoubleMatrixUtil.transpose(gramSchmidt), m);

	}

	public DoubleMatrix getGramSchmidt() {
		return gramSchmidt;
	}

	public DoubleMatrix getUppertriangle() {
		return uppertriangle;
	}
}
