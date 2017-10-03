# DummyAnimation 예제

### 설명
____________________________________________________

  ![DummyAnimation](https://github.com/Hooooong/DAY10_DummyAnimation/blob/master/image/DummyAnimation.gif)

  - Button 클릭 시, 동적 Button 을 생성하여 Goal 까지 이동시키는 애니메이션


### 소스코드
____________________________________________________

  - actity_main.xml

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <android.support.constraint.ConstraintLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:orientation="vertical"
      tools:context="hooooong.com.dummyanimation.MainActivity"
      android:id="@+id/stage">

      <Button
          android:id="@+id/btnGoal"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="GOAL"
          android:layout_marginRight="8dp"
          app:layout_constraintRight_toRightOf="parent"
          app:layout_constraintTop_toTopOf="parent"
          android:layout_marginTop="8dp" />

      <LinearLayout
          android:layout_width="368dp"
          android:layout_height="117dp"
          android:layout_marginBottom="8dp"
          android:layout_marginLeft="8dp"
          android:layout_marginRight="8dp"
          android:orientation="horizontal"
          app:layout_constraintBottom_toBottomOf="parent"
          app:layout_constraintHorizontal_bias="0.0"
          app:layout_constraintLeft_toLeftOf="parent"
          app:layout_constraintRight_toRightOf="parent">

          <Button
              android:id="@+id/btn1"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_weight="1"
              android:text="Button1" />

          <Button
              android:id="@+id/btn2"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_weight="1"
              android:text="Button2" />

          <Button
              android:id="@+id/btn3"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_weight="1"
              android:text="Button3" />

          <Button
              android:id="@+id/btn4"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_weight="1"
              android:text="Button4" />
      </LinearLayout>
  </android.support.constraint.ConstraintLayout>
  ```

  - MainActivity.java

  ```java
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
  ```
