package com.dongdao.gqwl.action.routline.topic;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdCards;
import com.dongdao.gqwl.model.routline.topic.DdComment;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.service.routline.topic.CardconService;
import com.dongdao.gqwl.service.routline.topic.CardsService;
import com.dongdao.gqwl.service.routline.topic.CommentService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentAction extends BaseAction {

    private final static Logger log= Logger.getLogger(CommentAction.class);

    @Autowired
    public CommentService commentService;
    @Autowired
    public CardsService cardsService;

    @RequestMapping(value = "/comment.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("routline/topic/comment", context);
    }

    

    @RequestMapping("/commentDataList.do")
    public void roleDataList(DdComment model, HttpServletRequest request, HttpServletResponse response) {


        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdComment> dataList = commentService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/commentCount.do")
    public void roleDataCount(DdComment model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=commentService.queryByCount(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("allNum", count);
        jsonMap.put("pageCount", pageCount);
        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/deletecomment.do")
    public void deletecomment(DdComment model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model=(DdComment)commentService.selectByPrimaryKey(model);
        DdCards card=new DdCards();
        if(model.getIsdelete()==1){
            model.setIsdelete(0);
            card.setCardid(model.getCardid());
            card.setCommnums(1);
            cardsService.updateNums(card);
        }else if(model.getIsdelete()==0){
            model.setIsdelete(1);
            card.setCardid(model.getCardid());
            card.setCommnums(0);
            cardsService.updateNums(card);
        }
        int num= commentService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }



    //审核信息
    @RequestMapping("/passcomment.do")
    public void passcomment(DdComment model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model=(DdComment)commentService.selectByPrimaryKey(model);
        if(model.getIspass()==1){
            DdCards card=new DdCards();
            model.setIspass(0);
            card.setCardid(model.getCardid());
            card.setCommnums(1);
            cardsService.updateNums(card);
        }else{
            DdCards card=new DdCards();
            card.setCardid(model.getCardid());
            card.setCommnums(0);
            cardsService.updateNums(card);
            model.setIspass(1);
        }
        int num= commentService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }



}
