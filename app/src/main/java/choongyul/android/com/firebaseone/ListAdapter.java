package choongyul.android.com.firebaseone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by myPC on 2017-03-13.
 */

public class ListAdapter extends BaseAdapter {

    Context context;
    List<Bbs> datas;
    LayoutInflater inflater;

    public ListAdapter(List<Bbs> datas, Context context) {
        this.datas = datas;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) { //한번 화면에 세팅됐던 행은 convertView에 담겨져서 돌아온다
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        // 2. 만들어진 뷰 객체를 통해서 findViewById를 사용한다.
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);

        // 3. inflate 된 뷰의 위젯에 값을 세팅한다.
        Bbs bbs = datas.get(position);
        tvTitle.setText(bbs.title);
        tvContent.setText(bbs.content);

        return convertView;
    }
}
