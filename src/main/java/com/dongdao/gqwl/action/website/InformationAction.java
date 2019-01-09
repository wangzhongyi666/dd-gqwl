package com.dongdao.gqwl.action.website;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.DdLeave;
import com.dongdao.gqwl.model.website.RasteMassage;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.model.website.job.DdInformation;
import com.dongdao.gqwl.service.gcolumn.RasteMassageService;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.service.website.news.InformationService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/information")
public class InformationAction extends BaseAction {

    private final static Logger log= Logger.getLogger(InformationAction.class);

    @Autowired
    public InformationService<DdInformation> informationService;

    @Autowired
    public RasteUserService<RasteUser> rasteUserService;

    @Autowired
    public RasteMassageService<RasteMassage> rasteMassageService;


    @RequestMapping(value = "/information.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/informationmassage", context);
    }

    //消息通知列表
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/informationDataList.do")
    public void informationList(DdInformation model,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {

            List<DdInformation> dataList =  informationService.queryByList(model);
            model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
            model.setNum2(model.getPageSize());
            jsonMap.put("rows", dataList);
            HtmlUtil.writerJson(response, jsonMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取总数
    @RequestMapping("/informationCount.do")
    public void roleDataCount(DdInformation model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=informationService.queryByCount(model);
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

    //消息通知删除
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/deleteinformation.do")
    public Map<String, Object> deleteinformation(Integer information_id,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            if (information_id == null) {
                return setFailureMap(jsonMap, "消息不能为空！", null);
            }
            DdInformation info = new DdInformation();
            info.setInformation_id(information_id);
            info.setIs_see(2);

            info.setUpdatetime(DateUtil.getNowPlusTime());
            informationService.updateByPrimaryKeySelective(info);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //添加消息通知
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addinformation.do")
    public Map<String, Object> addinformation(String userIds,Integer info_type,String title,String content,
                                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
//            if (userIds == null || userIds.equals("")) {
//                return setFailureMap(jsonMap, "消息接收人不能为空！", null);
//            }

            if(userIds!=null&&!userIds.equals("")){
                String[] userIdss = userIds.split(",");
                for (String sendee : userIdss){
                    if(sendee!=null&&!sendee.equals("")){
                        DdInformation info = new DdInformation();
                        info.setSendee(Integer.parseInt(sendee));
                        info.setInfo_type(info_type);
                        info.setTitle(title);
                        info.setContent(content);
                        info.setUpdatetime(DateUtil.getNowPlusTime());
                        info.setIs_see(0);
                        info.setRank(0);
                        informationService.insertSelective(info);
                    }
                }
            }else{

            }

            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }
}
