package j.jave.framework.commons.random;

import java.math.BigDecimal;

public interface JBigDecimalRandom extends JRandom<BigDecimal> {
	@Override
	public BigDecimal random();
	
}
