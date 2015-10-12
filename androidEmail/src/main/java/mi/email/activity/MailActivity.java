package mi.email.activity;

import mi.learn.com.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 一键发送邮件 <br>
 * 使用“smtp.163.com”，请确定你的发送方163邮箱设置中已经开启SMTP。<br>
 * 暂未整理发送附件的方法
 */
public class MailActivity extends Activity {

    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                UtilMail.sendEmail("标题","内容");
            }
        });

    }


}
