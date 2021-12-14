package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.bean.Permission;
import com.yjxxt.crm.bean.Role;
import com.yjxxt.crm.mapper.ModuleMapper;
import com.yjxxt.crm.mapper.PermissionMapper;
import com.yjxxt.crm.mapper.RoleMapper;
import com.yjxxt.crm.query.RoleQuery;
import com.yjxxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.*;

@Service
public class RoleService extends BaseService<Role,Integer> {
    @Autowired(required = false)
    private RoleMapper roleMapper;
    @Autowired(required = false)
    private PermissionMapper permissionMapper;
    @Autowired(required = false)
    private ModuleMapper moduleMapper;
    /**
     * 查询所有的角色信息
     * @return
     */
    public List<Map<String,Object>> findRoles(Integer userId){
      return   roleMapper.selectRoles(userId);

    }

    /**
     * 角色的条件查询
     * @param roleQuery
     * @return
     */
    public Map<String,Object> findRoleByParam(RoleQuery roleQuery){
        Map<String,Object> map=new HashMap<String, Object>();
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getLimit());
        PageInfo<Role> rlist=new PageInfo<>(selectByParams(roleQuery));
        map.put("code",0);
        map.put("msg","success");
        map.put("count",rlist.getTotal());
        map.put("data",rlist.getList());
        return map;
    }

    public void addRole(Role role){
        //角色名称非空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名称");
        //角色名称唯一
        Role temp=roleMapper.selectRoleByName(role.getRoleName());
        AssertUtil.isTrue(temp!=null,"角色已经存在");
        //设定默认值
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        //添加是否成功
        AssertUtil.isTrue(insertHasKey(role)<1,"添加失败了");

    }

    public void changeRole(Role role){
    //验证当前对象是否存在
       Role temp= roleMapper.selectByPrimaryKey(role.getId());
       AssertUtil.isTrue(temp!=null,"待修改记录不存在");
        Role temp2=roleMapper.selectRoleByName(role.getRoleName());
        AssertUtil.isTrue(temp2!=null && !(temp2.getId().equals(role.getId())),"角色已经存在");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"修改失败");

    }

    public void deleteRoleById(Role role) {
        //验证
        AssertUtil.isTrue(role.getId()==null || selectByPrimaryKey(role.getId())==null,"请选择数据");
           role.setIsValid(0);
           role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"删除失败了");

    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer roleId, Integer[] mids) {
        AssertUtil.isTrue(roleId==null|| roleMapper.selectByPrimaryKey(roleId)==null,"请选择角色");
        //t_permission  roleId,mid,
        //统计当前角色的资源数量
        int count=permissionMapper.countRoleModulesByRoleId(roleId);
        if(count>0){
            //删除当前角色的资源信息
            AssertUtil.isTrue(permissionMapper.deleteRoleModuleByRoleId(roleId)!=count,"角色资源分配失败");
        }
        //删除角色的资源信息
        List<Permission> plist=new ArrayList<Permission>();
        if(mids!=null && mids.length>0){
            //遍历mids
            for (Integer mid:mids) {
                //实例化对象
                Permission permission=new Permission();
                permission.setRoleId(roleId);
                permission.setModuleId(mid);
                //权限码
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());

                plist.add(permission);
            }
        }
        AssertUtil.isTrue(permissionMapper.insertBatch(plist)!=plist.size(),"授权失败");
    }
}



























































