/*<![CDATA[*/
var nodId=0;
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countlist($('#pageSizeInp').val());

    $("#searchbtn").on('click',function(){
        countlist($('#pageSizeInp').val());
    });
    $(".pagebtn").on('click',function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });

});


function countlist(pageSize){
    var jname=$("#jname").val();
    console.log(jname)
    $.ajax({
        url :'/sys/roleDataCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            jname:jname,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            $('#box').paging({
                initPageNo: 1, // 初始页码
                totalPages: result.pageCount, //总页数
                totalCount: '合计' + result.allNum + '条数据', // 条目总数
                slideSpeed: 600, // 缓动速度。单位毫秒
                jump: true, //是否支持跳转
                callback: function(page) { // 回调函数
                    getlist(page,pageSize);
                }
            })
        }
    });
}

function getlist(pageNum,pageSize){
    var jname=$("#jname").val();
    $.ajax({
        url :'/sys/roleDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            jname:jname,
            pageNum:pageNum,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            const  rowlist=[];
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;pageSize>i;i++){
                    const rowlist1=[];
                    if(i<datalist.length){
                        var row=datalist[i];
                        rowlist1[0]=row.jname;
                        rowlist1[1]=row.jdesc;
                        rowlist1[2]=row.create_time.toString().substring(0,19);
                        rowlist1[3]=row.update_time.toString().substring(0,19);

                        rowlist1[4]="<a href=\"javascript:void(0)\" a1="+row.id+" a2="+row.jname+" onclick=\"getTree(this)\" class=\"text-primary ml10\">编辑</a>"+
                            "<a href=\"javascript:void(0)\" a1="+row.id+" onclick=\"deleteRole(this)\" class=\"text-primary ml10\">删除</a>";
                    }else{
                        rowlist1[0]='';
                        rowlist1[1]='';
                        rowlist1[2]='';
                        rowlist1[3]='';
                        rowlist1[4]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"userDataList");
        }
    });
}

function goon(){
    var a=$('#pageNumInp').val();
    if(a!=null && a>0){
        getlist(a,$('#pageSizeInp').val())
    }else{
        alert("请输入页数!");
    }
}
function getTree(e){
    shadboxFun1('add',e);
    $.ajax({
        url :'/sys/getTree.do',
        type : 'POST',
        timeout : 20000,
        data:{
            roleId:$("#roleId").val()
        },
        async: false,
        success : function(result) {
            var tree =result.str;
            $('#tree').treeview({
                data: tree,
                showCheckbox:true,
                levels:0,
                onNodeChecked: function(event, node) { //选中节点
                    var selectNodes = getChildNodeIdArr(node); //获取所有子节点
                    if (selectNodes) { //子节点不为空，则选中所有子节点
                        $('#treeview-checkable').treeview('checkNode', [selectNodes, { silent: true }]);
                    }
                    var parentNode = $("#treeview-checkable").treeview("getNode", node.parentId);
                    setParentNodeCheck(node);
                },
                onNodeUnchecked: function(event, node) {
                    //取消选中节点
                    // 取消父节点 子节点取消
                    var selectNodes = setChildNodeUncheck(node); //获取未被选中的子节点
                    var childNodes = getChildNodeIdArr(node);    //获取所有子节点
                    if (selectNodes && selectNodes.length==0) { //有子节点且未被选中的子节点数目为0，则取消选中所有子节点
                        console.log("反选");
                        $('#treeview-checkable').treeview('uncheckNode', [childNodes, { silent: true }]);
                    }
                    // 取消节点 父节点取消
                    var parentNode = $("#treeview-checkable").treeview("getNode", node.parentId);  //获取父节点
                    var selectNodes = getChildNodeIdArr(node);
                    setParentNodeCheck(node);
                }
            });

        }
    });
}

function saveMenu(){
    var roleId=$("#roleId").val();
    var jname=$("#jnameinp").val();
    if(jname==null||jname==''){
        layer.alert("职位名称不能为空");
        return;
    }
    var a=$('#tree').treeview('getChecked');
    var b= '';
    if(a!=null && a.length>0){
        for(var i=0;i<a.length;i++){
            if(i==a.length-1){
                b+=a[i].menuId;
            }else{
                b+=a[i].menuId+",";
            }

        }
    }
    $.ajax({
        url :'/sys/saveRole.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:roleId,
            jname:jname,
            menustr:b
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg);
            $(this).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");
            $('.popbox-container').fadeOut();
            countlist($('#pageSizeInp').val());
        }
    });

}
function deleteRole(e){
    var id = $(e).attr("a1");
    if(confirm("确认删除吗！")){
        $.ajax({
            url :'/sys/deleteRole.do',
            type : 'POST',
            timeout : 20000,
            data:{
                id:id
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);
                countlist($('#pageSizeInp').val());
            }
        });
    }

}

// 选中父节点时，选中所有子节点
function getChildNodeIdArr(node) {
    var ts = [];
    if (node.nodes) {
        for (x in node.nodes) {
            ts.push(node.nodes[x].nodeId);
            if (node.nodes[x].nodes) {
                var getNodeDieDai = getChildNodeIdArr(node.nodes[x]);
                for (j in getNodeDieDai) {
                    ts.push(getNodeDieDai[j]);
                }
            }
        }
    } else {
        ts.push(node.nodeId);
    }
    return ts;
}

// 选中所有子节点时，选中父节点 取消子节点时取消父节点
function setParentNodeCheck(node) {
    var parentNode = $("#treeview-checkable").treeview("getNode", node.parentId);
    if (parentNode.nodes) {
        var checkedCount = 0;
        for (x in parentNode.nodes) {
            if (parentNode.nodes[x].state.checked) {
                checkedCount ++;
            } else {
                break;
            }
        }
        if (checkedCount == parentNode.nodes.length) {  //如果子节点全部被选 父全选
            $("#treeview-checkable").treeview("checkNode", parentNode.nodeId);
            setParentNodeCheck(parentNode);
        }else {   //如果子节点未全部被选 父未全选
            $('#treeview-checkable').treeview('uncheckNode', parentNode.nodeId);
            setParentNodeCheck(parentNode);
        }
    }
}

// 取消父节点时 取消所有子节点
function setChildNodeUncheck(node) {
    if (node.nodes) {
        var ts = [];    //当前节点子集中未被选中的集合
        for (x in node.nodes) {
            if (!node.nodes[x].state.checked) {
                ts.push(node.nodes[x].nodeId);
            }
            if (node.nodes[x].nodes) {
                var getNodeDieDai = node.nodes[x];
                console.log(getNodeDieDai);
                for (j in getNodeDieDai) {
                    if (!getNodeDieDai.nodes[x].state.checked) {
                        ts.push(getNodeDieDai[j]);
                    }
                }
            }
        }
    }
    return ts;
}


/*]]>*/