package me.bunny.kernel._c.support.random;

import java.sql.Timestamp;

public interface JTimestampRandom extends JRandom<Timestamp> {
	@Override
	public Timestamp random() ;
}
