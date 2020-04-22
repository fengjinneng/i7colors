package com.company.qcy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.WebNoBottomActivity;

public class AgreementActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private TextView mActivityAgreementTv;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        initView();
    }


    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityAgreementTv = (TextView) findViewById(R.id.activity_agreement_tv);

        name =  getIntent().getStringExtra("name");

        if(StringUtils.equals("yinsizhengce",name)){
            mToolbarTitle.setText("隐私政策");
            mActivityAgreementTv.setText("隐私声明\n" +
                    "尊敬的用户：\n" +
                    "        欢迎访问七彩云电商平台！\n" +
                    "        为了给您提供更加安全的服务，本声明公开七彩云电商平台，及七彩云电商平台现在及将来可能开设的手机客户端、无线端等）的隐私政策。七彩云电商平台承诺将按照本隐私声明收集、使用和披露用户信息，除本声明另有规定外，不会在未经您许可的情况下向第三方或公众披露您的信息。\n" +
                    "在您使用七彩云电商平台提供的各项服务之前，请务必仔细阅读本声明。本隐私声明构成七彩云电商平台规则不可分割的一部分，当您同意注册成为七彩云电商平台用户时，即视为同意本声明的全部内容。\n" +
                    "随着七彩云电商平台经营服务内容的变更或扩大，我们会本着高度谨慎原则对隐私声明进行改进。如果您对本声明有任何问题或建议，欢迎联系七彩云电商平台。\n" +
                    "通过本声明，我们希望您了解如下政策：\n" +
                    " \n" +
                    "一、“用户信息”的范围\n" +
                    "二、“用户信息”的收集、使用和披露\n" +
                    "三、“用户信息”的保护\n" +
                    "四、“用户信息”的使用政策\n" +
                    "五、未成年人的信息政策\n" +
                    " \n" +
                    "一、“用户信息”的范围\n" +
                    "1.账户信息：您注册、变更七彩云电商平台用户账户时，向七彩云电商平台提供的注册信息，包括但不限于用户名、密码、姓名、银行账号、电话号码、地址等信息。\n" +
                    "2.七彩云电商平台自动接收的信息包括：\n" +
                    "(1)您使用七彩云电商平台时，我们将自动接收并记录您的浏览器和计算机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间等数据；\n" +
                    "(2)您下载或使用七彩云电商平台移动客户端软件，或使用移动设备运行七彩云电商平台时，可能会读取与您位置和移动设备相关的信息，包括但不限于设备型号、设备识别码、操作系统、分辨率、电信运营商等。\n" +
                    "3.七彩云电商平台通过其他合法途径取得的用户信息。\n" +
                    "4.需要提醒您，以下信息不属于用户信息：\n" +
                    "(1)您在七彩云电商平台购买商品时的信息，包括但不限于购买时使用的用户名、成交时间、出价、数量、评价等信息；\n" +
                    "(2)您在七彩云电商平台搜索商品时的关键字、时间和频率；\n" +
                    "(3)根据法律法规和规章，需要公开的信息。\n" +
                    " \n" +
                    "二、“用户信息”的收集、使用和披露\n" +
                    "1.为了给您提供更加贴心和个性化的服务，我们可能通过使用用户信息，分析您可能感兴趣的信息并向您提供；或通过系统向您展示个性化的第三方推广信息；或在您同意的情况下，与合作伙伴共享您的信息，帮助您获得最便捷的交易体验；或在您同意的情况下，在平台上公开您的用户信息，帮助您获取交易机会。\n" +
                    "2.七彩云电商平台不会在未经您同意的情况下，向第三方披露任何可能用于识别用户个人身份的信息，但从用户的用户名或其它可披露资料分析得出的资料不受此限。\n" +
                    "3.为了给您提供更加安全的网络环境，七彩云电商平台可能使用用户信息以预防、发现、调查违法或违反与七彩云电商平台或其关联公司协议、政策或规则的行为，以保护您、七彩云电商平台及关联公司的合法权益。\n" +
                    "4.七彩云电商平台承诺披露用户信息的情形仅存在于：\n" +
                    "(1)经您事先同意，向第三方或公众披露；\n" +
                    "(2)根据法律的有关规定，或者行政或司法机关的要求，向第三方或者行政、司法机关披露；\n" +
                    "(3)如您的行为存在违反中国有关法律、法规或者七彩云电商平台规则的情况，需要向第三方披露；\n" +
                    "(4)但为方便您使用七彩云电商平台的服务及七彩云电商平台关联公司或其他组织的服务（以下称“其他服务”），您同意并授权七彩云电商平台将您的用户信息传递给您同时接受其他服务的七彩云电商平台关联公司或其他组织，或从为您提供其他服务的七彩云电商平台关联公司或其他组织获取您的用户信息；\n" +
                    "(5)在七彩云电商平台的交易中，如交易任何一方履行或部分履行了交易义务并提出信息披露请求的，七彩云电商平台有权决定向该用户提供其交易对方的联络方式等必要信息，以促成交易的完成或纠纷的解决；\n" +
                    "(6)其它根据法律、法规或者政策应进行的披露。\n" +
                    " \n" +
                    "三、“用户信息”的保护\n" +
                    "1.您的账户均有安全保护功能，请妥善保管您的账户及密码信息。七彩云电商平台将通过向其它服务器备份、对用户密码进行加密等安全措施确保您的信息不丢失，不被滥用和变造。尽管有前述安全措施，但同时也提请您注意在信息网络上不存在所谓“完善的安全措施”。\n" +
                    "2.除非经过您的同意，七彩云电商平台不允许任何用户、第三方通过七彩云电商平台收集、出售或者无偿传播您的用户信息。任何用户、第三方如从事上述活动，一经发现，七彩云电商平台将停止与其的服务或协议，必要时将采取法律手段保护您和七彩云电商平台的权益。\n" +
                    "3.七彩云电商平台含有其他网站的链接，七彩云电商平台不对链接网站的隐私保护措施负责。当您登陆链接网站时，请提高警惕，注意保护个人隐私。\n" +
                    " \n" +
                    "四、“Cookies”的使用政策\n" +
                    "Cookies是少量的数据，在您未拒绝接受cookies的情况下，cookies将被发送到您的浏览器，并储存在您的计算机硬盘。我们使用cookies储存您访问七彩云电商平台的相关数据，在您访问或再次访问七彩云电商平台时，我们能识别您的身份，并通过分析数据为您提供更便捷更完善的服务。\n" +
                    "您有权选择接受或拒绝接受cookies。您可以通过修改浏览器的设置以拒绝接受cookies，但是我们需要提醒您，如果您拒绝接受cookies，您可能无法使用依赖cookies的七彩云电商平台的部分功能。\n" +
                    " \n" +
                    "五、未成年人的信息政策\n" +
                    "如果您尚未年满18周岁，根据相关法律规定，您不能使用本平台提供的交易服务。因此，请您不要在本平台从事交易行为。\n" +
                    " \n" +
                    "声明人：七彩云电商平台");

        }else if(StringUtils.equals("yonghuxieyi",name)){
            mToolbarTitle.setText("用户服务协议");
            Intent intent = new Intent(this, WebNoBottomActivity.class);
            intent.putExtra("webUrl", "http://mobile.i7colors.com/groupBuyMobile/secret.html");
            intent.putExtra("from", "agreement");
            ActivityUtils.startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
