package mi.email.activity;

import android.content.Context;
import android.widget.Toast;

import com.cy.DataStructure.UtilArray;
import com.cy.DataStructure.UtilDate;
import com.cy.System.UtilSysInfo;
import com.cy.app.Log;
/**1、call init<br>
 * 2、call sendCrashEmail() or sendEmail()*/
public class UtilMail {
    private static String mServerHost;
    private static String mServerPort;
    private static String mFromAddress;
    private static String mPwd;
    private static String mToAddress;
    private static boolean isInited=false;
    /**@param serverHost eg:smtp.163.com
     * @param serverPort eg:25
     * @param fromAddress same as username*/
    public static void init(String serverHost,String serverPort,String fromAddress,String pwd,String toAddress){
        isInited=true;
        mServerHost=serverHost;
        mServerPort=serverPort;
        mFromAddress=fromAddress;
        mPwd=pwd;
        mToAddress=toAddress;
    }
    /**
     * 发送邮件的方法
     * 
     * @return
     */
    public static void sendEmail(final String title,final String content) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // 这个类主要是设置邮件
                MailSenderInfo mailInfo = new MailSenderInfo();
                if (isInited){
                    mailInfo.setMailServerHost(mServerHost);
                    mailInfo.setMailServerPort(mServerPort);
                    mailInfo.setValidate(true);
                    mailInfo.setUserName(mFromAddress);
                    mailInfo.setPassword(mPwd);// 您的邮箱密码
                    mailInfo.setFromAddress(mFromAddress);
                    mailInfo.setToAddress(mToAddress);
                }else {
                    mailInfo.setMailServerHost("smtp.163.com");
                    mailInfo.setMailServerPort("25");
                    mailInfo.setValidate(true);
                    mailInfo.setUserName("djun101@163.com");
                    mailInfo.setPassword("public123456");// 您的邮箱密码
                    mailInfo.setFromAddress("djun101@163.com");
                    mailInfo.setToAddress("jira_joye@163.com");
                }

                mailInfo.setSubject(title);//设置邮箱标题
                mailInfo.setContent(content);//设置邮箱内容
                
                // 这个类主要来发送邮件
                SimpleMailSender sms = new SimpleMailSender();
                sms.sendTextMail(mailInfo);// 发送文体格式
                // sms.sendHtmlMail(mailInfo);//发送html格式
            }

        }).start();

    }
	/**do log-toast-email
	 * @param context
	 * @param thread
	 * @param ex
	 */
	public static void sendCrashEmail(Context context,Thread thread, Throwable ex){
		String crashMessage="Crashed in " + thread + "\n\n"+ex.getMessage();
		StackTraceElement[] traces= ex.getStackTrace();
		Log.printArray(traces);
		Log.e(crashMessage);
		Toast.makeText(context, "程序异常，请退出重试", Toast.LENGTH_SHORT).show();
		UtilMail.sendEmail("crashlog-"+UtilDate.longToDate(System.currentTimeMillis()),
				UtilSysInfo.getPhoneInfo()+"\n\n"
						+crashMessage+"\n\n"
						+UtilArray.toString(traces));
	}
}
