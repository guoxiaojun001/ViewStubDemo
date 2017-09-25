package pie.test.com.viewstubdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HP on 2017/9/25.
 * 如果实现由
 */

public class ViewStubDemoActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstub_demo_activity);
        if ((((int) (Math.random() * 100)) & 0x01) == 0) {
            // to show text
            // all you have to do is inflate the ViewStub for textview
            ViewStub stub = (ViewStub) findViewById(R.id.viewstub_demo_text);
            stub.inflate();
            TextView text = (TextView) findViewById(R.id.viewstub_demo_textview);
            text.setText("此时显示文本");
        } else {
            // to show image
            // all you have to do is inflate the ViewStub for imageview
            ViewStub stub = (ViewStub) findViewById(R.id.viewstub_demo_image);
            stub.inflate();
            ImageView image = (ImageView) findViewById(R.id.viewstub_demo_imageview);
            image.setImageResource(R.mipmap.ic_launcher);
        }
    }

}
