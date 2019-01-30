package com.dongdao.gqwl.action.routline.topic;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdCards;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.model.website.news.DdNewstype;
import com.dongdao.gqwl.service.routline.topic.CardconService;
import com.dongdao.gqwl.service.routline.topic.CardsService;
import com.dongdao.gqwl.service.routline.topic.TopicService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cards")
public class CardsAction extends BaseAction {

    private final static Logger log= Logger.getLogger(CardsAction.class);

    @Autowired
    public CardsService cardsService;
    @Autowired
    public CardconService  cardconService;
    @Autowired
    public TopicService topicService;
    @RequestMapping(value = "/cards.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("routline/topic/cards", context);
    }

    @RequestMapping(value = "/cards_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView cards_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("routline/topic/cards_add", context);
    }


    @RequestMapping(value = "/cards_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView cards_update(DdCards model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        model=(DdCards) cardsService.selectByPrimaryKey(model.getTopid());
        request.setAttribute("CARDS",model);
        return forword("routline/topic/cards_update", context);
    }

    @RequestMapping("/cardsDataList.do")
    public void roleDataList(DdCards model, HttpServletRequest request, HttpServletResponse response) {


        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdCards> dataList = cardsService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/cardsCount.do")
    public void roleDataCount(DdCards model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=cardsService.queryByCount(model);
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

    @RequestMapping("/deletecards.do")
    public void deletecards(DdCards model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        DdCards myCard=(DdCards)cardsService.selectByPrimaryKey(model.getCardid());
        DdTopic topic=new DdTopic();
        if(model.getIsdelete()==1){
            model.setIsdelete(0);
            topic.setTopid(myCard.getTopid());
            topic.setJoinnums(1);
            topicService.updateNums(topic);
        }else if(model.getIsdelete()==0){
            model.setIsdelete(1);
            topic.setTopid(myCard.getTopid());
            topic.setJoinnums(0);
            topicService.updateNums(topic);
        }
        int num= cardsService.updateByPrimaryKeySelective(model);
        if(num==1){

            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addcards.do")
    public void sacecards(DdCards model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setIsdelete(1);
        model.setCreattime(DateUtil.getNowPlusTime());

        int num= cardsService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑信息
    @RequestMapping("/updatecards.do")
    public void updatecards(DdCards model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setIsdelete(1);
        int num= cardsService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //审核信息
    @RequestMapping("/passcards.do")
    public void passcards(DdCards model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        DdTopic topic=new DdTopic();
        if(model.getIspass()!=null){
            model=(DdCards) cardsService.selectByPrimaryKey(model);
            if(model.getIspass()==1){
                model.setIspass(0);

                topic.setTopid(model.getTopid());
                topic.setJoinnums(1);
                topicService.updateNums(topic);
            }else{
                model.setIspass(1);
                topic.setTopid(model.getTopid());
                topic.setJoinnums(0);
                topicService.updateNums(topic);
            }
        }
        else if(model.getFiled3()!=null){
            if(model.getFiled3()==1){
                model.setFiled3(0);
            }else{
                model.setFiled3(1);
            }
        }

        int num= cardsService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //查询话题
    @RequestMapping("/querytype.do")
    public void seetype(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdTopic> pictypes= cardsService.queryType();
        jsonMap.put("topic", pictypes);

        HtmlUtil.writerJson(response, jsonMap);

    }

    //查询文件
    @RequestMapping("/selectcon.do")
    public void setype(DdCards model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model=(DdCards)cardsService.selectByPrimaryKey(model.getCardid());
        List<DdCardcon> pictypes=cardsService.selectCfile(model.getCardid());
        jsonMap.put("cardcon", pictypes);
        jsonMap.put("card", model);
        HtmlUtil.writerJson(response, jsonMap);

    }
}
