package pacmancom.pacman;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLOutput;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements View.OnTouchListener {
    MediaPlayer mp=null;
    MediaPlayer mp_play=null;
    MediaPlayer mp_ghost=null;
    MediaPlayer mp_fruit=null;
    MediaPlayer mp_coins=null;
    MediaPlayer mp_death_pacman=null;
    MediaPlayer mp_eatghost=null;
    SoundPool soundPool;
    int flujo_coins;
    Timer time;
    TimerTask task;
    Timer time_fruits;
    TimerTask task_fruits;
    int left=0, right=0, down=0, up=0;
    int left2=0, right2=0, down2=0, up2=0;
    int x_finish,y_finish;
    float X=0;
    float Y=0;
    float X_END=0;
    float Y_END=0;
    int time_g=0;
    int score_ghost=0;
    int points=0;
    int copy_points=0;
    int points_generic=0;
    int mode_generic=0;
    //fruits
    int cont_fruits=0;
    int acum_fruit=0;

    boolean band_death=false;
    boolean  btStart_flag;
    //----
    int flag=0;
    int lives=3;
    int ban=0;
    GridLayout Grid;
    LinearLayout LinearName;
    LinearLayout LinearMenu;
    LinearLayout Linear2;
    LinearLayout LinearMain;
    LinearLayout Linear_level;
    FrameLayout Frame_Generic;
    FrameLayout FrameScore;
    FrameLayout Frame_level;
    RelativeLayout LayoutMain;
    FrameLayout TitleMenu;
    Button back_generic;
    Button btPlay;
    Button btExit;
    Button btOk;
    Button btBack;
    Button back_level;
    Button btScore;
    Button btStart;
    Button sonido;
    Button btPause;
    Button btnivel1;
    Button btnivel2;
    Button btnivel3;
    Button btnivel4;
    Button btlevels;
    EditText btText;
    TextView Point;
    ImageView Next;
    ImageView animacion_next;
    TextView TextScore;
    Ghost []ghosts=new Ghost[5];
    Ghost fruit;
    //Coordenadas pacman--
    int i_pacman=0;
    int j_pacman=0;
    int Level=1;
    int Level_generic=0;
    int speed=700;
    int active=1;
    int active1=1;
    boolean type_game;
    boolean flag_finish=false;
    //--------------------
    ImageView pac1;
    ImageView pac2;
    ImageView pac3;
    ImageView pac4;
    ImageView pac5;
    ImageView pac6;
    ImageView fruit1;
    ImageView fruit2;
    ImageView fruit3;
    ImageView fruit4;
    ImageView image_menu;
    ImageView YouWin;
    ImageView YouLose;
    ImageView animacion_win;
    ImageView animacion_lose;

    int finish=0;
    int row=20;
    int column=12;
    int [][]matrix1=new int[row][column];
    int [][]mat_coins=new int[row][column];
    String user_name;
    int sound_band=0;
    AnimationDrawable savingAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //rotacion
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        soundPool=new SoundPool(8,AudioManager.STREAM_MUSIC,0);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);



        image_menu=(ImageView) findViewById(R.id.image_menu);
        animacion_next=(ImageView)findViewById(R.id.animacion_next);
        Frame_Generic=(FrameLayout) findViewById(R.id.Frame_Generic);
        Frame_level=(FrameLayout) findViewById(R.id.Frame_level);
        FrameScore=(FrameLayout) findViewById(R.id.FrameScore);
        TitleMenu=(FrameLayout) findViewById(R.id.TitleMenu);
        LayoutMain=(RelativeLayout) findViewById(R.id.LayoutMain);
        YouWin=(ImageView) findViewById(R.id.YouWin);
        YouLose=(ImageView) findViewById(R.id.YouLose);
        animacion_win=(ImageView)findViewById(R.id.animacion_win);
        animacion_lose=(ImageView)findViewById(R.id.animacion_lose);
        TextScore=(TextView) findViewById(R.id.TextScore);
        btScore = (Button) findViewById(R.id.btScore);
        btStart = (Button) findViewById(R.id.btStart);
        back_level = (Button) findViewById(R.id.back_level);
        btText= (EditText) findViewById(R.id.btText);
        back_generic=(Button)findViewById(R.id.back_generic);
        btnivel1=(Button)findViewById(R.id.btnivel1);
        btnivel2=(Button)findViewById(R.id.btnivel2);
        btnivel3=(Button)findViewById(R.id.btnivel3);
        btnivel4=(Button)findViewById(R.id.btnivel4);
        btlevels=(Button)findViewById(R.id.btlevels);
        LinearName=(LinearLayout) findViewById(R.id.LinearName);
        LinearMenu=(LinearLayout) findViewById(R.id.LinearMenu);
        LinearMain=(LinearLayout) findViewById(R.id.LinearMain);
        Linear_level=(LinearLayout) findViewById(R.id.Linear_level);
        Linear2=(LinearLayout) findViewById(R.id.Linear2);
        btExit = (Button) findViewById(R.id.btExit);
        sonido = (Button) findViewById(R.id.sonido);
        btBack = (Button) findViewById(R.id.btBack);
        btPlay = (Button) findViewById(R.id.btPlay);
        btPause = (Button) findViewById(R.id.btPause);
        Grid=(GridLayout) findViewById(R.id.Grid);
        Point=(TextView) findViewById(R.id.Point);
        Next=(ImageView)findViewById(R.id.Next);
        pac1=(ImageView)findViewById(R.id.pac1);
        pac2=(ImageView)findViewById(R.id.pac2);
        pac3=(ImageView)findViewById(R.id.pac3);
        pac4=(ImageView)findViewById(R.id.pac4);
        pac5=(ImageView)findViewById(R.id.pac5);
        pac6=(ImageView)findViewById(R.id.pac6);
        fruit1=(ImageView)findViewById(R.id.fruit1);
        fruit2=(ImageView)findViewById(R.id.fruit2);
        fruit3=(ImageView)findViewById(R.id.fruit3);
        fruit4=(ImageView)findViewById(R.id.fruit4);
        btOk=(Button)findViewById(R.id.btOk);
        animacion_next.setBackgroundResource(R.drawable.animacion_next);
        savingAnimation=(AnimationDrawable)animacion_next.getBackground();
        savingAnimation.start();

        animacion_win.setBackgroundResource(R.drawable.animacion_win);
        savingAnimation=(AnimationDrawable)animacion_win.getBackground();
        savingAnimation.start();

        animacion_lose.setBackgroundResource(R.drawable.animacion_lose);
        savingAnimation=(AnimationDrawable)animacion_lose.getBackground();
        savingAnimation.start();

        image_menu.setBackgroundResource(R.drawable.animacion_menu);
        savingAnimation = (AnimationDrawable)image_menu.getBackground();
        savingAnimation.start();
        managerOfSound();
        btText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                btStart_flag=false;
                user_name=null;
                CharSequence h;
                h = btText.getText();
                user_name = h.toString();
                if (user_name.equals("") || user_name.equals(" ")) {

                }
                else {
                    btStart_flag=true;
                }
                    btStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if(btStart_flag==true){
                                btStart_flag=false;
                                LinearName.setVisibility(View.INVISIBLE);
                                Frame_Generic.setVisibility(View.VISIBLE);
                                Level = 1;
                                active1 = 1;
                                Game();
                                time = new Timer();
                                time_fruits = new Timer();
                                flag = 0;
                                mp.stop();
                                sound_theme();
                            }
                        }
                    });




                return false;
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearMain.setVisibility(View.INVISIBLE);
                LinearName.setVisibility(View.VISIBLE);
                type_game = true;
            }
        });
        btlevels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearMain.setVisibility(View.INVISIBLE);
                Frame_level.setVisibility(View.VISIBLE);
                type_game=false;
            }
        });
        btnivel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grid.removeAllViews();
                Frame_level.setVisibility(View.INVISIBLE);
                Frame_Generic.setVisibility(View.VISIBLE);
                fruit_shades();
                Level=1;
                active1=1;
                points=0;
                score_ghost=0;
                acum_fruit=0;
                Game();
                time = new Timer();
                time_fruits = new Timer();
                flag = 0;
                mp.stop();
                sound_theme();
            }
        });
        btnivel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grid.removeAllViews();
                Frame_level.setVisibility(View.INVISIBLE);
                Frame_Generic.setVisibility(View.VISIBLE);
                fruit_shades();
                Level=2;
                active1=1;
                points=0;
                score_ghost=0;
                acum_fruit=0;
                Game();
                time = new Timer();
                time_fruits = new Timer();
                flag = 0;
                mp.stop();
                sound_theme();
            }
        });
        btnivel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grid.removeAllViews();
                Frame_level.setVisibility(View.INVISIBLE);
                Frame_Generic.setVisibility(View.VISIBLE);
                fruit_shades();
                Level=3;
                active1=1;
                points=0;
                score_ghost=0;
                acum_fruit=0;
                Game();
                time = new Timer();
                time_fruits = new Timer();
                flag = 0;
                mp.stop();
                sound_theme();
            }
        });
        btnivel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grid.removeAllViews();
                Frame_level.setVisibility(View.INVISIBLE);
                Frame_Generic.setVisibility(View.VISIBLE);
                fruit_shades();
                Level=4;
                active1=1;
                points=0;
                score_ghost=0;
                acum_fruit=0;
                Game();
                time = new Timer();
                time_fruits = new Timer();
                flag = 0;
                mp.stop();
                sound_theme();

            }
        });
        btScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Read_Score();
                LinearMain.setVisibility(View.INVISIBLE);
                FrameScore.setVisibility(View.VISIBLE);
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound_theme();
                animacion_next.setVisibility(View.INVISIBLE);
                btOk.setVisibility(View.INVISIBLE);
                Next.setVisibility(View.INVISIBLE);
                Frame_Generic.setVisibility(View.VISIBLE);
                Game();
                time=new Timer();
                time_fruits=new Timer();
                flag=0;
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearMain.setVisibility(View.VISIBLE);
                FrameScore.setVisibility(View.INVISIBLE);
            }
        });
        back_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearMain.setVisibility(View.VISIBLE);
                Frame_level.setVisibility(View.INVISIBLE);
            }
        });
        sonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active==1) {
                    mp.stop();
                    active = 0;
                    sonido.setBackgroundResource(R.drawable.corneta_no);
                }
                else{
                    active=1;
                    sonido.setBackgroundResource(R.drawable.corneta);
                    managerOfSound();
                }
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit=new Intent(Intent.ACTION_MAIN);
                finish();
                System.exit(0);
            }
        });
        back_generic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finish==1){
                    animacion_win.setVisibility(View.INVISIBLE);
                    YouWin.setVisibility(View.INVISIBLE);
                }
                else{
                    if(finish==2){
                        animacion_lose.setVisibility(View.INVISIBLE);
                        YouLose.setVisibility(View.INVISIBLE);
                    }
                }
                finish=0;
                back_generic.setVisibility(View.INVISIBLE);
                LinearMain.setVisibility(View.VISIBLE);
            }
        });
        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active1==1){
                    if(flag!=0){
                        time_fruits.cancel();
                        task_fruits.cancel();
                        task.cancel();
                        time.cancel();
                        time=null;
                        task=null;
                        time_fruits=null;
                        task_fruits=null;
                        active1=0;
                        btPause.setBackgroundResource(R.drawable.play);
                    }
                }
                else{
                    time = new Timer();
                    time_fruits = new Timer();
                    btPause.setBackgroundResource(R.drawable.pause);
                    starTimer();
                    Timer_fruits();
                    active1=1;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void paint_fruits(){
        if(Level_generic==2){
            fruit1.setBackgroundResource(R.drawable.grapes);
        }
        else{
            if(Level_generic==3){
                fruit2.setBackgroundResource(R.drawable.apple);
            }
            else{
                if(Level_generic==4){
                    fruit3.setBackgroundResource(R.drawable.strawberry);
                }
                else{
                    if(Level_generic==5){
                        fruit4.setBackgroundResource(R.drawable.cherry);
                    }
                }
            }
        }
    }
    //-------------------------SOUNDS---------------------------------------------------------------
    public void play_soundPool(){
     /*   if (mp_coins != null) {
            mp_coins.reset();
            mp_coins.release();
        }
        mp_coins = MediaPlayer.create(this, R.raw.eatcoins);
        if(active==1){
            mp_coins.start();
        }*/
        flujo_coins = soundPool.load(this, R.raw.eatcoins, 0);
        if(active==1){
            soundPool.play(flujo_coins, 1, 1, 0, 0, 0);
        }
    }
    public void play_GhostBlue(){
        ///flujo_blue=sound.load(this,R.raw.pacman_blueghost,1);
        if (mp_ghost != null) {
            mp_ghost.reset();
            mp_ghost.release();
        }
        mp_ghost= MediaPlayer.create(this, R.raw.pacman_blueghost);
        if(active==1){
            mp_ghost.start();
            mp_ghost.setLooping(true);
        }
    }
    public void play_death_pacman(){
        if (mp_death_pacman != null) {
            mp_death_pacman.reset();
            mp_death_pacman.release();
        }
        mp_death_pacman= MediaPlayer.create(this, R.raw.pacman_death);
        if(active==1){
            mp_death_pacman.start();
            //mp_death_pacman.setLooping(true);
        }

    }
    public void play_death_blue(){

        if (mp_eatghost != null) {
            mp_eatghost.reset();
            mp_eatghost.release();
        }
        mp_eatghost= MediaPlayer.create(this, R.raw.pacman_eatghost);
        if(active==1){
            mp_eatghost.start();
            //mp_eatghost.setLooping(true);
        }
    }
    protected void managerOfSound() {
        if (mp != null) {
            mp.reset();
            mp.release();
        }
        mp = MediaPlayer.create(this, R.raw.fondo4);
        if(active==1){
            mp.start();
            mp.setLooping(true);
        }
    }
    protected void sound_theme(){
        if (mp_play != null) {
            mp_play.reset();
            mp_play.release();
        }
        mp_play = MediaPlayer.create(this, R.raw.pacmantheme);
        if(active==1){
            mp_play.start();
            mp_play.setLooping(true);
        }
    }
    public void play_fruit(){
        if (mp_fruit != null) {
            mp_fruit.reset();
            mp_fruit.release();
        }
        mp_fruit = MediaPlayer.create(this, R.raw.pacman_eatfruit);
        if(active==1){
            mp_fruit.start();
            //mp_fruit.setLooping(true);
        }
    }
    //------------------------------TIMER-----------------------------------------------------------
    public void starTimer(){
        task=new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Run 1");
                        if(band_death==true){
                            reset(Level_generic, i_pacman, j_pacman);
                            band_death=false;
                        }
                        if(mode_generic==0){
                            //sound.stop(flujo_blue);
                            if(sound_band==1){
                                mp_ghost.stop();
                                sound_theme();
                            }
                            sound_band=0;

                        }
                        else{
                            if(mode_generic==1){
                                if(sound_band==0){
                                    mp_play.stop();
                                    //soundTheme.stop(flujo_theme);
                                    play_GhostBlue();

                                }
                                sound_band=1;
                            }
                        }
                        if(copy_points<=points_generic+1 && flag_finish==false){

                            if(copy_points==points_generic){
                                int xx=0, yy=0;
                                Random rand = new Random();
                                do {
                                    xx = rand.nextInt(row - 1);
                                    yy = rand.nextInt(column - 1);
                                }
                                while (mat_coins[xx][yy] == -2 || mat_coins[xx][yy] == 1 || mat_coins[xx][yy] == 2 || mat_coins[xx][yy] == 3 || mat_coins[xx][yy] == 4 || mat_coins[xx][yy] == 5 || mat_coins[xx][yy] == 6 || mat_coins[xx][yy] == 7 || mat_coins[xx][yy] == 8 || mat_coins[xx][yy] == 9 || mat_coins[xx][yy] == 10 || mat_coins[xx][yy] == 11 || mat_coins[xx][yy] == 12 || mat_coins[xx][yy] == -1 || mat_coins[xx][yy] == 15 || mat_coins[xx][yy] == 16 || mat_coins[xx][yy] == 17 || mat_coins[xx][yy] == 13);
                                x_finish=xx;
                                y_finish=yy;
                                matrix1[xx][yy]=20;
                                points_generic++;
                            }

                            if(left2==2){
                                if(j_pacman>0) {
                                    if (matrix1[i_pacman][j_pacman - 1] == 0) {
                                        left = 1;
                                        right=0;
                                        up=0;
                                        down=0;
                                        left2=0;
                                    }
                                }
                            }
                            else{
                                if(right2==2){
                                    if(j_pacman<column-1) {
                                        if (matrix1[i_pacman][j_pacman + 1] == 0) {
                                            right=1;
                                            left=0;
                                            up=0;
                                            down=0;
                                            right2=0;
                                        }
                                    }
                                }
                                else{
                                    if(up2==2){
                                        if(i_pacman>0) {
                                            if (matrix1[i_pacman - 1][j_pacman] == 0) {
                                                up=1;
                                                down=0;
                                                left=0;
                                                right=0;
                                                up2=0;
                                            }
                                        }
                                    }
                                    else{
                                        if(down2==2) {
                                            if (i_pacman < row - 1) {
                                                if (matrix1[i_pacman + 1][j_pacman] == 0) {
                                                    down=1;
                                                    up=0;
                                                    right=0;
                                                    left=0;
                                                    down2=0;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if(left==1){
                                if(j_pacman>0){
                                    if (matrix1[i_pacman][j_pacman-1]==0 || matrix1[i_pacman][j_pacman-1]==20 || (matrix1[i_pacman][j_pacman-1]==-3 && (ghosts[0].mode==1 || ghosts[0].mode==2)) || (matrix1[i_pacman][j_pacman-1]==-4 &&(ghosts[1].mode==1 || ghosts[1].mode==2)) || (matrix1[i_pacman][j_pacman-1]==-5 && (ghosts[2].mode==1|| ghosts[2].mode==2)) || (matrix1[i_pacman][j_pacman-1]==-6 && (ghosts[3].mode==1|| ghosts[3].mode==2)) || (matrix1[i_pacman][j_pacman-1]==-7 && (ghosts[4].mode==1|| ghosts[4].mode==2))){
                                        if(mat_coins[i_pacman][j_pacman-1]==-2){
                                            mat_coins[i_pacman][j_pacman-1]=0;
                                            points+=2;
                                            copy_points+=2;
                                            play_soundPool();
                                        }
                                        if(mat_coins[i_pacman][j_pacman-1]==13){
                                            mat_coins[i_pacman][j_pacman-1]=0;
                                            for(int h=0;h<Level_generic;h++) {
                                                ghosts[h].mode=1;
                                            }
                                            Init();
                                            mode_generic=1;
                                            points+=2;
                                            copy_points+=2;

                                        }
                                        if(matrix1[i_pacman][j_pacman-1]==-3){
                                            if(ghosts[0].mode==1 || ghosts[0].mode==2){
                                                Special(0);
                                                score_ghost+=20;
                                            }
                                        }
                                        else{
                                            if(matrix1[i_pacman][j_pacman-1]==-4){
                                                if(ghosts[1].mode==1 || ghosts[1].mode==2){
                                                    Special(1);
                                                    score_ghost+=20;
                                                }
                                            }
                                            else{
                                                if(matrix1[i_pacman][j_pacman-1]==-5){
                                                    if(ghosts[2].mode==1 || ghosts[2].mode==2){
                                                        Special(2);
                                                        score_ghost+=20;
                                                    }
                                                }
                                                else{
                                                    if(matrix1[i_pacman][j_pacman-1]==-6){
                                                        if(ghosts[3].mode==1 || ghosts[3].mode==2){
                                                            Special(3);
                                                            score_ghost+=20;
                                                        }
                                                    }
                                                    else{
                                                        if(matrix1[i_pacman][j_pacman-1]==-7){
                                                            if(ghosts[4].mode==1 || ghosts[4].mode==2){
                                                                Special(4);
                                                                score_ghost+=20;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if(fruit.Matrix[i_pacman][j_pacman-1]==14){
                                            cont_fruits=150;
                                            fruit.Matrix[i_pacman][j_pacman-1]=0;
                                            acum_fruit+=50;
                                            paint_fruits();
                                            play_fruit();
                                        }
                                        matrix1[i_pacman][j_pacman] = 0;
                                        j_pacman--;
                                        matrix1[i_pacman][j_pacman] = -1;

                                    }
                                }
                                else{
                                    if(j_pacman==0){
                                        matrix1[i_pacman][j_pacman] = 0;
                                        j_pacman=column-1;
                                        if(mat_coins[i_pacman][j_pacman]==-2){
                                            mat_coins[i_pacman][j_pacman]=0;
                                            play_soundPool();
                                            points+=2;
                                            copy_points+=2;
                                        }
                                        if(mat_coins[i_pacman][j_pacman]==13){
                                            mat_coins[i_pacman][j_pacman]=0;
                                            for(int h=0;h<Level_generic;h++) {
                                                ghosts[h].mode=1;
                                            }
                                            Init();
                                            mode_generic=1;
                                            points+=2;
                                            copy_points+=2;
                                        }
                                        if(matrix1[i_pacman][j_pacman]==-3){
                                            if(ghosts[0].mode==1 || ghosts[0].mode==2){
                                                Special(0);
                                                score_ghost+=20;
                                            }
                                        }
                                        else{
                                            if(matrix1[i_pacman][j_pacman]==-4){
                                                if(ghosts[1].mode==1 || ghosts[1].mode==2){
                                                    Special(1);
                                                    score_ghost+=20;
                                                }
                                            }
                                            else{
                                                if(matrix1[i_pacman][j_pacman]==-5){
                                                    if(ghosts[2].mode==1 || ghosts[2].mode==2){
                                                        Special(2);
                                                        score_ghost+=20;
                                                    }
                                                }
                                                else{
                                                    if(matrix1[i_pacman][j_pacman]==-6){
                                                        if(ghosts[3].mode==1 || ghosts[3].mode==2){
                                                            Special(3);
                                                            score_ghost+=20;
                                                        }
                                                    }
                                                    else{
                                                        if(matrix1[i_pacman][j_pacman]==-7){
                                                            if(ghosts[4].mode==1 || ghosts[4].mode==2){
                                                                Special(4);
                                                                score_ghost+=20;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if(fruit.Matrix[i_pacman][j_pacman]==14){
                                            cont_fruits=150;
                                            fruit.Matrix[i_pacman][j_pacman]=0;
                                            acum_fruit+=50;
                                            paint_fruits();
                                            play_fruit();
                                        }
                                        matrix1[i_pacman][j_pacman] = -1;
                                    }
                                }
                            }//left
                            else{
                                if(right==1){
                                    if(j_pacman<column-1){
                                        if (matrix1[i_pacman][j_pacman+1]==0 || matrix1[i_pacman][j_pacman+1]==20 || (matrix1[i_pacman][j_pacman+1] == -3 && (ghosts[0].mode==1 || ghosts[0].mode==2)) || (matrix1[i_pacman][j_pacman+1]==-4 && (ghosts[1].mode==1 || ghosts[1].mode==2)) || (matrix1[i_pacman][j_pacman+1]==-5 &&(ghosts[2].mode==1|| ghosts[2].mode==2 )) || (matrix1[i_pacman][j_pacman+1]==-6 && (ghosts[3].mode==1|| ghosts[3].mode==2)) || (matrix1[i_pacman][j_pacman+1]==-7 && (ghosts[4].mode==1|| ghosts[4].mode==2))) {
                                            if(mat_coins[i_pacman][j_pacman+1] == -2){
                                                mat_coins[i_pacman][j_pacman+1]=0;
                                                play_soundPool();
                                                points+=2;
                                                copy_points+=2;
                                            }
                                            if(mat_coins[i_pacman][j_pacman+1]==13){
                                                mat_coins[i_pacman][j_pacman+1]=0;
                                                for(int h=0;h<Level_generic;h++) {
                                                    ghosts[h].mode=1;
                                                }
                                                Init();
                                                mode_generic=1;
                                                points+=2;
                                                copy_points+=2;
                                            }
                                            if(matrix1[i_pacman][j_pacman+1]==-3){
                                                if(ghosts[0].mode==1 || ghosts[0].mode==2){
                                                    Special(0);
                                                    score_ghost+=20;
                                                }
                                            }
                                            else{
                                                if(matrix1[i_pacman][j_pacman+1]==-4){
                                                    if(ghosts[1].mode==1 || ghosts[1].mode==2){
                                                        Special(1);
                                                        score_ghost+=20;
                                                    }
                                                }
                                                else{
                                                    if(matrix1[i_pacman][j_pacman+1]==-5){
                                                        if(ghosts[2].mode==1 || ghosts[2].mode==2){
                                                            Special(2);
                                                            score_ghost+=20;
                                                        }
                                                    }
                                                    else{
                                                        if(matrix1[i_pacman][j_pacman+1]==-6){
                                                            if(ghosts[3].mode==1 || ghosts[3].mode==2){
                                                                Special(3);
                                                                score_ghost+=20;
                                                            }
                                                        }
                                                        else{
                                                            if(matrix1[i_pacman][j_pacman+1]==-7){
                                                                if(ghosts[4].mode==1 || ghosts[4].mode==2){
                                                                    Special(4);
                                                                    score_ghost+=20;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if(fruit.Matrix[i_pacman][j_pacman+1]==14){
                                                cont_fruits=150;
                                                fruit.Matrix[i_pacman][j_pacman+1]=0;
                                                acum_fruit+=50;
                                                paint_fruits();
                                                play_fruit();
                                            }
                                            matrix1[i_pacman][j_pacman] = 0;
                                            j_pacman++;
                                            matrix1[i_pacman][j_pacman] = -1;
                                        }

                                    }
                                    else{
                                        if(j_pacman==column-1){
                                            matrix1[i_pacman][j_pacman] = 0;
                                            j_pacman=0;
                                            if(mat_coins[i_pacman][j_pacman]==-2){
                                                mat_coins[i_pacman][j_pacman]=0;
                                                play_soundPool();
                                                points+=2;
                                                copy_points+=2;
                                            }
                                            if(mat_coins[i_pacman][j_pacman]==13){
                                                mat_coins[i_pacman][j_pacman]=0;
                                                for(int h=0;h<Level_generic;h++) {
                                                    ghosts[h].mode=1;
                                                }
                                                Init();
                                                mode_generic=1;
                                                points+=2;
                                                copy_points+=2;
                                            }
                                            if(matrix1[i_pacman][j_pacman]==-3){
                                                if(ghosts[0].mode==1 || ghosts[0].mode==2){
                                                    Special(0);
                                                    score_ghost+=20;
                                                }
                                            }
                                            else{
                                                if(matrix1[i_pacman][j_pacman]==-4){
                                                    if(ghosts[1].mode==1 || ghosts[1].mode==2){
                                                        Special(1);
                                                        score_ghost+=20;
                                                    }
                                                }
                                                else{
                                                    if(matrix1[i_pacman][j_pacman]==-5){
                                                        if(ghosts[2].mode==1 || ghosts[2].mode==2){
                                                            Special(2);
                                                            score_ghost+=20;
                                                        }
                                                    }
                                                    else{
                                                        if(matrix1[i_pacman][j_pacman]==-6){
                                                            if(ghosts[3].mode==1 || ghosts[3].mode==2){
                                                                Special(3);
                                                                score_ghost+=20;
                                                            }
                                                        }
                                                        else{
                                                            if(matrix1[i_pacman][j_pacman]==-7){
                                                                if(ghosts[4].mode==1 || ghosts[4].mode==2){
                                                                    Special(4);
                                                                    score_ghost+=20;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if(fruit.Matrix[i_pacman][j_pacman]==14){
                                                cont_fruits=150;
                                                fruit.Matrix[i_pacman][j_pacman]=0;
                                                acum_fruit+=50;
                                                paint_fruits();
                                                play_fruit();
                                            }
                                            matrix1[i_pacman][j_pacman] = -1;
                                        }
                                    }
                                }
                                else{
                                    if(up==1){
                                        if(i_pacman>0){
                                            if(matrix1[i_pacman-1][j_pacman]==0 || matrix1[i_pacman-1][j_pacman]==20 ||(matrix1[i_pacman-1][j_pacman]==-3  && (ghosts[0].mode==1 || ghosts[0].mode==2)) || (matrix1[i_pacman-1][j_pacman]==-4 &&(ghosts[1].mode==1 || ghosts[1].mode==2 )) || (matrix1[i_pacman-1][j_pacman]==-5 && (ghosts[2].mode==1 || ghosts[2].mode==2 )) || (matrix1[i_pacman-1][j_pacman]==-6 && (ghosts[3].mode==1 || ghosts[3].mode==2 )) || (matrix1[i_pacman-1][j_pacman]==-7 && (ghosts[4].mode==1 || ghosts[4].mode==2)) ){
                                                if(mat_coins[i_pacman-1][j_pacman]==-2){
                                                    mat_coins[i_pacman-1][j_pacman]=0;
                                                    play_soundPool();
                                                    points+=2;
                                                    copy_points+=2;
                                                }
                                                if(mat_coins[i_pacman-1][j_pacman]==13){
                                                    mat_coins[i_pacman-1][j_pacman]=0;
                                                    for(int h=0;h<Level_generic;h++) {
                                                        ghosts[h].mode=1;
                                                    }
                                                    Init();
                                                    mode_generic=1;
                                                    points+=2;
                                                    copy_points+=2;
                                                }
                                                if(matrix1[i_pacman-1][j_pacman]==-3){
                                                    if(ghosts[0].mode==1 || ghosts[0].mode==2){
                                                        Special(0);
                                                        score_ghost+=20;
                                                    }
                                                }
                                                else{
                                                    if(matrix1[i_pacman-1][j_pacman]==-4){
                                                        if(ghosts[1].mode==1 || ghosts[1].mode==2){
                                                            Special(1);
                                                            score_ghost+=20;
                                                        }
                                                    }
                                                   else{
                                                        if(matrix1[i_pacman-1][j_pacman]==-5){
                                                            if(ghosts[2].mode==1 || ghosts[2].mode==2){
                                                                Special(2);
                                                                score_ghost+=20;
                                                            }
                                                        }
                                                        else{
                                                            if(matrix1[i_pacman-1][j_pacman]==-6){
                                                                if(ghosts[3].mode==1 || ghosts[3].mode==2){
                                                                    Special(3);
                                                                    score_ghost+=20;
                                                                }
                                                            }
                                                            else{
                                                                if(matrix1[i_pacman-1][j_pacman]==-7){
                                                                    if(ghosts[4].mode==1 || ghosts[4].mode==2){
                                                                        Special(4);
                                                                        score_ghost+=20;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if(fruit.Matrix[i_pacman-1][j_pacman]==14){
                                                    cont_fruits=150;
                                                    fruit.Matrix[i_pacman-1][j_pacman]=0;
                                                    acum_fruit+=50;
                                                    paint_fruits();
                                                    play_fruit();
                                                }
                                                matrix1[i_pacman][j_pacman] = 0;
                                                i_pacman--;
                                                matrix1[i_pacman][j_pacman] = -1;
                                            }

                                        }
                                        else{
                                            if(i_pacman==0){
                                                matrix1[i_pacman][j_pacman] = 0;
                                                i_pacman=row-1;
                                                if(mat_coins[i_pacman][j_pacman]==-2){
                                                    mat_coins[i_pacman][j_pacman]=0;
                                                    play_soundPool();
                                                    points+=2;
                                                    copy_points+=2;
                                                }

                                                if(mat_coins[i_pacman][j_pacman]==13){
                                                    mat_coins[i_pacman][j_pacman]=0;
                                                    for(int h=0;h<Level_generic;h++) {
                                                        ghosts[h].mode=1;
                                                    }
                                                    Init();
                                                    mode_generic=1;
                                                    points+=2;
                                                    copy_points+=2;
                                                }
                                                if(matrix1[i_pacman][j_pacman]==-3){
                                                    if(ghosts[0].mode==1 || ghosts[0].mode==2){
                                                        Special(0);
                                                        score_ghost+=20;
                                                    }
                                                }
                                                else{
                                                    if(matrix1[i_pacman][j_pacman]==-4){
                                                        if(ghosts[1].mode==1 || ghosts[1].mode==2){
                                                            Special(1);
                                                            score_ghost+=20;
                                                        }
                                                    }
                                                    else{
                                                        if(matrix1[i_pacman][j_pacman]==-5){
                                                            if(ghosts[2].mode==1 || ghosts[2].mode==2){
                                                                Special(2);
                                                                score_ghost+=20;
                                                            }
                                                        }
                                                        else{
                                                            if(matrix1[i_pacman][j_pacman]==-6){
                                                                if(ghosts[3].mode==1 || ghosts[3].mode==2){
                                                                    Special(3);
                                                                    score_ghost+=20;
                                                                }
                                                            }
                                                            else{
                                                                if(matrix1[i_pacman][j_pacman]==-7){
                                                                    if(ghosts[4].mode==1 || ghosts[4].mode==2){
                                                                        Special(4);
                                                                        score_ghost+=20;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if(fruit.Matrix[i_pacman][j_pacman]==14){
                                                    cont_fruits=150;
                                                    fruit.Matrix[i_pacman][j_pacman]=0;
                                                    acum_fruit+=50;
                                                    paint_fruits();
                                                    play_fruit();
                                                }
                                                matrix1[i_pacman][j_pacman] = -1;
                                            }
                                        }
                                    }
                                    else{
                                        if(down==1){
                                            if(i_pacman<row-1){
                                                if(matrix1[i_pacman+1][j_pacman]==0 || matrix1[i_pacman+1][j_pacman]==20 ||(matrix1[i_pacman+1][j_pacman]==-3 && (ghosts[0].mode==1 || ghosts[0].mode==2)) || (matrix1[i_pacman+1][j_pacman]==-4 && (ghosts[1].mode==1 || ghosts[1].mode==2)) || (matrix1[i_pacman+1][j_pacman]==-5 &&(ghosts[2].mode==1|| ghosts[2].mode==2)) || (matrix1[i_pacman+1][j_pacman]==-6 && (ghosts[3].mode==1|| ghosts[3].mode==2)) || (matrix1[i_pacman+1][j_pacman]==-7 && (ghosts[4].mode==1|| ghosts[4].mode==2))){
                                                    if(mat_coins[i_pacman+1][j_pacman]==-2){
                                                        mat_coins[i_pacman+1][j_pacman]=0;
                                                        play_soundPool();
                                                        points+=2;
                                                        copy_points+=2;
                                                    }

                                                    if(mat_coins[i_pacman+1][j_pacman]==13){
                                                        mat_coins[i_pacman+1][j_pacman]=0;
                                                        for(int h=0;h<Level_generic;h++) {
                                                            ghosts[h].mode=1;
                                                        }
                                                        Init();
                                                        mode_generic=1;
                                                        points+=2;
                                                        copy_points+=2;
                                                    }
                                                    if(matrix1[i_pacman+1][j_pacman]==-3){
                                                        if(ghosts[0].mode==1 || ghosts[0].mode==2){
                                                            Special(0);
                                                            score_ghost+=20;
                                                        }
                                                    }
                                                    else{
                                                        if(matrix1[i_pacman+1][j_pacman]==-4){
                                                            if(ghosts[1].mode==1 || ghosts[1].mode==2){
                                                                Special(1);
                                                                score_ghost+=20;
                                                            }
                                                        }
                                                        else{
                                                            if(matrix1[i_pacman+1][j_pacman]==-5){
                                                                if(ghosts[2].mode==1 || ghosts[2].mode==2){
                                                                    Special(2);
                                                                    score_ghost+=20;
                                                                }
                                                            }
                                                            else{
                                                                if(matrix1[i_pacman+1][j_pacman]==-6){
                                                                    if(ghosts[3].mode==1 || ghosts[3].mode==2){
                                                                        Special(3);
                                                                        score_ghost+=20;
                                                                    }
                                                                }
                                                                else{
                                                                    if(matrix1[i_pacman+1][j_pacman]==-7){
                                                                        if(ghosts[4].mode==1 || ghosts[4].mode==2){
                                                                            Special(4);
                                                                            score_ghost+=20;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if(fruit.Matrix[i_pacman+1][j_pacman]==14){
                                                        cont_fruits=150;
                                                        fruit.Matrix[i_pacman+1][j_pacman]=0;
                                                        acum_fruit+=50;
                                                        paint_fruits();
                                                        play_fruit();
                                                    }
                                                    matrix1[i_pacman][j_pacman] = 0;
                                                    i_pacman++;
                                                    matrix1[i_pacman][j_pacman] = -1;
                                                }

                                            }
                                            else{
                                                if(i_pacman==row-1){
                                                    matrix1[i_pacman][j_pacman] = 0;
                                                    i_pacman=0;
                                                    if(mat_coins[i_pacman][j_pacman]==-2){
                                                        mat_coins[i_pacman][j_pacman]=0;
                                                        play_soundPool();
                                                        points+=2;
                                                        copy_points+=2;
                                                    }

                                                    if(mat_coins[i_pacman][j_pacman]==13){
                                                        mat_coins[i_pacman][j_pacman]=0;
                                                        for(int h=0;h<Level_generic;h++) {
                                                            ghosts[h].mode=1;
                                                        }
                                                        Init();
                                                        mode_generic=1;
                                                        points+=2;
                                                        copy_points+=2;
                                                    }
                                                    if(matrix1[i_pacman][j_pacman]==-3){
                                                        if(ghosts[0].mode==1 || ghosts[0].mode==2){
                                                            Special(0);
                                                            score_ghost+=20;
                                                        }
                                                    }
                                                    else{
                                                        if(matrix1[i_pacman][j_pacman]==-4){
                                                            if(ghosts[1].mode==1 || ghosts[1].mode==2){
                                                                Special(1);
                                                                score_ghost+=20;
                                                            }
                                                        }
                                                        else{
                                                            if(matrix1[i_pacman][j_pacman]==-5){
                                                                if(ghosts[2].mode==1 || ghosts[2].mode==2){
                                                                    Special(2);
                                                                    score_ghost+=20;
                                                                }
                                                            }
                                                            else{
                                                                if(matrix1[i_pacman][j_pacman]==-6){
                                                                    if(ghosts[3].mode==1 || ghosts[3].mode==2){
                                                                        Special(3);
                                                                        score_ghost+=20;
                                                                    }
                                                                }
                                                                else{
                                                                    if(matrix1[i_pacman][j_pacman]==-7){
                                                                        if(ghosts[4].mode==1 || ghosts[4].mode==2){
                                                                            Special(4);
                                                                            score_ghost+=20;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if(fruit.Matrix[i_pacman][j_pacman]==14){
                                                        cont_fruits=150;
                                                        fruit.Matrix[i_pacman][j_pacman]=0;
                                                        acum_fruit+=50;
                                                        paint_fruits();
                                                        play_fruit();
                                                    }
                                                    matrix1[i_pacman][j_pacman] = -1;
                                                }
                                            }

                                        }
                                    }
                                }
                            }

                            if(time_g<=0){
                                for(int i=0;i<Level_generic;i++){
                                    ghosts[i].mode=0;
                                }
                                Init();
                                mode_generic=0;
                            }
                            for (int i=0;i<Level_generic;i++){
                                if(ghosts[i].mode==1 || ghosts[i].mode==2){
                                    ghosts[i].movement=ghosts[i].far_away(ghosts[i].x,ghosts[i].y,i_pacman,j_pacman, row, column, matrix1, ghosts[i].flag_before,matrix1);
                                }
                                else{
                                    ghosts[i].movement=ghosts[i].Calculate(ghosts[i].x,ghosts[i].y,i_pacman,j_pacman, row, column, matrix1, ghosts[i].flag_before, i,Level_generic,ghosts);
                                }
                            //}
                            //for (int i=0;i<Level_generic;i++){
                                if(ghosts[i].time_inicial>=5){
                                    if(mode_generic==1){
                                        time_g--;
                                        if(time_g==15){
                                            for (int j=0;j<Level_generic;j++) {
                                                if(ghosts[j].mode==1){
                                                    ghosts[j].mode = 2;
                                                }
                                            }
                                        }
                                        //speed=200;
                                    }
                                    if((i==0 || i==1) && ghosts[i].ban<2){

                                        matrix1[ghosts[i].x][ghosts[i].y]=11;
                                        ghosts[i].movement=1;
                                        ghosts[i].ban++;
                                    }
                                    else{
                                        matrix1[ghosts[i].x][ghosts[i].y]=0;
                                    }

                                    if(ghosts[i].movement==0){
                                        ghosts[i].flag_before=-1;
                                    }
                                    else{
                                        ghosts[i].flag_before=ghosts[i].movement;
                                    }

                                    if(ghosts[i].movement==1){
                                        ghosts[i].x--;
                                    }
                                    else{
                                        if(ghosts[i].movement==2){
                                            ghosts[i].x++;
                                        }
                                        else{
                                            if(ghosts[i].movement==3){
                                                ghosts[i].y--;
                                            }
                                            else{
                                                if(ghosts[i].movement==4){
                                                    ghosts[i].y++;
                                                }
                                            }
                                        }
                                    }

                                    ghosts[i].movement=0;
                                    if(i==0){
                                        if(matrix1[ghosts[i].x][ghosts[i].y]==-1 && ghosts[0].mode!=1 && ghosts[0].mode!=2){
                                            matrix1[ghosts[i].x][ghosts[i].y]=-3;
                                            band_death=true;
                                        }
                                        else{
                                            matrix1[ghosts[i].x][ghosts[i].y]=-3;
                                        }
                                    }
                                    else{
                                        if(i==1){
                                            if(matrix1[ghosts[i].x][ghosts[i].y]==-1 && ghosts[1].mode!=1 && ghosts[1].mode!=2){
                                                matrix1[ghosts[i].x][ghosts[i].y]=-4;
                                                band_death=true;
                                            }
                                            else{
                                                matrix1[ghosts[i].x][ghosts[i].y]=-4;
                                            }
                                        }
                                        else{
                                            if(i==2){
                                                if(matrix1[ghosts[i].x][ghosts[i].y]==-1 && ghosts[2].mode!=1 && ghosts[2].mode!=2){
                                                    matrix1[ghosts[i].x][ghosts[i].y]=-5;
                                                    band_death=true;
                                                }
                                                else{
                                                    matrix1[ghosts[i].x][ghosts[i].y]=-5;
                                                }
                                            }
                                            else{
                                                if(i==3){
                                                    if(matrix1[ghosts[i].x][ghosts[i].y]==-1 && ghosts[3].mode!=1 && ghosts[3].mode!=2){
                                                        matrix1[ghosts[i].x][ghosts[i].y]=-6;
                                                        band_death=true;
                                                    }
                                                    else{
                                                        matrix1[ghosts[i].x][ghosts[i].y]=-6;
                                                    }
                                                }
                                                else{
                                                    if(i==4){
                                                        if(matrix1[ghosts[i].x][ghosts[i].y]==-1 && ghosts[4].mode!=1 && ghosts[4].mode!=2){
                                                            matrix1[ghosts[i].x][ghosts[i].y]=-7;
                                                            band_death=true;
                                                        }
                                                        else{
                                                            matrix1[ghosts[i].x][ghosts[i].y]=-7;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                                else{
                                    ghosts[i].time_inicial++;
                                }
                            }
                            if(x_finish==i_pacman && y_finish==j_pacman){
                                flag_finish=true;

                            }
                            Point.setText("  " + (points + score_ghost+acum_fruit));
                            Game();
                        }
                        else{
                            time_fruits.cancel();
                            task_fruits.cancel();
                            time.cancel();
                            task.cancel();
                            time=null;
                            task=null;
                            time_fruits=null;
                            task_fruits=null;
                            //Game();
                            if(mode_generic==1){
                                mp_ghost.stop();
                            }
                            lives++;
                            copy_points=0;
                            Grid.removeAllViews();
                            Frame_Generic.setVisibility(View.INVISIBLE);
                            if(type_game==true) {
                                if (Level_generic == 2) {
                                    Level = 2;
                                } else {
                                    if (Level_generic == 3) {
                                        Level = 3;
                                    } else {
                                        if (Level_generic == 4) {
                                            Level = 4;
                                        }
                                        else{
                                            if(Level_generic==5){
                                                Level=4;
                                                Game();
                                            }
                                        }
                                    }
                                }
                                if(lives==2){
                                    pac2.setVisibility(View.VISIBLE);
                                }
                                else{
                                    if(lives==3){
                                        pac3.setVisibility(View.VISIBLE);
                                    }else{
                                        if(lives==4){
                                            pac4.setVisibility(View.VISIBLE);
                                        }else{
                                            if(lives==5){
                                                pac5.setVisibility(View.VISIBLE);
                                            }else{
                                                if(lives==6){
                                                    pac6.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    }
                                }
                                if(Level_generic<5){
                                    Next.setVisibility(View.VISIBLE);
                                    btOk.setVisibility(View.VISIBLE);
                                    animacion_next.setVisibility(View.VISIBLE);
                                }
                                else{
                                    finish=1;
                                    LevelFive();
                                }
                            }
                            else{
                                managerOfSound();
                                LinearMain.setVisibility(View.VISIBLE);
                                Point.setText("  ");
                            }
                        }
                    }
                });
            }
        };
        time.scheduleAtFixedRate(task, 0, 340);
    }
    public void Timer_fruits(){
        task_fruits=new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Run 3");
                        if(Level_generic>2){
                            if(cont_fruits>=50 && cont_fruits<=150){
                                int xx, yy;
                                if(cont_fruits==50) {
                                    Random rand = new Random();
                                    do {
                                        xx = rand.nextInt(row - 1);
                                        yy = rand.nextInt(column - 1);
                                    }
                                    while (mat_coins[xx][yy] == -2 || mat_coins[xx][yy] == 1 || mat_coins[xx][yy] == 2 || mat_coins[xx][yy] == 3 || mat_coins[xx][yy] == 4 || mat_coins[xx][yy] == 5 || mat_coins[xx][yy] == 6 || mat_coins[xx][yy] == 7 || mat_coins[xx][yy] == 8 || mat_coins[xx][yy] == 9 || mat_coins[xx][yy] == 10 || mat_coins[xx][yy] == 11 || mat_coins[xx][yy] == 12 || mat_coins[xx][yy] == -1 || mat_coins[xx][yy] == 15 || mat_coins[xx][yy] == 16 || mat_coins[xx][yy] == 17 || mat_coins[xx][yy] == 13);
                                    fruit.x_fruit=xx;
                                    fruit.y_fruit=yy;
                                    fruit.Matrix[xx][yy]=14;
                                }

                                fruit.movement=fruit.far_away(fruit.x_fruit,fruit.y_fruit,i_pacman,j_pacman,row,column,fruit.Matrix,fruit.flag_before,matrix1);
                                fruit.Matrix[fruit.x_fruit][fruit.y_fruit]=0;


                                if(fruit.movement==0){
                                    fruit.flag_before=-1;
                                }
                                else{
                                    fruit.flag_before=fruit.movement;
                                }

                                if(fruit.movement==1){
                                    fruit.x_fruit--;
                                }
                                if(fruit.movement==2){
                                    fruit.x_fruit++;
                                }
                                if(fruit.movement==3){
                                    fruit.y_fruit--;
                                }
                                if(fruit.movement==4){
                                    fruit.y_fruit++;
                                }
                                fruit.movement=0;
                                fruit.Matrix[fruit.x_fruit][fruit.y_fruit]=14;
                                if(cont_fruits==150){
                                    //cont_fruits=-1;
                                    fruit.Matrix[fruit.x_fruit][fruit.y_fruit]=0;
                                }
                                cont_fruits++;
                            }
                            else{
                                fruit.Matrix[fruit.x_fruit][fruit.y_fruit]=0;
                                cont_fruits++;
                            }
                        }
                        else{
                            if(cont_fruits>=50 && cont_fruits<=150){
                                if(cont_fruits==50){
                                    Random rand = new Random();
                                    int xx=0, yy=0;
                                    do{
                                        xx= rand.nextInt(row-1);
                                        yy= rand.nextInt(column-1);
                                    }while(mat_coins[xx][yy] == -2 || mat_coins[xx][yy] == 1 || mat_coins[xx][yy] == 2 || mat_coins[xx][yy] == 3 || mat_coins[xx][yy] == 4 || mat_coins[xx][yy] == 5 || mat_coins[xx][yy] == 6 || mat_coins[xx][yy] == 7 || mat_coins[xx][yy] == 8 || mat_coins[xx][yy] == 9 || mat_coins[xx][yy] == 10 || mat_coins[xx][yy] == 11 || mat_coins[xx][yy] == 12 || mat_coins[xx][yy] == -1 || mat_coins[xx][yy] == 15 || mat_coins[xx][yy] == 16 || mat_coins[xx][yy] == 17 || mat_coins[xx][yy] == 13);
                                    fruit.x_fruit=xx;
                                    fruit.y_fruit=yy;
                                    fruit.Matrix[xx][yy]=14;
                                }

                                if(cont_fruits==150){
                                    //cont_fruits=-1;
                                    fruit.Matrix[fruit.x_fruit][fruit.y_fruit]=0;
                                }
                                cont_fruits++;
                            }
                            else{
                                fruit.Matrix[fruit.x_fruit][fruit.y_fruit]=0;
                                cont_fruits++;
                            }

                        }
                    }
                });
            }
        };
        time_fruits.scheduleAtFixedRate(task_fruits,0,700);
    }
    //--------------------------------LEVELS--------------------------------------------------------
    public void LevelOne(){
        x_finish=0;
        y_finish=0;
        flag_finish=false;
        band_death=false;
        points=0;
        Linear_level.setBackgroundResource(R.drawable.level1);
        copy_points=0;
        points_generic=208;
        Level_generic=2;//cantidad de fantasmas
        int mat[][]={
                {2,15,15,15,15,15,15,15,15,9,-2,3},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1},
                {1,-2,6,-2,2,15,3,-2,2,15,15,16},
                {1,-2,7,-2,7,13,7,-2,7,-2,-2,1},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,-2,2,1},
                {1,3,-2,2,9,11,11,8,3,-2,1,1},
                {1,5,-2,1,11,11,11,11,1,-2,1,1},
                {1,13,-2,1,11,11,11,11,1,-2,1,1},
                {4,9,-2,4,15,15,15,17,5,-2,4,5},
                {-2,-2,-2,-2,-2,-2,0,7,-2,-2,-2,-2},
                {2,15,9,-2,2,9,-2,-2,-2,6,-2,6},
                {1,-2,-2,-2,7,-2,-2,6,13,1,-2,1},
                {1,-2,6,-2,-2,6,-2,10,15,5,-2,1},
                {10,15,12,9,-2,1,-2,7,-2,-2,-2,1},
                {1,-2,-2,-2,-2,1,-2,-2,-2,6,-2,1},
                {1,-2,2,9,-2,7,-2,6,-2,1,-2,1},
                {1,-2,1,13,-2,-2,-2,1,-2,1,-2,1},
                {1,-2,4,9,-2,6,-2,1,-2,7,-2,1},
                {1,-2,-2,-2,-2,1,-2,1,-2,-2,-2,1},
                {4,15,15,15,15,12,15,12,15,9,-2,5},};

        int mat_g[][]={
                {1,1,1,1,1,1,1,1,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,0,1,1,1,0,1,1,1,1},
                {1,0,1,0,1,0,1,0,1,0,0,1},
                {1,0,0,0,0,0,0,0,0,0,1,1},
                {1,1,0,1,1,11,11,1,1,0,1,1},
                {1,1,0,1,11,11,11,11,1,0,1,1},
                {1,0,0,1,11,11,11,11,1,0,1,1},
                {1,1,0,1,1,1,1,1,1,0,1,1},
                {0,0,0,0,0,0,-1,1,0,0,0,0},
                {1,1,1,0,1,1,0,0,0,1,0,1},
                {1,0,0,0,1,0,0,1,0,1,0,1},
                {1,0,1,0,0,1,0,1,1,1,0,1},
                {1,1,1,1,0,1,0,1,0,0,0,1},
                {1,0,0,0,0,1,0,0,0,1,0,1},
                {1,0,1,1,0,1,0,1,0,1,0,1},
                {1,0,1,0,0,0,0,1,0,1,0,1},
                {1,0,1,1,0,1,0,1,0,1,0,1},
                {1,0,0,0,0,1,0,1,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,0,1},};

        fruit=new Ghost();
        for (int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                matrix1[i][j]=mat_g[i][j];
                mat_coins[i][j]=mat[i][j];
                fruit.Matrix[i][j]=mat_g[i][j];
            }
        }
        for(int i=0;i<5;i++){
            ghosts[i]=new Ghost();
            ghosts[i].ban=0;
            ghosts[i].time_inicial=0;
        }
        ghosts[0].x=6;
        ghosts[0].y=5;
        ghosts[1].x=6;
        ghosts[1].y=6;
        matrix1[ghosts[0].x][ghosts[0].y]=-3;
        matrix1[ghosts[1].x][ghosts[1].y]=-4;
        ghosts[0].flag_before=-1;
        ghosts[1].flag_before=-1;
        i_pacman=9;
        j_pacman=6;
        lives=3;
        cont_fruits=0;
        time_g=70;
        mode_generic=0;
        left=0;
        right=0;
        up=0;
        down=0;
    }
    public void LevelTwo(){
        x_finish=0;
        y_finish=0;
        flag_finish=false;
        band_death=false;
        Linear_level.setBackgroundResource(R.drawable.level2);
        copy_points=0;
        points_generic=216;
        Level_generic=3;
        int mat[][]={
                {2,15,15,15,15,15,15,15,15,9,-2,3},
                {1,-2,-2,13,-2,-2,-2,-2,-2,-2,-2,1},
                {1,-2,8,15,17,3,-2,2,15,3,-2,1},
                {1,-2,-2,-2,4,5,-2,4,15,5,-2,1},
                {4,15,3,-2,-2,-2,-2,-2,-2,-2,-2,7},
                {-2,-2,1,-2,6,11,11,6,-2,6,-2,-2},
                {6,-2,1,-2,1,11,11,1,-2,1,-2,6},
                {1,-2,1,-2,1,11,11,1,-2,1,13,1},
                {1,-2,7,-2,4,15,15,5,-2,7,-2,1},
                {1,-2,-2,-2,-2,-2,0,-2,-2,-2,-2,1},
                {1,-2,6,-2,2,15,3,-2,8,15,15,16},
                {1,-2,7,-2,4,15,5,-2,-2,-2,-2,1},
                {1,13,-2,-2,-2,-2,-2,-2,8,15,15,16},
                {10,15,15,9,-2,8,9,-2,-2,-2,13,1},
                {1,-2,-2,-2,-2,-2,-2,-2,8,15,17,16},
                {7,-2,2,15,3,-2,6,-2,-2,-2,4,5},
                {-2,-2,1,11,1,-2,1,-2,6,-2,-2,-2},
                {6,-2,4,15,5,-2,7,-2,4,15,15,3},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1},
                {4,15,15,15,15,15,15,15,15,9,-2,5},};

        int mat_g[][]={
                {1,1,1,1,1,1,1,1,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,0,1,1,1,0,1},
                {1,0,0,0,1,1,0,1,1,1,0,1},
                {1,1,1,0,0,0,0,0,0,0,0,1},
                {0,0,1,0,1,11,11,1,0,1,0,0},
                {1,0,1,0,1,11,11,1,0,1,0,1},
                {1,0,1,0,1,11,11,1,0,1,0,1},
                {1,0,1,0,1,1,1,1,0,1,0,1},
                {1,0,0,0,0,0,-1,0,0,0,0,1},
                {1,0,1,0,1,1,1,0,1,1,1,1},
                {1,0,1,0,1,1,1,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,1,1,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,1,1,1,1},
                {1,0,1,1,1,0,1,0,0,0,1,1},
                {0,0,1,1,1,0,1,0,1,0,0,0},
                {1,0,1,1,1,0,1,0,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,0,1},};

        fruit=new Ghost();
        for (int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                matrix1[i][j]=mat_g[i][j];
                mat_coins[i][j]=mat[i][j];
                fruit.Matrix[i][j]=mat_g[i][j];
            }
        }
        for(int i=0;i<5;i++){
            ghosts[i]=new Ghost();
            ghosts[i].ban=0;
            ghosts[i].time_inicial=0;
        }
        time_g=70;
        mode_generic=0;
        ghosts[0].x=6;
        ghosts[0].y=5;
        ghosts[1].x=6;
        ghosts[1].y=6;
        ghosts[2].x=4;
        ghosts[2].y=6;
        matrix1[ghosts[0].x][ghosts[0].y]=-3;
        matrix1[ghosts[1].x][ghosts[1].y]=-4;
        matrix1[ghosts[2].x][ghosts[2].y]=-5;
        ghosts[0].flag_before=-1;
        ghosts[1].flag_before=-1;
        ghosts[2].flag_before=-1;
        fruit.flag_before=-1;
        i_pacman=9;
        j_pacman=6;
        cont_fruits=0;
        left=0;
        right=0;
        up=0;
        down=0;
    }
    public void LevelThree() {
        x_finish=0;
        y_finish=0;
        flag_finish=false;
        band_death=false;
        Linear_level.setBackgroundResource(R.drawable.level3);
        copy_points = 0;
        points_generic =224;
        Level_generic = 4;

        int mat[][] = {
                {2,15,15,15,9,-2,8,15,15,15,15,3},
                {1,-2,-2,13,-2,-2,-2,-2,-2,-2,-2,1},
                {1,-2,2,15,3,-2,2,15,15,3,-2,1},
                {1,-2,4,15,5,-2,4,15,15,5,-2,1},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1},
                {1,-2,2,3,-2,2,3,-2,2,3,-2,1},
                {1,-2,4,5,-2,4,5,-2,4,5,13,1},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1},
                {7,-2,2,9,-2,6,-2,8,15,9,-2,7},
                {-2,-2,1,-2,-2,1,-2,-2,-2,-2,-2,-2},
                {6,13,7,-2,8,12,15,9,-2,6,-2,6},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,7,-2,1},
                {1,-2,8,15,9,-2,8,9,-2,-2,-2,1},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,6,-2,1},
                {1,-2,6,-2,6,11,11,6,-2,1,-2,1},
                {1,-2,1,-2,1,11,11,1,-2,1,-2,1},
                {1,-2,1,-2,1,11,11,1,-2,1,13,1},
                {1,-2,7,-2,4,15,15,5,-2,7,-2,1},
                {1,-2,-2,-2,-2,-2,0,-2,-2,-2,-2,1},
                {4,15,15,15,9,-2,8,15,15,15,15,5},};

        int mat_g[][] = {
                {1,1,1,1,1,0,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,0,1,1,1,1,0,1},
                {1,0,1,1,1,0,1,1,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,0,1,0,1,1,1,0,1},
                {0,0,1,0,0,1,0,0,0,0,0,0},
                {1,0,1,0,1,1,1,1,0,1,0,1},
                {1,0,0,0,0,0,0,0,0,1,0,1},
                {1,0,1,1,1,0,1,1,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,1,0,1},
                {1,0,1,0,1,11,11,1,0,1,0,1},
                {1,0,1,0,1,11,11,1,0,1,0,1},
                {1,0,1,0,1,11,11,1,0,1,0,1},
                {1,0,1,0,1,1,1,1,0,1,0,1},
                {1,0,0,0,0,0,-1,0,0,0,0,1},
                {1,1,1,1,1,0,1,1,1,1,1,1},};
        fruit=new Ghost();
        for (int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                matrix1[i][j]=mat_g[i][j];
                mat_coins[i][j]=mat[i][j];
                fruit.Matrix[i][j]=mat_g[i][j];
            }
        }
        for(int i=0;i<5;i++){
            ghosts[i]=new Ghost();
            ghosts[i].ban=0;
            ghosts[i].time_inicial=0;
          //  ghosts[i].In(row,column,mat_g);
        }
        time_g=110;
        mode_generic=0;
        ghosts[0].x=15;
        ghosts[0].y=5;
        ghosts[1].x=15;
        ghosts[1].y=6;
        ghosts[2].x=13;
        ghosts[2].y=4;
        ghosts[3].x=13;
        ghosts[3].y=7;
        i_pacman=18;
        j_pacman=6;
        matrix1[ghosts[0].x][ghosts[0].y]=-3;
        matrix1[ghosts[1].x][ghosts[1].y]=-4;
        matrix1[ghosts[2].x][ghosts[2].y]=-5;
        matrix1[ghosts[3].x][ghosts[3].y]=-6;
        ghosts[0].flag_before=-1;
        ghosts[1].flag_before=-1;
        ghosts[2].flag_before=-1;
        ghosts[3].flag_before=-1;
        fruit.flag_before=-1;
        cont_fruits=0;
        left=0;
        right=0;
        up=0;
        down=0;
    }
    public void LevelFour(){
        x_finish=0;
        y_finish=0;
        flag_finish=false;
        band_death=false;
        Linear_level.setBackgroundResource(R.drawable.level4);
        copy_points = 0;
        points_generic =214;
        Level_generic = 5;
        int mat[][]={
                {2,15,15,15,15,15,15,15,15,15,15,3},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1},
                {1,13,2,15,3,-2,2,15,15,3,-2,1},
                {1,-2,4,15,5,-2,4,15,15,5,-2,1},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1},
                {1,-2,6,-2,8,15,9,-2,8,15,15,16},
                {1,-2,1,-2,-2,-2,-2,-2,-2,-2,-2,1},
                {1,-2 ,7,-2,6,11,11,6,-2,6,-2,1},
                {1,-2,-2,-2,1,11,11,1,-2,1,13,1},
                {1,-2,6,-2,1,11,11,1,-2,1,-2,1},
                {1,-2,1,-2,4,15,15,5,-2,1,-2,1},
                {7,-2,1,-2,-2,-2,0,-2,-2,1,-2,7},
                {-2,-2,4,15,9,-2,8,9,-2,7,-2,-2},
                {6,-2,-2,-2,-2,-2,-2,-2,-2,-2 ,-2,6},
                {1,-2,6,-2,2,3,-2,2,15,9,-2,1},
                {1,-2,1,-2,1,1,-2,1,-2,-2,-2,1},
                {1,-2,1,-2,1,1,-2,1,-2,6,13,1},
                {1,-2,7,-2,4,5,-2,4,15,5,-2,1},
                {1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1},
                {4,15,15,15,15,15,15,15,15,15,15,5},};

        int mat_g[][]={
                {1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,0,1,1,1,1,0,1},
                {1,0,1,1,1,0,1,1,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,0,1,1,1,0,1,1,1,1},
                {1,0,1,0,0,0,0,0,0,0,0,1},
                {1,0,1,0,1,11,11,1,0,1,0,1},
                {1,0,0,0,1,11,11,1,0,1,0,1},
                {1,0,1,0,1,11,11,1,0,1,0,1},
                {1,0,1,0,1,1,1,1,0,1,0,1},
                {1,0,1,0,0,0,-1,0,0,1,0,1},
                {0,0,1,1,1,0,1,1,0,1,0,0},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,0,1,1,0,1,0,0,0,1},
                {1,0,1,0,1,1,0,1,0,1,0,1},
                {1,0,1,0,1,1,0,1,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1},
        };
        fruit=new Ghost();
        for (int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                matrix1[i][j]=mat_g[i][j];
                fruit.Matrix[i][j]=mat_g[i][j];
                mat_coins[i][j]=mat[i][j];
            }
        }
        for(int i=0;i<5;i++){
            ghosts[i]=new Ghost();
            ghosts[i].ban=0;
            ghosts[i].time_inicial=0;
            //ghosts[i].In(row,column,mat_g);
        }
        time_g=110;
        mode_generic=0;
        ghosts[0].x=8;
        ghosts[0].y=5;
        ghosts[1].x=8;
        ghosts[1].y=6;
        ghosts[2].x=6;
        ghosts[2].y=4;
        ghosts[3].x=6;
        ghosts[3].y=6;
        ghosts[4].x=6;
        ghosts[4].y=7;
        i_pacman=11;
        j_pacman=6;
        matrix1[ghosts[0].x][ghosts[0].y]=-3;
        matrix1[ghosts[1].x][ghosts[1].y]=-4;
        matrix1[ghosts[2].x][ghosts[2].y]=-5;
        matrix1[ghosts[3].x][ghosts[3].y]=-6;
        matrix1[ghosts[4].x][ghosts[4].y]=-7;
        ghosts[0].flag_before=-1;
        ghosts[1].flag_before=-1;
        ghosts[2].flag_before=-1;
        ghosts[3].flag_before=-1;
        ghosts[4].flag_before=-1;
        fruit.flag_before=-1;
        cont_fruits=0;
        left=0;
        right=0;
        up=0;
        down=0;
    }
    public void LevelFive(){
        YouWin.setVisibility(View.VISIBLE);
        animacion_win.setVisibility(View.VISIBLE);
        back_generic.setVisibility(View.VISIBLE);
        Saved_Score();
        user_name="123";
        btText.setText("");
        cont_fruits=0;
        acum_fruit=0;
        score_ghost=0;
        points=0;
        Point.setText(" ");
        mp_play.stop();
        if(active==1){
            managerOfSound();
        }
        Frame_Generic.setVisibility(View.INVISIBLE);
        fruit_shades();
        lives=3;
        Level=1;
    }
    //----------------------------------------------------------------------------------------------
    public void reset(int level,int i_ant,int j_ant){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        play_death_pacman();
        if(level==2){
            i_pacman=9;
            j_pacman=6;
            matrix1[ghosts[0].x][ghosts[0].y]=0;
            matrix1[ghosts[1].x][ghosts[1].y]=0;
            ghosts[0].x=6;
            ghosts[0].y=5;
            ghosts[1].x=6;
            ghosts[1].y=6;
            matrix1[ghosts[0].x][ghosts[0].y]=-3;
            matrix1[ghosts[1].x][ghosts[1].y]=-4;
        }
        else{
            if(level==3){
                i_pacman=9;
                j_pacman=6;
                matrix1[ghosts[0].x][ghosts[0].y]=0;
                matrix1[ghosts[1].x][ghosts[1].y]=0;
                matrix1[ghosts[2].x][ghosts[2].y]=0;
                ghosts[0].x=6;
                ghosts[0].y=5;
                ghosts[1].x=6;
                ghosts[1].y=6;
                ghosts[2].x=4;
                ghosts[2].y=6;
                matrix1[ghosts[0].x][ghosts[0].y]=-3;
                matrix1[ghosts[1].x][ghosts[1].y]=-4;
                matrix1[ghosts[2].x][ghosts[2].y]=-5;
            }
            else{
                if(level==4){
                    i_pacman=18;
                    j_pacman=6;
                    matrix1[ghosts[0].x][ghosts[0].y]=0;
                    matrix1[ghosts[1].x][ghosts[1].y]=0;
                    matrix1[ghosts[2].x][ghosts[2].y]=0;
                    matrix1[ghosts[3].x][ghosts[3].y]=0;
                    ghosts[0].x=15;
                    ghosts[0].y=5;
                    ghosts[1].x=15;
                    ghosts[1].y=6;
                    ghosts[2].x=13;
                    ghosts[2].y=4;
                    ghosts[3].x=13;
                    ghosts[3].y=7;
                    matrix1[ghosts[0].x][ghosts[0].y]=-3;
                    matrix1[ghosts[1].x][ghosts[1].y]=-4;
                    matrix1[ghosts[2].x][ghosts[2].y]=-5;
                    matrix1[ghosts[3].x][ghosts[3].y]=-6;
                }
                else{
                    if(level==5){
                        matrix1[ghosts[0].x][ghosts[0].y]=0;
                        matrix1[ghosts[1].x][ghosts[1].y]=0;
                        matrix1[ghosts[2].x][ghosts[2].y]=0;
                        matrix1[ghosts[3].x][ghosts[3].y]=0;
                        matrix1[ghosts[4].x][ghosts[4].y]=0;
                        ghosts[0].x=8;
                        ghosts[0].y=5;
                        ghosts[1].x=8;
                        ghosts[1].y=6;
                        ghosts[2].x=6;
                        ghosts[2].y=4;
                        ghosts[3].x=6;
                        ghosts[3].y=6;
                        ghosts[4].x=6;
                        ghosts[4].y=7;
                        matrix1[ghosts[0].x][ghosts[0].y]=-3;
                        matrix1[ghosts[1].x][ghosts[1].y]=-4;
                        matrix1[ghosts[2].x][ghosts[2].y]=-5;
                        matrix1[ghosts[3].x][ghosts[3].y]=-6;
                        matrix1[ghosts[4].x][ghosts[4].y]=-7;
                        i_pacman=11;
                        j_pacman=6;
                    }
                }
            }
        }
        if(lives<=1){
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            time_fruits.cancel();
            task_fruits.cancel();
            task.cancel();
            time.cancel();
            time=null;
            task=null;
            time_fruits=null;
            task_fruits=null;
            mp_play.stop();
            if(active==1){
                managerOfSound();
            }

            Game();
            Frame_Generic.setVisibility(View.INVISIBLE);
            animacion_lose.setVisibility(View.VISIBLE);
            YouLose.setVisibility(View.VISIBLE);
            back_generic.setVisibility(View.VISIBLE);
            if(type_game==true) {
                Saved_Score();
            }
            //
            user_name="";
            btText.setText("");
            cont_fruits=0;
            acum_fruit=0;
            score_ghost=0;
            points=0;
            Point.setText(" ");
            finish=2;
            fruit_shades();
            lives=3;
        }
        else{
            lives--;
        }
        for(int i=0;i<Level_generic;i++){
            ghosts[i].ban=0;
            ghosts[i].mode=0;
            ghosts[i].time_inicial=0;
        }
        if(lives==5){
            pac6.setVisibility(View.INVISIBLE);
        }
        else{
            if(lives==4){
                pac5.setVisibility(View.INVISIBLE);
            }
            else{
                if(lives==3){
                    pac4.setVisibility(View.INVISIBLE);
                }
                else{
                    if(lives==2){
                        pac3.setVisibility(View.INVISIBLE);
                    }
                    else{
                        if(lives==1){
                            pac2.setVisibility(View.INVISIBLE);
                        }
                        else{
                            if(lives==0){
                                pac1.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            }
        }
        Init();
        mode_generic=0;
        left=0;
        right=0;
        up=0;
        down=0;
        matrix1[i_pacman][j_pacman]=-1;
    }
    public void Special(int position){
        play_death_blue();
        ghosts[position].ban=0;
        ghosts[position].mode=0;
        ghosts[position].time_inicial=0;
        if(Level_generic==2){
            if(position==0){
                matrix1[ghosts[position].x][ghosts[position].y]=0;
                ghosts[0].x=6;
                ghosts[0].y=5;
                matrix1[ghosts[0].x][ghosts[0].y]=-3;
            }
            else{
                if(position==1){
                    matrix1[ghosts[position].x][ghosts[position].y]=0;
                    ghosts[1].x=6;
                    ghosts[1].y=6;
                    matrix1[ghosts[1].x][ghosts[1].y]=-4;
                }
            }
        }
        if(Level_generic==3){
            if(position==0){
                matrix1[ghosts[position].x][ghosts[position].y]=0;
                ghosts[0].x=6;
                ghosts[0].y=5;
                matrix1[ghosts[0].x][ghosts[0].y]=-3;
            }
            else{
                if(position==1){
                    matrix1[ghosts[position].x][ghosts[position].y]=0;
                    ghosts[1].x=6;
                    ghosts[1].y=6;
                    matrix1[ghosts[1].x][ghosts[1].y]=-4;
                }
                else{
                    if(position==2){
                        matrix1[ghosts[2].x][ghosts[2].y]=0;
                        ghosts[2].x=4;
                        ghosts[2].y=6;
                        matrix1[ghosts[2].x][ghosts[2].y]=-5;
                    }
                }
            }
        }
        if(Level_generic==4){
            if(position==0){
                matrix1[ghosts[position].x][ghosts[position].y]=0;
                ghosts[0].x=15;
                ghosts[0].y=5;
                matrix1[ghosts[position].x][ghosts[position].y]=-3;
            }
            else{
                if(position==1){
                    matrix1[ghosts[position].x][ghosts[position].y]=0;
                    ghosts[1].x=15;
                    ghosts[1].y=6;
                    matrix1[ghosts[position].x][ghosts[position].y]=-4;
                }
                else{
                    if(position==2){
                        matrix1[ghosts[position].x][ghosts[position].y]=0;
                        ghosts[2].x=13;
                        ghosts[2].y=4;
                        matrix1[ghosts[position].x][ghosts[position].y]=-5;
                    }
                    else{
                        if(position==3){
                            matrix1[ghosts[position].x][ghosts[position].y]=0;
                            ghosts[3].x=13;
                            ghosts[3].y=7;
                            matrix1[ghosts[position].x][ghosts[position].y]=-6;
                        }
                    }
                }
            }
        }
        if(Level_generic==5){
            if(position==0){
                matrix1[ghosts[position].x][ghosts[position].y]=0;
                ghosts[0].x=8;
                ghosts[0].y=5;
                matrix1[ghosts[position].x][ghosts[position].y]=-3;
            }
            else{
                if(position==1){
                    matrix1[ghosts[position].x][ghosts[position].y]=0;
                    ghosts[1].x=8;
                    ghosts[1].y=6;
                    matrix1[ghosts[position].x][ghosts[position].y]=-4;
                }
                else{
                    if(position==2){
                        matrix1[ghosts[position].x][ghosts[position].y]=0;
                        ghosts[2].x=6;
                        ghosts[2].y=4;
                        matrix1[ghosts[position].x][ghosts[position].y]=-5;
                    }
                    else{
                        if(position==3){
                            matrix1[ghosts[position].x][ghosts[position].y]=0;
                            ghosts[3].x=6;
                            ghosts[3].y=6;
                            matrix1[ghosts[position].x][ghosts[position].y]=-6;
                        }
                        else{
                            if(position==4){
                                matrix1[ghosts[position].x][ghosts[position].y]=0;
                                ghosts[4].x=6;
                                ghosts[4].y=7;
                                matrix1[ghosts[position].x][ghosts[position].y]=-7;
                            }
                        }
                    }
                }
            }
        }
        int j=0;
        for(int i=0;i<Level_generic;i++){
            if(ghosts[i].mode==0){
                j++;
            }
        }
        if(j==Level_generic){
            mode_generic=0;
            Init();
        }

    }
    public void Init(){
        if(Level_generic==2 || Level_generic==3){
            time_g=70;
        }
        if(Level_generic==4 || Level_generic==5){
            time_g=110;
        }
    }
    public void fruit_shades(){
        pac1.setVisibility(View.VISIBLE);
        pac2.setVisibility(View.VISIBLE);
        pac3.setVisibility(View.VISIBLE);
        pac4.setVisibility(View.INVISIBLE);
        pac5.setVisibility(View.INVISIBLE);
        pac6.setVisibility(View.INVISIBLE);
        fruit1.setBackgroundResource(R.drawable.shade_grapes);
        fruit2.setBackgroundResource(R.drawable.shade_apple);
        fruit3.setBackgroundResource(R.drawable.shade_strawberry);
        fruit4.setBackgroundResource(R.drawable.shade_cherry);
    }
    //-------------------------------------DRAW-----------------------------------------------------
    public void Game(){ //Load the principal components of the game
        Grid.removeAllViews();
        int cont=0;
        ArrayList<ImageView> array=new ArrayList<ImageView>();
        ImageView button[];
        button=new ImageView[row*column];

        if(Level==1){
            LevelOne();
            Level=0;
        }
        else{
            if(Level==2){
                LevelTwo();
                Level=0;
            }
            else{
                if(Level==3){
                    LevelThree();
                    Level=0;
                }
                else{
                    if(Level==4){
                        LevelFour();
                        Level=0;
                    }
                }
            }
        }

        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                button[cont]=new ImageView(this);

                if(mat_coins[i][j]==1){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==2){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall2);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall2_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall2_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall2_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==3){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall3);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall3_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall3_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall3_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==4){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall4);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall4_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall4_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall4_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==5){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall5);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall5_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall5_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall5_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==6){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall6);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall6_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall6_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall6_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==7){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall7);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall7_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall7_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall7_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==8){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall8);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall8_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall8_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall8_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==9){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall9);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall9_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall9_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall9_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==10){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall10);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall10_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall10_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall10_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==12){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall11);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall11_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall11_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall11_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==15){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall1);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall1_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall1_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall1_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==16){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall15);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall15_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall15_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall15_4);
                                }
                            }
                        }
                    }
                }
                if(mat_coins[i][j]==17){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.wall16);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.wall16_2);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.wall16_3);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.wall16_4);
                                }
                            }
                        }
                    }
                }


                if(mat_coins[i][j]==-2){
                    button[cont].setBackgroundResource(R.drawable.animacion_coins);
                    savingAnimation = (AnimationDrawable)button[cont].getBackground();
                    savingAnimation.start();
                }
                if(mat_coins[i][j]==13){
                    button[cont].setBackgroundResource(R.drawable.special);//special
                }

                if(fruit.Matrix[i][j]==14){
                    if(Level_generic==2){
                        button[cont].setBackgroundResource(R.drawable.grapes);
                    }
                    else{
                        if(Level_generic==3){
                            button[cont].setBackgroundResource(R.drawable.apple);
                        }
                        else{
                            if(Level_generic==4){
                                button[cont].setBackgroundResource(R.drawable.strawberry);
                            }
                            else{
                                if(Level_generic==5){
                                    button[cont].setBackgroundResource(R.drawable.cherry);
                                }
                            }
                        }
                    }
                }
                if(matrix1[i][j]==20){
                    button[cont].setBackgroundResource(R.drawable.goal_animacion);
                    savingAnimation = (AnimationDrawable)button[cont].getBackground();
                    savingAnimation.start();
                }
                if(matrix1[i][j]==-1){
                        if(up==1){
                            button[cont].setBackgroundResource(R.drawable.animacion);
                            savingAnimation = (AnimationDrawable)button[cont].getBackground();
                            savingAnimation.start();
                        }
                        else{
                            if(down==1){
                                button[cont].setBackgroundResource(R.drawable.animacion1);
                                savingAnimation = (AnimationDrawable)button[cont].getBackground();
                                savingAnimation.start();
                            }
                            else{
                                if(left==1){
                                    button[cont].setBackgroundResource(R.drawable.animacion2);
                                    savingAnimation = (AnimationDrawable)button[cont].getBackground();
                                    savingAnimation.start();
                                }
                                else{
                                    if(right==1){
                                        button[cont].setBackgroundResource(R.drawable.animacion3);
                                        savingAnimation = (AnimationDrawable)button[cont].getBackground();
                                        savingAnimation.start();
                                    }
                                    else{
                                        button[cont].setBackgroundResource(R.drawable.right1);
                                    }
                                }
                            }
                        }


                }

                if(matrix1[i][j]==-3){
                        if(ghosts[0].mode==1){
                            button[cont].setBackgroundResource(R.drawable.animacion_blue);
                            savingAnimation = (AnimationDrawable)button[cont].getBackground();
                            savingAnimation.start();
                        }
                        else{
                            if(ghosts[0].mode==2){
                                button[cont].setBackgroundResource(R.drawable.animacion_danger);
                                savingAnimation = (AnimationDrawable)button[cont].getBackground();
                                savingAnimation.start();
                            }
                            else{
                                button[cont].setBackgroundResource(R.drawable.animacion_g1);
                                 savingAnimation = (AnimationDrawable)button[cont].getBackground();
                                 savingAnimation.start();
                            }
                        }

                }
                if(matrix1[i][j]==-4){
                    if(ghosts[1].mode==1){
                        button[cont].setBackgroundResource(R.drawable.animacion_blue);
                        savingAnimation = (AnimationDrawable)button[cont].getBackground();
                        savingAnimation.start();
                    }
                    else{
                        if(ghosts[1].mode==2){
                            button[cont].setBackgroundResource(R.drawable.animacion_danger);
                            savingAnimation = (AnimationDrawable)button[cont].getBackground();
                            savingAnimation.start();
                        }
                        else {
                            button[cont].setBackgroundResource(R.drawable.animacion_g5);
                            savingAnimation = (AnimationDrawable) button[cont].getBackground();
                            savingAnimation.start();
                        }
                    }

                }
                if(matrix1[i][j]==-5){
                    if(ghosts[2].mode==1){
                        button[cont].setBackgroundResource(R.drawable.animacion_blue);
                        savingAnimation = (AnimationDrawable)button[cont].getBackground();
                        savingAnimation.start();
                    }
                    else{
                        if(ghosts[2].mode==2){
                            button[cont].setBackgroundResource(R.drawable.animacion_danger);
                            savingAnimation = (AnimationDrawable)button[cont].getBackground();
                            savingAnimation.start();
                        }
                        else {
                            button[cont].setBackgroundResource(R.drawable.animacion_g2);
                            savingAnimation = (AnimationDrawable) button[cont].getBackground();
                            savingAnimation.start();
                        }
                    }

                }
                if(matrix1[i][j]==-6){
                    if(ghosts[3].mode==1){
                        button[cont].setBackgroundResource(R.drawable.animacion_blue);
                        savingAnimation = (AnimationDrawable)button[cont].getBackground();
                        savingAnimation.start();
                    }
                    else{
                        if(ghosts[3].mode==2){
                            button[cont].setBackgroundResource(R.drawable.animacion_danger);
                            savingAnimation = (AnimationDrawable)button[cont].getBackground();
                            savingAnimation.start();
                        }
                        else {
                            button[cont].setBackgroundResource(R.drawable.animacion_g3);
                            savingAnimation = (AnimationDrawable) button[cont].getBackground();
                            savingAnimation.start();
                        }
                    }

                }
                if(matrix1[i][j]==-7){
                    if(ghosts[4].mode==1){
                        button[cont].setBackgroundResource(R.drawable.animacion_blue);
                        savingAnimation = (AnimationDrawable)button[cont].getBackground();
                        savingAnimation.start();
                    }
                    else{
                        if(ghosts[4].mode==2){
                            button[cont].setBackgroundResource(R.drawable.animacion_danger);
                            savingAnimation = (AnimationDrawable)button[cont].getBackground();
                            savingAnimation.start();
                        }
                        else {
                            button[cont].setBackgroundResource(R.drawable.animacion_g4);
                            savingAnimation = (AnimationDrawable) button[cont].getBackground();
                            savingAnimation.start();
                        }
                    }
                }
                cont++;
            }
        }
        cont=0;
        for (int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                array.add(button[cont]);
                Grid.addView(array.get(cont));
                cont++;

            }
        }
        Grid.setOnTouchListener(this);
    }
    //-------------------------------------TOUC-----------------------------------------------------
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:{
                X=event.getX();
                Y=event.getY();
                break;
            }
            case MotionEvent.ACTION_UP:{
                X_END=event.getX();
                Y_END=event.getY();
                float resta_x=X-X_END;
                float resta_y=Y-Y_END;
                if(Math.abs(resta_x)>Math.abs(resta_y)){
                    if(resta_x>=5){
                        //LEFT
                        if(j_pacman>0){
                            if(matrix1[i_pacman][j_pacman-1]==0 || matrix1[i_pacman][j_pacman-1]==20 || ((matrix1[i_pacman][j_pacman-1]==-3 || matrix1[i_pacman][j_pacman-1]==-4 || matrix1[i_pacman][j_pacman-1]==-5 || matrix1[i_pacman][j_pacman-1]==-6 || matrix1[i_pacman][j_pacman-1]==-7) && (ghosts[0].mode==1 || ghosts[0].mode==2 || ghosts[1].mode==1 || ghosts[1].mode==2|| ghosts[2].mode==1|| ghosts[2].mode==2|| ghosts[3].mode==1|| ghosts[3].mode==2|| ghosts[4].mode==1|| ghosts[4].mode==2))){
                                left=1;
                                right=0;
                                down=0;
                                up=0;
                                left2=0;
                                right2=0;
                                down2=0;
                                up2=0;
                                if(flag==0) {
                                    starTimer();
                                    Timer_fruits();
                                    flag=1;
                                }
                            }
                            else{
                                if(matrix1[i_pacman][j_pacman-1]==1){
                                    left2=2;
                                    right2=0;
                                    down2=0;
                                    up2=0;
                                }
                            }
                        }

                    }
                    if(resta_x<=-5){
                        //RIGHT
                        if(j_pacman<column-1){
                            if(matrix1[i_pacman][j_pacman+1]==0 || matrix1[i_pacman][j_pacman+1]==20 || ((matrix1[i_pacman][j_pacman+1] == -3 || matrix1[i_pacman][j_pacman+1]==-4 || matrix1[i_pacman][j_pacman+1]==-5 || matrix1[i_pacman][j_pacman+1]==-6 || matrix1[i_pacman][j_pacman+1]==-7) && (ghosts[0].mode==1 || ghosts[0].mode==2 || ghosts[1].mode==1 || ghosts[1].mode==2|| ghosts[2].mode==1|| ghosts[2].mode==2|| ghosts[3].mode==1|| ghosts[3].mode==2|| ghosts[4].mode==1|| ghosts[4].mode==2))){
                                left=0;
                                right=1;
                                down=0;
                                up=0;
                                left2=0;
                                right2=0;
                                down2=0;
                                up2=0;
                                if(flag==0) {
                                    starTimer();
                                    Timer_fruits();
                                    flag=1;
                                }
                            }
                            else{
                                if(matrix1[i_pacman][j_pacman+1]==1){
                                    left2=0;
                                    right2=2;
                                    down2=0;
                                    up2=0;
                                }
                            }
                        }


                    }
                }
                else{
                    if(resta_y>=5){
                        //UP
                        if(i_pacman>0){
                            if(matrix1[i_pacman-1][j_pacman]==0 || matrix1[i_pacman-1][j_pacman]==20 || ((matrix1[i_pacman-1][j_pacman]==-3 || matrix1[i_pacman-1][j_pacman]==-4 || matrix1[i_pacman-1][j_pacman]==-5 || matrix1[i_pacman-1][j_pacman]==-6 || matrix1[i_pacman-1][j_pacman]==-7) && (ghosts[0].mode==1 || ghosts[0].mode==2 || ghosts[1].mode==1 || ghosts[1].mode==2 || ghosts[2].mode==1 || ghosts[2].mode==2 || ghosts[3].mode==1 || ghosts[3].mode==2 || ghosts[4].mode==1 || ghosts[4].mode==2))){
                                left=0;
                                right=0;
                                down=0;
                                up=1;
                                left2=0;
                                right2=0;
                                down2=0;
                                up2=0;
                                if(flag==0) {
                                    starTimer();
                                    Timer_fruits();
                                    flag=1;
                                }
                            }
                            else{
                                if(matrix1[i_pacman-1][j_pacman]==1){
                                    left2=0;
                                    right2=0;
                                    down2=0;
                                    up2=2;
                                }
                            }
                        }

                    }
                    if(resta_y<=-5){
                        //DOWN
                        if(i_pacman<row-1){
                            if(matrix1[i_pacman+1][j_pacman]==0 || matrix1[i_pacman+1][j_pacman]==0 || ((matrix1[i_pacman+1][j_pacman]==-3 || matrix1[i_pacman+1][j_pacman]==-4 || matrix1[i_pacman+1][j_pacman]==-5 || matrix1[i_pacman+1][j_pacman]==-6 || matrix1[i_pacman+1][j_pacman]==-7) && (ghosts[0].mode==1 || ghosts[0].mode==2 || ghosts[1].mode==1 || ghosts[1].mode==2|| ghosts[2].mode==1|| ghosts[2].mode==2|| ghosts[3].mode==1|| ghosts[3].mode==2|| ghosts[4].mode==1|| ghosts[4].mode==2))){
                                left2=0;
                                right2=0;
                                down2=0;
                                up2=0;
                                left=0;
                                right=0;
                                down=1;
                                up=0;
                                if(flag==0) {
                                    starTimer();
                                    Timer_fruits();
                                    flag=1;
                                }
                            }
                            else{
                                if(matrix1[i_pacman+1][j_pacman]==1){
                                    left2=0;
                                    right2=0;
                                    down2=2;
                                    up2=0;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        return true;
    }
    //-------------------------------------FILES----------------------------------------------------
    void Saved_Score(){
        try
        {
            OutputStreamWriter fout= new OutputStreamWriter(openFileOutput("Score.txt", Context.MODE_APPEND));
            String b = Integer.toString(points+score_ghost+acum_fruit);
            if(user_name.equals("")){

            }
            else{
                fout.write(user_name + "-" + b + "\n");
            }

            fout.close();
        }
        catch (Exception ex)
        {
            Toast.makeText(getBaseContext(),"No hay memoria ",Toast.LENGTH_SHORT).show();
        }
    }

    void Read_Score() {
        String []list;
        int []score;
        int lines=0;

        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("Score.txt")));
            String texto;
            while((texto = fin.readLine())!=null) {
                lines++;
            }
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        list=new String[lines];
        score=new int[lines];
        lines=0;

        try {
            BufferedReader fin1 = new BufferedReader(new InputStreamReader(openFileInput("Score.txt")));
            String texto;
            while((texto = fin1.readLine())!=null) {
                StringTokenizer st=new StringTokenizer(texto,"-",true);
                while(st.hasMoreTokens()){
                    list[lines]=st.nextToken();
                    System.out.println(list[lines]);
                    st.nextToken();
                    score[lines]=Integer.parseInt(st.nextToken());
                    System.out.println(score[lines]);
                }
                lines++;
            }
            fin1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Ordering(lines,list,score);
    }

    void Ordering(int lines,String []names,int []score){
        int temp;
        String tem;
        if(lines>1){
            for(int i=0; i<lines; i++){
                for(int j=0 ; j<lines - 1; j++){
                    if (score[j] < score[j+1]){
                        temp = score[j];
                        score[j] = score[j+1];
                        score[j+1] = temp;

                        tem= names[j];
                        names[j]=names[j+1];
                        names[j+1]=tem;
                    }
                }//j
            }//i
        }
        if(lines==1){
            TextScore.setText(names[0]+" "+score[0]+"\n");
        }
        else{
            if(lines==2){
                TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n");
            }
            else{
                if(lines==3){
                    TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n"+names[2]+" "+score[2]+"\n");
                }
                else{
                    if(lines==4){
                        TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n"+names[2]+" "+score[2]+"\n"+names[3]+" "+score[3]+"\n");
                    }
                    else{
                        if(lines==5){
                            TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n"+names[2]+" "+score[2]+"\n"+names[3]+" "+score[3]+"\n"+names[4]+" "+score[4]+"\n");
                        }
                        else{
                            if(lines==6){
                                TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n"+names[2]+" "+score[2]+"\n"+names[3]+" "+score[3]+"\n"+names[4]+" "+score[4]+"\n"+names[5]+" "+score[5]+"\n");
                            }
                            else{
                                if(lines==7){
                                    TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n"+names[2]+" "+score[2]+"\n"+names[3]+" "+score[3]+"\n"+names[4]+" "+score[4]+"\n"+names[5]+" "+score[5]+"\n"+names[6]+" "+score[6]+"\n");
                                }
                                else{
                                    if(lines==8){
                                        TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n"+names[2]+" "+score[2]+"\n"+names[3]+" "+score[3]+"\n"+names[4]+" "+score[4]+"\n"+names[5]+" "+score[5]+"\n"+names[6]+" "+score[6]+"\n"+names[7]+" "+score[7]+"\n");
                                    }
                                    else{
                                        if(lines==9){
                                            TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n"+names[2]+" "+score[2]+"\n"+names[3]+" "+score[3]+"\n"+names[4]+" "+score[4]+"\n"+names[5]+" "+score[5]+"\n"+names[6]+" "+score[6]+"\n"+names[7]+" "+score[7]+"\n"+names[8]+" "+score[8]+"\n");
                                        }
                                        else{
                                            if(lines>=10){
                                                TextScore.setText(names[0]+" "+score[0]+"\n"+names[1]+" "+score[1]+"\n"+names[2]+" "+score[2]+"\n"+names[3]+" "+score[3]+"\n"+names[4]+" "+score[4]+"\n"+names[5]+" "+score[5]+"\n"+names[6]+" "+score[6]+"\n"+names[7]+" "+score[7]+"\n"+names[8]+" "+score[8]+"\n"+names[9]+" "+score[9]+"\n");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    //-------------------------------------THE END--------------------------------------------------
}

//-------------------------------------GHOST, FUITS ATRIBUTES---------------------------------------
class Ghost{
    boolean ba=false;
    int time_inicial=0;
    int x,y;
    int x_fruit, y_fruit;
    int flag_before;
    int mode=0;
    int movement=0;
    int ban=0;

    int [][]Matrix= {
        {2,0,1,1,1,1,1,1,1,1,0,3},
        {1,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,6,0,2,1,3,0,2,1,1,1},
        {1,0,7,0,7,0,7,0,7,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,2,1},
        {1,3,0,2,9,11,11,8,3,0,1,1},
        {1,5,0,1,12,11,11,12,1,0,1,1},
        {1,0,0,1,12,12,12,12,1,0,1,1},
        {1,9,0,4,1,1,1,1,5,0,4,1},
        {0,0,0,0,0,0,0,7,0,0,0,0},
        {1,1,9,0,2,9,0,0,0,6,0,1},
        {1,0,0,0,7,0,0,6,0,1,0,1},
        {1,0,6,0,0,6,0,1,1,5,0,1},
        {1,1,1,9,0,1,0,7,0,0,0,1},
        {1,0,0,0,0,1,0,0,0,6,0,1},
        {1,0,6,9,0,7,0,6,0,1,0,1},
        {1,0,1,0,0,0,0,1,0,1,0,1},
        {1,0,4,9,0,6,0,1,0,8,0,1},
        {1,0,0,0,0,1,0,1,0,0,0,1},
        {4,0,1,1,1,1,1,1,1,1,0,5},};


    public int Calculate(int x_ghost, int y_ghost, int x_pacman, int y_pacman, int row, int column, int mat[][], int flag_b, int yy,int Level_generic,Ghost ghost[]){
        //UP 1
        //DOWN 2
        //LEFT 3
        //RIGHT 4
        int flag=0;
        int result_x=x_ghost-x_pacman;
        int result_y=y_ghost-y_pacman;
        if(y_ghost==y_pacman){
            if(x_ghost>0){
                if(result_x>0 && (mat[x_ghost-1][y_ghost]==0 || mat[x_ghost-1][y_ghost]==-1) && flag_b!=2){
                    flag= 1;
                }
                else{
                    if(x_ghost<row-1){
                        if(result_x<=0 && (mat[x_ghost+1][y_ghost]==0 || mat[x_ghost+1][y_ghost]==-1) && flag_b!=1) {
                            flag = 2;
                        }
                    }
                }
            }
            else{
                if(x_ghost<row-1){
                    if(result_x<=0 && (mat[x_ghost+1][y_ghost]==0 || mat[x_ghost+1][y_ghost]==-1) && flag_b!=1) {
                        flag = 2;
                    }
                }
            }
        }
        if(x_ghost==x_pacman){
            if(y_ghost<column-1){
                if(result_y<0 && (mat[x_ghost][y_ghost+1]==0 || mat[x_ghost][y_ghost+1]==-1) && flag_b!=3){
                    flag= 4;
                }
                else{
                    if(y_ghost>0){
                        if(result_y>=0 && (mat[x_ghost][y_ghost-1]==0 || mat[x_ghost][y_ghost-1]==-1) && flag_b!=4){
                            flag=3;
                        }
                    }
                }
            }
            else{
                if(y_ghost>0){
                    if(result_y>=0 && (mat[x_ghost][y_ghost-1]==0 || mat[x_ghost][y_ghost-1]==-1) && flag_b!=4){
                        flag=3;
                    }
                }
            }
        }

        if((y_ghost!=y_pacman || x_ghost!=x_pacman) && flag==0){
            if(Math.abs(result_y)<Math.abs(result_x)){
                if(y_ghost<column-1){
                    if(result_y<0 && (mat[x_ghost][y_ghost+1]==0 || mat[x_ghost][y_ghost+1]==-1) && flag_b!=3){
                        flag= 4;
                    }
                    else{
                        if(y_ghost>0){
                            if(result_y>=0 && (mat[x_ghost][y_ghost-1]==0 || mat[x_ghost][y_ghost-1]==-1) && flag_b!=4){
                                flag=3;
                            }
                        }
                    }
                }
                else{
                    if(y_ghost>0){
                        if(result_y>=0 && (mat[x_ghost][y_ghost-1]==0 || mat[x_ghost][y_ghost-1]==-1) && flag_b!=4){
                            flag=3;
                        }
                    }
                }
            }
            else{
                if(Math.abs(result_y)>=Math.abs(result_x)){
                    if(x_ghost>0){
                        if(result_x>0 && (mat[x_ghost-1][y_ghost]==0 || mat[x_ghost-1][y_ghost]==-1) && flag_b!=2){
                            flag= 1;
                        }
                        else{
                            if(x_ghost<row-1){
                                if(result_x<=0 && (mat[x_ghost+1][y_ghost]==0 || mat[x_ghost+1][y_ghost]==-1) && flag_b!=1) {
                                    flag = 2;
                                }
                            }
                        }
                    }
                    else{
                        if(x_ghost<row-1){
                            if(result_x<=0 && (mat[x_ghost+1][y_ghost]==0 || mat[x_ghost+1][y_ghost]==-1) && flag_b!=1) {
                                flag = 2;
                            }
                        }
                    }
                }
            }
        }
        if(x_ghost<x_pacman){
            //pacman abajo
            if(x_ghost<row-1){
                if((mat[x_ghost+1][y_ghost]==0 || mat[x_ghost+1][y_ghost]==-1) && flag_b!=1) {
                    flag = 2;
                }
            }
        }
        else{
            if(x_ghost>x_pacman){
                //pacman arriba
                if(x_ghost>0){
                    if((mat[x_ghost-1][y_ghost]==0 || mat[x_ghost-1][y_ghost]==-1) && flag_b!=2){
                        flag= 1;
                    }
                }
            }
        }

        if(flag==0){
            if(x_ghost<row-1){
                if((mat[x_ghost+1][y_ghost]==0 || mat[x_ghost+1][y_ghost]==-1) && flag_b!=1) {
                    flag = 2;
                }
            }
            if(x_ghost>0){
                if((mat[x_ghost-1][y_ghost]==0 || mat[x_ghost-1][y_ghost]==-1) && flag_b!=2){
                    flag=1;
                }
            }
            if(y_ghost>0){
                if((mat[x_ghost][y_ghost-1]==0 || mat[x_ghost][y_ghost-1]==-1) && flag_b!=4){
                    flag=3;
                }
            }

            if(y_ghost<column-1){
                if((mat[x_ghost][y_ghost+1]==0 || mat[x_ghost][y_ghost+1]==-1) && flag_b!=3){
                    flag=4;
                }
            }
        }
        return flag;
    }

    public int far_away(int x_ghost, int y_ghost, int x_pacman, int y_pacman, int row, int column, int mat[][], int flag_b,int mat_p[][]){
        //UP 1
        //DOWN 2
        //LEFT 3
        //RIGHT 4
        int flag=0;
        int result_x=x_ghost-x_pacman;
        int result_y=y_ghost-y_pacman;
        if(y_ghost==y_pacman){
            if(x_ghost<row-1){
                if(result_x>0 && mat[x_ghost+1][y_ghost]==0 && mat_p[x_ghost+1][y_ghost]!=-1 && flag_b!=1){
                    flag= 2;
                }
                else{
                    if(x_ghost>0){
                        if(result_x<=0 && mat[x_ghost-1][y_ghost]==0 && mat_p[x_ghost-1][y_ghost]!=-1 && flag_b!=2) {
                            flag = 1;
                        }
                    }
                }
            }
            else{
                if(x_ghost>0){
                    if(result_x<=0 && mat[x_ghost-1][y_ghost]==0 && mat_p[x_ghost-1][y_ghost]!=-1 && flag_b!=2) {
                        flag = 1;
                    }
                }
            }
        }
        if(x_ghost==x_pacman){
            if(y_ghost>0){
                if(result_y<0 && mat[x_ghost][y_ghost-1]==0 && mat_p[x_ghost][y_ghost-1]!=-1 && flag_b!=4){
                    flag= 3;
                }
                else{
                    if(y_ghost<column-1){
                        if(result_y>=0 &&  mat[x_ghost][y_ghost+1]==0 &&  mat_p[x_ghost][y_ghost+1]!=-1 && flag_b!=3){
                            flag=4;
                        }
                    }
                }
            }
            else{
                if(y_ghost<column-1){
                    if(result_y>=0 &&  mat[x_ghost][y_ghost+1]==0 &&  mat_p[x_ghost][y_ghost+1]!=-1 && flag_b!=3){
                        flag=4;
                    }
                }
            }
        }
        if((y_ghost!=y_pacman || x_ghost!=x_pacman) && flag==0){
            if(Math.abs(result_y)<Math.abs(result_x)){
                if(y_ghost>0){
                    if(result_y<0 && mat[x_ghost][y_ghost-1]==0 && mat_p[x_ghost][y_ghost-1]!=-1 && flag_b!=4){
                        flag= 3;
                    }
                    else{
                        if(y_ghost<column-1){
                            if(result_y>=0 &&  mat[x_ghost][y_ghost+1]==0 &&  mat_p[x_ghost][y_ghost+1]!=-1 && flag_b!=3){
                                flag=4;
                            }
                        }
                    }
                }
                else{
                    if(y_ghost<column-1){
                        if(result_y>=0 &&  mat[x_ghost][y_ghost+1]==0 &&  mat_p[x_ghost][y_ghost+1]!=-1 && flag_b!=3){
                            flag=4;
                        }
                    }
                }
            }
            else{
                if(Math.abs(result_y)>=Math.abs(result_x)){
                    if(x_ghost<row-1){
                        if(result_x>0 && mat[x_ghost+1][y_ghost]==0 && mat_p[x_ghost+1][y_ghost]!=-1 && flag_b!=1){
                            flag= 2;
                        }
                        else{
                            if(x_ghost>0){
                                if(result_x<=0 && mat[x_ghost-1][y_ghost]==0 && mat_p[x_ghost-1][y_ghost]!=-1 && flag_b!=2) {
                                    flag = 1;
                                }
                            }
                        }
                    }
                    else{
                        if(x_ghost>0){
                            if(result_x<=0 && mat[x_ghost-1][y_ghost]==0 && mat_p[x_ghost-1][y_ghost]!=-1 && flag_b!=2) {
                                flag = 1;
                            }
                        }
                    }
                }
            }
        }
        if(flag==0){
            if(x_ghost>0){
            if(mat[x_ghost-1][y_ghost]==0 && mat_p[x_ghost-1][y_ghost]!=1 && flag_b!=2){
                flag=1;
            }
        }
            if(x_ghost<row-1){
                if(mat[x_ghost+1][y_ghost]==0 && mat_p[x_ghost+1][y_ghost]!=-1 && flag_b!=1) {
                    flag = 2;
                }
            }

            if(y_ghost>0){
                if(mat[x_ghost][y_ghost-1]==0 && mat_p[x_ghost][y_ghost-1]!=-1 && flag_b!=4){
                    flag=3;
                }
            }
            if(y_ghost<column-1){
                if(mat[x_ghost][y_ghost+1]==0 && mat_p[x_ghost][y_ghost+1]!=-1 && flag_b!=3){
                    flag=4;
                }
            }

        }
        return flag;
    }
}