package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinner extends AppCompatSpinner {

    private PopupWindow popupWindow;
    private ArrayAdapter<String> adapter;
    private OnItemSelectedListener onItemSelectedListener;

    public CustomSpinner(Context context) {
        super(context);
        init(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        try {
            ListView listView = new ListView(context);

            String[] levels = new String[10];
            for (int i = 1; i <= 10; i++) {
                levels[i - 1] = "Level " + i;
            }

            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, levels);
            listView.setAdapter(adapter);

            int widthInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
            int heightInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());

            popupWindow = new PopupWindow(listView, widthInPx, heightInPx);

            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);

            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.black));
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedLevel = adapter.getItem(position);
                if (selectedLevel != null) {
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(selectedLevel);
                        setSelectedItem(selectedLevel.toString());
                    }
                }
                popupWindow.dismiss();
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error initializing CustomSpinner: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public void setSelectedItem(String value) {
        if (adapter != null && adapter.getPosition(value) >= 0) {
            int position = adapter.getPosition(value);
            setSelection(position);
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(value);
            }
        } else {
            Toast.makeText(getContext(), "Item not found in adapter", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String selectedItem);
    }

    @Override
    public boolean performClick() {
        if (!popupWindow.isShowing()) {
            try {
                popupWindow.showAsDropDown(this, 0, 0, Gravity.NO_GRAVITY);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error showing dropdown: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
