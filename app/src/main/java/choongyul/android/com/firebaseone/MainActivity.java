package choongyul.android.com.firebaseone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button postButton;
    EditText etContent, etTitle;
    FirebaseDatabase database;
    DatabaseReference bbsRef;
    ListAdapter adapter;
    ListView listView;
    List<Bbs> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etContent = (EditText) findViewById(R.id.etContent);
        etTitle = (EditText) findViewById(R.id.etTitle);
        postButton = (Button) findViewById(R.id.button);
        postButton.setOnClickListener(clickListener);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListAdapter(datas, this);
        listView.setAdapter(adapter);


        // 파이어베이스 연결
        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

//        myRef.setValue("Hello, World!");

        bbsRef.addValueEventListener(postListener);
    }

    // 데이터 베이스 변경시 알아채는 리스너
    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.w(TAG,"data count" + dataSnapshot.getChildrenCount());
            datas.clear();
            for( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                String key = snapshot.getKey();
                Bbs bbs = snapshot.getValue(Bbs.class);
                bbs.key = key;

                datas.add(bbs);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
//mPostReference.addValueEventListener(postListener);
    };



    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = etTitle.getText().toString();
            String content = etContent.getText().toString();

            // 1. bbs 레퍼런스(테이블) 의 키를 생성
            String key = bbsRef.push().getKey();
            // 2. 입력될 키, 값 세트(레코드) 를 생성
            Map<String, String> postValues = new HashMap<>();
            postValues.put("title", title);
            postValues.put("content", content);
            // 3. 생성된 레코드를 데이터베이스에 입력
            DatabaseReference keyRef = bbsRef.child(key);

            keyRef.setValue( postValues );

        }
    };
}
