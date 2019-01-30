package com.dongdao.gqwl.api.topic;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdCards;
import com.dongdao.gqwl.model.routline.topic.DdComment;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.service.routline.topic.CardconService;
import com.dongdao.gqwl.service.routline.topic.CardsService;
import com.dongdao.gqwl.service.routline.topic.CommentService;
import com.dongdao.gqwl.service.routline.topic.TopicService;
import com.dongdao.gqwl.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/comments")
public class CommentsAction extends BaseAction {

    private final static Logger log= Logger.getLogger(CommentsAction.class);

    @Autowired
    public CommentService commentService;
    @Autowired
    public CardconService cardconService;
    @Autowired
    public TopicService topicService;
    @Autowired
    public CardsService cardsService;

    //查询话题
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/comments.json")
    public Map<String, Object> sendCode(DdComment model,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setType(1);
        Integer count=commentService.queryByCount(model);
        if(model!=null && model.getPageNum()!=null && model.getPageSize()!=null){
            model.setNum1(model.getPageSize()*(model.getPageNum()-1));
            model.setNum2(model.getPageSize());
        }
        try {
            List<Map<String, Object>> comments=commentService.selectAll(model);
            for(int i=0;i<comments.size();i++){
                List<HashMap<String,Object>> hashMaps=commentService.selectByType((Long)comments.get(i).get("commentid"));

                comments.get(i).put("tcomments",hashMaps);
            }
            jsonMap.put("comments",comments );
            jsonMap.put("count",count);
            jsonMap.put("pageSize",model.getNum2());
            if( model.getPageSize()*(model.getPageNum())>=count){
                jsonMap.put("isNext",false);
            }else{
                jsonMap.put("isNext",true);
            }
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }


    //添加评论
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addcomments.json")
    public Map<String, Object> addCards(DdComment model,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        //RasteUser user= SessionUtils.getRasteUser(request);
        //model.setR_uid(Long.parseLong(user.getId()+"") );
        if(model.getC_content()!=null){
            model.setC_content(URLDecoder.decode(model.getC_content(), "UTF-8"));
            Set<String> s = BadWordUtils.words;
            Map<String,String> map = BadWordUtils.wordMap;
            System.out.println("敏感词的数量：" + BadWordUtils.wordMap.size());
            String string=model.getC_content();
            if(string!=null&&!"".equals(string)){
                Set<String> set = BadWordUtils.getBadWord(string, 2);
                if(set.size()>0){
                    return setFailureMap1(jsonMap, "包含敏感词！", null);
                }
            }
        }
        model.setCreattime(DateUtil.getNowPlusTime());
        model.setIsdelete(1);
        try {
            int num=commentService.insertSelective(model);
            if(num==1){
                DdCards card=new DdCards();
                card.setCardid(model.getCardid());
                card.setCommnums(0);
                cardsService.updateNums(card);
                return setSuccessMap(jsonMap, "操作成功！", null);
            }else{
                return setFailureMap(jsonMap, "操作失败！", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //点击量+1
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addnums.json")
    public Map<String, Object> addNums(DdComment model,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RasteUser user= SessionUtils.getRasteUser(request);
        int num=0;
        if(user!=null){

        }else{
            model=(DdComment) commentService.selectByPrimaryKey(model.getCommentid());
            model.setZannums(model.getZannums()+1);
            num=commentService.updateByPrimaryKeySelective(model);
        }

        if(num==1){

            return setSuccessMap(jsonMap, "操作成功！", null);
        }else{
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

}
