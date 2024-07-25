package com.example.mycuteapplication;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private static Context context;
    public List<MainElement> buttonList = new ArrayList<>();
    private boolean del, drag;
    private MainElement change, nowButton;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView card;
        private TextView text, name, type;
        private LinearLayout background;
        private MainElement element;
        private static final double MIN_SIZE = 45.3, MAX_SIZE = 30.65;

        ViewHolder(View view) {
            super(view);

            card = view.findViewById(R.id.btn);
            text = view.findViewById(R.id.textBtn);
            name = view.findViewById(R.id.nameBtn);
            type = view.findViewById(R.id.typeBtn);
            background = view.findViewById(R.id.backBtn);

            card.setStrokeColor(MainActivity.maincolor);
            card.setStrokeWidth(MainActivity.displayWidth / 140);
            card.setRadius((float) MainActivity.displayWidth / 10);
            text.setTextColor(MainActivity.maincolor);
            name.setTextColor(Color.WHITE);

            double increase_value = 1;
            if (MainActivity.textSize == 40) {
                increase_value = 1.25;
            } else if (MainActivity.textSize == 50) {
                increase_value = 1.5;
            }

            type.setTextSize((float) (MainActivity.displayWidth / (MIN_SIZE / increase_value)));
            name.setTextSize((float) (MainActivity.displayWidth / (MIN_SIZE / increase_value)));
            text.setTextSize((float) (MainActivity.displayWidth / (MAX_SIZE / increase_value)));

            background.setBackgroundColor(MainActivity.maincolor);
        }
    }

    public ButtonAdapter(Context context, List<MainElement> b, boolean del) {
        this.buttonList.clear();
        this.buttonList = b;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.del = del;
    }

    @NonNull
    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view, parent, false);
        view.getLayoutParams().height = (int) (MainActivity.displayWidth / 1.8);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonAdapter.ViewHolder holder, int position) {
        MainElement e = buttonList.get(position);

        if (e.toDelete) {
            holder.text.setBackgroundColor(MainActivity.maincolor);
            holder.text.setTextColor(Color.WHITE);
        }

        if (this.del) {
            holder.card.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MainActivity.nowElement = holder.element;
                    if (!holder.element.toDelete) {
                        holder.element.toDelete = true;
                        holder.text.setBackgroundColor(MainActivity.maincolor);
                        holder.text.setTextColor(Color.WHITE);
                    } else {
                        holder.element.toDelete = false;
                        holder.text.setBackgroundColor(Color.TRANSPARENT);
                        holder.text.setTextColor(MainActivity.maincolor);
                        if (DeletingFiles.select.isChecked()) {
                            DeletingFiles.select.setChecked(false);
                        }
                    }
                }
            });
            holder.card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    drag = !drag;

                    return false;
                }
            });

            holder.card.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View view, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            break;

                        case DragEvent.ACTION_DRAG_ENTERED:
                            change = holder.element;
                            break;

                        case DragEvent.ACTION_DRAG_EXITED:
                            break;

                        case DragEvent.ACTION_DRAG_LOCATION:
                            break;

                        case DragEvent.ACTION_DRAG_ENDED:
                            break;

                        case DragEvent.ACTION_DROP:
                            int i = DeletingFiles.adapter.buttonList.indexOf(nowButton);
                            int j = DeletingFiles.adapter.buttonList.indexOf(change);

                            DeletingFiles.adapter.buttonList.set(i, change);
                            DeletingFiles.adapter.buttonList.set(j, nowButton);

                            MainActivity.mainElementList.set(i, change);
                            MainActivity.mainElementList.set(j, nowButton);

                            drag = !drag;

                            DeletingFiles.adapter.notifyDataSetChanged();

                            try {
                                ReadWriteFiles.writeFile();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

            holder.card.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (drag) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(holder.card);
                            holder.card.startDrag(data, shadowBuilder, holder.card, 0);
                            holder.card.setVisibility(View.VISIBLE);
                            nowButton = holder.element;
                        }
                        return false;
                    }
                    return false;
                }
            });
        } else {
            holder.card.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MainActivity.nowElement = holder.element;
                    MainActivity.old = true;
                    if (MainActivity.nowElement.type.equals(TypesOfFiles.LIST)) {
                        Intent intent2 = new Intent(context, ListEdit.class);
                        context.startActivity(intent2);
                    } else if (MainActivity.nowElement.type.equals(TypesOfFiles.WRITE)) {
                        Intent intent2 = new Intent(context, Reading.class);
                        context.startActivity(intent2);
                    } else {
                        Intent intent2 = new Intent(context, Graph.class);
                        context.startActivity(intent2);
                    }
                }
            });
            holder.card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent2 = new Intent(context, DeletingFiles.class);
                    context.startActivity(intent2);
                    return false;
                }
            });
        }

        StringBuilder text = new StringBuilder();
        if (e.type.equals(TypesOfFiles.LIST)) {
            for (String s : e.checkBoxes) {
                text.append(s).append("; ");
            }
        } else {
            text = new StringBuilder(e.text);
        }

        holder.text.setText(text.toString());
        holder.name.setText(e.name);

        if (e.type.equals(TypesOfFiles.WRITE)) {
            holder.type.setText("з");
        } else if (e.type.equals(TypesOfFiles.LIST)) {
            holder.type.setText("с");
        }


        holder.element = e;
    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }
}
