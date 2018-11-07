package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.websit.DdLeave;
import com.dongdao.gqwl.model.website.Ddbrowse;
import com.dongdao.gqwl.service.gcolumn.DdLeaveService;
import com.dongdao.gqwl.service.gcolumn.DdbrowseService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/browse")
public class BrowseApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(BrowseApiAction.class);

    @Autowired
    public DdbrowseService<Ddbrowse> ddbrowseService;

    //浏览记录
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/browses.json")
    public Map<String, Object> browses(Integer user_id,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            List<Map> datalist = ddbrowseService.queryByBrowse(user_id);
            for (Map b:datalist) {
                if(b!=null&&b.get("createtime")!=null
                        &&!b.get("createtime").toString().equals("")){
                    if(b.get("createtime").toString().compareTo(DateUtil.getDateLong(DateUtil.getNextDay(new Date(),1)))<0){
                        b.put("createtime","今天");
                    }else{
                        b.put("createtime",DateUtil.getFormattedMD(b.get("createtime").toString().substring(0,10)));
                    }

                }
            }
            return setSuccessMap(jsonMap, "操作成功！", datalist);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //浏览记录
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/browseinfo.json")
    public Map<String, Object> browseinfo(Integer browse_id,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            Map list = ddbrowseService.queryByBrowseInfo(browse_id);

            return setSuccessMap(jsonMap, "操作成功！", list);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }


    //我的一公里
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/myonekilometre.json")
    public Map<String, Object> myonekilometre(Integer user_id,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {//                                              我的一公里
            List<Map> datalist = ddbrowseService.queryByCards(1,user_id);
            for (Map b:datalist) {
                if(b!=null&&b.get("creattime")!=null
                        &&!b.get("creattime").toString().equals("")){
                    if(b.get("creattime").toString().compareTo(DateUtil.getDateLong(DateUtil.getNextDay(new Date(),1)))<0){
                        b.put("creattime","今天");
                    }else{
                        b.put("creattime",DateUtil.getFormattedMD(b.get("creattime").toString().substring(0,10)));
                    }
                }
            }
            return setSuccessMap(jsonMap, "操作成功！", datalist);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }


    //我的一公里详情
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/myonekilometreinfo.json")
    public Map<String, Object> myonekilometreinfo(Integer cardid,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {//                                              我的一公里
            Map data = ddbrowseService.queryByCardsInfo(1,cardid);

            return setSuccessMap(jsonMap, "操作成功！", data);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }


    //删除我的一公里
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/deltemyonekilometre.json")
    public Map<String, Object> deltemyonekilometre(Integer cardsid,
                                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {//                                              我的一公里
            ddbrowseService.updateByCardsId(1,cardsid);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }
}
