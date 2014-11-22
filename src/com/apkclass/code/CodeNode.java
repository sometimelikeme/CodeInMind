package com.apkclass.code;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 28852028 on 11/21/2014.
 */
public class CodeNode {

    private String codeName;
    private AVUser codeUser;

    private ArrayList<AnswerNode> answerNodeArrayList;
    private HashMap<String, ArrayList<AnswerNode>> memLevelMapper;

    public CodeNode(String name, AVUser user){
        codeName = name;
        codeUser = user;
        answerNodeArrayList = new ArrayList<AnswerNode>();
        memLevelMapper = new HashMap<String, ArrayList<AnswerNode>>();
        initMemLevelMapper();
        initAnswerNodeList();
        parseAnswerNodeList();
    }

    public ArrayList<AnswerNode> getAnswerNodeArrayList(){
        return answerNodeArrayList;
    }

    public void setAnswerNodeArrayList(ArrayList<AnswerNode> answerNodes){
        answerNodeArrayList = answerNodes;
    }

    private void initAnswerNodeList(){
        List<AVObject> answerNodes = null;
        AVQuery<AVObject> query = new AVQuery<AVObject>("UserAnswerRecorder");
        query.whereEqualTo("codeUser", codeUser);
        query.whereEqualTo("codeName", codeName);
        try {
            answerNodes = query.find();
        }catch(AVException e){
            e.printStackTrace();
        }

        if(answerNodes.size() > 0){
            for(AVObject node:answerNodes){
                AnswerNode answerNode = (AnswerNode) node;
                answerNodeArrayList.add(new AnswerNode( answerNode.getCodeName(),
                                                            answerNode.getAnswerId(),
                                                            answerNode.getAnswerUser(),
                                                            answerNode.getMemLevel()));
            }
        }
    }

    private void initMemLevelMapper(){
        for(int i=AnswerNode.MEM_LEVEL_MIN; i< AnswerNode.MEM_LEVEL_MAX; i++){
            memLevelMapper.put(String.valueOf(i), new ArrayList<AnswerNode>());
        }
    }

    private void parseAnswerNodeList(){
        if(answerNodeArrayList.size() > 0) {
            for (AnswerNode answerNode : answerNodeArrayList) {
                memLevelMapper.get(String.valueOf(answerNode.getMemLevel())).add(answerNode);
            }
        }
    }
}
