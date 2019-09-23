package cn.avater.roomapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CallBack {

    private Button query;
    private Button query_all;
    private Button insert;
    private EditText query_id;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        query = findViewById(R.id.query);
        query_id = findViewById(R.id.query_id);
        result = findViewById(R.id.result);
        query_all = findViewById(R.id.query_all);
        insert = findViewById(R.id.insert);
        query.setOnClickListener(this);
        query_all.setOnClickListener(this);
        insert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query:
                int id = 0;
                try {
                    id = Integer.parseInt(query_id.getText().toString().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DbManager.getUserById(this, id, this);
                break;
            case R.id.insert:
                DbManager.insert(this, new Users("伟达", "123456"));
//                UsersDataBase.getInstance(this).getUsersDao().insert(new Users("伟达", "123456"));//抛出异常
                break;
            case R.id.query_all:
                DbManager.getAllUsers(this);
                break;
        }
    }

    @Override
    public void onSuccess() {
        Log.e("TAG", "------MainActivity--onSuccess-----");
    }

    @Override
    public void onFailed() {
        Log.e("TAG", "------MainActivity--onFailed-----");
    }
}
