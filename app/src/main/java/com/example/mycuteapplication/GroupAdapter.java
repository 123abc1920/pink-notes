package com.example.mycuteapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<Group> {
    private List<Group> groupList;
    private LayoutInflater inflater;
    private boolean findActivity;

    public GroupAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public GroupAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public GroupAdapter(@NonNull Context context, int resource, @NonNull Group[] objects) {
        super(context, resource, objects);
    }

    public GroupAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Group[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public GroupAdapter(@NonNull Context context, int resource, @NonNull List<Group> objects, boolean findActivity) {
        super(context, resource, objects);

        this.groupList = objects;
        this.inflater = LayoutInflater.from(context);
        this.findActivity = findActivity;
    }

    public GroupAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Group> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.checkbox_layout, parent, false);
        }

        Group group = groupList.get(position);

        NewCheckBox checkBox = view.findViewById(R.id.checkBox);
        checkBox.setText(group.name);

        if (!findActivity) {
            if (group.elementsName.contains(MainActivity.nowElement.name)) {
                checkBox.setChecked(true);
            }
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

        if (checkBox.toDelete) {
            checkBox.setBackgroundColor(MainActivity.maincolor);
            checkBox.setTextColor(Color.WHITE);
        }

        if (!this.findActivity) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        group.elementsName.add(MainActivity.nowElement.name);
                        group.elements.add(MainActivity.nowElement);
                    } else {
                        group.elementsName.remove(MainActivity.nowElement.name);
                        group.elements.remove(MainActivity.nowElement);
                    }
                }
            });
        } else {
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        for (MainElement e : group.elements) {
                            if (!Groups.elementsList.contains(e)) {
                                Groups.elementsList.add(e);
                            }
                        }
                    } else {
                        for (MainElement e : group.elements) {
                            boolean del = true;
                            int i = 0;
                            for (Group g : Groups.groupsList) {
                                if (g.elements.contains(e) && !g.equals(group)) {
                                    NewCheckBox n = (NewCheckBox) Groups.listView.getChildAt(i);
                                    if (n.isChecked()) {
                                        del = false;
                                        break;
                                    }
                                }
                                i++;
                            }
                            if (del) {
                                Groups.elementsList.remove(e);
                            }
                        }
                    }

                    Groups.recycleAdapter.notifyDataSetChanged();
                }
            });

            checkBox.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (checkBox.toDelete) {
                        checkBox.toDelete = false;
                        checkBox.setBackgroundColor(Color.TRANSPARENT);
                        checkBox.setTextColor(MainActivity.maincolor);
                    } else {
                        checkBox.toDelete = true;
                        checkBox.setBackgroundColor(MainActivity.maincolor);
                        checkBox.setTextColor(Color.WHITE);
                    }

                    return true;
                }
            });
        }

        return view;
    }
}
