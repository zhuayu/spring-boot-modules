package com.aitschool.user.Rabc.constant;

import java.util.List;
import java.util.Map;

public class PermissionConstants {
    public static final List<Map<String, Object>> GROUPS = List.of(
            Map.of("name","rabc-manage", "display_name","权限管理", "permissions", List.of(
                    Map.of("name","administrators-index", "display_name","管理员-列表"),
                    Map.of("name","administrators-update", "display_name","管理员-更新"),
                    Map.of("name","administrators-delete", "display_name","管理员-删除"),
                    Map.of("name","administrators-store", "display_name","管理员-创建"),
                    Map.of("name","role-index", "display_name","角色-列表"),
                    Map.of("name","role-update", "display_name","角色-更新"),
                    Map.of("name","role-delete", "display_name","角色-删除"),
                    Map.of("name","role-store", "display_name","角色-创建")
            )),
            Map.of("name","user-manage", "display_name","用户管理", "permissions", List.of(
                    Map.of("name","user-index", "display_name","用户-列表"),
                    Map.of("name","user-update", "display_name","用户-更新"),
                    Map.of("name","user-delete", "display_name","用户-删除"),
                    Map.of("name","user-store", "display_name","用户-创建")
            )));
}