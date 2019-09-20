using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Language{

    public static Dictionary<int, string> language = new Dictionary<int, string>()
    {
        {1, "用户名不存在"},
        {2, "用户名密码不正确" },
        {3, "权限不足"},
        {4, "登陆未知错误"},
        {5, "获取失败"},
        {6, "未发现可用设备"}
    };
}
