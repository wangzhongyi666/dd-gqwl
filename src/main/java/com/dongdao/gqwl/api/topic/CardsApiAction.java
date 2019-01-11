package com.dongdao.gqwl.api.topic;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.routline.activity.DdInget;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdCards;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.model.routline.topic.DdZrecord;
import com.dongdao.gqwl.model.website.Ddbrowse;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.service.gcolumn.DdbrowseService;
import com.dongdao.gqwl.service.routline.activity.IngetService;
import com.dongdao.gqwl.service.routline.topic.CardconService;
import com.dongdao.gqwl.service.routline.topic.CardsService;
import com.dongdao.gqwl.service.routline.topic.TopicService;
import com.dongdao.gqwl.service.routline.topic.ZrecordService;
import com.dongdao.gqwl.utils.*;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cards")
public class CardsApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(CardsApiAction.class);

    @Autowired
    public CardsService cardsService;
    @Autowired
    public CardconService cardconService;
    @Autowired
    public TopicService topicService;
    @Autowired
    public ZrecordService zrecordService;
    @Autowired
    public DdbrowseService<Ddbrowse> ddbrowseService;
    //查询话题
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/cards.json")
    public Map<String, Object> sendCode(DdCards model,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Integer count=cardsService.queryByCount(model);
        RasteUser user= SessionUtils.getRasteUser(request);
        if(user!=null){
            model.setR_uid(Long.parseLong(user.getId()+"") );
        }
        if(model!=null && model.getPageNum()!=null && model.getPageSize()!=null){
            model.setNum1(model.getPageSize()*(model.getPageNum()-1));
            model.setNum2(model.getPageSize());
        }
        try {
            List<Map<String, Object>> cards=cardsService.selectAll(model);
            for(int i=0;i<cards.size();i++){
                DdZrecord inget=new DdZrecord();
                if(user!=null){
                    inget.setR_uid(model.getR_uid());
                }
                List<HashMap<String,Object>> hashMaps=cardconService.selectByType((Long)cards.get(i).get("cardid"));
                inget.setCardid((Long)cards.get(i).get("cardid"));
                inget.setR_uid(model.getR_uid());
                inget=zrecordService.selectById(inget);
                if(inget!=null){
                    cards.get(i).put("iszan",true);
                }else{
                    cards.get(i).put("iszan",false);
                }
                cards.get(i).put("links",hashMaps);
            }
            jsonMap.put("cards",cards );
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

    @ResponseBody
    @RequestMapping("/carddetail.json")
    public Map<String, Object> onecard(DdCards model,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RasteUser user= SessionUtils.getRasteUser(request);
        HashMap<String,Object> card=cardsService.selectById(model);
        if((Long)card.get("r_uid")!=model.getR_uid()){
            Ddbrowse ddbrowse=new Ddbrowse();
            if(model.getR_uid()!=null){
                ddbrowse.setUser_id(Integer.parseInt(model.getR_uid()+""));
                ddbrowse.setTopid(Integer.parseInt(card.get("cardid")+""));
                ddbrowse=ddbrowseService.selectById(ddbrowse);
                if(ddbrowse!=null){
                    ddbrowse.setCreatetime(DateUtil.getNowPlusTime());
                    ddbrowseService.updateByPrimaryKeySelective(ddbrowse);
                }else{
                    ddbrowse=new Ddbrowse();
                    ddbrowse.setUser_id(Integer.parseInt(model.getR_uid()+""));
                    ddbrowse.setTopid(Integer.parseInt(card.get("cardid")+"") );
                    ddbrowse.setCreatetime(DateUtil.getNowPlusTime());
                    ddbrowseService.insertSelective(ddbrowse);
                }
            }
        }

        List<HashMap<String,Object>> hashMaps=cardconService.selectByType((Long)card.get("cardid"));
        DdZrecord inget=new DdZrecord();
        if(model.getR_uid()!=null){
            inget.setR_uid(model.getR_uid());
        }
        if(model.getTopid()!=null&&model.getTopid()!=0){
            DdTopic topic=new DdTopic();
            topic.setTopid(model.getTopid());
            topic.setOnlooks(0);
            topicService.updateNums(topic);
        }
        inget.setCardid(model.getCardid());
        inget=zrecordService.selectById(inget);
        if(inget!=null){
            card.put("iszan",true);
        }else{
            card.put("iszan",false);
        }
        card.put("links",hashMaps);
        jsonMap.put("card",card);

        try {

            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //点击量+1
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addnums.json")
    public Map<String, Object> addNums(DdCards model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RasteUser user= SessionUtils.getRasteUser(request);
        DdZrecord inget=new DdZrecord();
        inget.setR_uid(model.getR_uid());
        if(model.getZannums()!=null){
            inget.setCardid(model.getCardid());
            int num=0;
            String msg="";
            DdZrecord inget2=(DdZrecord)zrecordService.selectById(inget);
            if(inget2!=null){
                model.setZannums(1);
                zrecordService.delete(inget2);
                num=cardsService.updateNums(model);
                msg="取消成功！";
            }else{
                model.setZannums(0);
                inget.setCreattime(DateUtil.getNowPlusTime());
                if(model.getR_uid()!=null&&model.getR_uid()!=0){
                    zrecordService.insertSelective(inget);
                    num=cardsService.updateNums(model);
                    msg="操作成功！";
                }else{
                    msg="操作失败！";
                }
            }
            try {

                if(num==1){
                    return setSuccessMap(jsonMap, msg, null);
                }else{
                    return setFailureMap(jsonMap, "操作失败！", null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return setFailureMap(jsonMap, "操作失败！", null);
            }
        }else{
            int num=cardsService.updateNums(model);
            if(num==1){
                return setSuccessMap(jsonMap, "操作成功！", null);
            }else{
                return setFailureMap(jsonMap, "操作失败！", null);
            }
        }

    }

    //添加帖子
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addcards.json")
    public Map<String, Object> addCards(DdCards model,int filenum, @RequestParam("file")MultipartFile files,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String filed1="";
        try {
            int num=0;
            if(filenum==1){
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
                RasteUser user= SessionUtils.getRasteUser(request);
                if(user!=null){
                    model.setR_uid(Long.parseLong(user.getId()+"") );
                }
                model.setCreattime(DateUtil.getNowPlusTime());
                model.setIsdelete(1);
                num=cardsService.insertSelective(model);
                request.getSession().setAttribute("cardid",model.getCardid());
            }else{
                num=1;
            }

            if(num==1){
           /*     if(model.getTopid()!=null&&model.getTopid()!=0){
                    DdTopic topic=new DdTopic();
                    topic.setTopid(model.getTopid());
                    topic.setJoinnums(0);
                    topicService.updateNums(topic);
                }*/
                    String filepath="";

                    filepath=uploadifys(files,"cards",response,request);
                    DdCardcon cardcon=new DdCardcon();
                    cardcon.setFilepath(filepath);
                    if(filenum==1){
                        cardcon.setCardid(model.getCardid());
                    }else{
                        cardcon.setCardid(model.getCardid());
                    }
                    if(model.getType()!=null){
                        cardcon.setFiled2(model.getType());
                        if(model.getType()==2){
                             filed1= getpicpath(cardcon);
                            if(filed1!=null&&!"".equals(filed1)){
                                cardcon.setFiled1(filed1);
                            }
                        }
                    }
                    cardconService.insertSelective(cardcon);
                    jsonMap.put("cardid",model.getCardid());
                    jsonMap.put("filed1",filed1);
                    return setSuccessMap(jsonMap, "操作成功！", null);
                }else{
                    return setFailureMap(jsonMap, "操作失败！", null);
                }

        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }


    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/deletecards.json")
    public Map<String,Object> deletecards(DdCards model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setIsdelete(0);
        DdCards myCard=(DdCards)cardsService.selectByPrimaryKey(model.getCardid());
        DdTopic topic=new DdTopic();
        topic.setTopid(myCard.getTopid());
        topic.setJoinnums(1);
        topicService.updateNums(topic);
        int num= cardsService.updateByPrimaryKeySelective(model);
        if(num==1){
            return setSuccessMap(jsonMap, "操作成功！", null);
        }else{
            return setFailureMap(jsonMap, "操作失败！", null);
        }



    }

    /*********************uploadify上传方法***************************/
    public String uploadifys(MultipartFile file, String folder, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        // 文件原名
        String fileName = file.getOriginalFilename().toLowerCase();
        // 文件扩展名
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        // 验证后缀
        if (!VerifyFormat.verifyFormat(fileType)) {
            jsonMap.put("msg", "操作失败！");
        }
        String newname = System.currentTimeMillis() + "." + fileType;
        // 将文件保存到服务器
        String filePath = uploadFiles(file.getInputStream(),newname,folder);

        return filePath;

    }


    public static boolean processImg(String veido_path, String picpath)
            throws IOException {
        System.err.println(veido_path);
        /*
         * File file = new File(veido_path); if (!file.exists()) {
         * System.err.println("路径[" + veido_path + "]对应的视频文件不存在!"); return
         * false; }
         */
        List<String> commands = new java.util.ArrayList<String>();
        /*
         * commands.add(PropertiesUtil.relativeValue(CommonString.PROPERTIESPATH,
         * "FFMPEG_PATH"));
         */
        commands.add( UserConstants.VPNGPATH );
        commands.add("-i");
        commands.add(veido_path);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add("3");// 这个参数是设置截取视频多少秒时的画面
        commands.add("-t");
        commands.add("0.01");
        // commands.add("-s");
        // commands.add("340x340");
        // commands.add(veido_path.substring(0,
        // veido_path.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg");
        commands.add(picpath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 截取视频图片返回预览图
     * @param
     * @return
     */
    public String getpicpath(DdCardcon musicact) {
        String filePath;
        StringBuffer picname = new StringBuffer();
        StringBuffer picPath = new StringBuffer();
        String filepaht = musicact.getFilepath();
        //为空就返回空串
        if(filepaht==null&&"".equals(filepaht)){
            return "";
        }
        try {
            filePath =  UserConstants.FTPPATH  + filepaht;
            /*filePath = "D:\\youyang\\FTP_file" + filepaht;*/
            System.err.println(filePath);
            picname.append("/cards/");
            picname.append(DateUtil.getNowShortDate());
            picname.append(String.valueOf((Math.random() * 10000000)).substring(0, 6));
            picname.append(".jpg");
            picPath.append(UserConstants.UPLOADPATH+ File.separator+UserConstants.UPLOADFOLDER );
            //picPath.append(PropertiesUtil.relativeValue(CommonString.PROPERTIESPATH, "pic"));
		/*	picPath.append("d:\\youyang");
			picPath.append("/uploads/");*/
            picPath.append(picname);
            System.err.println(picPath.toString());
            // 截取图片保存
            boolean isture=processImg(filePath, picPath.toString());
		/*    if(isture){
		    	try {
					CreatePh.createImgThumbnail(picPath.toString(),340,440,picPath.toString());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picname.toString();
    }
}
