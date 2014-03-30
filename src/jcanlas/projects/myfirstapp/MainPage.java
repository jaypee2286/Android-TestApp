package jcanlas.projects.myfirstapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainPage extends Activity {

	private EditText textIn;
	private EditText itemName;
	private EditText itemCost;
	private EditText itemName_input;
	private EditText itemCost_input;
	private Button buttonAdd;
	private Button buttonCalculate;
	private LinearLayout container;
	private int numberOfDiners = 0;
	private int currentDiner = 0;
	private int currentItem = 0;
	public int counter = 0;
	private List<Integer> itemCosts = new ArrayList<Integer>();

	final Context context = this;
	private EditText result;
	ViewGroup contentArea;
	private int icon = android.R.drawable.ic_menu_add;
	
	private String tempstring1;
	private String tempstring2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		// attach objects to android UI elements
		itemCost = (EditText) findViewById(R.id.addItems_itemCost);
		itemName = (EditText) findViewById(R.id.addItems_itemName);
		itemCost_input = (EditText) findViewById(R.id.prompt_itemCost);
		itemName_input = (EditText) findViewById(R.id.prompt_itemName);

		buttonAdd = (Button) findViewById(R.id.diner_buttonAddItem);
		buttonCalculate = (Button) findViewById(R.id.diner_buttonCalculate);

		container = (LinearLayout) findViewById(R.id.container);

		// create a Diner object
		Diner guest1 = new Diner();
		
		// when the ADD button is pressed:
		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addItemPrompt();
			}
		});

		
		// when the CALCULATE button is pressed:
		buttonCalculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				calculate();
			}
		});

	}
	
	public void calculate() {
		
	}

	public void addItemPrompt() {

		LayoutInflater popup = LayoutInflater.from(context);
		View promptsView = popup.inflate(R.layout.activity_add_items_prompt,
				null);

		AlertDialog.Builder popup_content = new AlertDialog.Builder(context);

		// set promps.xml to builder
		popup_content.setView(promptsView);

		
		final EditText itemName_input2 = (EditText) promptsView
				.findViewById(R.id.prompt_itemName);
		final EditText itemCost_input2 = (EditText) promptsView
				.findViewById(R.id.prompt_itemCost);

		// set dialog message
		popup_content
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// get user input and set it to result
						// edit text
						//addLine();
						
						
						

						LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
								.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						final View addView = layoutInflater.inflate(R.layout.add_items, null);
						TextView addItems_itemName = (TextView) addView.findViewById(R.id.addItems_itemName);
						TextView addItems_itemCost = (TextView) addView.findViewById(R.id.addItems_itemCost);
						addItems_itemName.setText(itemName_input2.getText());
						addItems_itemCost.setText(itemCost_input2.getText());
						Button buttonRemove = (Button) addView.findViewById(R.id.remove);
						buttonRemove.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								((LinearLayout) addView.getParent()).removeView(addView);
								//container.removeAllViews();
							}
						});
						container.addView(addView);
						
						
						
						
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		
		AlertDialog popup_show = popup_content.create();
		popup_show.show();

	}
	
	public void addLine(){
		
		LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View addView = layoutInflater.inflate(R.layout.add_items, null);
		TextView addItems_itemName = (TextView) addView.findViewById(R.id.addItems_itemName);
		TextView addItems_itemCost = (TextView) addView.findViewById(R.id.addItems_itemCost);
		
		// textOut.setText(Integer.toString(counter));
		counter++;
		// textOut.setText((itemCosts.get(0)).toString());
		Button buttonRemove = (Button) addView.findViewById(R.id.remove);
		//contentArea.add
		buttonRemove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((LinearLayout) addView.getParent()).removeView(addView);
				//container.removeAllViews();
			}
		});
		container.addView(addView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

}
