package com.KinFourGUtils.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：KingGGG on 15/11/13 15:53
 * 描述：Adapter基类
 */
public abstract class SimpleBaseAdapter<T> extends android.widget.BaseAdapter {
    protected Context context;
    protected List<T> data;

    public SimpleBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= data.size()) {
            return null;
        }

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(getItemResource(), parent, false);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (null == viewHolder) {
            viewHolder = new ViewHolder(convertView);
        }
        return getItemView(position, convertView, viewHolder);
    }

    /**
     * 该方法需要子类实现，需要返回item布局的resource id
     */
    public abstract int getItemResource();

    /**
     * 使用该getItemView方法替换原来的getView方法，需要子类实现
     */
    public abstract View getItemView(int position, View convertView, ViewHolder holder);

    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }


        public <V extends View> V getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (V) v;
        }

        /**
         * 为TextView设置字符串
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId, String text) {
            TextView view = getView(viewId);
            view.setText(text);
            return this;
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param drawableId
         * @return
         */
        public ViewHolder setImageResource(int viewId, int drawableId) {
            ImageView view = getView(viewId);
            view.setImageResource(drawableId);
            return this;
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param bitmap
         * @return
         */
        public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }


    }
}
