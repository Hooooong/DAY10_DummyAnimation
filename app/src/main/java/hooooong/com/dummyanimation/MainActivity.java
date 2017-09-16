package hooooong.com.dummyanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ConstraintLayout stage;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btnGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    /**
     * View 초기화
     */
    public void initView(){
        stage = (ConstraintLayout)findViewById(R.id.stage);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btnGoal = (Button)findViewById(R.id.btnGoal);
    }

    /**
     * Listener 초기화
     */
    public void initListener(){
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 클릭된 버튼을 사용하기 위해 시스템에서 념겨받은 뷰를
        // 원래의 버튼으로 캐스팅해둔다.

        // View 변수가 Button 클래스의 인스턴스인지 확인
        if(view instanceof Button){
            Button original = (Button) view;

            // 실제 날아갈 더미를 생성해서 상위 레이아웃에 담은 후에 처리한다.
            final Button btnDummy = new Button(this);

            // 생성된 더미에 클릭한 버튼의 속성값을 적용한다.
            btnDummy.setText(original.getText().toString());
            btnDummy.setWidth(original.getWidth());
            btnDummy.setHeight(original.getHeight());
            btnDummy.setBackgroundColor(Color.BLUE);

            // original.getParent() 는 좌표 속성이 없기 때문에
            // 부모 레이아웃을 가져와서 원래 클래스로 캐스팅
            LinearLayout parent = (LinearLayout) original.getParent();

            // 부모 레이아웃의 위치값과 원래 버튼의 위치값을 더한다.
            btnDummy.setX( original.getX() + parent.getX());
            btnDummy.setY( original.getY() + parent.getY());

            // 더미를 상위 레이아웃에 담는다.
            stage.addView(btnDummy);


            ObjectAnimator aniY = ObjectAnimator.ofFloat(
                    btnDummy, "y", btnGoal.getY()
                    /* y 로 하면 좌표를 y로 이동한다. */
            );
            ObjectAnimator aniX = ObjectAnimator.ofFloat(
                    btnDummy, "x", btnGoal.getX()
                    /* x 로 하면 좌표를 x로 이동한다. */
            );

            ObjectAnimator aniRotate = ObjectAnimator.ofFloat(
                    btnDummy, "rotation", 720F
                    /* x 로 하면 좌표를 x로 이동한다. */
            );

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(aniY, aniX, aniRotate);
            animatorSet.setDuration(5000);

            // 애니메이션의 각종 상태를 확인할 수 있는 리스너를 달아준다.
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    stage.removeView(btnDummy);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            // 애니메이션 시작
            animatorSet.start();
        }
    }
}
