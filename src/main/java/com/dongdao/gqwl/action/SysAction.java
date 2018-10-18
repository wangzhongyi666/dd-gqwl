package com.dongdao.gqwl.action;

import com.dongdao.gqwl.bean.SysDept;
import com.dongdao.gqwl.bean.SysMenu;
import com.dongdao.gqwl.bean.SysRole;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.SysDeptModel;
import com.dongdao.gqwl.model.SysRoleModel;
import com.dongdao.gqwl.model.SysUserModel;
import com.dongdao.gqwl.service.SysMenuService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import com.dongdao.gqwl.service.SysDeptService;
import com.dongdao.gqwl.service.SysRoleService;
import com.dongdao.gqwl.service.SysUserService;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/sys")
public class SysAction extends BaseAction {

    private final static Logger log= Logger.getLogger(SysAction.class);
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysUserService sysUserService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysDeptService sysDeptService;

    @Autowired(required = false)
    public SysRoleService sysRoleService;

    @Autowired(required = false)
    public SysMenuService sysMenuService;

    @RequestMapping(value = "/sysUser.do")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        SysDeptModel model = new SysDeptModel();
        //地市列表
        List<SysDept> deptList = sysDeptService.queryByList1(model);
        //角色列表
        List<SysRole> roleList = sysRoleService.queryAllRole();
        Map<String, Object> context = getRootMap();
        context.put("roleList",roleList);
        context.put("deptList",deptList);
        return forword("sys/sysUser", context);
    }

    //获取审核列表
    @RequestMapping("/userDataList")
    public void userDataList(SysUserModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<SysUser> dataList=sysUserService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取用户总数
    @RequestMapping("/userCountDataList.do")
    public void userDataCount(SysUserModel model, HttpServletRequest request, HttpServletResponse response) {
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
        Integer count=sysUserService.queryByCount(model);
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

    //添加用户
    @RequestMapping("/adduser.do")
    public void addUser(SysUserModel model, HttpServletRequest request, HttpServletResponse response){
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            sendFailureMessage(response, "登录超时！");
            return;
        }
        model.setState(0);
        model.setLoginCount(0);
        model.setUpdateTime(new Date());
        if(model!=null&&model.getJid()!=null){
            if(model.getJid()!=1){
                model.setSuperAdmin(0);
            }else{
                model.setSuperAdmin(1);
            }
        }else{
            model.setSuperAdmin(0);
        }
        model.setCreateBy(user.getId());
        sysUserService.add(model);
        sendSuccessMessage(response,"注册成功！");
    }


    @RequestMapping("/queryByUserId")
    public void queryByUserId(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        SysUser sysuser=sysUserService.queryById(id);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("data", sysuser);
        HtmlUtil.writerJson(response, jsonMap);
    }

    //添加用户
    @RequestMapping("/updateuser.do")
    public void updateUser(SysUserModel model, HttpServletRequest request, HttpServletResponse response){
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            sendFailureMessage(response, "登录超时！");
            return;
        }

        model.setUpdateTime(new Date());
        sysUserService.updateInfo(model);
        sendSuccessMessage(response,"操作成功！");
    }



    @RequestMapping(value = "/sysRole.do")
    public ModelAndView sysRole(HttpServletRequest request, HttpServletResponse response) {
        SysDeptModel model = new SysDeptModel();

        Map<String, Object> context = getRootMap();
        return forword("sys/sysRole", context);
    }

    //获取审核列表
    @RequestMapping("/roleDataList.do")
    public void roleDataList(SysRoleModel model, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<SysRole> dataList=sysRoleService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取用户总数
    @RequestMapping("/roleDataCount.do")
    public void roleDataCount(SysRoleModel model, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        Integer count=sysRoleService.queryByCount(model);
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

    @RequestMapping(value = "/sysDept.do")
    public ModelAndView sysDept(HttpServletRequest request, HttpServletResponse response) {
        SysDeptModel model = new SysDeptModel();

        Map<String, Object> context = getRootMap();
        return forword("sys/sysDept", context);
    }

    //获取审核列表
    @RequestMapping("/deptDataList.do")
    public void deptDataList(SysDeptModel model, HttpServletRequest request, HttpServletResponse response) {
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

        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<SysDept> dataList=sysDeptService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取用户总数
    @RequestMapping("/deptDataCount.do")
    public void deptDataCount(SysDeptModel model, HttpServletRequest request, HttpServletResponse response) {
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
        Integer count=sysDeptService.queryByCount(model);
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

    @Auth(verifyURL=false)
    @RequestMapping(value = "/sysBusDept.do")
    public ModelAndView sysBusDept(Integer deptId,String deptName,HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        context.put("parentId",deptId);
        context.put("deptName",deptName);
        return forword("sys/sysSubDept", context);
    }

    //获取审核列表
    @RequestMapping("/queryDeptData.do")
    public void queryDeptData(Integer id, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        SysDept sysDept = sysDeptService.queryByDept(id);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("dept", sysDept);
        HtmlUtil.writerJson(response, jsonMap);
    }


    //获取审核列表
    @RequestMapping("/deptSubDataList")
    public void deptSubDataList(SysDeptModel model, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        List<SysDept> dataList=sysDeptService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }


    //获取用户总数
    @RequestMapping("/deleteRole.do")
    public void deleteRole(Integer id, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = SessionUtils.getUser(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(user==null){
            jsonMap.put("msg", "登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        if(id==null){
            jsonMap.put("msg", "role_id不能为空！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        Integer result=sysRoleService.deleteById(id);

        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);

    }

    /**
     * 获取权限树形菜单数据
     * @param roleId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getTree.do")
    public void getTree(Integer roleId,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysDeptModel model = new SysDeptModel();
        List<Map<String,Object>> datalist=null;
        List<SysMenu> mlist=sysMenuService.getAllMenu();
        List<Integer> rolelist=sysMenuService.getIdByRoleId(roleId);
        SysMenu nu=new SysMenu();
        String str=getTreeList(nu,datalist,mlist,rolelist);
        System.out.println(str);
        jsonMap.put("msg", "操作成功！");
        jsonMap.put("str", str);
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 添加、修改权限
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveRole.do")
    public void saveRole(SysRoleModel model,String menustr,HttpServletRequest request, HttpServletResponse response)throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String[] menulist=menustr.split(",");
        model.setJsign(model.getJname());
        model.setCreate_time(DateUtil.getNowPlusTime());
        model.setUpdate_time(DateUtil.getNowPlusTime());
        if(model.getId()==0){
            sysMenuService.addRole(model);

        }else{
            sysRoleService.deleteRelByRoleId(model.getId());
        }
        if(menulist!=null && menulist.length>0){
            for (String objId : menulist){
                model.setObjId(Integer.parseInt(objId));
                model.setRelType(0);
                sysMenuService.addRoleRel(model);
            }
        }
        //sysMenuService.addRoleRel(model);
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 获取树
     * @方法名:getTreeList
     * @参数 @param kpi
     * @参数 @return
     * @返回类型 String
     */
    public String getTreeList(SysMenu kpi,List<Map<String, Object>> listmap,List<SysMenu> list,List<Integer> menulist) {

        if(listmap == null || list == null ) {
            //list= super.findList(kpi);
            listmap = new ArrayList<Map<String,Object>>();
            for(SysMenu k: list) {
                if(k.getParentId() == null || "".equals(k.getParentId()) || "null".equals(k.getParentId())) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("menuId", k.getId().toString());
                    map.put("text", k.getName());
                    if(menulist!=null && menulist.size()>0){
                        for(Integer menuId : menulist){
                            if(k.getId().equals(menuId)){
                                Map<String, Object> map1 = new HashMap<String, Object>();
                                map1.put("checked", true);
                                map.put("state",map1);
                            }
                        }
                    }
                    listmap.add(map);
                }
            }
            getTreeList(kpi,listmap,list,menulist);

        } else if(listmap.size()>0 && list.size()>0) {
            for(Map<String, Object> mp:listmap) {
                List<Map<String, Object>> childlist = new ArrayList<Map<String,Object>>();
                for(SysMenu k:list) {
                    String id = mp.get("menuId")+"";
                    String pid = k.getParentId()+"";
                    if(id.equals(pid)) {
                        Map<String, Object> m = new HashMap<String, Object>();
                        m.put("menuId", k.getId().toString());
                        m.put("text", k.getName());
                        if(menulist!=null && menulist.size()>0){
                            for(Integer menuId : menulist){
                                if(k.getId().equals(menuId)){
                                    Map<String, Object> map1 = new HashMap<String, Object>();
                                    map1.put("checked", true);
                                    m.put("state",map1);
                                }
                            }
                        }
                        childlist.add(m);
                    }
                }
                if(childlist.size()>0) {
                    List<String> sizelist = new ArrayList<String>();
                    sizelist.add(childlist.size()+"");
                    mp.put("nodes", childlist);
                    mp.put("tags", sizelist);
                    getTreeList(kpi,childlist,list,menulist);
                }
            }
        }
        return JSONArray.fromObject(listmap).toString();
    }


    /**
     * 添加省份
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "/addDept.do")
    public void addDept(SysDeptModel model ,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysDept sd = sysDeptService.getDescDept();
        model.setDeptId(sd.getDeptId()+1);
        model.setDeleted(0);
        if(model.getTank() == 2){
            model.setParentId(0);
        }
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setIs_date(1);
        model.setBase(100.0);
        model.setYilbase(100.0);
        model.setDre_type(1);
        sysDeptService.addDept(model);
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 删除地区
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteDept.do")
    public void deleteDept(SysDeptModel model ,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        sysDeptService.deleteDept(model);
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 删除人员
     * @param request
     * @param response
     */
    @RequestMapping(value = "/delUser.do")
    public void delUser(Integer id ,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        sysUserService.delUser(id);
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping(value = "/getDeptByParentId.do")
    public void getDeptByParentId(Integer parentId ,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<SysDept> deptlist=sysDeptService.getDeptByParentId(parentId);
        jsonMap.put("msg", "操作成功！");
        jsonMap.put("data", deptlist);
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping(value = "/updateDeptByDeptId.do")
    public void updateDeptByDeptId(SysDeptModel model ,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        try {
            sysDeptService.updateDeptByDeptId(model);
            jsonMap.put("msg", "操作失败！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }


}
