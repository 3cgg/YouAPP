package j.jave.framework.android.components.demo;

import java.util.Arrays;
import java.util.List;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractBaseActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DemoSpinner extends AbstractBaseActivity {

	@Override
	protected void createView(Bundle savedInstanceState) {
		setContentView(R.layout.demo_spinner);

		Spinner city = (Spinner) super.findViewById(R.id.demo_spinner_load_from_resource);
		city.setPrompt("Prompt");
		ArrayAdapter<? extends CharSequence> cities = ArrayAdapter.createFromResource(this, R.array.degrees,
				android.R.layout.simple_spinner_item);
		city.setAdapter(cities);

		
		city = (Spinner) super.findViewById(R.id.demo_spinner_load_from_program);
		city.setPrompt("Prompt");
		List<String> datas=Arrays.asList(new String[]{"Junior","Senior","Colleage","Bachelor"}); 
		cities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datas);
		cities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(cities);
		
	}

}
