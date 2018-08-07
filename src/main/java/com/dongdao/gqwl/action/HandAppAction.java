package com.dongdao.gqwl.action;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.model.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.model.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/handApp")
public class HandAppAction extends BaseAction{
    private final static Logger log= Logger.getLogger(MainAction.class);

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public VipService vipService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public WebManaService webManaService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public FinanceService financeService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public GoodsAddressService goodsAddressService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysDeptService sysDeptService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public GoodsOrderService goodsOrderService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public GoodsService goodsService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public InsuranceService insuranceService;

    @Autowired(required = false)
    public InsuranceSetService insuranceSetService;

    @Autowired(required = false)
    public MessageService messageService;

    @Autowired(required = false)
    public IntegrationLogService integrationLogService;

    @Autowired(required = false)
    public RedeemCodeService redeemCodeService;

    @Autowired(required = false)
    public SysInsuranceService sysInsuranceService;

    @Autowired(required = false)
    public ZpResumesService zpResumesService;

    @Auth(verifyLogin=false,verifyURL=false)
    @ResponseBody
    @RequestMapping(value = "/madd.shtml")
    public String madd(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        SessionUtils.setVip(request,null);
        PrintWriter out = response.getWriter();
        String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        out.println("11111");
        out.flush();
        out.close();
        return "/goods/mall_add111";
    }

    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping(value = "/login_h5.shtml")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        SessionUtils.setVip(request,null);
        return "/login_h5";
    }

    @Auth(verifyURL=false)
    @RequestMapping("/info.shtml")
    public ModelAndView info( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/info",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/info_detail.shtml")
    public ModelAndView info_detail(WebinfoModel model, HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        List<Webinfo> list= webManaService.getwebinfolist(model);
        context.put("id",list.get(0).getId());
        context.put("title",list.get(0).getTitle());
        context.put("cont",list.get(0).getCont());
        context.put("picurl2",list.get(0).getPicurl2());
        context.put("picurl3",list.get(0).getPicurl3());
        context.put("releTime",list.get(0).getReleTime());
        return forword("h5/info_detail",context);
    }
    @Auth(verifyLogin=false,verifyURL=false)
    @ResponseBody
    @RequestMapping("/main_h5.shtml")
    public ModelAndView index( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        if(vip!=null){
            context.put("tel",vip.getTel());
            context.put("name",vip.getName());
            context.put("vipid",vip.getId());
            context.put("cityName",vip.getCityName());
        }else{
            context.put("cityName","石家庄市");
        }
        List<Map<String,Object>> deptList = sysDeptService.getktCity();
        context.put("citys",deptList);
        return forword("main_h5",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/jifen.shtml")
    public ModelAndView jifen( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/jifen",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/name.shtml")
    public ModelAndView name( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        context.put("tel",vip.getTel());
        return forword("h5/name",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/password.shtml")
    public ModelAndView password( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/password",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/register.shtml")
    public ModelAndView register( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/register",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/person_main.shtml")
    public ModelAndView person_main(String ifurl,HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        VipModel model=new VipModel();
        model.setTel(vip.getTel());
        List<Vip> vl=vipService.getVipByTel(model);
        SessionUtils.setVip(request,vl.get(0));
        if(vl.get(0).getTel()==null){
            context.put("msg","完善个人资料");
        }
        context.put("tel",vl.get(0).getTel());
        context.put("headImgUrl",vl.get(0).getHeadImgUrl());
        if(vl.get(0).getAudit()!=null && vl.get(0).getAudit()==2){
            context.put("audit","已实名认证");
        }else if(vl.get(0).getAudit()!=null && vl.get(0).getAudit()==1){
            context.put("audit","实名认证审核中");
        }else if(vl.get(0).getAudit()!=null && vl.get(0).getAudit()==3){
            context.put("audit","实名认证已驳回");
        }else{
            context.put("audit","未实名认证");
        }
        if(ifurl==null || ifurl.length()<1){
            context.put("ifurl","/handApp/my_person.shtml");
        }else{
            context.put("ifurl",ifurl);
        }
        return forword("h5/person_main",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/my_person.shtml")
    public ModelAndView my_person( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        context.put("tel",vip.getTel());
        return forword("h5/my_person",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/my_social.shtml")
    public ModelAndView my_social( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/my_social",context);
    } @Auth(verifyURL=false)
    @RequestMapping("/my_jifen.shtml")
    public ModelAndView my_jifen( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/my_jifen",context);
    } @Auth(verifyURL=false)
    @RequestMapping("/my_change.shtml")
    public ModelAndView my_change( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/my_change",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/my_address.shtml")
    public ModelAndView my_address( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/my_address",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/my_medical.shtml")
    public ModelAndView my_medical( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/my_medical",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/my_set.shtml")
    public ModelAndView my_set( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        context.put("pay_pwd",vip.getPay_pwd());
        context.put("tel",vip.getTel());
        return forword("h5/my_set",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/my_edit.shtml")
    public ModelAndView my_edit( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        context.put("tel",vip.getTel());
        return forword("h5/my_edit",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/my_detail.shtml")
    public ModelAndView my_detail(String orderNum,HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        context.put("orderNum",orderNum);
        return forword("h5/my_detail",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/my_goods.shtml")
    public ModelAndView my_goods(GoodsOrder order, HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        context.put("goods_number",order.getGoods_number());
        context.put("integral",order.getIntegral());
        context.put("onsignee_name",order.getOnsignee_name());
        context.put("deliver_goods_tel",order.getDeliver_goods_tel());
        context.put("deliver_goods_address",order.getDeliver_goods_address());
        context.put("write_off_time",order.getWrite_off_time());
        context.put("info",order.getInfo());
        context.put("ImgUrl",order.getImgUrl());
        context.put("goods_name",order.getGoods_name());
        context.put("write_off_time",order.getWrite_off_time());
        String write_off_state="";
        if(order.getTake_type()==1){
            if(order.getWrite_off_state()==1){
                write_off_state="待使用";
            }else{
                write_off_state="已使用";
            }
        }else{
            if(order.getWrite_off_state()==1){
                write_off_state="待发货";
            }else{
                write_off_state="已发货";
            }
        }
        context.put("write_off_state",write_off_state);
        context.put("take_type",order.getTake_type());
        context.put("imgary",null);
        context.put("titelimg",null);
        if(order.getImgUrl()!=null){
            String[] imgary=order.getImgUrl().split(",");
            context.put("imgary",imgary);
            context.put("titelimg",imgary[0]);
        }
        return forword("h5/my_goods",context);
    }

    @Auth(verifyURL=false)
    @RequestMapping("/news.shtml")
    public ModelAndView news( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        context.put("user_id",vip.getId());
        return forword("h5/news",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/newsInfo.shtml")
    public ModelAndView newsInfo( Integer id,Integer user_id,HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Message ms=messageService.queryById(id);
        messageService.updateUnread(id,null);//修改已读

        MessageModel model=new MessageModel();
        model.setUser_id(user_id);
        model.setUnread(1);
        Integer mcount=messageService.queryByCount(model);

        context.put("id",ms.getId());
        context.put("title",ms.getTitle());
        context.put("content",ms.getContent());
        context.put("createTime",ms.getCreateTime());
        context.put("mcount",mcount);
        return forword("h5/newsInfo",context);
    }
    @Auth(verifyURL=false)
    @RequestMapping("/xieyi.shtml")
    public ModelAndView xieyi(HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        return forword("h5/xieyi",context);
    }
    /**
     * 医疗订单核对页面
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/ybdetail.shtml")
    public ModelAndView news(OrderModel model, HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        model.setOrderNum(Md5Util.createPwd()+(new Date().getTime()));
        if(vip==null){
            context.put("msg","登录超时");
            return forword("h5/yletail",context);
        }

        context.put("name",vip.getName());
        context.put("identNum",vip.getIdentNum());
        context.put("insuranceType",2);
        context.put("insuranceType_zh","医疗保险");
        context.put("vipaudit",vip.getAudit());

        Integer month = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        if(model!=null&&model.getInsuEnd().equals("半年")){
            model.setInsuStart(sdf.format(new Date()));
            Date dateStart = DateUtil.getDateBetweenByMonth(DateUtil.fromStringToDate("yyyyy/MM",
                    model.getInsuStart().replaceAll("-","/")),5);

            context.put("InsuStart",model.getInsuStart());
            context.put("InsuEnd",sdf.format(dateStart));
            month = 6;

        }else if(model!=null&&model.getInsuEnd().equals("全年")){
            model.setInsuStart(sdf.format(new Date()));
            Date dateStart = DateUtil.getDateBetweenByMonth(DateUtil.fromStringToDate("yyyyy/MM",
                    model.getInsuStart().replaceAll("-","/")),11);

            context.put("InsuStart",model.getInsuStart());
            context.put("InsuEnd",sdf.format(dateStart));
            month = 12;
        }else{
            if(model.getSubTime()!=null&&!model.getSubTime().equals("")){
                model.setInsuStart(model.getSubTime().split(",")[0]);
                model.setInsuEnd(model.getSubTime().split(",")[1]);
            }


            context.put("InsuStart",model.getInsuStart());
            context.put("InsuEnd",model.getInsuEnd());

            month = DateUtil.getMonth(model.getInsuStart().replaceAll("-","/"),model.getInsuEnd().replaceAll("-","/"))+1;


        }

        model.setAudit(1);
        if(model.getDeptId()==null){
            model.setDeptId(vip.getCity());
        }
        InsuranceModel insModel = new InsuranceModel();
        insModel.setInsuranceType(2);
        insModel.setDeptId(model.getDeptId());
        insModel.setExpends_scale(Double.parseDouble(""+(model.getRatio()*0.01)+""));
        Insurance ins = insuranceService.calculatePayment(insModel);
        model.setPayment(Double.parseDouble(ins.getExpends_amount())*month);
        context.put("audit",model.getAudit());
        context.put("deptId",model.getDeptId());
        context.put("payment",model.getPayment());
        context.put("expends_amount",ins.getExpends_amount());
        if(month<6){
            context.put("integral",0);
        }else if(month>=6){
            context.put("integral",model.getPayment());
        }/*else{
            context.put("integral",model.getPayment()*1.5);
        }*/
        context.put("ratio",model.getRatio());
        context.put("base",ins.getExpends_base());

        return forword("h5/ybdetail",context);
    }

    /**
     * 养老订单核对
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/yldetail.shtml")
    public ModelAndView yletail(OrderModel model,HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);

        model.setOrderNum(Md5Util.createPwd()+(new Date().getTime()));
        if(vip==null){
            context.put("msg","登录超时");
            return forword("h5/yletail",context);
        }
        context.put("name",vip.getName());
        context.put("identNum",vip.getIdentNum());
        context.put("insuranceType",1);
        context.put("insuranceType_zh","养老保险");
        context.put("vipaudit",vip.getAudit());

        Integer month = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        if(model!=null&&model.getInsuEnd().equals("半年")){
            model.setInsuStart(sdf.format(new Date()));
            Date dateStart = DateUtil.getDateBetweenByMonth(DateUtil.fromStringToDate("yyyyy/MM",
                    model.getInsuStart().replaceAll("-","/")),5);

            context.put("InsuStart",model.getInsuStart());
            context.put("InsuEnd",sdf.format(dateStart));
            month = 6;

        }else if(model!=null&&model.getInsuEnd().equals("全年")){
            model.setInsuStart(sdf.format(new Date()));
            Date dateStart = DateUtil.getDateBetweenByMonth(DateUtil.fromStringToDate("yyyyy/MM",
                    model.getInsuStart().replaceAll("-","/")),11);

            context.put("InsuStart",model.getInsuStart());
            context.put("InsuEnd",sdf.format(dateStart));
            month = 12;
        }else{
            if(model.getSubTime()!=null&&model.getSubTime().equals("")){
                model.setInsuStart(model.getSubTime().split(",")[0]);
                model.setInsuEnd(model.getSubTime().split(",")[1]);
            }

            context.put("InsuStart",model.getInsuStart());
            context.put("InsuEnd",model.getInsuEnd());

            month = DateUtil.getMonth(model.getInsuStart().replaceAll("-","/"),model.getInsuEnd().replaceAll("-","/"))+1;


        }

        model.setAudit(1);
        if(model.getDeptId()==null){
            model.setDeptId(vip.getCity());
        }
        InsuranceModel insModel = new InsuranceModel();
        insModel.setInsuranceType(1);
        insModel.setDeptId(model.getDeptId());
        insModel.setExpends_scale(Double.parseDouble(""+(model.getRatio()*0.01)+""));
        Insurance ins = insuranceService.calculatePayment(insModel);
        model.setPayment(Double.parseDouble(ins.getExpends_amount())*month);
        context.put("audit",model.getAudit());
        context.put("deptId",model.getDeptId());
        context.put("payment",model.getPayment());
        context.put("expends_amount",ins.getExpends_amount());
        if(month<6){
            context.put("integral",0);
        }else if(month>=6){
            context.put("integral",model.getPayment());
        }/*else{
            context.put("integral",model.getPayment()*1.5);
        }*/
        context.put("ratio",model.getRatio());
        context.put("base",ins.getExpends_base());

        return forword("h5/yldetail",context);
    }


    /**
     * 支付成功页面
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/success.shtml")
    public ModelAndView success(HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        return forword("h5/success",context);
    }

    /*aboutus.html      关于我们（完成）

    ush5.html         小程序关于我们

    hospital.html     定点医院（完成）

    apply.html        申请补贴（完成）

    job.html          就业培训（完成）*/

    /**
     * 关于我们（完成）
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/aboutus.shtml")
    public ModelAndView aboutus(HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        return forword("h5/aboutus",context);
    }
    /**
     * 小程序关于我们
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/ush5.shtml")
    public ModelAndView ush5(HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        return forword("h5/ush5",context);
    }
    /**
     * 定点医院
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/hospital.shtml")
    public ModelAndView hospital(HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        return forword("h5/hospital",context);
    }
    /**
     * 申请补贴
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/apply.shtml")
    public ModelAndView apply(HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        return forword("h5/apply",context);
    }
    /**
     * 支付成功
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/paySuccess.shtml")
    public ModelAndView paySuccess(String order_number,HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        context.put("order_number",order_number);
        return forword("h5/pay_success",context);
    }

    /**
     * 就业培训
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @RequestMapping("/job.shtml")
    public ModelAndView job(HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        return forword("h5/job",context);
    }


    /**
     * 会员登录
     * @param tel
     * @param pwd
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/tologin.do")
    public Map<String, Object> getUsers(String tel, String pwd, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        if(StringUtils.isBlank(tel)){
            //sendFailureMessage(response, "账号不能为空.");
            return setFailureMap(jsonMap, "账号不能为空", null);
        }
        if(StringUtils.isBlank(pwd)){
            //sendFailureMessage(response, "密码不能为空.");
            return setFailureMap(jsonMap, "密码不能为空", null);
        }
        List<Vip> vips=vipService.getVipLogin(tel,pwd);
        String msg = "用户登录日志:";
        if(vips==null||vips.size()==0){
            //记录错误登录日志
            log.debug(msg+"["+tel+"]"+"账号或者密码输入错误.");
            // sendFailureMessage(response, "账号或者密码输入错误.");
            return setFailureMap(jsonMap, "账号密码错误", null);
        }else if (vips.size()==1){
            for (Vip vip : vips) {
                Map<String, Object> userMap = new HashMap<String, Object>();
                userMap.put("id", vip.getId());
                userMap.put("name", vip.getName());
                userMap.put("tel", vip.getTel());
                userMap.put("identNum", vip.getIdentNum());
                userMap.put("edu", vip.getEdu());
                userMap.put("professional", vip.getProfessional());
                userMap.put("email", vip.getEmail());
                userMap.put("qq", vip.getQq());
//                userMap.put("bank", vip.getBank());
//                userMap.put("bankNumail", vip.getBankNum());
                userMap.put("audit", vip.getAudit()==null?0:vip.getAudit());
                resList.add(userMap);
            }
            int loginCount = 0;
            if(vips.get(0).getLoginCount() != null){
                loginCount = vips.get(0).getLoginCount();
            }
            int id = vips.get(0).getId();
            loginCount++;
            vipService.updateLoginCount(id,loginCount);
            //用户信息放入session
            SessionUtils.setVip(request,vips.get(0));

        }
        return setSuccessMap(jsonMap, "登录成功！", resList);
    }
    /**
     * 注册会员
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/vipregister")
    public  Map<String, Object> vipregister(VipModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setCreateTime(DateUtil.getNowPlusTime());
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        try {
            Integer con= vipService.countVipBytel(model);
            if(con!=null && con>0){
                vipService.deletIdentByTel(model.getTel());
                return setFailureMap(jsonMap, "手机号已注册！", null);
            }else{
                List<Map> identlist=vipService.getIdent(model.getTel());
                if(identlist!=null && identlist.size()>0 && identlist.get(0).get("ident").equals(model.getIdent())){
                    vipService.vipregister(model);
                    IntegrationModel model2=new IntegrationModel();
                    model2.setUid(model.getId());
                    model2.setIntegration(0);
                    String time=DateUtil.getNowPlusTime();
                    model2.setUpdateTime(time);
                    integrationLogService.addIn(model2);
                    Map<String, Object> userMap = new HashMap<String, Object>();
                    userMap.put("id", model.getId());
                    userMap.put("name", null);
                    userMap.put("tel", model.getTel());
                    userMap.put("identNum", null);
                    userMap.put("edu", null);
                    userMap.put("professional", null);
                    userMap.put("email", null);
                    userMap.put("qq", null);
                    userMap.put("bank", null);
                    userMap.put("bankNumail", null);
                    userMap.put("audit", 0);
                    resList.add(userMap);
                }else{
                    return setFailureMap(jsonMap, "验证码错误！", null);
                }
                vipService.deletIdentByTel(model.getTel());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "注册失败！", null);
        }
        return setSuccessMap(jsonMap, "注册成功！", resList);
    }

    /**
     * 修改密码
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/updatePwd")
    public  Map<String, Object> updatePwd(VipModel model,String opwd, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setCreateTime(DateUtil.getNowPlusTime());
        try {
            if(opwd!=null){
                Vip vip=SessionUtils.getVip(request);
                if(model.getTel()==null && vip!=null){
                    model.setTel(vip.getTel());
                }
                List<Vip> viplist= vipService.getVipByTel(model);
                if(viplist!=null && viplist.size()>0){
                    if(!opwd.equals(viplist.get(0).getPwd())){
                        return setFailureMap(jsonMap, "原密码错误！", null);
                    }
                }else{
                    return setFailureMap(jsonMap, "手机号错误！", null);
                }
            }else{
                List<Vip> viplist=vipService.getVipByTel(model);
                if(viplist==null || viplist.size()<1){
                    return setFailureMap(jsonMap, "账号不存在！", null);
                }
                List<Map> identlist=vipService.getIdent(model.getTel());
                if(identlist!=null && identlist.size()>0 && identlist.get(0).get("ident").equals(model.getIdent())){
                }else{
                    return setFailureMap(jsonMap, "验证码错误！", null);
                }
                vipService.deletIdentByTel(model.getTel());
            }
            vipService.updatePwd(model);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "修改失败！", null);
        }
        return setSuccessMap(jsonMap, "修改成功！", null);
    }
    /**
     * 修改支付密码
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/updatePayPwd")
    public  Map<String, Object> updatePayPwd(VipModel model,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip=SessionUtils.getVip(request);
        model.setCreateTime(DateUtil.getNowPlusTime());
        try {
            List<Map> identlist=vipService.getIdent(model.getTel());
            if(identlist!=null && identlist.size()>0 && identlist.get(0).get("ident").equals(model.getYzm())){
                vipService.updatePayPwd(model);
                if(vip !=null){
                    vip.setPay_pwd(model.getPay_pwd());
                    SessionUtils.setVip(request,vip);
                }
            }else{
                return setFailureMap(jsonMap, "验证码错误！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    /**
     * 上传身份证
     * @param file1
     * @param name
     * @param identNum
     * @param tel
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/uploadImgUrl")
    public  Map<String, Object> uploadImgUrl(@RequestParam("file1") MultipartFile file1,Integer picType,
                                             String name,String identNum,String tel, HttpServletResponse response,
                                             HttpServletRequest request) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String parhstr=request.getSession().getServletContext()
                    .getRealPath(File.separator);
            parhstr=parhstr.substring(0,parhstr.length()-5);
            String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
            String type1 = file1!=null && !org.apache.commons.lang.StringUtils.isBlank(file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")):"";
            if(type1.equals("")){
                return setFailureMap(jsonMap, "请选择图片！", null);
            }
            if ((!type1.equals("") && imgeArray.indexOf(type1.toUpperCase()) < 0)) {
                return setFailureMap(jsonMap, "文件格式错误！", null);
            }
            if ((file1!=null && file1.getSize() > 10485760)) {
                return setFailureMap(jsonMap, "图片过大！", null);
            }
            String sjc = "";
            String path = "";
            VipModel model=new VipModel();
            if(!type1.equals("")){
                sjc=DateUtil.getNowPlusTimeMill();
                path = parhstr+"aptitude"
                        + File.separator
                        + sjc
                        +type1;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                file1.transferTo(new File(path));
                if(picType.equals(1)){
                    model.setIdentPicUrl1(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
                }else{
                    model.setIdentPicUrl2(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
                }
            }
            model.setName(name);
            model.setIdentNum(identNum);
            model.setTel(tel);
            model.setAudit(1);
            model.setProvince(130000);
            model.setCity(130100);
            model.setSubmitTime(DateUtil.getNowPlusTime());
            vipService.updateBytel(model);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    @RequestMapping("/uploadImgUrl1")
    public  void uploadImgUrl1(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                             String name,String identNum,String tel, HttpServletResponse response,
                                             HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
            String type1 = file1!=null && !org.apache.commons.lang.StringUtils.isBlank(file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")):"";
            String type2 = file2!=null && !org.apache.commons.lang.StringUtils.isBlank(file2.getOriginalFilename())?file2.getOriginalFilename().substring(
                    file2.getOriginalFilename().lastIndexOf(".")):"";
            String parhstr=request.getSession().getServletContext()
                    .getRealPath(File.separator);
            parhstr=parhstr.substring(0,parhstr.length()-5);
            if(type1.equals("") && type2.equals("")){
                result.put(SUCCESS, false);
                result.put(MSG, "请选择图片!");
                HtmlUtil.writerJson(response, result);
            }
            if ((!type1.equals("") && imgeArray.indexOf(type1.toUpperCase()) < 0) || (!type2.equals("") && imgeArray.indexOf(type2.toUpperCase()) < 0)) {
                result.put(SUCCESS, false);
                result.put(MSG, "上传文件不是图片格式,请重新上传！");
                HtmlUtil.writerJson(response, result);
            }
            if ((file1!=null && file1.getSize() > 10485760) || (file2!=null && file2.getSize() > 10485760) ) {
                result.put(SUCCESS, false);
                result.put(MSG, "上传图片不能大于10M,请重新上传！");
                HtmlUtil.writerJson(response, result);
            }
            String sjc = "";
            String path = "";
            VipModel model=new VipModel();
            if(!type1.equals("")){
                sjc=DateUtil.getNowPlusTimeMill();
                path = parhstr+"aptitude"
                        + File.separator
                        + sjc
                        +type1;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                file1.transferTo(new File(path));
                model.setIdentPicUrl1(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
            }
            if(!type2.equals("")){
                sjc=DateUtil.getNowPlusTimeMill()+2;
                path = parhstr+ "aptitude"
                        + File.separator
                        + sjc
                        +type2;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                file2.transferTo(new File(path));
                model.setIdentPicUrl2(UserConstants.CRMURL + "aptitude/"+ sjc + type2);
            }
            model.setName(name);
            model.setIdentNum(identNum);
            model.setTel(tel);
            model.setAudit(1);
            model.setProvince(130000);
            model.setCity(130100);
            model.setSubmitTime(DateUtil.getNowPlusTime());
            vipService.updateBytel(model);
        } catch (Exception e) {
            e.printStackTrace();
            result.put(SUCCESS, false);
            result.put(MSG, "操作失败");
            HtmlUtil.writerJson(response, result);
        }
        result.put(SUCCESS, true);
        result.put(MSG, "操作成功");
        HtmlUtil.writerJson(response, result);
    }
    /**
     * 获取资讯列表
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getInfo.do")
    public Map<String, Object> getInfo(WebinfoModel model ,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        if(model!=null && model.getPageNum()!=null && model.getPageSize()!=null){
            model.setNum1(model.getPageSize()*(model.getPageNum()-1));
            model.setNum2(model.getPageSize());
        }
        Integer infocoun=webManaService.countWebInfo(model);
        List<Webinfo> infos= webManaService.getwebinfolist(model);
        String msg = "用户登录日志:";
        if(infos==null||infos.size()==0){
            return setFailureMap(jsonMap, "暂无资讯", null);
        }else{
            for (Webinfo info : infos) {
                Map<String, Object> userMap = new HashMap<String, Object>();
                userMap.put("id", info.getId());
                userMap.put("title", info.getTitle());
                if(info.getCont()!=null && info.getCont().length()>20){
                    userMap.put("cont", info.getCont().substring(0,20)+"...");
                }else{
                    userMap.put("cont", info.getCont());
                }
                userMap.put("cont1", info.getCont());
                userMap.put("picurl1", info.getPicurl1());
                userMap.put("picurl2", info.getPicurl2());
                userMap.put("picurl3", info.getPicurl3());
                userMap.put("releTime", info.getReleTime());
                userMap.put("url",getRootMap().get("msUrl")+"/handApp/info_detail.shtml");
                resList.add(userMap);
            }
        }
        if(model.getPageSize()!=null && model.getPageNum()!=null && infocoun>model.getPageSize()*model.getPageNum()){
            jsonMap.put("flag",1);
        }else{
            jsonMap.put("flag",2);
        }
        return setSuccessMap(jsonMap, "操作成功！", resList);
    }

    /**
     * 获取H5图片、链接
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getoffice.do")
    public Map<String, Object> getoffice( HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        resList= webManaService.getOffice();
        Map<String, Object> map=resList.get(0);
        Map<String, Object> map1= new HashMap<String, Object>();
        Map<String, Object> map2= new HashMap<String, Object>();
        Map<String, Object> map3= new HashMap<String, Object>();
        map1.put("picture",map.get("picture1"));
        map1.put("url",map.get("url1"));
        map1.put("title",map.get("title1"));
        map1.put("id",1);
        map2.put("picture",map.get("picture2"));
        map2.put("url",map.get("url2"));
        map2.put("title",map.get("title2"));
        map2.put("id",2);
        map3.put("picture",map.get("picture3"));
        map3.put("url",map.get("url3"));
        map3.put("title",map.get("title3"));
        map3.put("id",3);

        List<Map<String, Object>> datelist=new ArrayList<Map<String, Object>>();
        datelist.add(map1);
        datelist.add(map2);
        datelist.add(map3);
        return setSuccessMap(jsonMap, "操作成功！", datelist);
    }

    /**
     * 修改会员信息
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/saveVipInfo.do")
    public Map<String, Object> saveVipInfo( @RequestParam("file1") MultipartFile file1,VipModel model,HttpServletRequest request,HttpServletResponse response)throws Exception {
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
            String type1 = file1!=null && !org.apache.commons.lang.StringUtils.isBlank(file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")):"";
            String parhstr=request.getSession().getServletContext()
                    .getRealPath(File.separator);
            parhstr=parhstr.substring(0,parhstr.length()-5);

            if(!type1.equals("")){
                if (imgeArray.indexOf(type1.toUpperCase()) < 0 ) {
                    return setFailureMap(jsonMap, "图片格式错误！", null);
                }
                if (file1!=null && file1.getSize() > 10485760 ) {
                    return setFailureMap(jsonMap, "图片过大！", null);
                }
                String sjc = "";
                String path = "";
                if(!type1.equals("")){
                    sjc=DateUtil.getNowPlusTimeMill();
                    path = parhstr+"aptitude"
                            + File.separator
                            + sjc
                            +type1;
                    File f = new File(path);
                    // 创建文件夹
                    if (!f.exists()) {
                        f.getParentFile().mkdirs();
                        f.createNewFile();
                    }
                    file1.transferTo(new File(path));
                    model.setHeadImgUrl(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
                }
            }
            vipService.updateBytel(model);
            if(vip!=null){
                vip.setSchool(model.getSchool());
                vip.setEdu(model.getEdu());
                vip.setProfessional(model.getProfessional());
                vip.setEmail(model.getEmail());
                vip.setQq(model.getQq());
                vip.setHeadImgUrl(model.getHeadImgUrl());
                SessionUtils.setVip(request,vip);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "修改失败！", null);
        }
        return setSuccessMap(jsonMap, "修改成功！", null);
    }
    /**
     * 修改会员头像(小程序)
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/saveVipPct.do")
    public Map<String, Object> saveVipPct( @RequestParam("file1") MultipartFile file1,VipModel model,HttpServletRequest request,HttpServletResponse response)throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
            String type1 = file1!=null && !org.apache.commons.lang.StringUtils.isBlank(file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")):"";
            String parhstr=request.getSession().getServletContext()
                    .getRealPath(File.separator);
            parhstr=parhstr.substring(0,parhstr.length()-5);

            if(!type1.equals("")){
                if (imgeArray.indexOf(type1.toUpperCase()) < 0 ) {
                    return setFailureMap(jsonMap, "图片格式错误！", null);
                }
                if (file1!=null && file1.getSize() > 10485760 ) {
                    return setFailureMap(jsonMap, "图片过大！", null);
                }
                String sjc = "";
                String path = "";
                if(!type1.equals("")){
                    sjc=DateUtil.getNowPlusTimeMill();
                    path = parhstr+"aptitude"
                            + File.separator
                            + sjc
                            +type1;
                    File f = new File(path);
                    // 创建文件夹
                    if (!f.exists()) {
                        f.getParentFile().mkdirs();
                        f.createNewFile();
                    }
                    file1.transferTo(new File(path));
                    model.setHeadImgUrl(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
                }
            }
            vipService.updateBytel(model);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "修改失败！", null);
        }
        return setSuccessMap(jsonMap, "修改成功！", null);
    }
    /**
     * 修改会员信息(小程序)
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/saveVipInfo1.do")
    public Map<String, Object> saveVipInfo(VipModel model,HttpServletRequest request,HttpServletResponse response)throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            vipService.updateBytel(model);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "修改失败！", null);
        }
        return setSuccessMap(jsonMap, "修改成功！", null);
    }
    /**
     * 获取vip个人信息
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getVipInfo.do")
    public Map<String, Object> getVipInfo(VipModel model,HttpServletRequest request,HttpServletResponse response)throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<Map<String,Object>> datalist=new ArrayList<Map<String,Object>>();
        try {
            List<Vip> viplist=vipService.getVipByTel(model);
            if(viplist!=null && viplist.size()>0){
                for(int i=0;i<viplist.size();i++){
                    Map<String,Object> vipmap=new HashMap<String, Object>();
                    vipmap.put("edu",viplist.get(i).getEdu());
                    vipmap.put("professional",viplist.get(i).getProfessional());
                    vipmap.put("email",viplist.get(i).getEmail());
                    vipmap.put("qq",viplist.get(i).getQq());
                    vipmap.put("province",viplist.get(i).getProvince());
                    vipmap.put("city",viplist.get(i).getCity());
                    vipmap.put("headImgUrl",viplist.get(i).getHeadImgUrl());
                    if(viplist.get(i).getHeadImgUrl()== null || viplist.get(i).getHeadImgUrl().length()<1){
                        vipmap.put("headImgUrl","https://www.365linghuo.com/static/images/h5/logo.png");
                    }
                    vipmap.put("provinceName",viplist.get(i).getProvinceName());
                    vipmap.put("cityName",viplist.get(i).getCityName());
                    vipmap.put("province",viplist.get(i).getProvince());
                    vipmap.put("city",viplist.get(i).getCity());
                    vipmap.put("audit",viplist.get(i).getAudit());
                    datalist.add(vipmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", datalist);

    }
    /**
     * count参保订单列表(by tel)
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/countOrderlist")
    public Map<String, Object> countOrderlist(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getTel()==null && vip!=null){
            model.setTel(vip.getTel());
        }
        Integer count=0;
        count= financeService.countOrderlist(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }
        jsonMap.put("allNum",count);
        jsonMap.put("pageCount",pageCount);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    /**
     * 查询参保订单列表（by tel）
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getOrderlist")
    public Map<String, Object> getOrderlist(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        if(model.getTel()==null && vip!=null){
            model.setTel(vip.getTel());
        }
        if(model.getTel()==null || model.getTel().length()<1){
            return setFailureMap(jsonMap, "请确认登录！", resList);
        }
        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Order> dataList=financeService.getOrderlist(model);
        if (dataList!=null && dataList.size()>0){
            for (Order ord : dataList) {
                Map<String, Object> userMap = new HashMap<String, Object>();
                userMap.put("id", ord.getId());
                userMap.put("orderNum", ord.getOrderNum());
                userMap.put("insuranceType", ord.getInsuranceType());
                userMap.put("insuStart", ord.getInsuStart());
                userMap.put("insuEnd", ord.getInsuEnd());
                userMap.put("payment", ord.getPayment());
                userMap.put("rechargeTime", ord.getRechargeTime());
                userMap.put("audit", ord.getAudit());
                userMap.put("deptName", ord.getDeptName());
                switch (ord.getAudit()){
                    case 1:
                        userMap.put("auditStr","资料审核中");
                        break;
                    case 2:
                        userMap.put("auditStr","待缴费");
                        break;
                    case 3:
                        userMap.put("auditStr","已拒绝");
                        break;
                    case 5:
                        userMap.put("auditStr","已过期");
                        break;
                    case 6:
                        userMap.put("auditStr","参保中");
                        break;
                    case 7:
                        userMap.put("auditStr","退保审核中");
                        break;
                    case 8:
                        userMap.put("auditStr","已退保");
                        break;
                    case 9:
                        userMap.put("auditStr","已完成");
                        break;
                    case 10:
                        userMap.put("auditStr","已取消");
                        break;
                }
                resList.add(userMap);
            }
        }
        return setSuccessMap(jsonMap, "操作成功！", resList);
    }

    /**
     * count积分记录
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/countInteInfo")
    public Map<String, Object> countInteInfo(IntegrationModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getUid()==null && vip!=null){
            model.setUid(vip.getId());
        }
        Integer count=0;
        count= financeService.countInteInfo(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }
        jsonMap.put("allNum",count);
        jsonMap.put("pageCount",pageCount);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    /**
     * 查询积分明细(by uid)
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getInteInfo")
    public Map<String, Object> getInteInfo(IntegrationModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getUid()==null && vip!=null){
            model.setUid(vip.getId());
        }
        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Map<String,Object>> dataList=financeService.getInteInfo(model);
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }

    /**
     * 查询订单详情
     * @param orderNum
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getOrderByOrderNum")
    public Map<String, Object> getOrderByOrderNum(String orderNum, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("servTel",getRootMap().get("serviceTel"));
        List<Map<String,Object>> dataList=financeService.getOrderByOrderNum(orderNum);
        List<OrderInfo> infolist=financeService.getOrdeInfoByOrderNum(orderNum);
        if(dataList!=null && dataList.size()>0 ){
            Integer monthNum=Integer.parseInt(dataList.get(0).get("monthNum")==null?"0":dataList.get(0).get("monthNum")+"");
            double onepay=Double.parseDouble(dataList.get(0).get("onepay")==null?"0.0":dataList.get(0).get("onepay")+"");
            double allpay=onepay * monthNum;
            double dpay=0.0;
            if(infolist!=null&&infolist.size()>0){
                monthNum+=infolist.get(0).getMonthNum()==null?0:infolist.get(0).getMonthNum();
                allpay+=infolist.get(0).getPayment()==null?0.0:infolist.get(0).getPayment();
            }
            dpay=Double.parseDouble(dataList.get(0).get("payment")==null?"0.0":dataList.get(0).get("payment")+"")-allpay;
            dataList.get(0).put("monthNum",monthNum);
            dataList.get(0).put("allpay",allpay);
            dataList.get(0).put("dpay",dpay);
            Integer mon=DateUtil.getMonth((dataList.get(0).get("insuStart")+"").replaceAll("-","/"),(dataList.get(0).get("insuEnd")+"").replaceAll("-","/"))+1;
            if(mon>=6){
                dataList.get(0).put("integration",(int)(Double.parseDouble(dataList.get(0).get("payment")+"")));
            }else{
                dataList.get(0).put("integration",0);
            }
            dataList.get(0).put("quit_integration",0);
            dataList.get(0).put("integration_diff",0);
            dataList.get(0).put("payment_inte",0.0);
            dataList.get(0).put("pra_payment",0.0);
            dataList.get(0).put("servTel",getRootMap().get("serviceTel"));
            switch (Integer.parseInt(dataList.get(0).get("audit")+"")){
                case 1:
                    dataList.get(0).put("auditStr","资料审核中");
                    break;
                case 2:
                    dataList.get(0).put("auditStr","待审核");
                    break;
                case 3:
                    dataList.get(0).put("auditStr","已拒绝");
                    break;
                case 5:
                    dataList.get(0).put("auditStr","已过期");
                    break;
                case 6:
                    dataList.get(0).put("auditStr","参保中");
                    break;
                case 7:
                    dataList.get(0).put("auditStr","退保审核中");
                    break;
                case 8:
                    dataList.get(0).put("auditStr","已退保");
                    break;
                case 9:
                    dataList.get(0).put("auditStr","已完成");
                    break;
                case 10:
                    dataList.get(0).put("auditStr","已取消");
                    break;
            }
            String auditTime=dataList.get(0).get("auditTime")+"";
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=format.parse(auditTime);
            Long lt=date.getTime()+1000*60*60*48;
            Date date1=new Date();
            if(date1.getTime()>lt){
                OrderModel ormodel=new OrderModel();
                ormodel.setAudit(5);
                ormodel.setOrderNum(orderNum);
                ormodel.setAuditTime(DateUtil.getNowPlusTime());
                insuranceService.upAuditByOrderNum(ormodel);
            }
            dataList.get(0).put("overTime",format.format(lt));
            if(Integer.parseInt(dataList.get(0).get("audit")+"")==8){
                List<Map<String,Object>> quitlist=financeService.getQuitByOrderNum(orderNum);
                if(quitlist!=null && quitlist.size()>0){
                    dataList.get(0).put("quit_integration",quitlist.get(0).get("quit_integration")==null?0:quitlist.get(0).get("quit_integration"));
                    dataList.get(0).put("integration_diff",quitlist.get(0).get("integration_diff")==null?0:quitlist.get(0).get("integration_diff"));
                    dataList.get(0).put("payment_inte",quitlist.get(0).get("payment_inte")==null?0:quitlist.get(0).get("payment_inte"));
                    dataList.get(0).put("pra_payment",quitlist.get(0).get("pra_payment")==null?0:quitlist.get(0).get("pra_payment"));

                }
            }

        }
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/qxOrder")
    public Map<String, Object> qxOrder(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String time=DateUtil.getNowPlusTime();
        model.setAuditTime(time);
        try {
            insuranceService.upAuditByOrderNum(model);
        } catch (Exception e) {
            return setSuccessMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    /**
     * 查询可用积分
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getJfye")
    public Map<String, Object> getJfye(Integer uid,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        if(uid ==null && vip!=null){
            uid=vip.getId();
        }
        List<Map<String,Object>> dataList=financeService.getJfye(uid);
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }

    /**
     * count收货地址
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/countAddresslist")
    public Map<String, Object> countAddresslist(GoodsAddressModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setVip_id(vip.getId());
        Integer count=0;
        count= goodsAddressService.countAddresslist(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }
        jsonMap.put("allNum",count);
        jsonMap.put("pageCount",pageCount);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    /**
     * 获取收货地址列表
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getAddresslist")
    public Map<String, Object> getAddresslist(GoodsAddressModel model,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getVip_id()==null && vip !=null){
            model.setVip_id(vip.getId());
        }

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Map<String,Object>> dataList=goodsAddressService.getAddresslist(model);
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }
    /**
     * 修改收货地址
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/updateAddress.do")
    public Map<String, Object> updateAddress( GoodsAddressModel model,HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        String mstr="操作";
        try {
            if(model.getId()!=null && model.getId()>0){
                mstr="修改";
                goodsAddressService.updateAddress(model);
            }else{
                mstr="保存";
                if(model.getVip_id()==null && vip!=null){
                    model.setVip_id(vip.getId());
                }
                Integer count=goodsAddressService.countAddresslist(model);
                if(count!=null && count>=20){
                    return setFailureMap(jsonMap, "最多20个地址！", null);
                }else{
                    goodsAddressService.addAddress(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap,mstr+"失败！", null);
        }
        return setSuccessMap(jsonMap, mstr+"成功！", null);
    }
    /**
     * 设置默认收货地址
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/updateDefAddress.do")
    public Map<String, Object> updateDefAddress( GoodsAddressModel model,HttpServletRequest request,HttpServletResponse response) {
        Vip vip = SessionUtils.getVip(request);
        if(model.getVip_id()==null && vip !=null){
            model.setVip_id(vip.getId());
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            goodsAddressService.updateDefAddressByVipId(model);
            goodsAddressService.updateDefAddress(model);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "修改失败！", null);
        }
        return setSuccessMap(jsonMap, "修改成功！", null);
    }
    /**
     * 删除收货地址
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/deleteAddress.do")
    public Map<String, Object> deleteAddress( GoodsAddressModel model,HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            goodsAddressService.deleteAddress(model);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "删除失败！", null);
        }
        return setSuccessMap(jsonMap, "删除成功！", null);
    }
    /**
     * 添加收货地址
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/addAddress.do")
    public Map<String, Object> addAddress( GoodsAddressModel model,HttpServletRequest request,HttpServletResponse response) {
        Vip vip = SessionUtils.getVip(request);
        if(model.getVip_id()==null && vip!=null){
            model.setVip_id(vip.getId());
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            Integer count=goodsAddressService.countAddresslist(model);
            if(count!=null && count>=20){
                return setFailureMap(jsonMap, "最多20个地址！", null);
            }else{
                goodsAddressService.addAddress(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "保存失败！", null);
        }
        return setSuccessMap(jsonMap, "保存成功！", null);
    }
    /**
     * count医保卡
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/countMedicallist")
    public Map<String, Object> countMedicallist(SysInsuranceModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setVip_id(vip.getId());
        Integer count=0;
        count= sysInsuranceService.countMedicallist(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }
        jsonMap.put("allNum",count);
        jsonMap.put("pageCount",pageCount);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    /**
     * 获取医保卡列表
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getMedical")
    public  Map<String, Object> getMedical(SysInsuranceModel model,HttpServletResponse response,
                                           HttpServletRequest request) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        if(model.getVip_id()==null && vip !=null){
            model.setVip_id(vip.getId());
            model.setNum1(model.getPageSize()*(model.getPageNum()-1));
            model.setNum2(model.getPageSize());
        }
        List<SysInsurance> datalist=new ArrayList<SysInsurance>();
        try {
            datalist=sysInsuranceService.getMedical(model);
            for (SysInsurance s : datalist){
                if(s.getBank()==null||s.getBank().equals("null")
                        ||s.getBank().equals("")){
                    s.setBank("无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", datalist);
    }
    /**
     * 修改医保卡
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/updateInsurance.do")
    public Map<String, Object> updateInsurance( GoodsAddressModel model,HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        String mstr="操作";
        try {
            if(model.getId()!=null && model.getId()>0){
                mstr="修改";
                goodsAddressService.updateAddress(model);
            }else{
                mstr="保存";
                if(model.getVip_id()==null && vip!=null){
                    model.setVip_id(vip.getId());
                }
                Integer count=goodsAddressService.countAddresslist(model);
                if(count!=null && count>=20){
                    return setFailureMap(jsonMap, "最多20个地址！", null);
                }else{
                    goodsAddressService.addAddress(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap,mstr+"失败！", null);
        }
        return setSuccessMap(jsonMap, mstr+"成功！", null);
    }
    /**
     * 获取地域列表
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/selectPRDept")
    public Map<String, Object> selectPRDept(SysDeptModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<Map<String,Object>> dataList=sysDeptService.getDeptlist(model);
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/selectKTDept")
    public Map<String, Object> selectKTDept(SysDeptModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<Map<String,Object>> dataList=sysDeptService.selectKTDept(model);
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }
    /**
     * count我兑换的商品
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/countGoodslist")
    public Map<String, Object> countGoodslist(GoodsOrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getVip_id()==null && vip!=null){
            model.setVip_id(vip.getId());
        }
        Integer count=0;
        count= goodsOrderService.countGoodslist(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }
        jsonMap.put("allNum",count);
        jsonMap.put("pageCount",pageCount);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    /**
     * 获取兑换商品列表
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getGoodslist")
    public Map<String, Object> getGoodslist(GoodsOrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getVip_id()==null && vip!=null){
            model.setVip_id(vip.getId());
        }

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Map<String,Object>> dataList=goodsOrderService.getGoodlist(model);
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }
    /**
     * 前台积分列表总数查询
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/goodsDataCount")
    public void goodsDataCount(GoodsModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        if(vip==null){
            //return setFailureMap(jsonMap, "登录超时", null);
            return;
        }

        model.setDeptId(vip.getDeptId());
        model.setState(1);
        Integer count=goodsService.queryByCount(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }


        jsonMap.put("allNum", count);
        jsonMap.put("pageCount", pageCount);
        HtmlUtil.writerJson(response, jsonMap);
        //return setSuccessMap(jsonMap, "操作成功", null);
    }

    /**
     * 前台积分列表
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/goodsDataList")
    public void getinsulist(GoodsModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        if(vip==null){
            return;// setFailureMap(jsonMap, "登录超时", null);

        }

        model.setDeptId(vip.getDeptId());
        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        model.setState(1);
        List<Goods> dataList = goodsService.queryByList(model);

        for (Goods goods : dataList){
            goods.setImgUrl(goods.getImgUrl().split(",")[0]);
            if(goods!=null && goods.getTake_type()==1){
                Integer count = redeemCodeService.queryByGoodsCount(goods.getId(),1);
                goods.setStock(count==null?0:count);
            }
        }

        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
        //return setFailureMap(jsonMap, "登录超时", dataList);
    }
    //积分列表接口
    @ResponseBody
    @RequestMapping("/goodsDataList_jk.do")
    public Map<String, Object> goodsDataList_jk(Integer uid ,GoodsModel model,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = vipService.queryVipById(uid);
        if(vip==null){
            return setFailureMap(jsonMap, "登录超时", null);

        }
        Integration inn = integrationLogService.queryByIntUid(uid);
        model.setDeptId(vip.getDeptId());

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        model.setState(1);
        List<Goods> dataList = goodsService.queryByList(model);
        for (Goods goods : dataList){
            goods.setImgUrl(goods.getImgUrl().split(",")[0]);
            if(goods!=null && goods.getTake_type()==1){
                Integer count = redeemCodeService.queryByGoodsCount(goods.getId(),1);
                goods.setStock(count==null?0:count);
            }
        }
        Integer count=goodsService.queryByCount(model);
        jsonMap.put("my_integral",inn.getIntegration()==null?0:inn.getIntegration());
        jsonMap.put("total",count);
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }


    @Auth(verifyURL=false)
    @RequestMapping("/social.shtml")
    public ModelAndView social( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        return forword("h5/social",context);
    }

    //养老订单提价
    @Auth(verifyURL=false)
    @RequestMapping("/pension.shtml")
    public ModelAndView pension(Integer deptId, HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        if(deptId==null){
            if(vip.getCity()==null){
                context.put("msg","请先完善认证！");
                return forword("h5/pension",context);
            }
            deptId = vip.getCity();
        }

        //查一下这个城市的基数
        InsuranceModel model = new InsuranceModel();
        model.setDeptId(deptId);
        model.setInsuranceType(1);
        Insurance ins = insuranceService.calculatePayment(model);

        if(ins!=null&&ins.getState()==1){
            context.put("ratioClss","display:black;");
        }else{
            context.put("ratioClss","display:none;");
        }

        context.put("base",ins.getExpends_base());
        context.put("month",ins.getEffect_start_time()+"-"+ins.getEffect_end_time());
        try {
            context.put("insuStart",DateUtil.getFomartDate(new Date(),"yyyy/MM"));
            context.put("insuStart_S",DateUtil.getFomartDate(new Date(),"yyyy")+"-01-01");
            context.put("insuStart_E",DateUtil.getFomartDate(new Date(),"yyyy-MM-dd"));
            context.put("insuEnd_S",DateUtil.getFomartDate(new Date(),"yyyy")+"-01-01");
            context.put("insuEnd_E",ins.getAdjustment_time_end().replaceAll("/","-")+"-30");
            context.put("adjustment_time_start",ins.getAdjustment_time_start());
        }catch (Exception e){
            e.printStackTrace();
            context.put("msg","该城市未设置调基月份，该服务不能使用！");
            return forword("h5/social",context);
        }

        return forword("h5/pension",context);
    }

    //商品详情
    @Auth(verifyURL=false)
    @RequestMapping("/goodsInfo.shtml")
    public ModelAndView goodsInfo( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        String id =  request.getParameter("id");
        Goods goods = goodsService.queryById(Integer.parseInt(id));
        if(goods.getTake_type()==1){
            Integer stock = redeemCodeService.queryByGoodsCount(goods.getId(),1);
            goods.setStock(stock==null?0:stock);
        }
        context.put("goods",goods);
        String imgUrl1 = "";
        String imgUrl2 = "";
        String imgUrl3 = "";
        String imgUrl4 = "";
        if(goods.getImgUrl()!=null){
            String[] imgUrls = goods.getImgUrl().split(",");

            for (int i = 1; i <= imgUrls.length;i++){
                String imgUrl = imgUrls[i-1];
                context.put("imgUrl"+i,imgUrl);
            }
        }
        return forword("h5/goods",context);
    }
    //商品订单
    @Auth(verifyURL=false)
    @RequestMapping("/goods_check.shtml")
    public ModelAndView goodsCheck(Integer id, HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);

        Vip vip1 = vipService.queryVipById(vip.getId());
        Goods goods = goodsService.queryById(id);

        GoodsAddressModel addressModel = new GoodsAddressModel();
        addressModel.setIs_default(1);
        addressModel.setVip_id(vip.getId());
        List<GoodsAddress> alist = goodsAddressService.queryByList(addressModel);


        context.put("order_number", Md5Util.createPwd()+(new Date().getTime()));
        context.put("vip",vip1);
        context.put("goods",goods);
        if(goods.getTake_type()==1){
            context.put("takeClass1","display:block;");
            context.put("takeClass2","display:none;");
        }else{
            context.put("takeClass1","display:none;");
            context.put("takeClass2","display:block;");
        }
        context.put("check_address",alist==null||alist.size()==0?false:true);
        context.put("alist",alist);
        return forword("h5/goods_check",context);
    }

    //发货地址设置（接口）
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/check_address.do")
    public Map<String,Object> check_address(Integer uid, HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = getRootMap();
        if(uid==null){
            return setFailureMap(jsonMap, "登录超时", null);
        }
        Vip vip = vipService.queryVipById(uid);

        GoodsAddressModel addressModel = new GoodsAddressModel();
        addressModel.setVip_id(vip.getId());
        List<GoodsAddress> alist = goodsAddressService.queryByList(addressModel);

        jsonMap.put("address",alist);
        return setSuccessMap(jsonMap, "操作成功！", alist);
    }

    //提交养老订单
    @ResponseBody
    @Auth(verifyURL=false)
    @RequestMapping("/addOrder.do")
    public Map<String,Object> addOrder(OrderModel model,Integer uid, Double expends_scale,
                                       HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<>();
        Vip vip = SessionUtils.getVip(request);
        List<Map<String,Object>> datalist = new ArrayList<Map<String,Object>>();
        Map<String,Object> map1=new HashMap<>();
        if(model.getOrderNum()==null||model.getOrderNum().equals("")){
            model.setOrderNum(Md5Util.createPwd()+(new Date().getTime()));
        }
        //判断账户是否登录
        if(vip==null&&uid!=null){
            vip = vipService.queryVipById(uid);//接口
            if(vip==null){
                return setFailureMap(jsonMap, "请先登录", null);
            }

        }else if(vip!=null){
            vip = vipService.queryVipById(vip.getId());//避免从session中得到数据

        }
        if(uid==null){
            vip.setCity(SessionUtils.getVip(request).getCity());
        }
        //判断账户是否实名认证
        if(vip.getAudit()==null||vip.getAudit()!=2){
            jsonMap.put("is_qualif",0);
            //return setFailureMap(jsonMap, "实名认证审核中，不能提交订单！", null);
        }else{
            jsonMap.put("is_qualif",1);
        }
        OrderModel model1=new OrderModel();
        if(model.getDeptId()==null){
            model.setDeptId(vip.getCity());
            model1.setDeptId(vip.getCity());
        }
        if(model.getInsuStart()!=null){
            model.setInsuStart(model.getInsuStart().trim().replaceAll("-","/"));
            model1.setInsuStart(model.getInsuStart().trim().replaceAll("-","/"));
        }else{
            return setFailureMap(jsonMap, "开始时间不能为空！", null);
        }
        if(model.getInsuEnd()!=null){
            model.setInsuEnd(model.getInsuEnd().trim().replaceAll("-","/"));
            model1.setInsuEnd(model.getInsuEnd().trim().replaceAll("-","/"));
        }else{
            return setFailureMap(jsonMap, "结束时间不能为空！", null);
        }
        Integer month = 0;
        month = DateUtil.getMonth(model.getInsuStart(),model.getInsuEnd())+1;
        jsonMap.put("wxts","参保月份为："+model.getInsuStart()+"至"+model.getInsuEnd()+",\n有问题请咨询客服电话："+getRootMap().get("serviceTel"));
        model1.setBase(model.getBase());
        model1.setRatio(model.getRatio());
        model1.setUid(vip.getId());
        model1.setInsuranceType(1);
        List<Order> orlist = insuranceService.queryCodeurlByvipId(model1);
        if(orlist!=null && orlist.size()>0){
            List<Map<String,Object>> datalist1 = new ArrayList<Map<String,Object>>();
            Map<String,Object> map2=new HashMap<>();
            map2.put("orderNum",orlist.get(0).getOrderNum());
            map2.put("payment",orlist.get(0).getPayment());
            map2.put("orderId",orlist.get(0).getId());
            map2.put("code_url",orlist.get(0).getCode_url());
            jsonMap.put("address",1);
            datalist1.add(map2);
            return setSuccessMap(jsonMap, "操作成功！", datalist1);
        }
        Order order = insuranceService.queryByAudit(vip.getId(),1,model.getDeptId());
        if(order!=null&&order.getId()!=null){
            if(order.getAudit() == 1 || order.getAudit() == 2){
                return setFailureMap(jsonMap, "有保单审核中,请稍后参保！", null);
            }
        }

        model.setUid(vip.getId());
        model.setInsuranceType(1);
        model.setSubTime(DateUtil.getCurrDateTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        /*if(model!=null&&model.getInsuEnd().equals("半年")){
            Date dateStart = DateUtil.getDateBetweenByMonth(DateUtil.fromStringToDate("yyyyy/MM",
                    model.getInsuStart().trim().replaceAll("-","/")),5);

            model.setInsuEnd(sdf.format(dateStart));
            month = 6;

        }else if(model!=null&&model.getInsuEnd().equals("全年")){
            Date dateStart1 = DateUtil.getDateBetweenByMonth(DateUtil.fromStringToDate("yyyyy/MM",
                    model.getInsuStart().trim().replaceAll("-","/")),11);
            model.setInsuEnd(sdf.format(dateStart1));
            month = 12;
        }else{*/
        //}

        for(int i=0;i<month;i++){
            Calendar c1 = Calendar.getInstance();
            c1.setTime(DateUtil.fromStringToDate("yyyy/MM",model.getInsuStart()));
            c1.add(Calendar.MONTH, i);
            //参保
            OrderInfo info = insuranceService.queryByInfoAudit(vip.getId(),model.getInsuranceType(),sdf.format(c1.getTime()));
            if(info!=null){
                return setFailureMap(jsonMap, "参保月份重叠！", null);
            }
            //补缴
            OrderInfo info1 = insuranceService.queryByInfoAudit2(vip.getId(),model.getInsuranceType());
            if(info1!=null){
               int month1 = DateUtil.getMonth(info1.getInsuStart(),info1.getInsuEnd())+1;
               for(int j=0;j<month1;j++){
                   Calendar c2 = Calendar.getInstance();
                   c2.setTime(DateUtil.fromStringToDate("yyyy/MM",info1.getInsuStart()));
                   c2.add(Calendar.MONTH, j);

                   if(sdf.format(c2.getTime()).compareTo(sdf.format(c1.getTime()))==0){
                       return setFailureMap(jsonMap, "补缴月份重叠！", null);
                   }
               }

            }

        }
        //每种参保订单只能有一个正在审核
        //insuranceSetService.insuranceDateList()
        //查询一下此人之前的提交的参保月份中  是否包含此次提交的月份


        model.setAudit(0);
        InsuranceModel insModel = new InsuranceModel();
        insModel.setInsuranceType(1);
        insModel.setDeptId(model.getDeptId());
        if(expends_scale==null){
            insModel.setExpends_scale(Double.parseDouble(""+(model.getRatio()*0.01)+""));
        }else{
            insModel.setExpends_scale(expends_scale);
        }

        Insurance ins = insuranceService.calculatePayment(insModel);

        if(model.getRatio()==60&&ins!=null
                &&ins.getState()==1){
            int s = DateUtil.compareDateYM(model.getInsuStart(),ins.getEffect_start_time());
            if(s<0){
                return setFailureMap(jsonMap, "您的参保月份不在缴费基数60%的规定参保月份之内，请重新选择！", null);
            }

            int e = DateUtil.compareDateYM(model.getInsuEnd(),ins.getEffect_end_time());
            if(e>0){
                return setFailureMap(jsonMap, "您的参保月份不在缴费基数60%的规定参保月份之内，请重新选择！", null);
            }

        }

        model.setPayment(Double.parseDouble(ins.getExpends_amount())*month);
        model.setMonthPayment(Double.parseDouble(ins.getExpends_amount()));
        map1.put("orderNum",model.getOrderNum());
        map1.put("payment",model.getPayment());

        try {

            model.setAuditTime(DateUtil.getCurrDateTime());
            insuranceService.addIns(model);
            map1.put("orderId",model.getId());
            jsonMap.put("address",1);
            datalist.add(map1);
        }catch (Exception e){
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", datalist);
    }

    @ResponseBody
    @Auth(verifyURL=false)
    @RequestMapping("/delOrderById.do")
    public Map<String, Object>  delOrderById(Integer id,HttpServletRequest request) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        insuranceService.delOrderById(id);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    //医疗订单验证是否有医保卡
    @ResponseBody
    @Auth(verifyURL=false)
    @RequestMapping("/medicalCheck.do")
    public Map<String, Object> medicalCheck(HttpServletRequest request) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Vip vip = SessionUtils.getVip(request);
        List<SysInsurance> lists = insuranceService.getYBKById(vip.getId(),vip.getCity());
        if(lists==null||lists.size()==0){
            jsonMap.put("msg","当前城市没有添加医保卡，请前往个人中心添加医保卡信息！");
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    //医疗订单页面
    @Auth(verifyURL=false)
    @RequestMapping("/medical.shtml")
    public ModelAndView medical( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        if(vip.getCity()==null){
            context.put("msg","请先完善认证！");
            return forword("h5/medical",context);
        }

        List<SysInsurance> lists = insuranceService.getYBKById(vip.getId(),vip.getCity());
        if(lists==null||lists.size()==0){
            context.put("msg","当前城市没有添加医保卡，请前往个人中心添加医保卡信息！");
            return null;
        }
        SysDept dept = sysDeptService.queryByDept(vip.getCity());
        //查一下这个城市的基数
        InsuranceModel model = new InsuranceModel();
        model.setDeptId(vip.getCity());
        model.setInsuranceType(2);
        Insurance ins = insuranceService.calculatePayment(model);
        context.put("base",ins.getExpends_base());
        context.put("insuStart",DateUtil.getFomartDate(new Date(),"yyyy/MM"));
        context.put("insuStart_S","2010-01-01");
        context.put("insuStart_E",DateUtil.getFomartDate(new Date(),"yyyy-MM-dd"));
        context.put("insuEnd_S",DateUtil.getFomartDate(new Date(),"yyyy-")+"01-01");
        context.put("insuEnd_E",ins.getAdjustment_time_end().replaceAll("/","-")+"-30");
        context.put("adjustment_time_start",ins.getAdjustment_time_start());
        return forword("h5/medical",context);
    }

    //提交医疗订单
    @ResponseBody
    @Auth(verifyURL=false)
    @RequestMapping("/addYiLiaoOrder.do")
    public Map<String,Object> addYiLiaoOrder(OrderModel model,Integer uid,Double expends_scale,
                                             HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<>();
        Vip vip = SessionUtils.getVip(request);
        List<Map<String,Object>> datalist = new ArrayList<Map<String,Object>>();
        Map<String,Object> map1=new HashMap<>();
        if(model.getOrderNum()==null||model.getOrderNum().equals("")){
            model.setOrderNum(Md5Util.createPwd()+(new Date().getTime()));
        }

        //判断账户是否登录
        if(vip==null&&uid!=null){
            vip = vipService.queryVipById(uid);//接口
            if(vip==null){
                return setFailureMap(jsonMap, "请先登录", null);
            }

        }else if(vip!=null){
            vip = vipService.queryVipById(vip.getId());//避免从session中得到数据
        }
        if(uid==null){
            vip.setCity(SessionUtils.getVip(request).getCity());
        }
        //判断账户是否实名认证
        if(vip.getAudit()==null||vip.getAudit()!=2){
            jsonMap.put("is_qualif",0);
            //return setFailureMap(jsonMap, "实名认证审核中，不能提交订单！", null);
        }else{
            jsonMap.put("is_qualif",1);
        }
        OrderModel model1=new OrderModel();
        if(model.getDeptId()==null){
            model.setDeptId(vip.getCity());
            model1.setDeptId(vip.getCity());
        }
        if(model.getInsuStart()!=null){
            model.setInsuStart(model.getInsuStart().trim().replaceAll("-","/"));
            model1.setInsuStart(model.getInsuStart().trim().replaceAll("-","/"));
        }else{
            return setFailureMap(jsonMap, "开始时间不能为空！", null);
        }
        if(model.getInsuEnd()!=null){
            model.setInsuEnd(model.getInsuEnd().trim().replaceAll("-","/"));
            model1.setInsuEnd(model.getInsuEnd().trim().replaceAll("-","/"));
        }else{
            return setFailureMap(jsonMap, "结束时间不能为空！", null);
        }
        Integer month = 0;
        month = DateUtil.getMonth(model.getInsuStart(),model.getInsuEnd())+1;
        jsonMap.put("wxts","参保月份为："+model.getInsuStart()+"至"+model.getInsuEnd()+",\n有问题请咨询客服电话："+getRootMap().get("serviceTel"));
        model1.setBase(model.getBase());
        model1.setRatio(model.getRatio());
        model1.setUid(vip.getId());
        model1.setInsuranceType(2);
        List<Order> orlist = insuranceService.queryCodeurlByvipId(model1);
        if(orlist!=null && orlist.size()>0){
            List<Map<String,Object>> datalist1 = new ArrayList<Map<String,Object>>();
            Map<String,Object> map2=new HashMap<>();
            map2.put("orderNum",orlist.get(0).getOrderNum());
            map2.put("payment",orlist.get(0).getPayment());
            map2.put("orderId",orlist.get(0).getId());
            map2.put("code_url",orlist.get(0).getCode_url());
            jsonMap.put("address",1);
            datalist1.add(map2);
            return setSuccessMap(jsonMap, "操作成功！", datalist1);
        }
        Order order = insuranceService.queryByAudit(vip.getId(),2,model.getDeptId());
        if(order!=null&&order.getId()!=null){
            if(order.getAudit() == 1 || order.getAudit() == 2){
                return setFailureMap(jsonMap, "有保单审核中,请稍后参保！", null);
            }
        }
        model.setUid(vip.getId());
        model.setInsuranceType(2);
        model.setSubTime(DateUtil.getCurrDateTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
       /* if(model!=null&&model.getInsuEnd().equals("半年")){
            Date dateStart = DateUtil.getDateBetweenByMonth(DateUtil.fromStringToDate("yyyyy/MM",
                    model.getInsuStart().replaceAll("-","/")),6);


            model.setInsuEnd(sdf.format(dateStart));
            month = 6;
        }else if(model!=null&&model.getInsuEnd().equals("全年")){
            Date dateStart1 = DateUtil.getDateBetweenByMonth(DateUtil.fromStringToDate("yyyyy/MM",
                    model.getInsuStart().replaceAll("-","/")),12);
            model.setInsuEnd(sdf.format(dateStart1));
            month = 12;
        }else{*/
        //}
        for(int i=0;i<month;i++){
            Calendar c1 = Calendar.getInstance();
            c1.setTime(DateUtil.fromStringToDate("yyyy/MM",model.getInsuStart()));
            c1.add(Calendar.MONTH, i);
            OrderInfo info = insuranceService.queryByInfoAudit(vip.getId(),model.getInsuranceType(),sdf.format(c1.getTime()));
            if(info!=null){
                return setFailureMap(jsonMap, "保单月份重叠！", null);
            }
            //补缴
            OrderInfo info1 = insuranceService.queryByInfoAudit2(vip.getId(),model.getInsuranceType());
            if(info1!=null){
                int month1 = DateUtil.getMonth(info1.getInsuStart(),info1.getInsuEnd())+1;
                for(int j=0;j<month1;j++){
                    Calendar c2 = Calendar.getInstance();
                    c2.setTime(DateUtil.fromStringToDate("yyyy/MM",info1.getInsuStart()));
                    c2.add(Calendar.MONTH, j);

                    if(sdf.format(c2.getTime()).compareTo(sdf.format(c1.getTime()))==0){
                        return setFailureMap(jsonMap, "补缴月份重叠！", null);
                    }
                }

            }

        }
        model.setAudit(0);
        InsuranceModel insModel = new InsuranceModel();
        insModel.setInsuranceType(1);
        insModel.setDeptId(model.getDeptId());
        if(expends_scale==null){
            insModel.setExpends_scale(Double.parseDouble((model.getRatio()/100)+""));
        }else{
            insModel.setExpends_scale(expends_scale);
        }

        Insurance ins = insuranceService.calculatePayment(insModel);
        model.setPayment(Double.parseDouble(ins.getExpends_amount())*month);
        model.setMonthPayment(Double.parseDouble(ins.getExpends_amount()));
        map1.put("orderNum",model.getOrderNum());
        map1.put("payment",model.getPayment());
        try {
            model.setAuditTime(DateUtil.getCurrDateTime());
            insuranceService.addIns(model);
            map1.put("orderId",model.getId());
            jsonMap.put("address",1);
            datalist.add(map1);
        }catch (Exception e){
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", datalist);
    }
    //查询保险订单
    @Auth(verifyURL=false)
    @RequestMapping("/check.shtml")
    public ModelAndView check( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        Vip vip = SessionUtils.getVip(request);
        OrderModel model = new OrderModel();
        model.setUid(vip.getId());

        try {
            List<Order> orderList = insuranceSetService.insuranceDataCheck(model);
            if(orderList!=null&&orderList.size()>0){
                for(Order o : orderList){
                    OrderModel omodel = new OrderModel();
                    omodel.setInsuranceType(o.getInsuranceType());
                    omodel.setUid(o.getUid());
                    List<Order> orderList1 = insuranceSetService.insuranceDataType(omodel);
                    if(orderList1!=null&&orderList1.size()!=0){
                        for (Order o1 : orderList1){
                            //是否正常缴纳 判断用户当月//以后是否有参保订单
                            OrderInfo info = insuranceService.queryByLastPay(o1.getOrderNum());
                            if(info!=null){

                                String now = DateUtil.getFomartDate(new Date(),"yyyy/MM");
                                Integer insCyle = 0;
                                if(info.getLastInsuEnd()!=null&&!info.getLastInsuEnd().equals("")
                                        &&info.getLastInseuranceCycle()!=null&&!info.getLastInseuranceCycle().equals("")){
                                    insCyle = DateUtil.compareDateYM(info.getLastInsuEnd(),info.getLastInseuranceCycle());
                                    o.setLastTime(insCyle>0?info.getLastInsuEnd():info.getLastInseuranceCycle());
                                }else{
                                    if(info.getLastInsuEnd()==null||info.getLastInsuEnd().equals("")){
                                        o.setLastTime(info.getLastInseuranceCycle());
                                    }
                                    if(info.getLastInseuranceCycle()==null||info.getLastInseuranceCycle().equals("")){
                                        o.setLastTime(info.getLastInsuEnd());
                                    }
                                }
                                if(info.getStatus()==1){
                                    if(o1.getAudit() == 6){
                                        int compre = DateUtil.compareDateYM(o1.getInsuEnd(),now);
                                        if(compre>=0){
                                            o.setAuditCh_zn("正常");
                                            break;
                                        }else{
                                            o.setAuditCh_zn("断缴");
                                        }
                                    }else{
                                        o.setAuditCh_zn("断缴");
                                    }
                                }else if(info.getStatus()==2){
                                    if(o1.getAudit() == 6){
                                        int compre = DateUtil.compareDateYM(o1.getInsuEnd(),now);
                                        if(compre>=0){
                                            o.setAuditCh_zn("正常");
                                            break;
                                        }else{
                                            o.setAuditCh_zn("断缴");
                                        }
                                    }else{
                                        o.setAuditCh_zn("断缴");
                                    }

                                }else{
                                    o.setAuditCh_zn("断缴");
                                }

                            }
                        }
                    }else{
                        o.setAuditCh_zn("断缴");
                        o.setLastTime("无");
                    }
                }
            }
            context.put("name",vip.getName());
            List<SysInsurance> lists = insuranceService.getYBKById(vip.getId(),vip.getCity());
            if(lists!=null&&lists.size()>0){
                context.put("insuranceNum",lists.get(0).getInsuranceNum()==null||lists.get(0).getInsuranceNum().equals("")
                        ||lists.get(0).getInsuranceNum().equals("null")?"无":lists.get(0).getInsuranceNum());//社保号
            }else{
                context.put("insuranceNum","无");//社保号
            }

            context.put("vip",vip);
            context.put("orders",orderList);
            //jsonMap.put("vip",vip);
            return forword("h5/check",context);
        }catch (Exception e){
            e.printStackTrace();
            return forword("h5/check",context);
        }
    }

    //查询保险订单
    @Auth(verifyURL=false)
    @RequestMapping("/check_detail.shtml")
    public ModelAndView checkDetail(OrderInfoModel model,
                                    HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,Object>  context = getRootMap();
        context.put("insuranceType",model.getInsuranceType());
        return forword("h5/check_detail",context);
    }


    /**
     * 查询保险订单详情列表总数查询
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/check_detailCount.do")
    public void checkDetailCount(OrderInfoModel model, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        if(vip==null){
            //return setFailureMap(jsonMap, "登录超时", null);
            return;
        }

        model.setUid(vip.getId());
        model.setDeptId(vip.getCity());
        Integer count=insuranceService.countUserlist1(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }

        jsonMap.put("allNum", count);
        jsonMap.put("pageCount", pageCount);
        HtmlUtil.writerJson(response, jsonMap);
        //return setSuccessMap(jsonMap, "操作成功", null);
    }
    //查询保险订单详情列表
    @Auth(verifyURL=false)
    @RequestMapping("/check_detailList.do")
    public void check_detailList(OrderInfoModel model, HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        if(vip==null){
            return;// setFailureMap(jsonMap, "登录超时", null);

        }
        model.setUid(vip.getId());
        model.setDeptId(vip.getCity());
        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<OrderInfo> dataList = insuranceService.getUserlist1(model);

        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    //查询保险订单详情列表（接口）
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/check_detailList_JK.do")
    public Map<String,Object> check_detailList(Integer uid,OrderInfoModel model, HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Vip vip = SessionUtils.getVip(request);
        if(uid==null){
            return setFailureMap(jsonMap, "登录超时！", null);
        }else{
            vip = vipService.queryVipById(uid);
        }
        model.setUid(uid);
        model.setDeptId(vip.getCity());
        List<OrderInfo> dataList = insuranceService.getUserlist1(model);
        for (OrderInfo info : dataList){
            if(info!=null&&info.getStatus()!=1){
                info.setInseuranceCycle(info.getInsuStart()+"-"+info.getInsuEnd());
            }
        }
        Integer count=insuranceService.countUserlist1(model);
        jsonMap.put("total",count);
        return setSuccessMap(jsonMap, "操作成功！", dataList);
    }
    //社保查询（接口）
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/orderCheck.do")
    public Map<String,Object> orderCheck(Integer uid,Integer deptId,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Vip vip = SessionUtils.getVip(request);
        OrderModel model = new OrderModel();
        try {
            if(uid!=null){
                model.setDeptId(deptId);
                vip = vipService.queryVipById(uid);
            }else{
                model.setDeptId(vip.getCity());
                return setSuccessMap(jsonMap, "操作失败！", null);
            }
            model.setUid(vip.getId());
            List<Order> orderList = insuranceSetService.insuranceDataCheck(model);
            if(orderList!=null&&orderList.size()>0){
                for(Order o : orderList){
                    OrderModel omodel = new OrderModel();
                    omodel.setInsuranceType(o.getInsuranceType());
                    omodel.setUid(o.getUid());
                    if(uid!=null && deptId!=null){
                        omodel.setDeptId(deptId);
                    }else{
                        omodel.setDeptId(vip.getCity());
                    }
                    List<Order> orderList1 = insuranceSetService.insuranceDataType(omodel);
                    if(orderList1!=null&&orderList1.size()!=0){
                        for (Order o1 : orderList1){
                            //是否正常缴纳 判断用户当月//以后是否有参保订单
                            OrderInfo info = insuranceService.queryByLastPay(o1.getOrderNum());
                            if(info!=null){

                                String now = DateUtil.getFomartDate(new Date(),"yyyy/MM");
                                Integer insCyle = 0;
                                if(info.getLastInsuEnd()!=null&&!info.getLastInsuEnd().equals("")
                                        &&info.getLastInseuranceCycle()!=null&&!info.getLastInseuranceCycle().equals("")){
                                    insCyle = DateUtil.compareDateYM(info.getLastInsuEnd(),info.getLastInseuranceCycle());
                                    o.setLastTime(insCyle>0?(info.getLastInsuEnd()==null||info.getLastInsuEnd().equals("null")?"无":info.getLastInsuEnd())
                                            :(info.getLastInseuranceCycle()==null||info.getLastInseuranceCycle().equals("null")?"无":info.getLastInseuranceCycle()));
                                }else{
                                    if(info.getLastInsuEnd()==null||info.getLastInsuEnd().equals("")){
                                        o.setLastTime(info.getLastInseuranceCycle()==null||info.getLastInseuranceCycle().equals("null")?"无":info.getLastInseuranceCycle());
                                    }
                                    if(info.getLastInseuranceCycle()==null||info.getLastInseuranceCycle().equals("")){
                                        o.setLastTime(info.getLastInsuEnd()==null||info.getLastInseuranceCycle().equals("null")?"无":info.getLastInsuEnd());
                                    }
                                }


                                if(info.getStatus()==1){
                                    if(o1.getAudit() == 6){
                                        int compre = DateUtil.compareDateYM(o1.getInsuEnd(),now);
                                        if(compre>=0){
                                            o.setAuditCh_zn("正常");
                                            break;
                                        }else{
                                            o.setAuditCh_zn("断缴");
                                        }
                                    }else{
                                        o.setAuditCh_zn("断缴");
                                    }
                                }else if(info.getStatus()==2){
                                    if(o1.getAudit() == 6){
                                        int compre = DateUtil.compareDateYM(o1.getInsuEnd(),now);
                                        if(compre>=0){
                                            o.setAuditCh_zn("正常");
                                            break;
                                        }else{
                                            o.setAuditCh_zn("断缴");
                                        }
                                    }else{
                                        o.setAuditCh_zn("断缴");
                                    }

                                }else{
                                    o.setAuditCh_zn("断缴");
                                }

                            }else{
                                o.setAuditCh_zn("断缴");
                                o.setLastTime("无");
                            }
                        }
                    }else{
                        o.setAuditCh_zn("断缴");
                        o.setLastTime("无");
                    }
                }
            }
            jsonMap.put("name",vip.getName());
            List<SysInsurance> lists = insuranceService.getYBKById(vip.getId(),vip.getCity());
            if(lists!=null&&lists.size()>0){
                jsonMap.put("insuranceNum",lists.get(0).getInsuranceNum()==null||lists.get(0).getInsuranceNum().equals("")
                        ||lists.get(0).getInsuranceNum().equals("null")?"无":lists.get(0).getInsuranceNum());//社保号
            }else{
                jsonMap.put("insuranceNum","无");//社保号
            }
            jsonMap.put("vip",vip);
            jsonMap.put("orders",orderList);
            return setSuccessMap(jsonMap, "操作成功！", orderList);
        }catch (Exception e){
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }
    /**
     * 开通城市列表
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/getktCity.do")
    public Map<String,Object> getktCity(HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        List<Map<String,Object>> dataList=sysDeptService.getktCity();
        return setFailureMap(jsonMap, "操作成功！", dataList);
    }
    //缴费比例（接口）
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/getBase.do")
    public Map<String,Object> getBase(Integer deptId, HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Vip vip = SessionUtils.getVip(request);
        OrderModel model = new OrderModel();
        try {
            if(deptId==null){
                return setFailureMap(jsonMap, "did为空！", null);
            }else{
                List<Map<String,Object>> datalist= insuranceService.getbaseByDeptId(deptId);
                String rastr="";
                String yistr="";
                if(datalist!=null && datalist.size()>0){
                    for(int i=0;i<datalist.size();i++){
                        Map<String,Object> map=datalist.get(i);
                        String str=Double.parseDouble(map.get("expends_scale")+"")*100+"";
                        if(map.get("insuranceType").equals(1)){
                            jsonMap.put("yl_start_time",DateUtil.getFomartDate(new Date(),"yyyy-")+"01");
                            jsonMap.put("yl_end_time",DateUtil.getFomartDate(new Date(),"yyyy-MM"));
                            jsonMap.put("custom_yl_start_time",DateUtil.getFomartDate(new Date(),"yyyy-")+"01");
                            jsonMap.put("custom_yl_end_time",map.get("adjustment_time_end").toString().replaceAll("/","-"));
                            jsonMap.put("base",map.get("expends_base"));
                            jsonMap.put("state",map.get("state"));
                            jsonMap.put("yl_adjustment_zh","本市基数调整月份为:"+map.get("adjustment_time_start"));
                            if(map.get("state")==2){

                                if(!map.get("expends_scale").equals(0.6)&&map.get("expends_scale")!=0.6){
                                    if(i==datalist.size()-1){
                                        rastr+=Integer.parseInt(str.substring(0,str.length()-2))+"%";
                                    }else{
                                        rastr+=Integer.parseInt(str.substring(0,str.length()-2))+"%;";
                                    }
                                }
                            }else{
                                jsonMap.put("scale_zh","60%的缴费基数只能缴纳参保月份在"+map.get("effect_start_time")+"-"+map.get("effect_end_time")+"的保险");

                                if(i==datalist.size()-1){
                                    rastr+=Integer.parseInt(str.substring(0,str.length()-2))+"%";
                                }else{
                                    rastr+=Integer.parseInt(str.substring(0,str.length()-2))+"%;";
                                }
                            }
                            jsonMap.put("ylexplain","1.选择周期包含当前月份之前的月份，则之前的月份按补缴一次性缴纳；\n " +
                                                          "2.养老保险可补缴月份为当年度月份，不能跨年补缴；\n " +
                                                          "3.根据国家政策每年的参保基数都会调整，参保周期的结束月份会按照参保基数调整月份而更改，具体请以每年\n" +
                                                            "参保基数相关政策文件为准\n " +
                                                          "4.参保月份大于等于6个月的订单可以获得积分奖励，缴费基数越大，缴纳的费用越高;");
                        }else{
                            jsonMap.put("yilbase",map.get("expends_base"));
                            jsonMap.put("yb_start_time","2010-01");
                            jsonMap.put("yb_end_time",DateUtil.getFomartDate(new Date(),"yyyy-MM"));
                            jsonMap.put("custom_yb_start_time",DateUtil.getFomartDate(new Date(),"yyyy-")+"01");
                            jsonMap.put("custom_yb_end_time",map.get("adjustment_time_end").toString().replaceAll("/","-"));
                            jsonMap.put("yb_adjustment_zh","本市基数调整月份为:"+map.get("adjustment_time_start"));
                            if(i==datalist.size()-1){
                                yistr+=Integer.parseInt(str.substring(0,str.length()-2))+"%";
                            }else{
                                yistr+=Integer.parseInt(str.substring(0,str.length()-2))+"%;";
                            }
                            jsonMap.put("ybexplain","1.选择周期包含当前月份之前的月份，则之前的月份按补缴一次性缴纳；\n " +
                                    "2.根据国家政策每年的参保基数都会调整，参保周期的结束月份会按照参保基数调整月份来更改，具体请以每年\n" +
                                    "参保基数相关政策文件为准\n " +
                                    "3.参保月份大于等于6个月的订单可以获得积分奖励，缴费基数越大，缴纳的费用越高;");
                        }
                    }
                }
                String ratio[]=rastr.split(";");
                jsonMap.put("ratio",ratio==null?"":ratio);//社保号
                String[] yilratio =yistr.split(";");
                jsonMap.put("yilratio",yilratio==null?"":yilratio);//社保号

            }
            return setSuccessMap(jsonMap, "操作成功！", null);

        }catch (Exception e){
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }


    //有无支付密码接口）
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/hasPayPwd.do")
    public Map<String,Object> hasPayPwd(Integer uid,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Vip vip = SessionUtils.getVip(request);
        OrderModel model = new OrderModel();
        try {
            if(uid==null&&vip==null){
                return setFailureMap(jsonMap, "操作失败！", null);
            }
            if(uid != null){
                vip = vipService.queryVipById(uid);
            }else{
                vip = vipService.queryVipById(vip.getId());
            }
            boolean flag = false;
            if(vip!=null&&vip.getPay_pwd()!=null){
                flag = true;
            }
            jsonMap.put("flag",flag);
            return setSuccessMap(jsonMap, "操作成功！", null);
        }catch (Exception e){
            jsonMap.put("flag",false);
            return setFailureMap(jsonMap, "您没有支付密码，请先设置！", null);
        }

    }

    //兑换商品接口）
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/enterExchange.do")
    public Map<String,Object> enterExchange(Integer uid,Integer deptId,String pay_pwd,
                                            GoodsOrderModel model,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Vip vip = SessionUtils.getVip(request);
        try {
            if(uid==null&&vip==null){
                return setFailureMap(jsonMap, "操作失败！", null);
            }
            if(uid != null){
                vip = vipService.queryVipById(uid);
            }else{
                vip = vipService.queryVipById(vip.getId());
            }
            System.out.println(vip.getPay_pwd()+"=============="+pay_pwd);
            if(!pay_pwd.equals(vip.getPay_pwd())){
                return setFailureMap(jsonMap, "密码错误！", null);
            }

            Goods g1 = goodsService.queryById(model.getGoods_id());

            if(g1!=null){
                //如果是接口的话
                if(model.getTake_type()==null){
                    model.setTake_type(g1.getTake_type());
                }
                if(g1.getExchange()!=null&&g1.getExchange()==2){
                    GoodsOrderModel gmodel1 = new GoodsOrderModel();
                    gmodel1.setGoods_id(model.getGoods_id());
                    gmodel1.setVip_id(vip.getId());
                    List<GoodsOrder> golist = goodsOrderService.getGoodlist(gmodel1);
                    if(golist!=null&&golist.size()>0){
                        return setFailureMap(jsonMap, "该商品只能兑换一次！", null);
                    }
                }
            }

            Integration integration =  integrationLogService.queryByIntUid(vip.getId());
            int integral = integration.getIntegration() - model.getIntegral();

            IntegrationModel imodel = new IntegrationModel();


            imodel.setUid(vip.getId());

            model.setGoods_id(g1.getId());
            model.setGoods_number(g1.getNumber());
            model.setDeptId(vip.getDeptId());
            model.setExchange_number(vip.getTel());
            model.setExchange_time(new Date());
            model.setWrite_off_state(1);
            model.setVip_id(vip.getId());
            model.setVname(vip.getName());
            model.setCreate_time(new Date());
            model.setGoods_name(g1.getGname());
            model.setOrder_number(Md5Util.createPwd()+(new Date().getTime()));
            model.setNums(model.getNums()==null?1:model.getNums());
            model.setIntegral(g1.getIntegral());

            /*VipModel vipModel = new VipModel();*/


            if(integral<0){
                //integrationLogService.updateIntegration(-integration.getIntegration(),vip.getId(),DateUtil.getCurrDateTime());
                return setFailureMap(jsonMap, "您的积分不足，不能兑换该商品！", null);
            }else{

                //检查库存
                if(model.getTake_type()==1){

                    RedeemCode rc = redeemCodeService.getReddmCode(model.getGoods_id());
                    if(rc==null){
                        return setFailureMap(jsonMap, "库存不足！", null);
                    }
                    RedeemCodeModel rcmodel = new RedeemCodeModel();
                    rcmodel.setStatus(2);
                    rcmodel.setRedeem_code(rc.getRedeem_code());
                    rcmodel.setExchange_time(DateUtil.getCurrDateTime());
                    redeemCodeService.updateRedmmCode(rcmodel);
                    model.setRedeem_code(rc.getRedeem_code());
                }else if(model.getTake_type()==2){
                    Goods g = goodsService.queryById(model.getGoods_id());
                    if(g==null){
                        return setFailureMap(jsonMap, "库存不足！", null);

                    }else{
                        if(g.getStock()<=0){
                            return setFailureMap(jsonMap, "库存不足！", null);
                        }
                        //接口
                        if(model.getDeliver_goods_address()==null
                                ||model.getDeliver_goods_address().equals("")){

                            GoodsAddress gas = goodsAddressService.queryByAddressId(model.getAddress_id());

                            model.setOnsignee_name(gas.getPerson_name());
                            model.setDeliver_goods_tel(gas.getPerson_tel());
                            model.setDeliver_goods_address(gas.getProvince_zh()+gas.getCity_zh()+gas.getArea_zh()+gas.getAddress());
                        }
                    }
                }
                //检查库存结束

                IntegrationModel ilogmodel = new IntegrationModel();
                ilogmodel.setUid(vip.getId());
                ilogmodel.setAddTime(DateUtil.getCurrDateTime());
                ilogmodel.setIntegration(model.getIntegral());
                ilogmodel.setType(2);
                integrationLogService.addInLog(ilogmodel);
                System.out.println(model.getNums()==null?1:model.getNums()+"======"+model.getIntegral()+"====="+vip.getId());
                integrationLogService.updateIntegration(-(model.getNums()==null?1:model.getNums())*model.getIntegral(),vip.getId(),DateUtil.getCurrDateTime());
            }

            goodsOrderService.addGoodsOrder(model);
            //减库存
            if(model.getTake_type()==2){
                goodsService.updateStock(-(model.getNums()==null?1:model.getNums()),model.getGoods_id());
            }
            return setSuccessMap(jsonMap, "操作成功！", model);
        }catch (Exception e){
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }

    /**
     * 计算支付金额
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/calculatePayment.do")
    public Map<String,Object> calculatePayment(Integer deptId,String startTime,String endTime,
                                               InsuranceModel model,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Vip vip = SessionUtils.getVip(request);

        if(deptId==null&&vip==null){
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        if(deptId != null){
            model.setDeptId(deptId);
        }
        Insurance insurance = insuranceService.calculatePayment(model);
       /* if(insurance!=null&&insurance.getInsuranceType()==1
                &&insurance.getExpends_scale().equals("0.6")){
            int x = DateUtil.compareDateYM(startTime.replaceAll("-","/"),insurance.getAdjustment_time_start()+"的保险");
            if(x<0){
                jsonMap.put("code","6");
                jsonMap.put("msg","60%的缴费基数只能缴纳参保月份在"+insurance.getAdjustment_time_start()+"-"+insurance.getAdjustment_time_end()+"的保险");
                return setFailureMap(jsonMap, "操作失败！", null);
            }
            int y = DateUtil.compareDateYM(endTime.replaceAll("-","/"),insurance.getAdjustment_time_end());
            if(y>0){
                jsonMap.put("code","6");
                jsonMap.put("msg","60%的缴费基数只能缴纳参保月份在"+insurance.getAdjustment_time_start()+"-"+insurance.getAdjustment_time_end());
                return setFailureMap(jsonMap, "操作失败！", null);
            }
        }*/
        Integer months = DateUtil.getMonth(startTime.replaceAll("-","/"),endTime.replaceAll("-","/"))+1;
        double expends_amount_sum = months*Double.parseDouble(insurance.getExpends_amount());
        double integral = 0.0;
        if(months<6){
            integral = 0;
        }else if(months>=6){
            integral = months*Double.parseDouble(insurance.getExpends_amount());
        }
        double expends_amount = Double.parseDouble(insurance.getExpends_amount());
        jsonMap.put("months",months==null?0:months);
        jsonMap.put("expends_amount","每月缴费"+expends_amount+"元");
        jsonMap.put("expends_amount_sum",expends_amount_sum);
        jsonMap.put("integral",integral);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/countMessageList.do")
    public Map<String,Object> countMessageList(MessageModel model,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Integer count=0;
        count= messageService.queryByCount(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }
        jsonMap.put("allNum",count);
        jsonMap.put("pageCount",pageCount);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    /**
     * 消息接口
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/getMessageList.do")
    public Map<String,Object> getMessageList(MessageModel model,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Message> mlist = messageService.queryByList(model);
        Integer count = messageService.queryByCount(model);
        Integer pageCount=0;
        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }
        jsonMap.put("allNum",count);
        jsonMap.put("pageCount",pageCount);
        return setSuccessMap(jsonMap, "操作成功！", mlist);
    }
    /**
     * 消息详情接口
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/getMessageInfo.do")
    public Map<String,Object> getMessageInfo(Integer message_id,Integer user_id,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        if(message_id==null){
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        Message message = messageService.queryById(message_id);
        if(message==null){
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        if(message.getUnread()==1){
            messageService.updateUnread(message_id,user_id);
        }
        jsonMap.put("messageInfo",message);

        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    /**
     * 全部已读
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/allUnread.do")
    public Map<String,Object> allUnread(Integer user_id,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        if(user_id==null){
            return setFailureMap(jsonMap, "操作失败！", null);
        }

        messageService.updateUnread(null,user_id);

        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    /**
     * 微信小程序支付获取openid
     * @param code
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/wxXPay.do")
    public JSONObject wxXPay(String code,HttpServletRequest request)throws Exception{
        String path="https://api.weixin.qq.com/sns/jscode2session?appid="+ConfigUtil.APPID+"&secret="+ConfigUtil.APPSECRET+"&js_code="+code+"&grant_type=authorization_code";
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(path);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("GET");

            httpUrlConn.connect();

            // 当有数据需要提交时
            /*if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }*/

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }
    /**
     * 微信支付(调用统一下单接口)
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/wxPrePay.do")
    public Map<String,Object> wxPrePay(Order order,String body,String trade_type,String openid,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        List<Map<String,Object>> datalst=new ArrayList<Map<String,Object>>();
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        String nonce_str="";
        for(int i=0;i<15;i++){
            nonce_str+=String.valueOf((int)(Math.random()*9));
        }
        Date date=new Date();
        String out_trade_no=order.getOrderNum();
        //String total_fee=order.getPayment();
        String total_fee="0.01";
        total_fee=(new BigDecimal(total_fee).multiply(new BigDecimal(100))).intValue()+"";
        String spbill_create_ip="123.12.12.123";
        String notify_url="https://www.365linghuo.com/handApp/wxNotify.do";


        parameters.put("appid", ConfigUtil.APPID);
        parameters.put("mch_id",ConfigUtil.MCH_ID);
        parameters.put("nonce_str", nonce_str);
        parameters.put("body", body);
        parameters.put("out_trade_no", out_trade_no); //订单id
        parameters.put("total_fee", total_fee);
        parameters.put("spbill_create_ip",spbill_create_ip);
        parameters.put("notify_url", notify_url);
        parameters.put("trade_type",trade_type);
        if( trade_type.equals("JSAPI") && openid!=null){
            parameters.put("openid",openid);
        }

        String sign = PayCommonUtil.createSign("UTF-8",parameters);
        parameters.put("sign", sign);
        //封装请求参数结束
        String requestXML = PayCommonUtil.getRequestXml(parameters);
        //调用统一下单接口
        String result = PayCommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
        System.out.println("\n"+result);
        try {
            /**统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay**/
            Map<String, Object> map = XMLUtil.doXMLParse(result);
            Date date1=new Date();
            Integer timeStamp=DateUtil.getSjcByDate(date1);
            map.put("timeStamp",timeStamp+"");
            if(map!=null && map.get("code_url")!=null && map.get("code_url").toString().length()>0){
                insuranceService.updateCodeurl(out_trade_no,map.get("code_url")+"");
            }
            datalst.add(map);
             /*SortedMap<Object, Object> parameterMap2 = new TreeMap<Object, Object>();
             parameterMap2.put("appid", ConfigUtil.APPID);
             parameterMap2.put("partnerid", ConfigUtil.MCH_ID);
             parameterMap2.put("prepayid", map.get("prepay_id"));
             parameterMap2.put("package", "Sign=WXPay");
             parameterMap2.put("noncestr", PayCommonUtil.CreateNoncestr());
             //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
             parameterMap2.put("timestamp", Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10)));
             String sign2 = PayCommonUtil.createSign("UTF-8",parameterMap2);
             parameterMap2.put("sign", sign2);
             resultMap.put("code","200");
             resultMap.put("msg",parameterMap2);*/
        } catch (IOException e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", datalst);
    }

    /**
     * 微信支付(异步通知接口)
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/wxNotify.do")
    public Map<String,Object> wxNotify(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        String paymsg="";
        String orderNum="";
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());
        for(Object keyValue : m.keySet()){
            System.out.println(keyValue+"="+m.get(keyValue));
        }
        //过滤空 设置 TreeMap
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        //判断签名是否正确
        String resXml = "";
        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id"); //商户号
                String openid = (String)packageParams.get("openid");  //用户标识
                String out_trade_no = (String)packageParams.get("out_trade_no"); //商户订单号
                orderNum=out_trade_no;
                String total_fee = (String)packageParams.get("total_fee");
                String transaction_id = (String)packageParams.get("transaction_id"); //微信支付订单号
                Order order=financeService.queryOrderByOrderNum(out_trade_no);
               // if(!ConfigUtil.MCH_ID.equals(mch_id)||order==null||new BigDecimal(total_fee).compareTo(new BigDecimal(order.getPayment()).multiply(new BigDecimal(100))) != 0){
                if(!ConfigUtil.MCH_ID.equals(mch_id)||order==null||new BigDecimal(total_fee).compareTo(new BigDecimal("0.01").multiply(new BigDecimal(100))) != 0){
                    //logger.info("支付失败,错误信息：" + "参数错误");
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                    paymsg="支付失败,错误信息:参数错误";
                }else {
                    paymsg="支付成功";
                    if(order.getAudit()==null || order.getAudit().equals(0)){
                        OrderModel model=new OrderModel();
                        model.setAudit(1);
                        Date date=new Date();
                        String auditTime=DateUtil.getPlusTime(date);
                        model.setAuditTime(auditTime);
                        model.setRechargeTime(auditTime);
                        model.setOrderNum(order.getOrderNum());
                        insuranceService.upAuditByOrderNum(model);
                        /*InsuranceModel insModel = new InsuranceModel();
                        insModel.setExpends_scale(Double.parseDouble((order.getRatio()*0.01)+""));
                        insModel.setDeptId(order.getDeptId());
                        insModel.setInsuranceType(order.getInsuranceType());
                        Insurance ins = insuranceService.calculatePayment(insModel);
                        //1 首先判单这个订单是补缴还是参保
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");//可以方便地修改日期格式
                        String nowformat = dateFormat.format(c.getTime());
                        Date nowDate = dateFormat.parse(nowformat);
                        Date insDate = dateFormat.parse(order.getInsuStart());
                        *//*
                          返回1， 说明订单开始时间小于当前时间，该订单包含补缴；
                          返回-1，说明订单开始时间大于当前时间，该订单肯定是参保；
                          返回0， 说明是订单开始时间和结束时间都是当月。*//*
                        int is_bujao = DateUtil.compareDate(nowDate,insDate);

                        //2 如果是补缴 有两种情况

                        if(is_bujao==1){
                            Date insEndDate = dateFormat.parse(order.getInsuEnd());
                            // 2a. bujaoTime>0 都是补缴
                            // 2b. bujaoTime<0 一部分补缴
                            // 2c. bujaoTime==0 说明insuEnd为当月，当月25号下午之前缴费（insuEnd）算参保，之后（insuEnd）算补缴
                            int bujaoTime = DateUtil.compareDate(nowDate,insEndDate);
                            OrderInfoModel imodel = new OrderInfoModel();
                            imodel.setOrderNum(order.getOrderNum());
                            imodel.setUid(order.getUid());
                            imodel.setInsuranceType(order.getInsuranceType());
                            imodel.setInseuranceCycle("");
                            imodel.setInsuStart(order.getInsuStart());
                            imodel.setAuditTime(DateUtil.getNowPlusTime());
                            imodel.setSubTime(order.getSubTime());
                            imodel.setBase(order.getBase());
                            imodel.setRatio(order.getRatio());
                            imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                            imodel.setStatus(2);
                            imodel.setAudit(1);
                            imodel.setDeptId(order.getDeptId());
                            imodel.setIsquit(2);
                            //2b
                            if(bujaoTime==-1||bujaoTime==0){
                                String timestr1=DateUtil.getNowPlusTime();
                                SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                                String timestr2=form.format(new Date())+"-25 17:00:00";
                                if(timestr1.compareTo(timestr2)>0){
                                    imodel.setInsuEnd(dateFormat.format(c.getTime()));
                                    int monthNum = DateUtil.getMonthSpace(order.getInsuStart(),nowformat)+1;

                                    *//*if(bujaoTime==0){//结束时间是当月那么这个合同是
                                        monthNum = DateUtil.getMonthSpace(order.getInsuStart(),nowformat)+1;
                                        System.out.println("当月");
                                    }*//*

                                    imodel.setMonthNum(monthNum);

                                    imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum);
                                    imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                }else{
                                    c.add(Calendar.MONTH, -1);
                                    imodel.setInsuEnd(dateFormat.format(c.getTime()));

                                    int monthNum = DateUtil.getMonthSpace(order.getInsuStart(),nowformat);
                                    imodel.setMonthNum(monthNum);

                                    imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum);
                                    imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                }
                            }else if(bujaoTime==1){
                                imodel.setInsuEnd(order.getInsuEnd());

                                int monthNum = DateUtil.getMonthSpace(order.getInsuStart(),order.getInsuEnd());
                                imodel.setMonthNum(monthNum==0?1:(monthNum+1));

                                imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*(monthNum+1));
                            }
                            //补缴录入
                            insuranceService.addInsInfo(imodel);

                            //剩下的就是参保
                            //拆分 录入
                            String timestr1=DateUtil.getNowPlusTime();
                            SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                            String timestr2=form.format(new Date())+"-25 17:00:00";
                            int cbNum =0;
                            if(timestr1.compareTo(timestr2)>0){
                                cbNum = DateUtil.getMonthSpace1(nowformat,order.getInsuEnd());
                            }else{
                                cbNum = DateUtil.getMonthSpace1(nowformat,order.getInsuEnd())+1;
                            }
                            for(int i=0;i<cbNum;i++){
                                OrderInfoModel model1 = new OrderInfoModel();
                                Calendar c_1 = Calendar.getInstance();
                                c_1.setTime(c.getTime());
                                model1.setOrderNum(order.getOrderNum());
                                model1.setUid(order.getUid());
                                model1.setInsuranceType(order.getInsuranceType());

                                c_1.add(Calendar.MONTH, i+1);
                                if(DateUtil.getMonthSpace1(order.getInsuEnd(),dateFormat.format(c_1.getTime()))>0){
                                   break;
                                }
                                model1.setInseuranceCycle(dateFormat.format(c_1.getTime()));
                                model1.setInsuStart("");
                                model1.setInsuEnd("");
                                model1.setBase(order.getBase());
                                model1.setRatio(order.getRatio());

                                model1.setMonthNum(1);

                                model1.setPayment(Double.parseDouble(ins.getExpends_amount()));

                                model1.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                model1.setStatus(1);
                                model1.setAudit(1);
                                model1.setDeptId(order.getDeptId());
                                model1.setIsquit(2);
                                model1.setSubTime(order.getSubTime());
                                model1.setAuditTime(DateUtil.getNowPlusTime());
                                insuranceService.addInsInfo(model1);
                            }
                        }else if(is_bujao==-1 || is_bujao==0){
                            Calendar c1 = Calendar.getInstance();
                            int monNum =0;
                            int monthNum1 =0;
                            int cbNum =0;
                            if(is_bujao==0){
                                OrderInfoModel imodel = new OrderInfoModel();
                                imodel.setOrderNum(order.getOrderNum());
                                imodel.setUid(order.getUid());
                                imodel.setInsuranceType(order.getInsuranceType());
                                imodel.setInseuranceCycle("");
                                imodel.setInsuStart(order.getInsuStart());
                                imodel.setAuditTime(DateUtil.getNowPlusTime());
                                imodel.setSubTime(order.getSubTime());
                                imodel.setBase(order.getBase());
                                imodel.setRatio(order.getRatio());
                                imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                imodel.setStatus(2);
                                imodel.setAudit(1);
                                imodel.setDeptId(order.getDeptId());
                                imodel.setIsquit(2);
                                String timestr1=DateUtil.getNowPlusTime();
                                SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                                String timestr2=form.format(new Date())+"-25 17:00:00";
                                if(timestr1.compareTo(timestr2)>0){
                                    c1.add(Calendar.MONTH, 1);
                                    imodel.setInsuEnd(order.getInsuStart());

                                    monthNum1 = 1;

                                    imodel.setMonthNum(monthNum1);

                                    imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum1);
                                    imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                    monNum = DateUtil.getMonthSpace(dateFormat.format(c1.getTime()),order.getInsuEnd());
                                    cbNum = DateUtil.getMonthSpace1(dateFormat.format(c1.getTime()),order.getInsuEnd());
                                    insuranceService.addInsInfo(imodel);
                                }else{
                                    monNum = DateUtil.getMonthSpace(order.getInsuStart(),order.getInsuEnd());
                                    cbNum=monNum==0?1:monNum;
                                }
                            }else{
                                monNum = DateUtil.getMonthSpace(order.getInsuStart(),order.getInsuEnd());
                                cbNum=monNum==0?1:monNum;
                            }
                            if(cbNum>0){
                                for(int i=0;i<=monNum;i++){
                                    OrderInfoModel model2 = new OrderInfoModel();

                                    model2.setOrderNum(order.getOrderNum());
                                    model2.setUid(order.getUid());
                                    model2.setInsuranceType(order.getInsuranceType());
                                    model2.setInsuranceNature(order.getInsuranceNature());
                                    c1.setTime(dateFormat.parse(order.getInsuStart()));
                                    c1.add(Calendar.MONTH, i+monthNum1);
                                    model2.setInseuranceCycle(dateFormat.format(c1.getTime()));
                                    model2.setInsuStart("");
                                    model2.setInsuEnd("");

                                    model2.setAuditTime(DateUtil.getNowPlusTime());
                                    model2.setSubTime(order.getSubTime());
                                    model2.setBase(order.getBase());
                                    model2.setRatio(order.getRatio());

                                    model2.setMonthNum(1);

                                    model2.setPayment(Double.parseDouble(ins.getExpends_amount()));

                                    model2.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                    model2.setStatus(1);
                                    model2.setAudit(1);
                                    model2.setDeptId(order.getDeptId());
                                    model2.setIsquit(2);
                                    insuranceService.addInsInfo(model2);
                                }
                            }
                        }

                        IntegrationModel model1=new IntegrationModel();
                        model1.setType(1);
                        String time=DateUtil.getNowPlusTime();
                        model1.setAddTime(time);
                        model1.setUid(order.getUid());
                        int mon = DateUtil.getMonth(order.getInsuStart(),order.getInsuEnd());
                        double x = 0.0;
                        if(mon>=6){
                            x = 1;
                        }
                        model1.setIntegration((int)(Double.parseDouble(order.getPayment())*x));
                        integrationLogService.addInLog(model1);
                        integrationLogService.updateIntegration((int)(Double.parseDouble(order.getPayment())*x),order.getUid(),time);*/
                        String typestr="";
                        if(order.getInsuranceType()==1){
                            typestr="养老保险";
                        }else{
                            typestr="医疗保险";
                        }
                        Integer month = DateUtil.getMonth(order.getInsuStart().replaceAll("-","/"),order.getInsuEnd().replaceAll("-","/"))+1;
                        MessageModel msmodel=new MessageModel();
                        msmodel.setUser_id(order.getUid());
                        msmodel.setTitle("微信支付");
                        msmodel.setCreate_time(new Date());
                        msmodel.setFlag(0);
                        msmodel.setUnread(1);
                        msmodel.setType(3);
                        msmodel.setContent("【365灵活通】您参保的《"+typestr+"》已经支付成功，参保周期为"+order.getInsuStart()+"-"+order.getInsuEnd()+",共"+month+"个月，请等待审核，如有问题请联系客服"+getRootMap().get("serviceTel"));
                        messageService.sendMsg(msmodel);
                        String viptel=vipService.getTelById(order.getUid());
                        SmsUtil.sendSms(msmodel.getContent(),viptel,null,null);
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    }else{
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        //logger.info("订单已处理");
                    }
                }
            }else {
                //logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                paymsg="支付失败,错误信息："+ packageParams.get("err_code");
            }
        } else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
            //logger.info("通知签名验证失败");
            paymsg="通知签名验证失败";
        }

        //------------------------------
        //处理业务完毕
        //------------------------------
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        if(paymsg.equals("支付成功")){
            return setSuccessMap(jsonMap, paymsg, null);
        }else {
            return setFailureMap(jsonMap, paymsg, null);
        }
    }

    /**
     * 发送验证码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/sendIdent.do")
    public Map<String,Object> sendIdent(String  tel,HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        String ident="";
        for(int i=0;i<4;i++){
            ident+=String.valueOf((int)(Math.random()*9));
        }
        String content="【365灵活通】尊敬的365灵活通用户，您的验证码："+ident+"，工作人员不会索取，请勿泄漏。";
        try {
            List<Map> identlist=vipService.getIdent(tel);
            if(identlist!=null && identlist.size()>0){
                vipService.updateIdentBytel(tel,ident);
            }else{
                vipService.addIdentBytel(tel,ident);
            }
            SmsUtil.sendSms(content,tel,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "发送失败", null);
        }
        return setSuccessMap(jsonMap, "发送成功", null);
    }

    @Auth(verifyURL=false)
    @RequestMapping("/index.shtml")
    public ModelAndView index_h5( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        return forword("h5/index",context);
    }


    //养老计算器
    @Auth(verifyURL=false)
    @RequestMapping("/counter.shtml")
    public ModelAndView counter(OrderInfoModel model,
                                    HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,Object>  context = getRootMap();

        return forword("h5/counter",context);
    }

    /**
     * 积分消费列表接口
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/integrationList.do")
    public Map<String,Object> integrationList(Integer uid,Integer pageSize,Integer pageNum,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        if(uid==null){
            return setFailureMap(jsonMap, "uid不能为空！", null);
        }
        Integration intg = integrationLogService.queryByIntUid(uid);
        Integer count = integrationLogService.queryByIntLogCount(uid);
        int num1 = pageSize*(pageNum-1)+1;
        int num2 = pageSize;
        List<Integration> ingList = integrationLogService.queryByIntLogList(uid,num1,num2);
        if(pageSize!=null && pageNum!=null && count>pageSize*pageNum){
            jsonMap.put("flag",1);
        }else{
            jsonMap.put("flag",2);
        }
        for(Integration intgt : ingList){
            if(intgt!=null){
                if(intgt.getType()==2||
                        intgt.getType()==3){
                    intgt.setIntegration_zh("-"+intgt.getIntegration());
                }
            }
        }
        jsonMap.put("integral",intg==null?0:intg.getIntegration()==null?0:intg.getIntegration());
        return setSuccessMap(jsonMap, "操作成功！", ingList);
    }

    /**
     * 积分消费列表接口
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/getGoodsTakeTypeList.do")
    public Map<String,Object> getGoodsTakeTypeList(GoodsOrderModel model,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();

        List<GoodsOrder> golist = new ArrayList<GoodsOrder>();
        if(model==null||model.getVip_id()==null){
            return setFailureMap(jsonMap, "vip_id不能为空！", null);
        }
        if(model.getTake_type()==1){
            golist = goodsOrderService.queryBySelfList(model);
        }else{
            golist = goodsOrderService.queryByMailList(model);
            for (GoodsOrder go : golist){
                if(go.getWrite_off_state()==null){
                    go.setWrite_off_time("发货时间为15个工作日内");
                    break;
                }
                if(go.getWrite_off_state()==1){
                    go.setWrite_off_time("发货时间为15个工作日内");
                }else{
                    go.setWrite_off_time(go.getWrite_off_time().replaceAll("\\.0",""));
                }
            }
        }

        return setSuccessMap(jsonMap, "操作成功！", golist);
    }

    /**
     * 订单详情接口
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/goodsInfo.do")
    public Map<String,Object> goodsOrderInfo(Integer order_id,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        if(order_id==null){
            return setFailureMap(jsonMap, "order_id不能为空！", null);
        }
        GoodsOrder goodsOrder = goodsOrderService.queryByOrderId(order_id);
        if(goodsOrder.getImgUrl()!=null){
            String[] imgUrls = goodsOrder.getImgUrl().split(",");
            goodsOrder.setImgUrls(imgUrls);
        }
        if(goodsOrder.getTake_type()==1){
            if(goodsOrder.getWrite_off_state()==1){
                goodsOrder.setWrite_off_info("待核销");
            }else if(goodsOrder.getWrite_off_state()==2){
                goodsOrder.setWrite_off_info("已核销");
                goodsOrder.setWrite_off_time(goodsOrder.getWrite_off_time().replaceAll("\\.0",""));
            }else{
                goodsOrder.setWrite_off_info("已过期");
            }
        }
        if(goodsOrder.getWrite_off_state()==1){
            goodsOrder.setWrite_off_state_zh("待发货");
            goodsOrder.setWrite_off_time("发货时间为15个工作日内");
        }else if(goodsOrder.getWrite_off_state()==2){
            goodsOrder.setWrite_off_state_zh("已发货");
            goodsOrder.setWrite_off_time(goodsOrder.getWrite_off_time().replaceAll("\\.0",""));
        }else{
            goodsOrder.setWrite_off_state_zh("已过期");
            goodsOrder.setWrite_off_time("发货时间为15个工作日内");
        }
        jsonMap.put("goodsOrder",goodsOrder);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }


    /**
     * 商品详情接口
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/goodsDetails.do")
    public Map<String,Object> goodsdetails(Integer goods_id,Integer uid,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        if(goods_id==null){
            return setFailureMap(jsonMap, "goods_id不能为空！", null);
        }
        if(uid==null){
            return setFailureMap(jsonMap, "用户不能为空！", null);
        }
        Goods goods = goodsService.queryById(goods_id);
        if(goods==null){
            return setFailureMap(jsonMap, "没有该商品！", null);
        }
        if(goods.getImgUrl()!=null){
            goods.setImgUrls(goods.getImgUrl().split(","));

        }
        if(goods.getTake_type()!=null&&goods.getTake_type()==1){
            Integer gcount = redeemCodeService.queryByGoodsCount(goods_id,1);
            if(gcount==null){
                gcount = 0;
            }
            goods.setStock(gcount);
        }

        Integration inra = integrationLogService.queryByIntUid(uid);

        if(inra!=null){
            goods.setType(1);
            if(inra.getIntegration()<=goods.getIntegral()){
                goods.setType(2);
                jsonMap.put("goods",goods);
                return setSuccessMap(jsonMap, "积分不足", null);
            }
                 //检查库存
            if(goods.getStock()<=0){
                goods.setType(3);
                jsonMap.put("goods",goods);
                return setSuccessMap(jsonMap, "库存不足", null);
            }
            if(goods.getTake_type()==1&&goods.getTerm_of_validity_end()!=null){
                int cop = DateUtil.compareDateYM(DateUtil.getFomartDate(new Date(),"yyyy/MM"),goods.getTerm_of_validity_end());
                if(cop>0){
                    goods.setType(4);
                    jsonMap.put("goods",goods);
                    return setSuccessMap(jsonMap, "已过期", null);
                }
            }
        }else{
            goods.setType(2);
            jsonMap.put("goods",goods);
            return setSuccessMap(jsonMap, "积分不足", null);
        }


        jsonMap.put("goods",goods);
        return setSuccessMap(jsonMap, "立即兑换", null);
    }

    //立即兑换（接口）
    @ResponseBody
    @RequestMapping("/goods_order_info.do")
    public Map<String,Object> goods_order_info(Integer goods_id,Integer uid,Integer num, HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        if(uid==null){
            return setFailureMap(jsonMap,"该用户不存在！",null);
        }
        if(goods_id==null){
            return setFailureMap(jsonMap,"该商品不存在！",null);
        }

        Vip vip1 = vipService.queryVipById(uid);
        Goods goods = goodsService.queryById(goods_id);
        if(goods!=null&&goods.getTake_type()==1){
            Integer gcount = redeemCodeService.queryByGoodsCount(goods_id,1);
            if(gcount!=null&&gcount<=0){
                return setFailureMap(jsonMap,"该商品库存不足！",null);
            }
        }else{
            if(goods.getStock()!=null&&goods.getStock()<=0){
                return setFailureMap(jsonMap,"该商品库存不足！",null);
            }
        }

        GoodsAddressModel addressModel = new GoodsAddressModel();
        //addressModel.setIs_default(1);
        addressModel.setVip_id(uid);
        List<GoodsAddress> alist = goodsAddressService.queryByList(addressModel);


        jsonMap.put("order_number", Md5Util.createPwd()+(new Date().getTime()));
        jsonMap.put("goods_name",goods.getGname());
        jsonMap.put("integral",goods.getIntegral());
        jsonMap.put("take_type",goods.getTake_type());
        jsonMap.put("check_address",alist==null||alist.size()==0?1:2);
        if(num==null){
            num = 1;
        }else if(num==0){
            num = 1;
        }
        jsonMap.put("num",num);
        jsonMap.put("integralNum",num*goods.getIntegral());
        if(alist==null||alist.size()==0){
            return setSuccessMap(jsonMap, "您未设置地址", null);
        }

        return setSuccessMap(jsonMap, "立即兑换", alist);
    }

    /**
     * 订单详情接口
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/vipInfo.do")
    public Map<String,Object> vipInfo(Integer uid,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        if(uid==null){
            return setFailureMap(jsonMap, "uid不能为空！", null);
        }
        Vip vip = vipService.queryVipById(uid);
        if(vip!=null){
            if(vip.getAudit()!=null){
                if(vip.getAudit()==1){
                    jsonMap.put("audit",1);
                    jsonMap.put("name",vip.getName());
                    return setSuccessMap(jsonMap, "认证审核中！", null);//aaa
                }else if(vip.getAudit()==2){
                    jsonMap.put("audit",2);
                    jsonMap.put("name",vip.getName());
                    jsonMap.put("identNum",vip.getIdentNum());
                }else{
                    jsonMap.put("audit",3);
                    jsonMap.put("name",vip.getName());
                    jsonMap.put("identNum",vip.getIdentNum());
                    return setSuccessMap(jsonMap, "认证已被驳回！", null);
                }

            }else{
                jsonMap.put("audit",0);
                return setSuccessMap(jsonMap, "未提交认证！", null);
            }
        }

        return setSuccessMap(jsonMap, "已认证成功！", null);
    }


    /**
     * 验证积分是否足够支付该商品
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/checkJifen.do")
    public Map<String,Object> checkJifen(Integer uid,GoodsModel model,HttpServletRequest request)throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Goods goods = goodsService.queryByNumber(model.getNumber());
        if(goods==null&&goods.getStock()==0){
            return setSuccessMap(jsonMap, "库存不足！", null);
        }

      /*  goodsOrderService.getGoodlist()*/

        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    /**
     * 点位
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/marker.do")
    public Map<String, Object>  marker(HttpServletRequest request) throws Exception{
        String location = request.getParameter("location");
        String key = request.getParameter("key");

        String url = "https://apis.map.qq.com/ws/geocoder/v1/?location="+location+"&key="+key;
        //List<PersonalJobsApply> dataList = personalJobsApplyService.queryByList(model);
        JSONObject json = HttpJSON.loadJSON(url);
        //设置页面数据
        Map<String,Object> jsonMap = new HashMap<String,Object>();

        jsonMap.put("marker", json);

        return setSuccessMap(jsonMap, "操作成功！", null);
    }

    /**
     * 修改城市id
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/updateCity.do")
    public Map<String, Object>  updateCity(Integer cityId,HttpServletRequest request) throws Exception{
        Vip vip = SessionUtils.getVip(request);
        vip.setCity(cityId);
        SessionUtils.setVip(request,vip);
        //设置页面数据
        Map<String,Object> jsonMap = new HashMap<String,Object>();


        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/getNoPayByVipId.do")
    public Map<String, Object>  getNoPayByVipId(Integer vipId,HttpServletRequest request) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        Integer number = insuranceService.countNoPayVipId(vipId);
        jsonMap.put("number",number==null?0:number);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/getNeedYBK.do")
    public Map<String, Object>  getNeedYBK(Integer vipId,Integer deptId,HttpServletRequest request) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        List<SysInsurance> inlist = insuranceService.getYBKById(vipId,deptId);
        Integer isSend=0;
        if(inlist!=null && inlist.size()>0 && (inlist.get(0).getTstype()==1 || (inlist.get(0).getMedicalUrl()!=null && inlist.get(0).getMedicalUrl().length()>0))){
            isSend=1;
        }
        if(isSend==0){
            SysInsuranceModel model=new SysInsuranceModel();
            model.setVip_id(vipId);
            model.setDeptId(deptId);
            model.setTstype(1);
            if(inlist==null || inlist.size()<1){
                sysInsuranceService.addSysIns(model);
            }else{
                sysInsuranceService.updateSysIns(model);
            }
        }
        jsonMap.put("isSend",isSend);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    @Auth(verifyURL=false)
    @ResponseBody
    @RequestMapping("/getPrompt.do")
    public Map<String, Object>  getPrompt(Integer deptId,HttpServletRequest request) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        String str1="";
        String str2="";
        String str3="";
        String str4="";
        String str5="";
        String str6="";
        SysDept sd= sysDeptService.queryByDept(deptId);
        if(sd!=null && sd.getBank()!=null){
            str1=sd.getBank();
        }else{
            str1="河北银行";
        }
        str2="请填写和上传同您医保卡绑定的"+str1;
        str3="请上传银行卡正面照片";
        str4="银行卡上传要求：请上传和您医保绑定的河北银行卡；正面清晰，布光均匀，银行卡号清晰可见，否则后台不予通过！";
        str5="身份证正面上传要求：正面清晰，布光均匀，身份证号清晰可见，否则后台不予通过！";
        str6="身份证背面上传要求：背面清晰，布光均匀，有效期清晰可见，否则后台不予通过！";
        jsonMap.put("str1",str1);
        jsonMap.put("str2",str2);
        jsonMap.put("str3",str3);
        jsonMap.put("str4",str4);
        jsonMap.put("str5",str5);
        jsonMap.put("str6",str6);
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/uploadMedicalUrl")
    public  Map<String, Object> uploadMedicalUrl(@RequestParam("file1") MultipartFile file1,SysInsuranceModel model,HttpServletResponse response,
                                             HttpServletRequest request) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Vip vip = SessionUtils.getVip(request);
        if(vip!=null && vip.getId()!=null){
            model.setVip_id(vip.getId());
        }
        try {
            String parhstr=request.getSession().getServletContext()
                    .getRealPath(File.separator);
            parhstr=parhstr.substring(0,parhstr.length()-5);
            String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
            String type1 = file1!=null && !org.apache.commons.lang.StringUtils.isBlank(file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")):"";
            if(type1.equals("")){
                return setFailureMap(jsonMap, "请选择图片！", null);
            }
            if ((!type1.equals("") && imgeArray.indexOf(type1.toUpperCase()) < 0)) {
                return setFailureMap(jsonMap, "文件格式错误！", null);
            }
            if ((file1!=null && file1.getSize() > 10485760)) {
                return setFailureMap(jsonMap, "图片过大！", null);
            }
            String sjc = "";
            String path = "";
            if(!type1.equals("")){
                sjc=DateUtil.getNowPlusTimeMill();
                path = parhstr+"aptitude"
                        + File.separator
                        + sjc
                        +type1;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                file1.transferTo(new File(path));
                model.setMedicalUrl(UserConstants.CRMURL + "aptitude/"+ sjc + type1);

            }
            List<SysInsurance> inlist=insuranceService.getYBKById(model.getVip_id(),model.getDeptId());
            Date data=new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String datetime=format.format(data);
            model.setMedicalUpdateTime(datetime);
            if(inlist != null && inlist.size()>0){
                sysInsuranceService.updateSysIns(model);
            }else{
                model.setCreateTime(datetime);
                sysInsuranceService.addSysIns(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/deleteInsurance")
    public  Map<String, Object> deleteInsurance(SysInsuranceModel model,HttpServletResponse response,
                                                 HttpServletRequest request) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            sysInsuranceService.deleteInsurance(model);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/uploadMedicalInfo")
    public  Map<String, Object> uploadMedicalInfo(SysInsuranceModel model,HttpServletResponse response,
                                                 HttpServletRequest request) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            List<SysInsurance> inlist=insuranceService.getYBKById(model.getVip_id(),model.getDeptId());
            Date data=new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String datetime=format.format(data);
            model.setMedicalUpdateTime(datetime);
            if(inlist != null && inlist.size()>0){
                sysInsuranceService.updateSysIns(model);
            }else{
                model.setCreateTime(datetime);
                sysInsuranceService.addSysIns(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", null);
    }
//    @ResponseBody
//    @Auth(verifyLogin=false,verifyURL=false)
//    @RequestMapping("/updateMedical")
//    public  Map<String, Object> updateMedical(SysInsuranceModel model,HttpServletResponse response,
//                                           HttpServletRequest request) throws Exception {
//        Map<String, Object> jsonMap = new HashMap<String, Object>();
//        List<SysInsurance> datalist=new ArrayList<SysInsurance>();
//        try {
//            datalist=sysInsuranceService.getMedical(model);
//
//            if(datalist!=null && datalist.size()>0){
//                sysInsuranceService.updateSysIns(model);
//            }else{
//                model.setCreateTime(model.getMedicalUpdateTime());
//                sysInsuranceService.addSysIns(model);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return setFailureMap(jsonMap, "操作失败！", null);
//        }
//        return setSuccessMap(jsonMap, "操作成功！", datalist);
//    }

    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/getPost")
    public  Map<String, Object> getPost(HttpServletResponse response,
                                                HttpServletRequest request) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            System.out.println(request.getParameter("resumePackage"));

            String str = request.getParameter("resumePackage");
            JSONObject json = JSONObject.fromObject(str);

            //JSONObject json = JSONObject.fromObject("");

            JSONArray jsonArray = JSONArray.fromObject(json.getString("resumes"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            for (int i=0;i<jsonArray.size();i++){
                JSONObject job = jsonArray.getJSONObject(i);

                if(job!=null){
                    ZpResumesModel model = new ZpResumesModel();
                    if(job.has("name")){
                        model.setName(job.getString("name")==null?"":job.getString("name"));
                    }else{
                        model.setName("");
                    }

                    if(job.has("age")){
                        model.setAge(job.getInt("age"));
                    }else{
                        model.setAge(0);
                    }

                    String address = "";
                    if(job.has("regionName")){
                        address = job.getString("regionName");
                        model.setFrom(1);
                        String bday = job.getString("birthday");
                        if(bday!=null){
                            Long birthl = sdf.parse(bday).getTime();
                            Integer birthday =  Integer.parseInt(birthl.toString().substring(0,birthl.toString().length()-3));
                            model.setBirtyday(birthday);
                        }else{
                            model.setBirtyday(0);
                        }
                    }else{
                        model.setBirtyday(0);
                        model.setFrom(2);
                    }
                    model.setAddress(address);

                    if(job.has("workName")){
                        model.setWork_expire(job.getString("workName")==null?"":job.getString("workName"));
                    }else{
                        model.setWork_expire("");
                    }
                    if(job.has("educationName")){
                        model.setEducation(job.getString("educationName")==null?"":job.getString("educationName"));
                    }else{
                        model.setEducation("");
                    }
                    if(job.has("sexName")){
                        model.setSex(job.getString("sexName")==null?"":job.getString("sexName"));
                    }else{
                        model.setSex("");
                    }
                    if(job.has("mobile")){
                        model.setMobile(job.getString("mobile"));
                    }else{
                        model.setMobile("");
                    }
                    if(job.has("email")){
                        model.setEmail(job.getString("email")==null?"":job.getString("email"));
                    }else{
                        model.setEmail("");
                    }

                    //school start
                    JSONArray arrs = null;
                    if(job.has("educationExperienceList")){
                        arrs = JSONArray.fromObject(job.getString("educationExperienceList"));
                    }else{
                        arrs = JSONArray.fromObject(job.getString("eduexprList"));
                    }
                    if(arrs!=null&&arrs.size()>0){
                        for(int j=0;j<arrs.size();j++){
                            JSONObject jobs = arrs.getJSONObject(j);
                            if(j==(arrs.size()-1)){
                                if(jobs.has("school")){
                                    model.setSchool(jobs.getString("school")==null?"":jobs.getString("school"));
                                }else{
                                    model.setSchool("");
                                }
                                if(jobs.has("major")){
                                    model.setMajor(jobs.getString("major")==null?"":jobs.getString("major"));
                                }else{
                                    model.setMajor("");
                                }
                                break;
                            }
                        }
                    }
                    //school end

                    //work start
                    String work = null;
                    if(job.has("workExperienceList")){
                        work = job.getString("workExperienceList");
                    }else{
                        work = job.getString("workexprList");
                    }
                    JSONArray arrw = JSONArray.fromObject(work);

                    if(arrw!=null&&arrw.size()>0){
                        for(int j=0;j<arrw.size();j++){
                            JSONObject jobw = arrw.getJSONObject(j);
                            if(j==(arrw.size()-1)){
                                if(jobw.has("workUnit")){
                                    model.setWork(jobw.getString("workUnit")==null?"":jobw.getString("workUnit"));
                                }else{
                                    model.setWork("");
                                }
                                if(jobw.has("position")){
                                    model.setPosition(jobw.getString("position")==null?"":jobw.getString("position"));
                                }else{
                                    model.setPosition("");
                                }
                                break;
                            }
                        }
                    }
                    //work end

                    if(job.has("applyPosition")){
                        model.setApply_position(job.getString("applyPosition")==null?"":job.getString("applyPosition"));
                    }else{
                        model.setApply_position("");
                    }
                    if(job.has("sendTime")){
                        String st = job.getString("sendTime");
                        if(st!=null){
                            Long l = sdf.parse(st).getTime();
                            Integer sendTime = Integer.parseInt(l.toString().substring(0,l.toString().length()-3));
                            model.setSendtime(sendTime);
                        }
                    }else{
                        model.setSendtime(0);
                    }

                    ZpResumes res = zpResumesService.queryByMobile(model.getMobile());
                    if(res!=null){
                        zpResumesService.updateResumes(model);
                    }else{
                        Long addl = new Date().getTime();
                        Integer addTime = Integer.parseInt(addl.toString().substring(0,addl.toString().length()-3));
                        model.setAddtime(addTime);
                        zpResumesService.addResumes(model);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap1(jsonMap, "操作失败！", null);
        }
        return setSuccessMap1(jsonMap, "操作成功！", "33333");
    }
}
