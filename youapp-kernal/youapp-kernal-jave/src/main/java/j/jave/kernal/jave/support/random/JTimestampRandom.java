package j.jave.kernal.jave.support.random;

import java.sql.Timestamp;

public interface JTimestampRandom extends JRandom<Timestamp> {
	@Override
	public Timestamp random() ;
}
