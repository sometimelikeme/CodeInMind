package com.apkclass.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkclass.code.AnswerNode;
import com.apkclass.code.CodeManager;
import com.apkclass.code.CodeNode;

/**
 * Created by 28852028 on 11/26/2014.
 */
public class LearnPage extends Activity {

    CodeManager codeManager;
    CodeNode codeNode;
    AnswerNode answerNode;

    RelativeLayout answerLayout;
    TextView codeName;
    TextView subject;
    ListView answerList;
    TextView answer;
    TextView result;

    TextView buttonEasy;
    TextView buttonOps;
    TextView buttonNext;
    Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learnpagenew);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String codeSelected = bundle.getString("codeName");

        subject = (TextView)findViewById(R.id.subject);
        answerList = (ListView)findViewById(R.id.answer_list);
        codeName = (TextView)findViewById(R.id.code_name);
        answer = (TextView)findViewById(R.id.answer);
        answerLayout = (RelativeLayout)findViewById(R.id.answerListLayout);
        result = (TextView)findViewById(R.id.result);

        buttonEasy = (TextView)findViewById(R.id.button_easy);
        buttonOps = (TextView)findViewById(R.id.button_ops);
        buttonNext = (TextView)findViewById(R.id.button_next);
        buttonReset = (Button)findViewById(R.id.button_reset);

        codeManager = new CodeManager(this);
        codeNode = codeManager.getCodeNode(codeSelected);
        codeName.setText(codeSelected);



        buttonEasy.setVisibility(View.INVISIBLE);
        buttonOps.setVisibility(View.INVISIBLE);
        buttonNext.setVisibility(View.INVISIBLE);

        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeNode.saveOneAnswerNodeAsRemembered(answerNode);
                answerNode = codeNode.getOneAnswerNode();
                showAnswerNode(answerNode);
            }
        });

        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog();

            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeNode.saveOneAnswerNode(answerNode, true);
                answerNode = codeNode.getOneAnswerNode();
                showAnswerNode(answerNode);
            }
        });

        buttonOps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswerNode(answerNode);
            }
        });

        answerNode = codeNode.getOneAnswerNode();
        if(answerNode.getAnswerID() == 2048){ // yeah, we true love 2048
            showDone();
        }else{
            showAnswerNode(answerNode);
        }
    }

    private void showConfirmDialog(){
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setTitle("注意！");
        confirmDialog.setMessage("你确定？");
        confirmDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                codeNode.saveOneAnswerNodeAsRemembered(answerNode);
                answerNode = codeNode.getOneAnswerNode();
                if(answerNode.getAnswerID() == 2048){ // yeah, we true love 2048
                    showDone();
                }else{
                    showAnswerNode(answerNode);
                }
            }
        });
        confirmDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        confirmDialog.create().show();
    }

    private void showDone(){
        subject.setText("厉害！你已经全部做完啦！！！看看别的题吧！");
        answerLayout.setVisibility(View.GONE);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeManager.resetDB();
                answerNode = codeNode.getOneAnswerNode();
                if(answerNode.getAnswerID() == 2048){ // yeah, we true love 2048
                    showDone();
                }else{
                    buttonReset.setVisibility(View.GONE);
                    answerLayout.setVisibility(View.VISIBLE );
                    showAnswerNode(answerNode);
                }
            }
        });
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void showAnswerNode(final AnswerNode answerNode){
        buttonEasy.setVisibility(View.VISIBLE);
        buttonOps.setVisibility(View.INVISIBLE);
        buttonNext.setVisibility(View.INVISIBLE);


        subject.setText(answerNode.getSubject());
        RelativeLayout relativeLayout =(RelativeLayout)findViewById(R.id.answerListLayout);
        ArrayAdapter<String> answerAdapter = new ArrayAdapter<String>(this, R.layout.array_list, answerNode.getRandomAnswerArrayList());
        answerList.setAdapter(answerAdapter);
        answerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String answer = (String) answerList.getItemAtPosition(i);
                if (answerNode.getCorrectAnswer().equals(answer)) {
                    showAnswer(answerNode, true);
                } else {
                    showAnswer(answerNode, false);
                }
            }
        });

        answer.setVisibility(View.GONE);
        answerList.setVisibility(View.VISIBLE);
        this.result.setVisibility(View.INVISIBLE);

    }

    private void showAnswer(AnswerNode answerNode, boolean result){
        answerList.setVisibility(View.GONE);
        answer.setText(answerNode.getCorrectAnswer());
        answer.setVisibility(View.VISIBLE);

        if(result) {
            this.result.setVisibility(View.VISIBLE);
            this.result.setText("正确！");
            buttonEasy.setVisibility(View.INVISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
            buttonOps.setVisibility(View.INVISIBLE);
        }else{
            this.result.setVisibility(View.VISIBLE);
            this.result.setText("错误！");
            buttonEasy.setVisibility(View.INVISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
            buttonOps.setVisibility(View.VISIBLE);
        }
    }

}
