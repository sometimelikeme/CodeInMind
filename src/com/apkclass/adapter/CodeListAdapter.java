package com.apkclass.adapter;

import java.util.ArrayList; 

import com.apkclass.database.AnswerDBHelper;
import com.apkclass.study.AnswerBean;
import com.apkclass.study.CodeBean;
import com.apkclass.ui.R;
import com.avos.avoscloud.LogUtil.log;

import android.content.Context;
import android.text.NoCopySpan.Concrete;
import android.view.LayoutInflater;
import android.view.View; 
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CodeListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<CodeBean> codebeanlist;
	private AnswerDBHelper asdb;

	public CodeListAdapter(Context context, AnswerDBHelper dbh) {
		super();
		this.context = context;
		this.asdb = dbh;
	}
	
	public ArrayList<CodeBean> getCodebeanlist() {
		return codebeanlist;
	}

	public void setCodebeanlist(ArrayList<CodeBean> codebeanlist) {
		this.codebeanlist = codebeanlist;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return codebeanlist != null ? codebeanlist.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return codebeanlist != null ? codebeanlist.get(position) : 0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		log.e("position:"+position);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.codesandanswer, null);
		TextView tv_codeid    = (TextView) view.findViewById(R.id.tv_codeid);
		TextView tv_codetitle = (TextView) view.findViewById(R.id.tv_codetitle);
		ListView lv_answers   = (ListView) view.findViewById(R.id.lv_answers);
		
		tv_codeid.setText(codebeanlist.get(position).getId());
		tv_codetitle.setText(codebeanlist.get(position).getTitle());
		lv_answers lvAnswers = new lv_answers(context);
		lv_answers.setAdapter(lvAnswers);
		lvAnswers.setAnswer_list(codebeanlist.get(position).getAnswer_list());
		lvAnswers.notifyDataSetChanged();
		LvHeightUtil.setListViewHeightBasedOnChildren(lv_answers);
		
		lv_answers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position1, long id) {
				// TODO Auto-generated method stub
				log.e("on answerlist item click listener");
				log.e("jiangkun" +codebeanlist.get(position).getAnswer_list().get(position1).toString());
				if(codebeanlist.get(position).getAnswer_list().get(position1).getAnswer_flag() == true){
					//正确的题目放进数据库的right列表中
					Toast.makeText(context, "答案正确", 1).show();
					asdb.insert_id(codebeanlist.get(position).getId(), "right");
				}else {
					//错误题目id
					asdb.insert_id(codebeanlist.get(position).getId(), "wrong");
				}
				
				ArrayList<String> right = asdb.getidlist("right");
				ArrayList<String> wrong = asdb.getidlist("wrong");
				
				for(int i=0; i<right.size(); i++) {
					log.e("right:" + right.get(i));
				}
				
				for(int i=0; i<wrong.size(); i++) {
					log.e("wrong:"+ wrong.get(i));
				}
			}
		});
		
		return view;
	}
	
	class lv_answers extends BaseAdapter {
		
		private Context context;
		private ArrayList<AnswerBean> answer_list;

		public lv_answers(Context context) {
			super();
			this.context = context;
		}

		public ArrayList<AnswerBean> getAnswer_list() {
			return answer_list;
		}

		public void setAnswer_list(ArrayList<AnswerBean> answer_list) {
			this.answer_list = answer_list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return answer_list != null ? answer_list.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return answer_list != null ? answer_list.get(position) : 0;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.answeritem, null);
			TextView tv_answeritem = (TextView) view.findViewById(R.id.tv_answeritem);
			tv_answeritem.setText(answer_list.get(position).getAnswer_content());
			return view;
		}
		
	}
	
	public static class LvHeightUtil {
		public static void setListViewHeightBasedOnChildren(ListView listView) {
			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				return;
			}
			int totalHeight = 0;
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			listView.setLayoutParams(params);
		}

	}

}
