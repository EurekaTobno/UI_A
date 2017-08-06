/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
package com.example.tobno.helloworld.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tobno.helloworld.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
    private static int[] rImgs = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d};

    public static final int LAST_POSITION = -1;
    private final Context mContext;
    private List<Integer> mData;

    public void add(int position) {
        position = position == LAST_POSITION ? getItemCount() : position;
        mData.add(position, rImgs[position]);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        if (position == LAST_POSITION && getItemCount() > 0)
            position = getItemCount() - 1;

        if (position > LAST_POSITION && position < getItemCount()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        int count = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, count);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView view;

        public SimpleViewHolder(View view) {
            super(view);
            this.view = (ImageView) view.findViewById(R.id.image);
        }
    }

    public SimpleAdapter(Context context, Integer[] data) {
        mContext = context;
        if (data != null)
            mData = new ArrayList<Integer>(Arrays.asList(data));
        else mData = new ArrayList<Integer>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.view.setImageResource(mData.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Position =" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
