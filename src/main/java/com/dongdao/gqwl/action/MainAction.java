package com.dongdao.gqwl.action;

import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/guangqi")

public class MainAction extends BaseAction {

    private final static Logger log= Logger.getLogger(MainAction.class);

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysUserService sysUserService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysMenuService sysMenuService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysMenuBtnService sysMenuBtnService;

    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping(value = "/login.shtml")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }
    @ResponseBody
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/tologin.do")
    public void getUsers(String email, String pwd, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(StringUtils.isBlank(email)){
            sendFailureMessage(response, "账号不能为空.");
            return;
        }
        if(StringUtils.isBlank(pwd)){
            sendFailureMessage(response, "密码不能为空.");
            return;
        }
        List<SysUser> users=sysUserService.queryLogin(email,pwd);
        String msg = "用户登录日志:";
        if(users==null||users.size()==0){
            //记录错误登录日志
            log.debug(msg+"["+email+"]"+"账号或者密码输入错误.");
            sendFailureMessage(response, "账号或者密码输入错误.");
            return;
        }else if (users.size()==1){

            if(users.get(0).getState()== BaseBean.STATE.DISABLE.key){

                sendFailureMessage(response, "账号已被禁用.");
                return;
            }else{
                //登录次数加1 修改登录时间
                int loginCount = 0;
                if(users.get(0).getLoginCount() != null){
                    loginCount = users.get(0).getLoginCount();
                }
                int id = users.get(0).getId();
                loginCount++;
                Date loginTime = DateUtil.getDateByString("");
                sysUserService.updateLogin(id,loginCount,loginTime);
                //用户信息放入session
                SessionUtils.setUser(request,users.get(0));
                sendSuccessMessage(response, "登录成功.");

            }
        }

    }
    @Auth(verifyURL=false)
    @RequestMapping("/main.shtml")
    public ModelAndView main( HttpServletRequest request)throws Exception{
        Map<String,Object>  context = getRootMap();
        SysUser user = SessionUtils.getUser(request);

         List<SysMenu> rootMenus = null;
        List<SysMenu> childMenus = null;
        List<SysMenu> childBtns = null;
        //超级管理员
        if(user != null && Constant.SuperAdmin.YES.key ==  user.getSuperAdmin()){
            rootMenus = sysMenuService.getRootMenu(null);// 查询所有根节点
            childMenus = sysMenuService.getChildMenu();//查询所有子节点
        }else{
            rootMenus = sysMenuService.getRootMenuByUser(user.getJid() );//根节点
            childMenus = sysMenuService.getChildMenuByUser(user.getJid());//子节点
            childBtns = sysMenuService.getChildMenuBtnByUser(user.getId());//按钮操作


           buildData(childMenus,childBtns,request); //构建必要的数据
        }
        String nameRole = "";
        String deptName = "";
        if(user != null){
            nameRole = sysUserService.queryNameRole(user.getId());
            deptName = sysUserService.queryDeptName(user.getId());
            context.put("nameRole",nameRole);
            context.put("deptName",deptName);
        }
        context.put("user", user);
        context.put("menuList", treeMenu(rootMenus,childMenus));
        return forword("/main",context);
    }

    /**
     * 构建树形数据
     * @return
     */
    private List<TreeNode> treeMenu(List<SysMenu> rootMenus, List<SysMenu> childMenus){
        TreeUtil util = new TreeUtil(rootMenus,childMenus);
        return util.getTreeNode();
    }


    /**
     * 构建树形数据
     * @return
     */
    private void buildData(List<SysMenu> childMenus, List<SysMenu> childBtns, HttpServletRequest request){
        //能够访问的url列表
        List<String> accessUrls  = new ArrayList<String>();
        //菜单对应的按钮
        Map<String,List> menuBtnMap = new HashMap<String,List>();
        for(SysMenu menu: childMenus){
            //判断URL是否为空
            if(StringUtils.isNotBlank(menu.getUrl())){
                List<String> btnTypes = new ArrayList<String>();
                for(SysMenu btn  : childBtns){
                    if(menu.getId().equals(btn.getParentId())){
                        btnTypes.add(btn.getUrl());
                        URLUtils.getBtnAccessUrls(menu.getUrl(), btn.getActions(),accessUrls);
                    }
                }
                menuBtnMap.put(menu.getUrl(), btnTypes);
                URLUtils.getBtnAccessUrls(menu.getUrl(), menu.getActions(),accessUrls);
                accessUrls.add(menu.getUrl());
            }
        }
        SessionUtils.setAccessUrl(request, accessUrls);//设置可访问的URL
        SessionUtils.setMemuBtnMap(request, menuBtnMap); //设置可用的按钮
    }

    /**
     * 修改密码
     */
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping(value = "/changepwd.shtml")
    public String changePwd(HttpServletRequest request, HttpServletResponse response) {
        return "/changepwd";
    }

    /**
     * 修改密码
     * @param oldPwd
     * @param password
     * @return
     * @throws Exception
     */
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/modifyPwd")
    public void modifyPwd(String oldPwd,String password,HttpServletRequest request,HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user == null){
            sendFailureMessage(response, "对不起,登录超时.");
            return;
        }
        SysUser bean  = sysUserService.queryById(user.getId());
        if(bean.getId() == null){
            sendFailureMessage(response, "对不起,用户不存在.");
            return;
        }
        if(StringUtils.isBlank(password)){
            sendFailureMessage(response, "密码不能为空.");
            return;
        }

        //判断密码是否包含数字：包含返回1，不包含返回0
        int i = password.matches(".*\\d+.*") ? 1 : 0;

        //判断密码是否包含字母：包含返回1，不包含返回0
        int j = password.matches(".*[a-zA-Z]+.*") ? 1 : 0;

        //判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
        int k = password.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;

        //判断密码长度是否在6-20位
        int l = password.length();

        //判断密码中是否包含用户名
        //boolean contains = newPwd.contains("123456");

        if (i + j + k < 2 || l < 6 || l > 20 ) {
            sendFailureMessage(response, "请输入6-20个字符，数字，字母，符号不少于两种组合的密码");
            return;
        }

        if(password.equals("123456")){
            sendFailureMessage(response, "不能为初始密码。");
            return;
        }
        //bean.setPwd(MethodUtil.MD5(password));
        bean.setPwd(password);
        sysUserService.updatePwd(bean.getId(),password);
        sendSuccessMessage(response, "修改成功！");
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws Exception
     */
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/logout")
    public void  logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
        SessionUtils.removeUser(request);
        response.sendRedirect(URLUtils.get("msUrl")+"/flexiblebe/login.shtml");
    }
}
