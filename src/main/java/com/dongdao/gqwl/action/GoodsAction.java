package com.dongdao.gqwl.action;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.bean.Goods;
import com.dongdao.gqwl.bean.GoodsOrder;
import com.dongdao.gqwl.bean.RedeemCode;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.GoodsModel;
import com.dongdao.gqwl.model.GoodsOrderModel;
import com.dongdao.gqwl.model.RedeemCodeModel;
import com.dongdao.gqwl.service.RedeemCodeService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.ExcelUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import com.dongdao.gqwl.service.GoodsOrderService;
import com.dongdao.gqwl.service.GoodsService;
import com.dongdao.gqwl.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/goods")
public class GoodsAction extends BaseAction {

    private final static Logger log= Logger.getLogger(GoodsAction.class);

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysUserService sysUserService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public GoodsService goodsService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public GoodsOrderService goodsOrderService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public RedeemCodeService redeemCodeService;

    @RequestMapping(value = "/mangoods.do")
    public ModelAndView manGoods(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        return forword("goods/mall_goods", context);
    }

    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping(value = "/addgoods.do")
    public ModelAndView Addgoods(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        return forword("goods/mall_add", context);
    }


    @RequestMapping("/addGoods")
    public void addInfo(@RequestParam("file1")MultipartFile file1, @RequestParam("file2")MultipartFile file2,
                        @RequestParam("file3")MultipartFile file3, @RequestParam("file4")MultipartFile file4,
                        GoodsModel model, HttpServletResponse response,
                        HttpServletRequest request) throws Exception{

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        String picurl1="";
        String picurl2="";
        String picurl3="";
        String picurl4="";
        String url="";
        //检查是否有重复商品

        Goods goodsname = goodsService.queryByName(model.getGname());
        if(goodsname!=null){
            sendFailureMessage(response, "已有此商品，请重新填写商品名称！");
            return ;
        }

        Goods goodsnum = goodsService.queryByNum(model.getNumber());
        if(goodsnum!=null){
            sendFailureMessage(response, "商品编号已存在，请重新填写商品编号！");
            return ;
        }

        url=uploadImg(file1,request);
        if(url==null||url.equals("")){
            sendFailureMessage(response, "请最少上传4张图片!");
            return;
        }
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")){
            picurl1 = url+",";
        }
        url=uploadImg(file2,request);
        if(url==null||url.equals("")){
            sendFailureMessage(response, "请最少上传4张图片!");
            return;
        }
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")){
            picurl2 = url+",";
        }
        url=uploadImg(file3,request);
        if(url==null||url.equals("")){
            sendFailureMessage(response, "请最少上传4张图片!");
            return;
        }
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")) {
            picurl3 = url+",";
        }
        url=uploadImg(file4,request);
        if(url==null||url.equals("")){
            sendFailureMessage(response, "请最少上传4张图片!");
            return;
        }
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")) {
            picurl4 = url;
        }

        model.setImgUrl(picurl1+picurl2+picurl3+picurl4);
        goodsService.addGoods(model);

        sendSuccessMessage(response, "保存成功~");
    }
    //获取审核列表
    @RequestMapping("/getGoodsDataList")
    public void getDoodsDataList(GoodsModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Goods> dataList = goodsService.queryByList(model);
        for (Goods g : dataList){
            if(g.getTake_type()!=null&&g.getTake_type()==1){
                Integer cont = redeemCodeService.queryByGoodsCount(g.getId(),1);
                g.setStock(cont==null?0:cont);
            }
            if(g.getState()==null){
                g.setState(2);
            }
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取用户总数
    @RequestMapping("/getGoodsCount.do")
    public void getDoodsCount(GoodsModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        Integer count=goodsService.queryByCount(model);
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

    @RequestMapping(value = "/mallquery.do")
    public ModelAndView mallQuery(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("goods/mall_query", context);
    }

    @RequestMapping(value = "/mallexchange.do")
    public ModelAndView mallExchange(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("goods/mall_exchange", context);
    }

    //获取审核列表
    @RequestMapping("/getGoodsOrderDataList.do")
    public void getDoodsOrderDataList(GoodsOrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<GoodsOrder> dataList = goodsOrderService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取用户总数
    @RequestMapping("/getGoodsOrderCount.do")
    public void getDoodsOrderCount(GoodsOrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        Integer count=goodsOrderService.queryByCount(model);
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

    @RequestMapping(value = "/sendgoods.do")
    public ModelAndView sendGoods(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        return forword("goods/mall_send", context);
    }

    //获取发货管理列表
    @RequestMapping("/getGoodsSendDataList.do")
    public void getDoodsSendDataList(GoodsOrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<GoodsOrder> dataList = goodsOrderService.queryBySendList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取发货管理总数
    @RequestMapping("/getGoodsSendCount.do")
    public void getDoodsSendCount(GoodsOrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        Integer count=goodsOrderService.queryBySendCount(model);
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

    @RequestMapping(value = "/querygoods.do")
    public ModelAndView queryGoods(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        return forword("goods/mall_query", context);
    }

    //获取核销查询列表
    @RequestMapping("/getGoodsQueryDataList.do")
    public void getDoodsQueryDataList(GoodsModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Goods> dataList = goodsService.queryByQList(model);
        for(Goods o : dataList){
            if(o.getTake_type()==1){
                Integer wdhgq = redeemCodeService.queryByEXCount(o.getId(),1);
                Integer ydhgq = redeemCodeService.queryByEXCount(o.getId(),2);
                o.setWdhgq(wdhgq);
                o.setYdhgq(ydhgq);

                Integer pick_up_nums = redeemCodeService.queryByGoods(o.getId());
                o.setPick_up_nums(pick_up_nums);
                o.setMail_nums(0);

                RedeemCodeModel rmodel = new RedeemCodeModel();
                rmodel.setGoods_id(o.getId());

                //已兑换数量
                rmodel.setStatus(2);
                Integer exchanged_nums = redeemCodeService.queryByCount(rmodel);
                o.setExchanged_nums(exchanged_nums);

                //已核销
                rmodel.setStatus(3);
                Integer writed_off_nums = redeemCodeService.queryByCount(rmodel);
                o.setWrited_off_nums(writed_off_nums);

                //未核销数量
                rmodel.setStatus(1);
                Integer write_off_nums = redeemCodeService.queryByCount(rmodel);
                o.setWrite_off_nums(write_off_nums);

                //未核销数量
                rmodel.setStatus(4);
                Integer not_exchange_nums = redeemCodeService.queryByCount(rmodel);
                o.setNot_exchange_nums(not_exchange_nums);
            }else{
                o.setWdhgq(0);
                o.setYdhgq(0);
                o.setPick_up_nums(0);

                GoodsOrderModel gmodel = new GoodsOrderModel();
                gmodel.setGoods_id(o.getId());
                gmodel.setTake_type(2);
                Integer gCount = goodsOrderService.countGoods(gmodel);
                o.setMail_nums(gCount);

                o.setExchanged_nums(0);
                o.setWrited_off_nums(0);
                o.setWrite_off_nums(0);
                o.setNot_exchange_nums(0);
            }
        }

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取核销查询总数
    @RequestMapping("/getGoodsQueryCount.do")
    public void getDoodsQueryCount(GoodsModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        Integer count=goodsService.queryByQCount(model);
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

    /**
     * 导出兑换数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportExchangeList.do")
    public void exportExchangeList(GoodsOrderModel model, HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
            // 获取前台传来的题型和课程

//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("order_number", "商品订单");
            fieldMap.put("redeem_code", "商品编号");
            fieldMap.put("goods_name", "商品名称");
            fieldMap.put("nums", "兑换数量");
            fieldMap.put("integral", "消耗积分");
            fieldMap.put("exchange_number", "兑换账号");
            fieldMap.put("exchange_time", "兑换时间");
            fieldMap.put("write_off_time", "核销时间");
            fieldMap.put("write_off_info", "核销状态");

//	        // excel的sheetName
            String sheetName = "兑换列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<GoodsOrder> dataList = goodsOrderService.queryByExcel(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出发送数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportSendList.do")
    public void exportSendList(GoodsOrderModel model, HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("order_number", "商品订单");
            fieldMap.put("goods_number", "商品编号");
            fieldMap.put("exchange_number", "兑换账号");
            fieldMap.put("integral", "消耗积分");
            fieldMap.put("goods_name", "商品名称");
            fieldMap.put("nums", "邮寄数量");
            fieldMap.put("onsignee_name", "收货人姓名");
            fieldMap.put("deliver_goods_tel", "手机号");
            fieldMap.put("deliver_goods_address", "地址");
            fieldMap.put("stateCh_zn", "状态");
            fieldMap.put("exchange_time", "兑换时间");
            fieldMap.put("write_off_time", "发货时间");

//	        // excel的sheetName
            String sheetName = "发送列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<GoodsOrder> dataList = goodsOrderService.queryBySendExcel(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
                GoodsOrder g = new GoodsOrder();
                g.setOrder_number("暂无数据");
                dataList.add(g);
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出核销数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportQueryList.do")
    public void exportQueryList(GoodsModel model, HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("number", "商品编号");
            fieldMap.put("gname", "商品名称");
            fieldMap.put("integral", "商品积分");
            fieldMap.put("exchanged_nums", "已兑换数量");
            fieldMap.put("not_exchange_nums", "未兑换数量");
            fieldMap.put("pick_up_nums", "自提数量");
            fieldMap.put("mail_nums", "邮寄数量");
            fieldMap.put("writed_off_nums", "已核销数量");
            fieldMap.put("write_off_nums", "未核销数量");


//	        // excel的sheetName
            String sheetName = "核销列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<Goods> dataList = goodsService.queryByExcel(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uploadImg (@RequestParam("file")MultipartFile file,HttpServletRequest request ) throws Exception{
        String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
        String parhstr=request.getSession().getServletContext()
                .getRealPath(File.separator);
        parhstr=parhstr.substring(0,parhstr.length()-5);
        String type = file!=null && !StringUtils.isBlank(file.getOriginalFilename())?file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf(".")):"";
        if (type.equals("")) {
            return "1";
        }
        if ((!type.equals("") && imgeArray.indexOf(type.toUpperCase()) < 0)) {
            return "2";

        }
        if ((file!=null && file.getSize() > 10485760)) {
            return "3";
        }
        String sjc = "";
        String path = "";
        sjc= com.dongdao.gqwl.utils.DateUtil.getNowPlusTimeMill();
        path = parhstr +"aptitude"
                + File.separator
                + sjc
                +type;
        File f = new File(path);
        // 创建文件夹
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        System.out.println(path);
        file.transferTo(new File(path));
        return UserConstants.CRMURL + "aptitude/"+ sjc + type;
    }
    @RequestMapping("/upAudit.do")
    public void upAudit(Integer state,Integer goods_id, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg", "登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        Goods g = goodsService.queryById(goods_id);
        if(g.getTake_type() == 1){
            Integer rcount = redeemCodeService.queryByGoods(goods_id);
            if(rcount == null || rcount == 0){
                jsonMap.put("msg", "没有兑换码，您不能上架该商品！");
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }
        }

        goodsService.updateGoods(state,goods_id);
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/upAuditAll.do")
    public void upAuditAll(Integer state,String goods_ids, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg", "登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        String[] goods_idss = goods_ids.split(",");

        for(String goods_id : goods_idss){
            Goods g = goodsService.queryById(Integer.parseInt(goods_id));
            if(g.getTake_type() == 1){
                Integer rcount = redeemCodeService.queryByGoods(g.getId());
                if(rcount == null || rcount == 0){
                    jsonMap.put("msg", "没有兑换码，您不能上架该商品！");
                    HtmlUtil.writerJson(response, jsonMap);
                    return;
                }
            }
            goodsService.updateGoods(state,Integer.parseInt(goods_id));
        }

        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/deletgoods.do")
    public void deletgoods(Integer goods_id, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg", "登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        goodsService.delGoods(goods_id);
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping("/deletgoodsAll.do")
    public void deletgoodsAll(String goods_ids, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg", "登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        String[] goods_idss = goods_ids.split(",");

        for(String goods_id : goods_idss){
            goodsService.delGoods(Integer.parseInt(goods_id));
        }

        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/deletRedeemCodeAll.do")
    public void deletRedeemCodeAll(String ordeem_code_ids, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg", "登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        String[] ordeem_code_idss = ordeem_code_ids.split(",");

        for(String code_id : ordeem_code_idss){
            RedeemCode redeemCode = redeemCodeService.getReddmCodeId(Integer.parseInt(code_id));
            if(redeemCode!=null&&redeemCode.getStatus()==2){
                jsonMap.put("msg", redeemCode.getRedeem_code()+" 该兑换码已兑换！");
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }else if(redeemCode!=null&&redeemCode.getStatus()==3){
                jsonMap.put("msg", redeemCode.getRedeem_code()+" 该兑换码已核销！");
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }else if(redeemCode!=null&&redeemCode.getStatus()==4){
                jsonMap.put("msg", redeemCode.getRedeem_code()+" 该兑换码已过期！");
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }
        }

        for(String code_id : ordeem_code_idss){
            redeemCodeService.deleteRedmmCode(Integer.parseInt(code_id));
        }

        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping(value = "/redeemCodeUpload.do")
    public ModelAndView redeemCodeUpload(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        return forword("goods/redeem_code_upload", context);
    }

    @RequestMapping(value = "/redeemCodeCheck.do")
    public ModelAndView redeemCodeCheck(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        return forword("goods/redeem_code_check", context);
    }


    @RequestMapping("/uploadExcel.do")
    public void uploadExcel(@RequestParam(value = "file", required = false) MultipartFile file,
                            HttpServletRequest request,HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = formatter.format(new Date());
        File file1 = new File(request.getSession().getServletContext().getRealPath("/excel"));
        if(file1.exists()){
            //上传文件逻辑...
        }else{
            file1.mkdir();
            //上传文件逻辑...
        }
        OutputStream os = new FileOutputStream(request.getSession().getServletContext().getRealPath("/excel")+"/"+format+"userid"+user.getId()+".xlsx");
        InputStream inputStream = file.getInputStream();
        int temp;
        while((temp=inputStream.read())!=-1){
            os.write(temp);
        }
        os.flush();
        os.close();
        inputStream.close();
        StringBuilder sb = new StringBuilder();
        Workbook wb = null;
        wb = WorkbookFactory.create(file.getInputStream()); //new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);

        //检查格式
        Sheet sheet1 = wb.getSheetAt(0);
        for(int i = 1;i<=sheet1.getLastRowNum();i++){
            Row row = sheet1.getRow(i);
            if(row==null){
                break;
            }
            Iterator cells = row.cellIterator();
            String gname=null;
            String redeem_code = null;
            String term_of_validity_end = null;

            Cell cellGName = (Cell) cells.next();
            cellGName.setCellType(CellType.STRING);
            gname = cellGName.getStringCellValue();
            if(gname==null||gname.equals("")){
                sendSuccessMessage(response, "商品名称为空请完善信息重新导入！");
                return;
            }

            Cell redeemCode = (Cell) cells.next();
            redeemCode.setCellType(CellType.STRING);
            redeem_code = redeemCode.getStringCellValue();

            if(redeem_code==null||redeem_code.equals("")){
                sendSuccessMessage(response, gname+"兑换码为空请完善信息重新导入！");
                return;
            }

            RedeemCode rc = redeemCodeService.queryByCode(redeem_code);

            if(rc!=null){
                sendSuccessMessage(response, redeem_code+" 兑换码已存在请重新录入！");
                return;
            }

            Cell term_of_validity_end_cell = (Cell) cells.next();
            term_of_validity_end_cell.setCellType(CellType.STRING);
            term_of_validity_end = term_of_validity_end_cell.getStringCellValue();
            if(term_of_validity_end==null){
                sendSuccessMessage(response, term_of_validity_end+" 到期时间不能为空！");
                return;
            }
            if(StringUtils.isNotEmpty(gname)&&StringUtils.isNotEmpty(redeem_code)
                    &&StringUtils.isNotEmpty(term_of_validity_end)){
                RedeemCodeModel rcode = new RedeemCodeModel();
                rcode.setRedeem_code(redeem_code);
                term_of_validity_end =  term_of_validity_end.replaceAll("\\.","-").replaceAll("/","-");
                if(!com.dongdao.gqwl.utils.DateUtil.validateFormat(term_of_validity_end)){
                    sendSuccessMessage(response, term_of_validity_end+" 兑换时间格式不正确！");
                    return;
                }
                rcode.setTerm_of_validity_end(term_of_validity_end);
                rcode.setStatus(1);
                Goods g = goodsService.queryByName(gname);
                if(g==null){
                    sendSuccessMessage(response, gname+" 商品不存在，请重新录入！");
                    return ;
                }
            }else{
                sb.append("第"+(i+1)+"条数据有空数据!!!导入失败");
                sendSuccessMessage(response, sb.toString()+"\n导入失败!");
                return;
            }
        }
        //检查结束


        //上传开始
        for(int i = 1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            if(row==null){
                break;
            }
            Iterator cells = row.cellIterator();
            String gname=null;
            String redeem_code = null;
            String term_of_validity_end = null;

            Cell cellGName = (Cell) cells.next();
            cellGName.setCellType(CellType.STRING);
            gname = cellGName.getStringCellValue();
            if(gname==null||gname.equals("")){
                sendSuccessMessage(response, "商品名称为空请完善信息重新导入！");
                return;
            }

            Cell redeemCode = (Cell) cells.next();
            redeemCode.setCellType(CellType.STRING);
            redeem_code = redeemCode.getStringCellValue();

            if(redeem_code==null||redeem_code.equals("")){
                sendSuccessMessage(response, gname+"兑换码为空请完善信息重新导入！");
                return;
            }

            RedeemCode rc = redeemCodeService.queryByCode(redeem_code);

            if(rc!=null){
                sendSuccessMessage(response, redeem_code+" 兑换码已存在请重新录入！");
                return;
            }

            Cell term_of_validity_end_cell = (Cell) cells.next();
            term_of_validity_end_cell.setCellType(CellType.STRING);
            term_of_validity_end = term_of_validity_end_cell.getStringCellValue();
            if(term_of_validity_end==null){
                sendSuccessMessage(response, term_of_validity_end+" 到期时间不能为空！");
                return;
            }
            if(StringUtils.isNotEmpty(gname)&&StringUtils.isNotEmpty(redeem_code)
                    &&StringUtils.isNotEmpty(term_of_validity_end)){
                RedeemCodeModel rcode = new RedeemCodeModel();
                rcode.setRedeem_code(redeem_code);
                term_of_validity_end =  term_of_validity_end.replaceAll("\\.","-").replaceAll("/","-");
                rcode.setTerm_of_validity_end(term_of_validity_end);
                rcode.setStatus(1);
                Goods g = goodsService.queryByName(gname);
                if(g==null){
                    sendSuccessMessage(response, gname+" 商品不存在，请重新录入！");
                    return ;
                }

                if(g.getTake_type()==2){
                    sendSuccessMessage(response, gname+" 该商品提货方式为邮寄（邮寄商品不需要提货码），请重新录入！");
                    return ;
                }
                rcode.setGoods_id(g.getId());
                rcode.setGoods_name(gname);
                rcode.setCreate_time(new Date());
                redeemCodeService.addRedeemCode(rcode);
                if(i==sheet.getLastRowNum()){
                    goodsService.updateEndTime(term_of_validity_end,gname);
                }

                if(g==null){
                    sb.append(gname+"导入成功");
                }
            }else{
                sb.append("第"+(i+1)+"条数据有空数据!!!导入失败");
            }
        }
        //上传结束
        sendSuccessMessage(response, sb.toString()+"\n导入成功!");
    }

    //获取兑换码列表
    @RequestMapping("/getCodeDataList")
    public void getCodeDataList(RedeemCodeModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<RedeemCode> dataList = redeemCodeService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取兑换码总数
    @RequestMapping("/getCodeCount.do")
    public void getCodeCount(RedeemCodeModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        Integer count=redeemCodeService.queryByCount(model);
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


    /**
     * 上传兑换
     * @param file
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/uploadEExcel.do")
    public void uploadEExcel(@RequestParam(value = "file", required = false) MultipartFile file,
                            HttpServletRequest request,HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = formatter.format(new Date());
        File file1 = new File(request.getSession().getServletContext().getRealPath("/excel"));
        if(file1.exists()){
            //上传文件逻辑...
        }else{
            file1.mkdir();
            //上传文件逻辑...
        }
        OutputStream os = new FileOutputStream(request.getSession().getServletContext().getRealPath("/excel")+"/"+format+"userid"+user.getId()+".xlsx");
        InputStream inputStream = file.getInputStream();
        int temp;
        while((temp=inputStream.read())!=-1){
            os.write(temp);
        }
        os.flush();
        os.close();
        inputStream.close();
        StringBuilder sb = new StringBuilder();
        Workbook wb = null;
        wb = WorkbookFactory.create(file.getInputStream()); //new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        Sheet sheet1 = wb.getSheetAt(0);
        //检测开始
        for(int i = 1;i<=sheet1.getLastRowNum();i++){
            Row row = sheet1.getRow(i);
            if(row==null){
                break;
            }
            Iterator cells = row.cellIterator();
            String gname=null;
            String redeem_code = null;
            String shop_name = null;
            String write_off_time = null;
            String status_zh = null;

            Cell cellGName = (Cell) cells.next();
            cellGName.setCellType(CellType.STRING);
            gname = cellGName.getStringCellValue();
            if(gname==null||gname.equals("")){
                sendSuccessMessage(response, "商品名称为空请完善信息重新导入！");
                return;
            }

            Cell redeemCode = (Cell) cells.next();
            redeemCode.setCellType(CellType.STRING);
            redeem_code = redeemCode.getStringCellValue();

            if(redeem_code==null||redeem_code.equals("")){
                sendSuccessMessage(response, gname+"兑换码为空请完善信息重新导入！");
                return;
            }

            RedeemCode rc = redeemCodeService.queryByCode(redeem_code);

            if(rc==null){
                sendSuccessMessage(response, redeem_code+" 兑换码不存在请重新录入！");
                return;
            }

            Cell cellShopName = (Cell) cells.next();
            cellShopName.setCellType(CellType.STRING);
            shop_name = cellShopName.getStringCellValue();
            if(shop_name==null||shop_name.equals("")){
                sendSuccessMessage(response, redeem_code+"商店名称为空请完善信息重新导入！");
                return;
            }

            Cell write_off_time_cell = (Cell) cells.next();
            write_off_time_cell.setCellType(CellType.STRING);
            write_off_time = write_off_time_cell.getStringCellValue();
            if(write_off_time==null){
                sendSuccessMessage(response, write_off_time+" 兑换时间不能为空！");
                return;
            }

            Cell status_zh_cell = (Cell) cells.next();
            status_zh_cell.setCellType(CellType.STRING);
            status_zh = status_zh_cell.getStringCellValue();
            if(status_zh==null){
                sendSuccessMessage(response, status_zh+" 消费状态不能为空！");
                return;
            }
            if(StringUtils.isNotEmpty(gname)&&StringUtils.isNotEmpty(redeem_code)
                    &&StringUtils.isNotEmpty(write_off_time)){
                RedeemCodeModel rcode = new RedeemCodeModel();
                rcode.setRedeem_code(redeem_code);
                rcode.setGoods_name(gname);
                write_off_time =  write_off_time.replaceAll("\\.","-").replaceAll("/","-");
                if(!DateUtil.validateFormats(write_off_time)){
                    sendSuccessMessage(response, write_off_time+" 兑换时间格式不正确！");
                    return;
                }
                rcode.setWrite_off_time(write_off_time.replaceAll("-","/"));
                rcode.setShop_name(shop_name);
                rcode.setStatus(3);
                Goods g = goodsService.queryByName(gname);
                if(g==null){
                    sendSuccessMessage(response, gname+" 商品不存在，请重新录入！");
                    return ;
                }
            }else{
                sb.append("第"+(i+1)+"条数据有空数据!!!导入失败");
                sendSuccessMessage(response, sb.toString());
                return ;
            }
        }
        //检测结束


        //上传开始
        for(int i = 1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            if(row==null){
                break;
            }
            Iterator cells = row.cellIterator();
            String gname=null;
            String redeem_code = null;
            String shop_name = null;
            String write_off_time = null;
            String status_zh = null;

            Cell cellGName = (Cell) cells.next();
            cellGName.setCellType(CellType.STRING);
            gname = cellGName.getStringCellValue();
            if(gname==null||gname.equals("")){
                sendSuccessMessage(response, "商品名称为空请完善信息重新导入！");
                return;
            }

            Cell redeemCode = (Cell) cells.next();
            redeemCode.setCellType(CellType.STRING);
            redeem_code = redeemCode.getStringCellValue();

            if(redeem_code==null||redeem_code.equals("")){
                sendSuccessMessage(response, gname+"兑换码为空请完善信息重新导入！");
                return;
            }

            RedeemCode rc = redeemCodeService.queryByCode(redeem_code);

            if(rc==null){
                sendSuccessMessage(response, redeem_code+" 兑换码不存在请重新录入！");
                return;
            }

            Cell cellShopName = (Cell) cells.next();
            cellShopName.setCellType(CellType.STRING);
            shop_name = cellShopName.getStringCellValue();
            if(shop_name==null||shop_name.equals("")){
                sendSuccessMessage(response, redeem_code+"商店名称为空请完善信息重新导入！");
                return;
            }

            Cell write_off_time_cell = (Cell) cells.next();
            write_off_time_cell.setCellType(CellType.STRING);
            write_off_time = write_off_time_cell.getStringCellValue();
            if(write_off_time==null){
                sendSuccessMessage(response, write_off_time+" 兑换时间不能为空！");
                return;
            }

            Cell status_zh_cell = (Cell) cells.next();
            status_zh_cell.setCellType(CellType.STRING);
            status_zh = status_zh_cell.getStringCellValue();
            if(status_zh==null){
                sendSuccessMessage(response, status_zh+" 消费状态不能为空！");
                return;
            }
            if(StringUtils.isNotEmpty(gname)&&StringUtils.isNotEmpty(redeem_code)
                    &&StringUtils.isNotEmpty(write_off_time)){
                RedeemCodeModel rcode = new RedeemCodeModel();
                rcode.setRedeem_code(redeem_code);
                rcode.setGoods_name(gname);
                write_off_time =  write_off_time.replaceAll("\\.","-").replaceAll("/","-");
                rcode.setWrite_off_time(write_off_time);
                rcode.setShop_name(shop_name);
                rcode.setStatus(3);
                rcode.setStatus_zh(status_zh);
                Goods g = goodsService.queryByName(gname);
                if(g==null){
                    sendSuccessMessage(response, gname+" 商品不存在，请重新录入！");
                    return ;
                }


                redeemCodeService.updateRedmmCode(rcode);

                if(g==null){
                    sb.append(gname+"导入成功");
                }
            }else{
                sb.append("第"+(i+1)+"条数据有空数据!!!导入失败");
            }
        }
        //上传结束
        sendSuccessMessage(response, sb.toString()+"\n导入成功!");
    }

    /**
     * 保存发货信息
     * @param order_id
     * @param waybill_number
     * @param courier_firm
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("/sendGoods.do")
    public void sendGoods(Integer order_id,String waybill_number,String courier_firm, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg", "登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        if(order_id==null){
            jsonMap.put("msg", "order_id为空！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }

        if(waybill_number==null||waybill_number.equals("")){
            jsonMap.put("msg", "运单号为空！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }

        if(courier_firm==null||courier_firm.equals("")){
            jsonMap.put("msg", "公司名称为空！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }

        try {
            goodsOrderService.updateSendGoods(waybill_number,courier_firm,order_id);
            jsonMap.put("msg", "操作成功！");
            HtmlUtil.writerJson(response, jsonMap);
        }catch (Exception e){
            jsonMap.put("msg", "操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
            e.printStackTrace();
        }

    }

    /**
     * 保存发货信息
     * @param order_id
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("/sendGoodsInfo.do")
    public void sendGoodsInfo(Integer order_id,HttpServletResponse response, HttpServletRequest request) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg", "登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        if(order_id==null){
            jsonMap.put("msg", "order_id为空！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }

        try {
            GoodsOrder goodsOrder = goodsOrderService.queryByOrderId(order_id);
            jsonMap.put("goodsOrder",goodsOrder);
            jsonMap.put("msg", "操作成功！");
            HtmlUtil.writerJson(response, jsonMap);
        }catch (Exception e){
            jsonMap.put("msg", "操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
            e.printStackTrace();
        }

    }


    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping(value = "/goodsInfo.do")
    public ModelAndView goodsInfo(Integer goods_id,HttpServletRequest request, HttpServletResponse response) {

        Goods g = goodsService.queryById(goods_id);
        Map<String, Object> context = getRootMap();
        if(g!=null){
            String imgurl = g.getImgUrl();
            if(imgurl!=null){
                String[] imgurls = imgurl.split(",");
                g.setImgUrls(imgurls);
                for (int i=0;i<imgurls.length;i++){
                    if(i==0){
                        g.setImgUrl1(imgurls[i]);
                    }
                    if(i==1){
                        g.setImgUrl2(imgurls[i]);
                    }
                    if(i==2){
                        g.setImgUrl3(imgurls[i]);
                    }
                    if(i==3){
                        g.setImgUrl4(imgurls[i]);
                    }
                }
            }
            if(g.getTake_type()==2){
               context.put("stocksClass","display:block;");
               context.put("ziti",false);
                context.put("youji",true);
            }else{
                context.put("stocksClass","display:none;");
                context.put("ziti",true);
                context.put("youji",false);
            }

            if(g.getExchange()==1){
                context.put("exchange1",true);
                context.put("exchange2",false);
            }else{
                context.put("exchange2",true);
                context.put("exchange1",false);
            }

        }


        context.put("goods",g);
        return forword("goods/mall_info", context);
    }


    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping(value = "/editgoods.do")
    public ModelAndView editgoods(Integer goods_id,HttpServletRequest request, HttpServletResponse response) {

        Goods g = goodsService.queryById(goods_id);
        Map<String, Object> context = getRootMap();
        if(g!=null){
            String imgurl = g.getImgUrl();
            if(imgurl!=null){
                String[] imgurls = imgurl.split(",");
                g.setImgUrls(imgurls);
                for (int i=0;i<imgurls.length;i++){
                    if(i==0){
                        g.setImgUrl1(imgurls[i]);
                    }
                    if(i==1){
                        g.setImgUrl2(imgurls[i]);
                    }
                    if(i==2){
                        g.setImgUrl3(imgurls[i]);
                    }
                    if(i==3){
                        g.setImgUrl4(imgurls[i]);
                    }
                }

            }
            if(g.getTake_type()==2){
                context.put("stocksClass","display:block;");
                context.put("ziti",false);
                context.put("youji",true);
            }else{
                context.put("stocksClass","display:none;");
                context.put("ziti",true);
                context.put("youji",false);
            }

            if(g.getExchange()==1){
                context.put("exchange1",true);
                context.put("exchange2",false);
            }else{
                context.put("exchange2",true);
                context.put("exchange1",false);
            }

        }


        context.put("goods",g);
        return forword("goods/mall_update", context);
    }

    @RequestMapping("/updateGoods")
    public void updateGoods(@RequestParam("file1")MultipartFile file1, @RequestParam("file2")MultipartFile file2,
                        @RequestParam("file3")MultipartFile file3, @RequestParam("file4")MultipartFile file4,
                        String pa1,String pa2,String pa3,String pa4,
                        GoodsModel model, HttpServletResponse response,
                        HttpServletRequest request) throws Exception{

        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        String picurl1="";
        String picurl2="";
        String picurl3="";
        String picurl4="";
        String url="";
        //检查是否有重复商品

        url=uploadImg(file1,request);
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")){
            picurl1 = url+",";
        }
        if(url==null||url.equals("")){
            picurl1 = pa1;
        }

        url=uploadImg(file2,request);
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")){
            picurl2 = url+",";
        }
        if(url==null||url.equals("")){
            picurl2 = pa2;
        }

        url=uploadImg(file3,request);
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")) {
            picurl3 = url+",";
        }
        if(url==null||url.equals("")){
            picurl3 = pa3;
        }

        url=uploadImg(file4,request);
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")) {
            picurl4 = url;
        }
        if(url==null||url.equals("")){
            picurl4 = pa4;
        }

        String imgurls = picurl1+picurl2+picurl3+picurl4;
        if(imgurls!=null&&!imgurls.equals("")){
            model.setImgUrl(picurl1+picurl2+picurl3+picurl4);
        }

        goodsService.updateGs(model);

        sendSuccessMessage(response, "保存成功~");
    }
}
