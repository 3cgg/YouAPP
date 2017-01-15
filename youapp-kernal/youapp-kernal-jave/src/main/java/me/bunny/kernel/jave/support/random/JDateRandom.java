package me.bunny.kernel.jave.support.random;

import java.util.Date;

public interface JDateRandom extends JRandom<Date> {
	@Override
	public Date random() ;
}
