package me.bunny.kernel.jave.support.random;

import java.math.BigDecimal;

public interface JBigDecimalRandom extends JRandom<BigDecimal> {
	@Override
	public BigDecimal random();
	
}
