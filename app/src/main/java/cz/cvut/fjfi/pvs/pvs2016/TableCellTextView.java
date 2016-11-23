package cz.cvut.fjfi.pvs.pvs2016;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

public class TableCellTextView extends TextView {

	private static final TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);

	public TableCellTextView(Context context) {
		super(context);
		setLayoutParams(params);
		setBackgroundResource(R.drawable.cell_shape);
		setPadding(16, 0, 0, 0);
	}
}
