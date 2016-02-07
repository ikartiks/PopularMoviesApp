//package udacity.popularmoviesapp.adapters;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import java.util.ArrayList;
//
//import udacity.popularmoviesapp.R;
//import udacity.popularmoviesapp.pojos.VSales;
//
//public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
//
//	ArrayList<VSales> listItems;
//	OnItemClickListener clickListener;
//
//
//	public RecyclerAdapter(ArrayList<VSales> list) {
//		this.listItems = list;
//
//	}
//
//	@Override
//	public int getItemCount() {
//
//		return listItems.size();
//	}
//
//	@Override
//	public void onBindViewHolder(ViewHolder holder, int position) {
//
//        VSales t = listItems.get(position);
//        if(t.getCountOfOccurenes()==-1)
//            holder.name.setText(t.getCustomerName());
//        else
//            holder.name.setText(t.getCustomerName()+" ("+t.getCountOfOccurenes()+")");
//	}
//
//	@Override
//	public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
//
//		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_list, parent, false);
//		ViewHolder vh = new ViewHolder(v);
//		return vh;
//	}
//
//
//	public interface OnItemClickListener {
//		   public void onItemClick(View view, int position);
//	}
//
//	public void setOnItemClickLickListener(OnItemClickListener listener){
//
//		clickListener=listener;
//	}
//
//
//	public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
//
//		TextView name;
//
//		public ViewHolder(View itemView) {
//			super(itemView);
//			name = (TextView) itemView.findViewById(R.id.id);
//
//			itemView.setOnClickListener(this);
//		}
//
//		@SuppressWarnings("deprecation")
//		@Override
//		public void onClick(View v) {
//
//			if(clickListener!=null){
//				clickListener.onItemClick(v, getPosition()); //OnItemClickListener mItemClickListener;
//			}
//		}
//
//	}
//
//}
