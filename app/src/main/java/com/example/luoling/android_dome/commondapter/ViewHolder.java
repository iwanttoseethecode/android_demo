package com.example.luoling.android_dome.commondapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolder {
	
	private SparseArray<View> ViewSparseArray;
	private int mPosition;
	private View mConvertView;
	private Context context;
	
	private ViewHolder(Context context,ViewGroup parent ,int LayoutId,int position) {
		super();
		this.mPosition = position;
		ViewSparseArray = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(LayoutId,parent,false);
		mConvertView.setTag(this);
	}
	
	public static ViewHolder getInstance(Context context,View convertView,ViewGroup parent,int LayoutId,int position){
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder(context, parent, LayoutId, position);
		}else{
			holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
		}
		return holder;
	}
	
	public View getConvertView()
	{
		return mConvertView;
	}
	
	public int getPosition(){
		return mPosition;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 *
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId)
	{
		View view = ViewSparseArray.get(viewId);
		if (view == null)
		{
			view = mConvertView.findViewById(viewId);
			ViewSparseArray.put(viewId, view);
		}
		return (T) view;
	}
	
}
