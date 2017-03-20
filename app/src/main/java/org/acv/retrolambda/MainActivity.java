package org.acv.retrolambda;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static String[] url = {
            "http://anhdep.pro/wp-content/uploads/2015/09/anh-nguoi-dep-.12.jpg",//
            "http://hinhnendepnhat.net/wp-content/uploads/2016/09/hinh-nen-girl-dep.jpg",//
            "http://bloganhdep.net/wp-content/uploads/2016/04/anh-nguoi-dep-trung-quoc-.jpg",//
            "http://hoahongmagic.com/wp-content/uploads/2016/01/y-nghia-hoa-hong-hong.jpg",
            "http://hoahongmagic.com/wp-content/uploads/2016/01/y-nghia-hoa-hong-hong.jpg"
    };

    private TextView left, right, option;
    private Button _true, _false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        left = (TextView)findViewById(R.id.left);
        right = (TextView)findViewById(R.id.right);
        option = (TextView)findViewById(R.id.option);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        //TODO lambda OnClickListener
        findViewById(R.id._true).setOnClickListener(v->checkResult(true));
        findViewById(R.id._false).setOnClickListener(v->checkResult(false));

        setData();
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return url.length;
            }

            @Override
            public Object getItem(int position) {
                return url[position];
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView == null){
                    convertView = ((LayoutInflater)parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item, null);
                }

                ImageView img = (ImageView)convertView.findViewById(R.id.img);

                ImageLoader.getInstance().displayImage(getItem(position)+"", img);
                return convertView;
            }
        });

        //TODO lamdbda setOnItemClickListener
        list.setOnItemClickListener(
                (parent, view, position, id) -> {
                    String url = parent.getItemAtPosition(position) + "";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
        );
    }




    public void checkResult(boolean isTrue){
        String result = "You are %s";
        int left = Integer.parseInt(this.left.getText().toString());
        int right = Integer.parseInt(this.right.getText().toString());
        String option = this.option.getText().toString();

        //TODO lamdbda constructor
        ReResult rr =  (_left, _right, _option, choose)->{
            int delta = _left - _right;
            String __option = delta < 0 ? "<" : delta == 0 ? "=" : ">";
            return _option.equals(__option) == choose ? "true":"fail";
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(String.format(result,rr.getResult(left,right,option,isTrue)));
        builder.setPositiveButton("Reset" , (dialog, witch)->{
            setData();
        });

        builder.show();
    }

    private void setData() {
        //TODO lambda constructor
        ReExecuteValue rex = (isNUmber)->{

            if(isNUmber)  {
              return (new Random().nextInt(100) + 1) + "";
            }

            int index = new Random().nextInt(3);

            if(index == 0){
                return "=";
            }else if(index == 1){
                return "<";
            }
            return ">";
        };

        left.setText(rex.getValue(true));
        right.setText(rex.getValue(true));
        option.setText(rex.getValue(false));
    }
}