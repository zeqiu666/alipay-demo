package com.yjxxt.crm.service;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.bean.Module;
import com.yjxxt.crm.dto.TreeDto;
import com.yjxxt.crm.mapper.ModuleMapper;
import com.yjxxt.crm.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {
    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    public List<TreeDto> selectModules(){
        return moduleMapper.selectModules();
    }

    public List<TreeDto> findModulesByRoleId(Integer roleId){
        //获取所有资源信息
        List<TreeDto> tlist = moduleMapper.selectModules();
        //获取当前角色的拥有的咨询信息
        List<Integer> roleHasModuls=permissionMapper.selectModelByRoleId(roleId);
        //遍历
        for (TreeDto treeDto: tlist) {
            if(roleHasModuls.contains(treeDto.getId())){
                treeDto.setChecked(true);
            }
        }
        //判断比对，checked=true;
        return tlist;
    }

    public Map<String, Object> queryModules() {
        Map<String,Object> map=new HashMap();
       List<Module> mlist= moduleMapper.selectAllModules();
        map.put("code",0);
        map.put("msg","success");
        map.put("count",mlist.size());
        map.put("data",mlist);
        return map;
    }
}



























































