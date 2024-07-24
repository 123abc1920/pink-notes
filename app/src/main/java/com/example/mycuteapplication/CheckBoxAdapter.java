package com.example.mycuteapplication;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class CheckBoxAdapter extends ArrayAdapter<String> {
    private CheckBoxAdapter checkBoxAdapter;
    private List<String> checkBoxes;
    private Context context;
    private LayoutInflater inflater;
    private boolean delete, drag;
    private String oldBox, newBox;

    public CheckBoxAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public CheckBoxAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CheckBoxAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    public CheckBoxAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CheckBoxAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, boolean delete) {
        super(context, resource, objects);

        this.checkBoxes = objects;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.delete = delete;
        checkBoxAdapter = this;
    }

    public CheckBoxAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.checkbox_layout, parent, false);
        }

        String checkBoxName = checkBoxes.get(position);
        NewCheckBox checkBox = view.findViewById(R.id.checkBox);

        if (checkBoxName.contains("@")) {
            checkBox.setText(checkBoxName.substring(1));
            checkBox.setChecked(true);
        } else {
            checkBox.setText(checkBoxName);
            checkBox.setChecked(false);
        }

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        MainActivity.maincolor,
                        MainActivity.maincolor
                });

        checkBox.setButtonTintList(colorStateList);
        checkBox.setBackgroundColor(Color.TRANSPARENT);
        checkBox.setTextSize(MainActivity.textSize);
        checkBox.setTextColor(MainActivity.maincolor);

        if (this.delete) {
            if (checkBox.toDelete) {
                checkBox.setBackgroundColor(MainActivity.maincolor);
                checkBox.setTextColor(Color.WHITE);
            }
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    checkBox.setChecked(!checkBox.isChecked());

                    if (!checkBox.toDelete) {
                        checkBox.toDelete = true;
                        checkBox.setBackgroundColor(MainActivity.maincolor);
                        checkBox.setTextColor(Color.WHITE);
                    } else {
                        checkBox.toDelete = false;
                        checkBox.setBackgroundColor(Color.TRANSPARENT);
                        checkBox.setTextColor(MainActivity.maincolor);
                    }
                }
            });

            checkBox.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    drag = !drag;

                    return false;
                }
            });
            checkBox.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View view, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            break;

                        case DragEvent.ACTION_DRAG_ENTERED:
                            if (checkBox.isChecked()) {
                                newBox = "@" + checkBox.getText().toString();
                            } else {
                                newBox = checkBox.getText().toString();
                            }
                            break;

                        case DragEvent.ACTION_DRAG_EXITED:
                            break;

                        case DragEvent.ACTION_DRAG_LOCATION:
                            break;

                        case DragEvent.ACTION_DRAG_ENDED:
                            break;

                        case DragEvent.ACTION_DROP:
                            if (drag) {
                                drag = false;
                                int i = checkBoxes.indexOf(oldBox);
                                int j = checkBoxes.indexOf(newBox);
                                checkBoxes.set(j, oldBox);
                                checkBoxes.set(i, newBox);

                                checkBoxAdapter.notifyDataSetChanged();
                                break;
                            }
                        default:
                            break;
                    }
                    return true;
                }
            });

            checkBox.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (drag) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(checkBox);
                            checkBox.startDrag(data, shadowBuilder, checkBox, 0);
                            checkBox.setVisibility(View.VISIBLE);
                            if (checkBox.isChecked()) {
                                oldBox = "@" + checkBox.getText().toString();
                            } else {
                                oldBox = checkBox.getText().toString();
                            }
                        }
                        return false;
                    }
                    return false;
                }
            });
        } else {
            if (checkBox.toDelete) {
                checkBox.toDelete = false;
            }
            checkBox.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent2 = new Intent(context, DeletingList.class);
                    context.startActivity(intent2);
                    return false;
                }
            });
        }

        return view;
    }
}