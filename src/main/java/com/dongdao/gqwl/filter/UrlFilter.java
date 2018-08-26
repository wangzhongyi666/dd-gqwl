package com.dongdao.gqwl.filter;

import com.dongdao.gqwl.bean.SysMenu;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.service.SysMenuBtnService;
import com.dongdao.gqwl.service.SysMenuService;
import com.dongdao.gqwl.utils.SessionUtils;
import com.dongdao.gqwl.utils.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
//重点
@WebFilter(filterName = "login", urlPatterns = "/*")
public class UrlFilter implements Filter {

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysMenuService sysMenuService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysMenuBtnService sysMenuBtnService;

    protected static List<Pattern> patterns = new ArrayList<Pattern>();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                   FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //打印请求Url
        //验证用户是否登录
        //如果用户登录 验证url是否正确
        //验证url  .do  , .shtml


       /* List<SysMenu> rootMenus = null;
        System.out.println("this is MyFilter,url :" + request.getRequestURI());
        rootMenus = sysMenuService.getAllMenu();// 查询所有根节点
        if(!request.getRequestURI().equals("/")){
            if(rootMenus!=null&&rootMenus.size()>0){
                for (SysMenu menu : rootMenus){
                    if(menu!=null&&menu.getUrl()!=null&&menu.getUrl().equals(request.getRequestURI())){
                        SysUser user = SessionUtils.getUser(request);
                        if(user==null){
                            response.sendRedirect(URLUtils.get("msUrl")+"/guangqi/login.shtml");
                            return;
                        }
                    }else{
                        response.sendRedirect(URLUtils.get("msUrl")+"/guangqi/login.shtml");
                        return;
                    }
                }
            }
        }*/

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
       // logger.info("aaaaaaaaaa");
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }

        if (isInclude(url)){
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        } else {
            SysUser user = SessionUtils.getUser(request);
            if (user != null){
                // session存在
                filterChain.doFilter(httpRequest, httpResponse);
                return;
            } else {
                // session不存在 准备跳转失败
        /* RequestDispatcher dispatcher = request.getRequestDispatcher(path);
          dispatcher.forward(request, response);*/
                System.out.println("this is MyFilter,url :" + request.getRequestURI());
                List<SysMenu> rootMenus = sysMenuService.getAllMenu();// 查询所有根节点
                for (SysMenu menu : rootMenus){
                    if(menu!=null&&menu.getUrl()!=null&&menu.getUrl().equals(request.getRequestURI())){
                        response.sendRedirect(URLUtils.get("msUrl")+"/guangqi/login.shtml");
                        return;
                    }
                }
                filterChain.doFilter(httpRequest, httpResponse);
                return;
            }
        }
        //System.out.println("this is MyFilter,url :" + request.getRequestURI());
        //filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        Pattern p = Pattern.compile("\\*.shtml|.do");
        patterns.add(p);
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            return matcher.find();
        }
        return false;
    }

}

